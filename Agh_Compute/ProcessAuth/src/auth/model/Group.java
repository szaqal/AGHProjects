package auth.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Transient;

import core.model.AbstractEntity;

/**
 * Represents Single Group.
 * @author  <a href="mailto:malczyk.pawel@gmail.com">malczyk.pawel@gmail.com</a>
 */
@NamedQueries({
	@NamedQuery(name="existingGroups", 
			query="SELECT grp FROM Group AS grp"),
	@NamedQuery(name="existingGroupsCount", 
			query="SELECT COUNT(grp) FROM Group AS grp")		
})
@Entity
public class Group extends AbstractEntity {
	
	/** Constant. */
	public static final String EXISTING = "existingGroups";
	
	/** Constant. */
	public static final String EXISTING_COUNT = "existingGroupsCount";

	/**
	 * Unique identifier.
	 * @uml.property  name="uniqueId"
	 */
	private int uniqueId;
	
	/**
	 * Group name.
	 * @uml.property  name="name"
	 */
	private String name;
	
	/**
	 * Set of rights assigned to group.
	 * @uml.property  name="rights"
	 */
	private String rights;
	
	/**
	 * 
	 * Constructor.
	 */
	public Group() { 
		super();
	}
	
	/**
	 * 
	 * Constructor.
	 * @param name name of the group
	 */
	public Group(String name) {
		this();
		setName(name);
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
	
	
	/** {@inheritDoc} */
	@Transient
	public String[] getJsonData() {
		List<String> data = new ArrayList<String>();
		data.add(name);
		data.add(getRights());
		return data.toArray(new String[data.size()]);
	}


	/**
	 * Getter.
	 * @return  the rights
	 * @uml.property  name="rights"
	 */
	public String getRights() {
		return rights;
	}

	/**
	 * Setter.
	 * @param rights  the rights to set
	 * @uml.property  name="rights"
	 */
	public void setRights(String rights) {
		this.rights = rights;
	}



}
