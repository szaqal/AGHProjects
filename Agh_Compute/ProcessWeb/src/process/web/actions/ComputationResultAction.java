package process.web.actions;

import computation.model.ComputationResult;
import computation.worker.ComputationResultWorker;

/**
 * Action that handles computation results.
 * @author  malczyk.pawel@gmail.com
 * @deprecated don't use anymore
 */
@Deprecated
public class ComputationResultAction extends AbstractProcessAction {

	/**
	 * Serial. 
	 */
	private static final long serialVersionUID = -2177549722378054343L;
	
	/**
	 * Found computation result.
	 * @uml.property  name="computationResult"
	 * @uml.associationEnd  
	 */
	private ComputationResult computationResult;
	
	/**
	 * Result identifier.
	 * @uml.property  name="resultId"
	 */
	private String resultId;
	
	/**
	 * Constructor.
	 */
	public ComputationResultAction() {
		super();
	}
	
	
	/** {@inheritDoc} */
	@Override
	public String execute() throws Exception {
		computationResult = locate(ComputationResultWorker.class).retrieveComputationResult(Integer.valueOf(resultId));
		return VIEW;
	}


	/**
	 * Getter.
	 * @return  the resultId
	 * @uml.property  name="resultId"
	 */
	public String getResultId() {
		return resultId;
	}


	/**
	 * Setter.
	 * @param resultId  the resultId to set
	 * @uml.property  name="resultId"
	 */
	public void setResultId(String resultId) {
		this.resultId = resultId;
	}


	/**
	 * Getter.
	 * @return  the computationResult
	 * @uml.property  name="computationResult"
	 */
	public ComputationResult getComputationResult() {
		return computationResult;
	}


	/**
	 * Setter.
	 * @param computationResult  the computationResult to set
	 * @uml.property  name="computationResult"
	 */
	public void setComputationResult(ComputationResult computationResult) {
		this.computationResult = computationResult;
	}

}
