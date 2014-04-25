package ewpp.auth.workers;

/**
 * Interfejs workera rejestracji uzytkownikow.
 * @author malczyk.pawel@gmail.com
 *
 */
public interface DatabaseRegistratorWorker extends Registrator {

	/** Interfejs lokalny. */
	interface DatabaseRegistratorWorkerLocal extends DatabaseRegistratorWorker { };

	/** Interfejs zdalny. */
	interface DatabaseRegistratorWorkerRemote extends DatabaseRegistratorWorker { };
}
