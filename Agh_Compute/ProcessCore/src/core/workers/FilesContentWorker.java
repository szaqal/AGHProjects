package core.workers;

import java.io.File;

import core.model.FileContent;

/**
 * Files worker. 
 * @author <a href="mailto:malczyk.pawel@gmail.com">malczyk.pawel@gmail.com</a>
 *
 */
public interface FilesContentWorker {
	
	/**
	 * Retrieves actual content from file content.
	 * @param fileContentId file content identifier.
	 * @return content
	 */
	byte [] retrieveContent(int fileContentId);
	
	/**
	 * Retrieves fileContent.
	 * @param fileContentId file content identifier
	 * @return {@link FileContent}
	 */
	FileContent retrieveFileContent(int fileContentId);
	
	/**
	 * Saves file content.
	 * @param content file content to save
	 * @return id of saved file content
	 */
	FileContent saveFileContent(FileContent content);
	
	/**
	 * Creates temporary file in /tmp/.
	 * @param data data to be saved in file
	 * @return created temporary file
	 */
	File createTempFile(byte [] data);
	
	
	/**
	 * Local interface.
	 * @author <a href="mailto:malczyk.pawel@gmail.com">malczyk.pawel@gmail.com</a>
	 *
	 */
	public interface FilesContentWorkerLocal extends FilesContentWorker { };
	
	/**
	 * Remote interface. 
	 */
	public interface FilesContentWorkerRemote extends FilesContentWorker { };
	
}
