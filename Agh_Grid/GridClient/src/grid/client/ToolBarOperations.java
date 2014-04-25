package grid.client;
/**
 * Enum zawiera nazwy akcji dla poszczegĂłlnych przyciskĂłw menu.
 * @author malczyk.pawel@gmail.com
 *
 */
public enum ToolBarOperations {
	
	/** Wyswietlenie panelu klientow */
	LIST_ACCOUTS("Lista kont"),
	/** Wyswietlenie panelu ofert */
	NEW_CLOSE_ACCOUNT("Otworz/Zamknij konto"),
	/** Wyswietlenie panelu atrakcji */
	TRANSFERS("Przelewy"),
	/** Wyswietlenie panelu rezerwacji */
	DEPOSIT_WITHDRAW("Wpłaty/Wypłaty"),
	/** Wyswietlenie panelu administracji */
	CHECK("Stan konta"),
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
