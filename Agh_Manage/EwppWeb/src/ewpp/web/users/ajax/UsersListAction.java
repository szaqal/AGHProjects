package ewpp.web.users.ajax;

import ewpp.auth.entity.User;
import ewpp.business.workers.UsersWorker;
import ewpp.utils.PagingInfo;
import ewpp.utils.SearchParameters;
import ewpp.utils.StringUtils;
import ewpp.web.ajax.AbstractAjaxPagedListAction;
import ewpp.web.ajax.AjaxPage;
import ewpp.web.ajax.AjaxRow;

import java.util.ArrayList;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Obsluguje Ajaxowoa liste uzytkownikow.
 * @author malczyk.pawel@gmail.com
 *
 */
public class UsersListAction extends AbstractAjaxPagedListAction<User> {

	/**
	 * Null.
	 */
	private static final String NULL = "Null";

	/**
	 * Serial.
	 */
	private static final long serialVersionUID = 211132906695693284L;
	
	/**
	 * Parametr requestu ktory odpowiada z wyszukiwanie po imieniu.
	 */
	private static final String FIRST_NAME_SEARCH_PARAM = "fname";
	
	/**
	 * Parametr requestu ktory odpowiada z wyszukiwanie po nazwisku.
	 */
	private static final String LAST_NAME_SEARCH_PARAM = "lname";
	
	/**
	 * Alias encji.
	 */
	private static final String ENTITY_ALIAS = "user";
	
	/**
	 * Logger.
	 */
	private static final Logger LOG = LoggerFactory.getLogger(UsersListAction.class);
	
	/** Zwracana przygotwana strona. */
	private AjaxPage<User> pageContent;


	/** Konstruktor. */
	public UsersListAction() { };

	/** {@inheritDoc} */
	@SuppressWarnings("unchecked")
	@Override
	public String execute() throws Exception {
		LOG.debug("execute () in {}", this.getClass().getName() );
		
		
		PagingInfo paginginfo = getPagingInfo();
		Collection<User> contents = getContents(paginginfo);
		Collection<AjaxRow<User>> rows = new ArrayList<AjaxRow<User>>();
	
		for(User user : contents ) {
			rows.add(new AjaxRow<User>(user.getJsonData(), user.getUniqueId(), true));
		}
		
		AjaxRow<User> [] resultRows = rows.toArray(new AjaxRow [rows.size()]);
		
		pageContent = new AjaxPage<User>(resultRows, getPage(), paginginfo.getPageCount(getUsersCount()), getUsersCount());

		return SUCCESS;
	}
	
	
	/**
	 * Zwraca dane.
	 * @param pagingInfo info o stronicowaniu
	 * @return lista elementow
	 */
	protected Collection<User> getContents(PagingInfo pagingInfo) {
		LOG.debug("getContents() with {}", getRows());
		if (StringUtils.isEmpty(getFnameParam()) && StringUtils.isEmpty(getLnameParam())) {
			return locate(UsersWorker.class).retrieveUsers(pagingInfo);
		} else {
			return locate(UsersWorker.class).retrieveUsers(extractParamsFromRequest(), pagingInfo);
		}
		
	}
	
	/**
	 * Tworzy odpowiednie parametry wyszukiwania 
	 * z danych przychodz�cych z requestu.
	 * @return przygotowane parametry wyszukiwania.
	 */
	protected SearchParameters<User> extractParamsFromRequest() {
		SearchParameters<User> searchParameters = new SearchParameters<User>(User.class, ENTITY_ALIAS);
		if(StringUtils.isNotEmpty(getFnameParam())) {
			searchParameters.addLenientLikeSearchParam("firstName", getFnameParam());
		}
		if(StringUtils.isNotEmpty(getLnameParam())) {
			searchParameters.addLenientLikeSearchParam("lastName", getLnameParam());
		}
		searchParameters.addEqualsSearchParam("deleted", false);
		return searchParameters;
	}
	
	/**
	 * Zwraca ilosc uzytkownik�w.
	 * @return ilosc uzytkownik�w
	 */
	protected long getUsersCount() {
		if (StringUtils.isEmpty(getFnameParam()) && StringUtils.isEmpty(getLnameParam())) {
			return locate(UsersWorker.class).retrieveUsersCount();
		} else {
			return locate(UsersWorker.class).retrieveUsersCount(extractParamsFromRequest());
		}
			
		
	}


	/** {@inheritDoc} */
	@Override
	public AjaxPage<User> getPageContent() {
		return pageContent;
	}
	
	/**
	 * Pobiera parametr requestu.
	 * 
	 * @return wartosc parametru
	 */
	protected String getFnameParam() {
		String result = StringUtils.EMPTY;
		try {
			result = getReqAttributes().get(FIRST_NAME_SEARCH_PARAM)[0];
		} catch (NullPointerException e) {
			LOG.warn(NULL);
		}
		return result;
	}
	
	/**
	 * Pobiera parametr requestu.
	 * 
	 * @return wartosc parametru
	 */
	protected String getLnameParam() {
		String result = StringUtils.EMPTY;
		try {
			result = getReqAttributes().get(LAST_NAME_SEARCH_PARAM)[0];
		} catch (NullPointerException e) {
			LOG.warn(NULL);
		}
		return result;
	}
	
	

}
