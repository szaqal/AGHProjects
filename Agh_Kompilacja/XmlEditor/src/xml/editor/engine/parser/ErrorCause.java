package xml.editor.engine.parser;

/**
 * Stala przechwujace informacje o mozliwych zlych sytucjach. (tu nalezy dopisywac kolejne)
 * @author malczyk.pawel@gmail.com
 */
public interface ErrorCause {

	/** B��dny glowny element. */
	int INVALID_DOC_ROOT_ELEMENT = 0;

	/** B��dny element dziecka. */
	int INVALID_CHILD_ELEMENT = 1;

	/** B��dny atrybut. */
	int INVALID_ATTRIBUTE = 2;

	/** B��dny gdy oczekiwany byl jeden elemet. */
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
