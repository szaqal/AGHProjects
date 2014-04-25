package core.workers;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.InputSource;

import core.model.FileContent;
import core.model.XslTransform;
import core.utils.PagingInfo;

/**
 * Worker implementation.
 * 
 * @author malczyk.pawel@gmail.com
 *
 */
@Stateless
@Local(TransformWorker.TransformWorkerLocal.class)
@Remote(TransformWorker.TransformWorkerRemote.class)
public class TransformWorkerBean extends AbstractWorkerBean implements TransformWorker {
	
	/**
	 * Logger.
	 */
	private static final Logger LOG = LoggerFactory.getLogger(TransformWorkerBean.class);
	
	/**
	 * Constructor.
	 */
	public TransformWorkerBean() {
		super();
	}

	/** {@inheritDoc} */
	@Override
	public XslTransform retrieveById(int uniqueId) {
		return find(uniqueId, XslTransform.class);
	}

	/** {@inheritDoc} */
	@Override
	public List<XslTransform> retrieveTransforms(PagingInfo pagingInfo) {
	
		Query query = getEntityManager().createNamedQuery(XslTransform.LIVE);
		
		if(pagingInfo != null) {
			if(pagingInfo.isSortingEnabled()) {
				String qry = String.format("SELECT xslt FROM XslTransform AS xslt ORDER BY xslt.%s %s", pagingInfo.getSortBy(), pagingInfo.getSortType());
				query = getEntityManager().createQuery(qry);
			}
			query.setFirstResult(pagingInfo.getFirstResult());
			query.setMaxResults(pagingInfo.getRowsPerPage());
		}
		
		return getResultList(query, XslTransform.class);
	}

	/** {@inheritDoc} */
	@Override
	public XslTransform store(XslTransform xslTransform) {
		return save(xslTransform);
	}
	
	/** {@inheritDoc} */
	@Override
	public Long retrieveTransformCount() {
		Query qry = getEntityManager().createNamedQuery(XslTransform.LIVE_COUNT);
		return getSingleResult(qry, Long.class);
	}

	/** {@inheritDoc} */
	@Override
	public XslTransform store(String title, String fileName, byte[] data, String restulMime, String extension) {
		
		FileContent content = new FileContent();
		content.setContent(data);
		content.setFileName(fileName);
		content.setMimeType("text/xsl");
		save(content);
		
		XslTransform transform = new XslTransform();
		transform.setTitle(title);
		transform.setFileContent(content);
		transform.setResultMime(restulMime);
		transform.setResultExtension(extension);
		return save(transform);
	}

	/** {@inheritDoc} */
	@Override
	public byte[] doTransformation(byte[] inputData, int xslTransformId) {
		TransformerFactory factory = TransformerFactory.newInstance();
		byte [] result = new byte[0];
		byte [] transformData = retrieveById(xslTransformId).getFileContent().getContent();
		try {
			Transformer transformer = factory.newTransformer(new SAXSource(new InputSource(new ByteArrayInputStream(transformData))));
			ByteArrayOutputStream resultOutputStream = new ByteArrayOutputStream();
			transformer.transform(new SAXSource(new InputSource(new ByteArrayInputStream(inputData))), new StreamResult(resultOutputStream));
			result = resultOutputStream.toByteArray();
			
		} catch (TransformerConfigurationException e) {
			LOG.warn("TransformerConfigurationException caught", e);
		} catch (TransformerException e) {
			LOG.warn("TransformerException caught", e);
		}
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public byte[] transformContent(int transformId) {
		return retrieveById(transformId).getFileContent().getContent();
	}
}
