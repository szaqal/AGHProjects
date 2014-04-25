package ewpp.document.workers;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;

/**
 * Worker dokumentow.
 * @author malczyk.pawel@gmail.com
 *
 */
@Stateless
@Local(DocumentWorker.DokumentWorkerLocal.class)
@Remote(DocumentWorker.DoumentWorkerRemote.class)
public class DocumentWorkerBean implements DocumentWorker {

	/** Konstruktor. */
	public DocumentWorkerBean() { };
}
