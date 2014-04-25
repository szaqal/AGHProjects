package process.web.actions.ajax;

import process.web.actions.AbstractProcessAction;

import computation.model.ComputationPackage;
import computation.worker.ComputationPackageWorker;

/**
 * Details about uploaded computation package details.
 * @author  <a href="mailto:malczyk.pawel@gmail.com">malczyk.pawel@gmail.com</a>
 */
public class AjaxPackageDetailAction extends AbstractProcessAction {

	/**
	 * Serial.
	 */
	private static final long serialVersionUID = -2697263545984717808L;
	
	/**
	 * Package Id.
	 * @uml.property  name="id"
	 */
	private String id;
	
	/**
	 * File identifier.
	 * @uml.property  name="fileId"
	 */
	private String fileId;
	
	/**
	 * Computation package.
	 * @uml.property  name="comPackage"
	 * @uml.associationEnd  
	 */
	private ComputationPackage comPackage;

	
	/**
	 * Constructor.
	 */
	public AjaxPackageDetailAction() { }

	
	/** {@inheritDoc} */
	@Override
	public String execute() throws Exception {
		ComputationPackageWorker computationPackageWorker = locate(ComputationPackageWorker.class);
		comPackage = computationPackageWorker.retrieveComputationPackage(Integer.valueOf(getId()));
		return SUCCESS;
	}

	/**
	 * Getter.
	 * @return  the id
	 * @uml.property  name="id"
	 */
	public String getId() {
		return id;
	}


	/**
	 * Setter.
	 * @param id  the id to set
	 * @uml.property  name="id"
	 */
	public void setId(String id) {
		this.id = id;
	}


	/**
	 * Getter.
	 * @return  the fileId
	 * @uml.property  name="fileId"
	 */
	public String getFileId() {
		return fileId;
	}


	/**
	 * Setter.
	 * @param fileId  the fileId to set
	 * @uml.property  name="fileId"
	 */
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}


	/**
	 * Getter.
	 * @return  the comPackage
	 * @uml.property  name="comPackage"
	 */
	public ComputationPackage getComPackage() {
		return comPackage;
	}


	/**
	 * Setter.
	 * @param comPackage  the comPackage to set
	 * @uml.property  name="comPackage"
	 */
	public void setComPackage(ComputationPackage comPackage) {
		this.comPackage = comPackage;
	}
	
}
