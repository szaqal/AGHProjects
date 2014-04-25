package ewpp.web.ajax;

import ewpp.utils.CollectionUtils;

/**
 * Wiersz tabeli ajaxowej.
 * @author malczyk.pawel@gmail.com
 *
 * @param <T>
 */
public class AjaxRow<T> {
	
	
	/**
	 * Tabela danych wiersza.
	 */
	private String[] cell;
	
	/**
	 * Identryikator wiersza.
	 */
	private String id;
	
	/**
	 * Konstrukor.
	 */
	public AjaxRow() { }
	
	/**
	 * Konstruktor.
	 * @param cell komorka
	 * @param id wiersza
	 * @param appendEmpty
	 * 			dodaje jeden pusta komorke (na potrzeby ikon w listach) 
	 * 			TODO: to nalezy pozniej zmienic.
	 */
	public AjaxRow(String [] cell, int id, boolean appendEmpty) {
		
		if(!appendEmpty) {
			this.cell = cell;
		} else {
			this.cell = CollectionUtils.shitArrayElementsRight(cell, 1);
		}
		
		this.id = String.valueOf(id);
	}

	/**
	 * Getter.
	 * 
	 * @return dane komorki.
	 */
	public final String[] getCell() {
		return cell;
	}

	/**
	 * Getter.
	 * @return
	 * identyfikator wiersza
	 */
	public final String getId() {
		return id;
	}
	
}
