package ewpp.messaging.workers;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ewpp.messaging.workers.NotifierWorker.NotifierWorkerLocal;
import ewpp.messaging.workers.NotifierWorker.NotifierWorkerRemote;

/**
 * Implemetnacja workera "przypomniacza".
 * @author malczyk.pawel@gmail.com
 *
 */
@Stateless
@Local(NotifierWorkerLocal.class)
@Remote(NotifierWorkerRemote.class)
public class NotifierWorkerBean implements NotifierWorker {

	/**
	 * Logger.
	 */
	private static final Logger LOG = LoggerFactory.getLogger(NotifierWorkerBean.class);

	/** Konstruktor. */
	public NotifierWorkerBean() { };

	/** {@inheritDoc} */
	@Override
	public void sendNotification() {
		LOG.info("Sending notification");
	}

}
