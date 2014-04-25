package auth;

import java.util.Collection;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.Query;

import auth.GroupsWorker.GroupsWorkerLocal;
import auth.GroupsWorker.GroupsWorkerRemote;
import auth.model.Group;
import core.utils.PagingInfo;
import core.workers.AbstractWorkerBean;


/**
 * Implementation of worker.
 * @author <a href="mailto:malczyk.pawel@gmail.com">malczyk.pawel@gmail.com</a>
 *
 */
@Stateless
@Local(GroupsWorkerLocal.class)
@Remote(GroupsWorkerRemote.class)
public class GroupsWorkerBean  extends AbstractWorkerBean implements GroupsWorker {
	
	/**
	 * Constructor.
	 */
	public GroupsWorkerBean() { }

	/** {@inheritDoc} */
	@Override
	public Group retrieveGroup(int uniqueId) {
		return find(uniqueId, Group.class);
	}

	/** {@inheritDoc} */
	@Override
	public Collection<Group> retrieveGroups(PagingInfo pagingInfo) {
		Query query = null;
		
		query = getEntityManager().createNamedQuery(Group.EXISTING);
		if(pagingInfo != null) {
			if(pagingInfo.getSortBy() != null && pagingInfo.getSortType() != null) {
				String qry = String.format("SELECT grp FROM Group AS grp ORDER BY grp.%s %s", pagingInfo.getSortBy(), pagingInfo.getSortType());
				query = getEntityManager().createQuery(qry);
			}
			query.setFirstResult(pagingInfo.getFirstResult());
			query.setMaxResults(pagingInfo.getRowsPerPage());
		}
		
		return getResultList(query, Group.class);
	}

	/** {@inheritDoc} */
	@Override
	public Group saveGroup(Group group) {
		return save(group);
	}

	/** {@inheritDoc} */
	@Override
	public Long retrieveGroupsCount() {
		Query qry = getEntityManager().createNamedQuery(Group.EXISTING_COUNT);
		return getSingleResult(qry, Long.class);
	}

	/** {@inheritDoc} */
	@Override
	public void deleteGroup(int groupId) {
		Group group = find(groupId, Group.class);
		remove(group);
	}

}
