package auth;

import java.util.Collection;

import auth.model.User;
import core.utils.PagingInfo;

/**
 * Users worker.
 * @author <a href="mailto:malczyk.pawel@gmail.com">malczyk.pawel@gmail.com</a>
 *
 */
public interface UsersWorker {
	
	/**
	 * Returns users count.
	 * @return users count.
	 */
	Long retrieveUsersCount();
	
	/**
	 * Returns users.
	 * @param pagingInfo information about paging 
	 * @return users
	 */
	Collection<User> retrieveUsers(PagingInfo pagingInfo);
	
	/**
	 * Authenticates user. 
	 * @param login supplied login
	 * @param passwd supplied password
	 * @return user if authenticated or null.
	 */
	User authenticate(String login, String passwd);
	
	/**
	 * Retrieves user by id.
	 * @param id user identifier
	 * @return found user
	 */
	User find(int id);
	
	/**
	 * Removes user.
	 * @param userId identifier of user being removed.
	 */
	void deleteUser(int userId);
	
	/**
	 * Saves user.
	 * @param user user being saved.
	 * @return return key of saved user.
	 */
	User saveUser(User user);
	
	/**
	 * Local interface.
	 * @author <a href="mailto:malczyk.pawel@gmail.com">malczyk.pawel@gmail.com</a>
	 *
	 */
	public interface UsersWorkerLocal extends UsersWorker { }
	
	/**
	 * Remote interface.
	 * @author <a href="mailto:malczyk.pawel@gmail.com">malczyk.pawel@gmail.com</a>
	 *
	 */
	public interface UsersWorkerRemote extends UsersWorker { }

}
