package node.http;

import java.beans.PropertyDescriptor;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import javax.servlet.ServletException;

import node.services.PackageLoader;
import node.utils.ResultSerializer;
import node.utils.TaskPopulator;

import org.joda.time.Chronology;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.joda.time.format.PeriodFormat;
import org.joda.time.format.PeriodFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import api.annotations.Value;
import api.computation.ComputationContext;

import com.caucho.hessian.server.HessianServlet;

/**
 * <ol>
 * 	<li>Intercepts request</li>
 * 	<li>Performs requested operation</li>
 * 	<li>Returns operation result.</li>
 * </ol>
 * 
 * @author malczyk.pawel@gmail.com
 *
 */
public class DispatcherServlet extends HessianServlet implements Dispatcher {

	/**
	 * Exception message.
	 */
	private static final String EXCEPTION = "Exception";

	/**
	 * Constant.
	 */
	private static final int BUFFER_SIZE = 1024;

	/**
	 * Serial.
	 */
	private static final long serialVersionUID = -9124481584977383081L;
	
	/**
	 * Logger.
	 */
	private static final Logger LOG = LoggerFactory.getLogger(DispatcherServlet.class);
	
	/**
	 * Loads classes needed by particular computation.
	 */
	private PackageLoader packageLoader;
	
	/**
	 * Holds mapping for particular operations.
	 */
	private Map<String, String> operationMapper = new HashMap<String, String>();
	
	/**
	 * Constructor.
	 */
	public DispatcherServlet() {
		super();
	}
	
	/** {@inheritDoc} */
	@Override
	public void init() throws ServletException {
		super.init();
		String jarLocation = getServletContext().getInitParameter("libLocation");
		String configLocation = getServletContext().getInitParameter("confLocation");
		LOG.info("JAR {}", jarLocation);
		LOG.info("CONF {}", configLocation);
		packageLoader = PackageLoader.getInstance(jarLocation);
		operationMapper = Collections.synchronizedMap(TaskPopulator.populate(jarLocation));
		operationMapper.putAll(TaskPopulator.parseConfigs(configLocation));
	}

	
	/** {@inheritDoc} */
	@Override
	public byte [] dispatch(byte [] data) {
		return data;
	}
	
	/** {@inheritDoc} */
	@SuppressWarnings("unchecked")
	@Override
	public synchronized byte [] compute(String computation, byte [] inputs, byte [] outputs, ComputationContext computationContext) {
		byte [] results = null;
		try {
			ObjectInputStream inputsStream = new ObjectInputStream(new GZIPInputStream(new ByteArrayInputStream(inputs)));
			ObjectInputStream outputsStream = new ObjectInputStream(new GZIPInputStream(new ByteArrayInputStream(outputs)));
			HashMap<String, Serializable> in = (HashMap<String, Serializable>) inputsStream.readObject();
			HashMap<String, Serializable> out = (HashMap<String, Serializable>) outputsStream.readObject();
			HashMap<String, Serializable> computed = compute(computation, in, out, computationContext);
			ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOut);
			objectOutputStream.writeObject(computed);
			results = gzipData(byteArrayOut.toByteArray());
		} catch (Exception e) {
			LOG.warn(EXCEPTION, e);
		}
		return results;
	}
	
	
	/**
	 * Compresses data with using GZIP.
	 * @param input input data.
	 * @return compressed data
	 * @throws IOException exception throwed
	 */
	private byte [] gzipData(byte [] input) throws IOException {
		ByteArrayOutputStream gzipped = new ByteArrayOutputStream();
		GZIPOutputStream gzipStream = new GZIPOutputStream(gzipped);
		ByteArrayInputStream is = new ByteArrayInputStream(input);
		byte [] buf = new byte[BUFFER_SIZE];
		int numRead;

		while ((numRead = is.read(buf)) > -1) {
			gzipStream.write(buf, 0, numRead);
		}
		
		gzipStream.close();
		gzipped.flush();
		byte[] gzippedArray = gzipped.toByteArray();
		return gzippedArray;
	}

	/** {@inheritDoc} */
	@SuppressWarnings("deprecation")
	@Override
	public synchronized HashMap<String, Serializable> compute(String computation, Map<String, Serializable> inputs, HashMap<String, Serializable> outputs, ComputationContext computationContext) {
		String className = operationMapper.get(computation);
		if(className==null) {
			LOG.error("Operation {} not found", computation);
		}
		
		Class<?> clazz = packageLoader.load(className);
		
		if(clazz == null) {
			LOG.error("Unable to load class");
		}
		
		try {
			Class<?> computableClazz = Class.forName(className);
			Object computableTask = computableClazz.cast(clazz.newInstance());
			
			Date startDate = new Date();
			if(computableClazz.isInstance(computableTask)) {
				Field [] fields = computableTask.getClass().getDeclaredFields();
				
				preRun(inputs, computationContext, clazz, computableTask, fields);
				
				Method method = clazz.getMethod("doComputation", new Class[]{});
				method.invoke(computableTask, new Object[]{});
				
				postRun(outputs, clazz, computableTask, fields);
			}
			
			Date endDate = new Date();
			Period period = new Period(endDate.getTime()-startDate.getTime(), PeriodType.standard(), Chronology.getGregorian());
			PeriodFormatter formatter = PeriodFormat.getDefault();
			String periodStr = formatter.print(period);
			LOG.info("Computation {} done in {}", computation, periodStr);
		} catch (Exception e) {
			LOG.warn(EXCEPTION, e);
		} 
		return outputs;
	}


	/**
	 * Pre running logic sets values etc.
	 * @param outputs outputs map
	 * @param clazz task class
	 * @param computableTask computable task
	 * @param fields array of class fields.
	 * @throws Exception throwed
	 */
	private void postRun(HashMap<String, Serializable> outputs, Class<?> clazz, Object computableTask, Field[] fields)  throws Exception {
		for(String outputName : outputs.keySet()) {
			for(Field field : fields) {
				if(field.isAnnotationPresent(Value.class)) {
					Value valAnnotation = field.getAnnotation(Value.class);
					if(outputName.equals(valAnnotation.name())) {
						PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), clazz);
						Object result = propertyDescriptor.getReadMethod().invoke(computableTask);
						outputs.put(outputName, (Serializable) result);
					}
				}
			}
		}
	}


	/**
	 * Post running logic sets values etc.
	 * @param inputs map of inputs
	 * @param computationContext computation context
	 * @param clazz class of task
	 * @param computableTask computable task
	 * @param fields fieds
	 * @throws Exception exception Throwed
	 */
	private void preRun(Map<String, Serializable> inputs, ComputationContext computationContext, Class<?> clazz, Object computableTask, Field[] fields) throws Exception {
		if(computationContext!=null) {
			for (Field field : fields) {
				if (field.isAnnotationPresent(api.annotations.ComputationContext.class)) {
					PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), clazz);
					propertyDescriptor.getWriteMethod().invoke(computableTask, computationContext);
					LOG.info("Settig computation context");
				}
			}
		}
		
		for(String inputName : inputs.keySet()) {
			for(Field field : fields) {
				if(field.isAnnotationPresent(Value.class)) {
					Value valAnnotation = field.getAnnotation(Value.class);
					if(inputName.equals(valAnnotation.name())) {
						PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), clazz);
						propertyDescriptor.getWriteMethod().invoke(computableTask, inputs.get(inputName));
					}
				}
			}
		}
	}


	/** {@inheritDoc} */
	@Override
	public synchronized String computationList() {
		HashMap<String, String> result = new HashMap<String, String>();
		result.putAll(operationMapper);
		return ResultSerializer.get().serialize(result);
	}
	
}
