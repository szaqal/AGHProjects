package computation.worker;

/**
 * Performs some operations on variables.
 * 
 * @author malczyk.pawel@gmail.com
 *
 */
public interface VariableWorker {
	
	
	/**
	 * Deserializes XSream serialized object to particular object.
	 * @param <T> type of object that result will be casted to
	 * @param serialized XML doc containing serialized object.
	 * @param clazz return object type.
	 * @return deserialized object.
	 */
	<T> T deserialize(String serialized, Class<T> clazz);
	
	
	/**
	 * Local interface.
	 * 
	 * @author malczyk.pawel@gmail.com
	 *
	 */
	public interface VariableWorkerLocal extends VariableWorker { }
	
	
	/**
	 * Remote interface.
	 * 
	 * @author malczyk.pawel@gmail.com
	 *
	 */
	public interface VariableWorkerRemote extends VariableWorker { }
	
}
