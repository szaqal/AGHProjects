package core.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

/**
 * Represents configuration file.
 * @author malczyk.pawel@gmail.com
 *
 */
@Entity
@NamedQueries({
	@NamedQuery(name="byFileConfigKey", query="SELECT acf FROM ApplicationConfigurationFile AS acf WHERE acf.key=:acfkey")
})
public class ApplicationConfigurationFile extends AbstractEntity {

	/**
	 * Named query name.
	 */
	public static final String BY_KEY = "byFileConfigKey";
	
	/**
	 * Pk.
	 */
	private int uniqueId;
	
	/**
	 * Application config file key.
	 */
	private ConfigFiles key;
	
	/**
	 * Actual file.
	 */
	private FileContent fileContent;
	
	/**
	 * Constructor.
	 */
	public ApplicationConfigurationFile() {
		super();
	}

	/**
	 * Getter.
	 * @return the uniqueId
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
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
	@Enumerated(EnumType.STRING)
	public ConfigFiles getKey() {
		return key;
	}

	/**
	 * Setter.
	 * @param key the key to set
	 */
	public void setKey(ConfigFiles key) {
		this.key = key;
	}

	/**
	 * Getter.
	 * @return the fileContent
	 */
	@OneToOne(cascade = CascadeType.ALL)
	public FileContent getFileContent() {
		return fileContent;
	}

	/**
	 * Setter.
	 * @param fileContent the fileContent to set
	 */
	public void setFileContent(FileContent fileContent) {
		this.fileContent = fileContent;
	}

	/** {@inheritDoc} */
	@Transient
	@Override
	public String[] getJsonData() {
		// TODO Auto-generated method stub
		return null;
	}
}
