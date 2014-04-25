package ewpp.messaging.workers;

/**
 * Interfejs zawiera metody przypomnajace.
 * @author malczyk.pawel@gmail.com
 *
 */
public interface NotifierWorker {

	/** Wysyla powiadomienie. */
	void sendNotification();

	/** Interfejs lokalny. */
	interface NotifierWorkerLocal extends NotifierWorker {
	};

	/** Interfejs zdalny. */
	interface NotifierWorkerRemote extends NotifierWorker {
	};
}
