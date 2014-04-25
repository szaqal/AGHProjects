package process.web.actions;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import core.model.FileContent;
import core.workers.FilesContentWorker;

/**
 * Action that handles file download.
 * @author  <a href="mailto:malczyk.pawel@gmail.com">malczyk.pawel@gmail.com</a>
 */
public class DownloadAction extends AbstractProcessAction {

	/**
	 * Serial.
	 */
	private static final long serialVersionUID = 7843212570907455541L;
	
	/**
	 * File being downloaded.
	 * @uml.property  name="inputStream"
	 */
	private InputStream inputStream;
	
	/**
	 * FileID.
	 * @uml.property  name="fileId"
	 */
	private String fileId;
	
	/**
	 * Filename.
	 * @uml.property  name="fileName"
	 */
	private String fileName;
	
	/**
	 * Content type.
	 * @uml.property  name="contentType"
	 */
	private String contentType;
	
	/**
	 * Constructor.
	 */
	public DownloadAction() {
		super();
	}
	

	
	
	/** {@inheritDoc} */
	@Override
	public String execute() throws Exception {
		FilesContentWorker worker = locate(FilesContentWorker.class);
		FileContent content = worker.retrieveFileContent(Integer.valueOf(fileId));
		byte [] data = content.getContent();
		fileName = content.getFileName();
		contentType = content.getMimeType();
		inputStream = new ByteArrayInputStream(data);
		return "download";
	}

	/**
	 * Getter.
	 * @return  inputsream
	 * @uml.property  name="inputStream"
	 */
	public InputStream getInputStream() {
		return inputStream;
	}

	/**
	 * Getter.
	 * @return  file identifier.
	 * @uml.property  name="fileId"
	 */
	public String getFileId() {
		return fileId;
	}

	/**
	 * Setter.
	 * @param fileId  file identifier.
	 * @uml.property  name="fileId"
	 */
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	/**
	 * Getter.
	 * @return  file name.
	 * @uml.property  name="fileName"
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * Getter.
	 * @return  content type
	 * @uml.property  name="contentType"
	 */
	public String getContentType() {
		return contentType;
	}

}
