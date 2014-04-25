package computation.worker;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.Query;

import org.apache.commons.digester.Digester;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import computation.model.ComputationConfiguration;
import computation.utils.DigesterRulesBuilder;
import computation.worker.ComputationConfgurationWorker.ComputationConfgurationWorkerLocal;
import computation.worker.ComputationConfgurationWorker.ComputationConfgurationWorkerRemote;

import core.model.FileContent;
import core.utils.PagingInfo;
import core.workers.AbstractWorkerBean;

/**
 * Worker implementation.
 * 
 * @author malczyk.pawel@gmail.com
 *
 */
@Stateless
@Local(ComputationConfgurationWorkerLocal.class)
@Remote(ComputationConfgurationWorkerRemote.class)
public class ComputationConfigurationWorkerBean extends AbstractWorkerBean implements ComputationConfgurationWorker {
	
	/**
	 * Logger.
	 */
	private static final Logger LOG = LoggerFactory.getLogger(ComputationConfigurationWorkerBean.class);
	
	
	/**
	 * Constructor.
	 */
	public ComputationConfigurationWorkerBean() {
		super();
	}

	/** {@inheritDoc} */
	@Override
	public List<ComputationConfiguration> retrieveComputationConfigurations() {
		return retrieveComputationConfigurations(null);
	}

	/** {@inheritDoc} */
	@Override
	public List<ComputationConfiguration> retrieveComputationConfigurations(
			PagingInfo pagingInfo) {
		
		
		Query query = getEntityManager().createNamedQuery(ComputationConfiguration.CONFIGURATIONS);
		
		if(pagingInfo != null) {
			if(pagingInfo.isSortingEnabled()) {
				String qry = String.format("SELECT cc FROM ComputationConfiguration AS cc ORDER BY cc.%s %s", pagingInfo.getSortBy(), pagingInfo.getSortType());
				query = getEntityManager().createQuery(qry);
			}
			query.setFirstResult(pagingInfo.getFirstResult());
			query.setMaxResults(pagingInfo.getRowsPerPage());
		}
		
		return getResultList(query, ComputationConfiguration.class);
	}

	/** {@inheritDoc} */
	@Override
	public Long retrieveComputationConfigurationsCount() {
		Query qry = getEntityManager().createNamedQuery(ComputationConfiguration.CONFIGURATIONS_COUNT);
		return getSingleResult(qry, Long.class);
	}

	/** {@inheritDoc} */
	@Override
	public ComputationConfiguration store(byte[] data, String fileName) {
		FileContent fileContent = new FileContent(data);
		fileContent.setFileName(fileName);
		fileContent.setMimeType("text/xml");
		ComputationConfiguration config = null;
		try {
			InputStream xmlInputStream = new ByteArrayInputStream(data);
			Digester digester = DigesterRulesBuilder.createConfigurationImport();
			config = (ComputationConfiguration) digester.parse(xmlInputStream);
			xmlInputStream.close();
		} catch (IOException e) {
			LOG.warn("IO Exception while loading configuration");
		} catch (SAXException e) {
			LOG.warn("SAX Exception while parsing configuration file", e);
		}
		
		config.setConfigurationFile(fileContent);
		return save(config);
	}

	/** {@inheritDoc} */
	@Override
	public ComputationConfiguration retrieveById(int uniqueId) {
		return find(uniqueId, ComputationConfiguration.class);
	}

	/** {@inheritDoc} */
	@Override
	public boolean isConfigurationValidated(int uniqueId) {
		ComputationConfiguration computationConfig = retrieveById(uniqueId);
		return computationConfig.isValidated();
	}

}
