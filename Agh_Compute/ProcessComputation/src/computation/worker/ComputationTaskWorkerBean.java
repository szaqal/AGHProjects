package computation.worker;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import computation.exceptions.ComputationRunTimeException;
import computation.model.ComputationForTask;
import computation.model.ComputationTask;
import computation.model.ComputationTaskInput;
import computation.model.ComputationTaskOutput;
import computation.worker.ComputationTaskWorker.ComputationTaskWorkerLocal;
import computation.worker.ComputationTaskWorker.ComputationTaskWorkerRemote;

import core.workers.AbstractWorkerBean;

/**
 * Worker that operates on {@link ComputationTask}s.
 * @author malczyk.pawel@gmail.com
 *
 */
@Stateless
@Local(ComputationTaskWorkerLocal.class)
@Remote(ComputationTaskWorkerRemote.class)
public class ComputationTaskWorkerBean extends AbstractWorkerBean implements ComputationTaskWorker {

	/**
	 * Message.
	 */
	private static final String COMPUTATION_TASK_NOT_FOUND_FOR_TASK_ID = "Computation task not found for task ID {}";

	/**
	 * Named query parameter name.
	 */
	private static final String CFT_ID = "cftId";

	/**
	 * Logger.
	 */
	private static final Logger LOG = LoggerFactory.getLogger(ComputationTaskWorkerBean.class);


	/**
	 * Constructor.
	 */
	public ComputationTaskWorkerBean() {
		super();
	}

	/** {@inheritDoc} */
	@Override
	public ComputationTask findNext(
			ComputationTaskOutput prevComputationTaskOutput,
			int computationId) throws ComputationRunTimeException {

		ComputationTask result = null;
		String query = "SELECT ct.nextInput.computationTask FROM ComputationTransition AS ct WHERE ct.computation.uniqueId=:cid AND ct.previousOutput.uniqueId=:prevOutId";
		Query qry = getEntityManager().createQuery(query);
		qry.setParameter("cid", computationId);
		qry.setParameter("prevOutId", prevComputationTaskOutput.getUniqueId());
		//XXX: what if more
		qry.setMaxResults(1);
		result = getSingleResult(qry, ComputationTask.class);
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public Collection<ComputationTaskInput> firstTaskInputs(int computationId) {
		return retrieveStartTask(computationId).getInputs();
	}

	/** {@inheritDoc} */
	@Override
	public List<ComputationTask> getByComputation(int computationId) {
		Query query = getEntityManager().createNamedQuery(ComputationForTask.BY_COMP_ID);
		query.setParameter(CFT_ID, computationId);
		List<ComputationForTask> forTasks = getResultList(query, ComputationForTask.class);
		List<ComputationTask> result = new ArrayList<ComputationTask>();
		for(ComputationForTask computationForTask : forTasks) {
			result.add(computationForTask.getComputationTask());
		}
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public ComputationTask getByTaskId(String computationTaskId) {
		ComputationTask computationTask = null;
		try {
			Query query = getEntityManager().createNamedQuery(ComputationTask.BY_TASK_ID);
			query.setParameter("taskId", computationTaskId);
			computationTask = getSingleResult(query, ComputationTask.class);
		} catch (NoResultException e) {
			LOG.warn(COMPUTATION_TASK_NOT_FOUND_FOR_TASK_ID, computationTaskId);
		}
		return computationTask;
	}

	/** {@inheritDoc} */
	@Override
	public ComputationTask retrieveStartTask(int computationId) {
		Query query = getEntityManager().createNamedQuery(ComputationForTask.FIRST_BY_COMP_ID);
		query.setParameter(CFT_ID, computationId);
		ComputationForTask computationForTask = getSingleResult(query, ComputationForTask.class);
		return computationForTask.getComputationTask();
	}

	/** {@inheritDoc} */
	@Override
	public ComputationForTask store(ComputationForTask computationForTask) {
		return save(computationForTask);
	}


	/** {@inheritDoc} */
	@Override
	public ComputationTask store(ComputationTask computationTask) {
		return save(computationTask);
	}

	/** {@inheritDoc} */
	@Override
	public ComputationForTask getByTaskComputationId(int computationTaskId, int computationId) {
		ComputationForTask computationTask = null;
		try {
			Query query = getEntityManager().createNamedQuery(ComputationForTask.BY_COMP_TASK_ID);
			query.setParameter("compTaskId", computationTaskId);
			query.setParameter("compId", computationId);
			computationTask = getSingleResult(query, ComputationForTask.class);
		} catch (NoResultException e) {
			LOG.warn(COMPUTATION_TASK_NOT_FOUND_FOR_TASK_ID, computationTaskId);
		}
		return computationTask;
	}

}
