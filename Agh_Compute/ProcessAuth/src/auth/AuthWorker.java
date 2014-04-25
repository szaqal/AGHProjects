package auth;

import auth.model.User;

/**
 * Authentication and Authorization worker. 
 * @author <a href="mailto:malczyk.pawel@gmail.com">malczyk.pawel@gmail.com</a>
 *
 */
public interface AuthWorker {
	
	/** 
	 * Retrieves user by identifier.
	 * @param id identifier
	 * @return user
	 */
	User retrieveUser(String id);
	
	/**
	 * Local interface.
	 * @author <a href="mailto:malczyk.pawel@gmail.com">malczyk.pawel@gmail.com</a>
	 *
	 */
	public interface AuthWorkerLocal extends AuthWorker { };
	
	/**
	 * Remote interface.
	 * @author <a href="mailto:malczyk.pawel@gmail.com">malczyk.pawel@gmail.com</a>
	 *
	 */
	public interface AuthWorkerRemote extends AuthWorker { };
}
