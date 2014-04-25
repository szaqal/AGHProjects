package ewpp.messaging.workers;

import java.util.HashMap;
import java.util.Map;

import javax.jms.MapMessage;
import javax.mail.internet.MimeMessage;

import ewpp.messaging.MessageEvent;
import ewpp.messaging.MessageOnEvent;
import ewpp.messaging.MessageProjCreateCmd;
import ewpp.messaging.MessageProjectAcceptedCmd;
import ewpp.messaging.MessageProjectCloseCmd;
import ewpp.messaging.MessageSentCmd;
import ewpp.messaging.MessageStageCloseCmd;
import ewpp.messaging.MessageStageItemUploadedCmd;

/**
 * Przechowuje odpowiednie commandy decydujacy o rodzaju
 * wyslanego maila w zaleznosci od zaistnialego zdarzenia.
 * @author malczyk.pawel@gmail.com
 *
 */
public final class MessageCommands {
	
	/**
	 * Zarejestrowane eventy.
	 */
	private static final Map<String, MessageOnEvent> REGISTERED_EVENTS;
	
	/**
	 * Konstruktor.
	 */
	private MessageCommands() { }
	
	/**
	 * Uzupelnia wiadomosc zaleznie od rodzaju zdarzenia.
	 * @param commandType typ zdarzenia
	 * @param mimeMessage uzupelniana wiadomosc
	 * @param message wiadomnosc JMS z ktorej czerpane sa dane.
	 */
	public static void invokeCommand(String commandType, MimeMessage mimeMessage, MapMessage message) {
		REGISTERED_EVENTS.get(commandType).populateMessage(mimeMessage, message);
	}
	

	/**
	 * Blok statyczny
	 */
	static {
		REGISTERED_EVENTS = new HashMap<String, MessageOnEvent>();
		REGISTERED_EVENTS.put(MessageEvent.MESSAGE_SENT.getEvent(), new MessageSentCmd());
		REGISTERED_EVENTS.put(MessageEvent.PROJECT_CREATED.getEvent(), new MessageProjCreateCmd());
		REGISTERED_EVENTS.put(MessageEvent.PROJECT_ACCEPTED.getEvent(), new MessageProjectAcceptedCmd());
		REGISTERED_EVENTS.put(MessageEvent.PROJECT_CLOSED.getEvent(), new MessageProjectCloseCmd());
		REGISTERED_EVENTS.put(MessageEvent.PROJECT_STAGE_CLOSED.getEvent(), new MessageStageCloseCmd());
		REGISTERED_EVENTS.put(MessageEvent.STAGE_FILE_UPLOADED.getEvent(), new MessageStageItemUploadedCmd());
	}
	

}
