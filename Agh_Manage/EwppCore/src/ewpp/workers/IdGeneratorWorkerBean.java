package ewpp.workers;

import javax.ejb.Local;
import javax.ejb.Stateless;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.eaio.uuid.UUID;
import ewpp.workers.IdGeneratorWorker.IdGeneratorWorkerLocal;

/**
 * Implementacja woker.
 * @author malczyk.pawel@gmail.com
 *
 */
@Stateless
@Local(IdGeneratorWorkerLocal.class)
public class IdGeneratorWorkerBean implements IdGeneratorWorker{

	/** Logger. */
	private static final Logger LOG = LoggerFactory.getLogger(IdGeneratorWorkerBean.class);

	/** Konstruktor. */
	public IdGeneratorWorkerBean() { }

	/** {@inheritDoc} */
	@Override
	public String generate() {
		LOG.debug("Wygenerowano Identyfikator");
		UUID uuid = new UUID();
		String result = uuid.toString();
		LOG.debug("Wygenerowano Identyfikator {}", result);
		return result;
	}

}

