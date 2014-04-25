package ewpp.entity;

/**
 * 
 * @author malczyk
 *
 */
public interface AbstractEwppEntity {
	
	/**
	 * Zwraca dane do wyswietleia w ewentualnych tabelkach.
	 * 
	 * @return dane do wyswietlenia
	 */
	@Deprecated
	String [] getJsonData();
}
