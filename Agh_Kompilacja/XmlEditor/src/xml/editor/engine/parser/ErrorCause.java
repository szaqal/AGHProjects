package xml.editor.engine.parser;

/**
 * Stala przechwujace informacje o mozliwych zlych sytucjach. (tu nalezy dopisywac kolejne)
 * @author malczyk.pawel@gmail.com
 */
public interface ErrorCause {

	/** B³êdny glowny element. */
	int INVALID_DOC_ROOT_ELEMENT = 0;

	/** B³êdny element dziecka. */
	int INVALID_CHILD_ELEMENT = 1;

	/** B³êdny atrybut. */
	int INVALID_ATTRIBUTE = 2;

	/** B³êdny gdy oczekiwany byl jeden elemet. */
	int LESS_THAN_ONE_ELEMENT_OCCURENCE = 3;

	/** Gdy wymagane wieksza liczba wystapien elementu. */
	int MORE_THAN_ONE_ELEMENT_OCCURENCE_REQUIRED = 4;

	/**
	 * Zwraca przyczyne bledu.
	 *
	 * @return kod przyczyny bledu
	 */
	int getErrorCause();

}
