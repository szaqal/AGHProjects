package computation.exceptions;

/**
 * Exception throwed when there is no configuration file within Computation package
 * or is invalid.
 * @author <a href="mailto:malczyk.pawel@gmail.com>malczyk.pawel@gmail.com</a>
 *
 */
public class NoValidConfigFileException extends Exception {

	/**
	 * Serial.
	 */
	private static final long serialVersionUID = -4081808952498782921L;
	
	/**
	 * Constructor.
	 */
	public NoValidConfigFileException() {
		super();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getMessage() {
		return "No configuration file found";
	}

}
