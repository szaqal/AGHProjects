package core.workers;

/**
 * Interface type.
 * @author    <a href="mailto:malczyk.pawel@gmail.com">malczyk.pawel@gmail.com</a>
 */
public enum EjbInterfaceType {
	/**
	 * @uml.property  name="rEMOTE"
	 * @uml.associationEnd  
	 */
	REMOTE("Remote"),
	/**
	 * @uml.property  name="lOCAL"
	 * @uml.associationEnd  
	 */
	LOCAL("Local");
	
	/** ChoosedType. */
	private String type;
	
	/**
	 * Constructor.
	 * @param arg interface type
	 */
	EjbInterfaceType(String arg) {
		this.type=arg;
	}
	
	/** {@inheritDoc} */
	@Override
	public String toString() {
		return type;
	}
	
}
