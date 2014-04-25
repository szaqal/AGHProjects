package ewpp.web;

import ewpp.business.entity.File;
import ewpp.business.workers.FilesWorker;

/**
 * Pokazuje metdane uploadowanego pliku.
 * @author malczyk.pawel@gmail.com
 *
 */
public class FileMetadataAction extends AbstractEwppAction {

	/**
	 * Serial.
	 */
	private static final long serialVersionUID = -1086917950132484440L;

	/** Id pliku. */
	private int fileId;

	/**
	 * Plik.
	 */
	private File file;

	/**
	 * Konstruktor.
	 */
	public FileMetadataAction() { };

	/** {@inheritDoc} */
	@Override
	protected String doView() {
		file = locate(FilesWorker.class).retrieveFile(fileId);
		return VIEW;
	}

	/** {@inheritDoc} */
	@Override
	public String getDefaultOpertation() {
		return VIEW;
	}

	/**
	 * @return the fileId
	 */
	public int getFileId() {
		return fileId;
	}

	/**
	 * @param fileIdArg the fileId to set
	 */
	public void setFileId(int fileIdArg) {
		this.fileId = fileIdArg;
	}

	/**
	 * @return the file
	 */
	public File getFile() {
		return file;
	}

	/**
	 * @param fileArg the file to set
	 */
	public void setFile(File fileArg) {
		this.file = fileArg;
	}

}
