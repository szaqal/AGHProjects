package computation.worker;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.Map;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import computation.exceptions.ComputationRunTimeException;
import computation.utils.ParameterSerializer;
import computation.worker.ComputationPerformerWorker.ComputationPerformerWorkerLocal;

import core.messaging.workers.MailerWorker.MailerWorkerLocal;



/**
 * Message bean that initiates computation. 
 * @author  malczyk.pawel@gmail.com
 */
@MessageDriven(activationConfig = {
		@ActivationConfigProperty(propertyName="destinationType", propertyValue="javax.jms.Queue") },
		mappedName="jms/ComputationRunner"
)
public class ComputationRunnerMessageBean implements MessageListener {
	
	/**
	 * Logger.
	 */
	private static final Logger LOG = LoggerFactory.getLogger(ComputationRunnerMessageBean.class);

	/**
	 * Computation.
	 * @uml.property  name="computationPerformerWorker"
	 * @uml.associationEnd  
	 */
	@EJB
	private ComputationPerformerWorkerLocal computationPerformerWorker;
	

	
	/**
	 * MailerWorker.
	 * @uml.property  name="mailerWorkerLocal"
	 * @uml.associationEnd  
	 */
	@EJB
	private MailerWorkerLocal mailerWorkerLocal;
	
	/**
	 * Constructor.
	 */
	public ComputationRunnerMessageBean() {
		super();
	}

	/** {@inheritDoc} */
	@Override
	public void onMessage(Message message) {
		long time = new Date().getTime();
		LOG.debug("Recieved message on [jms/ComputationRunner] {}", time);
		String email = null;
		try {
			String recipientEmail = message.getStringProperty("recipientEmail");
			String parameters  = message.getStringProperty("Parameters");
			String computationId = message.getStringProperty("computationId");				
			String userId = message.getStringProperty("userId");
			email = recipientEmail;
			Map<String, Number> deserialized = ParameterSerializer.deserialize(parameters);
			String settings  = message.getStringProperty("Settings");
			Map<String, String> initSettings = ParameterSerializer.deserializeString(settings);
			computationPerformerWorker.setup(deserialized, initSettings);
			
			Date start=new Date();
			long resultId = computationPerformerWorker.doComputation(Integer.valueOf(computationId), Integer.valueOf(userId));
			Date end=new Date();
			
			
			mailerWorkerLocal.sendComputationCompleted(resultId, recipientEmail, start, end);
		} catch (JMSException e) {
			LOG.error("JMS exception {}", e.getMessage());
		} catch (ComputationRunTimeException e) {
			StringWriter stringWriter = new StringWriter();
			e.printStackTrace(new PrintWriter(stringWriter));
			String messageContent = stringWriter.getBuffer().toString();
			mailerWorkerLocal.sendComputationFailed(email, messageContent);
		}
		LOG.debug("Message processed {}", time);
	}
	


}
