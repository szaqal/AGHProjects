package core.utils;



/**
 * Class with helpful methods.
 * @author <a href="mailto:malczyk.pawel@gmail.com">malczyk.pawel@gmail.com</a>
 *
 */
public final class BusinessUtils {

	/**
	 * Regular expression describing email.
	 */
	public static final String EMAIL_REG_EX = "^(([a-zA-Z0-9_-])+(\\.)?)+[a-zA-Z0-9_-]@(([a-zA-Z0-9_-])+(\\.)?)+[a-zA-Z0-9_-]$";

	/**
	 * Regular expression describing URL.
	 */
	public static final String URL_REG_EX = "^((ht|f)tp(s?)\\:\\/\\/|~/|/)?([\\w]+:\\w+@)?([a-zA-Z]{1}([\\w\\-]+\\.)+([\\w]{2,5}))(:[\\d]{1,5})?((/?\\w+/)+|/?)(\\w+\\.[\\w]{3,4})?((\\?\\w+=\\w+)?(&\\w+=\\w+)*)?";
	
	/**
	 * Private Constructor.
	 *
	 */
	private BusinessUtils() { }
	
}

