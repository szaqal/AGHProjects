package process.web.actions.ajax;

import java.util.ArrayList;
import java.util.List;

import process.web.actions.AbstractProcessAction;
import core.model.XslTransform;
import core.workers.TransformWorker;

/**
 * Details action.
 * @author  malczyk.pawel@gmail.com
 */
public class AjaxTransformDetailAction extends AbstractProcessAction {

	/**
	 * Serial.
	 */
	private static final long serialVersionUID = -8144289136009952120L;
	
	/**
	 * Transform id.
	 * @uml.property  name="transformId"
	 */
	private String transformId;
	
	
	/**
	 * FileId.
	 * @uml.property  name="configData"
	 * @uml.associationEnd  
	 */
	private ConfigData configData;
	
	/**
	 * Constructor.
	 */
	public AjaxTransformDetailAction() {
		super();
	}
	
	@Override
	public String execute() throws Exception {
		XslTransform schema = locate(TransformWorker.class).retrieveById(Integer.valueOf(transformId));
		configData = new ConfigData(schema.getFileContent().getUniqueId());
		return SUCCESS;
	}

	/**
	 * Getter.
	 * @return  the transformId
	 * @uml.property  name="transformId"
	 */
	public String getTransformId() {
		return transformId;
	}

	/**
	 * Setter.
	 * @param transformId  the transformId to set
	 * @uml.property  name="transformId"
	 */
	public void setTransformId(String transformId) {
		this.transformId = transformId;
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
