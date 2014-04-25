package core.workers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.model.FileContent;
import core.workers.FilesContentWorker.FilesContentWorkerLocal;
import core.workers.FilesContentWorker.FilesContentWorkerRemote;

/**
 * Worker implementation. 
 * @author <a href="mailto:malczyk.pawel@gmail.com">malczyk.pawel@gmail.com</a>
 *
 */
@Stateless
@Local(FilesContentWorkerLocal.class)
@Remote(FilesContentWorkerRemote.class)
public class FilesContentWorkerBean extends AbstractWorkerBean implements FilesContentWorker {
	
	/**
	 * Constant.
	 */
	public static final String LINUX_TEMP = "/tmp/";

	/**
	 * Logger.
	 */
	private static final Logger LOG = LoggerFactory.getLogger(FilesContentWorkerBean.class);
	
	
	/**
	 * 
	 * Constructor.
	 */
	public FilesContentWorkerBean() {
		super();
	}

	/** {@inheritDoc} */
	@Override
	public byte[] retrieveContent(int fileContentId) {
		FileContent fileContent = find(fileContentId, FileContent.class);
		return fileContent.getContent();
	}

	/** {@inheritDoc} */
	@Override
	public FileContent saveFileContent(FileContent content) {
		return save(content);
	}

	/** {@inheritDoc} */
	@Override
	public FileContent retrieveFileContent(int fileContentId) {
		return find(fileContentId, FileContent.class);
	}

	/** {@inheritDoc} */
	@Override
	public File createTempFile(byte[] data) {
		try {
			long time = new Date().getTime();
			File file = new File(LINUX_TEMP + "file_" + time);
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(data);
			fos.close();	
			return file;
		}catch(IOException e) {
			LOG.warn(e.getMessage());
		}

		return null;
	}

}
