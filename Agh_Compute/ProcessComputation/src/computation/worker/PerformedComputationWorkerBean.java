package computation.worker;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.Query;

import computation.model.Computation;
import computation.model.PerformedComputation;
import computation.worker.PerformedComputationWorker.PerformedComputationWorkerLocal;
import computation.worker.PerformedComputationWorker.PerformedComputationWorkerRemote;

import core.utils.PagingInfo;
import core.workers.AbstractWorkerBean;

/**
 * Worker implementation.
 * @author malczyk.pawel@gmail.com
 *
 */
@Stateless
@Local(PerformedComputationWorkerLocal.class)
@Remote(PerformedComputationWorkerRemote.class)
public class PerformedComputationWorkerBean extends AbstractWorkerBean implements PerformedComputationWorker {
	
	
	/**
	 * Constructor.
	 */
	public PerformedComputationWorkerBean() {
		super();
	}

	/** {@inheritDoc} */
	@Override
	public PerformedComputation retrievePerformedComputation(int uniqueId) {
		return find(uniqueId, PerformedComputation.class);
	}

	/** {@inheritDoc} */
	@Override
	public PerformedComputation savePerformedComputation(PerformedComputation performedComputation) {
		return save(performedComputation);
	}
	
	/** {@inheritDoc} */
	@Override
	public Long retrievePerformedComputationCount() {
		Query qry = getEntityManager().createNamedQuery(Computation.EXISTING_COUNT);
		return getSingleResult(qry, Long.class);
	}

	/** {@inheritDoc} */
	@Override
	public List<PerformedComputation> retrievePerformedComputations(
			PagingInfo pagingInfo) {
		
		Query query = getEntityManager().createNamedQuery(PerformedComputation.EXISTING);
		
		if(pagingInfo != null) {
			if(pagingInfo.isSortingEnabled()) {
				String qry = String.format("SELECT cmp FROM PerformedComputation cmp ORDER BY cmp.%s %s", pagingInfo.getSortBy(), pagingInfo.getSortType());
				query = getEntityManager().createQuery(qry);
			}
			query.setFirstResult(pagingInfo.getFirstResult());
			query.setMaxResults(pagingInfo.getRowsPerPage());
		}
		return getResultList(query, PerformedComputation.class);
	}

}
