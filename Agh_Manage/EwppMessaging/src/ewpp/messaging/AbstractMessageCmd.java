package ewpp.messaging;

import java.io.UnsupportedEncodingException;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;


/**
 * Zawiera wspoldzelony kod dla wszystkich obiektow command.
 * @author malczyk.pawel@gmail.com
 *
 */
public abstract class AbstractMessageCmd {
	
	/**
	 * Tworzy adres email odbiorcy.
	 * @param  message wiadomosc 
	 * @return przygotowany adres
	 * @throws JMSException exception
	 * @throws AddressException  exception
	 * @throws UnsupportedEncodingException exception
	 */
	protected InternetAddress createRecipientAddress(MapMessage message) throws AddressException, JMSException, UnsupportedEncodingException {
		return new InternetAddress(message.getString(MessageFields.RECIPIENT_EMAIL), message.getString(MessageFields.RECIPIENT_EMAIL));
	}
}
