package computation.exceptions;

/**
 * Exception throwed when unable to create computation from supplied xml file.
 * 
 * @author <a href="mailto:malczyk.pawel@gmail.com>malczyk.pawel@gmail.com</a>
 *
 */
public class ComputationCreationException extends Exception {
	
	/**
	 * Serial.
	 */
	private static final long serialVersionUID = -8057589157631460984L;

	/**
	 * Constructor.
	 */
	public ComputationCreationException() {
		super();
	}
	
	/**
	 * {@inheritDoc} 
	 */
	@Override
	public String getMessage() {
		return "Unable to create computation object ";
	}

}
