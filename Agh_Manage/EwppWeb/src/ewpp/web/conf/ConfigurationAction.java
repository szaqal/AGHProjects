package ewpp.web.conf;

import ewpp.web.AbstractEwppAction;
import ewpp.workers.ConfigurationWorker;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Akcja konfiguracji systemu.
 * @author malczyk.pawel@gmail.com
 *
 */
public class ConfigurationAction extends AbstractEwppAction {

	/**
	 * Serial.
	 */
	private static final long serialVersionUID = 2596500812066753340L;
	
	/**
	 * Logger. 
	 */
	private static final Logger LOG = LoggerFactory.getLogger(ConfigurationAction.class);
	
	
	/**
	 * Konfiguracje.
	 */
	private Map<String, String> configs;
	
	/**
	 * Klucze elementow konfiguracji.
	 */
	private String [] configurationKeys;

	/**
	 * Konstruktor.
	 */
	public ConfigurationAction() { }
	
	/** {@inheritDoc} */
	@Override
	protected String doEdit() {
		ConfigurationWorker worker = locate(ConfigurationWorker.class);
		configs = worker.retrieveConfigurations();
		configurationKeys = ConfigurationWorker.CONFIGURATIONS;
		return EDIT;
	}
	
	/** {@inheritDoc} */
	@Override
	protected String doView() {
		ConfigurationWorker worker = locate(ConfigurationWorker.class);
		configs = worker.retrieveConfigurations();
		configurationKeys = ConfigurationWorker.CONFIGURATIONS;
		return VIEW;
	}
	
	/** {@inheritDoc} */
	@Override
	protected String doSave() {
		ConfigurationWorker worker = locate(ConfigurationWorker.class);
		LOG.debug("SAVE >>>> {}", configs.toString());
		worker.saveConfiguration(configs);
		return doView();
	}
	
	/** {@inheritDoc} */
	@Override
	public String getDefaultOpertation() {
		return VIEW;
	}

	/**
	 * Getter.
	 * @return the configs
	 */
	public final Map<String, String> getConfigs() {
		return configs;
	}

	/**
	 * Setter.
	 * @param configs the configs to set
	 */
	public final void setConfigs(Map<String, String> configs) {
		this.configs = configs;
		LOG.debug("SETTER >>>> {}", configs.toString());
	}

	/**
	 * Getter.
	 * @return the configurationKeys
	 */
	public final String[] getConfigurationKeys() {
		return configurationKeys;
	}

	/**
	 * Setter.
	 * @param configurationKeys the configurationKeys to set
	 */
	public final void setConfigurationKeys(String[] configurationKeys) {
		this.configurationKeys = configurationKeys;
	}
}
