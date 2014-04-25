package computation.model;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import core.model.AbstractEntity;

/**
 * Single computable task (Persistent entity).
 * @author  <a href="mailto:malczyk.pawel@gmail.com">malczyk.pawel@gmail.com</a>.
 */
@Entity
@NamedQueries({
	@NamedQuery(name="byTaskId", 
			query="SELECT ct FROM ComputationTask AS ct WHERE ct.taskId =:taskId")		
})
public class ComputationTask extends AbstractEntity {
	
	/**
	 * Constant.
	 */
	public static final String BY_TASK_ID = "byTaskId";
	
	
	/**
	 * Task identifier.
	 * @uml.property  name="uniqueId"
	 */
	private int uniqueId;
	
	/**
	 * Task identifier (xml id attribute).
	 * @uml.property  name="taskId"
	 */
	private String taskId;
	
	/**
	 * Name of the task.
	 * @uml.property  name="taskName"
	 */
	private String taskName;
	
	/**
	 * Designates start of the process.
	 * @uml.property  name="start"
	 */
	private boolean start;
	
	/**
	 * Task class name.
	 * @uml.property  name="className"
	 */
	private String className;
	
	/**
	 * Performing node.
	 * @uml.property  name="performer"
	 */
	private String performer;
	
	/**
	 * Task description.
	 * @uml.property  name="description"
	 */
	private String description;
	
	/**
	 * List of inputs.
	 * @uml.property  name="inputs"
	 */
	private Collection<ComputationTaskInput> inputs=new ArrayList<ComputationTaskInput>();
	
	/**
	 * List of outputs.
	 * @uml.property  name="outputs"
	 */
	private Collection<ComputationTaskOutput> outputs=new ArrayList<ComputationTaskOutput>();
	
	/**
	 * Constructor.
	 */
	public ComputationTask() { }
	
	/**
	 * Adds task output.
	 * @param taskOutput output of the task.
	 */
	@Transient
	public void addOutput(ComputationTaskOutput taskOutput) {
		taskOutput.setComputationTask(this);
		outputs.add(taskOutput);
	}
	
	/**
	 * Adds task input.
	 * @param taskInput entry point for the task.
	 */
	@Transient
	public void addInput(ComputationTaskInput taskInput) {
		taskInput.setComputationTask(this);
		inputs.add(taskInput);
	}
	
	

	/** {@inheritDoc} */
	@Override
	@Transient
	public String[] getJsonData() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * {@inheritDoc} 
	 * @uml.property  name="uniqueId"
	 */
	@Override
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int getUniqueId() {
		return uniqueId;
	}


	/**
	 * {@inheritDoc} 
	 * @uml.property  name="uniqueId"
	 */
	@Override
	public void setUniqueId(int uniqueId) {
		this.uniqueId = uniqueId;
	}

	/**
	 * Getter.
	 * @return  the taskId
	 * @uml.property  name="taskId"
	 */
	public String getTaskId() {
		return taskId;
	}

	/**
	 * Setter.
	 * @param taskId  the taskId to set
	 * @uml.property  name="taskId"
	 */
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	/**
	 * Getter.
	 * @return  the taskName
	 * @uml.property  name="taskName"
	 */
	public String getTaskName() {
		return taskName;
	}

	/**
	 * Setter.
	 * @param taskName  the taskName to set
	 * @uml.property  name="taskName"
	 */
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}



	/**
	 * Getter.
	 * @return  the start
	 * @uml.property  name="start"
	 */
	public boolean getStart() {
		return start;
	}



	/**
	 * Setter.
	 * @param start  the start to set
	 * @uml.property  name="start"
	 */
	public void setStart(boolean start) {
		this.start = start;
	}



	/**
	 * Getter.
	 * @return  the className
	 * @uml.property  name="className"
	 */
	public String getClassName() {
		return className;
	}



	/**
	 * Setter.
	 * @param className  the className to set
	 * @uml.property  name="className"
	 */
	public void setClassName(String className) {
		this.className = className;
	}



	/**
	 * Getter.
	 * @return  the outputs
	 * @uml.property  name="outputs"
	 */
	@OneToMany(mappedBy="computationTask", cascade={CascadeType.ALL})
	public Collection<ComputationTaskOutput> getOutputs() {
		return outputs;
	}



	/**
	 * Setter.
	 * @param outputs  the outputs to set
	 * @uml.property  name="outputs"
	 */
	public void setOutputs(Collection<ComputationTaskOutput> outputs) {
		this.outputs = outputs;
	}



	/**
	 * Getter.
	 * @return  the inputs
	 * @uml.property  name="inputs"
	 */
	@OneToMany(mappedBy="computationTask", cascade={CascadeType.ALL}, fetch=FetchType.EAGER)
	public Collection<ComputationTaskInput> getInputs() {
		return inputs;
	}
	
	
	/**
	 * Returns input by it's name.
	 * @param name input name.
	 * @return {@link ComputationTaskInput}
	 */
	@Transient
	public ComputationTaskInput getInputByName(String name) {
		for(ComputationTaskInput input : getInputs()) {
			if(name.equals(input.getName())) {
				return input;
			}
		}
		return null;
	}
	
	/**
	 * Returns input by it's id.
	 * @param id input id.
	 * @return {@link ComputationTaskInput}
	 */
	@Transient
	public ComputationTaskInput getInputById(String id) {
		for(ComputationTaskInput input : getInputs()) {
			if(id.equals(input.getId())) {
				return input;
			}
		}
		return null;
	}
	
	/**
	 * Returns true if input is owned by this ComputationTask.
	 * @param inputId checked input id.
	 * @return true/false
	 */
	@Transient
	public boolean hasInput(String inputId) {
		return getInputById(inputId)!= null;
	}
	
	
	/**
	 * Returns output by it's name.
	 * @param name output name.
	 * @return {@link ComputationTaskOutput}
	 */
	@Transient
	public ComputationTaskOutput getOutputByName(String name) {
		for(ComputationTaskOutput output : getOutputs()) {
			if(name.equals(output.getName())) {
				return output;
			}
		}
		return null;
	}
	
	/**
	 * Returns output by it's name.
	 * @param id output name.
	 * @return {@link ComputationTaskOutput}
	 */
	@Transient
	public ComputationTaskOutput getOutputById(String id) {
		for(ComputationTaskOutput output : getOutputs()) {
			if(id.equals(output.getId())) {
				return output;
			}
		}
		return null;
	}



	/**
	 * Setter.
	 * @param inputs  the inputs to set
	 * @uml.property  name="inputs"
	 */
	public void setInputs(Collection<ComputationTaskInput> inputs) {
		this.inputs = inputs;
	}

	/**
	 * Getter. !! Transient used by Digester ONLY !!
	 * @return  the performer
	 * @uml.property  name="performer"
	 */
	@Transient
	public String getPerformer() {
		return performer;
	}

	/**
	 * Setter.
	 * @param performer  the performer to set
	 * @uml.property  name="performer"
	 */
	public void setPerformer(String performer) {
		this.performer = performer;
	}

	/**
	 * Getter.
	 * @return  the description
	 * @uml.property  name="description"
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Setter.
	 * @param description  the description to set
	 * @uml.property  name="description"
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/** {@inheritDoc} */
	@Override
	public String toString() {
		return "Computation task [ID:" +taskId + " Name:" + taskName + "]";
	}

}
