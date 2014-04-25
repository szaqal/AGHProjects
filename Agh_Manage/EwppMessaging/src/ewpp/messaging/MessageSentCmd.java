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
 * Wyslano wiadomosc.
 * @author malczyk.pawel@gmail.com
 *
 */
public class MessageSentCmd implements MessageOnEvent {
	
	/**
	 * Logger. 
	 */
	private static final Logger LOG = LoggerFactory.getLogger(MessageSentCmd.class);
	
	/**
	 * Konstruktor. 
	 */
	public MessageSentCmd() { }

	/** {@inheritDoc} */
	@Override
	public MimeMessage populateMessage(MimeMessage mimeMessage, MapMessage message) {
		try {
			LOG.debug("Sending email message to {}", message.getString(MessageFields.RECIPIENT_EMAIL));
			String sender = message.getString(MessageFields.SENDER_LABEL);
			mimeMessage.setSubject("Otrzymano wiadomosc");
			mimeMessage.setText("Otrzymano nowa wiadomosc od " + sender); 
			mimeMessage.addRecipient(javax.mail.Message.RecipientType.TO, 
					new InternetAddress(message.getString(MessageFields.RECIPIENT_EMAIL), message.getString(MessageFields.RECIPIENT_EMAIL)));
		} catch(MessagingException e) {
			LOG.error(e.getMessage());
		} catch (JMSException e) {
			LOG.error(e.getMessage());
		} catch (UnsupportedEncodingException e) {
			LOG.error(e.getMessage());
		}
		return mimeMessage;
	}

}
