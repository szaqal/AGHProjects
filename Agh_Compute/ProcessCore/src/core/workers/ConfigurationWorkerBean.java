package core.workers;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.model.ConfigurationItem;
import core.workers.ConfigurationWorker.ConfigurationWorkerLocal;
import core.workers.ConfigurationWorker.ConfigurationWorkerRemote;

/**
 * Implementation of {@link ConfigurationWorker}.
 * @author <a href="mailto:malczyk.pawel@gmail.com">malczyk.pawel@gmail.com</a>
 *
 */
@Stateless
@Local(ConfigurationWorkerLocal.class)
@Remote(ConfigurationWorkerRemote.class)
public class ConfigurationWorkerBean extends AbstractWorkerBean implements ConfigurationWorker {
	
	/**
	 * Logger.
	 */
	private static final Logger LOG = LoggerFactory.getLogger(ConfigurationWorkerBean.class);
	
	/**
	 * Constructor.
	 */
	public ConfigurationWorkerBean() { 
		super();
	}

	/** {@inheritDoc} */
	@Override
	public ConfigurationItem store(ConfigurationItem configurationItem) {
		return save(configurationItem);
	}

	/** {@inheritDoc} */
	@Override
	public ConfigurationItem getConfigByKey(String key) {
		Query query = getEntityManager().createNamedQuery(ConfigurationItem.BY_KEY);
		query.setParameter("key", key);
		ConfigurationItem result = null;
		try {
			result = getSingleResult(query, ConfigurationItem.class);
		} catch (NoResultException e) {
			LOG.warn("Configuration for key {} not found", key);
		}
		
		return result;
	}
}
