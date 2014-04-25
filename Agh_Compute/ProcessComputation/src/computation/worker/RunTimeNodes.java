package computation.worker;

import java.util.Map;

import computation.model.ComputingNode;

/**
 * Holds information about current node load.
 * 
 * @author malczyk.pawel@gmail.com
 *
 */
public final class RunTimeNodes {
	/**
	 * Maintains currently used salve computing nodes.
	 */
	private static Map<ComputingNode, Integer> nodesInUse = new java.util.Hashtable<ComputingNode, Integer>();
	
	
	/**
	 * Constructor.
	 */
	private RunTimeNodes() { }
	
	
	/**
	 * Checks whether there is already node in use.
	 * @param computingNode comuting note to check
	 * @return true/false
	 */
	public static  boolean contains(ComputingNode computingNode) {
		synchronized (RunTimeNodes.class) {
			boolean contains = nodesInUse.keySet().contains(computingNode);
			if(!contains) {
				nodesInUse.put(computingNode, 1);
			}
			return contains;
		}
	}
	
	/**
	 * Returns least loded computing node.
	 * @return least loded computing node.
	 */
	public static ComputingNode leastLoaded() {
		synchronized (RunTimeNodes.class) {
			Integer load = Integer.MAX_VALUE;
			ComputingNode result = null;
			for(ComputingNode node : nodesInUse.keySet()) {
				Integer nodeLoad = nodesInUse.get(node);
				if (nodeLoad < load) {
					load = nodeLoad;
					result = node;
				}
			}
			
			int currLoad = nodesInUse.get(result);
			currLoad +=1;
			nodesInUse.put(result, currLoad);
			
			return result;
		}
	}
	
	/**
	 * Returns load for given Computing node.
	 * @param computingNode computing node.
	 * @return current load
	 */
	public static Integer load(ComputingNode computingNode) {
		synchronized (RunTimeNodes.class) {
			return nodesInUse.get(computingNode);
		}
	}
	
	/**
	 * Releases node.
	 * @param node node to release
	 */
	public static void realease(ComputingNode node) {
		synchronized (RunTimeNodes.class) {
			int currentLoad = nodesInUse.get(node);
			currentLoad-=1;
			if(currentLoad==0) {
				nodesInUse.remove(node);
			} else {
				nodesInUse.put(node, currentLoad);
			}
		}
	}
	
	/**
	 * ToString counterpart (static).
	 * @return string representation
	 */
	public static String string() {
		synchronized (RunTimeNodes.class) {
			return nodesInUse.toString();
		}
	}

	/**
	 * Getter.
	 * @return the nodesInUse
	 */
	public static Map<ComputingNode, Integer> getNodesInUse() {
		synchronized (RunTimeNodes.class) {
			return nodesInUse;
		}
	}
	
}
