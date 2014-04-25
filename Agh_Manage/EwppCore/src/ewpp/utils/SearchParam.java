package ewpp.utils;

import javax.persistence.Query;

/**
 * Interfejs parametru wyszukiwania.
 * @author malczyk.pawel@gmail.com
 *
 */
public interface SearchParam {
	
	/**
	 * Wstawia odpowiednie danee do zapytania.
	 * @param result uzueplnione zapytanie do EJB
	 */
	void processQueryValues(Query result);
}
