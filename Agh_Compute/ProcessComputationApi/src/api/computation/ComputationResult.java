package api.computation;

import java.io.Serializable;

/**
 * Acts as a wrapper for computation result.
 * @author  <a href="mailto:malczyk.pawel@gmail.com>malczyk.pawel@gmail.com</a>
 * @param  <T>  type for result
 */
public class ComputationResult<T extends Serializable> implements Serializable {

	/** Serial.	 */
	private static final long serialVersionUID = -581384671614059304L;
	
	/**
	 * Actual result.
	 * @uml.property  name="result"
	 */
	private T result;
	
	/**
	 * Constructor.
	 * @param result computation result
	 */
	public ComputationResult(T result) {
		this.result = result;
	}

	
	/**
	 * Returns actual result.
	 * @return  result
	 * @uml.property  name="result"
	 */
	public T getResult() {
		return result;
	}
	
}
