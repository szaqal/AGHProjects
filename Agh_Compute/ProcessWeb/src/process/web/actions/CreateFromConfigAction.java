package process.web.actions;

import computation.worker.ComputationConfgurationWorker;
import computation.worker.ComputationWorker;

/**
 * Creates computation from configuration file.
 * @author  malczyk.pawel@gmail.com
 */
public class CreateFromConfigAction extends AbstractProcessAction {

	/**
	 * Serial.
	 */
	private static final long serialVersionUID = 5054876271022932600L;
	
	/**
	 * Computation config id.
	 * @uml.property  name="computationConfigId"
	 */
	private String computationConfigId;
	
	/**
	 * Constructor.
	 */
	public CreateFromConfigAction() {
		super();
	}
	
	
	/** {@inheritDoc} */
	@Override 
	public String execute() throws Exception {
		
		boolean isValiated = locate(ComputationConfgurationWorker.class).isConfigurationValidated(Integer.valueOf(computationConfigId));
		if(!isValiated) {
			return "notvalidated";
		}
		
		locate(ComputationWorker.class).createComputationFromConfig(Integer.valueOf(computationConfigId));
		return SUCCESS;
	}


	/**
	 * Getter.
	 * @return  the computationConfigId
	 * @uml.property  name="computationConfigId"
	 */
	public String getComputationConfigId() {
		return computationConfigId;
	}


	/**
	 * Setter.
	 * @param computationConfigId  the computationConfigId to set
	 * @uml.property  name="computationConfigId"
	 */
	public void setComputationConfigId(String computationConfigId) {
		this.computationConfigId = computationConfigId;
	}

}
