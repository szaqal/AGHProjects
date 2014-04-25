package computation.engine;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import api.annotations.Routable;
import api.annotations.RouteTo;
import api.computation.ComputableTask;
import api.computation.Performable;

import computation.exceptions.ComputationRunTimeException;
import computation.model.ComputationTask;


/**
 * Represents running computation.
 * @author  malczyk.pawel@gmail.com
 */
public class RunableComputation implements Serializable, Performable {
	
	/**
	 * Logger.
	 */
	private static final Logger LOG = LoggerFactory.getLogger(RunableComputation.class);

	/**
	 * Serial.
	 */
	private static final long serialVersionUID = -6011864009485356423L;
	
	/**
	 * Ordered collection of computable tasks.
	 */
	private List<ComputationTask> tasks = new LinkedList<ComputationTask>();
	
	/**
	 * Holds mapping. previous output (Id) -> next input (Id)
	 * @uml.property  name="transitionMapping"
	 */
	private HashMap<String, ArrayList<String>> transitionMapping = new HashMap<String, ArrayList<String>>();
	
	/**
	 * Classes that corresponds to particular computation task.
	 */
	private List<Class<?>> taskClasses = new LinkedList<Class<?>>();
	
	
	/**
	 * Tasks map.
	 * @uml.property  name="tasks"
	 */
	private Map<ComputationTask, Class<?>> tasksMap = new HashMap<ComputationTask, Class<?>>();
	
	/**
	 * Constructor.
	 */
	public RunableComputation() {
		super();
	}
	
	/**
	 * Next input id for particular output id is returned.
	 * @param prevOutputId previous output name.
	 * @return next input name
	 */
	public ArrayList<String> getNextInput(String prevOutputId) {
		ArrayList<String> arrayList = transitionMapping.get(prevOutputId);
		return (arrayList!=null)?arrayList:new ArrayList<String>();
	}
	
	/**
	 * Returns true if {@link ComputationTask} is annotated with Routable annotation.
	 * @param computationTask computation task.
	 * @return true/false
	 */
	private boolean isRoutable(ComputationTask computationTask) {
		Class<?> taskClass = tasksMap.get(computationTask);
		boolean annotationPresent = taskClass.isAnnotationPresent(Routable.class);
		return annotationPresent;
	}
	
	/**
	 * Returns next input for routable tasks.
	 * @param computableTask routable task
	 * @throws ComputationRunTimeException excepion
	 * @return next input id.
	 */
	private String getNextInput( ComputableTask computableTask) throws ComputationRunTimeException {
		Class<?> taskClass = computableTask.getClass();
		Method [] methods = taskClass.getDeclaredMethods();
		String result = null;
		for(Method method : methods) {
			if (method.isAnnotationPresent(RouteTo.class)) {
				try {
					result  = (String) method.invoke(computableTask);
					break;
				} catch (IllegalArgumentException e) {
					LOG.warn("IllegalArgument caught", e);
				} catch (IllegalAccessException e) {
					LOG.warn("Illegal Access caught", e);
				} catch (InvocationTargetException e) {
					LOG.warn("Invocation exception caught", e);
				}
			}
		}
		return result;
	}
	
	/**
	 * Returns next task for given task.
	 * @param computationTask task 
	 * @param computableTask task
	 * @return computation task.
	 * @throws ComputationRunTimeException 
	 */
	public ComputationTask getNextTask(ComputationTask computationTask, ComputableTask computableTask) throws ComputationRunTimeException {
		
		ComputationTask result = null;
		if(isRoutable(computationTask)) {
			String nextInput = getNextInput(computableTask);
			if(nextInput == null) {
				throw new ComputationRunTimeException("Next Input not found");
			}
			
			if(!api.computation.Computation.END_COMPUTATION.equals(nextInput)) {
				for(ComputationTask tsk : tasks) {
					if (tsk.hasInput(nextInput)) {
						result = tsk;
						break;
					}
				}
			}
			
		} else {
			int index =0;
			for(int i=0; i<tasks.size(); i++) {
				if(tasks.get(i).equals(computationTask)) {
					index = i;
				}
			}
			int nextIndex = ++index;
			
			if(nextIndex < tasks.size()) {
				result = tasks.get(nextIndex);
			}
		}
		return result;
		
		
	}
	
	/**
	 * Adds computation task.
	 * @param computationTask add computation task
	 */
	public void addTask(ComputationTask computationTask) {
		tasks.add(computationTask);
	}

	/**
	 * Getter.
	 * @return  the tasks
	 * @uml.property  name="tasks"
	 */
	public List<ComputationTask> getTasks() {
		return tasks;
	}
	
	/**
	 * Adds task class.
	 * @param clazz task class.
	 */
	public void addTaskClass(Class<?> clazz) {
		taskClasses.add(clazz);
	}

	/**
	 * Getter.
	 * @return  the tasksMap
	 * @uml.property  name="tasks"
	 */
	public Map<ComputationTask, Class<?>> getTasksMap() {
		if(tasks.size() != taskClasses.size()) {
			throw new RuntimeException("Task classes count not equals classes count");
		}
		
		for(int i=0; i<tasks.size(); i++) {
			tasksMap.put(tasks.get(i), taskClasses.get(i));
		}
		//FIXME: moze byc dwa razy zainicjowane!!
		return tasksMap;
	}

	/**
	 * Getter.
	 * @return  the transitionMapping
	 * @uml.property  name="transitionMapping"
	 */
	public Map<String, ArrayList<String>> getTransitionMapping() {
		return transitionMapping;
	}

	/**
	 * Setter.
	 * @param transitionMapping  the transitionMapping to set
	 * @uml.property  name="transitionMapping"
	 */
	public void setTransitionMapping(HashMap<String, ArrayList<String>> transitionMapping) {
		this.transitionMapping = transitionMapping;
	}
	
	/**
	 * Adds mapping.
	 * @param prevOutId previous output.
	 * @param nextInId next input
	 */
	public void addMapping(String prevOutId, String nextInId) {
		ArrayList<String> nextInputs = transitionMapping.get(prevOutId);
		if(nextInputs == null) {
			nextInputs = new ArrayList<String>();
			nextInputs.add(nextInId);
			transitionMapping.put(prevOutId, nextInputs);
		} else {
			transitionMapping.get(prevOutId).add(nextInId);
		}
	}
	
	

}
