package computation.worker;

import java.util.Collection;
import java.util.Date;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.Query;

import computation.model.ComputationNodeHistory;
import computation.worker.NodeHistoryWorker.NodeHistoryWorkerLocal;
import computation.worker.NodeHistoryWorker.NodeHistoryWorkerRemote;

import core.workers.AbstractWorkerBean;

/**
 * Worker implementation. 
 * 
 * @author malczyk.pawel@gmail.com
 *
 */
@Local(NodeHistoryWorkerLocal.class)
@Remote(NodeHistoryWorkerRemote.class)
@Stateless
public class NodeHistoryWorkerBean extends AbstractWorkerBean implements NodeHistoryWorker {

	/**
	 * Maximum historical entries.
	 */
	private static final int MAX_HISTORY_RESULTS = 1000;

	/**
	 * Constructor.
	 */
	public NodeHistoryWorkerBean() {
		super();
	}

	/** {@inheritDoc} */
	@Override
	public Collection<ComputationNodeHistory> retrieveHistory(String nodeIp) {
		Query query = getEntityManager().createNamedQuery(ComputationNodeHistory.BY_IP);
		query.setParameter("nodeIp", nodeIp);
		query.setMaxResults(MAX_HISTORY_RESULTS);
		return getResultList(query, ComputationNodeHistory.class);
	}

	/** {@inheritDoc} */
	@Override
	public void storeHistoryItem(ComputationNodeHistory computationNodeHistory) {
		computationNodeHistory.setRecordDate(new Date());
		save(computationNodeHistory);
	}
	
}
