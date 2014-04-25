package ewpp.messaging.workers;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ewpp.messaging.MessageFields;
import ewpp.workers.ConfigurationWorker;
import ewpp.workers.ConfigurationWorker.ConfigurationWorkerLocal;

/**
 * Komponenet sterowany komunikatami.
 * @author malczyk.pawel@gmail.com
 *
 */
@MessageDriven(activationConfig = {
		@ActivationConfigProperty(propertyName="destinationType", propertyValue="javax.jms.Queue") },
		mappedName="jms/Mailer"
)
public class MailerMessageBean implements MessageListener {
	
	/**
	 * Logger.
	 */
	private static final Logger LOG = LoggerFactory.getLogger(MailerMessageBean.class);
	
	
	/**
	 * Worker konfiguracji.
	 */
	@EJB
	private ConfigurationWorkerLocal configWorker;
	
	/**
	 * Konstruktor.
	 */
	public MailerMessageBean() { }

	/** {@inheritDoc} */
	@Override
	public void onMessage(Message message) {
		LOG.debug(">>> Sending mail... <<<");
		Session session = createSession();
		try {
			Transport.send(createMessage(session, (MapMessage) message ));
		} catch (MessagingException e) {
			LOG.error(e.getMessage());
		}
	}
	
	/**
	 * Tworzy wiadomosc email.
	 * @param session sesja
	 * @param mapMessage nadawca
	 * @return przygotowana wiadomosc email.
	 */
	private MimeMessage createMessage(Session session, MapMessage mapMessage) {
		MimeMessage message = new MimeMessage(session);
		try {
			String senderEmail = configWorker.retrieveConfigItemByKey(ConfigurationWorker.EWPP_SENDER_ADDRESS).getValue();
			message.setFrom(new InternetAddress(senderEmail, "ewpp"));
			String msgType = mapMessage.getString(MessageFields.MESSAGE_EVENT);
			MessageCommands.invokeCommand(msgType, message, mapMessage);
		} catch (UnsupportedEncodingException e) {
			LOG.error(e.getMessage());
		} catch (MessagingException e) {
			LOG.error(e.getMessage());
		} catch (JMSException e) {
			LOG.error(e.getMessage());
		}
		
		return message;
	}
	
	/**
	 * Zwraca sesje.
	 * @return sesja
	 */
	private Session createSession() {
		Properties props = new Properties();
		props.put("mail.from", "ewpp@o2.pl"); 
		props.put("mail.smtp.host", configWorker.retrieveConfigItemByKey(ConfigurationWorker.SMTP_HOST).getValue() );
		props.put("mail.smtp.user", configWorker.retrieveConfigItemByKey(ConfigurationWorker.SMTP_USER).getValue());
		props.put("mail.smtp.auth", "true");
		Session session = Session.getInstance(props, new PasswdAuthenticator());
		return session;
	}
	
	/**
	 * Autentykator.
	 * @author malczyk.pawel@gmail.com
	 *
	 */
	private class PasswdAuthenticator extends Authenticator {
		
		/**
		 * Konstruktor.
		 */
		public PasswdAuthenticator() { }
		
		/** {@inheritDoc} */
		@Override
		protected PasswordAuthentication getPasswordAuthentication() {
			String user = configWorker.retrieveConfigItemByKey(ConfigurationWorker.SMTP_USER).getValue();
			String passwd = configWorker.retrieveConfigItemByKey(ConfigurationWorker.SMTP_PASSWD).getValue();
			return new PasswordAuthentication(user, passwd);
		}
	}
	

	

}
