package ewpp.web.messages;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ewpp.messaging.entity.Message;
import ewpp.messaging.workers.MessageWorker;
import ewpp.web.AbstractEwppAction;

/**
 * Akcja podgladu wiadomosci.
 * @author malczyk.pawel@gmail.com
 *
 */
public class MessageViewAction extends AbstractEwppAction {

	/** Serial. */
	private static final long serialVersionUID = 4866596718572548922L;

	/** Logger. */
	private static final Logger LOG = LoggerFactory.getLogger(MessageViewAction.class);

	/** Identyfikator ogladanej wiadomosci. */
	private int messageId;

	/** Podgladana wiadomosc. */
	private Message message;

	/** Konstruktor. */
	public MessageViewAction() { };

	/** {@inheritDoc} */
	@Override
	protected String doView() {
		message = locate(MessageWorker.class).retrieveMessage(messageId);
		
		if (getLoggedUserId().equals(message.getRecipient().getUniqueId())) {
			locate(MessageWorker.class).markMessageAsReaded(messageId);
		}
		LOG.debug("Found message {}", message.toString());
		return VIEW;
	}

	/**
	 * Getter.
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
	 * @return the message.
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
}
