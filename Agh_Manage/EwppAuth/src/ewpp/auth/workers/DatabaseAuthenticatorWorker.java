package ewpp.auth.workers;

/**
 * Implementacja interfejsu przeprowadza autentykacje
 * w oparciu o relazyjna baze danych.
 * @author malczyk.pawel@gmail.com
 *
 */
public interface DatabaseAuthenticatorWorker extends Authenticator {

	/** Interfejs lokalny. */
	interface DatabaseAuthenticatorWorkerLocal
					extends DatabaseAuthenticatorWorker { };

	/** Interfejs zdalny. */
	interface DatabaseAuthenticatorWorkerRemote
					extends DatabaseAuthenticatorWorker { };
}
