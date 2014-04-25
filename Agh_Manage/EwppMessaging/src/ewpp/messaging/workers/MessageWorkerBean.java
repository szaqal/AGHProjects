package ewpp.messaging.workers;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ewpp.auth.entity.User;
import ewpp.messaging.entity.Message;
import ewpp.messaging.entity.MessageStatus;
import ewpp.messaging.workers.MailerWorker.MailerWorkerLocal;
import ewpp.messaging.workers.MessageWorker.MessageWorkerLocal;
import ewpp.messaging.workers.MessageWorker.MessageWorkerRemote;
import ewpp.utils.PagingInfo;
import ewpp.utils.SearchParameters;
import ewpp.utils.StringUtils;
import ewpp.workers.AbstractWorkerBean;

/**
 * Implementacja workera wiadomosci.
 * @author malczyk.pawel@gmail.com
 *
 */
@Stateless
@Remote(MessageWorkerRemote.class)
@Local(MessageWorkerLocal.class)
public class MessageWorkerBean extends AbstractWorkerBean implements MessageWorker {

	/** Stala. */
	private static final String MSG_RECIPIENT_DELETED = "recipientDeleted";

	/** Stala. */
	private static final String MSG_SENDER_DELETED = "senderDeleted";

	/** Stala. */
	private static final String FALSE = "false";

	/** Nazwa nazwanego zapytania. */
	private static final String SENDER_ARG = "sender";

	/** Nazwa parametru zapytania. */
	private static final String RECIPIENT_ARG = "recipient";

	/** Nazwa nazwanego zapytania. */
	private static final String OUT_MESSAGES_QUERY = "outgoingMessages";

	/** Nazwa nazwanego zapytania. */
	private static final String IN_MESSAGES_QUERY = "incomingMessages";

	/** Parametr zapytania. */
	private static final String DELETED_PARAM = "deleted";

	/** Logger. */
	private static final Logger LOG = LoggerFactory.getLogger(MessageWorkerBean.class);

	
	/** Worker maili.*/
	@EJB
	private MailerWorkerLocal mailerWorker;

	/** Konstruktor. */
	public MessageWorkerBean() { };

	/** {@inheritDoc} */
	@Override
	public void sendMessage(Message message, User recipient, User sender) {

		message.setSender(sender);
		message.setRecipient(recipient);
		LOG.debug(StringUtils.WHITESPACE + message.toString() + StringUtils.WHITESPACE + sender + StringUtils.WHITESPACE + recipient);
		getEntityManager().persist(message);
		
		mailerWorker.sendSentMessageNotification(sender.getLabel(), recipient.getEmail() );
	}

	/**{@inheritDoc} */
	@Override
	public List<Message> retrieveIncomingMessages(User user) {
		Query query = getEntityManager().createNamedQuery(IN_MESSAGES_QUERY);
		query.setParameter(DELETED_PARAM, false);
		query.setParameter(RECIPIENT_ARG, user);
		return getResultList(query, Message.class);
	}

	/**{@inheritDoc} */
	@Override
	public List<Message> retrieveOutgoingMessages(User user) {
		Query query = getEntityManager().createNamedQuery(OUT_MESSAGES_QUERY);
		query.setParameter(DELETED_PARAM, false);
		query.setParameter(SENDER_ARG, user);
		return getResultList(query, Message.class);
	}

	/**{@inheritDoc} */
	@Override
	public void removeMessage(int messageId, User loggedUser, String mode) {
		Message message = retrieveMessage(messageId);
		if("incoming".equals(mode)) {
			message.setRecipientDeleted(true);
		} else if("outgoing".equals(mode)) {
			message.setSenderDeleted(true);
		}
		
		saveEntity(message);
	}

	/** {@inheritDoc} */
	@Override
	public Message retrieveMessage(int messageId) {
		return getEntityManager().find(Message.class, messageId);
	}
	

	/** {@inheritDoc} */
	@Override
	public List<Message> retrieveIncomingMessages(User user, PagingInfo pagingInfo) {
		LOG.debug("Pobieranie wiadomosci przychodzacych {}", user);
		Query query = getEntityManager().createNamedQuery(IN_MESSAGES_QUERY);
		query.setParameter(DELETED_PARAM, false);
		query.setParameter(RECIPIENT_ARG, user);
		
		if(pagingInfo != null) {
			query.setFirstResult(pagingInfo.getFirstResult());
			query.setMaxResults(pagingInfo.getRowsPerPage());
		}
		return getResultList(query, Message.class);
	}

	/** {@inheritDoc} */
	@Override
	public List<Message> retrieveOutgoingMessages(User user, PagingInfo pagingInfo) {
		LOG.debug("Pobieranie wiadomosci wyslanych");
		Query query = getEntityManager().createNamedQuery(OUT_MESSAGES_QUERY);
		query.setParameter(DELETED_PARAM, false);
		query.setParameter(SENDER_ARG, user);
		
		if(pagingInfo != null) {
			query.setFirstResult(pagingInfo.getFirstResult());
			query.setMaxResults(pagingInfo.getRowsPerPage());
		}
		return getResultList(query, Message.class);
	}

	/** {@inheritDoc} */
	@Override
	public Long retrieveIncomingMessagesCount(User user) {
		Query qry = getEntityManager().createNamedQuery("incomingMessagesCount");
		qry.setParameter(RECIPIENT_ARG, user);
		qry.setParameter(DELETED_PARAM, false);
		return getSingleResult(qry, Long.class);
	}

	/** {@inheritDoc} */
	@Override
	public Long retrieveOutgoingMessagesCount(User user) {
		Query qry = getEntityManager().createNamedQuery("outgoingMessagesCount");
		qry.setParameter(SENDER_ARG, user);
		qry.setParameter(DELETED_PARAM, false);
		return getSingleResult(qry, Long.class);
	}

	/** {@inheritDoc} */
	@Override
	public void markMessageAsReaded(int messageId) {
		Message message = retrieveMessage(messageId);
		message.setMessageStatus(MessageStatus.READED);
		saveEntity(message);
	}

	/** {@inheritDoc} */
	@Override
	public List<Message> retrieveIncomingMessages(PagingInfo pagingInfo, SearchParameters<Message> searchParams) {
		
		searchParams.addEqualsSearchParam(MSG_RECIPIENT_DELETED, Boolean.FALSE);
		Query query = searchParams.createQuery(getEntityManager());
		
		if(pagingInfo != null) {
			query.setFirstResult(pagingInfo.getFirstResult());
			query.setMaxResults(pagingInfo.getRowsPerPage());
		}
		
		List<Message> result = getResultList(query, Message.class);
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public Long retrieveIncomingMessagesCount(SearchParameters<Message> searchParams) {
		searchParams.addEqualsSearchParam(MSG_RECIPIENT_DELETED, Boolean.FALSE);
		Query query = searchParams.createCountingQuery(getEntityManager());
		return getSingleResult(query, Long.class);
	}

	/** {@inheritDoc} */
	@Override
	public List<Message> retrieveOutgoingMessages(PagingInfo pagingInfo, SearchParameters<Message> searchParams) {
		
		searchParams.addEqualsSearchParam(MSG_SENDER_DELETED, Boolean.FALSE);
		Query query = searchParams.createQuery(getEntityManager());
		
		if(pagingInfo != null) {
			query.setFirstResult(pagingInfo.getFirstResult());
			query.setMaxResults(pagingInfo.getRowsPerPage());
		}
		
		List<Message> result = getResultList(query, Message.class);
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public Long retrieveOutgoingMessagesCount(SearchParameters<Message> searchParams) {
		searchParams.addEqualsSearchParam(MSG_SENDER_DELETED, Boolean.FALSE);
		Query query = searchParams.createCountingQuery(getEntityManager());
		return getSingleResult(query, Long.class);
	}

}
