package computation.worker;

import java.util.List;

import computation.model.ComputationResult;

import core.utils.PagingInfo;

/**
 * Provides operations on computation results.
 * 
 * @author malczyk.pawel@gmail.com
 *
 */
public interface ComputationResultWorker {
	
	/**
	 * Saves single {@link ComputationResult}.
	 * @param computationResult computation result being saved.
	 * @param userId prormed userId
	 * @return result identifier.
	 */
	ComputationResult saveResult(ComputationResult computationResult, int userId);
	
	/**
	 * Retrieves collection of {@link ComputationResult}s.
	 * @return collection of items.
	 */
	List<ComputationResult> retrieveResults();
	
	/**
	 * Retrieves collection of {@link ComputationResult}s for particular result. 
	 * @param pagingInfo {@link PagingInfo}
	 * @param ownerId owning user id.
	 * @return collection of stored entities
	 */
	List<ComputationResult> retrieveResults(PagingInfo pagingInfo, int ownerId);
	
	/**
	 * Retrieves computation results count. 
	 * @param ownerId owning user id.
	 * @return collection of stored entities
	 */
	Long resultsCount(int ownerId);
	
	/**
	 * Retrieves single {@link ComputationResult}.
	 * @param uniqueId result identifier.
	 * @return {@link ComputationResult}
	 */
	ComputationResult retrieveComputationResult(int uniqueId);
	
	/**
	 * Returns result as XML document bytes.
	 * @param resultId resultId.
	 * @return byte array
	 */
	byte [] retrieveAsXml(int resultId);
	
	/**
	 * Local interface.
	 * @author malczyk.pawel@gmail.com
	 *
	 */
	public interface ComputationResultWorkerLocal extends ComputationResultWorker { }
	
	/**
	 * Remote interface.
	 * @author malczyk.pawel@gmail.com
	 *
	 */
	public interface ComputationResultWorkerRemote extends ComputationResultWorker { }

}
