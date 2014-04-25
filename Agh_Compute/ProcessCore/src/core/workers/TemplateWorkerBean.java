package core.workers;

import java.io.StringWriter;
import java.util.Map;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.utils.StringUtils;
import core.workers.TemplateWorker.TemplateWorkerLocal;
import core.workers.TemplateWorker.TemplateWorkerRemote;

/**
 * Worker implementation.
 * 
 * @author malczyk.pawel@gmail.com
 *
 */
@Stateless
@Local(TemplateWorkerLocal.class)
@Remote(TemplateWorkerRemote.class)
public class TemplateWorkerBean extends AbstractWorkerBean implements TemplateWorker {
	
	/**
	 * Logger.
	 */
	private static final Logger LOG = LoggerFactory.getLogger(TemplateWorkerBean.class);
	
	/**
	 * Constructor.
	 */
	public TemplateWorkerBean() {
		super();
	}

	/** {@inheritDoc} */
	@Override
	public String create(Map<String, String> params, String templateName) {
		VelocityEngine velocityEngine = new VelocityEngine();
		velocityEngine.setProperty("resource.loader", "class");
		velocityEngine.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
		String result = StringUtils.EMPTY;
		try {
			velocityEngine.init();
			Template template = velocityEngine.getTemplate(templateName);
			VelocityContext context = prepareData(params);
			StringWriter stringWriter = new StringWriter();			
			template.merge(context, stringWriter);
			result = stringWriter.toString();
		} catch (ResourceNotFoundException e) {
			LOG.warn(e.getMessage(), e);
		} catch (ParseErrorException e) {
			LOG.warn(e.getMessage(), e);
		} catch (Exception e) {
			LOG.warn(e.getMessage(), e);
		}
		return result;
	}
	
	/**
	 * Creates Velocity context with appropriate data.
	 * @param params context parameters
	 * @return prepared velocity context
	 */
	private VelocityContext prepareData(Map<String, String> params) {
		VelocityContext context = new VelocityContext();
		for(String key : params.keySet()) {
			context.put(key, params.get(key));
		}
		return context;
	}

}
