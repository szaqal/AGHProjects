package process.web.actions.ajax;

import java.util.ArrayList;
import java.util.List;

import process.web.actions.AbstractProcessAction;

import computation.model.ComputationConfiguration;
import computation.worker.ComputationConfgurationWorker;

/**
 * Ajax configuration details action.
 * @author  malczyk.pawel@gmail.com
 */
public class AjaxConfigurationDetailsAction extends AbstractProcessAction {

	/**
	 * Serial.
	 */
	private static final long serialVersionUID = -703544922558556234L;
	
	/**
	 * Configuration id.
	 * @uml.property  name="configurationId"
	 */
	private String configurationId;
	
	/**
	 * FileId.
	 * @uml.property  name="configData"
	 * @uml.associationEnd  
	 */
	private ConfigData configData;
	
	/**
	 * Constructor.
	 */
	public AjaxConfigurationDetailsAction() {
		super();
	}
	
	/** {@inheritDoc} */
	@Override
	public String execute() throws Exception {
		ComputationConfiguration config = locate(ComputationConfgurationWorker.class).retrieveById(Integer.valueOf(configurationId));
		configData = new ConfigData(Integer.valueOf(config.getConfigurationFile().getUniqueId()));
		return SUCCESS;
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
	 * DTO storing details.
	 * 
	 * @author malczyk.pawel@gmail.com
	 *
	 */
	public class ConfigData {
		
		/**
		 * Id of file where configuration is stored.
		 */
		private long fileId;
		
		/**
		 * Constructor.
		 * @param fileId file identifier.
		 */
		public ConfigData(long fileId) {
			this.fileId = fileId;
		}
		
		
		/**
		 * Data for json resolution.
		 * @return array of data
		 */
		public String[] getJsonData() {
			List<String> data = new ArrayList<String>();
			data.add(String.valueOf(fileId));
			return data.toArray(new String[data.size()]);
		}
	}

	/**
	 * Getter.
	 * @return  the configData
	 * @uml.property  name="configData"
	 */
	public ConfigData getConfigData() {
		return configData;
	}

	/**
	 * Setter.
	 * @param configData  the configData to set
	 * @uml.property  name="configData"
	 */
	public void setConfigData(ConfigData configData) {
		this.configData = configData;
	}



}
