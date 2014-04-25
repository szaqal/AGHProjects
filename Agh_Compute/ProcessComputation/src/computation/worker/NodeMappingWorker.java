package computation.worker;

import java.util.List;

import computation.model.NodeMapping;

import core.utils.PagingInfo;

/**
 * Node mapping worker.
 * 
 * @author malczyk.pawel@gmail.com
 *
 */
public interface NodeMappingWorker {
	
	/**
	 * Stores new {@link NodeMapping}.
	 * @param mapping mapping being stored..
	 * @return stored mapping id
	 */
	NodeMapping store(NodeMapping mapping);
	
	/**
	 * Removed node mapping.
	 * @param mappingId mapping id used
	 */
	void delete(int mappingId);
	
	/**
	 * Retrieves mapping by it's id.
	 * @param id mapping id.
	 * @return node mapping.
	 */
	NodeMapping retrieveById(int id);
	
	/**
	 * Retrieves mapping by performer name.
	 * @param performer name.
	 * @param ownerId every user may have it's own mappings.
	 * @return {@link NodeMapping}
	 */
	NodeMapping retrieveByPerformer(String performer, int ownerId);
	
	/**
	 * Retrieves all mappings specified by particular user.
	 * @param ownerId owner id.
	 * @param paginginfo pagining infor used.
 	 * @return collection of created node mappings for user.
	 */
	List<NodeMapping> retrieveByOwner(int ownerId, PagingInfo paginginfo);
	
	/**
	 * Retrieves owned mappings count for particular users.
	 * @param ownerId mapping owner identifier.,
	 * @return results count.
	 */
	Long retrieveByOwnerCount(int ownerId);
	
	/**
	 * Local interface.
	 * @author malczyk.pawel@gmail.com
	 *
	 */
	public interface NodeMappingWorkerLocal extends NodeMappingWorker { }
	
	/**
	 * Remote interface.
	 * @author malczyk.pawel@gmail.com
	 *
	 */
	public interface NodeMappingWorkerRemote extends NodeMappingWorker { }
	
}
