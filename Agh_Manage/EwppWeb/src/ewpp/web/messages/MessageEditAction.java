package ewpp.web.messages;

import ewpp.auth.entity.User;
import ewpp.business.workers.UsersWorker;
import ewpp.messaging.entity.Message;
import ewpp.messaging.workers.MessageWorker;
import ewpp.web.AbstractEwppAction;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * Akcja tworzneia wiadomosci.
 * @author malczyk.pawel@gmail.com
 *
 */
public class MessageEditAction extends AbstractEwppAction {

	/** Logger. */
	private static final Logger LOG = LoggerFactory.getLogger(MessageEditAction.class);

	/** Serial. */
	private static final long serialVersionUID = 1405964822020214483L;

	/** operacja. */
	private static final String SEND = "send";

	/** Rezeutat zwracany gdy pomyslnie wyslano wiadomosc. */
	private static final String SENT = "sent";

	/** List wszystki uzytkownikow. */
	private List<User> availableUsers;

	/** Tworzona wiadomosc. */
	private Message message;
	
	/** Identyfikatory odbiorc�w. */
	private int [] recipientsId;

	/** Identyfikator wiadomosci usuwanej/edytowanej. */
	private int messageId;
	
	/** Tryb. */
	private String mode;

	/** Konstruktor. */
	public MessageEditAction() { };


	/** {@inheritDoc} */
	@Override
	public String execute() throws Exception {
		String result = super.execute();
		if (getOperation().equals(SEND)) {
			result = doSend();
		}
		return result;
	}

	/** {@inheritDoc} */
	@Override
	protected String doDelete() {
		LOG.debug("Deleting");
		locate(MessageWorker.class).removeMessage(messageId, getLoggedUser(), mode);
		return "deleted";
	}

	/**
	 * Wysyla wiadomosc.
	 * @return widok.
	 */
	private String doSend() {
		LOG.debug("Sending message");
		LOG.debug("Odbiorcow : {}", recipientsId.length);
		if(isUserLogged()) {
			LOG.debug(message.toString());
			MessageWorker messageWorker = locate(MessageWorker.class);
			for(int recipientId : recipientsId) {
				messageWorker.sendMessage(message, getUser(recipientId), getLoggedUser());
			}
		} else {
			return NOT_LOGGED;
		}
		return SENT;
	}
	
	/**
	 * Zwraca uzytkownika.
	 * @param userId idetnyfikator uzytkownika
	 * @return uzytkownik
	 */
	private User getUser(int userId) {
		return locate(UsersWorker.class).retrieveUser(userId);
	}


	/** {@inheritDoc}*/
	@Override
	public String getDefaultOpertation() {
		return EDIT;
	}

	/** {@inheritDoc} */
	@Override
	protected String doEdit() {
		availableUsers = locate(UsersWorker.class).retrieveUsers();
		return EDIT;
	}

	/**
	 * Getter.
	 * @return the availableUsers
	 */
	public List<User> getAvailableUsers() {
		return availableUsers;
	}

	/**
	 * Setter.
	 * @param availableUsers the availableUsers to set
	 */
	public void setAvailableUsers(List<User> availableUsers) {
		this.availableUsers = availableUsers;
	}

	/**
	 * @return the message
	 */
	public Message getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(Message message) {
		this.message = message;
	}


	/**
	 * @return the messageId
	 */
	public int getMessageId() {
		return messageId;
	}

	/**
	 * @param messageId the messageId to set
	 */
	public void setMessageId(int messageId) {
		this.messageId = messageId;
	}


	/**
	 * Getter.
	 * @return the recipientsId
	 */
	public final int [] getRecipientsId() {
		return recipientsId;
	}


	/**
	 * Setter.
	 * @param recipientsId the recipientsId to set
	 */
	public final void setRecipientsId(int [] recipientsId) {
		this.recipientsId = recipientsId;
	}


	/**
	 * Getter.
	 * @return the mode
	 */
	public final String getMode() {
		return mode;
	}


	/**
	 * Setter.
	 * @param mode the mode to set
	 */
	public final void setMode(String mode) {
		this.mode = mode;
	}

}
