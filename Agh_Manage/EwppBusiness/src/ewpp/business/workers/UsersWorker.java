package ewpp.business.workers;

import java.util.List;

import ewpp.auth.entity.User;
import ewpp.utils.PagingInfo;
import ewpp.utils.SearchParameters;

/**
 * Worker wyciagajacy i wyszukujacy uzytkownikow.
 * @author malczyk
 *
 */
public interface UsersWorker {

	/**
	 * Zwraca liste wszystkich uzytkownikow.
	 * 
	 * @param pagingInfo
	 *            dane stronicowania gdy null wtedy sa ignorowane i leci
	 *            wszystko
	 * @return lista uzytkownik�w
	 */
	List<User> retrieveUsers(PagingInfo pagingInfo);
	
	/**
	 * Zwraca liste uzytkownikow po parametrach wyszukiwania.
	 * @param searchParameters
	 * 			parametry wyszukiwania.
	 * @param pagingInfo
	 * 			dane stronicowania
	 * @return
	 * 			lista uzytkownik�w.
	 */
	List<User> retrieveUsers(SearchParameters<User> searchParameters, PagingInfo pagingInfo);
	
	/**
	 * Zwraca liste wszystkich uzytkownikow.
	 * wtedy sa ignorowane i leci wszystko
	 * @return lista uzytkownik�w
	 */
	List<User> retrieveUsers();

	/**
	 * Zwraca liste wykladowcow.
	 * @return lista wykladowcow
	 */
	List<User> retrieveLecturers();

	/**
	 * Zwraca liste studentow.
	 * @param pagingInfo info o stronicowaniu
	 * @return lista studentow
	 */
	List<User> retrieveStudents(PagingInfo pagingInfo);
	
	
	/**
	 * Zwraca ilosc znaleionych uzytkownik� w systemie.
	 * 
	 * @param searchParam
	 *            parametry wyszukiwania
	 * @return ilosc znlezionych rezultatow
	 */
	Long retrieveStudentsCount(SearchParameters<User> searchParam);
	
	/**
	 * Zwraca ilosc studentow.
	 * @return ilosc stidentow
	 */
	Long retrieveStudentsCount();

	/**
	 * Zwraca uztkownika.
	 * @param userId identyfikator uzytkownika
	 * @return encja uztkownika
	 */
	User retrieveUser(int userId);
	
	/**
	 * Zwraca ilo�� uzytkonik�w.
	 * @return ilo�� u�ytkownikow.
	 */
	Long retrieveUsersCount();
	
	/**
	 * Zwraca ilosc znaleionych uzytkownik� w systemie.
	 * 
	 * @param searchParam
	 *            parmaetry wyszukiwania
	 * @return ilosc znlezionych rezultatow
	 */
	Long retrieveUsersCount(SearchParameters<User> searchParam);

	/**
	 * Zapisuje uzytkownika.
	 * @param user encja uzytkownika
	 * uzytkownik.
	 */
	void saveUser(User user);

	/**
	 * Usuwa uzytkownika z bazy.
	 * @param userId id usera
	 */
	void removeUser(int userId);


	/**
	 * Interfejs lokalny.
	 * @author malczyk.pawel@gmail.com
	 *
	 */
	interface UsersWorkerLocal extends UsersWorker { };


	/**
	 * Interfejs zdalny.
	 * @author malczyk.pawel@gmail.com
	 *
	 */
	interface UsersWorkerRemote extends UsersWorker { };

}
