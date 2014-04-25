package node;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Holds configuration items.
 * 
 * http://tomcat.apache.org/tomcat-6.0-doc/monitoring.html
 * 
 * @author malczyk.pawel@gmail.com
 *
 */
public final class Configuration {
	
	/**
	 * Logger.
	 */
	private static final Logger LOG = LoggerFactory.getLogger(Configuration.class);

	
	/**
	 * Map for configuration items.
	 */
	private static final Map<String, String> CONFIG_ITEMS;
	
	/**
	 * Constructor.
	 */
	private Configuration() { }
	
	/**
	 * Returns configuration value.
	 * @return config value.
	 */
	public static String getJmxPort() {
		return CONFIG_ITEMS.get("node.jmx.port");
	}
	
	/**
	 * Returns configuration value.
	 * @return config value.
	 */
	public static String getOperationPort() {
		return CONFIG_ITEMS.get("node.operation.port");
	}

	/**
	 * Returns configuration value.
	 * 
	 * @return config value.
	 */
	public static String getNodeIp() {
		return CONFIG_ITEMS.get("node.address");
	}

	/**
	 * Returns configuration value.
	 * 
	 * @return config value.
	 */
	public static String getServerIp() {
		return CONFIG_ITEMS.get("process.server.inetaddr");
	}

	/**
	 * Returns configuration value.
	 * 
	 * @return config value.
	 */
	public static String getServerPort() {
		return CONFIG_ITEMS.get("process.server.port");
	}
	
	
	static {
		LOG.info("Loading configuration");
		CONFIG_ITEMS = new HashMap<String, String>();
		InputStream configInput = Configuration.class.getResourceAsStream("/config.properties");

		Properties props = new Properties();
		try {
			props.load(configInput);
			for (Object key : props.keySet()) {
				CONFIG_ITEMS.put((String) key, (String) props.getProperty((String) key));
			}
		} catch (IOException e) {
			LOG.warn("Exception", e);
		}
		LOG.info("Configuration loaded");
	}
	
}
