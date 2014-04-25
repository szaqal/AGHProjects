package computation.worker;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import computation.model.ComputationPackage;
import computation.worker.ComputationPackageWorker.ComputationPackageWorkerLocal;
import computation.worker.ComputationPackageWorker.ComputationPackageWorkerRemote;

import core.model.FileContent;
import core.workers.AbstractWorkerBean;

/**
 * Worker implementation. 
 * @author <a href="mailto:malczyk.pawel@gmail.com">malczyk.pawel@gmail.com</a>
 *
 */
@Stateless
@Remote(ComputationPackageWorkerRemote.class)
@Local(ComputationPackageWorkerLocal.class)
public class ComputationPackageWorkerBean extends AbstractWorkerBean implements ComputationPackageWorker {
	
	/**
	 * Logger.
	 */
	private static final Logger LOG = LoggerFactory.getLogger(ComputationPackageWorkerBean.class);
	

	/**
	 * Constructor.
	 */
	public ComputationPackageWorkerBean() {
		super();
	}

	/** {@inheritDoc} */
	@Override
	public ComputationPackage retrieveComputationPackage(int packageId) {
		return find(packageId, ComputationPackage.class);
	}

	/** {@inheritDoc} */
	@Override
	public List<ComputationPackage> retrieveComputationPackages() {
		Query query = getEntityManager().createQuery("SELECT cp FROM ComputationPackage AS cp");
		return getResultList(query, ComputationPackage.class);
	}

	/** {@inheritDoc} */
	@Override
	public ComputationPackage saveComputationPackage(ComputationPackage computationPackage, String fileName, String mime) {
		computationPackage.getContent().setFileName(fileName);
		computationPackage.getContent().setMimeType(mime);
		return save(computationPackage);
	}

	/** {@inheritDoc} */
	@Override
	public ComputationPackage saveComputationPackage(byte[] computationPackage, String title, String fileName, String mime) {
		
		ComputationPackage comPackage = new ComputationPackage();
		comPackage.setAddDate(Calendar.getInstance());
		FileContent fileContent = new FileContent(computationPackage);
		comPackage.setContent(fileContent);
		comPackage.setTitle(title);
		return saveComputationPackage(comPackage, fileName, mime);
		
	}

	/** {@inheritDoc} */
	@Override
	public boolean isPackageValid(ComputationPackage computationPackage) {
		boolean result = false;
		
		try {
			byte [] fileContent = computationPackage.getContent().getContent();
			JarInputStream packageFileStream = new JarInputStream(new ByteArrayInputStream(fileContent));
			JarEntry entry = null;
			JarEntry config = null;
			while ( (entry = packageFileStream.getNextJarEntry()) != null) {
				LOG.info(entry.getName());
				if(ComputationPackage.CONFIG_FILENAME.equals(entry.getName())) {
					config = entry;
				}
			}
			
			if(config != null) {
				result = true;
			}
				
		} catch (NullPointerException e) {
			LOG.warn("Invalid fileContent");
		} catch (IOException e) {
			LOG.warn("Error reading Jar insput stream");
		}
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public boolean isPackageValid(int computationPackageId) {
		ComputationPackage compPackage = retrieveComputationPackage(computationPackageId);
		return isPackageValid(compPackage);
	}

	/** {@inheritDoc} */
	@Override
	public byte[] retrieveConfigFromPackage(int computationPackageId) {
		ComputationPackage pack = retrieveComputationPackage(computationPackageId);
		byte [] fileContent = pack.getContent().getContent();
		try {
			JarInputStream packageFileStream = new JarInputStream(new ByteArrayInputStream(fileContent));
			JarEntry entry = null;
			while ((entry = packageFileStream.getNextJarEntry()) != null) {
				LOG.info(entry.getName());
				if (ComputationPackage.CONFIG_FILENAME.equals(entry.getName())) {
					long size = entry.getSize();
					byte[] content = new byte[(int) size];
					int rb = 0;
					int chunk = 0;
					while (((int) size - rb) > 0) {
						chunk = packageFileStream.read(content, rb, (int) size - rb);
						if (chunk == -1) {
							break;
						}
						rb += chunk;
					}
					return content;
				}
			}
//			ByteArrayOutputStream configFileOutStream = new ByteArrayOutputStream();
//			JarOutputStream outStream = new JarOutputStream(configFileOutStream);
//			outStream.putNextEntry(config);
//			result = configFileOutStream.toByteArray();
//			outStream.close();
		} catch (IOException e) {
			LOG.warn("Exception", e);
		}
		
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public boolean isPackageValidated(int packageId) {
		ComputationPackage computationPackage = retrieveComputationPackage(packageId);
		return computationPackage.isValidated();
	}


}
