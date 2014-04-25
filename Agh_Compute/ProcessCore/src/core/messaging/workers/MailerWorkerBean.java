package core.messaging.workers;

import java.util.Date;

import javax.annotation.Resource;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;

import org.joda.time.Chronology;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.joda.time.format.PeriodFormat;
import org.joda.time.format.PeriodFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.messaging.workers.MailerWorker.MailerWorkerLocal;
import core.messaging.workers.MailerWorker.MailerWorkerRemote;
import core.workers.AbstractWorkerBean;

/**
 * Worker implementation.
 * @author malczyk.pawel@gmail.com
 *
 */
@Stateless
@Remote(MailerWorkerRemote.class)
@Local(MailerWorkerLocal.class)
public class MailerWorkerBean extends AbstractWorkerBean implements MailerWorker {
		

	/**
	 * Constant.
	 */
	private static final String EMAIL_PARAM = "email";


	/**
	 * Logger. 
	 */
	private static final Logger LOG = LoggerFactory.getLogger(MailerWorkerBean.class);
	
	/**
	 * JMS connection factory.
	 */
	@Resource(mappedName="jms/ConnectionFactory")
	private ConnectionFactory connectionFactory;
	
	/**
	 * Queue.
	 */
	@Resource(mappedName="jms/Mailer")
	private Queue queue;
	
	/**
	 * Constructor.. 
	 */
	public MailerWorkerBean() { 
		super();
	}

	/** {@inheritDoc} */
	@SuppressWarnings("deprecation")
	@Override
	public void sendComputationCompleted(long computationResultId, String email, Date startDate, Date endDate) {
		try {
			
			Period period = new Period(endDate.getTime()-startDate.getTime(), PeriodType.standard(), Chronology.getGregorian());
			PeriodFormatter formatter = PeriodFormat.getDefault();
			String periodStr = formatter.print(period);
			
			LOG.info("Sending computation completed email");
			Connection connection = connectionFactory.createConnection();
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			MessageProducer producer = session.createProducer(queue);
			MapMessage message = session.createMapMessage();
			message.setString("resultId", String.valueOf(computationResultId));
			message.setString("periodStr", periodStr);
			message.setString(EMAIL_PARAM, email);
			message.setString(MESSAGE_TYPE, COMPUTATION_COMPLETED);
			producer.send(message);
		} catch (JMSException e) {
			LOG.error(e.getMessage());
		}
	}

	/** {@inheritDoc} */
	@Override
	public void sendCompleteRegistration(long registrationId, String email) {
		Connection connection = null;
		try {
			LOG.info("Sending registration email");
			connection = connectionFactory.createConnection();
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			MessageProducer producer = session.createProducer(queue);
			MapMessage message = session.createMapMessage();
			message.setString("registrationId", String.valueOf(registrationId));
			message.setString(EMAIL_PARAM, email);
			message.setString(MESSAGE_TYPE, COMPLETE_REGISTRATION);
			producer.send(message);
			session.close();
		} catch (JMSException e) {
			LOG.error(e.getMessage());
		} finally {
			if(connection!=null) {
				try {
					connection.close();
				} catch (JMSException e) {
					LOG.error(e.getMessage());
				}
			}
		}
	}

	@Override
	public void sendComputationFailed(String email, String messageContent) {
		Connection connection = null;
		try {
			LOG.info("Sending computation failed email");
			connection = connectionFactory.createConnection();
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			MessageProducer producer = session.createProducer(queue);
			MapMessage message = session.createMapMessage();
			message.setString(EMAIL_PARAM, email);
			message.setString(MESSAGE_TYPE, COMPUTATION_FAILED);
			message.setString("messageContent", messageContent);
			producer.send(message);
			session.close();
		} catch (JMSException e) {
			LOG.error(e.getMessage());
		} finally {
			if(connection!=null) {
				try {
					connection.close();
				} catch (JMSException e) {
					LOG.error(e.getMessage());
				}
			}
		}
		
	}


}
