package process.web.actions;

import java.util.Map;

import org.apache.struts2.dispatcher.SessionMap;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

/**
 * Handles user's logout.
 * @author <a href="mailto:malczyk.pawel@gmail.com">malczyk.pawel@gmail.com</a>
 *
 */
public class LogoutAction extends ActionSupport implements SessionAware {
	
	/**
	 * Serial.
	 */
	private static final long serialVersionUID = 1773066796131538653L;
	
	/** Atrybuty sesji. */
	private Map < String, Object > sessionAttributes;

	/**
	 * Constructor.
	 */
	public LogoutAction() {
		super();
	}
	
	/** {@inheritDoc} */
	@Override
	public String execute() throws Exception {
		((SessionMap < String, Object > ) sessionAttributes).invalidate();
		return LOGIN;
	}
	
	/** {@inheritDoc} */
	@Override
	public void setSession(Map < String, Object > session) {
		this.sessionAttributes = session;
	}
}
