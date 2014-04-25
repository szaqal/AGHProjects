package computation.worker;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.jar.JarEntry;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import computation.exceptions.ComputationCreationException;
import computation.model.Computation;
import computation.model.ComputationConfiguration;
import computation.model.ComputationForTask;
import computation.model.ComputationPackage;
import computation.model.ComputationSetting;
import computation.model.ComputationTask;
import computation.model.ComputationTaskInput;
import computation.model.ComputationTaskOutput;
import computation.model.ComputationTransition;
import computation.worker.ComputationConfgurationWorker.ComputationConfgurationWorkerLocal;
import computation.worker.ComputationCreateWorker.ComputationCreateWorkerLocal;
import computation.worker.ComputationPackageWorker.ComputationPackageWorkerLocal;
import computation.worker.ComputationTaskWorker.ComputationTaskWorkerLocal;
import computation.worker.ComputationWorker.ComputationWorkerLocal;
import computation.worker.ComputationWorker.ComputationWorkerRemote;

import core.model.FileContent;
import core.utils.PagingInfo;
import core.workers.AbstractWorkerBean;
import core.workers.JarWorker.JarWorkerLocal;


/**
 * Worker implementation.
 * @author  <a href="mailto:malczyk.pawel@gmail.com>malczyk.pawel@gmail.com</a>
 */
@Stateless
@Local(ComputationWorkerLocal.class)
@Remote(ComputationWorkerRemote.class)
public class ComputationWorkerBean extends AbstractWorkerBean implements ComputationWorker {
	
	/**
	 * Task id search parameter.
	 */
	private static final String TASK_ID = "taskId";

	/**
	 * Id search parameter.
	 */
	private static final String ID = "id";

	/**
	 * Logger.
	 */
	private static final Logger LOG = LoggerFactory.getLogger(ComputationWorkerBean.class);
	
	/**
	 * Computation worker.
	 * @uml.property  name="computationPackageWorker"
	 * @uml.associationEnd  
	 */
	@EJB
	private ComputationPackageWorkerLocal computationPackageWorker;
	
	/**
	 * Computation create worker.
	 * @uml.property  name="computationCreateWorker"
	 * @uml.associationEnd  
	 */
	@EJB
	private ComputationCreateWorkerLocal computationCreateWorker;
	
	/**
	 * Jar worker.
	 * @uml.property  name="jarWorkerLocal"
	 * @uml.associationEnd  
	 */
	@EJB
	private JarWorkerLocal jarWorkerLocal;
	
	/**
	 * Computation confiuration worker.
	 * @uml.property  name="computationConfgurationWorker"
	 * @uml.associationEnd  
	 */
	@EJB
	private ComputationConfgurationWorkerLocal computationConfgurationWorker;
	
	/**
	 * Computation taske worker.
	 * @uml.property  name="computationTaskWorker"
	 * @uml.associationEnd  
	 */
	@EJB
	private ComputationTaskWorkerLocal computationTaskWorker;

	
	/**
	 * Constructor.
	 */
	public ComputationWorkerBean() { }

	/** {@inheritDoc} */
	@Override
	public int createComputation(ComputationPackage computationPackage) {
		int result = 0;
		FileContent fileContent = computationPackage.getContent();
		byte [] fileData = fileContent.getContent();
		
		try{ 
			JarEntry config = jarWorkerLocal.retrieveJarEntry(fileData, ComputationPackage.CONFIG_FILENAME);
			Computation computation = computationCreateWorker.processConfigFile(config, jarWorkerLocal.createFromBytes(fileData));
			if(computation ==null) {
				throw new ComputationCreationException();
			}
			
			Computation existing = retrieveComputationByComputationId(computation.getComputationId());
			if(existing!=null) {
				return result;
			}
			
			
			for(ComputationTransition computationTransition: computation.getComputationTransitions()) {
				String nextInputId = computationTransition.getNextInputId();
				String prevOutId = computationTransition.getPreviousOutputId();
				for(ComputationTask task : computation.getComputationTasks()) {
					ComputationTaskInput input = task.getInputById(nextInputId);
					ComputationTaskOutput output = task.getOutputById(prevOutId);
					if(input!=null) {
						computationTransition.setNextInput(input);
					}
					if(output!=null) {
						computationTransition.setPreviousOutput(output);
					}
				}
			}
			
			result = save(computation).getUniqueId();
			
			for(ComputationTask computationTask : computation.getComputationTasks()) {
				ComputationForTask computationForTask = new ComputationForTask();
				computationForTask.setComputation(computation);
				computationForTask.setComputationTask(computationTask);
				save(computationForTask);
			}
			
			
		} catch (ComputationCreationException e) {
			LOG.warn(e.getMessage());
		}
		
		return result;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public int createComputationFromConfig(int computationConfigurationId) {
		ComputationConfiguration config = computationConfgurationWorker.retrieveById(computationConfigurationId);
		byte[]  data = config.getConfigurationFile().getContent();
		Computation computation = computationCreateWorker.createComputation(data);
		
		if(computation==null) {
			LOG.warn("Computation for configuration {} not created !!!", computationConfigurationId);
		}
		
		Computation existing = retrieveComputationByComputationId(computation.getComputationId());
		
		if(existing!=null) {
			LOG.warn("Computation for configuration {} already exists");
			return existing.getUniqueId();
		}
		
		for(ComputationTransition computationTransition: computation.getComputationTransitions()) {
			String nextInputId = computationTransition.getNextInputId();
			String prevOutId = computationTransition.getPreviousOutputId();
			for(ComputationTask task : computation.getComputationTasks()) {
				ComputationTaskInput input = task.getInputById(nextInputId);
				ComputationTaskOutput output = task.getOutputById(prevOutId);
				if(input!=null) {
					computationTransition.setNextInput(input);
				}
				if(output!=null) {
					computationTransition.setPreviousOutput(output);
				}
			}
		}
		
		for(ComputationTransition transition : computation.getComputationTransitions()) {
			
			String nextInputId = transition.getNextInputId();
			String prevOutId = transition.getPreviousOutputId();
			
			ComputationTaskOutput out = rerieveByTask(prevOutId, transition.getPreviousOutput().getComputationTask().getTaskId());
			ComputationTaskInput in = retrieveByTask(nextInputId, transition.getNextInput().getComputationTask().getTaskId());
			if(out!=null) {
				transition.setPreviousOutput(out);
			}
			if(in!=null) {
				transition.setNextInput(in);
			}
		}
				
		computation = save(computation);
		
		/*
		 * Check if there are already
		 * these computation tasks if so, user them
		 */
		Iterator<ComputationTask> iterator = computation.getComputationTasks().iterator();
		List<ComputationForTask> toSave = new ArrayList<ComputationForTask>();
		while(iterator.hasNext()) {
			ComputationTask computationTask = iterator.next();
			ComputationTask foundTask = computationTaskWorker.getByTaskId(computationTask.getTaskId());
			ComputationForTask computationForTask = new ComputationForTask();
			computationForTask.setComputation(computation);
			if(foundTask == null) {
				computationForTask.setComputationTask(computationTask);
			} else {
				computationForTask.setComputationTask(foundTask);
				iterator.remove();
			}
			toSave.add(computationForTask);
		}
		
		
		for(ComputationForTask task : toSave) {
			save(task);
		}
		
		int result = computation.getUniqueId();
		
		return result;
	}
	


	/** {@inheritDoc} */
	@Override
	public int createComputation(int computationPackageId) {
		LOG.info("Creating computation for package id {}", computationPackageId);
		ComputationPackage computationPackage = computationPackageWorker.retrieveComputationPackage(computationPackageId);
		int createComputationResult = createComputation(computationPackage);
		return createComputationResult;
	}

	/** {@inheritDoc} */
	@Override
	public Serializable runProcess(String processId) {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public List<Computation> retrieveComputations(PagingInfo pagingInfo) {
		
		Query query = getEntityManager().createNamedQuery(Computation.EXISTING);
		
		if(pagingInfo != null) {
			if(pagingInfo.isSortingEnabled()) {
				String qry = String.format("SELECT cmp FROM Computation AS cmp ORDER BY cmp.%s %s", pagingInfo.getSortBy(), pagingInfo.getSortType());
				query = getEntityManager().createQuery(qry);
			}
			query.setFirstResult(pagingInfo.getFirstResult());
			query.setMaxResults(pagingInfo.getRowsPerPage());
		}
		return getResultList(query, Computation.class);
	}

	/** {@inheritDoc} */
	@Override
	public Long retrieveComputationsCount() {
		Query qry = getEntityManager().createNamedQuery(Computation.EXISTING_COUNT);
		return getSingleResult(qry, Long.class);
	}

	/** {@inheritDoc} */
	@Override
	public Computation retrieveComputation(int computationId) {
		return find(computationId, Computation.class);
	}

	/** {@inheritDoc} */
	@Override
	public Collection<ComputationTask> retieveTasksForComputation(
			int computationId) {
		return computationTaskWorker.getByComputation(computationId);
	}

	/** {@inheritDoc} */
	@Override
	public Collection<ComputationTransition> retrieveTransitionsForComputation(
			int computationId) {
		return retrieveComputation(computationId).getComputationTransitions();
	}

	/** {@inheritDoc} */
	@Override
	public Collection<ComputationTaskInput> retrieveTaskInputs(
			int computationId) {
		Collection<ComputationTaskInput> result = new ArrayList<ComputationTaskInput>();
		Collection<ComputationTask> computationTasks = retieveTasksForComputation(computationId);
		for(ComputationTask task : computationTasks) {
			result.addAll(task.getInputs());
		}
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public Computation retrieveComputationByComputationId(String computationId) {
		Computation computation = null;
		try {
			Query query = getEntityManager().createNamedQuery(Computation.BY_COMPUTATION_ID);
			query.setParameter("compId", computationId);
			computation = getSingleResult(query, Computation.class);
		} catch (NoResultException e ) {
			LOG.info("Computation with computationId {} doesn't exits", computationId);
		}
		return computation; 
	}

	/** {@inheritDoc} */
	@Override
	public ComputationTaskOutput rerieveByTask(String outputId, String taskId) {
		ComputationTaskOutput result = null;
		try {
			Query query = getEntityManager().createNamedQuery(ComputationTaskOutput.OUT_BY_ID);
			query.setParameter(ID, outputId);
			query.setParameter(TASK_ID, taskId);
			result = getSingleResult(query, ComputationTaskOutput.class);
		} catch (NoResultException e) {
			LOG.info("Output not found for outputId {} and taskId {}", outputId, taskId);
		}
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public ComputationTaskInput retrieveByTask(String inputId, String taskId) {
		ComputationTaskInput result = null;
		try {
			Query query = getEntityManager().createNamedQuery(ComputationTaskInput.IN_BY_ID);
			query.setParameter(ID, inputId);
			query.setParameter(TASK_ID, taskId);
			result = getSingleResult(query, ComputationTaskInput.class);
		} catch (NoResultException e) {
			LOG.info("Input not found for inputId {} and taskId {}", inputId, taskId);
		}
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public List<ComputationSetting> retrieveSettings(int computationId) {
		Query query = getEntityManager().createNamedQuery(ComputationSetting.SETTING_BY_COMPUTATION);
		query.setParameter(ID, computationId);
		return getResultList(query, ComputationSetting.class);
	}

	

}
