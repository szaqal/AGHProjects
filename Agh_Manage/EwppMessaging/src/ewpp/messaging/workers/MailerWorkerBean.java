package ewpp.messaging.workers;

import java.util.List;

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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ewpp.messaging.MessageEvent;
import ewpp.messaging.MessageFields;
import ewpp.messaging.workers.MailerWorker.MailerWorkerLocal;
import ewpp.messaging.workers.MailerWorker.MailerWorkerRemote;
import ewpp.workers.AbstractWorkerBean;


/**
 * Implementacja workera maili.
 * @author malczyk.pawel@gmail.com
 *
 */
@Stateless
@Remote(MailerWorkerRemote.class)
@Local(MailerWorkerLocal.class)
public class MailerWorkerBean extends AbstractWorkerBean implements MailerWorker {
	
	/**
	 * Logger. 
	 */
	private static final Logger LOG = LoggerFactory.getLogger(MailerWorkerBean.class);
	

	
	/**
	 * Fabryka polaczen JMS.
	 */
	@Resource(mappedName="jms/ConnectionFactory")
	private ConnectionFactory connectionFactory;
	
	/**
	 * Kolejka.
	 */
	@Resource(mappedName="jms/Mailer")
	private Queue queue;
	
	/**
	 * Konstruktor. 
	 */
	public MailerWorkerBean() { }

	/** {@inheritDoc} */
	@Override
	public void sendSentMessageNotification(String senderLabel, String recipientEmail) {
		try {
			Connection connection = connectionFactory.createConnection();
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			MessageProducer producer = session.createProducer(queue);
			MapMessage message = session.createMapMessage();
			message.setString(MessageFields.MESSAGE_EVENT, MessageEvent.MESSAGE_SENT.getEvent());
			message.setString(MessageFields.RECIPIENT_EMAIL, recipientEmail);
			message.setString(MessageFields.SENDER_LABEL, senderLabel);
			producer.send(message);
		} catch (JMSException e) {
			LOG.error(e.getMessage());
		}
	}

	/** {@inheritDoc} */
	@Override
	public void sendProjectCreatedNotification(String creatorLabel, List<String> participantsEmail, String projectTitle) {
		Connection connection;
		try {
			connection = connectionFactory.createConnection();
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			MessageProducer producer = session.createProducer(queue);
			
			for(String participantEmail : participantsEmail) {
				MapMessage message = session.createMapMessage();
				message.setString(MessageFields.MESSAGE_EVENT, MessageEvent.PROJECT_CREATED.getEvent());
				message.setString(MessageFields.PROJECT_CREATOR_LABEL, creatorLabel);
				message.setString(MessageFields.PROJECT_TITLE, projectTitle);
				message.setString(MessageFields.RECIPIENT_EMAIL, participantEmail);
				producer.send(message);
			}
		} catch (JMSException e) {
			LOG.error(e.getMessage());
		}
	
		
	}

	/** {@inheritDoc} */
	@Override
	public void sendProjectAcceptedNotification(List<String> participantsEmail, String leaderLabel) {
		Connection connection;
		try {
			connection = connectionFactory.createConnection();
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			MessageProducer producer = session.createProducer(queue);
			
			for(String participantEmail : participantsEmail) {
				MapMessage message = session.createMapMessage();
				message.setString(MessageFields.MESSAGE_EVENT, MessageEvent.PROJECT_ACCEPTED.getEvent());
				message.setString(MessageFields.RECIPIENT_EMAIL, participantEmail);
				message.setString(MessageFields.PROJECT_LEADER_LABEL, leaderLabel);
				producer.send(message);
			}
		} catch (JMSException e) {
			LOG.error(e.getMessage());
		}
		
		
	}

	/** {@inheritDoc} */
	@Override
	public void sendProjectClosedNotification(List<String> participantsEmail, String leaderLabel) {
		Connection connection;
		try {
			connection = connectionFactory.createConnection();
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			MessageProducer producer = session.createProducer(queue);
			
			for(String participantEmail : participantsEmail) {
				MapMessage message = session.createMapMessage();
				message.setString(MessageFields.MESSAGE_EVENT, MessageEvent.PROJECT_CLOSED.getEvent());
				message.setString(MessageFields.RECIPIENT_EMAIL, participantEmail);
				message.setString(MessageFields.PROJECT_LEADER_LABEL, leaderLabel);
				producer.send(message);
			}
		} catch (JMSException e) {
			LOG.error(e.getMessage());
		}
	}

	/** {@inheritDoc}*/
	@Override
	public void sendProjectStageClosedNotification(List<String> participantsEmail, String leaderLabel) {
		try {
			Connection connection = connectionFactory.createConnection();
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			MessageProducer producer = session.createProducer(queue);
			
			for(String participantEmail : participantsEmail) {
				MapMessage message = session.createMapMessage();
				message.setString(MessageFields.MESSAGE_EVENT, MessageEvent.PROJECT_STAGE_CLOSED.getEvent());
				message.setString(MessageFields.RECIPIENT_EMAIL, participantEmail);
				message.setString(MessageFields.PROJECT_LEADER_LABEL, leaderLabel);
				producer.send(message);
			}
		} catch (JMSException e) {
			LOG.error(e.getMessage());
		}
		
	}

	/** {@inheritDoc} */
	@Override
	public void sendFileUploadedNotification(String addingUserLabel, List<String> participantsEmail) {
		try {
			Connection connection = connectionFactory.createConnection();
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			MessageProducer producer = session.createProducer(queue);
			
			for(String participantEmail : participantsEmail) {
				MapMessage message = session.createMapMessage();
				message.setString(MessageFields.MESSAGE_EVENT, MessageEvent.STAGE_FILE_UPLOADED.getEvent());
				message.setString(MessageFields.RECIPIENT_EMAIL, participantEmail);
				message.setString(MessageFields.USER_LABEL, addingUserLabel);
				producer.send(message);
			}
		} catch (JMSException e) {
			LOG.error(e.getMessage());
		}
	}

}
