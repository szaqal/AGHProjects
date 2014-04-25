package auth;

import java.util.Collection;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.Query;

import auth.LoginEntryWorker.LoginEntryWorkerLocal;
import auth.LoginEntryWorker.LoginEntryWorkerRemote;
import auth.model.LoginEntry;
import core.workers.AbstractWorkerBean;

/**
 * Worker implementation. 
 * @author <a href="mailto:malczyk.pawel@gmail.com">malczyk.pawel@gmail.com</a>
 *
 */
@Stateless
@Remote(LoginEntryWorkerRemote.class)
@Local(LoginEntryWorkerLocal.class)
public class LoginEntryWorkerBean extends AbstractWorkerBean implements LoginEntryWorker {
	
	/** Constructor. */
	public LoginEntryWorkerBean() { }

	/** {@inheritDoc} */
	@Override
	public LoginEntry retrieveLatestLoginEntry(int userId) {
		Query query = getEntityManager().createNamedQuery(LoginEntry.BY_USER_AND_DATE);
		query.setParameter("userId", userId);
		query.setMaxResults(2);
		List<LoginEntry> results = getResultList(query, LoginEntry.class);
		LoginEntry result = null;
		if(results.isEmpty()) {
			result = emptyLoginEntry();
		} else {			
			result = (results.size()==2) ? results.get(1) : results.get(0);
		}
		return result;
	}
	
	/**
	 * Creates empty login Entry.
	 * @return login entry.
	 */
	private LoginEntry emptyLoginEntry() {
		LoginEntry loginEntry = new LoginEntry();
		loginEntry.setLoginFrom("N/A");
		return loginEntry;
	}

	/** {@inheritDoc} */
	@Override
	public Collection<LoginEntry> retrieveLoginEntries(int userId) {
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public void storeLoginEntry(LoginEntry loginEntry) {
		save(loginEntry);
	}

}
