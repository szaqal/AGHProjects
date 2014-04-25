package computation.worker;

import java.util.List;

import computation.model.ComputationLogEntry;

import core.utils.PagingInfo;

/**
 * Worker that performs operations on {@link ComputationLogEntry}.
 * @author malczyk.pawel@gmail.com
 *
 */
public interface ComputationLogEntryWorker {
	
	
	/**
	 * Stores computation entry.
	 * @param computationLogEntry object being persisted.
	 * @return id of saved object
	 */
	ComputationLogEntry store(ComputationLogEntry computationLogEntry);
	
	/**
	 * Retrieves collection of Computation entries based on perfored computation id.
	 * @param performedComputationId id of performed computation
	 * @param pagingInfo paging information
	 * @return collection of computation logs.
	 */
	List<ComputationLogEntry> retrieveLogs(int performedComputationId, PagingInfo pagingInfo);
	
	/**
	 * Returns {@link ComputationLogEntry} count.
	 * @param performedComputationId performed computation primary key
	 * @return item.
	 */
	Long retrieveLogsCount(int performedComputationId);
	
	/**
	 * Local interface.
	 * @author malczyk.pawel@gmail.com
	 *
	 */
	public interface ComputationLogEntryWorkerLocal extends ComputationLogEntryWorker { }
	
	/**
	 * Remote interface.
	 * @author malczyk.pawel@gmail.com
	 *
	 */
	public interface ComputationLogEntryWorkerRemote extends ComputationLogEntryWorker { }
	
}
