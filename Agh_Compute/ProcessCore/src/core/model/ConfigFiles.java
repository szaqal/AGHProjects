package core.model;

/**
 * Holds heys for application-wide configuration files.
 * 
 * @author malczyk.pawel@gmail.com
 * 
 */
public enum ConfigFiles {
	/**
	 * Config key.
	 */
	COMPUTATION_START_VALIDATION("computation.start.validation"),
	/**
	 * Config key.
	 */
	COMPUTATION_VALIDATION("computation.validation");

	/**
	 * Key value.
	 */
	private String keyValue;

	/**
	 * Constructor.
	 * 
	 * @param keyValue
	 *            key value
	 */
	ConfigFiles(String keyValue) {
		this.keyValue = keyValue;
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return keyValue;
	}
}
