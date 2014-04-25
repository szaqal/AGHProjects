package ewpp.web.users;

import ewpp.auth.entity.User;
import ewpp.auth.entity.UserType;
import ewpp.auth.workers.DatabaseAuthenticatorWorker;
import ewpp.web.AbstractEwppAction;
import ewpp.web.SessionData;


/**
 * Akcja logownia.
 * @author malczyk.pawel@gmail.com
 *
 */
public class LogonAction extends AbstractEwppAction {


	/**
	 * Serial.
	 */
	private static final long serialVersionUID = -6877733154218120625L;

	/** Logowanie. */
	private static final String LOGIN = "login";

	/** Login z formularza. */
	private String login;

	/** Haslo z formulara. */
	private String passwd;

	/**
	 * Konstruktor.
	 */
	public LogonAction() { };

	/** {@inheritDoc} */
	@Override
	protected String doEdit() {
		return EDIT;
	}


	/** {@inheritDoc} */
	@Override
	public String execute() throws Exception {
		String operation = super.execute();
		if ( operation.equals(LOGIN) ) {
			operation = doLogin();
		}
		return operation;
	}

	/**
	 * Przeperowadza logowanie.
	 * @return rezultat logowania
	 */
	private String doLogin() {
		DatabaseAuthenticatorWorker authenticator = locate(DatabaseAuthenticatorWorker.class);
		User user = authenticator.authenticate(login, passwd, null);
		if (user != null) {
			if (getSessionAttributes().get(SessionData.SESSION_DATA_KEY) == null) {
				SessionData sessionData = new SessionData();
				sessionData.setUserId(user.getUniqueId());
				sessionData.setUserLabel(user.getLabel());
				sessionData.setLecturer(UserType.LECTURER.equals(user.getUserType()));
				sessionData.setAdmin(UserType.ADMIN.equals(user.getUserType()));
				sessionData.setStudent(UserType.STUDENT.equals(user.getUserType()));
				getSessionAttributes().put(SessionData.SESSION_DATA_KEY, sessionData);
			}
		}
		
		
		if ( user == null ) {
			return "notlogged";
		} else {
			return UserType.ADMIN.equals(user.getUserType())?"adminlogged":"logged";
		}
		
		
		
	}


	/** {@inheritDoc} */
	@Override
	public String getDefaultOpertation() {
		return EDIT;
	}


	/**
	 * Getter.
	 * @return the login
	 */
	public String getLogin() {
		return login;
	}


	/**
	 * Setter.
	 * @param logon the login to set
	 */
	public void setLogin(String logon) {
		this.login = logon;
	}


	/**
	 * Getter.
	 * @return the passwd
	 */
	public String getPasswd() {
		return passwd;
	}


	/**
	 * Setter.
	 * @param password the passwd to set
	 */
	public void setPasswd(String password) {
		this.passwd = password;
	}

}
