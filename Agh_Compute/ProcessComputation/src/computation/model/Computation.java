package computation.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import core.model.AbstractEntity;

/**
 * Represents single computation. It is created from XML definition.
 * @author  <a href="mailto:malczyk.pawel@gmail.com>malczyk.pawel@gmail.com</a>
 */
@NamedQueries({
	@NamedQuery(name="existingComputations", 
			query="SELECT cmp FROM Computation AS cmp"),
	@NamedQuery(name="existingComputationsCount", 
			query="SELECT COUNT(cmp) FROM Computation AS cmp"),
	@NamedQuery(name="byComputationId", 
			query="SELECT cmp FROM Computation AS cmp WHERE cmp.computationId=:compId")
})
@Entity
public class Computation extends AbstractEntity {
	
	/** Constant. */
	public static final String EXISTING = "existingComputations";
	
	/** Constant. */
	public static final String EXISTING_COUNT = "existingComputationsCount";
	
	/** Constant. */
	public static final String BY_COMPUTATION_ID = "byComputationId";
	
	/**
	 * Identifier.
	 * @uml.property  name="uniqueId"
	 */
	private int uniqueId;
	
	/**
	 * Computation identifier specified in xml.
	 * @uml.property  name="computationId"
	 */
	private String computationId;
	
	/**
	 * Computation description.
	 * @uml.property  name="description"
	 */
	private String description;
	
	/**
	 * Computation tasks within computation.
	 * @uml.property  name="computationTasks"
	 */
	private List<ComputationTask> computationTasks = new ArrayList<ComputationTask>();
	
	/**
	 * Computation transition within computations.
	 * @uml.property  name="computationTransitions"
	 */
	private List<ComputationTransition> computationTransitions = new ArrayList<ComputationTransition>();
	
	/**
	 * Computation settings.
	 * @uml.property  name="computationSettings"
	 */
	private List<ComputationSetting> computationSettings = new ArrayList<ComputationSetting>();
	
	
	/**
	 * Constructor.
	 */
	public Computation() { 
		super();
	}

	
	/** {@inheritDoc} */
	@Override
	@Transient
	public String[] getJsonData() {
		List<String> data = new ArrayList<String>();
		data.add(computationId);
		data.add(description);
		return data.toArray(new String[data.size()]);
	}

	/**
	 * {@inheritDoc} 
	 * @uml.property  name="uniqueId"
	 */
	@Id
	@Override
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
	 * @return  the computationId
	 * @uml.property  name="computationId"
	 */
	public String getComputationId() {
		return computationId;
	}


	/**
	 * Setter.
	 * @param computationId  the computationId to set
	 * @uml.property  name="computationId"
	 */
	public void setComputationId(String computationId) {
		this.computationId = computationId;
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
	
	
	/**
	 * Adds computation task to computation.
	 * @param task task being added to computation
	 */
	@Transient
	public void addTask(ComputationTask task) {
		computationTasks.add(task);
	}
	
	/**
	 * Adds transition to computation.
	 * @param trans transition being added to computation
	 */
	@Transient
	public void addTransiton(ComputationTransition trans) {
		trans.setComputation(this);
		
		for(ComputationTask compTask : getComputationTasks()) {
			ComputationTaskInput compInput = compTask.getInputByName(trans.getNextInputId());
			if(null!=compInput) {
				trans.setNextInput(compInput);
				break;
			}
			
		}
		
		for(ComputationTask compTask : getComputationTasks()) {
			ComputationTaskOutput compOutput = compTask.getOutputByName(trans.getPreviousOutputId());
			if(null!=compOutput) {
				trans.setPreviousOutput(compOutput);
				break;
			}
		}
		
		computationTransitions.add(trans);
	}
	
	/**
	 * Adds setting to settings.
	 * @param setting to be stored.
	 */
	@Transient
	public void addSetting(ComputationSetting setting) {
		setting.setComputation(this);
		computationSettings.add(setting);
	}


	/**
	 * Getter.
	 * @return  the computationTasks
	 * @uml.property  name="computationTasks"
	 */
	@Transient
	public List<ComputationTask> getComputationTasks() {
		return computationTasks;
	}
	
	


	/**
	 * Setter.
	 * @param computationTasks  the computationTasks to set
	 * @uml.property  name="computationTasks"
	 */
	public void setComputationTasks(List<ComputationTask> computationTasks) {
		this.computationTasks = computationTasks;
	}


	/**
	 * Getter.
	 * @return  the computationTransitions
	 * @uml.property  name="computationTransitions"
	 */
	@OneToMany(mappedBy="computation", cascade=CascadeType.ALL)
	public Collection<ComputationTransition> getComputationTransitions() {
		return computationTransitions;
	}


	/**
	 * Setter.
	 * @param computationTransitions  the computationTransitions to set
	 * @uml.property  name="computationTransitions"
	 */
	public void setComputationTransitions(
			List<ComputationTransition> computationTransitions) {
		this.computationTransitions = computationTransitions;
	}


	/**
	 * Getter.
	 * @return  the computationSettings
	 * @uml.property  name="computationSettings"
	 */
	@OneToMany(mappedBy="computation",  cascade={CascadeType.ALL})
	public List<ComputationSetting> getComputationSettings() {
		return computationSettings;
	}


	/**
	 * Setter.
	 * @param computationSettings  the computationSettings to set
	 * @uml.property  name="computationSettings"
	 */
	public void setComputationSettings(List<ComputationSetting> computationSettings) {
		this.computationSettings = computationSettings;
	}

}
