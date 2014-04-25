package xml.editor.engine.parser;


/**
 * Informacja zwrotna o typie bledu jego kodzie i polozeniu
 * w celu zaznaczenia odpowiednich sekcji w edytorze.
 * @author malczyk.pawel@gmail.com
 *
 */
public class ParseViolation {

	/** Przyczyna (co jest nie tak?). */
	private int errorCause;

	/** Typ. */
	private ErrorType errorType;

	/** Numer linii w ktorej wystepuje blad. */
	private int location;

	/**
	 * Konstruktor.
	 *
	 * @param cause
	 *            przyczyna
	 * @param type
	 *            typ bledu
	 * @param loc
	 *            polozenie w dokumencie {@link Document}
	 */
	public ParseViolation(final int cause, final ErrorType type, final int loc) {
		errorCause = cause;
		this.errorType = type;
		this.location = loc;
	}


	/**
	 * Getter.
	 *
	 * @return wartosc pola
	 */
	public int getErrorCause() {
		return errorCause;
	}


	/**
	 * Getter.
	 *
	 * @return wartosc pola
	 */
	public ErrorType getErrorType() {
		return errorType;
	}


	/**
	 * Getter.
	 *
	 * @return wartosc pola
	 */
	public int getLocation() {
		return location;
	}



}
