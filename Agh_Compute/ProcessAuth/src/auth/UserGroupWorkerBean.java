package auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.Query;

import org.apache.commons.collections.ListUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import auth.GroupsWorker.GroupsWorkerLocal;
import auth.UserGroupWorker.UserGroupWorkerLocal;
import auth.UserGroupWorker.UserGroupWorkerRemote;
import auth.model.Group;
import auth.model.User;
import auth.model.UserGroup;
import core.workers.AbstractWorkerBean;

/**
 * Worker implementation.
 * @author  malczyk.pawel@gmail.com
 */
@Stateless
@Local(UserGroupWorkerLocal.class)
@Remote(UserGroupWorkerRemote.class)
public class UserGroupWorkerBean extends AbstractWorkerBean implements UserGroupWorker {
	
	
	/**
	 * Logger.
	 */
	private static final Logger LOG = LoggerFactory.getLogger(UsersWorkerBean.class);
	
	/**
	 * Worker.
	 * @uml.property  name="groupWorker"
	 * @uml.associationEnd  
	 */
	@EJB
	private GroupsWorkerLocal groupWorker;
	
	/**
	 * Constructor.
	 */
	public UserGroupWorkerBean() {
		super();
	}

	/** {@inheritDoc} */
	@Override
	public void addUserToGroup(int groupId, User user) {
		LOG.debug("Adding user to group");
		Group group = groupWorker.retrieveGroup(groupId);
		UserGroup userGroup = new UserGroup();
		userGroup.setGrp(group);
		userGroup.setUsr(user);
		save(userGroup);
	}

	/** {@inheritDoc} */
	@Override
	public void removeUserFromGroup(int groupId, User user) {
		LOG.debug("Remving user to group");
		Collection<UserGroup> usrGroups = retrieveByUserAndGroup(groupId, user.getUniqueId());
		for(UserGroup grp : usrGroups) {
			getEntityManager().remove(grp);
		}
	}

	/** {@inheritDoc} */
	@Override
	public Collection<Group> retrieveByUser(int userId) {
		Query query = getEntityManager().createNamedQuery(UserGroup.BY_USER);
		query.setParameter(1, userId);
		Collection<UserGroup> userGroups = getResultList(query, UserGroup.class);
		Collection<Group> result = new ArrayList<Group>();
		for(UserGroup userGroup : userGroups) {
			result.add(userGroup.getGrp());
		}
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public Collection<User> retriveByGroup(int groupId) {
		Query query = getEntityManager().createNamedQuery(UserGroup.BY_GROUP);
		query.setParameter(1, groupId);
		Collection<UserGroup> userGroups = getResultList(query, UserGroup.class);
		Collection<User> result = new ArrayList<User>();
		for(UserGroup userGroup : userGroups) {
			result.add(userGroup.getUsr());
		}
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public Collection<UserGroup> retrieveByUserAndGroup(int groupId,
			int userId) {
		Query query = getEntityManager().createNamedQuery(UserGroup.BY_GRP_USR);
		query.setParameter(1, groupId);
		query.setParameter(2, userId);
		Collection<UserGroup> userGroups = getResultList(query, UserGroup.class);
		LOG.debug("Found {} groups", userGroups.size());
		return userGroups;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Group> retrieveAvailable(int userId) {
		List<Group> userGroups = (List<Group>) retrieveByUser(userId);
		List<Group> allGroups = (List<Group>) groupWorker.retrieveGroups(null);
		return ListUtils.subtract(allGroups, userGroups);
	}


}
