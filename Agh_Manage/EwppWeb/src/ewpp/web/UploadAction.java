package ewpp.web;

import ewpp.business.workers.FilesWorker;
import ewpp.utils.StringUtils;
import ewpp.web.projects.ProjectItemsAction;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Akcja uzywana do uploadowania plikow na serwer.
 * @author malczyk.pawel@gmail.com
 */
public class UploadAction extends AbstractEwppAction {

	/**
	 * Serial.
	 */
	private static final long serialVersionUID = -2016567423887794579L;

	/**
	 * Logger.
	 */
	private static final Logger LOG = LoggerFactory.getLogger(UploadAction.class);


	/**
	 * Plik.
	 */
	private File file;

	/** ContentType. */
	private String fileContentType;

	/** Nazwa pliku. */
	private String fileFileName;

	/** Identyfikator projektu. */
	private int projectId;

	/** Naglowek. */
	private String title;

	/** Operacja. */
	private String operation;
	
	/** Czy bedzie publiczny. */
	private boolean isPublic;

	/** Konstruktor. */
	public UploadAction() { };



	/** {@inheritDoc} */
	@Override
	public String execute() throws Exception {
		LOG.debug("Uploading  {}", file + StringUtils.WHITESPACE + fileFileName + StringUtils.WHITESPACE + fileContentType+ " Public: " +isPublic );
		if (ProjectItemsAction.IMPORT_FILE.equals(operation)) {
			LOG.debug("Upload to project");
			byte [] content = FileUtils.readFileToByteArray(file);
			locate(FilesWorker.class).addFileToProject(projectId, fileContentType, fileFileName, content, title, isPublic);
		}

		return SUCCESS;
	}

	/**
	 * @param uploadingFile the file to set
	 */
	public void setFile(File uploadingFile) {
		this.file = uploadingFile;
	}

	/**
	 * @param contentType the fileContentType to set
	 */
	public void setFileContentType(String contentType) {
		this.fileContentType = contentType;
	}

	/**
	 * @param fileFilename the fileFilename to set
	 */
	public void setFileFileName(String fileFilename) {
		this.fileFileName = fileFilename;
	}

	/**
	 * @return the projectId
	 */
	public int getProjectId() {
		return projectId;
	}

	/**
	 * @param projId the projectId to set
	 */
	public void setProjectId(int projId) {
		this.projectId = projId;
	}

	/**
	 * @param oper the operation to set
	 */
	public void setOperation(String oper) {
		this.operation = oper;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param fileTitle the title to set
	 */
	public void setTitle(String fileTitle) {
		this.title = fileTitle;
	}



	/**
	 * Getter.
	 * @return the isPublic
	 */
	public final boolean isPublic() {
		return isPublic;
	}



	/**
	 * Setter.
	 * @param isPublic the isPublic to set
	 */
	public final void setPublic(boolean isPublic) {
		this.isPublic = isPublic;
	}




}
