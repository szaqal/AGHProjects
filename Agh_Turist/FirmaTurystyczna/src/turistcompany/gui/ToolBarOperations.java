package turistcompany.gui;

/**
 * Enum zawiera nazwy akcji dla poszczególnych przycisków menu.
 * @author malczyk.pawel@gmail.com
 *
 */
public enum ToolBarOperations {
	
	/** Wyświetlenie panelu klientow */
	MANAGE_CLIENTS("Klieci"),
	/** Wyświetlenie panelu ofert */
	MANAGE_OFFERS("Oferty"),
	/** Wyświetlenie panelu atrakcji */
	MANAGE_ATTRACTIONS("Atrakcje"),
	/** Wyświetlenie panelu rezerwacji */
	MANAGE_RESERVATIONS("Rezerwacje"),
	/** Wyświetlenie panelu administracji */
	MANAGE_SYSTEM("Administracja"),
	/** Zamkniecie aplikacji  */
	CLOSE("Zamknij");
	
	/** Wybrana wartosc*/
	private String value;
	
	/**
	 * Konstruktor
	 * @param arg wybrana wartosc.
	 */
	private ToolBarOperations(String arg) {
		value = arg;
	}
	
	/**
	 * Getter wybranej wartosci.
	 * @return wybana wartosc.
	 */
	public String getValue(){
		return value;
	}
	
}
