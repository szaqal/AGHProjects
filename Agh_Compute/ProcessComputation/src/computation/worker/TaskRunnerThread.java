package computation.worker;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import api.annotations.Partitionable;
import api.annotations.Value;
import api.computation.ComputableTask;
import api.computation.ComputationContext;

import computation.model.ComputationTask;
import computation.model.ComputationTaskInput;
import computation.model.ComputationTaskOutput;
import computation.model.ComputingNode;
import computation.model.PerformedComputation;

/**
 * Thread that runs paritionable task.
 * @author malczyk.pawel@gmail.com
 *
 */
public class TaskRunnerThread extends Thread {

	/**
	 * Logger.
	 */
	private static final Logger LOG = LoggerFactory.getLogger(TaskRunnerThread.class);
	
	/**
	 * {@link ComputableTask}.
	 */
	private ComputableTask computableTask;
	
	/**
	 * {@link ComputationTask}.
	 */
	private ComputationTask computationTask;
	
	/**
	 * Variable stack.
	 */
	private Map<String, Serializable> variables = new HashMap<String, Serializable>();
	
	/**
	 * 
	 */
	private int entryElementsCount;
	
	/**
	 * Owning {@link ComputationPerformerWorker}.
	 */
	private ComputationPerformerWorker computationPerformerWorker;
	
	
	/**
	 * {@link NodeInvokerWorker}.
	 */
	private NodeInvokerWorker nodeInvokerWorker;
	
	/**
	 * {@link PerformedComputation}.
	 */
	private PerformedComputation performedComputation;
	
	/**
	 * True when thread finished working.
	 */
	private boolean finished;
	
	/**
	 * If there was any error here it will be available.
	 */
	private Throwable error;
	
	/**
	 * Current computation context.
	 */
	private ComputationContext computationContext;
	
	/**
	 * Outputs returned from slave server.
	 */
	private Map<String, Serializable> outputsReturned;
	
	/**
	 * Current computing node.
	 */
	private ComputingNode currentNode;
	
	/**
	 * All connected computing nodes.
	 */
	private Collection<ComputingNode> allNodes;
	
	
	private Date startDate;
	
	
	
	/**
	 * Constructor.
	 * @param computableTask computable task.
	 * @param computationTask computationtask
	 * @param variables variable stack
	 * @param entryElementsCount 
	 * @param computationPerformerWorker thread owning {@linkplain ComputationPerformerWorker} instance.
	 * @param nodeInvokerWorker {@link NodeInvokerWorker}
	 * @param performedComputation 
	 * @param computationContext computation
	 * @param allNodes all connected nodes.
	 * @param nodesInUse nodes currently in use
	 * @param startDate TODO
	 */
	public TaskRunnerThread(ComputableTask computableTask,
			ComputationTask computationTask,
			Map<String, Serializable> variables, ComputingNode computingNode, int entryElementsCount,
			ComputationPerformerWorker computationPerformerWorker,
			NodeInvokerWorker nodeInvokerWorker,
			PerformedComputation performedComputation, ComputationContext computationContext, 
			Collection<ComputingNode> allNodes, Date startDate) {
		
		super();
		this.computableTask = computableTask;
		this.computationTask = computationTask;
		this.variables = variables;
		currentNode = computingNode;
		this.entryElementsCount = entryElementsCount;
		this.computationPerformerWorker = computationPerformerWorker;
		this.nodeInvokerWorker = nodeInvokerWorker;
		this.performedComputation = performedComputation;
		this.computationContext = computationContext;
		this.allNodes = allNodes;
		this.startDate = startDate;
	}
	
	
	/**
	 * Merges node that are currently in use with all available computing nodes.
	 * @param allNodes all connected nodes
	 * @param nodesInUse currently used ones.
	 * @return merged map.
	 */
	private Map<ComputingNode, Integer> setupNodesMap(Collection<ComputingNode> allNodes, Map<ComputingNode, Integer> nodesInUse) {
		Map<ComputingNode, Integer> result = new HashMap<ComputingNode, Integer>();
		
		for(ComputingNode node : allNodes) {
			if(nodesInUse.containsKey(node)) {
				Integer value = nodesInUse.get(node);
				result.put(node, value == 0 ? 1: value);
			} else {
				result.put(node, 1);
			}
		}
		
		return result;
	}
	
	

	/**
	 * Prepares subset of values that is used as an input for partitionable task.
	 * @param computationTask paritionable {@link ComputableTask}
	 * @return prepared
	 */
	private HashMap<String, Serializable> prepareInputs(ComputationTask computationTask) {
		HashMap<String, Serializable> inputs = new HashMap<String, Serializable>();
		for(ComputationTaskInput computationTaskInput : computationTask.getInputs()) {
			if(variables.get(computationTaskInput.getValueName()) instanceof Collection<?>) {
				int [] range = getRange();
				Collection<?> entryData = (Collection<?>) variables.get(computationTaskInput.getValueName());
				Serializable [] data = entryData.toArray(new Serializable[entryData.size()]);
				Serializable [] partitioned = Arrays.copyOfRange(data, range[0], range[1]);
				inputs.put(computationTaskInput.getName(), wrap(partitioned));
				LOG.info(String.format("%s : %s Inputs - full size data %s partioned %s", startDate.getTime(), getId(), data.length, partitioned.length));
				
			} else {
				inputs.put(computationTaskInput.getName(), variables.get(computationTaskInput.getValueName()));
			}
		}
		return inputs;
	}
	
	
	/**
	 * Returns subrange of data based on load and nodes count.
	 * @return subrange.
	 */
	private int [] getRange() {
		int [] result = new int[2];
		Map<ComputingNode, Integer> nodeMappings = setupNodesMap(allNodes, RunTimeNodes.getNodesInUse());
		int count =0;
		for(Integer j : nodeMappings.values()) {
			count +=j;
		}
		System.out.println("Total Jobs "+count);
		int start = 0; 
		int end = 0;
		int resultStart = 0;
		int resultEnd = 0;
		for(ComputingNode computingNode : nodeMappings.keySet()) {
			double load = nodeMappings.get(computingNode) / (double) count;
			int itemsCount = (int) ((load * entryElementsCount));
			start = end;
			end = start+itemsCount;
			if(computingNode.equals(currentNode)) {
				resultStart = start;
				resultEnd = end;
//				LOG.info(LOG_MSG_PATTERN, getId(), String.format("Range %s -> %s ", resultStart, resultEnd));
				break;
			}
			
		}
		
		result[0] = resultStart;
		result[1] = resultEnd;
		
		
		int lastIndex = entryElementsCount -1;
		if(resultEnd < lastIndex && result[1] + nodeMappings.keySet().size() > entryElementsCount) {
			int prev = result[1];
			result[1] = lastIndex;
			LOG.info("Last node will handle {} insted of {}", result[1], prev);
		}
		
		return result;
	}


	/**
	 * Prepares subset of value that is used as an output for partitionable task.
	 * @param computationTask paritionabled {@link ComputableTask}
	 * @return outputs
	 */
	private HashMap<String, Serializable> prepareOutputs(ComputationTask computationTask) {
		HashMap<String, Serializable> outputs = new HashMap<String, Serializable>();
		for(ComputationTaskOutput computationTaskOutput : computationTask.getOutputs()) {
			if(variables.get(computationTaskOutput.getValueName()) instanceof Collection<?>) {
				Collection<?> entryData = (Collection<?>) variables.get(computationTaskOutput.getValueName());
				Serializable [] data = entryData.toArray(new Serializable[entryData.size()]);
				int [] range = getRange();
				Serializable [] partitioned = Arrays.copyOfRange(data, range[0], range[1]);
				outputs.put(computationTaskOutput.getName(), null);
				LOG.info(String.format("%s : %s Outputs - full size data %s partioned %s",startDate.getTime(), getId(), data.length, partitioned.length));
			} else {
				outputs.put(computationTaskOutput.getName(), null);
			}
		}
		return outputs;
	}
	
	/**
	 * Converts array to ArrayList.
	 * @param input array
	 * @return result list
	 */
	private ArrayList<Serializable> wrap(Serializable [] input) {
		ArrayList<Serializable> result = new ArrayList<Serializable>();
		for(Serializable item : input) {
			result.add(item);
		}
		return result;
	}



	/** {@inheritDoc} */
	@Override
	public void run() {
		LOG.info(String.format("%s : %s %s Started",startDate.getTime(), getId(), computationTask.getTaskName()));
		try {
			Class<?> taskClass = computableTask.getClass();
			Field [] fields = taskClass.getDeclaredFields();
			setupInputs(taskClass, fields);
			
			
			ComputingNode nodeAcquired = computationPerformerWorker.acquireNode();
			outputsReturned = nodeInvokerWorker.invoke(nodeAcquired, prepareInputs(computationTask), 
					prepareOutputs(computationTask),
					computationTask.getTaskName(), performedComputation, computationContext);
			
			
			computationPerformerWorker.releaseNode(nodeAcquired);
			
			finished = true;
		} catch (Exception e) {
			LOG.warn(" Exception", e);
			error = e;
			finished = true;
		} 
		LOG.info("{} : {} Finished", startDate.getTime(), getId());
	}

	/**
	 * Gets inputs subset of values needed.
	 * @param taskClass current task
	 * @param fields collection of fields within task
	 * @throws IntrospectionException exception throwed
	 * @throws IllegalAccessException exception throwed
	 * @throws InvocationTargetException exception throwed
	 */
	private void setupInputs(Class<?> taskClass, Field[] fields) throws IntrospectionException, IllegalAccessException, InvocationTargetException {
		for(Field field : fields) {
			if(field.isAnnotationPresent(Value.class)) {
				Value valAnnotation = field.getAnnotation(Value.class);
				ComputationTaskInput taskInput = computationTask.getInputByName(valAnnotation.name());
				if(taskInput != null && variables.containsKey(taskInput.getValueName())) {
					if(field.isAnnotationPresent(Partitionable.class)) {
						PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), taskClass);
						Collection<?> entryData = (Collection<?>) variables.get(taskInput.getValueName());
						Serializable [] data = entryData.toArray(new Serializable[entryData.size()]);
						int [] range = getRange();
						Serializable [] partitioned = Arrays.copyOfRange(data, range[0], range[1]);
//						LOG.info(LOG_MSG_PATTERN, String.format("Getting range partitioned data %s to %s", range[0], range[1]));
						propertyDescriptor.getWriteMethod().invoke(computableTask, Arrays.asList(partitioned));
					} else {
						PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), taskClass);
						propertyDescriptor.getWriteMethod().invoke(computableTask, variables.get(taskInput.getValueName()));
						
//						LOG.info(LOG_MSG_PATTERN, getId(), String.format("Setting variable %s with value %s", taskInput.getName(), variables.get(taskInput.getValueName())));
					}
				}
			}
		}
	}



	/**
	 * Getter.
	 * @return the finished
	 */
	public boolean isFinished() {
		return finished;
	}



	/**
	 * Setter.
	 * @param finished the finished to set
	 */
	public void setFinished(boolean finished) {
		this.finished = finished;
	}



	/**
	 * Getter.
	 * @return the error
	 */
	public Throwable getError() {
		return error;
	}



	/**
	 * Setter.
	 * @param error the error to set
	 */
	public void setError(Throwable error) {
		this.error = error;
	}



	/**
	 * Getter.
	 * @return the outpusReturned
	 */
	public Map<String, Serializable> getOutputsReturned() {
		return outputsReturned;
	}
	
}
