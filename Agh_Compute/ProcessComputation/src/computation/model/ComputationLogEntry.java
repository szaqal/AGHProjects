package computation.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
 * Represents single event while computation is in progress.
 * @author  malczyk.pawel@gmail.com
 */
@Entity
@NamedQueries({
	@NamedQuery(name="byPerformedComputation", 
			query="SELECT cl FROM ComputationLogEntry AS cl WHERE cl.performedComputation.uniqueId=:performedComputationId"),
	@NamedQuery(name="byPerformedComputationCount", 
			query="SELECT COUNT(cl) FROM ComputationLogEntry AS cl WHERE cl.performedComputation.uniqueId=:performedComputationId")
})
public class ComputationLogEntry extends AbstractEntity {
	
	/**
	 * Named query name.
	 */
	public static final String BY_PERFORMED_COMPUTATION = "byPerformedComputation";
	
	/**
	 * Named query name.
	 */
	public static final String BY_PERFORMED_COMPUTATION_COUNT="byPerformedComputationCount";
	
	/**
	 * Unique identifier.
	 * @uml.property  name="uniqueId"
	 */
	private int uniqueId;
	
	/**
	 * Log message.
	 * @uml.property  name="message"
	 */
	private String message;
	
	/**
	 * Entry create time.
	 * @uml.property  name="createTime"
	 */
	private Date createTime;
	
	/**
	 * Performed computation.
	 * @uml.property  name="performedComputation"
	 * @uml.associationEnd  
	 */
	private PerformedComputation performedComputation;
	
	/**
	 * Constructor.
	 */
	public ComputationLogEntry() {
		super();
	}
	
	/**
	 * Constructor.
	 * @param message log message
	 * @param performedComputation performed computation
	 */
	public ComputationLogEntry(String message, PerformedComputation performedComputation) {
		setCreateTime(new Date());
		setMessage(message);
		setPerformedComputation(performedComputation);
	}

	/** {@inheritDoc} */
	@Transient
	@Override
	public String[] getJsonData() {
		List<String> data = new ArrayList<String>();
		data.add(message);
		data.add(createTime.toString());
		return data.toArray(new String[data.size()]);
		
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
	 * @return  the performedComputation
	 * @uml.property  name="performedComputation"
	 */
	@ManyToOne
	public PerformedComputation getPerformedComputation() {
		return performedComputation;
	}

	/**
	 * Setter.
	 * @param performedComputation  the performedComputation to set
	 * @uml.property  name="performedComputation"
	 */
	public void setPerformedComputation(PerformedComputation performedComputation) {
		this.performedComputation = performedComputation;
	}

	/**
	 * Getter.
	 * @return  the message
	 * @uml.property  name="message"
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Setter.
	 * @param message  the message to set
	 * @uml.property  name="message"
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Getter.
	 * @return  the createTime
	 * @uml.property  name="createTime"
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * Setter.
	 * @param createTime  the createTime to set
	 * @uml.property  name="createTime"
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
