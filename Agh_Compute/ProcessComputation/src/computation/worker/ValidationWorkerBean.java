package computation.worker;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.sax.SAXSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import computation.model.ComputationConfiguration;
import computation.model.ComputationPackage;
import computation.model.ValidationSchema;
import computation.worker.ComputationConfgurationWorker.ComputationConfgurationWorkerLocal;
import computation.worker.ComputationPackageWorker.ComputationPackageWorkerLocal;
import computation.worker.ValidationWorker.ValidationWorkerLocal;
import computation.worker.ValidationWorker.ValidationWorkerRemote;

import core.model.FileContent;
import core.utils.PagingInfo;
import core.workers.AbstractWorkerBean;

/**
 * Worker implementation.
 * @author  malczyk.pawel@gmail.com
 */
@Stateless
@Local(ValidationWorkerLocal.class)
@Remote(ValidationWorkerRemote.class)
public class ValidationWorkerBean extends AbstractWorkerBean implements ValidationWorker {
	
	/**
	 * Logger.
	 */
	private static final Logger LOG = LoggerFactory.getLogger(ValidationWorkerBean.class);
	
	/**
	 * Computation configuration worker.
	 * @uml.property  name="computationConfguration"
	 * @uml.associationEnd  
	 */
	@EJB
	private ComputationConfgurationWorkerLocal computationConfguration;
	
	/**
	 * Computation package worker.
	 * @uml.property  name="computationPackageWorker"
	 * @uml.associationEnd  
	 */
	@EJB
	private ComputationPackageWorkerLocal computationPackageWorker;
	
	/**
	 * Constructor.
	 */
	public ValidationWorkerBean() {
		super();
	}

	/** {@inheritDoc} */
	@Override
	public ValidationSchema retrieveSchema(int uniqueId) {
		return find(uniqueId, ValidationSchema.class);
	}

	/** {@inheritDoc} */
	@Override
	public List<ValidationSchema> retrieveSchemas(PagingInfo pagingInfo) {
		
		
		Query query = getEntityManager().createNamedQuery(ValidationSchema.EXISTING);
		
		if(pagingInfo != null) {
			if(pagingInfo.isSortingEnabled()) {
				String qry = String.format("SELECT vs FROM ValidationSchema AS vs ORDER BY vs.%s %s", pagingInfo.getSortBy(), pagingInfo.getSortType());
				query = getEntityManager().createQuery(qry);
			}
			query.setFirstResult(pagingInfo.getFirstResult());
			query.setMaxResults(pagingInfo.getRowsPerPage());
		}
		
		return getResultList(query, ValidationSchema.class);
	}

	/** {@inheritDoc} */
	@Override
	public ValidationSchema store(String title, String fileName, byte[] data) {
		FileContent fileContent = new FileContent(data);
		ValidationSchema validationSchema = new ValidationSchema();
		validationSchema.setTitle(title);
		validationSchema.setFileContent(fileContent);
		validationSchema.getFileContent().setFileName(fileName);
		validationSchema.getFileContent().setMimeType("application/xml");
		return save(validationSchema);
	}

	/** {@inheritDoc} */
	@Override
	public boolean validateConfigutaion(int configurationId, int schemaId) {
		ValidationSchema schema = retrieveSchema(schemaId);
		byte [] xsdData = schema.getFileContent().getContent();
		ComputationConfiguration computationConfiguration = computationConfguration.retrieveById(configurationId);
		byte [] xmlData = computationConfiguration.getConfigurationFile().getContent();
		boolean validated = validate(xmlData, xsdData);
		if(validated) {
			computationConfiguration.setValidated(true);
			save(computationConfiguration);
		}
		return validated;
	}

	/** {@inheritDoc} */
	@Override
	public boolean validatePackage(int packageId, int schemaId) {
		ValidationSchema schema = retrieveSchema(schemaId);
		byte [] xsdData = schema.getFileContent().getContent();
		byte [] xmlData = computationPackageWorker.retrieveConfigFromPackage(packageId);
		boolean validated = validate(xmlData, xsdData);
		if(validated) {
			ComputationPackage pack = computationPackageWorker.retrieveComputationPackage(packageId);
			pack.setValidated(true);
			save(pack);
		}
		
		
		return validated;
	}

	/**
	 * Validates xml file against specified schema.
	 * @param xmlData raw data of xml file.
	 * @param xsdData raw data of xsd file.
	 * @return true if xml document is valid.
	 */
	public boolean validate(byte [] xmlData, byte [] xsdData) {
		boolean result = false;
		try {
			SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			Schema schema = factory.newSchema(new SAXSource(new InputSource(new ByteArrayInputStream(xsdData))));
			Validator validator = schema.newValidator();
			Source source = new SAXSource(new InputSource(new ByteArrayInputStream(xmlData)));
			validator.validate(source);
			result = true;
		} catch (SAXException e) {
			LOG.warn("SAXException", e);
		} catch (IOException e) {
			LOG.warn("IOException", e);
		} 
		return result;
	}
	
	
	
}
