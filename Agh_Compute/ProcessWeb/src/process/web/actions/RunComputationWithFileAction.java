package process.web.actions;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import computation.model.Computation;
import computation.model.ComputationTaskInput;
import computation.worker.ComputationInvokerWorker;
import computation.worker.ComputationTaskWorker;
import computation.worker.ComputationWorker;
import computation.worker.ValidationWorker;

import core.model.ApplicationConfigurationFile;
import core.utils.StringUtils;
import core.workers.ApplicationConfigurationFilesWorker;

/**
 * Run computation with file action.
 * @author  malczyk.pawel@gmail.com
 */
public class RunComputationWithFileAction extends AbstractProcessAction {
	
	/**
	 * Value element attribute.
	 */
	private static final String VALUE_ATTR = "value";

	/**
	 * Name value attribute.
	 */
	private static final String NAME_ATTR = "name";

	/**
	 * Logger.
	 */
	private static final Logger LOG = LoggerFactory.getLogger(RunComputationWithFileAction.class);

	/**
	 * Serial.
	 */
	private static final long serialVersionUID = 2934306504610191724L;
	
	/**
	 * Computation identifier.
	 * @uml.property  name="computationId"
	 */
	private String computationId;
	
	/**
	 * Operation.
	 */
	private String operation;
	
	/**
	 * {@link ComputationWorker}  .
	 * @uml.property  name="computationWorker"
	 * @uml.associationEnd  
	 */
	private ComputationWorker computationWorker;
	
	/**
	 * {@link ComputationInvokerWorker}  .
	 * @uml.property  name="computationInvokerWorker"
	 * @uml.associationEnd  
	 */
	private ComputationInvokerWorker computationInvokerWorker;
	
	/**
	 * Starting computation parameters.
	 */
	private Map<String, String> initParams = new HashMap<String, String>();
	
	/**
	 * If true computation will be executed.
	 * @uml.property  name="runComputation"
	 */
	private boolean runComputation;
	
	/**
	 * Computation that we are about to run.
	 * @uml.property  name="computation"
	 * @uml.associationEnd  
	 */
	private Computation computation;
	
	/**
	 * File.
	 * @uml.property  name="file"
	 */
	private File file;

	
	/**
	 * Content type.
	 * @uml.property  name="fileContentType"
	 */
	private String fileContentType;
	
	/**
	 * fileName.
	 * @uml.property  name="fileFileName"
	 */
	private String fileFileName;
	
	/**
	 * {@link ComputationTaskWorker}  .
	 * @uml.property  name="computationTaskWorker"
	 * @uml.associationEnd  
	 */
	private ComputationTaskWorker computationTaskWorker;
	
	
	/**
	 * Constructor.
	 */
	public RunComputationWithFileAction() {
		super();
	}
	
	@Override
	public void validate() {
		if ("save".equals(operation)) {
			String strFile = "file";
			if (file == null) {
				addFieldError(strFile, getText("common.requiredField"));
			}

			ApplicationConfigurationFilesWorker appConfWorker = locate(ApplicationConfigurationFilesWorker.class);
			ApplicationConfigurationFile confFile = appConfWorker.retrieveStartValidation();
			ValidationWorker validationWorker = locate(ValidationWorker.class);
			
			byte[] uploadContent;
			try {
				uploadContent = FileUtils.readFileToByteArray(file);
				boolean result  = validationWorker.validate(uploadContent, confFile.getFileContent().getContent());
				if(!result) {
					addFieldError(strFile, getText("startfile.invalid"));
				}
			} catch (IOException e) {
				LOG.warn("IOException", e);
			}
		}
	}
	
	
	/** {@inheritDoc} */
	@SuppressWarnings("unchecked")
	@Override
	public String execute() throws Exception {
		LOG.debug("Run computation with file action");
		if (file == null) {
			return "loadFile";
		}
		
		computation = getComputationWorker().retrieveComputation(Integer.valueOf(computationId));
		
		
		SAXBuilder builder = new SAXBuilder();
		Document doc = builder.build(new FileInputStream(file));
		Element root = doc.getRootElement();
		
		List<Element> inputs = root.getChild("inputs").getChildren("input");
		Map<String, String> inputsMap = new HashMap<String, String>();
		
		for(Element e: inputs) {
			String name = e.getAttribute(NAME_ATTR).getValue();
			String value = e.getAttribute(VALUE_ATTR).getValue();
			inputsMap.put(name, value);
		}
		
		List<Element> settings = root.getChild("settings").getChildren("setting");
		Map<String, String> initialSettings = new HashMap<String, String>();
		for(Element e: settings	) {
			String name = e.getAttribute(NAME_ATTR).getValue();
			String value = e.getAttribute(VALUE_ATTR).getValue();
			initialSettings.put(name, value);
		}
		
		
		if(initParams.keySet().isEmpty()) {
			Collection<ComputationTaskInput> taskInputs = getComputationTaskWorker().firstTaskInputs(Integer.valueOf(computationId));
			for (ComputationTaskInput input : taskInputs) {
				initParams.put(input.getName(), inputsMap.get(input.getName()));
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
		
		
		if(runComputation) {
			getComputationInvokerWorker().setup(initParameters);
			getComputationInvokerWorker().setupSettings(initialSettings);
			getComputationInvokerWorker().doComputation(Integer.class, computationId, getSessionData().getEmail(), getSessionData().getUserId());
		}
		
		return "scheduled";
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
	 * @return  the file
	 * @uml.property  name="file"
	 */
	public File getFile() {
		return file;
	}


	/**
	 * Setter.
	 * @param file  the file to set
	 * @uml.property  name="file"
	 */
	public void setFile(File file) {
		this.file = file;
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
	 * @return  the computationTaskWorker
	 * @uml.property  name="computationTaskWorker"
	 */
	public ComputationTaskWorker getComputationTaskWorker() {
		return computationTaskWorker = (computationTaskWorker == null) ? locate(ComputationTaskWorker.class):computationTaskWorker;
	}


	/**
	 * Getter.
	 * @return the fileContentType
	 */
	public String getFileContentType() {
		return fileContentType;
	}


	/**
	 * Setter.
	 * @param fileContentType the fileContentType to set
	 */
	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}


	/**
	 * Getter.
	 * @return the fileFileName
	 */
	public String getFileFileName() {
		return fileFileName;
	}


	/**
	 * Setter.
	 * @param fileFileName the fileFileName to set
	 */
	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	/**
	 * Getter.
	 * @return the operation
	 */
	public String getOperation() {
		return operation;
	}

	/**
	 * Setter.
	 * @param operation the operation to set
	 */
	public void setOperation(String operation) {
		this.operation = operation;
	}


}
