package core.workers;

import core.model.ApplicationConfigurationFile;

/**
 * Manages application wide confiuration files.
 * @author malczyk.pawel@gmail.com
 *
 */
public interface ApplicationConfigurationFilesWorker {
	
	/**
	 * Stores computation validation.
	 * @param data file content
	 * @param fileName TODO
	 * @return {@link ApplicationConfigurationFile}
	 */
	ApplicationConfigurationFile storeComputationStartValidation(byte [] data, String fileName);
	
	/**
	 * Retrieves start validation file.
	 * @return {@link ApplicationConfigurationFile}
	 */
	ApplicationConfigurationFile retrieveStartValidation();
	
	/**
	 * Local interface.
	 * @author malczyk.pawel@gmail.com
	 *
	 */
	public interface ApplicationConfigurationFilesWorkerLocal extends ApplicationConfigurationFilesWorker { }
	
	/**
	 * Remote interface.
	 * @author malczyk.pawel@gmail.com
	 *
	 */
	public interface ApplicationConfigurationFilesWorkerRemote extends ApplicationConfigurationFilesWorker { }
}
