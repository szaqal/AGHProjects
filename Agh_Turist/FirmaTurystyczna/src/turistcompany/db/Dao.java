package turistcompany.db;

import java.util.List;

import turistcompany.model.Attraction;
import turistcompany.model.Client;
import turistcompany.model.Offer;
import turistcompany.model.Reservation;
import turistcompany.model.ReservationData;
import turistcompany.model.ReservedItem;


/**
 * Ogolny interfejs definiujcy metody operujące na bazie danych.
 * @author malczyk.pawel@gmail.com
 * @author gchmiel@lkn.pl
 *
 */
public interface Dao {
	
	/**
	 * Zapisuje klienta
	 * @param client utworzony klient.
	 */
	Client storeClient(Client client);
	
	/**
	 * Zapisuje atrakcje.
	 * @param attracton utworzona atrakcja.
	 */
	Attraction storeAttraction(Attraction attracton);
	
	/**
	 * Zapisuje oferte.
	 * @param offer utworzona ofera
	 */
	Offer storeOffer(Offer offer);
	
	/**
	 * Zwraca liste wszystkich klientow.
	 * @return lista wszystkich klientow.
	 */
	List<Client> retrieveClients();
	
	/**
	 * Zwraca liste wszystkich atrakcji.
	 * @return lista wszystkich atrakcji
	 */
	List<Attraction> retrieveAttractions();
	
	/**
	 * Zwraca liste wszystkich ofert.
	 * @return lista wszystkich ofert
	 */
	List<Offer> retrieveOffers();
	
	/**
	 * Zwraca pojedyncza oferte
	 * @param id identyfikator oferty.
	 * @return wypelniony obiekt ofert
	 */
	Offer retrieveOffer(int id);
	
	/**
	 * Zwraca pojedyncza atrakcje
	 * @param id identyfikator atrakcji/
	 * @return wypelniony obiekt atrakcji.
	 */
	Attraction retrieveAttraction(int id);
		
	
	/**
	 * Wyszukuje jakis liste pewnych typow elementow w bazie.
	 * @param <T> typ poszukiwanego elemtnu
	 * @param arg wartosc ktorej w danym elemencie bedziemy wyszukiwac
	 * @param itemClass klasa obiektow bazy
	 * @return znaleziona lista elementow odpowiadajacych.
	 */
	<T>List<T> searchItems(String arg, Class<T> itemClass);	
	
	/**
	 * usuwa oferte z bazy.
	 * @param id identyfikator oferty do usuniecia
	 */
	void deleteOffer(int id);
	
	/**
	 * Usuwa atrakcje z bazy
	 * @param id identyfikator atrakcji w bazie
	 */
	void deleteAttraction(int id);

	/**
	 * Usuwa klienta z bazy.
	 * @param id identyfikator klienta w bazie.
	 */
	void deleteClient(int id);
	
	/**
	 * Usuwa klienta z bazy (z uzyciem deleteRow)
	 * @param id identyfikatory klienta w bazie
	 */
	void deleteClientRow(int id);
	
	/**
	 * Zwapisuje rezerwacje (Uzyta transakcja).
	 * @param reservation
	 * @param clientId identyfiaktor klienta ktory rezerwuje
	 */
	void storeReservation(Reservation reservation, String clientId);
	
	/**
	 * wczytuje i wykonuje plik z sql.
	 * @param value
	 */
	void executeScript(String value);
	
	/**
	 * Zwraca wszystki istniejace rezerwacje.
	 * @return lista rezerwacji.
	 */
	List<ReservationData> retrieveReservations();
	
	/**
	 * Zwraca liste zrezerwowany obiektow dla pojedynczej rezerwacji.
	 * @param reservationId id rezerwacji.
	 * @return lista zarezerwowanych obiektow.
	 */
	List<ReservedItem> reservedItems(int reservationId);
	
	/**
	 * Uaktualnia dane klienta
	 * @param client uaktualniany klient
	 */
	void updateClient(Client client);
	
	/**
	 * Uaktualnia oferte.
	 * @param offer uaktualniana oferta
	 */
	void updateOffer(Offer offer);
	
	/**
	 * Uaktualnia atrakcje
	 * @param attraction uaktualniana atrakcja
	 */
	void updateAttractions(Attraction attraction);
	
}
