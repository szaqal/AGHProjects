package computation.worker;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateful;
import javax.persistence.NoResultException;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import api.annotations.Partitionable;
import api.annotations.Value;
import api.computation.ComputableTask;
import api.computation.ComputationContext;

import computation.NodesHolder;
import computation.engine.RunableComputation;
import computation.exceptions.ComputationRunTimeException;
import computation.model.Computation;
import computation.model.ComputationLogEntry;
import computation.model.ComputationSetting;
import computation.model.ComputationTask;
import computation.model.ComputationTaskInput;
import computation.model.ComputationTaskOutput;
import computation.model.ComputationTransition;
import computation.model.ComputingNode;
import computation.model.PerformedComputation;
import computation.worker.ComputationLogEntryWorker.ComputationLogEntryWorkerLocal;
import computation.worker.ComputationPerformerWorker.ComputationPerformerWorkerLocal;
import computation.worker.ComputationPerformerWorker.ComputationPerformerWorkerRemote;
import computation.worker.ComputationResultWorker.ComputationResultWorkerLocal;
import computation.worker.ComputationTaskWorker.ComputationTaskWorkerLocal;
import computation.worker.ComputationWorker.ComputationWorkerLocal;
import computation.worker.NodeInvokerWorker.NodeInvokerWorkerLocal;
import computation.worker.NodesWorker.NodesWorkerLocal;
import computation.worker.PerformedComputationWorker.PerformedComputationWorkerLocal;

import core.workers.AbstractWorkerBean;

/**
 * Worker implementation.
 * @author   malczyk.pawel@gmail.com
 */
@Stateful
@Local(ComputationPerformerWorkerLocal.class)
@Remote(ComputationPerformerWorkerRemote.class)
public class ComputationPerformerWorkerBean extends AbstractWorkerBean implements ComputationPerformerWorker {
	
	/**
	 * Log message.
	 */
	private static final String NODES_LOAD_MSG = "{} : Current nodes load {}";

	/**
	 * Exception message.
	 */
	private static final String IAE_MESSAGE = "IllegalAccessException Excpetion";

	/** Error message. */
	private static final String CONTEXT_SET_FAILED = "Unable to set computation context";

	/** Logger. */
	private static final Logger LOG = LoggerFactory.getLogger(ComputationPerformerWorkerBean.class);

	/**
	 * Worker providing logger features.
	 * @uml.property  name="computationLogEntryWorkerLocal"
	 * @uml.associationEnd  
	 */
	@EJB
	private ComputationLogEntryWorkerLocal computationLogEntryWorkerLocal;

	/**
	 * Computation task worker local.
	 * @uml.property  name="computationTaskWorkerLocal"
	 * @uml.associationEnd  
	 */
	@EJB
	private ComputationTaskWorkerLocal computationTaskWorkerLocal;
	
	/**
	 * {@link ComputationResultWorker}  .
	 * @uml.property  name="computationResultWorker"
	 * @uml.associationEnd  
	 */
	@EJB
	private ComputationResultWorkerLocal computationResultWorker;

	/**
	 * Computation worker.
	 * @uml.property  name="computationWorker"
	 * @uml.associationEnd  
	 */
	@EJB
	private ComputationWorkerLocal computationWorker;

	/**
	 * Node invoker worker.
	 * @uml.property  name="nodeInvokerWorkerLocal"
	 * @uml.associationEnd  
	 */
	@EJB
	private NodeInvokerWorkerLocal nodeInvokerWorkerLocal;
	
	/**
	 * Nodes worker.
	 * @uml.property  name="nodesWorkerLocal"
	 * @uml.associationEnd  
	 */
	@EJB
	private NodesWorkerLocal nodesWorkerLocal;

	/**
	 * Performed computation.
	 * @uml.property  name="performedComputation"
	 * @uml.associationEnd  
	 */
	private PerformedComputation performedComputation;

	/**
	 * Performed computation worker.
	 * @uml.property  name="performedComputationWorkerLocal"
	 * @uml.associationEnd  
	 */
	@EJB
	private PerformedComputationWorkerLocal performedComputationWorkerLocal;

	/** Contains mapping between task an it's classes. */
	private Map<ComputationTask, Class<?>> taskMap;

	
	/**
	 * Current runable computation.
	 * @uml.property  name="runableComputation"
	 * @uml.associationEnd  
	 */
	private RunableComputation runableComputation;
	
	/**
	 * Computation context.
	 * @uml.property  name="computationContext"
	 * @uml.associationEnd  
	 */
	private ComputationContext computationContext;

	/** Holds computation variables. */
	private Map<String, Serializable> variables = new HashMap<String, Serializable>();
	
	/** Initial settings. */
	private Map<String, String> initialSettings = new HashMap<String, String>();
	
	/**
	 * Utils class for performer.
	 * @uml.property  name="performerUtil"
	 * @uml.associationEnd  
	 */
	private ComputationPerformerUtil performerUtil;
	
	/**
	 * Date started.
	 */
	private Date startDate;

	/** Constructor.  */
	public ComputationPerformerWorkerBean() {
		super();
	}

	/** {@inheritDoc} */
	public RunableComputation createRunnableComputation(Collection<ComputationTask> computationTasks,
			Collection<ComputationTransition> tansitions) throws ComputationRunTimeException{

		RunableComputation runableComputation = new RunableComputation();
		ComputationTask firstTask = performerUtil.findFirst(computationTasks);
		runableComputation.addTask(firstTask);

		computationTasks.remove(firstTask);

		ComputationTask prevTask = firstTask;
		try {
			while(!computationTasks.isEmpty()) {
				ComputationTaskOutput prevOutput = prevTask.getOutputs().iterator().next();
				int nextInput = tansitions.iterator().next().getComputation().getUniqueId();
				ComputationTask nextTask = computationTaskWorkerLocal.findNext(prevOutput, nextInput);
				if (nextTask != null) {
					computationTasks.remove(nextTask);
					runableComputation.addTask(nextTask);
					prevTask = nextTask;
				} else {
					break;
				}
			}
		} catch (NoResultException e) {
			LOG.error(String.format("%s : Error creating runnable computation due to ", startDate.getTime()), e);
			throw new ComputationRunTimeException(e.getMessage());
		}
		
		return runableComputation;
	}
	


	/** {@inheritDoc} */
	@Override
	public RunableComputation createRunnableComputation(int computationId) throws ComputationRunTimeException {
		LOG.debug("{} : Creating runnable compuatation from computation [{}]", startDate.getTime(), computationId);
		try {
			Computation computation = computationWorker.retrieveComputation(computationId);
			Collection<ComputationTransition> tansitions  = computation.getComputationTransitions();
			Collection<ComputationTransition> detachedTransitions = new ArrayList<ComputationTransition>();
			detachedTransitions.addAll(tansitions);
			
			Collection<ComputationTask> detachedComputationTasks = new ArrayList<ComputationTask>();
			detachedComputationTasks.addAll(computationTaskWorkerLocal.getByComputation(computationId));
			runableComputation = createRunnableComputation(detachedComputationTasks, detachedTransitions);
			
			
			computationContext = new ComputationContext();
			computationContext.setPerformable(runableComputation);
			if(initialSettings != null && !initialSettings.isEmpty()) {
				for(String key : initialSettings.keySet()) {
					String value = initialSettings.get(key);
					if(value!=null && !value.equalsIgnoreCase("null")) {
						computationContext.addSetting(key, value);
					}
				}
			} else {
				for(ComputationSetting setting : computation.getComputationSettings()) {
					computationContext.addSetting(setting.getName(), setting.getVal());
				}
			}
			
			performedComputation = new PerformedComputation();
			performedComputation.setStartDate(new Date());
			performedComputation.setComputation(computation);
			performedComputationWorkerLocal.savePerformedComputation(performedComputation);
			
			ComputationLogEntry computationLogEntry = new ComputationLogEntry(
					String.format("%s : Runnable computation form computation [%s] created", startDate.getTime(), computationId), performedComputation);
			computationLogEntryWorkerLocal.store(computationLogEntry);
			
			for(ComputationTransition computationTransition:tansitions) {
				runableComputation.addMapping(computationTransition.getPreviousOutput().getId(), 
						computationTransition.getNextInput().getId());
			}
			
			
			//Set computation context to proper tasks
		} catch (NoResultException e) {
			LOG.error(String.format("%s : Error creating runnable computation", startDate.getTime()), e);
			throw new ComputationRunTimeException("Error creating runable computation due to " + e.getMessage());
		}
		
		return runableComputation;
	}
	
	/**
	 * Sets {@link ComputationContext} to properly annotated tasks.
	 * @param computableTask computable task.
	 * @throws ComputationRunTimeException exception throwed
	 */
	private void applyComputationContext(ComputableTask computableTask) throws ComputationRunTimeException {
		Class<?> taskClass = computableTask.getClass();
		Field[] fields = taskClass.getDeclaredFields();
		for (Field field : fields) {
			if (field.isAnnotationPresent(api.annotations.ComputationContext.class)) {
				try {
					PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), taskClass);
					propertyDescriptor.getWriteMethod().invoke(computableTask, computationContext);
				} catch (IntrospectionException ie) {
					throw new ComputationRunTimeException(CONTEXT_SET_FAILED);
				} catch (IllegalArgumentException e) {
					throw new ComputationRunTimeException(CONTEXT_SET_FAILED);
				} catch (IllegalAccessException e) {
					throw new ComputationRunTimeException(CONTEXT_SET_FAILED);
				} catch (InvocationTargetException e) {
					throw new ComputationRunTimeException(CONTEXT_SET_FAILED);
				}
			}
		}
	}

	/** {@inheritDoc} */
	@Override
	public int doComputation(int computationId, int userId) throws ComputationRunTimeException {
		startDate = new Date();
		runableComputation = createRunnableComputation(computationId);
		
		if(runableComputation == null) {
			throw new ComputationRunTimeException("Unable to create runable computation");
		}
		
		
		List<ComputationTask> tasks = runableComputation.getTasks();
		for(ComputationTask task : tasks) {
			try {
				Class<?> taskClass = Class.forName(task.getClassName());
				runableComputation.addTaskClass(taskClass);
			} catch (ClassNotFoundException e) {
				LOG.error("{} : Class not found",startDate.getTime(), e);
				throw new ComputationRunTimeException("Task class not found "+e.getMessage());
			}
		}

		taskMap = runableComputation.getTasksMap();

		ComputationTask nextTask = tasks.iterator().next();
		while(nextTask!=null) {
			ComputableTask task = runTask(nextTask);
			nextTask = runableComputation.getNextTask(nextTask, task);
		}
		
		performedComputation.setEndDate(new Date());
		performedComputationWorkerLocal.savePerformedComputation(performedComputation);
		
		
		Map<String, Serializable> results = new HashMap<String, Serializable>();
		for (ComputationTask task : runableComputation.getTasks()) {
			for(ComputationTaskOutput output : task.getOutputs() ) {
				if(output.isResult()) {
					results.put(output.getName(), variables.get(output.getName()));
				}
			}
		}

		computation.model.ComputationResult savableResult = new computation.model.ComputationResult();
		savableResult.setCreateDate(new Date());
		savableResult.setResult(performerUtil.serialize(results));
		savableResult.setPerformedComputation(performedComputation);
		int resultIdentifier = computationResultWorker.saveResult(savableResult, userId).getUniqueId();
		return resultIdentifier;
	}

	/**
	 * Post processing.
	 *
	 * <ol>
	 * 	<li>Puts computed value as a output to the variables map</li>
	 * </ol>
	 *
	 * @param computationTask {@link ComputationTask}
	 * @param deserilizedOutputs deserialized map of outputs and values from node.
	 * @param computableTask computable task
	 * @throws ComputationRunTimeException exception throwed
	 */
	private void postRun(ComputationTask computationTask, 
			Map<String, Serializable> deserilizedOutputs, ComputableTask computableTask)  throws ComputationRunTimeException  {
		
		LOG.debug("Computation postprocessing.");

		for(String outputName : deserilizedOutputs.keySet()) {
			ComputationTaskOutput out = computationTask.getOutputByName(outputName);
			variables.put(out.getValueName(), (Serializable) deserilizedOutputs.get(outputName));
			
			ArrayList<String> nextInputIds = runableComputation.getNextInput(out.getId());
			for(String inputId: nextInputIds) {
				ComputationTask nextTask = runableComputation.getNextTask(computationTask, computableTask);
				if(nextTask!=null) {
					ComputationTaskInput in = nextTask.getInputById(inputId);
					variables.put(in.getValueName(), (Serializable) deserilizedOutputs.get(outputName));
				}
			}
		}

		ComputationLogEntry computationLogEntry = new ComputationLogEntry(
				String.format("Postrunning finished value stack"), performedComputation);
		computationLogEntryWorkerLocal.store(computationLogEntry);
	}
	

	/** {@inheritDoc} */
	public HashMap<String, Serializable> prepareInputs(ComputationTask computationTask) {
		HashMap<String, Serializable> result = new HashMap<String, Serializable>();
		for(ComputationTaskInput computationTaskInput : computationTask.getInputs()) {
			result.put(computationTaskInput.getName(), variables.get(computationTaskInput.getValueName()));
		}
		return result;
	}


	/** {@inheritDoc} */
	public HashMap<String, Serializable> prepareOutputs(ComputationTask computationTask) {
		HashMap<String, Serializable> result = new HashMap<String, Serializable>();
		for(ComputationTaskOutput computationTaskOutput : computationTask.getOutputs()) {
			result.put(computationTaskOutput.getName(), null);
		}
		return result;
	}

	



	/**
	 * Pre processing.
	 *
	 * <ol>
	 * 	<li>Read variable values that can be set.</li>
	 * </ol>
	 *
	 * @param computationTask {@link ComputationTask}
	 * @param computableTask TODO
	 * @throws IllegalAccessException {@link IllegalAccessException}
	 * @throws ClassNotFoundException {@link ClassNotFoundException}
	 * @throws IntrospectionException {@link IntrospectionException}
	 * @throws InvocationTargetException TODO
	 */
	private void preRun(ComputationTask computationTask, ComputableTask computableTask) throws IllegalAccessException, ClassNotFoundException,
			IntrospectionException, InvocationTargetException {
		LOG.debug("Computation preprocessing");
		Class<?> taskClass = computableTask.getClass();
		Field [] fields = taskClass.getDeclaredFields();
		for(Field field : fields) {
			if(field.isAnnotationPresent(Value.class)) {
				Value valAnnotation = field.getAnnotation(Value.class);
				ComputationTaskInput taskInput = computationTask.getInputByName(valAnnotation.name());
				if(taskInput != null && variables.containsKey(taskInput.getValueName())) {
					PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), taskClass);
					propertyDescriptor.getWriteMethod().invoke(computableTask, variables.get(taskInput.getValueName()));
					LOG.debug("Setting variable [{}] with value [{}]", taskInput.getName(), variables.get(taskInput.getValueName()));
				}
			}
		}

		ComputationLogEntry computationLogEntry = new ComputationLogEntry(
				String.format("Prerunning finished value stack"), performedComputation);
		computationLogEntryWorkerLocal.store(computationLogEntry);
	}
	
	/**
	 * Finds free computing node that can be used for computation.
	 * @return computing node.
	 */
	public ComputingNode acquireNode() {
		ComputingNode result = null;
		if(CollectionUtils.isEmpty(nodesWorkerLocal.retrieveComputingNodes())) {
			LOG.warn("{} : No nodes available", startDate.getTime());
			return result;
		}
		
		for(ComputingNode node :  nodesWorkerLocal.retrieveComputingNodes()) {
			if(!RunTimeNodes.contains(node)) {
				LOG.info("{} : Acquiring node {} ", startDate.getTime(), node);
				result = node;
				break;
			}
		} 
		if(result==null) {
			result = RunTimeNodes.leastLoaded();
			String mesg = String.format("%s : Least loaded node {} with load {}", startDate.getTime());
			LOG.info(mesg, result.getInetAddr(), RunTimeNodes.load(result));
			LOG.info(NODES_LOAD_MSG, startDate.getTime(), RunTimeNodes.string());
		}
		return result;
	}
	
	/**
	 * Releases node.
	 * @param node {@link ComputingNode}
	 */
	public void releaseNode(ComputingNode node) {
		LOG.info("{} : Releasing node {}...", startDate.getTime(), node);
		RunTimeNodes.realease(node);
		LOG.info(NODES_LOAD_MSG, startDate.getTime(), RunTimeNodes.string());
	}
	
	
	/**
	 * Runs either reqular non-partitionale task or paritionable-one.
	 * @param computationTask task being executed
	 * @return {@link ComputableTask}.
	 * @throws ComputationRunTimeException exeception throwed.
	 */
	private ComputableTask runTask(ComputationTask computationTask) throws ComputationRunTimeException {
		try {
			ComputableTask computableTask = (ComputableTask) taskMap.get(computationTask).newInstance();
			if(isPartitionable(computationTask, computableTask)) {
				return runPartitionableTask(computationTask, computableTask);
			} else {
				return runRegularTask(computationTask, computableTask);
			}
		} catch (InstantiationException e) {
			LOG.warn("Instantiation Excpetion", e);
			throw new ComputationRunTimeException(e.getMessage());
		} catch (IllegalAccessException e) {
			LOG.warn(IAE_MESSAGE, e);
			throw new ComputationRunTimeException(e.getMessage());
		} 
		
	}
	
	/**
	 * Runs partitionable task.
	 * @param computationTask {@link ComputationTask}
	 * @param computableTask {@link ComputableTask}
	 * @return {@link ComputableTask}
	 * @throws ComputationRunTimeException {@link ComputationRunTimeException}
	 */
	@SuppressWarnings("unchecked")
	private ComputableTask runPartitionableTask(ComputationTask computationTask, ComputableTask computableTask) throws ComputationRunTimeException {
		LOG.info("{} : Running partitionable task {}", startDate.getTime(), computationTask);
		
		applyComputationContext(computableTask);
		
		Collection<ComputingNode>  computingNodes = NodesHolder.getInstance().retrieveComputingNodes();
		int entryElementsCount = getPartitionedVariableCount(computationTask, computableTask, computingNodes.size());
		
		TaskRunnerThread [] runningThreads  = new TaskRunnerThread[computingNodes.size()]; 
		int i=0;
		for (ComputingNode computingNode : computingNodes ) {
			runningThreads[i] = new TaskRunnerThread(computableTask, computationTask, variables, computingNode, entryElementsCount, this, 
					nodeInvokerWorkerLocal, performedComputation, computationContext, nodesWorkerLocal.retrieveComputingNodes(), startDate);
			runningThreads[i].start();
			i++;
		}
		
		
		while(true) {
			boolean allFinished = true;
			for(TaskRunnerThread taskRunnerThread : runningThreads) {
				if(taskRunnerThread.getError() != null) {
					throw new ComputationRunTimeException(taskRunnerThread.getError().getMessage());
				}
				if(!taskRunnerThread.isFinished()) {
					allFinished = false;
				}
			}
			if(allFinished) {
				break;
			}
		}
		
		
		Map<String, Serializable> deserilizedOutputs = new HashMap<String, Serializable>();
		for(TaskRunnerThread taskThread : runningThreads) {
			for(String key: taskThread.getOutputsReturned().keySet()) {
				if(deserilizedOutputs.containsKey(key)) {
					((Collection) deserilizedOutputs.get(key)).addAll((Collection) taskThread.getOutputsReturned().get(key));
				} else {
					deserilizedOutputs.put(key, taskThread.getOutputsReturned().get(key));
				}
			}
		}
		
		postRun(computationTask, deserilizedOutputs, computableTask);
		
		return computableTask;
	}
	
	
	/**
	 * Returns size of partitioned data to make it applicable to run on multiple nodes.
	 * @param computationTask {@link ComputationTask}
	 * @param computableTask {@link ComputableTask}
	 * @param nodes nodes count
	 * @return size of partitioned data.
	 * @throws ComputationRunTimeException exectption throwed
	 */
	private int getPartitionedVariableCount(ComputationTask computationTask, ComputableTask computableTask, int nodes) throws ComputationRunTimeException {
		Class<?> taskClass = computableTask.getClass();
		Field [] fields = taskClass.getDeclaredFields();
		int size = 0;
		for(Field field : fields) {
			if(field.isAnnotationPresent(Value.class)) {
				Value valAnnotation = field.getAnnotation(Value.class);
				ComputationTaskInput taskInput = computationTask.getInputByName(valAnnotation.name());
				if(taskInput != null && variables.containsKey(taskInput.getValueName())) {
					Serializable variable = variables.get(taskInput.getValueName());
					if(variable instanceof Collection<?>) {
						size = ((Collection<?>) variable).size();
					} else {
						throw new ComputationRunTimeException("Invalid partitionable type");
					}
				}
			}
		}
		
		return size;
		
	}

	/**
	 * Runs single task.
	 * @param computationTask {@link ComputationTask} to run
	 * @param computableTask {@link ComputableTask}
	 * @return Computable instance of tak already perocessed.
	 * @throws ComputationRunTimeException exception throwed
	 */
	private ComputableTask runRegularTask(ComputationTask computationTask, ComputableTask computableTask) throws ComputationRunTimeException {
		LOG.info("{} : Running task {}", startDate.getTime(), computationTask);
		try {
			//--> 1.) Slave node
			applyComputationContext(computableTask);
			
			ComputingNode node = acquireNode();
			
			if(node == null) {
				throw new ComputationRunTimeException("Unable to use Slave server");
			}
			//--> 2.) setup variables
			preRun(computationTask, computableTask);

			//--> 3.) create log
			LOG.info("{} : Computation will be run at {}", startDate.getTime(), node.getInetAddr());
			ComputationLogEntry computationLogEntry = new ComputationLogEntry("Running computation", performedComputation);
			computationLogEntryWorkerLocal.store(computationLogEntry);

			//--> 4.) use computing node
			Map<String, Serializable> outputsReturned = nodeInvokerWorkerLocal.invoke(node, prepareInputs(computationTask), prepareOutputs(computationTask),
					computationTask.getTaskName(), performedComputation, computationContext);

			
			//--> 5.) Release node
			releaseNode(node);

			//--> 6.) update variables
			postRun(computationTask, outputsReturned, computableTask);
			
			return computableTask;

		} catch (IllegalAccessException e) {
			LOG.warn(IAE_MESSAGE, e);
			throw new ComputationRunTimeException(e.getMessage());
		} catch (ClassNotFoundException e) {
			LOG.warn("ClassNotFound Exception", e);
			throw new ComputationRunTimeException(e.getMessage());
		} catch (IntrospectionException e) {
			LOG.warn("IntrospectionException Exception", e);
			throw new ComputationRunTimeException(e.getMessage());
		} catch (InvocationTargetException e) {
			LOG.warn("InvocationTargetException Exception", e);
			throw new ComputationRunTimeException(e.getMessage());
		} catch (NoResultException e) {
			LOG.warn("NoResultException Exception", e);
			throw  new ComputationRunTimeException(e.getMessage());
		}
	}
	
	/**
	 * Checks whether this task.
	 * @param computationTask computation task
	 * @param computableTask computatble task
	 * @return true if annotation was present.
	 */
	private boolean isPartitionable(ComputationTask computationTask, ComputableTask computableTask) {
		Class<?> taskClass = computableTask.getClass();
		Field [] fields = taskClass.getDeclaredFields();
		for(Field field : fields) {
			if(field.isAnnotationPresent(Partitionable.class)) {
				return true;
			}
		}
		return false;
	}
	


	/** {@inheritDoc} */
	@Override
	public void setup(Map<String, Number> initialValues, Map<String, String> initialSettings) {
		variables = new HashMap<String, Serializable>();
		variables.putAll(initialValues);
		performerUtil = new ComputationPerformerUtil();
		this.initialSettings = initialSettings;
	}
}
