package core.messaging.workers;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
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
import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.utils.StringUtils;
import core.workers.ConfigurationWorker.ConfigurationWorkerLocal;
import core.workers.TemplateWorker.TemplateWorkerLocal;

/**
 * Message driven bean.
 * @author  malczyk.pawel@gmail.com
 */
@MessageDriven(activationConfig = {
		@ActivationConfigProperty(propertyName="destinationType", propertyValue="javax.jms.Queue") },
		mappedName="jms/Mailer"
)
public class MailerMessageBean implements MessageListener {
	
	/**
	 * Constant property.
	 */
	private static final String PERIOD_STR = "periodStr";

	/**
	 * Constant.
	 */
	private static final String EMAIL = "email";

	/**
	 * Configuration item key.
	 */
	private static final String KEY_SMTP_USER = "smtp.user";

	/**
	 * Configuration item key.
	 */
	private static final String KEY_MAIL_FROM = "application.mail.from";

	/**
	 * Configuration item key.
	 */
	private static final String KEY_APP_URL = "application.url";
	
	/**
	 * Logger.
	 */
	private static final Logger LOG = LoggerFactory.getLogger(MailerMessageBean.class);
	
	/**
	 * Configuration worker.
	 * @uml.property  name="configurationWorker"
	 * @uml.associationEnd  
	 */
	@EJB
	private ConfigurationWorkerLocal configurationWorker;
	
	/**
	 * Template worker.
	 * @uml.property  name="templateWorker"
	 * @uml.associationEnd  
	 */
	@EJB
	private TemplateWorkerLocal templateWorker;
	
	
	
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
		} catch (JMSException e) {
			LOG.error(e.getMessage());
		}
	}
	
	
	
	/**
	 * Tworzy wiadomosc email.
	 * @param session sesja
	 * @param mapMessage nadawca
	 * @return przygotowana wiadomosc email.
	 * @throws JMSException  exceptions
	 */
	private MimeMessage createMessage(Session session, MapMessage mapMessage) throws JMSException {
		MimeMessage message = new MimeMessage(session);
		try {
			
			if(MailerWorker.COMPUTATION_COMPLETED.equals(mapMessage.getString(MailerWorker.MESSAGE_TYPE))){
				populateComputationCompletedMessage(mapMessage, message);
			} else if (MailerWorker.COMPLETE_REGISTRATION.equals(mapMessage.getString(MailerWorker.MESSAGE_TYPE))) {
				populateCompleteREgistrationMessage(mapMessage, message);
			} else if(MailerWorker.COMPUTATION_FAILED.equals(mapMessage.getString(MailerWorker.MESSAGE_TYPE))) {
				populateComputationFailedMessage(mapMessage, message);
			}
		} catch (UnsupportedEncodingException e) {
			LOG.error(e.getMessage());
		} catch (MessagingException e) {
			LOG.error(e.getMessage());
		}
		
		return message;
	}
	
	
	/**
	 * Populates message that registration should be completed.
	 * @param mapMessage jms message
	 * @param message message being populated
	 * @throws MessagingException exception
	 * @throws UnsupportedEncodingException exception
	 * @throws JMSException exception
	 */
	private void populateCompleteREgistrationMessage(MapMessage mapMessage, MimeMessage message) throws MessagingException, UnsupportedEncodingException, 
				JMSException {
		
		String senderEmail = configurationWorker.getConfigByKey(KEY_MAIL_FROM).getConfigValue();
		message.setFrom(new InternetAddress(senderEmail, senderEmail));
		message.setSubject("Potwierdzenie rejestracji");			
		
		String resultUrl = configurationWorker.getConfigByKey(KEY_APP_URL).getConfigValue();
		resultUrl += "/CompleteRegistration?registrationId=" + mapMessage.getString("registrationId"); 
		Map<String, String> params = new HashMap<String, String>();
		params.put("completeRegistrationUrl", resultUrl);
		String msgContent = templateWorker.create(params, "completeRegistration.vm");	
		message.setContent(msgContent, StringUtils.TEXT_HTML);
		message.addRecipient(RecipientType.TO, new InternetAddress(mapMessage.getString(EMAIL)));
	}
	

	/**
	 * Populates message that computation completed.
	 * @param mapMessage jms message
	 * @param message message being populated
	 * @throws MessagingException exception
	 * @throws UnsupportedEncodingException exception
	 * @throws JMSException exception
	 */
	private void populateComputationCompletedMessage(MapMessage mapMessage, MimeMessage message) throws MessagingException, UnsupportedEncodingException, 
				JMSException {
		
		String senderEmail = configurationWorker.getConfigByKey(KEY_MAIL_FROM).getConfigValue();
		message.setFrom(new InternetAddress(senderEmail, senderEmail));
		message.setSubject("Wynik obliczen");			
		String periodStr = mapMessage.getString(PERIOD_STR);
		String resultUrl = configurationWorker.getConfigByKey(KEY_APP_URL).getConfigValue();
		resultUrl += "/ComputationResult?resultId=" + mapMessage.getString("resultId"); 
		Map<String, String> params = new HashMap<String, String>();
		params.put("computationResultUrl", resultUrl);
		params.put(PERIOD_STR, periodStr);
		String msgContent = templateWorker.create(params, "email.vm");
		message.setContent(msgContent, StringUtils.TEXT_HTML);
		message.addRecipient(RecipientType.TO, new InternetAddress(mapMessage.getString(EMAIL)));
	}
	
	/**
	 * Populates message that computation completed.
	 * @param mapMessage jms message
	 * @param message message being populated
	 * @throws MessagingException exception
	 * @throws UnsupportedEncodingException exception
	 * @throws JMSException exception
	 */
	private void populateComputationFailedMessage(MapMessage mapMessage, MimeMessage message) throws MessagingException, UnsupportedEncodingException, 
				JMSException {
		
		String senderEmail = configurationWorker.getConfigByKey(KEY_MAIL_FROM).getConfigValue();
		message.setFrom(new InternetAddress(senderEmail, senderEmail));
		message.setSubject("Blad obliczenia");			
		String messageContent = mapMessage.getString("messageContent");
		String msgContent = "<b>Blad obliczenia</b><br/><hr/>";
		msgContent += "<pre>"+messageContent+"</pre>";
		message.setContent(msgContent, StringUtils.TEXT_HTML);
		message.addRecipient(RecipientType.TO, new InternetAddress(mapMessage.getString(EMAIL)));
	}
	
	/**
	 * Zwraca sesje.
	 * @return sesja
	 */
	private Session createSession() {
		Properties props = new Properties();
		try {
			props.put("mail.from", configurationWorker.getConfigByKey(KEY_MAIL_FROM).getConfigValue()); 
			props.put("mail.smtp.host", configurationWorker.getConfigByKey("smtp.host").getConfigValue());
			props.put("mail.smtp.user", configurationWorker.getConfigByKey(KEY_SMTP_USER).getConfigValue());
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.port", configurationWorker.getConfigByKey("smtp.port").getConfigValue());
		} catch (NoResultException e) {
			LOG.error("Email is not configured properly (Configurations not found)");
		}
		Session session = Session.getInstance(props, new PasswdAuthenticator());
		return session;
	}
	
	/**
	 * Password authenticator.
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
			String user = configurationWorker.getConfigByKey(KEY_SMTP_USER).getConfigValue();
			String passwd = configurationWorker.getConfigByKey("smtp.password").getConfigValue();
			return new PasswordAuthentication(user, passwd);
		}
	}

	
	

	

}
