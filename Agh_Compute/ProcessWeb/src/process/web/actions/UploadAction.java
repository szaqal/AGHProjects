package process.web.actions;

import java.io.File;
import java.util.Calendar;
import java.util.Collection;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import computation.model.ComputationPackage;
import computation.model.ComputingNode;
import computation.worker.ComputationPackageWorker;
import computation.worker.NodesWorker;

import core.model.FileContent;
import core.utils.StringUtils;
import core.workers.SftpWorker;

/**
 * Upload action. 
 * @author  <a href="mailto:malczyk.pawel@gmail.com">malczyk.pawel@gmail.com</a>
 */
public class UploadAction extends AbstractProcessAction {
	
	/**
	 * Serial.
	 */
	private static final long serialVersionUID = -2016567423887794579L;

	/**
	 * Logger.
	 */
	private static final Logger LOG = LoggerFactory.getLogger(UploadAction.class);


	/** File. */
	private File file;

	/**
	 * Content type.
	 * @uml.property  name="contentType"
	 */
	private String fileContentType;

	/**
	 * fileName.
	 * @uml.property  name="filename"
	 */
	private String fileFileName;

	/**
	 * Title.
	 * @uml.property  name="title"
	 */
	private String title;
	

	/** Constructor. */
	public UploadAction() {
		super();
	};



	/** {@inheritDoc} */
	@Override
	public String execute() throws Exception {
		LOG.debug("Uploading  {}", file + StringUtils.WHITESPACE + fileFileName + StringUtils.WHITESPACE + fileFileName );
		byte [] content = FileUtils.readFileToByteArray(file);
		ComputationPackage uploadingPackage = createPackageFromUpload(content);
		
		ComputationPackageWorker worker = locate(ComputationPackageWorker.class);
		worker.saveComputationPackage(uploadingPackage, fileFileName, fileContentType);
		
		Collection<ComputingNode> nodes = locate(NodesWorker.class).retrieveComputingNodes();
		SftpWorker sftpWoker = locate(SftpWorker.class);
		for(ComputingNode computingNode: nodes) {
			try {
				sftpWoker.uploadFile(content, computingNode.getInetAddr(), computingNode.getLibLocation(), 
						computingNode.getSshUser(), computingNode.getSshPasswd(), fileFileName);
			} catch (Exception e) {
				LOG.warn("Exception ", e);
			}
		}
		return SUCCESS;
	}


	/**
	 * Creates {@link ComputationPackage} which will be uploaded.
	 * @param content file content
	 * @return package.
	 */
	private ComputationPackage createPackageFromUpload(byte[] content) {
		ComputationPackage comPackage = new ComputationPackage();
		comPackage.setAddDate(Calendar.getInstance());
		comPackage.setContent(new FileContent(content));
		comPackage.setTitle(title);
		return comPackage;
	}

	/**
	 * @param uploadingFile  the file to set
	 * @uml.property  name="file"
	 */
	public void setFile(File uploadingFile) {
		this.file = uploadingFile;
	}


	/**
	 * @return  the title
	 * @uml.property  name="title"
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param fileTitle  the title to set
	 * @uml.property  name="title"
	 */
	public void setTitle(String fileTitle) {
		this.title = fileTitle;
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

}
