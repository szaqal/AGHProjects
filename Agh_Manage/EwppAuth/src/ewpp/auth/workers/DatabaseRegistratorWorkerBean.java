package ewpp.auth.workers;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ewpp.auth.entity.User;
import ewpp.auth.workers.DatabaseRegistratorWorker.DatabaseRegistratorWorkerLocal;
import ewpp.auth.workers.DatabaseRegistratorWorker.DatabaseRegistratorWorkerRemote;
import ewpp.workers.AbstractWorkerBean;

/**
 * Worker rejestrujacy uzytkownikow.
 * @author malczyk.pawel@gmail.com
 *
 */
@Stateless
@Local(DatabaseRegistratorWorkerLocal.class)
@Remote(DatabaseRegistratorWorkerRemote.class)
public class DatabaseRegistratorWorkerBean extends AbstractWorkerBean implements DatabaseRegistratorWorker {


	/** Logger. */
	private static final Logger LOG = LoggerFactory.getLogger(DatabaseRegistratorWorkerBean.class);

	/** Konstruktor. */
	public DatabaseRegistratorWorkerBean() {};

	/** {@inheritDoc} */
	@Override
	public boolean storeNewUser(User user) {
		LOG.info( "Rejestrowanie uzytkownika" );
		saveEntity(user);
		return false;
	}

}
