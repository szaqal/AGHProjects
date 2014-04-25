package auth.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Transient;

import core.model.AbstractEntity;

/**
 * Represents user.
 * @author  <a href="mailto:malczyk.pawel@gmail.com">malczyk.pawel@gmail.com</a>
 */
@Entity
@NamedQueries({
	@NamedQuery(name="retrieveByEmail",
			query="SELECT usr FROM User AS usr WHERE usr.email =:email AND usr.active=true"),
	@NamedQuery(name="existingUsers", 
			query="SELECT usr FROM User AS usr"),
	@NamedQuery(name="existingUsersCount", 
			query="SELECT COUNT(usr) FROM User AS usr")		
})
public class User extends AbstractEntity {
	
	/** Constant. */
	public static final String BY_EMAIL = "retrieveByEmail";
	
	/** Constant. */
	public static final String EXISTING = "existingUsers";
	
	/** Constant. */	
	public static final String EXISTING_COUNT = "existingUsersCount";
	
	/**
	 * Normal, regular user.
	 */
	public static final int NORMAL = 0;
	
	/**
	 * System user.
	 */
	public static final int SYSTEM = 1;
	
	/**
	 * Super user.
	 */
	public static final int SUPER = 2;
	
	/**
	 * SuperUser.
	 * @uml.property  name="userType"
	 */
	private int userType;
	
	/**
	 * Identifier.
	 * @uml.property  name="uniqueId"
	 */
	private int uniqueId;
	
	/**
	 * User's email.
	 * @uml.property  name="email"
	 */
	private String email;
	
	/**
	 * User's password.
	 * @uml.property  name="password"
	 */
	private String password;
	
	/**
	 * User's first name.
	 * @uml.property  name="firstName"
	 */
	private String firstName;
	
	/**
	 * User's last name.
	 * @uml.property  name="lastName"
	 */
	private String lastName;
	
	
	/**
	 * Is user active (meaning completed registration).
	 * @uml.property  name="active"
	 */
	private boolean active;
	
	
	/**
	 * Default constructor.
	 */
	public User() {
		
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

	/**
	 * Getter.
	 * @return  the password
	 * @uml.property  name="password"
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Setter.
	 * @param password  the password to set
	 * @uml.property  name="password"
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Getter.
	 * @return  the firstName
	 * @uml.property  name="firstName"
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Setter.
	 * @param firstName  the firstName to set
	 * @uml.property  name="firstName"
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Getter.
	 * @return  the lastName
	 * @uml.property  name="lastName"
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Setter.
	 * @param lastName  the lastName to set
	 * @uml.property  name="lastName"
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}



	/**
	 * Getter.
	 * @return  the uniqueId
	 * @uml.property  name="uniqueId"
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Override
	public int getUniqueId() {
		return uniqueId;
	}

	/**
	 * Setter.
	 * @param uniqueId  the uniqueId to set
	 * @uml.property  name="uniqueId"
	 */
	public void setUniqueId(int uniqueId) {
		this.uniqueId = uniqueId;
	}
	
	/**
	 * Returns user label.
	 * @return user label
	 */
	@Transient
	public String getLabel() {
		return String.format(" %s %s ", getFirstName(), getLastName());
	}
	
	
	/** {@inheritDoc} */
	@Transient
	public String[] getJsonData() {
		List<String> data = new ArrayList<String>();
		data.add(firstName);
		data.add(lastName);
		data.add(email);
		return data.toArray(new String[data.size()]);
	}


	/**
	 * Getter.
	 * @return  the userType
	 * @uml.property  name="userType"
	 */
	@Transient
	public int getUserType() {
		return userType;
	}

	/**
	 * Setter.
	 * @param userType  the userType to set
	 * @uml.property  name="userType"
	 */
	public void setUserType(int userType) {
		this.userType = userType;
	}

	/**
	 * Getter.
	 * @return  the active
	 * @uml.property  name="active"
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * Setter.
	 * @param active  the active to set
	 * @uml.property  name="active"
	 */
	public void setActive(boolean active) {
		this.active = active;
	}

}
