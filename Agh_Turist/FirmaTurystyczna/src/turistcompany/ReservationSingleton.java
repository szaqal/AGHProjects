package turistcompany;

import turistcompany.model.Reservation;

/**
 * Zawiera tworzoną rezerwacje.
 * 
 * @author malczyk.pawel@gmail.com
 * @author gchmiel@lkn.pl
 *
 */
public class ReservationSingleton {
	
	private static ReservationSingleton instance;
	private static Reservation currentReservation;
	
	/** Prywatny konstruktor*/
	private ReservationSingleton() { }
	
	/**
	 * Zwraca instancje singletona
	 * @return instancja {@link ReservationSingleton}
	 */
	public static ReservationSingleton getInstance() {
		if (instance == null) {
			instance = new ReservationSingleton(); 
			return instance;
		} else {
			return instance;
		}
	}
	
	/** 
	 * Zwraca instancje single
	 * @return instancje {@link Reservation}
	 */
	public Reservation getCurrentReservation() {
		if (currentReservation == null) {
			currentReservation = new Reservation();
			return currentReservation;
		} else {
			return currentReservation;
		}
	}
}
