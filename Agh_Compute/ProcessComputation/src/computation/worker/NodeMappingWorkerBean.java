package computation.worker;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import computation.model.NodeMapping;
import computation.worker.NodeMappingWorker.NodeMappingWorkerLocal;
import computation.worker.NodeMappingWorker.NodeMappingWorkerRemote;

import core.utils.PagingInfo;
import core.workers.AbstractWorkerBean;

/**
 * Worker implementation.
 * 
 * @author malczyk.pawel@gmail.com
 *
 * @deprecated since there is dynamic node assignment it is not used.
 *
 */
@Deprecated
@Stateless
@Local(NodeMappingWorkerLocal.class)
@Remote(NodeMappingWorkerRemote.class)
public class NodeMappingWorkerBean extends AbstractWorkerBean implements NodeMappingWorker {
	
	/**
	 * Logger.
	 */
	private static final Logger LOG = LoggerFactory.getLogger(NodeMappingWorkerBean.class);
	
	/**
	 * Constant.
	 */
	private static final String OWNER_ID = "ownerId";

	/**
	 * Constructor.
	 */
	public NodeMappingWorkerBean() {
		super();
	}

	/** {@inheritDoc} */
	@Override
	public NodeMapping retrieveById(int id) {
		return find(id, NodeMapping.class);
	}

	/** {@inheritDoc} */
	@Override
	public NodeMapping retrieveByPerformer(String mapping, int ownerId) {
		
		try {
			Query query = getEntityManager().createNamedQuery(NodeMapping.BY_MAPPING);
			query.setParameter("mapping", mapping);
			query.setParameter(OWNER_ID, ownerId);
			query.setMaxResults(1);
			NodeMapping result = getSingleResult(query, NodeMapping.class);
			return result; 
			
		} catch (NoResultException e) {
			LOG.warn("No result found on retrieveByPerformer");
		}
		
		//There should be only one or none 
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public NodeMapping store(NodeMapping mapping) {
		return save(mapping);
	}

	/** {@inheritDoc} */
	@Override
	public List<NodeMapping> retrieveByOwner(int ownerId, PagingInfo paginginfo) {
		Query query = getEntityManager().createNamedQuery(NodeMapping.BY_OWNER);
		
		query.setParameter(OWNER_ID, ownerId);

		if (paginginfo != null) {
			query.setFirstResult(paginginfo.getFirstResult());
			query.setMaxResults(paginginfo.getRowsPerPage());
		}

		return getResultList(query, NodeMapping.class);
	}

	/** {@inheritDoc} */
	@Override
	public Long retrieveByOwnerCount(int ownerId) {
		Query qry = getEntityManager().createNamedQuery(NodeMapping.BY_OWNER_COUNT);
		qry.setParameter(OWNER_ID, ownerId);
		return getSingleResult(qry, Long.class);
	}

	/** {@inheritDoc} */
	@Override
	public void delete(int mappingId) {
		NodeMapping mapping = retrieveById(mappingId);
		getEntityManager().remove(mapping);
	}

}
