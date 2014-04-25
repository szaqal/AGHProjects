package computation.worker;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import computation.engine.RunableComputation;
import computation.exceptions.ComputationRunTimeException;
import computation.model.ComputationTask;
import computation.model.ComputationTransition;
import computation.model.ComputingNode;

/**
 * Interface for worker that is performing opertaion.
 * 
 * @author malczyk.pawel@gmail.com
 *
 */
public interface ComputationPerformerWorker {
	

	/**
	 * Sets up initial values for computation.
	 * @param initialValues values.
	 * @param initialSettings TODO
	 */
	void setup(Map<String, Number> initialValues, Map<String, String> initialSettings);
	
	/**
	 * Releases node.
	 * @param node releases node
	 */
	void releaseNode(ComputingNode node);
	
	/**
	 * Acquires node.
	 * @return acquired computing node or exception is throwed
	 */
	ComputingNode acquireNode();
	
	/**
	 * Prepares input map for remove invocation.
	 * @param computationTask that is about to exectute.
	 * @return filled map with input values
	 */
	HashMap<String, Serializable> prepareInputs(ComputationTask computationTask);
	
	/**
	 * Prepares outputs map for remote invocation.
	 * @param computationTask computation task
	 * @return filled map with output values.
	 */
	HashMap<String, Serializable> prepareOutputs(ComputationTask computationTask);
	
	/**
	 * Performs computation and returns {@linkplain ComputationResult}.
	 * @param computationId computation identifier that will be performed
	 * @param userId user that invoked computation
	 * @return saved result id.
	 * @throws ComputationRunTimeException error
	 */
	int doComputation(int computationId, int userId) throws ComputationRunTimeException; 
	
	/**
	 * Creates {@linkplain RunableComputation}.
	 * @param computationTasks collection of tasks.
	 * @param tansitions collection of transitions.
	 * @throws ComputationRunTimeException error
	 * @return runnableTask
	 */
	RunableComputation createRunnableComputation(Collection<ComputationTask> computationTasks, 
			Collection<ComputationTransition> tansitions) throws ComputationRunTimeException;
	
	/**
	 * Creates {@linkplain RunableComputation}. from computation
	 * @param computationId computation id.
	 * @throws ComputationRunTimeException error
	 * @return runnable task
	 */
	RunableComputation createRunnableComputation(int computationId) throws ComputationRunTimeException;
	
	/**
	 * Local interface.
	 * @author malczyk.pawel@gmail.com
	 *
	 */
	public interface ComputationPerformerWorkerLocal extends ComputationPerformerWorker  { }
	
	
	/**
	 * Local interface.
	 * @author malczyk.pawel@gmail.com
	 *
	 */
	public interface ComputationPerformerWorkerRemote extends ComputationPerformerWorker  { }

}
