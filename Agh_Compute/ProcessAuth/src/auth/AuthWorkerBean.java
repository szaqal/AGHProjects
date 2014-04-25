package auth;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import auth.AuthWorker.AuthWorkerLocal;
import auth.AuthWorker.AuthWorkerRemote;
import auth.model.User;
import core.workers.AbstractWorkerBean;

/**
 * Implementation of {@linkplain AuthWorker}.
 * @author <a href="mailto:malczyk.pawel@gmail.com">malczyk.pawel@gmail.com</a>
 *
 */
@Stateless
@Local(AuthWorkerLocal.class)
@Remote(AuthWorkerRemote.class)
public class AuthWorkerBean extends AbstractWorkerBean implements AuthWorker {
	
	/**
	 * Default Constructor.
	 */
	public AuthWorkerBean() { 
	}

	/** {@inheritDoc} */
	@Override
	public User retrieveUser(String id) {
		// TODO Auto-generated method st
		return null;
	}
}
