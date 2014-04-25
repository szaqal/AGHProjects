package process.web.actions;

import auth.LoginEntryWorker;
import auth.model.LoginEntry;

/**
 * Index Action.
 * @author  <a href="mailto:malczyk.pawel@gmail.com">malczyk.pawel@gmail.com</a>
 */
public class IndexAction extends AbstractProcessAction {

	/**
	 * Serial.
	 */
	private static final long serialVersionUID = 8557824988600698721L;

	
	/**
	 * Currently logged user.
	 * @uml.property  name="loggedUser"
	 */
	private String loggedUser;
	
	/**
	 * Info about last login.
	 * @uml.property  name="loginData"
	 * @uml.associationEnd  
	 */
	private LoginEntry loginData;
	
	/**
	 * Constructor.
	 */
	public IndexAction() { }
	
	/** {@inheritDoc} */
	@Override
	public String execute() throws Exception {
		loggedUser = getSessionData().getUserLabel();
		loginData = locate(LoginEntryWorker.class).retrieveLatestLoginEntry(Integer.valueOf(getSessionData().getUserId()));
		return VIEW;
	}

	/**
	 * Getter.
	 * @return  the loggedUser
	 * @uml.property  name="loggedUser"
	 */
	public String getLoggedUser() {
		return loggedUser;
	}

	/**
	 * Setter.
	 * @param loggedUser  the loggedUser to set
	 * @uml.property  name="loggedUser"
	 */
	public void setLoggedUser(String loggedUser) {
		this.loggedUser = loggedUser;
	}

	/**
	 * Getter.
	 * @return  the loginData
	 * @uml.property  name="loginData"
	 */
	public LoginEntry getLoginData() {
		return loginData;
	}

	/**
	 * Setter.
	 * @param loginData  the loginData to set
	 * @uml.property  name="loginData"
	 */
	public void setLoginData(LoginEntry loginData) {
		this.loginData = loginData;
	}

}
