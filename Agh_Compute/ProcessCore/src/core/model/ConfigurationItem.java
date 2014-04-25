package core.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Transient;

/**
 * Represents SimpleConfiguratioItem.
 * @author  <a href="mailto:malczyk.pawel@gmail.com">malczyk.pawel@gmail.com</a>
 */
@Entity
@NamedQueries({
		@NamedQuery(name = "byKey", query = "SELECT cfi FROM ConfigurationItem AS cfi WHERE cfi.configKey =:key")
})
public class ConfigurationItem extends AbstractEntity {
	
	/** Named query name. */
	public static final String BY_KEY = "byKey";
	
	/**
	 * Id.
	 * @uml.property  name="uniqueId"
	 */
	private int uniqueId;
	
	/**
	 * Key of the ConfigurationItem.
	 * @uml.property  name="configKey"
	 */
	private String configKey;
	
	/**
	 * Value of the ConfigurationItem.
	 * @uml.property  name="configValue"
	 */
	private String configValue;
	
	/**
	 * Default constructor.
	 */
	public ConfigurationItem() { }

	/**
	 * Getter.
	 * @return  the key
	 * @uml.property  name="configKey"
	 */
	public String getConfigKey() {
		return configKey;
	}

	/**
	 * Setter.
	 * @param key  the key to set
	 * @uml.property  name="configKey"
	 */
	public void setConfigKey(String key) {
		this.configKey = key;
	}

	/**
	 * Getter.
	 * @return  the value
	 * @uml.property  name="configValue"
	 */
	public String getConfigValue() {
		return configValue;
	}

	/**
	 * Setter.
	 * @param value  the value to set
	 * @uml.property  name="configValue"
	 */
	public void setConfigValue(String value) {
		this.configValue = value;
	}

	/**
	 * Getter.
	 * @return  the uniqueId
	 * @uml.property  name="uniqueId"
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Override
	public int getUniqueId() {
		return uniqueId;
	}

	/**
	 * Setter.
	 * @param uniqueId  the uniqueId to set
	 * @uml.property  name="uniqueId"
	 */
	public void setUniqueId(int uniqueId) {
		this.uniqueId = uniqueId;
	}

	/** {@inheritDoc} */
	@Override
	@Transient
	public String[] getJsonData() {
		// TODO Auto-generated method stub
		return null;
	}

}
