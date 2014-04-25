package ewpp.web;

import java.io.Serializable;

/**
 * Przechowuje przydante informacje na temat uzytkownika
 * itp w sesji.
 * @author malczyk.pawel@gmail.com
 *
 */
public class SessionData implements Serializable {

	/**
	 * Klucz atrybutu sesji.
	 */
	public static final String SESSION_DATA_KEY = "sessionData";


	/**
	 * Serial.
	 */
	private static final long serialVersionUID = -9130538237849418038L;

	/**
	 * Labelka uzytkownika.
	 */
	private String userLabel;

	/**
	 * Identyfikator uzytkownika.
	 */
	private Integer userId;
	
	/**
	 * Czy wykladowca.
	 */
	private boolean lecturer;
	
	/**
	 * Czy admin.
	 */
	private boolean admin;
	
	/**
	 * Czy student.
	 */
	private boolean student;

	/**
	 * Konstruktor.
	 */
	public SessionData() { };

	/**
	 * Getter.
	 *
	 * @return label uzytkownika
	 */
	public final String getUserLabel() {
		return userLabel;
	}

	/**
	 * Setter.
	 *
	 * @param userLabelArg
	 *            identyfikator uzytkownika
	 */
	public final void setUserLabel(String userLabelArg) {
		this.userLabel = userLabelArg;
	}

	/**
	 * Getter.
	 *
	 * @return identyfikator uzytkownika
	 */
	public final Integer getUserId() {
		return userId;
	}

	/**
	 * Setter.
	 *
	 * @param userIdArg
	 *            identyfikator uzytkownika
	 */
	public final void setUserId(Integer userIdArg) {
		this.userId = userIdArg;
	}

	/**
	 * Getter.
	 * @return the lecturer
	 */
	public final boolean isLecturer() {
		return lecturer;
	}

	/**
	 * Setter.
	 * @param lecturer the lecturer to set
	 */
	public final void setLecturer(boolean lecturer) {
		this.lecturer = lecturer;
	}

	/**
	 * Getter.
	 * @return the admin
	 */
	public final boolean isAdmin() {
		return admin;
	}

	/**
	 * Setter.
	 * @param admin the admin to set
	 */
	public final void setAdmin(boolean admin) {
		this.admin = admin;
	}

	/**
	 * Getter.
	 * @return the student
	 */
	public final boolean isStudent() {
		return student;
	}

	/**
	 * Setter.
	 * @param student the student to set
	 */
	public final void setStudent(boolean student) {
		this.student = student;
	}

}