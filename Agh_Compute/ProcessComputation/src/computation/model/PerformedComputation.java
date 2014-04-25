package computation.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import core.utils.StringUtils;

/**
 * Represents performed or being performed computation.
 * @author  malczyk.pawel@gmail.com
 */
@Entity
@NamedQueries({
	@NamedQuery(name="existingPerformedComputations", 
			query="SELECT cmp FROM PerformedComputation cmp"),
	@NamedQuery(name="existingPerformedComputationsCount", 
			query="SELECT COUNT(cmp) FROM PerformedComputation cmp")
})
public class PerformedComputation extends AbstractEntity {
	
	/** Constant. */
	public static final String EXISTING = "existingPerformedComputations";
	
	/** Constant. */
	public static final String EXISTING_COUNT = "existingPerformedComputationsCount";
	
	/**
	 * Unique identifier.
	 * @uml.property  name="uniqueId"
	 */
	private int uniqueId;
	
	/**
	 * Execution start date.
	 * @uml.property  name="startDate"
	 */
	private Date startDate;
	
	/**
	 * Execution end date.
	 * @uml.property  name="endDate"
	 */
	private Date endDate;
	
	/**
	 * Original computation.
	 * @uml.property  name="computation"
	 * @uml.associationEnd  
	 */
	private Computation computation;
	
	/**
	 * Constructor.
	 */
	public PerformedComputation() {
		super();
	}

	/** {@inheritDoc} */
	@Transient
	@Override
	public String[] getJsonData() {
		List<String> data = new ArrayList<String>();
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");		
		data.add(String.valueOf(uniqueId));
		data.add(startDate==null ? StringUtils.EMPTY:format.format(startDate));
		data.add(endDate==null ? StringUtils.EMPTY : format.format(endDate));
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
	 * @return  the startDate
	 * @uml.property  name="startDate"
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * Setter.
	 * @param startDate  the startDate to set
	 * @uml.property  name="startDate"
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * Getter.
	 * @return  the endDate
	 * @uml.property  name="endDate"
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * Setter.
	 * @param endDate  the endDate to set
	 * @uml.property  name="endDate"
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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
	
	/** {@inheritDoc} */
	@Override
	public String toString() {
		return super.toString();
	}

}
