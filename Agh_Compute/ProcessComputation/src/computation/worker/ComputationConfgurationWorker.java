package computation.worker;

import java.util.List;

import computation.model.ComputationConfiguration;

import core.utils.PagingInfo;

/**
 * Worker operating on computation configurations .
 * 
 * @see ComputationConfiguration
 * 
 * @author malczyk.pawel@gmail.com
 *
 */
public interface ComputationConfgurationWorker {
	
	/**
	 * Stores computation configuration.
	 * @param data file content.
	 * @param fileName configuration file name;
	 * @return stored indentifier.
	 */
	ComputationConfiguration store(byte[] data, String fileName);
	
	/**
	 * Retrieves list of computation configurations.
	 * @return list of {@link ComputationConfiguration}
	 */
	List<ComputationConfiguration> retrieveComputationConfigurations();
	
	/**
	 * Retrieves computations configurations.
	 * @param pagingInfo paging info used.
	 * @return list of {@link ComputationConfiguration}
	 */
	List<ComputationConfiguration> retrieveComputationConfigurations(PagingInfo pagingInfo);
	
	/**
	 * Returns computation configurations count.
	 * @return item count.
	 */
	Long retrieveComputationConfigurationsCount();
	
	/**
	 * Returns configuration by Id. 
	 * @param uniqueId configuration key.
	 * @return {@link ComputationConfiguration} found.
	 */
	ComputationConfiguration retrieveById(int uniqueId);
	
	/**
	 * Configuration validated.
	 * @param uniqueId configuration key.
	 * @return true/false
	 */
	boolean isConfigurationValidated(int uniqueId);
	
	/**
	 * Local worker.
	 * 
	 * @author malczyk.pawel@gmail.com
	 *
	 */
	public interface ComputationConfgurationWorkerLocal extends ComputationConfgurationWorker { }
	
	/**
	 * Remote worker.
	 * 
	 * @author malczyk.pawel@gmail.com
	 *
	 */
	public interface ComputationConfgurationWorkerRemote extends ComputationConfgurationWorker { }
	
}
