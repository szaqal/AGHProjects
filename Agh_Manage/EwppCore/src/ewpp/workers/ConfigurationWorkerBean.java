package ewpp.workers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ewpp.entity.ConfigurationItem;
import ewpp.workers.ConfigurationWorker.ConfigurationWorkerLocal;
import ewpp.workers.ConfigurationWorker.ConfigurationWorkerRemote;

/**
 * Implementacja worker konfiguracji.
 * @author malczyk.pawel@gmail.com
 *
 */
@Stateless
@Remote(ConfigurationWorkerRemote.class)
@Local(ConfigurationWorkerLocal.class)
public class ConfigurationWorkerBean extends AbstractWorkerBean implements ConfigurationWorker {
	
	/**
	 * Logger. 
	 */
	private static final Logger LOG = LoggerFactory.getLogger(ConfigurationWorkerBean.class);
	
	/**
	 * Konstruktor.
	 */
	public ConfigurationWorkerBean() { }

	/** {@inheritDoc} */
	@Override
	public ConfigurationItem retrieveConfigurationItem(int itemId) {
		return retrieveEntityId(itemId, ConfigurationItem.class);
	}

	/** {@inheritDoc} */
	@Override
	public void storeConfigurationItem(String key, String value) {
		ConfigurationItem configurationItem = new ConfigurationItem(key, value);
		saveEntity(configurationItem);		
	}

	/** {@inheritDoc} */
	@Override
	public Map<String, String> retrieveConfigurations() {
		String qry = "SELECT confItem FROM ConfigurationItem confItem";
		Query query = getEntityManager().createQuery(qry);
		List<ConfigurationItem> list =  getResultList(query, ConfigurationItem.class);
		Map<String, String> result = new HashMap<String, String>();
		for(ConfigurationItem confItem : list) {
			result.put(confItem.getKey(), confItem.getValue());
		}
		return result;
	}
	
	/**
	 * Wybiaga element konfiguracji po kluczu.
	 * @param key klucz
	 * @return element konfiguracji
	 */
	public ConfigurationItem retrieveConfigItemByKey(String key) {
		String qry = "SELECT confItem FROM ConfigurationItem confItem WHERE confItem.key = :key";
		Query query = getEntityManager().createQuery(qry);
		query.setParameter("key", key);
		
		List<ConfigurationItem> confItem  = getResultList(query, ConfigurationItem.class);
		return (confItem.size() == 0)?new ConfigurationItem():confItem.iterator().next();
		
	}

	/** {@inheritDoc} */
	@Override
	public void saveConfiguration(Map<String, String> configItems) {
		
		for(String key : configItems.keySet()) {
			LOG.debug("Saving configurationItem {}", key);
			ConfigurationItem confItem = retrieveConfigItemByKey(key);
			confItem.setKey(key);
			confItem.setValue(configItems.get(key));
			saveEntity(confItem);
		}
		
	}

}
