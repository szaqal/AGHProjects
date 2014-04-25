package computation.worker;

import java.util.List;

import computation.model.ValidationSchema;

import core.utils.PagingInfo;

/**
 * Worker for managing schemas.
 * 
 * @author malczyk.pawel@gmail.com
 *
 */
public interface ValidationWorker {
	
	/**
	 * Stores single validation schema.
	 * @param title title
	 * @param data file data.
	 * @param fileName file name
	 * @return schema id
	 */
	ValidationSchema store(String title, String fileName, byte[] data);
	
	/**
	 * Retrieves validation schema by it's pk.
	 * @param uniqueId primary key.
	 * @return {@link ValidationSchema}
	 */
	ValidationSchema retrieveSchema(int uniqueId);
	
	/**
	 * List of uploaded validation schemas.
	 * @param pagingInfo {@link PagingInfo}
	 * @return list of validation schemas.
	 */
	List<ValidationSchema> retrieveSchemas(PagingInfo pagingInfo);
	
	/**
	 * Validates configuration.
	 * @param configurationId configuration id.
	 * @param schemaId validation schema.
	 * @return list of errors.
	 */
	boolean validateConfigutaion(int configurationId, int schemaId);
	
	/**
	 * Validates package.
	 * @param packageId computation packageId.
	 * @param schemaId schem.
	 * @return list of errors
	 */
	boolean validatePackage(int packageId, int schemaId);
	
	
	/**
	 * Validates xml file against specified schema.
	 * @param xmlData raw data of xml file.
	 * @param xsdData raw data of xsd file.
	 * @return true if xml document is valid.
	 */
	boolean validate(byte [] xmlData, byte [] xsdData);
	
	
	/**
	 * Local interface.
	 * 
	 * @author malczyk.pawel@gmail.com
	 *
	 */
	public interface ValidationWorkerLocal extends ValidationWorker { }
	
	/**
	 * Remote interface.
	 * @author malczyk.pawel@gmail.com
	 *
	 */
	public interface ValidationWorkerRemote extends ValidationWorker { }
	
}
