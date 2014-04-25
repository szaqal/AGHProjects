package computation.model;

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
 * Stores node <-> performer mapping.
 * @author  malczyk.pawel@gmail.com
 */
@Entity
@NamedQueries({
	@NamedQuery(name="retrieveByMapping",
			query="SELECT np FROM NodeMapping AS np WHERE np.mapping=:mapping AND np.ownerId=:ownerId"),
	@NamedQuery(name="retrieveByOwner",
			query="SELECT np FROM NodeMapping AS np WHERE np.ownerId=:ownerId"),
	@NamedQuery(name="mappingByOwnerCount", 
			query="SELECT COUNT(np) FROM NodeMapping AS np WHERE np.ownerId=:ownerId")			
})
public class NodeMapping extends AbstractEntity {
	
	/**
	 * Constant named query name.
	 */
	public static final String BY_MAPPING = "retrieveByMapping";
	
	/**
	 * Constant. named query.
	 */
	public static final String BY_OWNER = "retrieveByOwner";
	
	/**
	 * Constant. named query.
	 */
	public static final String BY_OWNER_COUNT = "mappingByOwnerCount";
	
	/**
	 * Unique identifier.
	 * @uml.property  name="uniqueId"
	 */
	private int uniqueId;
	
	
	/**
	 * Owning user id.
	 * @uml.property  name="ownerId"
	 */
	private int ownerId;
	
	/**
	 * Node ip or DNS name.
	 * @uml.property  name="node"
	 */
	private String node;
	
	/**
	 * Node mapping.
	 * @uml.property  name="mapping"
	 */
	private String mapping;
	
	/**
	 * Constructor.
	 */
	public NodeMapping() {
		super();
	}


	/** {@inheritDoc} */
	@Transient
	@Override
	public String[] getJsonData() {
		List<String> data = new ArrayList<String>();
		data.add(node);
		data.add(mapping);
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
	 * @return  the ownerId
	 * @uml.property  name="ownerId"
	 */
	public int getOwnerId() {
		return ownerId;
	}


	/**
	 * Setter.
	 * @param ownerId  the ownerId to set
	 * @uml.property  name="ownerId"
	 */
	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}


	/**
	 * Getter.
	 * @return  the node
	 * @uml.property  name="node"
	 */
	public String getNode() {
		return node;
	}


	/**
	 * Setter.
	 * @param node  the node to set
	 * @uml.property  name="node"
	 */
	public void setNode(String node) {
		this.node = node;
	}


	/**
	 * Getter.
	 * @return  the mapping
	 * @uml.property  name="mapping"
	 */
	public String getMapping() {
		return mapping;
	}


	/**
	 * Setter.
	 * @param mapping  the mapping to set
	 * @uml.property  name="mapping"
	 */
	public void setMapping(String mapping) {
		this.mapping = mapping;
	}
	
	@Override
	public String toString() {
		return node + " -> "+ mapping;
	}
	
	
	

}
