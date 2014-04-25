package core.workers;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.Query;

import core.model.ApplicationConfigurationFile;
import core.model.ConfigFiles;
import core.model.FileContent;

/**
 * Worker implementation.
 * @author malczyk.pawel@gmail.com
 *
 */
@Stateless
@Local(ApplicationConfigurationFilesWorker.ApplicationConfigurationFilesWorkerLocal.class)
@Remote(ApplicationConfigurationFilesWorker.ApplicationConfigurationFilesWorkerRemote.class)
public class ApplicationConfigurationFilesWorkerBean extends AbstractWorkerBean implements ApplicationConfigurationFilesWorker {
	
	/**
	 * Constructor.
	 */
	public ApplicationConfigurationFilesWorkerBean() {
		super();
	}

	/** {@inheritDoc} */
	@Override
	public ApplicationConfigurationFile storeComputationStartValidation(
			byte[] data, String fileName) {
		FileContent content = new FileContent();
		content.setFileName(fileName);
		content.setMimeType("application/xml");
		content.setContent(data);
		ApplicationConfigurationFile applicationConfigurationFile = new ApplicationConfigurationFile();
		applicationConfigurationFile.setFileContent(content);
		applicationConfigurationFile.setKey(ConfigFiles.COMPUTATION_START_VALIDATION);
		ApplicationConfigurationFile save = save(applicationConfigurationFile);
		return save;
	}

	/** {@inheritDoc} */
	@Override
	public ApplicationConfigurationFile retrieveStartValidation() {
		Query query = getEntityManager().createNamedQuery(ApplicationConfigurationFile.BY_KEY);
		query.setParameter("acfkey", ConfigFiles.COMPUTATION_START_VALIDATION);
		return getSingleResult(query, ApplicationConfigurationFile.class);
	}

}

