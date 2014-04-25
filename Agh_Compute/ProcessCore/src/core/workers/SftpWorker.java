package core.workers;

/**
 * Does operations based on SFTP protocol.
 * @author malczyk.pawel@gmail.com
 *
 */
public interface SftpWorker {
	
	/**
	 * Uploads some data to remote location with use of SFTP protocol.
	 * @param data raw data
	 * @param host slave node host
	 * @param targetLocation location where to upload.
	 * @param sftpUser SSH user name
	 * @param sftpPassword SSH user password
	 * @param fileName file name
	 */
	void uploadFile(byte [] data, String host, String targetLocation, String sftpUser, String sftpPassword, String fileName);
	
	
	/**
	 * Local interface.
	 * @author malczyk.pawel@gmail.com
	 *
	 */
	public interface SftpWorkerLocal extends SftpWorker { }
	
	/**
	 * Remote interface.
	 * @author malczyk.pawel@gmail.com
	 *
	 */
	public interface SftpWorkerRemote extends SftpWorker { }
}
