package auth.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Transient;

import core.model.AbstractEntity;

/**
 * Contains single information about user login.
 * @author  <a href="mailto:malczyk.pawel@gmail.com">malczyk.pawel@gmail.com</a>
 */
@Entity
@NamedQueries({
	@NamedQuery(name="retrieveByUserAndDate", query="SELECT loginEntry FROM LoginEntry AS loginEntry " +
			"WHERE loginEntry.loggedUser.uniqueId =:userId ORDER BY loginEntry.loginDate DESC")
})
public class LoginEntry extends AbstractEntity {
	
	/** Constant. */
	public static final String BY_USER_AND_DATE = "retrieveByUserAndDate";
	
	/**
	 * Identifier.
	 * @uml.property  name="uniqueId"
	 */
	private int uniqueId;
	
	/**
	 * Login location (contains Ip address).
	 * @uml.property  name="loginFrom"
	 */
	private String loginFrom;
	
	/**
	 * Login time.
	 * @uml.property  name="loginDate"
	 */
	private Calendar loginDate;
	
	
	/**
	 * User who was logging.
	 * @uml.property  name="loggedUser"
	 * @uml.associationEnd  
	 */
	private User loggedUser;
	
	/**
	 * Constructor.
	 */
	public LoginEntry() { }

	/** {@inheritDoc} */
	@Override
	@Transient
	public String[] getJsonData() {
		List<String> data = new ArrayList<String>();
		data.add(loginFrom);
		data.add(getLoggedUser().getLabel());
		return data.toArray(new String[data.size()]);
	}

	/**
	 * {@inheritDoc} 
	 * @uml.property  name="uniqueId"
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Override
	public int getUniqueId() {
		return uniqueId;
	}

	/**
	 * {@inheritDoc} 
	 * @uml.property  name="uniqueId"
	 */
	@Override
	public void setUniqueId(int uniqueId) {
		this.uniqueId = uniqueId;
	}

	/**
	 * Getter.
	 * @return  the loginFrom
	 * @uml.property  name="loginFrom"
	 */
	public String getLoginFrom() {
		return loginFrom;
	}

	/**
	 * Setter.
	 * @param loginFrom  the loginFrom to set
	 * @uml.property  name="loginFrom"
	 */
	public void setLoginFrom(String loginFrom) {
		this.loginFrom = loginFrom;
	}

	/**
	 * Getter.
	 * @return  the loginDate
	 * @uml.property  name="loginDate"
	 */
	public Calendar getLoginDate() {
		return loginDate;
	}

	/**
	 * Setter.
	 * @param loginDate  the loginDate to set
	 * @uml.property  name="loginDate"
	 */
	public void setLoginDate(Calendar loginDate) {
		this.loginDate = loginDate;
	}

	/**
	 * Getter.
	 * @return  the user
	 * @uml.property  name="loggedUser"
	 */
	@ManyToOne
	public User getLoggedUser() {
		return loggedUser;
	}

	/**
	 * Setter.
	 * @param user  the user to set
	 * @uml.property  name="loggedUser"
	 */
	public void setLoggedUser(User user) {
		this.loggedUser = user;
	}
	
	/** {@inheritDoc} */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		String space = " ";
		builder.append(getLoggedUser().getLabel()).append(space)
		.append(getLoginFrom()).append(space).append(getLoginDate());
		return builder.toString();
	}


}
