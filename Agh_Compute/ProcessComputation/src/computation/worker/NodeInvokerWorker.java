package computation.worker;

import java.io.Serializable;
import java.util.HashMap;

import api.computation.ComputationContext;

import computation.exceptions.ComputationRunTimeException;
import computation.model.ComputingNode;
import computation.model.PerformedComputation;

/**
 * Handles core operations based.
 * @author malczyk.pawel@gmail.com
 *
 */
public interface NodeInvokerWorker {

	/**
	 * Invokes operation on node server.
	 * 
	 * @param node
	 *            registered computing node.
	 * @param inputs
	 *            task inputs
	 * @param outputs
	 *            task outputs
	 * @param taskName
	 *            task name to be computed
	 * @param performedComputation
	 *            needed in order to create computation log
	 * @param computationContext
	 *            current compuation context.
	 * @throws ComputationRunTimeException
	 *             that may be throwed
	 * @return xml result.
	 */
	HashMap<String, Serializable> invoke(ComputingNode node, HashMap<String, Serializable> inputs, HashMap<String, Serializable> outputs, String taskName, PerformedComputation performedComputation, ComputationContext computationContext) throws ComputationRunTimeException;
	
	
	/**
	 * Local interface.
	 * @author malczyk.pawel@gmail.com
	 *
	 */
	public interface NodeInvokerWorkerLocal extends NodeInvokerWorker { }
	
	/**
	 * Remote interface.
	 * @author malczyk.pawel@gmail.com
	 *
	 */
	public interface NodeInvokerWorkerRemote extends NodeInvokerWorker { }
	
}
