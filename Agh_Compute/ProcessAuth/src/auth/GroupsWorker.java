package auth;

import java.util.Collection;

import auth.model.Group;
import core.utils.PagingInfo;

/**
 * Worker operating on groups.
 * @author <a href="mailto:malczyk.pawel@gmail.com">malczyk.pawel@gmail.com</a>
 *
 */
public interface GroupsWorker {
	
	
	/**
	 * Saves new group.
	 * @param group prepared group entity
	 * @return group identifier
	 */
	Group saveGroup(Group group);
	
	/**
	 * Removes group.
	 * @param groupId identifier of group to remove
	 */
	void deleteGroup(int groupId);
	
	/**
	 * Returns new group from db.
	 * @param uniqueId identifier of group
	 * @return found group
	 */
	Group retrieveGroup(int uniqueId);
	
	/**
	 * Returns all groups in the system.
	 * @param pagingInfo paging info
	 * @return collection of groups
	 */
	Collection<Group> retrieveGroups(PagingInfo pagingInfo);
		
	/**
	 * Returns groups count.
	 * @return groups count
	 */
	Long retrieveGroupsCount();
	
	/**
	 * Local interface.
	 * @author <a href="mailto:malczyk.pawel@gmail.com">malczyk.pawel@gmail.com</a>
	 *
	 */
	public interface GroupsWorkerLocal extends GroupsWorker { }
	
	/**
	 * Remote interface.
	 * @author <a href="mailto:malczyk.pawel@gmail.com">malczyk.pawel@gmail.com</a>
	 *
	 */
	public interface GroupsWorkerRemote extends GroupsWorker { }
	
}
