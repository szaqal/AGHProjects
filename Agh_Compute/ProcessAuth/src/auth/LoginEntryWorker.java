package auth;

import java.util.Collection;

import auth.model.LoginEntry;

/**
 * Contains methods used to manipulate information about user logins. 
 * 
 * @author <a href="mailto:malczyk.pawel@gmail.com">malczyk.pawel@gmail.com</a>
 *
 */
public interface LoginEntryWorker {
	
	/**
	 * Stores new login entry.
	 * @param loginEntry login entry to save.
	 */
	void storeLoginEntry(LoginEntry loginEntry);
	
	/**
	 * Retrieves latest login entry.
	 * @param userId user identifier.
	 * @return latest login entry.
	 */
	LoginEntry retrieveLatestLoginEntry(int userId);
	
	/**
	 * Retrieves collection of user login entries.
	 * @param userId user identifier
	 * @return collection of login entries connected particular user.
	 */
	Collection<LoginEntry> retrieveLoginEntries(int userId);
	
	/**
	 * Local interface.
	 * @author <a href="mailto:malczyk.pawel@gmail.com">malczyk.pawel@gmail.com</a>
	 *
	 */
	public interface LoginEntryWorkerLocal extends LoginEntryWorker { };
	
	/**
	 * Remote interface.
	 * @author <a href="mailto:malczyk.pawel@gmail.com">malczyk.pawel@gmail.com</a>
	 *
	 */
	public interface LoginEntryWorkerRemote extends LoginEntryWorker { };
	
}
