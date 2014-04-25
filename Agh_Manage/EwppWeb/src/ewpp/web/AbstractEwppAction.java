package ewpp.web;

import com.opensymphony.xwork2.ActionSupport;

import ewpp.auth.entity.User;
import ewpp.business.workers.UsersWorker;
import ewpp.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.struts2.interceptor.ParameterAware;
import org.apache.struts2.interceptor.SessionAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Abstrakcujen klasa dla aplikacji.
 * @author malczyk.pawel@gmail.com
 *
 */
public class AbstractEwppAction extends ActionSupport implements ParameterAware, SessionAware {

	/** Rezultat. */
	public static final String LIST = "list";

	/** Mozliwa operacja. */
	public static final String SAVE = "save";

	/** Mozliwa operacja. */
	public static final String EDIT = "edit";

	/** Mozliwa operacja. */
	public static final String DELETE = "delete";

	/** Mozliwa operacja. */
	public static final String NEW = "new";

	/** Mozliwa operacja. */
	public static final String VIEW = "view";

	/** Mozliwa operacja. */
	public static final String SEARCH = "search";

	/** Zwracany widok gdy user nie zalogowany. */
	public static final String NOT_LOGGED = "notlogged";

	/** Logger. */
	private static final Logger LOG = LoggerFactory.getLogger(AbstractEwppAction.class);

	/** Mozliwa operacja. */
	private static final String OPERATION = "operation";

	/** Serial. */
	private static final long serialVersionUID = 7834332377621880734L;


	/** Kontekst JNDI. */
	private InitialContext initialContext;

	/** Mapa parametrow requestu. */
	private Map < String, String[] > reqAttributes;

	/** Atrybuty sesji. */
	private Map < String, Object > sessionAttributes;

	/** Kontruktor. */
	public AbstractEwppAction() { }

	/**
	 * Znajduje w JNDI odpowiedni worker.
	 * @param <T>
	 * 			typ workera
	 * @param workerClass
	 * 			klasa worker
	 * @return
	 * 		`worker
	 */
	@SuppressWarnings("unchecked")
	public final < T > T locate( Class < T > workerClass ) {
		//TODO: jakos to ladniej by trzeba rozwiazac
		T result = null;
		try {
			Object object = getInitialContext().lookup(workerClass.getName() + "$" + workerClass.getSimpleName() + "Remote" );
			result = (T) object;
		} catch (NamingException e) {
			LOG.warn(e.getMessage());
		}
		return result;
	}

	/**
	 * Zwraca startwoy kontekst.
	 * @return kontekst JNDI
	 */
	public final InitialContext getInitialContext() {
		if (initialContext == null) {
			try {
				initialContext = new InitialContext();
			} catch (NamingException e) {
				LOG.warn("Error obtaining initial context");
			}
		}
		return initialContext;
	}


	/**
	 * Handler listowania elementow.
	 * @return nazwa widoku listowania
	 * Pusta implementacja
	 */
	protected String doList() {
		return null;
	}

	/**
	 * handler zapisywania elementu.
	 * @return nazwa widoku zapisania.
	 * Pusta implementacja
	 */
	protected String doSave() {
		return null;
	}

	/**
	 * handler edycji elementu.
	 * @return nazwa widoku edycji.
	 * Pusta implemetncaja
	 */
	protected String doEdit() {
		return null;
	}

	/**
	 * handler podgladu elementu.
	 * @return nazwa widoku podgladu.
	 * Pusta implementacja
	 */
	protected String doView() {
		return null;
	}

	/**
	 * handler dodawania nowego elementu.
	 * @return nazwa widoku tworznie nowego.
	 * Pusta implementacja
	 */
	protected String doNew() {
		return null;
	}

	/** handler usuwania elementu.
	 * @return nazwa widoku usuwania.
	 * Pusta imeplementacja
	 */
	protected String doDelete() {
		return null;
	}

	/**
	 * handler usuwania elementu.
	 * @return nazwa widoku wyszukiwania
	 * Pusta implementacja
	 */
	protected String doSearch() {
		return null;
	}


	/** {@inheritDoc} */
	@Override
	public String execute() throws Exception {
		System.out.println("Abstract EXECTUTE");

		String operation = getOperation();


		if ( StringUtils.isEmpty( operation ) ) {
			operation = getDefaultOpertation();
		}

		if ( NEW.equals( operation ) ) {
			operation = doNew();
		} else if (EDIT.equals(operation)) {
			operation = doEdit();
		} else if (VIEW.equals(operation)) {
			operation = doView();
		} else if (LIST.equals(operation)) {
			operation = doList();
		} else if (SAVE.equals(operation)) {
			operation = doSave();
		} else if (DELETE.equals(operation)) {
			operation = doDelete();
		} else if (SEARCH.equals(operation)) {
			operation = doSearch();
		}

		LOG.info(operation);

		return operation;
	}

	/**
	 * Do przeslanaia - typowa domyslna operacja jesli nie sprecyzowano.
	 * @return domyslna operacja dla akcji
	 */
	public String getDefaultOpertation() {
		return LIST;
	}


	/**
	 * Zwraca operacje z requestu.
	 *
	 * @return wartosc operacji z requestu
	 */
	public final String getOperation() {
		String [] vals = reqAttributes.get(OPERATION);
		return (vals != null) ? vals[0] : StringUtils.EMPTY;
	}

	/**
	 * Zwraca parametry requestu.
	 *
	 * @return parametry requestu
	 */
	public final Map < String, String[] > getReqAttributes() {
		return (reqAttributes==null)?new HashMap<String, String[]>():reqAttributes;
	}


	/** {@inheritDoc} */
	@Override
	public void setSession(Map < String, Object > session) {
		this.sessionAttributes = session;
	}

	/**
	 * Sprawdza czy zaloowany uzytkownik.
	 * @return czy uzytkownik jest zalogowany
	 */
	public final boolean isUserLogged() {
		return getSessionAttributes().get(SessionData.SESSION_DATA_KEY) != null;
	}

	/**
	 * Zwraca identyfikator zalogowanego uzytkownika.
	 * @return identyfikator zalowanego uzytkownika
	 */
	public final Integer getLoggedUserId() {
		SessionData sessionData = (SessionData) getSessionAttributes().get(SessionData.SESSION_DATA_KEY);
		return ( sessionData == null ) ? -1 : sessionData.getUserId();
	}
	
	
	/**
	 * Zwraca zalogowanego uzytkownika.
	 * @return zalogowany uzytkownik
	 */
	public final User getLoggedUser() {
		return locate(UsersWorker.class).retrieveUser(getLoggedUserId());
	}




	/**
	 * Zwraca atryuty sesji.
	 * @return atrybuty sesji
	 */
	public final Map < String, Object > getSessionAttributes() {
		return sessionAttributes;
	}

	/** {@inheritDoc} */
	@Override
	public void setParameters(Map < String, String[] > arg0) {
		LOG.debug("Setting request parameters");
		reqAttributes = arg0;
	}

}
