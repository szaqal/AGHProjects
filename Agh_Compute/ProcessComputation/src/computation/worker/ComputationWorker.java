package computation.worker;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import computation.model.Computation;
import computation.model.ComputationPackage;
import computation.model.ComputationSetting;
import computation.model.ComputationTask;
import computation.model.ComputationTaskInput;
import computation.model.ComputationTaskOutput;
import computation.model.ComputationTransition;

import core.utils.PagingInfo;

/**
 * Worker that provides operation based on computation objects.
 * 
 * @author <a href="mailto:malczyk.pawel@gmail.com>malczyk.pawel@gmail.com</a>
 *
 */
public interface ComputationWorker {

	/**
	 * Creates computation process representation from {@linkplain ComputationPackage}.
	 * @param computationPackage computation package.
	 * @return identifier of created process
	 */
	int createComputation(ComputationPackage computationPackage); 
	
	/**
	 * Creates computation process from configuration file only.
	 * @param computationConfigurationId {@link ComputationConfiguration} 
	 * @return  identifier of created process
	 */
	int createComputationFromConfig(int computationConfigurationId);
	
	/**
	 * Creates computation process from {@linkplain ComputationPackage} specified by it's id.
	 * @param computationPackageId {@linkplain ComputationPackage} id;
	 * @return identifier of created process
	 */
	int createComputation(int computationPackageId);
	
	/**
	 * Retrieves computation by it's computation id property NOT uniqueId.
	 * @param computationId computtionId.
	 * @return computation
	 */
	Computation retrieveComputationByComputationId(String computationId);
	
	/**
	 * Runs specified by process id.
	 * @param processId process identifier that is about to run
	 * @return some kind of result.
	 */
	Serializable runProcess(String processId);
	
	/**
	 * Retrieves computations.
	 * @param pagingInfo pageingInfo.
 	 * @return collection of computations
	 */
	List<Computation> retrieveComputations(PagingInfo pagingInfo);
	
	/**
	 * Retrieves computation by id.
	 * @param computationId computation identifier
	 * @return computation
	 */
	Computation retrieveComputation(int computationId);
	
	/**
	 * Retrieves number of existing computations.
	 * @return computations count.
	 */
	Long retrieveComputationsCount();
	
	/**
	 * Retrieves computation tasks for computation.
	 * @param computationId computationsID
	 * @return collection of tasks
	 */
	Collection<ComputationTask> retieveTasksForComputation(int computationId);
	
	/**
	 * Retrieves all task inputs for particular computation.
	 * @param computationId computation identifier.
	 * @return collection of tasks.
	 */
	Collection<ComputationTaskInput> retrieveTaskInputs(int computationId);
	
	/**
	 * Returns collection of transitions for computation.
	 * @param computationId computationsID
	 * @return collection of transitions.
	 */
	Collection<ComputationTransition> retrieveTransitionsForComputation(int computationId);
	
	
	/**
	 * Retrieves computation task output by taskid and computation task id.
	 * @param taskId task id (not PK)
	 * @param inputId input id (not PK)
	 * @return input.
	 */
	ComputationTaskInput retrieveByTask(String inputId, String taskId);
	
	/**
	 * Retrieves computation task output by taskid and computation task id.
	 * @param taskId task id (not PK)
	 * @param inputId input id (not PK)
	 * @return output.
	 */
	ComputationTaskOutput rerieveByTask(String inputId, String taskId);
	
	/**
	 * Retrieves computation settings for computation.
	 * @param computationId computationId.
	 * @return collection of {@link ComputationSetting}
	 */
	List<ComputationSetting> retrieveSettings(int computationId);
	
	
	
	/**
	 * Remote interface.
	 * @author <a href="mailto:malczyk.pawel@gmail.com>malczyk.pawel@gmail.com</a>
	 *
	 */
	public interface ComputationWorkerRemote extends ComputationWorker { }
	
	/**
	 * Local interface.
	 * @author <a href="mailto:malczyk.pawel@gmail.com>malczyk.pawel@gmail.com</a>
	 *
	 */
	public interface ComputationWorkerLocal extends ComputationWorker { }
}
