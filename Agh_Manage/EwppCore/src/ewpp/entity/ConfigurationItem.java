package ewpp.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Element konfiguracacji.
 * @author malczyk.pawel@gmail.com
 *
 */
@Entity
@Table(name="configuration")
@SequenceGenerator(name="CONF_SEQUENCE", sequenceName="CONF_SEQ")
public class ConfigurationItem implements Serializable {

	/**
	 * Serial.
	 */
	private static final long serialVersionUID = 7997514544480406138L;
	

	/** Identyfikator. */
	private int uniqueId;
	
	/** Klucz. */
	private String key;
	
	/** Wartosc. */
	private String value;

	/**
	 * Konstruktor. 
	 */
	public ConfigurationItem() { }
	
	/**
	 * Konstruktor.
	 * @param key klucz
	 * @param value wartosc
	 */
	public ConfigurationItem(String key, String value) {
		this.key = key;
		this.value = value;
	}
	

	/**
	 * Getter.
	 * @return the uniqueId
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CONF_SEQUENCE")
	public int getUniqueId() {
		return uniqueId;
	}

	/**
	 * Setter.
	 * @param uniqueId the uniqueId to set
	 */
	public void setUniqueId(int uniqueId) {
		this.uniqueId = uniqueId;
	}


	/**
	 * Getter.
	 * @return the key
	 */
	public String getKey() {
		return key;
	}


	/**
	 * Setter.
	 * @param key the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}


	/**
	 * Getter.
	 * @return the value
	 */
	public String getValue() {
		return value;
	}


	/**
	 * Setter.
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
	
}
