package ewpp.auth.workers;

import ewpp.auth.entity.User;

/**
 * Przeprowadza rejestracje nowego uzytkownika.
 * @author malczyk.pawel@gmail.com
 *
 */
public interface Registrator {

	/**
	 * Zapisuje nowego uzytkownika.
	 * @param user przygotowany user
	 * @return powodzenie/brak powodzenia
	 */
	boolean storeNewUser(User user);

}
