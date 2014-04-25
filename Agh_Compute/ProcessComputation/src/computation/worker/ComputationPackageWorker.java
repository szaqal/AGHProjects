package computation.worker;

import java.util.List;

import computation.model.ComputationPackage;


/**
 * Defines operations on computation packages. 
 * @author <a href="mailto:malczyk.pawel@gmail.com">malczyk.pawel@gmail.com</a>
 *
 */
public interface ComputationPackageWorker {
	
	
	/**
	 * Saves uploaded computation package.
	 * @param computationPackage computation package to save
	 * @param fileName packageFileName
	 * @param mime mime type
	 * @return return pk of stored computation package.
	 */
	ComputationPackage saveComputationPackage(ComputationPackage computationPackage, String fileName, String mime);
	
	/**
	 * Saves uploaded file as a computation package if file is valid computation package file.
	 * @param computationPackage file containing computation package.
	 * @param title human readable title for package
	 * @param fileName packageFileName
	 * @param mime mime type
	 * @return returns pk for stored computation package
	 */
	ComputationPackage saveComputationPackage(byte [] computationPackage, String title, String fileName, String mime);


	/**
	 * Retrieves list of uploaded computational packages.
	 * @return list of computational packages.
	 */
	List<ComputationPackage> retrieveComputationPackages();
	
	/**
	 * Retrieves particular computation package based on it's id.
	 * @param packageId id of package to retrieve/
	 * @return {@link ComputationPackage}
	 */
	ComputationPackage retrieveComputationPackage(int packageId);
	
	
	/**
	 * Performs validation on computation packages.
	 * Only valid ones are allowed to upload.
	 * @param computationPackage computation package being checked
	 * @return true/false;
	 */
	boolean isPackageValid(ComputationPackage computationPackage);
	
	/**
	 * Checks whether computation package is already validated.
	 * @param packageId package id.
	 * @return true/false
	 */
	boolean isPackageValidated(int packageId);
	
	
	/**
	 * Performs validation on computation packages.
	 * Only valid ones are allowed to upload.
	 * @param computationPackageId identifier of computation package being checked
	 * @return true/false;
	 */
	boolean isPackageValid(int computationPackageId);
	
	/**
	 * Retrieves content from package configuration file.
	 * @param computationPackageId package id
	 * @return file content
	 */
	byte [] retrieveConfigFromPackage(int computationPackageId);

	
	/**
	 * Local interface.
	 * @author <a href="mailto:malczyk.pawel@gmail.com">malczyk.pawel@gmail.com</a>
	 *
	 */
	public interface ComputationPackageWorkerLocal extends ComputationPackageWorker { }
	
	
	/**
	 * Remote interface.
	 * @author <a href="mailto:malczyk.pawel@gmail.com">malczyk.pawel@gmail.com</a>
	 *
	 */
	public interface ComputationPackageWorkerRemote extends ComputationPackageWorker { }

}
