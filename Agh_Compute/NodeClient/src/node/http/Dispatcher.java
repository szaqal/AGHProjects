package node.http;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import api.computation.ComputationContext;

/**
 * Dispatches requests.
 * 
 * @author malczyk.pawel@gmail.com
 *
 */
public interface Dispatcher {
	
	/**
	 * Dispatches request.	 
	 * @param data inputs data
	 * @return result
	 */
	byte [] dispatch(byte [] data);
	
	
	/**
	 * Performs computation.
	 * @param computation computation name 
	 * @param inputs computation task input values
	 * @param outputs map of outputs
	 * @param computationContext computation context
	 * @return result of computation (taskId)
	 */
	byte [] compute(String computation, byte [] inputs, byte [] outputs, ComputationContext computationContext);
	
	/**
	 * Performs computation.
	 * @param computation computation name 
	 * @param inputs computation task input values
	 * @param outputs map of outputs
	 * @param computationContext computation context
	 * @return result of computation (taskId)
	 */
	HashMap<String, Serializable> compute(String computation, Map<String, Serializable> inputs, HashMap<String, Serializable> outputs, ComputationContext computationContext);
	
	
	/**
	 * Returns computations list.
	 * @return return string representation of computations list.
	 */
	String computationList();
}
