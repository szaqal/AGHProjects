package computation.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import core.model.AbstractEntity;
import core.model.FileContent;

/**
 * Uploaded computation configuration.
 * @author  malczyk.pawel@gmail.com
 */
@Entity
@NamedQueries({
	@NamedQuery(name="computationConfigurations",
			query="SELECT cc FROM ComputationConfiguration AS cc"),
	@NamedQuery(name="computationConfigurationCount", 
			query="SELECT COUNT(cc) FROM ComputationConfiguration AS cc")
})
public class ComputationConfiguration extends AbstractEntity {
	
	/**
	 * Constant.
	 */
	public static final String CONFIGURATIONS="computationConfigurations";
	
	/**
	 * Constant.
	 */
	public static final String CONFIGURATIONS_COUNT="computationConfigurationCount";
	
	/**
	 * Identifier.
	 * @uml.property  name="uniqueId"
	 */
	private int uniqueId;
	
	/**
	 * Configuration identifier.
	 * @uml.property  name="configurationId"
	 */
	private String configurationId;
	
	/**
	 * Configuration description.
	 * @uml.property  name="description"
	 */
	private String description;
	
	
	/**
	 * Actual configuration file.
	 * @uml.property  name="configurationFile"
	 * @uml.associationEnd  
	 */
	private FileContent configurationFile;
	
	/**
	 * Confguration validated.
	 * @uml.property  name="validated"
	 */
	private boolean validated;
	
	
	/**
	 * Constructor.
	 */
	public ComputationConfiguration() {
		super();
	}

	/** {@inheritDoc} */
	@Transient
	@Override
	public String[] getJsonData() {
		List<String> data = new ArrayList<String>();
		data.add(String.valueOf(validated));
		data.add(configurationId);
		data.add(description);
		return data.toArray(new String[data.size()]);
	}

	/**
	 * {@inheritDoc} 
	 * @uml.property  name="uniqueId"
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Override
	public int getUniqueId() {
		return uniqueId;
	}


	/**
	 * {@inheritDoc} 
	 * @uml.property  name="uniqueId"
	 */
	@Override
	public void setUniqueId(int uniqueId) {
		this.uniqueId = uniqueId;
	}

	/**
	 * Getter.
	 * @return  the configurationFile
	 * @uml.property  name="configurationFile"
	 */
	@OneToOne(cascade={CascadeType.ALL})
	public FileContent getConfigurationFile() {
		return configurationFile;
	}

	/**
	 * Setter.
	 * @param configurationFile  the configurationFile to set
	 * @uml.property  name="configurationFile"
	 */
	public void setConfigurationFile(FileContent configurationFile) {
		this.configurationFile = configurationFile;
	}

	/**
	 * Getter.
	 * @return  the configurationId
	 * @uml.property  name="configurationId"
	 */
	public String getConfigurationId() {
		return configurationId;
	}

	/**
	 * Setter.
	 * @param configurationId  the configurationId to set
	 * @uml.property  name="configurationId"
	 */
	public void setConfigurationId(String configurationId) {
		this.configurationId = configurationId;
	}

	/**
	 * Getter.
	 * @return  the description
	 * @uml.property  name="description"
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Setter.
	 * @param description  the description to set
	 * @uml.property  name="description"
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Getter.
	 * @return  the validated
	 * @uml.property  name="validated"
	 */
	public boolean isValidated() {
		return validated;
	}

	/**
	 * Setter.
	 * @param validated  the validated to set
	 * @uml.property  name="validated"
	 */
	public void setValidated(boolean validated) {
		this.validated = validated;
	}

}
