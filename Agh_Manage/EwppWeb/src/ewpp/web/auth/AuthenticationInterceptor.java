package ewpp.web.auth;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

import ewpp.web.SessionData;
import ewpp.web.users.LogonAction;
import ewpp.web.users.RegisterUserAction;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Interceptor autentykacji.
 * @author malczyk.pawel@gmail.com
 *
 */
public class AuthenticationInterceptor implements Interceptor{

	/**
	 * Serial.
	 */
	private static final long serialVersionUID = -2279010413748913914L;
	
	/**
	 * Logger. 
	 */
	private static final Logger LOG = LoggerFactory.getLogger(AuthenticationInterceptor.class);
	
	/**
	 * Akcje dostepne ogolnie (tzn wylaczone z logowania).
	 */
	private static final List<Class<?>> EXCLUDED_ACTIONS = new ArrayList<Class<?>>();
	
	static {
		EXCLUDED_ACTIONS.add(LogonAction.class);
		EXCLUDED_ACTIONS.add(RegisterUserAction.class);
	}
	
	/**
	 * Konstruktor.
	 */
	public AuthenticationInterceptor() { }

	/** {@inheritDoc} */
	@Override
	public void destroy() {
		LOG.debug("Destroying AuthenticationInterceptor...");
	}

	/** {@inheritDoc} */
	@Override
	public void init() {
		LOG.debug("Initiating AuthenticationInterceptor...");
	}

	@Override
	public String intercept(ActionInvocation actionInvocation) throws Exception {
		LOG.debug("Intercepting");
		Class<?> requestedActionClass = actionInvocation.getAction().getClass();
		LOG.debug("Requested {}", requestedActionClass.getName());
		SessionData sessionData = (SessionData) actionInvocation.getInvocationContext().getSession().get(SessionData.SESSION_DATA_KEY);
		return ( sessionData == null && !EXCLUDED_ACTIONS.contains(requestedActionClass)) ? Action.LOGIN : actionInvocation.invoke();

	}

}
