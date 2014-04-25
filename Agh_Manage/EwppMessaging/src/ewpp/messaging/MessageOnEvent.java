package ewpp.messaging;

import javax.jms.MapMessage;
import javax.mail.internet.MimeMessage;

/**
 * Interfejs generowania wiadomosci.
 * @author malczyk.pawel@gmail.com
 *
 */
public interface MessageOnEvent {
	
	/** 
	 * Uzupelnia wiadomosc email o tresc i naglowek. 
	 * @param mimeMessage tworzona wiadomosc
	 * @param message wiadomosc JMS z potrzebnymi danymi
	 * @return uzupelniona wiadomosc
	 */
	MimeMessage populateMessage(MimeMessage mimeMessage, MapMessage message);
	
}
