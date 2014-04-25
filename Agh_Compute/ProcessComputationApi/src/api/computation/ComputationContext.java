package api.computation;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Holdes runnable information context.
 * @author  malczyk.pawel@gmail.com
 */
public final class ComputationContext implements Serializable {
	
	/**
	 * Serial.
	 */
	private static final long serialVersionUID = 5661766200074283154L;

	/**
	 * Holds settings.
	 * @uml.property  name="settings"
	 */
	private Map<String, String> settings = new HashMap<String, String>();
	
	/**
	 * Performable.
	 * @uml.property  name="performable"
	 * @uml.associationEnd  
	 */
	private transient Performable performable;
	
	/**
	 * Constructor.
	 */
	public ComputationContext() {
	}
	
	/**
	 * Adds setting.
	 * @param key key
	 * @param value value
	 */
	public void addSetting(String key,  String value) {
		settings.put(key, value);
	}
	
	/**
	 * Increments value MUST BE Integer.
	 * @param key setting key.
	 */
	public void incrementSetting(String key) {
		String val = settings.get(key);
		if(val == null) {
			settings.put(key, String.valueOf(0));
		} else {
			Integer valInt = Integer.valueOf(val);
			valInt++;
			settings.put(key, valInt.toString());
		}
	}
	
	/**
	 * Returns setting by key.
	 * @param key key
	 * @return setting value
	 */
	public String getSetting(String key) {
		return settings.get(key);
	}

	/**
	 * Getter.
	 * @return  the performable
	 * @uml.property  name="performable"
	 */
	public Performable getPerformable() {
		return performable;
	}

	/**
	 * Setter.
	 * @param performable  the performable to set
	 * @uml.property  name="performable"
	 */
	public void setPerformable(Performable performable) {
		this.performable = performable;
	}

	/**
	 * Getter.
	 * @return  the settings
	 * @uml.property  name="settings"
	 */
	public Map<String, String> getSettings() {
		return settings;
	}

	/**
	 * Setter.
	 * @param settings  the settings to set
	 * @uml.property  name="settings"
	 */
	public void setSettings(Map<String, String> settings) {
		this.settings = settings;
	}
	
	/** {@inheritDoc} */
	@Override
	public String toString() {
		return settings.toString();
	}

}
