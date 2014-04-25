package computation.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Transient;

import core.model.AbstractEntity;

/**
 * Links computation with computation tasks.
 * @author  malczyk.pawel@gmail.com
 */
@Entity
@NamedQueries({
	@NamedQuery(name="taskByComputationId", query="SELECT cft FROM ComputationForTask AS cft WHERE cft.computation.uniqueId=:cftId"),
	@NamedQuery(name="byComputationIdTaskId", query="SELECT cft FROM ComputationForTask AS cft WHERE cft.computation.uniqueId=:compId AND cft.computationTask.uniqueId=:compTaskId"),
	@NamedQuery(name="firstTaskByComputationId", query="SELECT cft FROM ComputationForTask AS cft WHERE cft.computation.uniqueId=:cftId AND cft.computationTask.start=true")
})
public class ComputationForTask extends AbstractEntity {
	
	/**
	 * Constant Named query name.
	 */
	public static final String BY_COMP_ID = "taskByComputationId";

	/**
	 * Constant Named query name.
	 */
	public static final String BY_COMP_TASK_ID ="byComputationIdTaskId";
	
	/**
	 * Constant Named query name.
	 */
	public static final String FIRST_BY_COMP_ID = "firstTaskByComputationId";
	
	/**
	 * id.
	 * @uml.property  name="uniqueId"
	 */
	private int uniqueId;
	
	/**
	 * computation.
	 * @uml.property  name="computation"
	 * @uml.associationEnd  
	 */
	private Computation computation;
	
	/**
	 * task.
	 * @uml.property  name="computationTask"
	 * @uml.associationEnd  
	 */
	private ComputationTask computationTask;
	
	/**
	 * Constructor.
	 */
	public ComputationForTask() {
		super();
	};

	/**
	 * Getter.
	 * @return  the uniqueId
	 * @uml.property  name="uniqueId"
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int getUniqueId() {
		return uniqueId;
	}

	/**
	 * Setter.
	 * @param uniqueId  the uniqueId to set
	 * @uml.property  name="uniqueId"
	 */
	public void setUniqueId(int uniqueId) {
		this.uniqueId = uniqueId;
	}

	/**
	 * Getter.
	 * @return  the computation
	 * @uml.property  name="computation"
	 */
	@ManyToOne
	public Computation getComputation() {
		return computation;
	}

	/**
	 * Setter.
	 * @param computation  the computation to set
	 * @uml.property  name="computation"
	 */
	public void setComputation(Computation computation) {
		this.computation = computation;
	}

	/**
	 * Getter.
	 * @return  the computationTask
	 * @uml.property  name="computationTask"
	 */
	@ManyToOne(cascade=CascadeType.ALL)
	public ComputationTask getComputationTask() {
		return computationTask;
	}

	/**
	 * Setter.
	 * @param computationTask  the computationTask to set
	 * @uml.property  name="computationTask"
	 */
	public void setComputationTask(ComputationTask computationTask) {
		this.computationTask = computationTask;
	}

	/**{@inheritDoc}*/
	@Transient
	@Override
	public String[] getJsonData() {
		return null;
	}
}
