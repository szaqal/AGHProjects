package ewpp.web.ajax;


/**
 * Pojedyncza strona zwracana do list ajaxowych.
 * @author malczyk.pawel@gmail.com
 * @param <T> typ elementu zwracanego na stronie
 */
public class AjaxPage<T> {
	
	/**
	 * Wiersze.
	 */
	private AjaxRow<T> [] rows;
	
	/**
	 * Biezaca strona.
	 */
	private String page;
	
	/**
	 * Wszystki strony.
	 */
	private int total;
	
	/**
	 * Wszystki znalezione elementy.
	 */
	private String records;
	
	/**
	 * Domy�lny konstruktor.
	 */
	public AjaxPage() {
		
	}
	
	/**
	 * Konstruktor.
	 * @param arr elementy na stronie
	 * @param totalPages ca�kowita ilosc stron
	 * @param foundRecords ilosc znalezionych rekord�w.
	 * @param page numer strony
	 */
	public AjaxPage(AjaxRow<T> [] arr, int page, int totalPages, long foundRecords) {
		rows = arr;
		total = totalPages;
		records = String.valueOf(foundRecords);
		this.page = String.valueOf(page);
	}

	/**
	 * Getter.
	 * 
	 * @return kolekcja elementow na stronie.
	 */
	public final AjaxRow<T> [] getRows() {
		return rows;
	}

	/**
	 * Getter.
	 * @return
	 * 	wartosc pola
	 */
	public final String getPage() {
		return page;
	}

	/**
	 * Getter.
	 * @return
	 * 	wartosc pola
	 */
	public final int getTotal() {
		return total;
	}

	/**
	 * Getter.
	 * @return
	 * 	wartosc pola
	 */
	public final String getRecords() {
		return records;
	}
}
