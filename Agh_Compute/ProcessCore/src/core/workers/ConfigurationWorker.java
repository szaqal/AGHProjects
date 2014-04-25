package core.workers;

import core.model.ConfigurationItem;

/**
 * ConfigurationWorker. 
 * @author <a href="mailto:malczyk.pawel@gmail.com">malczyk.pawel@gmail.com</a>
 *
 */
public interface ConfigurationWorker {
	
	/**
	 * Stores configuration item.
	 * @param configurationItem 
	 * @return saved configuration item
	 */
	ConfigurationItem store(ConfigurationItem configurationItem);
	
	/**
	 * Retrieves {@link ConfigurationItem} by key.
	 * @param key configuration key
	 * @return configuration item
	 */
	ConfigurationItem getConfigByKey(String key);
	
	/**
	 * Remote Interface.
	 * @author <a href="mailto:malczyk.pawel@gmail.com">malczyk.pawel@gmail.com</a>
	 *
	 */
	public interface ConfigurationWorkerRemote extends ConfigurationWorker { }
	
	
	/**
	 * Local Interface.
	 * @author <a href="mailto:malczyk.pawel@gmail.com">malczyk.pawel@gmail.com</a>
	 *
	 */
	public interface ConfigurationWorkerLocal extends ConfigurationWorker { }
}
