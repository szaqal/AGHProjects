package computation.worker;

import java.util.Map;

import javax.annotation.Resource;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateful;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import computation.utils.ParameterSerializer;
import computation.worker.ComputationInvokerWorker.ComputationInvokerWorkerLocal;
import computation.worker.ComputationInvokerWorker.ComputationInvokerWorkerRemote;

import core.workers.AbstractWorkerBean;


/**
 * Worker implementation.
 * @author malczyk.pawel@gmail.com
 *
 */
@Stateful
@Remote(ComputationInvokerWorkerRemote.class)
@Local(ComputationInvokerWorkerLocal.class)
public class ComputationInvokerWorkerBean extends AbstractWorkerBean implements ComputationInvokerWorker {
	
	/**
	 * Logger.
	 */
	private static final Logger LOG = LoggerFactory.getLogger(ComputationInvokerWorkerBean.class);
	
	/**
	 * Map of computation parameters.
	 */
	private Map<String, Number> computationParams;
	
	/**
	 * Map of computation parameters.
	 */
	private Map<String, String> computationSettings;
	
	/**
	 * JMS connection factory.
	 */
	@Resource(mappedName="jms/ConnectionFactory")
	private ConnectionFactory connectionFactory;
	
	
	/**
	 * Queue.
	 */
	@Resource(mappedName="jms/ComputationRunner")
	private Queue queue;
	
	/**
	 * Constructor.
	 */
	public ComputationInvokerWorkerBean() {
		super();
	}
	
	

	/** {@inheritDoc} */
	@Override
	public <T> void doComputation(Class<T> clazz, String computationId, String recipientEmail, String ownerId) {
		try {
			Connection connection = connectionFactory.createConnection();
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			MessageProducer producer = session.createProducer(queue);
			MapMessage message = session.createMapMessage();
			//FIXME: use constants.
			message.setStringProperty("Parameters", ParameterSerializer.serialize(computationParams));
			message.setStringProperty("Settings", ParameterSerializer.serializeString(computationSettings));
			message.setStringProperty("computationId", computationId);
			String name = clazz.getName();
			message.setStringProperty("clazzName", name);
			message.setStringProperty("recipientEmail", recipientEmail);
			message.setStringProperty("userId", ownerId);
			producer.send(message);
			LOG.debug("JMS message sent");
		} catch (JMSException e) {
			LOG.error(e.getMessage());
		}

		
	}

	/** {@inheritDoc} */
	@Override
	public void setup(Map<String, Number> initialValues) {
		this.computationParams = initialValues;
	}

	/** {@inheritDoc} */
	@Override
	public void setupSettings(Map<String, String> initialSettings) {
		this.computationSettings=initialSettings;
	}

}
