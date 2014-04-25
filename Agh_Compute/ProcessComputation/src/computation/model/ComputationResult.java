package computation.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Transient;

import auth.model.User;
import core.model.AbstractEntity;

/**
 * @author  malczyk.pawel@gmail.com
 */
@NamedQueries({
	@NamedQuery(name="existing", 
			query="SELECT enity FROM ComputationResult AS enity"),
	@NamedQuery(name="existingForUser", 
			query="SELECT entity FROM ComputationResult as entity WHERE entity.owner.uniqueId=:userId"),
	@NamedQuery(name="existingForUserCount", 
			query="SELECT COUNT(entity) FROM ComputationResult AS entity WHERE entity.owner.uniqueId=:userId")		
})
@Entity
public class ComputationResult extends AbstractEntity {
	
	
	/**
	 * Named query name.
	 */
	public static final String EXISTING = "existing";
	
	/**
	 * Named query name.
	 */
	public static final String EXISTING_BY_USR = "existingForUser";
	/**
	 * Named query name.
	 */
	public static final String EXISTING_BY_USR_COUNT = "existingForUserCount";
	
	/** File size. */
	private static final int FILE_CONTENT_SIZE = 1000000;
	
	/**
	 * Unique id.
	 * @uml.property  name="uniqueId"
	 */
	private int uniqueId;
	
	/**
	 * Create date.
	 * @uml.property  name="createDate"
	 */
	private Date createDate;
	
	/**
	 * Result.
	 * @uml.property  name="result"
	 */
	private byte [] result;
	
	/**
	 * Owner.
	 * @uml.property  name="owner"
	 * @uml.associationEnd  
	 */
	private User owner;
	
	/**
	 * Computation to which this result belongs.
	 * @uml.property  name="performedComputation"
	 * @uml.associationEnd  
	 */
	private PerformedComputation performedComputation;
	
	/**
	 * Constructor.
	 */
	public ComputationResult() {
		super();
	}
	
	/**
	 * Constructor.
	 * @param result computation result
	 */
	public ComputationResult(byte[] result) {
		this.result = result;
	}

	/** {@inheritDoc} */
	@Transient
	@Override
	public String[] getJsonData() {
		List<String> data = new ArrayList<String>();
		data.add(new SimpleDateFormat("yyy.MM.dd HH:mm:ss").format(createDate));
		data.add(getPerformedComputation().getComputation().getComputationId());
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
	 * @return  the createDate
	 * @uml.property  name="createDate"
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * Setter.
	 * @param createDate  the createDate to set
	 * @uml.property  name="createDate"
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * Getter.
	 * @return  the result
	 * @uml.property  name="result"
	 */
	@Lob
	@Column(length=FILE_CONTENT_SIZE)
	public byte [] getResult() {
		return result;
	}

	/**
	 * Setter.
	 * @param result  the result to set
	 * @uml.property  name="result"
	 */
	public void setResult(byte [] result) {
		this.result = result;
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
	 * @return  the user
	 * @uml.property  name="owner"
	 */
	@ManyToOne
	public User getOwner() {
		return owner;
	}

	/**
	 * Setter.
	 * @param user  the user to set
	 * @uml.property  name="owner"
	 */
	public void setOwner(User user) {
		this.owner = user;
	}

}
