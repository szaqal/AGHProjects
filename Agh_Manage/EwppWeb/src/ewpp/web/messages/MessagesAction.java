package ewpp.web.messages;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ewpp.messaging.entity.Message;
import ewpp.messaging.workers.MessageWorker;
import ewpp.utils.StringUtils;
import ewpp.web.AbstractEwppAction;

/**
 * Akcja wiadomosci.
 * @author malczyk.pawel@gmail.com
 *
 */
public class MessagesAction extends AbstractEwppAction {

	/** Serial. */
	private static final long serialVersionUID = 4183062369992303803L;

	/** Logger. */
	private static final Logger LOG = LoggerFactory.getLogger(MessagesAction.class);

	/** Wiadomosci wychodzace. */
	private static final String OUTGOING = "outgoing";

	/** Wiadomosci przychodzace. */
	private static final String INCOMING = "incoming";

	/** Incoming/Outgoing. */
	private String mode;

	/** Lista wiadomosci. */
	private List<Message> messages;

	/** Konstruktor. */
	public MessagesAction() { };



	/** {@inheritDoc} */
	@Override
	public String getDefaultOpertation() {
		return LIST;
	}

	/** {@inheritDoc} */
	@Override
	protected String doList() {

		if(StringUtils.isEmpty(mode)) {
			mode = INCOMING;
		}

		if (INCOMING.equals(mode)) {
			messages = locate(MessageWorker.class).retrieveIncomingMessages(getLoggedUser());
			LOG.debug("retrieved {} incoming messages", messages.size());
		} else if (OUTGOING.equals(mode)) {
			messages = locate(MessageWorker.class).retrieveOutgoingMessages(getLoggedUser());
			LOG.debug("retrieved {} outgoing messages", messages.size());
		}

		return LIST;
	}


	/**
	 * @return the mode
	 */
	public String getMode() {
		return mode;
	}

	/**
	 * @param mode the mode to set
	 */
	public void setMode(String mode) {
		this.mode = mode;
	}

	/**
	 * @return the messages
	 */
	public List<Message> getMessages() {
		return messages;
	}

	/**
	 * @param messages the messages to set
	 */
	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}

}
