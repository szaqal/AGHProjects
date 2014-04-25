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
 * Task output.
 * @author  malczyk.pawel@gmail.com
 */
@Entity
@NamedQueries({
	@NamedQuery(name="outputById",
			query="SELECT cto FROM ComputationTaskOutput AS cto WHERE cto.id =:id AND cto.computationTask.taskId =:taskId")		
})
public class ComputationTaskOutput extends AbstractEntity {
	
	/**
	 * Named query name.
	 */
	public static final String OUT_BY_ID="outputById";
	
	/**
	 * Input name.
	 * @uml.property  name="name"
	 */
	private String name;
	
	/**
	 * Unique id.
	 * @uml.property  name="uniqueId"
	 */
	private int uniqueId;
	
	
	/**
	 * If true/it will be returned in overall result.
	 * @uml.property  name="result"
	 */
	private boolean result;
	
	/**
	 * Value name on value stack that will be used as an output value.
	 * @uml.property  name="valueName"
	 */
	private String valueName;
	
	/**
	 * Output id.
	 * @uml.property  name="id"
	 */
	private String id;
	
	/**
	 * Computation task.
	 * @uml.property  name="computationTask"
	 * @uml.associationEnd  
	 */
	private ComputationTask computationTask;
	
	/**
	 * Constructor. 
	 */
	public ComputationTaskOutput() {
		super();
	}

	/** {@inheritDoc} */
	@Transient
	@Override
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
	 * @return  the name
	 * @uml.property  name="name"
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setter.
	 * @param name  the name to set
	 * @uml.property  name="name"
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Getter.
	 * @return  the computationTask
	 * @uml.property  name="computationTask"
	 */
	@ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE})
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
	
	/**
	 * Getter.
	 * @return  the result
	 * @uml.property  name="result"
	 */
	public boolean isResult() {
		return result;
	}

	/**
	 * Setter.
	 * @param result  the result to set
	 * @uml.property  name="result"
	 */
	public void setResult(boolean result) {
		this.result = result;
	}
	
	/**
	 * Setter.
	 * @param result string result representation.
	 */
	@Transient
	public void setStrResult(String result) {
		this.result = Boolean.valueOf(result);
	}

	/**
	 * Getter.
	 * @return  the id
	 * @uml.property  name="id"
	 */
	public String getId() {
		return id;
	}

	/**
	 * Setter.
	 * @param id  the id to set
	 * @uml.property  name="id"
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Getter.
	 * @return  the valueName
	 * @uml.property  name="valueName"
	 */
	public String getValueName() {
		return valueName;
	}

	/**
	 * Setter.
	 * @param valueName  the valueName to set
	 * @uml.property  name="valueName"
	 */
	public void setValueName(String valueName) {
		this.valueName = valueName;
	}
	
	@Override
	public String toString() {
		return "Output " + id + " - "+valueName;
	}

}
