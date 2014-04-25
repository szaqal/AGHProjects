package core.session;

import java.io.Serializable;

/**
 * Represents data about loggedUser.
 * @author  <a href="mailto:malczyk.pawel@gmail.com">malczyk.pawel@gmail.com</a>
 */
public class SessionInfo implements Serializable {
	/**
	 * Serial.
	 */
	private static final long serialVersionUID = -7498443957591767732L;
	
	/**
	 * Logged userId.
	 * @uml.property  name="loggedUserId"
	 */
	private String loggedUserId;
	
	/**
	 * Constructor.
	 */
	public SessionInfo() { }

	/**
	 * Getter.
	 * @return  the loggedUserId
	 * @uml.property  name="loggedUserId"
	 */
	public String getLoggedUserId() {
		return loggedUserId;
	}

	/**
	 * Setter.
	 * @param loggedUserId  the loggedUserId to set
	 * @uml.property  name="loggedUserId"
	 */
	public void setLoggedUserId(String loggedUserId) {
		this.loggedUserId = loggedUserId;
	}
	

}
