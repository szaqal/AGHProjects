package process.interceptors;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import process.web.SessionData;
import process.web.actions.CompleteRegistrationAction;
import process.web.actions.LoginAction;
import process.web.actions.RegisterNodeAction;
import process.web.actions.RegisterUserAction;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

/**
 * Authentication Interceptor.
 * @author <a href="mailto:malczyk.pawel@gmail.com">malczyk.pawel@gmail.com</a>
 *
 */
public class AuthenticationInterceptor implements Interceptor {

	/**
	 * Serial.
	 */
	private static final long serialVersionUID = -2279010413748913914L;
	
	/**
	 * Logger. 
	 */
	private static final Logger LOG = LoggerFactory.getLogger(AuthenticationInterceptor.class);
	
	/**
	 * Actions that user don't have to be authenticated.
	 */
	private static final List<Class<?>> EXCLUDED_ACTIONS = new ArrayList<Class<?>>();
	
	static {
		EXCLUDED_ACTIONS.add(LoginAction.class);
		EXCLUDED_ACTIONS.add(RegisterUserAction.class);
		EXCLUDED_ACTIONS.add(RegisterNodeAction.class);
		EXCLUDED_ACTIONS.add(CompleteRegistrationAction.class);
	}
	
	/**
	 * Constructor..
	 */
	public AuthenticationInterceptor() { }

	/** {@inheritDoc} */
	@Override
	public void destroy() {
		LOG.info("Destroying AuthenticationInterceptor...");
	}

	/** {@inheritDoc} */
	@Override
	public void init() {
		LOG.info("Initiating AuthenticationInterceptor...");
	}

	@Override
	public String intercept(ActionInvocation actionInvocation) throws Exception {
		LOG.debug("Intercepting");
		Class<?> requestedActionClass = actionInvocation.getAction().getClass();
		LOG.debug("Requested {}", requestedActionClass.getName());
		SessionData sessionData = (SessionData) actionInvocation.getInvocationContext().getSession().get(SessionData.SESSION_DATA_KEY);
		boolean loginRequired = sessionData == null && !EXCLUDED_ACTIONS.contains(requestedActionClass);
		return loginRequired ? Action.LOGIN : actionInvocation.invoke();

	}

	
}
