package process.web;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Object hold information about current session. 
 * @author  <a href="mailto:malczyk.pawel@gmail.com">malczyk.pawel@gmail.com</a>
 */
public class SessionData implements Serializable {
	/**
	 * Session attribute key.
	 */
	public static final String SESSION_DATA_KEY = "sessionData";


	/**
	 * Serial.
	 */
	private static final long serialVersionUID = -9130538237849418038L;

	/**
	 * User label.
	 * @uml.property  name="userLabel"
	 */
	private String userLabel;

	/**
	 * User identifier.
	 * @uml.property  name="userId"
	 */
	private String userId;
	
	/**
	 * Is super user.
	 * @uml.property  name="superUser"
	 */
	private boolean superUser;
	
	/**
	 * Logged user email.
	 * @uml.property  name="email"
	 */
	private String email;
	
	/**
	 * Users permissions.
	 * @uml.property  name="permissions"
	 */
	private Set<String> permissions = new HashSet<String>();
	

	/**
	 * Constructor.
	 * @param superUser is superUser.
	 */
	public SessionData(boolean superUser) { 
		this.superUser = superUser;
	};

	/**
	 * Getter.
	 * @return  label user
	 * @uml.property  name="userLabel"
	 */
	public final String getUserLabel() {
		return userLabel;
	}

	/**
	 * Setter.
	 * @param userLabelArg  user label
	 * @uml.property  name="userLabel"
	 */
	public final void setUserLabel(String userLabelArg) {
		this.userLabel = userLabelArg;
	}

	/**
	 * Getter.
	 * @return  user identifier
	 * @uml.property  name="userId"
	 */
	public final String getUserId() {
		return userId;
	}

	/**
	 * Setter.
	 * @param userIdArg  user identifier
	 * @uml.property  name="userId"
	 */
	public final void setUserId(String userIdArg) {
		this.userId = userIdArg;
	}

	/**
	 * Getter.
	 * @return  the permissions
	 * @uml.property  name="permissions"
	 */
	public Set<String> getPermissions() {
		return permissions;
	}

	/**
	 * Setter.
	 * @param permissions  the permissions to set
	 * @uml.property  name="permissions"
	 */
	public void setPermissions(Set<String> permissions) {
		this.permissions = permissions;
	}

	/**
	 * Getter.
	 * @return  the superUser
	 * @uml.property  name="superUser"
	 */
	public boolean isSuperUser() {
		return superUser;
	}

	/**
	 * Getter.
	 * @return  the email
	 * @uml.property  name="email"
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Setter.
	 * @param email  the email to set
	 * @uml.property  name="email"
	 */
	public void setEmail(String email) {
		this.email = email;
	}

}
