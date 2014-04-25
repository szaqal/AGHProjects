package ewpp.workers;

/**
 * Generuje unikalne identyfikatory dla encji.
 * @author malczyk.pawel@gmail.com
 *
 */
public interface IdGeneratorWorker {

	/**
	 * Generuje identyfikator.
	 * @return identyfikator
	 */
	String generate();

	/** Interfejs lokalny. */
	interface IdGeneratorWorkerLocal extends IdGeneratorWorker { };

}
