package computation.worker;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.Query;

import computation.model.ComputationLogEntry;
import computation.worker.ComputationLogEntryWorker.ComputationLogEntryWorkerLocal;
import computation.worker.ComputationLogEntryWorker.ComputationLogEntryWorkerRemote;

import core.utils.PagingInfo;
import core.workers.AbstractWorkerBean;

/**
 * Worker implementation. 
 * @author malczyk.pawel@gmail.com
 *
 */
@Stateless
@Local(ComputationLogEntryWorkerLocal.class)
@Remote(ComputationLogEntryWorkerRemote.class)
public class ComputationLogEntryWorkerBean extends AbstractWorkerBean implements ComputationLogEntryWorker {
	
	
	/**
	 * Constant.
	 */
	private static final String PERFORMED_COMPUTATION_ID = "performedComputationId";

	/**
	 * Constructor.
	 */
	public ComputationLogEntryWorkerBean() {
		super();
	}

	/** {@inheritDoc} */
	@Override
	public List<ComputationLogEntry> retrieveLogs(
			int performedComputationId, PagingInfo pagingInfo) {
		
		Query query = getEntityManager().createNamedQuery(ComputationLogEntry.BY_PERFORMED_COMPUTATION);
		
		if(pagingInfo != null) {
			if(pagingInfo.isSortingEnabled()) {
				String qry = String.format("SELECT cl FROM ComputationLogEntry AS cl WHERE " +
						"cl.performedComputation.uniqueId=:performedComputationId ORDER BY cl.%s %s", pagingInfo.getSortBy(), pagingInfo.getSortType());
				query = getEntityManager().createQuery(qry);
			}
			query.setFirstResult(pagingInfo.getFirstResult());
			query.setMaxResults(pagingInfo.getRowsPerPage());
		}
		
		query.setParameter(PERFORMED_COMPUTATION_ID, performedComputationId);
		return getResultList(query, ComputationLogEntry.class);
	}


	/** {@inheritDoc} */
	@Override
	public ComputationLogEntry store(ComputationLogEntry computationLogEntry) {
		return save(computationLogEntry);
	}

	/** {@inheritDoc} */
	@Override
	public Long retrieveLogsCount(int performedComputationId) {
		Query qry = getEntityManager().createNamedQuery(ComputationLogEntry.BY_PERFORMED_COMPUTATION_COUNT);
		qry.setParameter(PERFORMED_COMPUTATION_ID, performedComputationId);
		return getSingleResult(qry, Long.class);
	}

}
