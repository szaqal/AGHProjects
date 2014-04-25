package ewpp.auth.workers;

import ewpp.auth.entity.User;

/**
 * Przperowadza uwiezytelnienie i autoryzacje.
 * @author malczyk.pawel@gmail.com
 */
public interface Authenticator {

	/**
	 * Przeprowadza uwierzytelnianie.
	 * @param login
	 * 			wpropwadzony login
	 * @param passwd
	 * 			wpropwadzone haslo
	 * @param method
	 * 			metoda uwiezytelnienia
	 * @return Zalowany uzytkownik
	 */
	User authenticate(String login, String passwd, AuthenticationMethod method);

}
