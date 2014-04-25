package core.workers;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarInputStream;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.workers.FilesContentWorker.FilesContentWorkerLocal;
import core.workers.JarWorker.JarWorkerLocal;
import core.workers.JarWorker.JarWorkerRemote;

/**
 * Worker implementation.
 * @author  <a href="mailto:malczyk.pawel@gmail.com>malczyk.pawel@gmail.com</a>
 */
@Stateless
@Remote(JarWorkerRemote.class)
@Local(JarWorkerLocal.class)
public class JarWorkerBean extends AbstractWorkerBean implements JarWorker {
	
	/**
	 * Exception message string.
	 */
	private static final String EXEPTION_CAUGHT = "Exeption caught [{}]";

	/**
	 * Logger.
	 */
	private static final Logger LOG = LoggerFactory.getLogger(JarWorkerBean.class);
	
	/**
	 * Files content worker.
	 * @uml.property  name="filesContentWorker"
	 * @uml.associationEnd  
	 */
	@EJB
	private FilesContentWorkerLocal filesContentWorker;
	
	/**
	 * Constructor.
	 */
	public JarWorkerBean() {
		super();
	}

	/** {@inheritDoc} */
	@Override
	public JarFile createFromBytes(byte[] fileData) {
		File tempFile = filesContentWorker.createTempFile(fileData);
		JarFile jarFile=null;
		try {
			jarFile = new JarFile(tempFile);
		} catch (IOException e) {
			LOG.warn(EXEPTION_CAUGHT, e.getMessage());
		}
		return jarFile;
	}

	/** {@inheritDoc} */
	@Override
	public JarEntry retrieveJarEntry(byte[] fileData, String entryName) {
		
		JarEntry entry = null;
		JarEntry result = null;
		try {
			JarInputStream packageFileStream = new JarInputStream(new ByteArrayInputStream(fileData));
			while ( (entry = packageFileStream.getNextJarEntry()) != null) {
				LOG.info(entry.getName());
				if(entryName.equals(entry.getName())) {
					result = entry;
					break;
				}
			}
			packageFileStream.close();
		} catch (IOException e) {
			LOG.warn(EXEPTION_CAUGHT, e.getMessage());
		}
		
		return result;
	}

}
