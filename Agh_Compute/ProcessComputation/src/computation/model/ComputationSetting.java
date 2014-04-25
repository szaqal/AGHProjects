package computation.model;

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
 * Initial computation settings.
 * @author  malczyk.pawel@gmail.com
 */
@Entity
@NamedQueries({
	@NamedQuery(name="retrieveByComputation",
			query="SELECT cs FROM ComputationSetting AS cs WHERE cs.computation.uniqueId=:id")
})
public class ComputationSetting extends AbstractEntity {
	
	/**
	 * Constant named query name.
	 */
	public static final String SETTING_BY_COMPUTATION  = "retrieveByComputation";
	
	/**
	 * pk.
	 * @uml.property  name="uniqueId"
	 */
	private int uniqueId;
	
	/**
	 * setting name.
	 * @uml.property  name="name"
	 */
	private String name;
	
	/**
	 * setting value.
	 * @uml.property  name="val"
	 */
	private String val;
	
	/**
	 * Description.
	 */
	private String description;
	
	/**
	 * Computation to which these settings are reffering to.
	 * @uml.property  name="computation"
	 * @uml.associationEnd  
	 */
	private Computation computation;
	
	
	
	/**
	 * Constructor.
	 */
	public ComputationSetting() {
	}

	/**
	 * Getter.
	 * @return  the uniqueId
	 * @uml.property  name="uniqueId"
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Override
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


	@Transient
	@Override
	public String[] getJsonData() {
		return null;
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
	 * @return  the val
	 * @uml.property  name="val"
	 */
	public String getVal() {
		return val;
	}

	/**
	 * Setter.
	 * @param val  the val to set
	 * @uml.property  name="val"
	 */
	public void setVal(String val) {
		this.val = val;
	}

	/**
	 * Getter.
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Setter.
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
}
