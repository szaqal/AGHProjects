package auth;

import auth.model.Registration;
import auth.model.User;

/**
 * 
 * Performs user registration. 
 * @author <a href="mailto:malczyk.pawel@gmail.com">malczyk.pawel@gmail.com</a>
 *
 */
public interface RegUserWorker {
	
	/**
	 * Stores newly created user.
	 * @param user prepared user.
	 * @return identifier
	 */
	int storeUser(User user);
	
	/**
	 * User id.
	 * @param userId usert identifier for which registration will be removed.
	 */
	void deleteRegistrationByUserId(int userId);
	
	/**
	 * Completes registration.
	 * @param registrationId registration identifier.
	 * @return success or not
	 */
	boolean completeRegistration(int registrationId);
	
	/**
	 * Returns registration for user.
	 * @param userId registered user id
	 * @return {@link Registration}
	 */
	Registration getByUser(int userId);
	
	/**
	 * Local interface.
	 * @author <a href="mailto:malczyk.pawel@gmail.com">malczyk.pawel@gmail.com</a>
	 *
	 */
	public interface RegUserWorkerLocal extends RegUserWorker { }
	
	/**
	 * Remote interface.
	 * @author <a href="mailto:malczyk.pawel@gmail.com">malczyk.pawel@gmail.com</a>
	 *
	 */
	public interface RegUserWorkerRemote extends RegUserWorker { }

}
