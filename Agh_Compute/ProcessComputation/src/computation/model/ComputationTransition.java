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
 * Represents transition between computational tasks.
 * @author  malczyk.pawel@gmail.com
 */
@Entity
@NamedQueries({
	@NamedQuery(name="byPrevAndNext", query="SELECT ct FROM ComputationTransition AS ct WHERE ct.previousOutput.id=:prevId AND ct.nextInput.id=:nextId")
})
public class ComputationTransition extends AbstractEntity {
	
	/**
	 * Named query name.
	 */
	public static final String BY_PREV_NEXT = "byPrevAndNext";
	
	/**
	 * Identifier.
	 * @uml.property  name="uniqueId"
	 */
	private int uniqueId;
	
	/**
	 * Transition id.
	 * @uml.property  name="transitionId"
	 */
	private String transitionId;
	
	/**
	 * Start of transition (output of previous task).
	 * @uml.property  name="previousOutput"
	 * @uml.associationEnd  
	 */
	private ComputationTaskOutput previousOutput;
	
	/**
	 * End of transition (input of next task).
	 * @uml.property  name="nextInput"
	 * @uml.associationEnd  
	 */
	private ComputationTaskInput nextInput;
	
	/**
	 * Previous output identifier.
	 * @uml.property  name="previousOutputId"
	 */
	private String previousOutputId;
	
	/**
	 * Next input identifier.
	 * @uml.property  name="nextInputId"
	 */
	private String nextInputId;
	
	/**
	 * Computation to which this task belongs.
	 * @uml.property  name="computation"
	 * @uml.associationEnd  
	 */
	private Computation computation;
	
	/**
	 * Constructor.
	 */
	public ComputationTransition() {
		super();
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
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Override
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
	 * @return  the previousOutput
	 * @uml.property  name="previousOutput"
	 */
	@ManyToOne(cascade=CascadeType.ALL)
	public ComputationTaskOutput getPreviousOutput() {
		return previousOutput;
	}

	/**
	 * Setter.
	 * @param previousOutput  the previousOutput to set
	 * @uml.property  name="previousOutput"
	 */
	public void setPreviousOutput(ComputationTaskOutput previousOutput) {
		this.previousOutput = previousOutput;
	}

	/**
	 * Getter.
	 * @return  the nextInputId
	 * @uml.property  name="nextInput"
	 */
	@ManyToOne(cascade=CascadeType.ALL)
	public ComputationTaskInput getNextInput() {
		return nextInput;
	}

	/**
	 * Setter.
	 * @param nextInputId  the nextInputId to set
	 * @uml.property  name="nextInput"
	 */
	public void setNextInput(ComputationTaskInput nextInputId) {
		this.nextInput = nextInputId;
	}

	/**
	 * Getter.
	 * @return  the nextInputId
	 * @uml.property  name="nextInputId"
	 */
	@Transient
	public String getNextInputId() {
		return nextInputId;
	}

	/**
	 * Setter.
	 * @param nextInputId  the nextInputId to set
	 * @uml.property  name="nextInputId"
	 */
	public void setNextInputId(String nextInputId) {
		this.nextInputId = nextInputId;
	}

	/**
	 * Getter.
	 * @return  the previousOutpuId
	 * @uml.property  name="previousOutputId"
	 */
	@Transient
	public String getPreviousOutputId() {
		return previousOutputId;
	}

	/**
	 * Setter.
	 * @param previousOutpuId  the previousOutpuId to set
	 * @uml.property  name="previousOutputId"
	 */
	public void setPreviousOutputId(String previousOutpuId) {
		this.previousOutputId = previousOutpuId;
	}

	/**
	 * Getter.
	 * @return  the transitionId
	 * @uml.property  name="transitionId"
	 */
	public String getTransitionId() {
		return transitionId;
	}

	/**
	 * Setter.
	 * @param transitionId  the transitionId to set
	 * @uml.property  name="transitionId"
	 */
	public void setTransitionId(String transitionId) {
		this.transitionId = transitionId;
	}


}
