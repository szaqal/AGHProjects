package core.utils;

/**
 * Class operating on strings.
 * @author malczyk.pawel@gmail.com
 *
 */
public final class StringUtils {

	/** Constant. */
	public static final String WHITESPACE = " ";

	/** Constant. */
	public static final String AMP = "&";

	/** Constant. */
	public static final String SLASH = "/";

	/** Constant. */
	public static final String EMPTY = "";
	
	/** Constant. */
	public static final String DOT = ".";
	
	/** Constant. */
	public static final String COMMA = ",";
	
	/** Constant. */
	public static final String COLON = ":";
	
	/** Constant. */
	public static final String AND = "AND";
	
	/** Constant. */
	public static final String NULL = "NULL";
	
	/** Constant. */
	public static final String QL_ANY = "%";
	
	/** Constant. */
	public static final String TEXT_HTML = "text/html";
	

	/** Constructor. */
	private StringUtils() { }

	/**
	 * Checks if string is not empty.
	 * @param arg
	 * 		string being checked
	 * @return
	 * 		result.
	 */
	public static boolean isEmpty(String arg) {
		return arg == null || arg.length() == 0;
	}

	/**
	 * Checks if string is not empty.
	 * @param arg
	 * 		string being checked.
	 * @return
	 * 		result.
	 */
	public static boolean isNotEmpty(String arg) {
		return arg != null && arg.length() > 0;
	}

}

