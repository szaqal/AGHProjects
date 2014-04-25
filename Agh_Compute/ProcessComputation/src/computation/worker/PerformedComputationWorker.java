package computation.worker;

import java.util.List;

import computation.model.PerformedComputation;

import core.utils.PagingInfo;

/**
 * Performed computation worker.
 * 
 * @author malczyk.pawel@gmail.com
 *
 */
public interface PerformedComputationWorker {
	
	/**
	 * Saves {@link PerformedComputation}.
	 * @param performedComputation performed computation
	 * @return computation identifier.
	 */
	PerformedComputation savePerformedComputation(PerformedComputation performedComputation);
	
	/**
	 * Retrieves {@link PerformedComputation} by identifier.
	 * @param uniqueId computation identifier.
	 * @return {@link PerformedComputation}
	 */
	PerformedComputation retrievePerformedComputation(int uniqueId);
	
	/**
	 * Retrieves list of {@link PerformedComputation}.
	 * @param pagingInfo {@link PagingInfo}
	 * @return list of performed computations
	 */
	List<PerformedComputation> retrievePerformedComputations(PagingInfo pagingInfo);
	
	/**
	 * Retrieves performed computations count.
	 * @return result
	 */
	Long retrievePerformedComputationCount();
	
	/**
	 * Local interface.
	 * @author malczyk.pawel@gmail.com
	 *
	 */
	public interface PerformedComputationWorkerLocal extends PerformedComputationWorker { }
	
	/**
	 * Remote interface.
	 * @author malczyk.pawel@gmail.com
	 *
	 */
	public interface PerformedComputationWorkerRemote extends PerformedComputationWorker { }
	
}
