package computation;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import computation.model.ComputingNode;

import core.utils.StringUtils;


/**
 * Contains information about current registered nodes.
 * @author  <a href="mailto:malczyk.pawel@gmail.com">malczyk.pawel@gmail.com</a>
 */
public final class NodesHolder {
	
	/**
	 * Instance.
	 * @uml.property  name="instance"
	 * @uml.associationEnd  
	 */
	private static NodesHolder instance;
	
	/** Computing nodes map. */
	private Map<String, ComputingNode> nodes = Collections.synchronizedMap(new HashMap<String, ComputingNode>());
	
	/**
	 * Private
	 * Constructor.
	 */
	private NodesHolder() { }

	/**
	 * Getter.
	 * @return  the instance
	 * @uml.property  name="instance"
	 */
	public static NodesHolder getInstance() {
		synchronized (NodesHolder.class) {
			if (instance == null) {
				instance = new NodesHolder();
			}
			return instance;
		}
	}
	
	/**
	 * Removes computing node.
	 * @param computingNode {@link ComputingNode}
	 */
	public void removeNode(ComputingNode computingNode) {
		nodes.remove(computingNode.nodeKey());
	}
	
	
	/**
	 * Registers new node.
	 * @param node nodes.
	 */
	public void registerNode(ComputingNode node) {
		if(StringUtils.isNotEmpty(node.nodeKey())) {
			nodes.put(node.nodeKey(), node);
		}
	}
	
	/**
	 * Returns computing nodes.
	 * @return returns computing nodes
	 */
	public Collection<ComputingNode> retrieveComputingNodes() {
		return nodes.values();
	}
	
	/**
	 * Returns registered node.
	 * @param key key in the map.
	 * @return registered node
	 */
	public ComputingNode retrieveNode(String key) {
		return nodes.get(key);
	}
}
