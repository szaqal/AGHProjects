package ewpp.web.messages.ajax;

import ewpp.messaging.entity.Message;
import ewpp.messaging.workers.MessageWorker;
import ewpp.utils.CollectionUtils;
import ewpp.utils.PagingInfo;
import ewpp.utils.SearchParameters;
import ewpp.utils.StringUtils;
import ewpp.web.ajax.AbstractAjaxPagedListAction;
import ewpp.web.ajax.AjaxPage;
import ewpp.web.ajax.AjaxRow;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Akcja oblusgujaca ajaxowa liste wiadomosci.
 * @author malczyk.pawel@gmail.com
 *
 */
public class MessagesListAction extends AbstractAjaxPagedListAction<Message> {

	

	/** 
	 * Logger. 
	 */
	public static final Logger LOG = LoggerFactory.getLogger(MessagesListAction.class);

	/**
	 * Stala.
	 */
	private static final String TITLE = "title";
	
	/**
	 * Stala.
	 */
	private static final String NULL = "null";
	
	/**
	 * Serial.
	 */
	private static final long serialVersionUID = 9114968091715714334L;
	
	/**
	 * Alias encji.
	 */
	private static final String ENTITY_ALIAS = "msg";
	
	/**
	 * Tryb przychodzacych.
	 */
	private static final String INCOMING_MODE = "incoming";

	/**
	 * Tryb wychodzacaych.
	 */
	private static final String OUTGOING_MODE = "outgoing";
	
	/**
	 * Index nadawcy w tablicy dla JSON.
	 */
	private static final int SENDER_INDEX_JSON = 2;
	
	/**
	 * Index adresata w tablicy dla JSON.
	 */
	private static final int RECIPIENT_INDEX_JSON = 3;
	
	
	/** Zwracana przygotwana strona. */
	private AjaxPage<Message> pageContent;
	
	/**
	 * Konstruktor.
	 */
	public MessagesListAction() { }
	
	/** {@inheritDoc} */
	@SuppressWarnings("unchecked")
	@Override
	public String execute() throws Exception {
		LOG.debug("execute () in {}", this.getClass().getName() );
		PagingInfo pagingInfo = getPagingInfo();
		Collection<Message> contents = getContents(pagingInfo);
		Collection<AjaxRow<Message>> rows = new ArrayList<AjaxRow<Message>>();
		
		for (Message message : contents) {
			
			
			if(INCOMING_MODE.equals(getMode())) {
				rows.add(new AjaxRow<Message>(CollectionUtils.removeItemFromArray(RECIPIENT_INDEX_JSON, message.getJsonData()), message.getUniqueId(), true));
			} else if (OUTGOING_MODE.equals(getMode())) {
				rows.add(new AjaxRow<Message>(CollectionUtils.removeItemFromArray(SENDER_INDEX_JSON, message.getJsonData()), message.getUniqueId(), true));
			} else if(StringUtils.isEmpty(getMode())) {
				rows.add(new AjaxRow<Message>(CollectionUtils.removeItemFromArray(RECIPIENT_INDEX_JSON, message.getJsonData()), message.getUniqueId(), true));
			}
			
		}
		
		AjaxRow<Message> [] resultRows = rows.toArray(new AjaxRow [rows.size()]);
		
		pageContent = new AjaxPage<Message>(resultRows, getPage(), pagingInfo.getPageCount(getMessagesCount()), getMessagesCount());
		return SUCCESS;
	}
	
	/**
	 * Zwraca dane.
	 * @param pagingInfo info o stronicowaniu
	 * @return lista elementow
	 */
	private Collection<Message> getContents(PagingInfo pagingInfo) {
		LOG.debug("getContents() with {}", getRows());
		Collection<Message> result = new ArrayList<Message>();
		if(OUTGOING_MODE.equals(getMode())) {
			if(StringUtils.isEmpty(getTitle()) && StringUtils.isEmpty(getUser())) {
				result = locate(MessageWorker.class).retrieveOutgoingMessages(getLoggedUser(), pagingInfo);
			} else {
				result = locate(MessageWorker.class).retrieveOutgoingMessages(pagingInfo, extractParamsFromRequest());
			}
		} else if(INCOMING_MODE.equals(getMode())) {
			if(StringUtils.isEmpty(getTitle()) && StringUtils.isEmpty(getUser())) {
				result = locate(MessageWorker.class).retrieveIncomingMessages(getLoggedUser(), pagingInfo);
			} else {
				result = locate(MessageWorker.class).retrieveIncomingMessages(pagingInfo, extractParamsFromRequest());
			}
			
		} 
		return result;
		
	}
	
	/**
	 * Pobiera parametr requestu.
	 * 
	 * @return warto�� parametru
	 */
	private String getMode() {
		String result = StringUtils.EMPTY;
		try {
			result = getReqAttributes().get("mode")[0];
		} catch (NullPointerException e) {
			LOG.warn(StringUtils.NULL);
		}
		return result;
	}

	
	/**
	 * Zwraca ilosc wiadomosci.
	 * @return ilosc wiadomosci
	 */
	private long getMessagesCount() {
		long result = 0;
		if(OUTGOING_MODE.equals(getMode())) {
			result = locate(MessageWorker.class).retrieveOutgoingMessagesCount(extractParamsFromRequest());
		} else if(INCOMING_MODE.equals(getMode()) || StringUtils.isEmpty(getMode())) {
			result = locate(MessageWorker.class).retrieveIncomingMessagesCount(extractParamsFromRequest());
		} 
		return result;
	}

	

	/** {@inheritDoc} */
	@Override
	public AjaxPage<Message> getPageContent() {
		return pageContent;
	}
	
	/**
	 * Tworzy odpowiednie parametry wyszukiwania 
	 * z danych przychodz�cych z requestu.
	 * @return przygotowane parametry wyszukiwania.
	 */
	private SearchParameters<Message> extractParamsFromRequest() {
		SearchParameters<Message> searchParameters = new SearchParameters<Message>(Message.class, ENTITY_ALIAS);
		
		String param = INCOMING_MODE.equals(getMode()) ? "recipient" : "sender";
		searchParameters.addEqualsSearchParam(param, getLoggedUser());
		
		if(StringUtils.isNotEmpty(getTitle())) {
			searchParameters.addLenientLikeSearchParam(TITLE, getTitle());
		}
		
		if(StringUtils.isNotEmpty(getUser())) {
			if (INCOMING_MODE.equals(getMode()) || StringUtils.isEmpty(getMode())) {
				Map<String, String> paramMap = new HashMap<String, String>();
				paramMap.put("recipient.firstName", getUser());
				paramMap.put("recipient.lastName", getUser());
				searchParameters.addOrLenientLikeSearchParams(paramMap);
			} else {
				Map<String, String> paramMap = new HashMap<String, String>();
				paramMap.put("sender.firstName", getUser());
				paramMap.put("sender.lastName", getUser());
				searchParameters.addOrLenientLikeSearchParams(paramMap);
			}
		}
		return searchParameters;
	}
	
	/**
	 * Pobiera parametr requestu.
	 * 
	 * @return warto�� parametru
	 */
	private String getTitle() {
		String result = StringUtils.EMPTY;
		try {
			result = getReqAttributes().get(TITLE)[0];
		} catch (NullPointerException e) {
			LOG.warn(NULL);
		}
		return result;
	}
	
	/**
	 * Pobiera parametr requestu.
	 * 
	 * @return warto�� parametru
	 */
	private String getUser() {
		String result = StringUtils.EMPTY;
		try {
			result = getReqAttributes().get("user")[0];
		} catch (NullPointerException e) {
			LOG.warn(NULL);
		}
		return result;
	}

}
