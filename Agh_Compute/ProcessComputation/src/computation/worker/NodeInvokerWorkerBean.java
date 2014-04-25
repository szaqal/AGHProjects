package computation.worker;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import node.http.Dispatcher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import api.computation.ComputationContext;

import com.caucho.hessian.client.HessianProxyFactory;
import computation.exceptions.ComputationRunTimeException;
import computation.model.ComputingNode;
import computation.model.PerformedComputation;
import computation.worker.NodeInvokerWorker.NodeInvokerWorkerLocal;
import computation.worker.NodeInvokerWorker.NodeInvokerWorkerRemote;

import core.workers.AbstractWorkerBean;

/**
 * Worker implementation.
 * 
 * @author malczyk.pawel@gmail.com
 */
@Stateless
@Local(NodeInvokerWorkerLocal.class)
@Remote(NodeInvokerWorkerRemote.class)
public class NodeInvokerWorkerBean extends AbstractWorkerBean implements NodeInvokerWorker {

	/**
	 * Constant. 
	 */
	private static final int BUFFER_SIZE = 1024;
	
	/**
	 * Logger.
	 */
	private static final Logger LOG = LoggerFactory.getLogger(NodeInvokerWorker.class);

	/**
	 * Constructor.
	 */
	public NodeInvokerWorkerBean() {
		super();
	}

	/**
	 * Compresses data with using GZIP.
	 * @param input uncompressed input data.
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
	@SuppressWarnings("unchecked")
	@Override
	public HashMap<String, Serializable> invoke(ComputingNode nodeMapping, HashMap<String, Serializable> inputs,
			HashMap<String, Serializable> outputs, String taskName, PerformedComputation performedComputation,
			ComputationContext computationContext) throws ComputationRunTimeException {

		HashMap<String, Serializable> result = null;
		try {
			String serverUrl = "http://" + nodeMapping.getInetAddr() + ":" + nodeMapping.getOperationPort() + "/node/dispatch";
			LOG.debug("Runnig {}", serverUrl);
			
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
			objectOutputStream.writeObject(inputs);
			byte[] serializedInputs = byteArrayOutputStream.toByteArray();
			int unCompressed = serializedInputs.length;
			serializedInputs = gzipData(serializedInputs);
			LOG.debug("Serialized inputs size [{}] compressed [{}]", unCompressed, serializedInputs.length);

			byteArrayOutputStream = new ByteArrayOutputStream();
			objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
			objectOutputStream.writeObject(outputs);
			byte[] serializedOutputs = byteArrayOutputStream.toByteArray();
			int uncompressed = serializedOutputs.length;
			serializedOutputs = gzipData(serializedOutputs);
			LOG.debug("Serialized outputs size [{}] compressed [{}]", uncompressed, serializedOutputs.length);

			HessianProxyFactory factory = new HessianProxyFactory();
			Dispatcher basic = (Dispatcher) factory.create(Dispatcher.class, serverUrl);
			byte[] serializedResult = basic.compute(taskName, serializedInputs, serializedOutputs, computationContext);
			ObjectInputStream in = new ObjectInputStream(new GZIPInputStream(new ByteArrayInputStream(serializedResult)));
			result = (HashMap<String, Serializable>) in.readObject();

		} catch (IOException e) {
			LOG.error(e.getMessage());
			throw new ComputationRunTimeException(e.getMessage());
		} catch (ClassNotFoundException e) {
			LOG.error(e.getMessage());
			throw new ComputationRunTimeException(e.getMessage());
		}

		return result;
	}

}
