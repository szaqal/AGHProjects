package computation.worker;

import java.util.Collection;

import computation.model.ComputingNode;
import computation.model.RegistrationData;

/**
 * Worker operating on computational nodes.
 * @author <a href="mailto:malczyk.pawel@gmail.com">malczyk.pawel@gmail.com</a>
 *
 */
public interface NodesWorker {
	
	/**
	 * Register node as eligable to perform computation.
	 * @param registrationData data needed to registration
	 */
	void registerNode(RegistrationData registrationData);
	
	/**
	 * Registers node (usually by update).
	 * @param computingNode computing node being registered
	 */
	void registerComputingNode(ComputingNode computingNode);
	
	/**
	 * Removes computing node from available pool.
	 * @param computingNode {@link ComputingNode}
	 */
	void removeComputingNode(ComputingNode computingNode);
	
	/**
	 * Registers multiple nodes.
	 * @param registrationDatas collection of registration data.
	 */
	void registerNodes(Collection<RegistrationData> registrationDatas);
	
	/**
	 * Returns all registered computing nodes.
	 * @return list of registered computing nodes
	 */
	Collection<ComputingNode> retrieveComputingNodes();
	
	/**
	 * Retrieves currently registered node by it's key.
	 * @param key key used to retrieve node
	 * @return registered computing node
	 */
	ComputingNode retrieveRegisteredComputingNode(String key);
	
	
	
	/**
	 * Local Interface.
	 * @author <a href="mailto:malczyk.pawel@gmail.com">malczyk.pawel@gmail.com</a>
	 *
	 */
	public interface NodesWorkerLocal extends NodesWorker { }
	
	/**
	 * Remote interface.
	 * @author <a href="mailto:malczyk.pawel@gmail.com">malczyk.pawel@gmail.com</a>
	 *
	 */
	public interface NodesWorkerRemote extends NodesWorker { }
}
