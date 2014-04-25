package auth.model;

import java.util.Date;

import javax.persistence.CascadeType;
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
 * Single user registration.
 * @author  malczyk.pawel@gmail.com
 */
@Entity
@NamedQueries({
	@NamedQuery(name="retrieveByUser",
			query="SELECT reg FROM Registration AS reg WHERE reg.usr.uniqueId =:usrId")
})
public class Registration extends AbstractEntity {
	
	/**
	 * Named query name.
	 */
	public static final String BY_USER = "retrieveByUser";
	
	/**
	 * Unique id.
	 * @uml.property  name="uniqueId"
	 */
	private int uniqueId;
	
	/**
	 * User for which registration was made.
	 * @uml.property  name="usr"
	 * @uml.associationEnd  
	 */
	private User usr;
	
	/**
	 * Date when registration was made.
	 * @uml.property  name="registrationDate"
	 */
	private Date registrationDate;
	
	/**
	 * Date when registration completed.
	 * @uml.property  name="completeRegistration"
	 */
	private Date completeRegistration;
	
	/**
	 * Constructor.
	 */
	public Registration() {
		super();
	}

	/** {@inheritDoc} */
	@Transient
	@Override
	public String[] getJsonData() {
		return null;
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
	 * @return  the user
	 * @uml.property  name="usr"
	 */
	@ManyToOne(cascade={CascadeType.PERSIST})
	public User getUsr() {
		return usr;
	}

	/**
	 * Setter.
	 * @param user  the user to set
	 * @uml.property  name="usr"
	 */
	public void setUsr(User user) {
		this.usr = user;
	}

	/**
	 * Getter.
	 * @return  the registrationDate
	 * @uml.property  name="registrationDate"
	 */
	public Date getRegistrationDate() {
		return registrationDate;
	}

	/**
	 * Setter.
	 * @param registrationDate  the registrationDate to set
	 * @uml.property  name="registrationDate"
	 */
	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}

	/**
	 * Getter.
	 * @return  the completeRegistration
	 * @uml.property  name="completeRegistration"
	 */
	public Date getCompleteRegistration() {
		return completeRegistration;
	}

	/**
	 * Setter.
	 * @param completeRegistration  the completeRegistration to set
	 * @uml.property  name="completeRegistration"
	 */
	public void setCompleteRegistration(Date completeRegistration) {
		this.completeRegistration = completeRegistration;
	}
	

}
