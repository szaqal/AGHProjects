package ewpp.messaging;

import java.io.UnsupportedEncodingException;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Powiadomienie o zuploadowniu pliku do etapu projektu.
 * @author malczyk.pawel@gmail.com
 *
 */
public class MessageStageItemUploadedCmd implements MessageOnEvent {
	
	/**
	 * Logger.
	 */
	private static final Logger LOG = LoggerFactory.getLogger(MessageStageCloseCmd.class);
	
	/**
	 * Konstruktor.
	 */
	public MessageStageItemUploadedCmd() { }

	/** {@inheritDoc} */
	@Override
	public MimeMessage populateMessage(MimeMessage mimeMessage, MapMessage message) {
		try {
			mimeMessage.setSubject("Etap projektu zakoczony");
			mimeMessage.setText(message.getString(MessageFields.USER_LABEL)+ " dodal plik do etapu projektu.");
			mimeMessage.addRecipient(javax.mail.Message.RecipientType.TO, 
					new InternetAddress(message.getString(MessageFields.RECIPIENT_EMAIL), message.getString(MessageFields.RECIPIENT_EMAIL)));
		} catch (MessagingException e) {
			LOG.error(e.getMessage());
		} catch (UnsupportedEncodingException e) {
			LOG.error(e.getMessage());
		} catch (JMSException e) {
			LOG.error(e.getMessage());
		}
		return mimeMessage;
	}

}
