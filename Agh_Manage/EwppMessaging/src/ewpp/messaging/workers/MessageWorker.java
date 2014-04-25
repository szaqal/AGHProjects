package ewpp.messaging.workers;

import java.util.List;

import ewpp.auth.entity.User;
import ewpp.messaging.entity.Message;
import ewpp.utils.PagingInfo;
import ewpp.utils.SearchParameters;

/**
 * Worker wiadomosci wysylanie itp..
 * @author malczyk.pawel@gmail.com
 *
 */
public interface MessageWorker {

	/**
	 * Wysyla wiadomosci.
	 * @param message wiadomosc
	 * @param sender identyfikator nadawcy
	 * @param recipient identyfiaktor odbiorcy
	 */
	void sendMessage(Message message, User recipient, User sender);

	/**
	 * Wyciaga wiadomosc z bazy danych.
	 *
	 * @param messageId
	 *            identyfikator wiadomosci
	 * @return odnaleziona wiadomosc
	 */
	Message retrieveMessage(int messageId);

	/**
	 * Zwraca liste przychodzacych wiadomosci dla uzytkownika.
	 *
	 * @param user
	 *            identyfikator uzytkownika
	 * @return lista jego przychodzacych wiadomosci
	 */
	List<Message> retrieveIncomingMessages(User user);
	
	
	/**
	 * Zwraca liste przychodzacych wiadomosci dla uzytkownika (z uwzglednieniem stronicowania).
	 *
	 * @param user
	 *            identyfikator uzytkownika
	 * @param pagingInfo
	 * 			  informacje o stronicowaniu           
	 * @return lista jego przychodzacych wiadomosci
	 */
	List<Message> retrieveIncomingMessages(User user, PagingInfo pagingInfo);
	
	/**
	 * Zwraca liste przychodzacych wiadomosci dla uzytkownika (z uwzglednieniem stronicowania).
	 * @param pagingInfo
	 * 			  informacje o stronicowaniu       
	 * @param searchParams
	 * 			  parametry wyszukiwnia    
	 *
	 * @return lista jego przychodzacych wiadomosci
	 */
	List<Message> retrieveIncomingMessages(PagingInfo pagingInfo, SearchParameters<Message> searchParams);

	/**
	 * Zwraca liste wyslanych wiadomosci dla uzytkownika (z uwzglednieniem stronicowania).
	 *
	 * @param user
	 *            identyfikator uzytkownika
	 * @param pagingInfo
	 * 			  informacje o stronicowaniu           
	 * @return lista jego przychodzacych wiadomosci
	 */
	List<Message> retrieveOutgoingMessages(User user, PagingInfo pagingInfo);
	
	/**
	 * Zwraca liste wyslanych wiadomosci dla uzytkownika (z uwzglednieniem stronicowania).
	 * @param pagingInfo
	 * 			  informacje o stronicowaniu  
	 * @param searchParams
	 * 			  parametry wyszukiwania         
	 *
	 * @return lista jego przychodzacych wiadomosci
	 */
	List<Message> retrieveOutgoingMessages(PagingInfo pagingInfo, SearchParameters<Message> searchParams);
	

	/**
	 * Zwraca liste wychodzacych wiadomosci.
	 *
	 * @param user
	 *            identyfikator uzytkownika
	 * @return lista jego wychodzacych wiadomosci
	 */
	List<Message> retrieveOutgoingMessages(User user);
	
	
	/**
	 * Zlicza ilosc przychodzacych wiadomosci.
	 * 
	 * @param user
	 * 			identyfikator uzytkownika.
	 * @return
	 * 			ilosc przychodzacych wiadomosc
	 */
	Long retrieveIncomingMessagesCount(User user);
	
	/**
	 * Zlicza ilosc przychodzacych wiadomosci.
	 * @param searchParams
	 * 			parametry wyszukiwania
	 * 
	 * @return
	 * 			ilosc przychodzacych wiadomosc
	 */
	Long retrieveIncomingMessagesCount(SearchParameters<Message> searchParams);
	
	
	/**
	 * Zlicza ilosc wychodzacych wiadomosci.
	 * 
	 * @param user
	 * 			identyfikator uzytkownika.
	 * @return
	 * 			ilosc przychodzacych wiadomosc
	 */
	Long retrieveOutgoingMessagesCount(User user);
	
	/**
	 * Zlicza ilosc wychodzacych wiadomosci.
	 * @param searchParams
	 * 			parametry wyszukiwania 
	 * 
	 * @return
	 * 			ilosc przychodzacych wiadomosc
	 */
	Long retrieveOutgoingMessagesCount(SearchParameters<Message> searchParams);

	/**
	 * Usuwa wiadomosc tzn oznacza jako usunietam dla albo nadawcy albo odbiorcy
	 * zaleznie od tego czy jest to wiadomosc wyslana czy odbrana.
	 *
	 * @param messageId identyfikator wiadomosci
	 * @param loggedUser identyfikator zalogowanego uzytkownika
	 * @param mode TODO
	 *
	 */
	void removeMessage(int messageId, User loggedUser, String mode);
	
	/**
	 * Oznacza wiadomosc jako przeczytana.
	 * @param messageId identyfikator wiadomosci
	 */
	void markMessageAsReaded(int messageId);


	/** Interfejs lokalny. */
	interface MessageWorkerLocal extends MessageWorker {};

	/** Interfejs zdalny. */
	interface MessageWorkerRemote extends MessageWorker {};

}
