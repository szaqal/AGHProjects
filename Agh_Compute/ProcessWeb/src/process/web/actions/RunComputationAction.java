package process.web.actions;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import computation.model.Computation;
import computation.model.ComputationSetting;
import computation.model.ComputationTaskInput;
import computation.worker.ComputationInvokerWorker;
import computation.worker.ComputationTaskWorker;
import computation.worker.ComputationWorker;

import core.utils.StringUtils;


/**
 * Action that allows running computations.
 * @author  malczyk.pawel@gmail.com
 */
public class RunComputationAction extends AbstractProcessAction {
	
	/**
	 * Logger.
	 */
	private static final Logger LOG = LoggerFactory.getLogger(RunComputationAction.class);

	/**
	 * Serial.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Computation that we are about to run.
	 * @uml.property  name="computation"
	 * @uml.associationEnd  
	 */
	private Computation computation;
	
	/**
	 * Computation identifier.
	 * @uml.property  name="computationId"
	 */
	private String computationId;
	
	/**
	 * If true computation will be executed.
	 * @uml.property  name="runComputation"
	 */
	private boolean runComputation;
	
	/**
	 * {@link ComputationWorker}  .
	 * @uml.property  name="computationWorker"
	 * @uml.associationEnd  
	 */
	private ComputationWorker computationWorker;
	
	/**
	 * {@link ComputationTaskWorker}  .
	 * @uml.property  name="computationTaskWorker"
	 * @uml.associationEnd  
	 */
	private ComputationTaskWorker computationTaskWorker;
	
	/**
	 * {@link ComputationInvokerWorker}  .
	 * @uml.property  name="computationInvokerWorker"
	 * @uml.associationEnd  
	 */
	private ComputationInvokerWorker computationInvokerWorker;
	
	/**
	 * Starting computation parameters.
	 * @uml.property  name="initParams"
	 */
	private Map<String, String> initParams = new HashMap<String, String>();
	
	/**
	 * Starting computation settings.
	 */
	private Map<String, String> initSettings = new HashMap<String, String>();
	
	/**
	 * Init lables.
	 */
	private Map<String, String> initLabels = new  HashMap<String, String>();
	
	/**
	 * Constructor.
	 */
	public RunComputationAction() {
		super();
	}
	
	@Override
	public String execute() throws Exception {
		LOG.debug("Run computation action");
		computation = getComputationWorker().retrieveComputation(Integer.valueOf(computationId));
		if(initParams.keySet().isEmpty()) {
			Collection<ComputationTaskInput> taskInputs = getComputationTaskWorker().firstTaskInputs(Integer.valueOf(computationId));
			for (ComputationTaskInput input : taskInputs) {
				initParams.put(input.getName(), null);
			}
		}
		
		Map<String, Number> initParameters = new HashMap<String, Number>();
		for (String paramKey : initParams.keySet()) {
			String value = initParams.get(paramKey);
			if (StringUtils.isNotEmpty(value)) {
				Number number = NumberUtils.createNumber(value);
				initParameters.put(paramKey, number);
			}
		}
		
		if(!runComputation) {
			List<ComputationSetting> settings = locate(ComputationWorker.class).retrieveSettings(Integer.valueOf(computationId));
			for(ComputationSetting setting : settings) {
				initSettings.put(setting.getName(), setting.getVal());
				initLabels.put(setting.getName(), setting.getDescription());
			}
		}
		
	
		if(runComputation) {
			getComputationInvokerWorker().setup(initParameters);
			getComputationInvokerWorker().setupSettings(initSettings);
			getComputationInvokerWorker().doComputation(Integer.class, computationId, getSessionData().getEmail(), getSessionData().getUserId());
			return "scheduled";
		}
	
		
		
		return SUCCESS;
	}

	/**
	 * Getter.
	 * @return  the computation
	 * @uml.property  name="computation"
	 */
	public Computation getComputation() {
		return computation;
	}

	/**
	 * Setter.
	 * @param computation  the computation to set
	 * @uml.property  name="computation"
	 */
	public void setComputation(Computation computation) {
		this.computation = computation;
	}

	/**
	 * Getter.
	 * @return  the computationId
	 * @uml.property  name="computationId"
	 */
	public String getComputationId() {
		return computationId;
	}

	/**
	 * Setter.
	 * @param computationId  the computationId to set
	 * @uml.property  name="computationId"
	 */
	public void setComputationId(String computationId) {
		this.computationId = computationId;
	}

	/**
	 * Getter.
	 * @return  the computationWorker
	 * @uml.property  name="computationWorker"
	 */
	public ComputationWorker getComputationWorker() {
		computationWorker = (computationWorker == null) ? locate(ComputationWorker.class):computationWorker;
		return computationWorker;
	}
	
	/**
	 * Getter.
	 * @return  the computationTaskWorker
	 * @uml.property  name="computationTaskWorker"
	 */
	public ComputationTaskWorker getComputationTaskWorker() {
		return computationTaskWorker = (computationTaskWorker == null) ? locate(ComputationTaskWorker.class):computationTaskWorker;
	}

	/**
	 * Getter.
	 * @return  the computationPerformerWorker
	 * @uml.property  name="computationInvokerWorker"
	 */
	public ComputationInvokerWorker getComputationInvokerWorker() {
		computationInvokerWorker = (computationInvokerWorker == null) ? locate(ComputationInvokerWorker.class)
				: computationInvokerWorker;
		return computationInvokerWorker;
	}

	/**
	 * Getter.
	 * @return  the initParams
	 * @uml.property  name="initParams"
	 */
	public Map<String, String> getInitParams() {
		return initParams;
	}

	/**
	 * Setter.
	 * @param initParams  the initParams to set
	 * @uml.property  name="initParams"
	 */
	public void setInitParams(Map<String, String> initParams) {
		this.initParams = initParams;
	}

	/**
	 * Getter.
	 * @return  the runComputation
	 * @uml.property  name="runComputation"
	 */
	public boolean isRunComputation() {
		return runComputation;
	}

	/**
	 * Setter.
	 * @param runComputation  the runComputation to set
	 * @uml.property  name="runComputation"
	 */
	public void setRunComputation(boolean runComputation) {
		this.runComputation = runComputation;
	}

	/**
	 * Getter.
	 * @return the initSettings
	 */
	public Map<String, String> getInitSettings() {
		return initSettings;
	}

	/**
	 * Setter.
	 * @param initSettings the initSettings to set
	 */
	public void setInitSettings(Map<String, String> initSettings) {
		this.initSettings = initSettings;
	}

	/**
	 * Getter.
	 * @return the initLabels
	 */
	public Map<String, String> getInitLabels() {
		return initLabels;
	}

	/**
	 * Setter.
	 * @param initLabels the initLabels to set
	 */
	public void setInitLabels(Map<String, String> initLabels) {
		this.initLabels = initLabels;
	}


	

}
