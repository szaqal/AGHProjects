package core.workers;

/**
 * Invokes operation on remote servers. 
 * 
 * @author malczyk.pawel@gmail.com
 *
 */
public interface OperationInvokerWorker {
	
	/**
	 * Invokes method on remote server.
	 * @param clazz api interface cass
	 * @param url node url
	 * @param <T> remote object type.
	 * 
	 * @return result.
	 */
	<T> T getObject(Class<T> clazz, String url);
	
	
	/**
	 * Local interface.
	 * @author malczyk.pawel@gmail.com
	 *
	 */
	public interface OperationInvokerWorkerLocal extends OperationInvokerWorker { }
	
	/**
	 * Remote interface.
	 * @author malczyk.pawel@gmail.com
	 *
	 */
	public interface OperationInvokerWorkerRemote extends OperationInvokerWorker { }
	
	
}
