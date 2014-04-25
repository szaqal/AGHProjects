package ewpp.business.workers;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ewpp.auth.entity.User;
import ewpp.auth.entity.UserType;
import ewpp.business.workers.UsersWorker.UsersWorkerLocal;
import ewpp.business.workers.UsersWorker.UsersWorkerRemote;
import ewpp.utils.PagingInfo;
import ewpp.utils.SearchParameters;
import ewpp.workers.AbstractWorkerBean;

/**
 * Implementacja workera uzytkownikow.
 * @author malczyk.pawel@gmail.com
 *
 */
@Stateless
@Remote(UsersWorkerRemote.class)
@Local(UsersWorkerLocal.class)
public class UsersWorkerBean extends AbstractWorkerBean implements UsersWorker {

	/** Stala. */
	private static final String USR_TYPE = "usrType";
	
	/** Logger. */
	private static final Logger LOG = LoggerFactory.getLogger(UsersWorkerBean.class);

	/** Konstruktor. */
	public UsersWorkerBean() { };

	/** {@inheritDoc} */
	@Override
	public List<User> retrieveUsers(PagingInfo pagingInfo) {
		LOG.debug("Pobieranie uzytkownikow");
		Query qry = getEntityManager().createQuery("SELECT user FROM User AS user where user.deleted=false");
		
		if(pagingInfo != null) {
			qry.setFirstResult(pagingInfo.getFirstResult());
			qry.setMaxResults(pagingInfo.getRowsPerPage());
		}
		
		return getResultList(qry, User.class);
	}

	/** {@inheritDoc} */
	@Override
	public User retrieveUser(int userId) {
		return getEntityManager().find(User.class, userId);
	}

	/** {@inheritDoc} */
	@Override
	public void saveUser(User user) {
		LOG.debug("Saving user");
		getEntityManager().merge(user);
	}

	/** {@inheritDoc} */
	@Override
	public void removeUser(int userId) {
		LOG.debug("Removing user");
		User user = retrieveUser(userId);
		user.setDeleted(true);
		saveEntity(user);
	}

	/** {@inheritDoc} */
	@Override
	public List<User> retrieveLecturers() {
		LOG.debug("Retrieveing lecturers");
		return retrieveUserByType(UserType.LECTURER, null);
	}

	/** {@inheritDoc} */
	@Override
	public List<User> retrieveStudents(PagingInfo pagingInfo) {
		LOG.debug("Retrieveing students");
		return retrieveUserByType(UserType.STUDENT, pagingInfo);
	}
	


	/**
	 * Zwraca uzytkownika po typie.
	 * @param userType typ uzytkownika
	 * @param pagingInfo info o stronicowaniu
	 * @return lista uzytkownikow
	 */
	private List<User> retrieveUserByType(UserType userType, PagingInfo pagingInfo) {
		String qry="SELECT user FROM User AS user where user.userType=:usrType and user.deleted = false";
		Query query = getEntityManager().createQuery(qry);
		query.setParameter(USR_TYPE, userType);
		
		
		if(pagingInfo != null) {
			query.setFirstResult(pagingInfo.getFirstResult());
			query.setMaxResults(pagingInfo.getRowsPerPage());
		}
		
		return getResultList(query, User.class);
	}

	/** {@inheritDoc} */
	@Override
	public Long retrieveUsersCount() {
		Query qry = getEntityManager().createQuery("SELECT COUNT(user) FROM User AS user where user.deleted=false");
		return getSingleResult(qry, Long.class);
	}

	/** {@inheritDoc} */
	@Override
	public List<User> retrieveUsers() {
		return retrieveUsers(null);
	}

	/** {@inheritDoc} */
	@Override
	public List<User> retrieveUsers(SearchParameters<User> searchParameters, PagingInfo pagingInfo) {
		LOG.debug("Retrieving users with search parameters");
		Query query = searchParameters.createQuery(getEntityManager());
		
		if(pagingInfo != null) {
			query.setFirstResult(pagingInfo.getFirstResult());
			query.setMaxResults(pagingInfo.getRowsPerPage());
		}
		
		return getResultList(query, User.class);
	}

	/** {@inheritDoc} */
	@Override
	public Long retrieveUsersCount(SearchParameters<User> searchParams) {
		LOG.debug("Counting users with search parameters");
		Query query = searchParams.createCountingQuery(getEntityManager());
		return getSingleResult(query, Long.class);
	}

	/** {@inheritDoc} */
	@Override
	public Long retrieveStudentsCount() {
		return retrieveUserCountByType(UserType.STUDENT);
	}
	
	/**
	 * Zwraca ilosc uzytkownikow po typie.
	 * @param userType typ uzytkownika
	 * @return ilosc uzytkownikow
	 */
	private Long retrieveUserCountByType(UserType userType) {
		Query qry = getEntityManager().createQuery("SELECT COUNT(user) FROM User AS user WHERE user.userType=:usrType");
		qry.setParameter(USR_TYPE, userType);
		return getSingleResult(qry, Long.class);
	}


	/** {@inheritDoc} */
	@Override
	public Long retrieveStudentsCount(SearchParameters<User> searchParam) {
		// TODO Auto-generated method stub
		return null;
	}



}
