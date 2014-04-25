package computation.worker;

import java.util.Map;

/**
 * Handles invoking of computations.
 * 
 * @author malczyk.pawel@gmail.com
 *
 */
public interface ComputationInvokerWorker {
	
	/**
	 * Sets up initial values for computation.
	 * @param initialValues values.
	 */
	void setup(Map<String, Number> initialValues);
	
	/**
	 * Sets up initial values for computation.
	 * @param initialSettings values.
	 */
	void setupSettings(Map<String, String> initialSettings);
	
	/**
	 * Performs computation and returns {@linkplain ComputationResult}.
	 * @param <T> type of object being wrapped
	 * @param clazz class of object being returned
	 * @param computationId computation identifier that will be performed
	 * @param recipientEmail email address of recipient to which result will be send
	 * @param ownerId user who triggered computation.
	 */
	<T> void doComputation(Class<T> clazz, String computationId, String recipientEmail, String ownerId);
	
	/**
	 * Local interface.
	 * @author malczyk.pawel@gmail.com
	 *
	 */
	public interface ComputationInvokerWorkerLocal extends ComputationInvokerWorker { }
	
	/**
	 * Remote interface.
	 * @author malczyk.pawel@gmail.com
	 *
	 */
	public interface ComputationInvokerWorkerRemote extends ComputationInvokerWorker { }
}
