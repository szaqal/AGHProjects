package ewpp.utils;

/**
 * Przechowuje informacja na temat stronicowania.
 * @author malczyk.pawel@gmail.com
 *
 */
public class PagingInfo {
	
	/**
	 * Biezaca strona.
	 */
	private int currentPage;
	
	/**
	 * Ilosc wierszy na stronie.
	 */
	private int rowsPerPage;
	
	/**
	 * Domy�lny konstruktor.
	 */
	private PagingInfo() { }
	
	/**
	 * Konstruktor.
	 * @param page
	 * 		biezaca strona
	 * @param rows
	 * 		ilosc wierszy na stronie
	 */
	public PagingInfo(int page, int rows){
		this();
		/*jquery numeruje strony od 1 a nie od 0*/
		currentPage = page -1; 
		rowsPerPage = rows;
	}

	/**
	 * Getter.
	 * @return wartosc pola
	 */
	public final int getCurrentPage() {
		return currentPage;
	}

	/**
	 * Getter.
	 * @return wartosc pola
	 */
	public final int getRowsPerPage() {
		return rowsPerPage;
	}
	
	/**
	 * Zwraca pierwszy rezultat dla danej strony.
	 * @return wynik
	 */
	public final int getFirstResult() {
		return currentPage * rowsPerPage;
	}
	
	/**
	 * Oblicza ilosc stron dla kolekcji.
	 * @param collectionSize wielkosc znalezionej kolekcji
	 * @return ilosc stron
	 */
	public final int getPageCount(long collectionSize) {
		long pages = (int) (collectionSize / getRowsPerPage());
		return (int) ++pages;
	}

}
