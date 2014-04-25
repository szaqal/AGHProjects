package computation.worker;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import auth.UsersWorker.UsersWorkerLocal;
import auth.model.User;

import com.thoughtworks.xstream.XStream;
import computation.model.ComputationResult;
import computation.worker.ComputationResultWorker.ComputationResultWorkerLocal;
import computation.worker.ComputationResultWorker.ComputationResultWorkerRemote;

import core.utils.ListUtils;
import core.utils.PagingInfo;
import core.utils.StringUtils;
import core.workers.AbstractWorkerBean;

/**
 * Worker implementation.
 * @author  malczyk.pawel@gmail.com
 */
@Stateless
@Local(ComputationResultWorkerLocal.class)
@Remote(ComputationResultWorkerRemote.class)
public class ComputationResultWorkerBean extends AbstractWorkerBean implements ComputationResultWorker {
	
	
	/**
	 * Logger.
	 */
	private static final Logger LOG = LoggerFactory.getLogger(ComputationResultWorkerBean.class);
	
	/**
	 * User id search parameter.
	 */
	private static final String USER_ID_PARAM = "userId";

	/**
	 * Users worker.
	 * @uml.property  name="usersWorker"
	 * @uml.associationEnd  
	 */
	@EJB
	private UsersWorkerLocal usersWorker;
	
	/**
	 * Constructor.
	 */
	public ComputationResultWorkerBean() {
		super();
	}

	/** {@inheritDoc} */
	@Override
	public List<ComputationResult> retrieveResults() {
		return ListUtils.convertRawListToGenericList(ComputationResult.class, 
				getEntityManager().createNamedQuery(ComputationResult.EXISTING).getResultList());
		
	}

	/** {@inheritDoc} */
	@Override
	public ComputationResult saveResult(ComputationResult computationResult, int userId) {
		User user = usersWorker.find(userId);
		computationResult.setOwner(user);
		return save(computationResult);
	}

	/** {@inheritDoc} */
	@Override
	public ComputationResult retrieveComputationResult(int uniqueId) {
		return find(uniqueId, ComputationResult.class);
	}

	/** {@inheritDoc} */
	@SuppressWarnings("unchecked")
	@Override
	public byte[] retrieveAsXml(int resultId) {
		ComputationResult computationResult = find(resultId, ComputationResult.class);
		byte[] resultData = computationResult.getResult();
		String result = StringUtils.EMPTY;
		try {
			ByteArrayInputStream inStream = new ByteArrayInputStream(resultData);
			ObjectInputStream objectInputStream = new ObjectInputStream(inStream);
			Map<String, Serializable> compResult = (Map<String, Serializable>) objectInputStream.readObject();
			XStream xStream = new XStream();
			xStream.setMode(XStream.NO_REFERENCES);
			result  = xStream.toXML(compResult);
		} catch (IOException e) {
			LOG.warn(e.getMessage());
		} catch (ClassNotFoundException e) {
			LOG.warn(e.getMessage());
		}
		
		return result.getBytes();
	}

	/** {@inheritDoc} */
	@Override
	public List<ComputationResult> retrieveResults(PagingInfo pagingInfo, int ownerId) {
		
		Query query = getEntityManager().createNamedQuery(ComputationResult.EXISTING_BY_USR);
		
		if(pagingInfo != null) {
			if(pagingInfo.isSortingEnabled()) {
				String qry = String.format("SELECT entity FROM ComputationResult as entity WHERE entity.owner.uniqueId=:userId " +
						"ORDER BY entity.%s %s", pagingInfo.getSortBy(), pagingInfo.getSortType());
				query = getEntityManager().createQuery(qry);
			}
			query.setParameter(USER_ID_PARAM, ownerId);
			query.setFirstResult(pagingInfo.getFirstResult());
			query.setMaxResults(pagingInfo.getRowsPerPage());
		}
		
		return getResultList(query, ComputationResult.class);
	}

	/** {@inheritDoc} */
	@Override
	public Long resultsCount(int ownerId) {
		Query qry = getEntityManager().createNamedQuery(ComputationResult.EXISTING_BY_USR_COUNT);
		qry.setParameter(USER_ID_PARAM, ownerId);
		return getSingleResult(qry, Long.class);
	}
	
}
