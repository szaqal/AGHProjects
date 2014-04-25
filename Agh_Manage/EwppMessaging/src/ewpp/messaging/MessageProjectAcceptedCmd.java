package ewpp.messaging;

import java.io.UnsupportedEncodingException;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Powiadomienie o zaakceptowaniu projektu.
 * @author malczyk.pawel@gmail.com
 *
 */
public class MessageProjectAcceptedCmd extends AbstractMessageCmd implements MessageOnEvent {
	
	/**
	 * Logger.
	 */
	private static final Logger LOG = LoggerFactory.getLogger(MessageProjectAcceptedCmd.class);

	/**
	 * Konstruktor.
	 */
	public MessageProjectAcceptedCmd() { }
	
	/** {@inheritDoc} */
	@Override
	public MimeMessage populateMessage(MimeMessage mimeMessage, MapMessage message) {
		try {
			mimeMessage.setSubject("Projekt zaakceptowany");
			mimeMessage.setText(message.getString(MessageFields.PROJECT_LEADER_LABEL)+ " zaakceptowal projekt.");
			mimeMessage.addRecipient(javax.mail.Message.RecipientType.TO, createRecipientAddress(message) );
		} catch (MessagingException e) {
			LOG.error(e.getMessage());
		} catch (JMSException e) {
			LOG.error(e.getMessage());
		} catch (UnsupportedEncodingException e) {
			LOG.error(e.getMessage());
		}
		return mimeMessage;
	}

}
