package ewpp.workers;

import java.util.Map;

import ewpp.entity.ConfigurationItem;

/**
 * Worker konfiguracji.
 * @author malczyk.pawel@gmail.com
 *
 */
public interface ConfigurationWorker {

	/** Klucz elementu konfiguracji. */
	String SMTP_HOST = "mail.smtp.host"; 
	
	/** Klucz elementu konfiguracji. */
	String SMTP_USER = "mail.smtp.user";
	
	/** Klucz elementu konfiguracji. */
	String SMTP_PASSWD = "mail.smtp.passwd"; 
	
	/** Adres email systemu jako nadawcy. */
	String EWPP_SENDER_ADDRESS = "ewpp.sender.address";
	
	/** Konfiguracje. */
	String [] CONFIGURATIONS = { SMTP_HOST, SMTP_USER, SMTP_PASSWD, EWPP_SENDER_ADDRESS };
	
	/**
	 * Zapisuje element konfiguracji.
	 * @param key klucz
	 * @param value wartosc
	 */
	void storeConfigurationItem(String key, String value);
	
	/**
	 * Pobiera element konfiguracji.
	 * @param itemId identyfikator elementu konfiguracji
	 * @return element konfiguracji
	 */
	ConfigurationItem retrieveConfigurationItem(int itemId);
	
	/**
	 * Zwraca wszystkie konfiguracje.
	 * @return wszystkie konfiguracje
	 */
	Map<String, String> retrieveConfigurations();
	
	/**
	 * Zapisuje ustawienia konfiguracji. 
	 * @param configItems elementy konfiguracji
	 */
	void saveConfiguration(Map<String, String> configItems);
	
	/**
	 * Wybiaga element konfiguracji po kluczu.
	 * @param key klucz
	 * @return element konfiguracji.
	 */
	ConfigurationItem retrieveConfigItemByKey(String key);
	
	/**
	 * Interfejs zdalny. 
	 * @author malczyk.pawel@gmail.com
	 *
	 */
	public interface ConfigurationWorkerRemote extends ConfigurationWorker { }
	
	/**
	 * Interfejs lokalny.
	 * @author malczyk.pawel@gmail.com 
	 *
	 */
	public interface ConfigurationWorkerLocal extends ConfigurationWorker { }
	
}
