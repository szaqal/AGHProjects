package process.web.actions.ajax;

import java.util.ArrayList;
import java.util.List;

import process.web.actions.AbstractProcessAction;

import computation.model.ValidationSchema;
import computation.worker.ValidationWorker;

/**
 * Validation schema details action.
 * @author  malczyk.pawel@gmail.com
 */
public class AjaxValidationDetailsAction extends AbstractProcessAction {

	/**
	 * Serial.
	 */
	private static final long serialVersionUID = 3607800268987262626L;
	
	
	/**
	 * Schema primary key.
	 * @uml.property  name="schemaId"
	 */
	private String schemaId;
	
	
	/**
	 * FileId.
	 * @uml.property  name="configData"
	 * @uml.associationEnd  
	 */
	private ConfigData configData;
	
	
	/**
	 * Constructor.
	 */
	public AjaxValidationDetailsAction() {
		super();
	}
	
	
	@Override
	public String execute() throws Exception {
		ValidationSchema schema = locate(ValidationWorker.class).retrieveSchema(Integer.valueOf(schemaId));
		configData = new ConfigData(schema.getFileContent().getUniqueId());
		return SUCCESS;
	}
	/**
	 * Getter.
	 * @return  the schemaId
	 * @uml.property  name="schemaId"
	 */
	public String getSchemaId() {
		return schemaId;
	}

	/**
	 * Setter.
	 * @param schemaId  the schemaId to set
	 * @uml.property  name="schemaId"
	 */
	public void setSchemaId(String schemaId) {
		this.schemaId = schemaId;
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
