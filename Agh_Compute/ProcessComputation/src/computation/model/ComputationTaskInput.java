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
 * Input for computation task.
 * @author  malczyk.pawel@gmail.com
 */
@Entity
@NamedQueries({
	@NamedQuery(name="inputById",
			query="SELECT cti FROM ComputationTaskInput AS cti WHERE cti.id =:id AND cti.computationTask.taskId =:taskId")		
})
public class ComputationTaskInput extends AbstractEntity {
	
	/**
	 * Named query name.
	 */
	public static final String IN_BY_ID="inputById";
	
	/**
	 * key.
	 * @uml.property  name="uniqueId"
	 */
	private int uniqueId;
	
	/**
	 * Input name.
	 * @uml.property  name="name"
	 */
	private String name;
	
	/**
	 * Value name on value stack that will be used as an input value.
	 * @uml.property  name="valueName"
	 */
	private String valueName;
	
	/**
	 * Input id.
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
	public ComputationTaskInput() {
		super();
	}

	/** {@inheritDoc} */
	@Transient
	@Override
	public String[] getJsonData() {
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
		return "Input "+id + " - "+valueName;
	}

}
