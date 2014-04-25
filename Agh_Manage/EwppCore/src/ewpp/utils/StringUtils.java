package ewpp.utils;

/**
 * Utilowa klasa operacji na stringach.
 * @author malczyk.pawel@gail.com
 *
 */
public final class StringUtils {

	/** Stala. */
	public static final String WHITESPACE = " ";

	/** Stala. */
	public static final String AMP = "&";

	/** Stala. */
	public static final String SLASH = "/";

	/** Stala. */
	public static final String EMPTY = "";
	
	/** Stala. */
	public static final String DOT = ".";
	
	/** Stala. */
	public static final String COLON = ":";
	
	/** Stala. */
	public static final String AND = "AND";
	
	/** Stala. */
	public static final String NULL = "NULL";
	
	/** Stala. */
	public static final String QL_ANY = "%";
	

	/** Konstruktor. */
	private StringUtils() { }

	/**
	 * Sprawdza czy pusty String.
	 * @param arg
	 * 		badany string
	 * @return
	 * 		sprawdza czy pusty string
	 */
	public static boolean isEmpty(String arg) {
		return arg == null || arg.length() == 0;
	}

	/**
	 * Sprawdza czy nie jest pusty string.
	 * @param arg
	 * 		badany string.
	 * @return
	 * 		pusty/nie pusty string
	 */
	public static boolean isNotEmpty(String arg) {
		return arg != null && arg.length() > 0;
	}

}
