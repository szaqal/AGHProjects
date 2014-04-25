package ewpp.web.projects;

import ewpp.business.entity.ProjectStage;
import ewpp.business.entity.ProjectStageIteration;
import ewpp.business.workers.FilesWorker;
import ewpp.business.workers.ProjectWorker;
import ewpp.utils.StringUtils;
import ewpp.web.AbstractEwppAction;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Akacja zarzadzania etapem projektu.
 * @author malczyk.pawel@gmail.com
 *
 */
public class ManageStageAction extends AbstractEwppAction {

	/** Serial. */
	private static final long serialVersionUID = -6489122969486096891L;
	
	/** Stala. */
	private static final String ACCEPT_STAGE = "acceptProjectStage";
	
	/** Stala. */
	private static final String UPLOAD = "upload";

	/** Logger. */
	private static final Logger LOG = LoggerFactory.getLogger(ManageStageAction.class);
	
	/** Lista iteracji etapu.*/
	private List<ProjectStageIteration> stageIterations;

	/** Edytowany etap ptojektu. */
	private ProjectStage projectStage;

	/** Identyfikator etapu projektu. */
	private Integer projectStageId;
	
	/** Identyfikator projektu. */
	private Integer projectId;
	
	/** Plik. */
	private File file;

	/** ContentType. */
	private String fileContentType;

	/** Nazwa pliku. */
	private String fileFileName;
	
	/** Identyfikator itearcji etapu projektu do ktorego uploadowanyu plik. */
	private Integer stageIterationId;

	/** Konstruktor. */
	public ManageStageAction() { };
	
	/** {@inheritDoc} */
	@Override
	public String execute() throws Exception {
		String operation  = super.execute();
		
		if(operation.equals(ACCEPT_STAGE)){
			operation = doAccept();
		} else if(operation.equals(UPLOAD)) {
			operation = doUpload();
		}
		
		LOG.debug(operation);
		return operation;
	}
	
	/**
	 * Upload pliku.
	 * @return widok
	 */
	private String doUpload() {
		LOG.debug("Uploading  {}",  fileFileName + StringUtils.WHITESPACE + fileContentType );
		try {
			byte [] content = FileUtils.readFileToByteArray(file);
			locate(FilesWorker.class).addFileToIteration(stageIterationId, content, fileContentType, fileFileName, getLoggedUser());
		} catch (IOException e) {
			LOG.error(e.getMessage());
		}
		return "uploaded";
	}


	/** {@inheritDoc} */
	@Override
	protected String doEdit() {
		LOG.debug("Editing project stage");
		ProjectWorker worker = locate(ProjectWorker.class);
		projectStage = worker.retrieveProjectStage(projectStageId);
		LOG.debug("{}", projectStage.getStageStatus());
		stageIterations = worker.retrieveIterations(projectStageId);
		return EDIT;
	}
	
	/** {@inheritDoc} */
	@Override
	public String getDefaultOpertation() {
		return EDIT;
	}
	
	/**
	 * Przeprowadza akceptacje.
	 * @return operacja.
	 */
	private String doAccept() {
		locate(ProjectWorker.class).acceptProjectStage(projectStageId);
		return "accepted";
	}


	/**
	 * @return the projectStage
	 */
	public ProjectStage getProjectStage() {
		return projectStage;
	}


	/**
	 * @param projectStage the projectStage to set
	 */
	public void setProjectStage(ProjectStage projectStage) {
		this.projectStage = projectStage;
	}


	/**
	 * @return the projectStageId
	 */
	public String getProjectStageId() {
		return String.valueOf(projectStageId);
	}


	/**
	 * @param projectStageId the projectStageId to set
	 */
	public void setProjectStageId(String projectStageId) {
		this.projectStageId = Integer.parseInt(projectStageId);
	}

	/**
	 * Getter.
	 * @return the projectId
	 */
	public final String getProjectId() {
		return String.valueOf(projectId);
	}

	/**
	 * Setter.
	 * @param projectId the projectId to set
	 */
	public final void setProjectId(String projectId) {
		this.projectId = Integer.parseInt(projectId);
	}

	/**
	 * Getter.
	 * @return the file
	 */
	public final File getFile() {
		return file;
	}

	/**
	 * Setter.
	 * @param file the file to set
	 */
	public final void setFile(File file) {
		this.file = file;
	}

	/**
	 * Getter.
	 * @return the fileContentType
	 */
	public final String getFileContentType() {
		return fileContentType;
	}

	/**
	 * Setter.
	 * @param fileContentType the fileContentType to set
	 */
	public final void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}

	/**
	 * Getter.
	 * @return the fileFileName
	 */
	public final String getFileFileName() {
		return fileFileName;
	}

	/**
	 * Setter.
	 * @param fileFileName the fileFileName to set
	 */
	public final void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	/**
	 * Getter.
	 * @return the stageIterations
	 */
	public final List<ProjectStageIteration> getStageIterations() {
		return stageIterations;
	}

	/**
	 * Setter.
	 * @param stageIterations the stageIterations to set
	 */
	public final void setStageIterations(List<ProjectStageIteration> stageIterations) {
		this.stageIterations = stageIterations;
	}

	/**
	 * Getter.
	 * @return the stageIterationId
	 */
	public final Integer getStageIterationId() {
		return stageIterationId;
	}

	/**
	 * Setter.
	 * @param stageIterationId the stageIterationId to set
	 */
	public final void setStageIterationId(Integer stageIterationId) {
		this.stageIterationId = stageIterationId;
	}
}
