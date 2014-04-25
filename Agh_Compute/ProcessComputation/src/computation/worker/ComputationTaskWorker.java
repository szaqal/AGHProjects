package computation.worker;

import java.util.Collection;
import java.util.List;

import computation.exceptions.ComputationRunTimeException;
import computation.model.ComputationForTask;
import computation.model.ComputationTask;
import computation.model.ComputationTaskInput;
import computation.model.ComputationTaskOutput;

/**
 * Worker operating on {@link ComputationTask}s.
 *
 * @author malczyk.pawel@gmail.com
 *
 */
public interface ComputationTaskWorker {

	/**
	 * Local interface.
	 * @author malczyk.pawel@gmail.com
	 *
	 */
	public interface ComputationTaskWorkerLocal extends ComputationTaskWorker { }

	/**
	 * Remote interface.
	 * @author malczyk.pawel@gmail.com
	 *
	 */
	public interface ComputationTaskWorkerRemote extends ComputationTaskWorker { }

	/**
	 * Finds next {@link ComputationTask} in the process graph.
	 * @param prevComputationTaskOutput previous {@link ComputationTaskOutput}
	 * @param computationId computation identifier.
	 *
	 * @return {@link ComputationTask}
	 * @throws ComputationRunTimeException exception throwed
	 */
	ComputationTask findNext(ComputationTaskOutput prevComputationTaskOutput,  int computationId) throws ComputationRunTimeException;

	/**
	 * Retrieves computation task inputs for first task in computation.
	 * @param computationId computation pk.
	 * @return list of inputs to first computation task.
	 */
	Collection<ComputationTaskInput> firstTaskInputs(int computationId);

	/**
	 * Returns computation tasks by computation id.
	 * @param computationId computation id for which computation tasks are retrieved
	 * @return list of {@link ComputationTask}
	 */
	List<ComputationTask> getByComputation(int computationId);

	/**
	 * Returns computation task by its id NOT primary key.
	 * @param computationTaskId computation task id for search.
	 * @return found {@link ComputationTask}
	 */
	ComputationTask getByTaskId(String computationTaskId);
	
	/**
	 * Return {@link ComputationForTask} entity based by compuationid and taskid.
	 * @param computationTaskId computation task id.
	 * @param computationId computation id
	 * @return computation for task
	 */
	ComputationForTask getByTaskComputationId(int computationTaskId, int computationId);

	/**
	 * Returns first computation task for particular computation.
	 * @param computationId computation pk for which first task i retrieved
	 * @return {@link ComputationTask}
	 */
	ComputationTask retrieveStartTask(int computationId);

	/**
	 * Stores computation for task entry.
	 * @param computationForTask computation for task
	 * @return return id.
	 */
	ComputationForTask store(ComputationForTask computationForTask);

	/**
	 * Stores computation task.
	 * @param computationTask computation task to save.
	 * @return id
	 */
	ComputationTask store(ComputationTask computationTask);

}
