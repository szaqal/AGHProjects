package process.web.actions;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import process.web.SessionData;
import auth.LoginEntryWorker;
import auth.UserGroupWorker;
import auth.UsersWorker;
import auth.model.Group;
import auth.model.LoginEntry;
import auth.model.User;


/**
 * Login action.
 * @author  <a href="mailto:malczyk.pawel@gmail.com">malczyk.pawel@gmail.com</a>
 */
public class LoginAction extends AbstractProcessAction {

	/**
	 * Serial.
	 */
	private static final long serialVersionUID = -4574278226701090427L;
	
	/**
	 * Login operation key.
	 */
	private static final String LOGIN_OPERATION = "form.login.perform";
	
	/**
	 * Login operation key.
	 */
	private static final String INDEX = "index";
	
	/**
	 * Logger.
	 */
	private static final Logger LOG = LoggerFactory.getLogger(LoginAction.class);
	
	/**
	 * Submitted login.
	 * @uml.property  name="email"
	 */
	private String email;
	
	/**
	 * Submitted password.
	 * @uml.property  name="passwd"
	 */
	private String passwd;
	
	/**
	 * Operation supplied.
	 * @uml.property  name="operation"
	 */
	private String operation;
	
	/**
	 * Authenticated user.
	 * @uml.property  name="user"
	 * @uml.associationEnd  
	 */
	private User user;

	/**
	 * Constructor.
	 */
	public LoginAction() {
		super();
	}
	
	/** {@inheritDoc}*/
	@Override
	public String execute() throws Exception {
		LOG.info("Executing Login Action");
		
		if( LOGIN_OPERATION.equals(operation) ) {
			return doLogin();
		}
		
		return LOGIN;
	}
	
	
	
	/**
	 * Returns user.
	 * @return {@link User}
	 */
	private User retrieveUser() {
		UsersWorker worker = locate(UsersWorker.class);
		return worker.authenticate(email, passwd);
	}
	
	/**
	 * Performs login.
	 * @return result od authenticating
	 */
	private String doLogin() {
		LoginEntryWorker loginEntryWorker = locate(LoginEntryWorker.class);
		UserGroupWorker userGroupWorker = locate(UserGroupWorker.class);
		if(user == null) {
			user = retrieveUser();
		}
		
		if (user != null) {
			if (getSessionAttributes().get(SessionData.SESSION_DATA_KEY) == null) {
				SessionData sessionData = new SessionData(User.SUPER == user.getUserType());
				sessionData.setUserId(String.valueOf(user.getUniqueId()));
				sessionData.setEmail(user.getEmail());
				sessionData.setUserLabel(user.getLabel());
				Collection<Group> groups = userGroupWorker.retrieveByUser(user.getUniqueId());
				for(Group group:groups) {
					
					if(group.getRights() != null ) {
						String [] splitted = group.getRights().split(";");
						sessionData.getPermissions().addAll(Arrays.asList(splitted));
					}
				}
				
				getSessionAttributes().put(SessionData.SESSION_DATA_KEY, sessionData);
				
				if(User.SUPER != user.getUserType()) {
					LoginEntry loginEntry = prepareLoginEntry(user);		
					loginEntryWorker.storeLoginEntry(loginEntry);
				}
			}
		}
		
		
		return (user != null)?INDEX:LOGIN;
	}
	
	/** {@inheritDoc} */
	@Override
	public void validate() {
		
		if(LOGIN_OPERATION.equals(operation)) {
		
			if(user == null) {
				user = retrieveUser();
			}
			
			if(user==null) {
				String invalidLoginMessageKey = "invalidLoginOrPassword";
				addFieldError("email", getText(invalidLoginMessageKey));
				addFieldError("passwd", getText(invalidLoginMessageKey));
			}
		}
	}

	/**
	 * Prepares {@link LoginEntry} entity.
	 * @param user user being logged.
	 * @return prepared login entry.
	 */
	private LoginEntry prepareLoginEntry(User user) {
		LoginEntry loginEntry = new LoginEntry();
		loginEntry.setLoggedUser(user);
		loginEntry.setLoginDate(Calendar.getInstance());
		loginEntry.setLoginFrom(getHost());
		return loginEntry;
	}

	/**
	 * Getter.
	 * @return  the login
	 * @uml.property  name="email"
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Setter.
	 * @param email  the login to set
	 * @uml.property  name="email"
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Getter.
	 * @return  the password
	 * @uml.property  name="passwd"
	 */
	public String getPasswd() {
		return passwd;
	}

	/**
	 * Setter.
	 * @param passwd  the passwd to set
	 * @uml.property  name="passwd"
	 */
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	/**
	 * Getter.
	 * @return  the operation
	 * @uml.property  name="operation"
	 */
	public String getOperation() {
		return operation;
	}

	/**
	 * Setter.
	 * @param operation  the operation to set
	 * @uml.property  name="operation"
	 */
	public void setOperation(String operation) {
		this.operation = operation;
	}
}
