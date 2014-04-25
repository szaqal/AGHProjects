package auth;

import java.util.Collection;

import auth.model.Group;
import auth.model.User;
import auth.model.UserGroup;

/**
 * User group worker.
 * @author malczyk.pawel@gmail.com
 *
 */
public interface UserGroupWorker {
	
	/**
	 * Adds user to group.
	 * @param groupId group identifier
	 * @param user being added to group
	 */
	void addUserToGroup(int groupId, User user);
	
	/**
	 * Removes user from group.
	 * @param groupId group identifier
	 * @param user being removed from group
	 */
	void removeUserFromGroup(int groupId, User user);
	
	/**
	 * Retrieves users by group.
	 * @param groupId group identifier
	 * @return collection of users.
	 */
	Collection<User> retriveByGroup(int groupId);
	
	/**
	 * Retrieves collection of group to which user belongs.
	 * @param userId user identifier
	 * @return collection of user's groups
	 */
	Collection<Group> retrieveByUser(int userId);
	
	/**
	 * Retrieves group that still user can be assigned to.
	 * @param userId user identifier
	 * @return collection of groups.
	 */
	Collection<Group> retrieveAvailable(int userId);
	
	
	/**
	 * Retrieves users group by group and user.
	 * @param groupId group identifier
	 * @param userId user identifier
	 * @return collection of {@linkplain UserGroup} entries
	 */
	Collection<UserGroup> retrieveByUserAndGroup(int groupId, int userId);
	
	/**
	 * Local interface.
	 * @author malczyk.pawel@gmail.com
	 *
	 */
	public interface UserGroupWorkerLocal extends UserGroupWorker { }
	
	/**
	 * Remote interface.
	 * @author malczyk.pawel@gmail.com
	 *
	 */
	public interface UserGroupWorkerRemote extends UserGroupWorker  { }
	
}
