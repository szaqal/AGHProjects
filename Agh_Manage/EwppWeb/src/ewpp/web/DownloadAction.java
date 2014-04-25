package ewpp.web;

import ewpp.business.entity.File;
import ewpp.business.workers.FilesWorker;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * Akcja sciagania plikow serwera.
 * @author malczyk.pawel@gmail.com
 *
 */
public class DownloadAction extends AbstractEwppAction {

	/**
	 * Serial.
	 */
	private static final long serialVersionUID = 620732606614642534L;
	
	/**
	 * Sciagny plik.
	 */
	private InputStream inputStream;
	
	/**
	 * Identyfikator sciaganego pliku.
	 */
	private int fileId;
	
	/**
	 * Nazwa pliku. 
	 */
	private String fileName;
	
	/**
	 * Typ zawartosci.
	 */
	private String contentType;
	
	/**
	 * Domyslny konstruktor.
	 */
	public DownloadAction() { }
	
	/** {@inheritDoc} */
	@Override
	public String execute() throws Exception {
		File file = locate(FilesWorker.class).retrieveFile(fileId);
		fileName = file.getFileName();
		contentType = file.getContentType();
		inputStream = new ByteArrayInputStream(file.getContent());
		return "download";
	}

	/**
	 * Getter.
	 * @return inputsream
	 */
	public InputStream getInputStream() {
		return inputStream;
	}

	/**
	 * Getter.
	 * 
	 * @return identyfikator pliku.
	 */
	public int getFileId() {
		return fileId;
	}

	/**
	 * Setter.
	 * 
	 * @param fileId
	 *            identyfikator pliku.
	 */
	public void setFileId(int fileId) {
		this.fileId = fileId;
	}

	/**
	 * Getter.
	 * @return
	 * 		nazwa pliku.
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * Getter.
	 * @return
	 * 	typ zawartosci
	 */
	public String getContentType() {
		return contentType;
	}
	

}
