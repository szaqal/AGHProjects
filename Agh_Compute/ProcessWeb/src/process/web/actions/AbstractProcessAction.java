package process.web.actions;

import java.util.HashMap;
import java.util.Map;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ParameterAware;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;

import process.web.SessionData;
import process.web.actions.lists.AbstractListAction;

import com.opensymphony.xwork2.ActionSupport;

import core.workers.EjbInterfaceType;

/**
 * Common action for all other actions in the project.
 * @author  <a href="mailto:malczyk.pawel@gmail.com">malczyk.pawel@gmail.com</a>
 */
public class AbstractProcessAction extends ActionSupport implements ParameterAware, SessionAware, ServletRequestAware {

	
	/** Constant. */
	public static final String VIEW = "view";
	
	/** Serial. */
	private static final long serialVersionUID = 7834332377621880734L;

	/**
	 * JNDI Context.
	 * @uml.property  name="initialContext"
	 */
	private InitialContext initialContext;

	/**
	 * Request parameters.
	 * @uml.property  name="reqAttributes"
	 */
	private Map < String, String[] > reqAttributes;

	/**
	 * Session attributes.
	 * @uml.property  name="sessionAttributes"
	 */
	private Map < String, Object > sessionAttributes;
	
	/** Current servlet request. */
	private HttpServletRequest request;
	


	/** Constructor. */
	public AbstractProcessAction() { }

	/**
	 * lookups for worker in JNDI.
	 * @param <T>
	 * 			worker type
	 * @param workerClass
	 * 			worker class
	 * @return
	 * 		`worker
	 */
	@SuppressWarnings("unchecked")
	public final < T > T locate( Class < T > workerClass ) {
		//TODO: jakos to ladniej by trzeba rozwiazac
		T result = null;
		try {
			Object object = getInitialContext().lookup(workerClass.getName() + "$" + workerClass.getSimpleName() + EjbInterfaceType.REMOTE );
			result = (T) object;
		} catch (NamingException e) {
			LOG.warn(e.getMessage());
		}
		return result;
	}

	/**
	 * Returns JNDI context.
	 * @return  JNDI context
	 * @uml.property  name="initialContext"
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

	/** {@inheritDoc} */
	@Override
	public String execute() throws Exception {
		String result = SUCCESS;
		if(this instanceof AbstractEditAction<?>) {
			result = ((AbstractEditAction<?>) this).handle();
		} else if(this instanceof AbstractListAction<?>) {
			result = ((AbstractListAction<?>) this).doList();
		}
		return result;
	}

	/**
	 * Returns request parameters.
	 * @return  request parameters.
	 * @uml.property  name="reqAttributes"
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
	 * Returns session attributes.
	 * @return  session attributes
	 * @uml.property  name="sessionAttributes"
	 */
	public final Map < String, Object > getSessionAttributes() {
		return sessionAttributes;
	}

	/** {@inheritDoc} */
	@Override
	public void setParameters(Map < String, String[] > arg0) {
		reqAttributes = arg0;
	}
	
	/** 
	 * Returns {@linkplain SessionData} from request.
	 * @return session data. 
	 */
	protected SessionData getSessionData() {
		return (SessionData) getSessionAttributes().get(SessionData.SESSION_DATA_KEY);
	}

	/** {@inheritDoc} */
	@Override
	public void setServletRequest(HttpServletRequest req) {
		request = req;
	}
	
	/**
	 * Returns connected host address.
	 * @return host address.
	 */
	protected String getHost() {
		String localAddr = request.getRemoteAddr();
		return localAddr;
	}
	

}