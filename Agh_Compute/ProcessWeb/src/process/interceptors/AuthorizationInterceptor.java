package process.interceptors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import process.web.Require;
import process.web.SessionData;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

/**
 * Authorization interceptor.
 * @author malczyk.pawel@gmail.com
 *
 */
public class AuthorizationInterceptor implements Interceptor {
	
	/**
	 * Serial.
	 */
	private static final long serialVersionUID = 3816803467724732236L;
	
	/**
	 * Logger.
	 */
	private static final Logger LOG = LoggerFactory.getLogger(AuthenticationInterceptor.class);

	/**
	 * Constructor.
	 */
	public AuthorizationInterceptor() {	}
	
	/** {@inheritDoc} */
	@Override
	public void destroy() {
		LOG.debug("Destroying Authorization interceptor.");
	}

	/** */
	@Override
	public void init() {
		LOG.debug("Initializing Authorization interceptor.");
		
	}

	/** {@inheritDoc} */
	@Override
	public String intercept(ActionInvocation actionInvocation) throws Exception {
		Class<?> requestedActionClass = actionInvocation.getAction().getClass();
		Require annotation = requestedActionClass.getAnnotation(Require.class);
		String result = null;
		if(annotation==null) {
			result = actionInvocation.invoke();
		} else {
			if(isAuthorized(actionInvocation, annotation.permission())) {
				result = actionInvocation.invoke();
			} else {
				result = "Unauthorized";
			}
		}
		return result;
	}
	
	/**
	 * Checks if logged user contains appropriate permissions.
	 * @param actionInvocation action invocation
	 * @param requiredPermission required permission
	 * 
	 * @return true/false
	 */
	private boolean isAuthorized(ActionInvocation actionInvocation, String requiredPermission) {
		SessionData sessionData = (SessionData) actionInvocation.getInvocationContext().getSession().get(SessionData.SESSION_DATA_KEY);
		return sessionData.getPermissions().contains(requiredPermission) || sessionData.isSuperUser();
	}

}
