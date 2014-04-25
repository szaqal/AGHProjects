package auth.model;

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
 * Connects user with its group.
 * @author  <a href="mailto:malczyk.pawel@gmail.com">malczyk.pawel@gmail.com</a>
 */
@Entity
@NamedQueries({
	@NamedQuery(name="byGroup", query="SELECT ug FROM UserGroup ug WHERE ug.grp.uniqueId=?1"),
	@NamedQuery(name="byUser", query="SELECT ug FROM UserGroup ug WHERE ug.usr.uniqueId=?1"),
	@NamedQuery(name="byGroupAndUser", query="SELECT ug FROM UserGroup ug WHERE ug.grp.uniqueId=?1 AND ug.usr.uniqueId=?2")
})
public class UserGroup extends AbstractEntity {
	
	/**
	 * Constant.
	 */
	public static final String BY_USER = "byUser";
	
	/**
	 * Constant.
	 */
	public static final String BY_GROUP = "byGroup";
	
	/**
	 * Constant.
	 */
	public static final String BY_GRP_USR = "byGroupAndUser";
	
	/**
	 * Identifier.
	 * @uml.property  name="uniqueId"
	 */
	private int uniqueId;
	
	/**
	 * User's group.
	 * @uml.property  name="grp"
	 * @uml.associationEnd  
	 */
	private Group grp;

	/**
	 * User.
	 * @uml.property  name="usr"
	 * @uml.associationEnd  
	 */
	private User usr;
	
	/**
	 * Constructor.
	 */
	public UserGroup() {
		super();
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
	 * @return  the group
	 * @uml.property  name="grp"
	 */
	@ManyToOne
	public Group getGrp() {
		return grp;
	}

	/**
	 * Setter.
	 * @param group  the group to set
	 * @uml.property  name="grp"
	 */
	public void setGrp(Group group) {
		this.grp = group;
	}

	/**
	 * Getter.
	 * @return  the user
	 * @uml.property  name="usr"
	 */
	@ManyToOne
	public User getUsr() {
		return usr;
	}

	/**
	 * Setter.
	 * @param user  the user to set
	 * @uml.property  name="usr"
	 */
	public void setUsr(User user) {
		this.usr = user;
	}

	/** {@inheritDoc} */
	@Override
	@Transient
	public String[] getJsonData() {
		// TODO Auto-generated method stub
		return null;
	}


}
