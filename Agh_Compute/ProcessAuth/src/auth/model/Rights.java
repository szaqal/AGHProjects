package auth.model;

/**
 * Represents available rights.
 * @author    <a href="mailto:malczyk.pawel@gmail.com">malczyk.pawel@gmail.com</a>
 */
public enum Rights {
	
	/**
	 * @uml.property  name="uSER_ADMIN"
	 * @uml.associationEnd  
	 */
	USER_ADMIN("user_admin"),
	/**
	 * @uml.property  name="gROUP_ADMIN"
	 * @uml.associationEnd  
	 */
	GROUP_ADMIN("group_admin"),
	/**
	 * @uml.property  name="pROCESS_ADMIN"
	 * @uml.associationEnd  
	 */
	PROCESS_ADMIN("process_admin");
	
	/**
	 * Choosed value.
	 */
	private String choise;

	/**
	 * Constructor.
	 * @param arg choose right
	 */
	Rights(String arg) {
		choise = arg;
	}
	
	/** {@inheritDoc} */
	@Override
	public String toString() {
		return choise;
	}
	
}
