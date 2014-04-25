package core.workers;

/**
 * Pdf worker.
 * @author malczyk.pawel@gmail.com
 *
 */
public interface PdfWorker {
	
	
	/**
	 * Returns pdf based on XSL transformation file content id.
	 * @param transformId fil content where FOP transformation can be found.
	 * @param xmlData data xml content
	 * @return raw data bytes
	 */
	byte [] getPdf(int transformId, byte[] xmlData);
	
	/**
	 * Local interface.
	 * @author malczyk.pawel@gmail.com
	 *
	 */
	public interface PdfWorkerLocal extends PdfWorker { };
	
	/**
	 * Remote interface.
	 * @author malczyk.pawel@gmail.com
	 *
	 */
	public interface PdfWorkerRemote extends PdfWorker { };
	
	
}
