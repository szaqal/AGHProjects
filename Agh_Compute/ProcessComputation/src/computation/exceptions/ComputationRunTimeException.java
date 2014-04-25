package computation.exceptions;

/**
 * Exception throwed whenever something wrong happened. 
 * @author malczyk.pawel@gmail.com
 *
 */
public class ComputationRunTimeException extends Exception {
	
	/**
	 * Serial.
	 */
	private static final long serialVersionUID = 581631066048832937L;

	/**
	 * Constructor.
	 */
	public ComputationRunTimeException() {
		super();
	}
	
	/**
	 * Constructor.
	 * @param message error message.
	 */
	public ComputationRunTimeException(String message) {
		super(message);
	}
	
	/** {@inheritDoc} */
	@Override
	public String getMessage() {
		return super.getMessage();
	}

}
