package ewpp.document.workers;

/**
 *
 * Worker dokumentow.
 * @author malczyk.pawel@gmail.com
 *
 */
public interface DocumentWorker {

	/**
	 * Interfejs lokalny.
	 * @author malczyk.pawel@gmail.com
	 *
	 */
	interface DokumentWorkerLocal extends DocumentWorker { };

	/**
	 * Interfejs zdalny.
	 * @author malczyk.pawel@gmail.com
	 *
	 */
	interface DoumentWorkerRemote extends DocumentWorker { };
}
