package process.web.actions;

import computation.worker.ComputationPackageWorker;
import computation.worker.ComputationWorker;

/**
 * Action that handles process creation.
 * @author  malczyk.pawel@gmail.com
 */
public class CreateProcessAction extends AbstractProcessAction {

	/**
	 * Serial.
	 */
	private static final long serialVersionUID = -1854917029960314074L;
	
	/**
	 * Computation package id.
	 * @uml.property  name="computationPackageId"
	 */
	private String computationPackageId;
	
	/**
	 * Constructor.
	 */
	public CreateProcessAction() {
		super();
	}
	
	/** {@inheritDoc} */
	@Override
	public String execute() throws Exception {
		boolean isValiated = locate(ComputationPackageWorker.class).isPackageValidated(Integer.valueOf(computationPackageId));
		if(!isValiated) {
			return "notvalidated";
		}
		long result = locate(ComputationWorker.class).createComputation(Integer.valueOf(computationPackageId));
		return result==0 ? ERROR : SUCCESS;
	}

	/**
	 * Getter.
	 * @return  the computationPackageId
	 * @uml.property  name="computationPackageId"
	 */
	public String getComputationPackageId() {
		return computationPackageId;
	}

	/**
	 * Setter.
	 * @param computationPackageId  the computationPackageId to set
	 * @uml.property  name="computationPackageId"
	 */
	public void setComputationPackageId(String computationPackageId) {
		this.computationPackageId = computationPackageId;
	}

}
