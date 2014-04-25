package core.workers;

import java.util.List;

import core.model.XslTransform;
import core.utils.PagingInfo;

/**
 * Transform worker.
 * 
 * @author malczyk.pawel@gmail.com
 *
 */
public interface TransformWorker {
	
	/**
	 * Stores transform.
	 * @param xslTransform xslt transform
	 * @return uniqueId
	 */
	XslTransform store(XslTransform xslTransform);
	
	/**
	 * Transforms input data with particular transformation.
	 * @param inputData input raw bytes.
	 * @param xslTransformId transform id.
	 * @return transformation
	 */
	byte [] doTransformation(byte [] inputData, int xslTransformId);
	
	/**
	 * Stores transform.
	 * @param title title
	 * @param fileName upload file name.
	 * @param data raw file content
	 * @param restulMime result mime type after transform
	 * @param extension result file extension
	 * @return uniqueid of saved object
	 */
	XslTransform store(String title, String fileName, byte[] data, String restulMime, String extension);
	
	/**
	 * Retrieves by id.
	 * @param uniqueId primary key value.
	 * @return {@link XslTransform}
	 */
	XslTransform retrieveById(int uniqueId);
	
	/**
	 * Retrieves XSLT transform file content by id.
	 * @param transformId transform id
	 * @return content.
	 */
	byte [] transformContent(int transformId);
	
	/**
	 * REturns transforms count.
	 * @return item count
	 */
	Long retrieveTransformCount();
	
	/**
	 * Returns list of {@link XslTransform}.
	 * @param pagingInfo paging info.
	 * @return list of Xsl transforms.
	 */
	List<XslTransform> retrieveTransforms(PagingInfo pagingInfo);
	
	/**
	 * Local interface.
	 * @author malczyk.pawel@gmail.com
	 *
	 */
	public interface TransformWorkerLocal extends TransformWorker {};
	
	/**
	 * Remote interface.
	 * @author malczyk.pawel@gmail.com
	 *
	 */
	public interface TransformWorkerRemote extends TransformWorker {};
	
}
