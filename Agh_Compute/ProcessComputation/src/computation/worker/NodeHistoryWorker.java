package computation.worker;

import java.util.Collection;

import computation.model.ComputationNodeHistory;

/**
 * 
 * Worker history that gather histrical data about system load.
 * 
 * @author malczyk.pawel@gmail.com
 *
 */
public interface NodeHistoryWorker {
	
	/**
	 * Stores computation node history.
	 * @param computationNodeHistory item to be stored
	 */
	void storeHistoryItem(ComputationNodeHistory computationNodeHistory);
	
	/**
	 * Retrieves collection of computation node history entries.
	 * @param nodeIp node ip for which history is retrieved.
	 * @return collection of items
	 */
	Collection<ComputationNodeHistory> retrieveHistory(String nodeIp);
	
	/**
	 * Local interface.
	 * @author malczyk.pawel@gmail.com
	 *
	 */
	public interface NodeHistoryWorkerLocal extends NodeHistoryWorker { };
	
	/**
	 * Remote interface.
	 * @author malczyk.pawel@gmail.com
	 *
	 */
	public interface NodeHistoryWorkerRemote extends NodeHistoryWorker { };

}
