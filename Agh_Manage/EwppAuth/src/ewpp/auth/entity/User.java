package ewpp.auth.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import ewpp.entity.AbstractEwppEntity;
import ewpp.utils.StringUtils;

/**
 * Encja u�ytkownika.
 *
 * @author malczyk.pawel@gmail.com
 *
 */
@Entity
@SequenceGenerator(name = "USER_SEQUENCE", sequenceName = "USER_SEQ")
@Table(name = "users")
public class User implements Serializable, AbstractEwppEntity {

	/**
	 * Serial.
	 */
	private static final long serialVersionUID = -5490828715631856275L;

	/** Identyfikator. */
	private int uniqueId;

	/** Login. */
	private String login;

	/** Haslo. */
	private String passwd;

	/** Drugie imie. */
	private String middleName;

	/** email. */
	private String email;

	/** tel. */
	private String phoneNr;

	/** Imie. */
	private String firstName;

	/** Nazwisko. */
	private String lastName;

	/** Typ uzytkownika. */
	private UserType userType;
	
	/** Czy oznaczony jako usuniety. */
	private boolean deleted;

	/** Konstruktor. */
	public User() { };



	/**
	 * Getter.
	 * @return the uniqueId
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="USER_SEQUENCE")
	public int getUniqueId() {
		return uniqueId;
	}

	/**
	 * Setter.
	 * @param uniqueId the uniqueId to set
	 */
	public void setUniqueId(int uniqueId) {
		this.uniqueId = uniqueId;
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
	 * @param login the login to set
	 */
	public void setLogin(String login) {
		this.login = login;
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
	 * @param passwd the passwd to set
	 */
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	/**
	 * Getter.
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Setter.
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Getter.
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Setter.
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Zwraca typ uzytkownika.
	 * @return the userType
	 */
	@Enumerated(EnumType.STRING)
	public UserType getUserType() {
		return userType;
	}

	/**
	 * Ustawia typ uzytkownika.
	 * @param userType the userType to set
	 */
	public void setUserType(UserType userType) {
		this.userType = userType;
	}

	/**
	 * Zwraca user firentlu label uzytkownika.
	 * @return labelka uzytkownika
	 */
	@Transient
	public String getLabel() {
		return firstName + StringUtils.WHITESPACE + lastName;
	}

	/**
	 * @return the middleName
	 */
	public String getMiddleName() {
		return middleName;
	}

	/**
	 * @param middleName the middleName to set
	 */
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the phoneNr
	 */
	public String getPhoneNr() {
		return phoneNr;
	}

	/**
	 * @param phoneNr the phoneNr to set
	 */
	public void setPhoneNr(String phoneNr) {
		this.phoneNr = phoneNr;
	}


	/** {@inheritDoc} */
	@Transient
	@Override
	public String[] getJsonData() {
		List<String> data = new ArrayList<String>();
		data.add(firstName);
		data.add(lastName);
		data.add(email);
		data.add(phoneNr);
		return data.toArray(new String[data.size()]);
	}



	/**
	 * Getter.
	 * @return the deleted
	 */
	public boolean isDeleted() {
		return deleted;
	}



	/**
	 * Setter.
	 * @param deleted the deleted to set
	 */
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}



}
