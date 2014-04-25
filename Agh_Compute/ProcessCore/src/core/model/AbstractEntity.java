package core.model;



/**
 * Base entity class. 
 * @author    <a href="mailto:malczyk.pawel@gmail.com">malczyk.pawel@gmail.com</a>
 */
public abstract class AbstractEntity {
	
	/**
	 * Constructor.
	 */
	public AbstractEntity() { }

	/**
	 * Getter.
	 * @return    the uniqueId
	 * @uml.property  name="uniqueId"
	 */
	public abstract int getUniqueId();

	/**
	 * Setter.
	 * @param uniqueId    the uniqueId to set
	 * @uml.property  name="uniqueId"
	 */
	public abstract void setUniqueId(int uniqueId);
	
	/** 
	 * Returns array of properties which will be used when generating json.
	 * @return properties as array
	 */
	public abstract String[] getJsonData();
	
	
}
