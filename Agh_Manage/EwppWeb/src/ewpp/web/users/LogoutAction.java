package ewpp.web.users;

import java.util.Map;

import org.apache.struts2.dispatcher.SessionMap;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

/**
 * Akcja wylogowania.
 * @author malczyk.pawel@gmail.com
 *
 */
public class LogoutAction extends ActionSupport implements SessionAware {

	/** Serial. */
	private static final long serialVersionUID = 1299078624018492913L;

	/** Atrybuty sesji. */
	private Map < String, Object > sessionAttributes;

	/** Konstruktor. */
	public LogoutAction() { };

	/** {@inheritDoc} */
	@Override
	public String execute() throws Exception {
		((SessionMap < String, Object > ) sessionAttributes).invalidate();
		return SUCCESS;
	}

	/** {@inheritDoc} */
	@Override
	public void setSession(Map < String, Object > session) {
		this.sessionAttributes = session;
	}

}
