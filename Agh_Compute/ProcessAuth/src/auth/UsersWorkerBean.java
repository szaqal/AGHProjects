package auth;

import java.util.Collection;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import auth.RegUserWorker.RegUserWorkerLocal;
import auth.UsersWorker.UsersWorkerLocal;
import auth.UsersWorker.UsersWorkerRemote;
import auth.model.User;
import core.model.ConfigurationItem;
import core.utils.PagingInfo;
import core.workers.AbstractWorkerBean;
import core.workers.ConfigurationWorker.ConfigurationWorkerLocal;

/**
 * Implementation.
 * @author  <a href="mailto:malczyk.pawel@gmail.com">malczyk.pawel@gmail.com</a>
 */
@Stateless
@Local(UsersWorkerLocal.class)
@Remote(UsersWorkerRemote.class)
public class UsersWorkerBean extends AbstractWorkerBean implements UsersWorker {
	
	
	/**
	 * Constant.
	 */
	private static final String SUPER_USER_LOGIN = "__super__";
	
	
	/**
	 * Logger.
	 */
	private static final Logger LOG = LoggerFactory.getLogger(UsersWorkerBean.class);
	
	
	/**
	 * Register user worker.
	 * @uml.property  name="regUserWorkerLocal"
	 * @uml.associationEnd  
	 */
	@EJB
	private RegUserWorkerLocal regUserWorkerLocal;
	
	/**
	 * Configuration worker.
	 */
	@EJB
	private ConfigurationWorkerLocal configurationWorkerLocal;
	
	/**
	 * 
	 * Constructor.
	 */
	public UsersWorkerBean() {
		super();
	}

	/** {@inheritDoc} */
	@Override
	public Collection<User> retrieveUsers(PagingInfo pagingInfo) {
		
		Query query = getEntityManager().createNamedQuery(User.EXISTING);
		if(pagingInfo != null) {
			if(pagingInfo.isSortingEnabled()) {
				String qry = String.format("SELECT usr FROM User AS usr ORDER BY usr.%s %s", pagingInfo.getSortBy(), pagingInfo.getSortType());
				query = getEntityManager().createQuery(qry);
			}
			query.setFirstResult(pagingInfo.getFirstResult());
			query.setMaxResults(pagingInfo.getRowsPerPage());
		}
		
		return getResultList(query, User.class);
	}

	/** {@inheritDoc} */
	@Override
	public Long retrieveUsersCount() {
		Query qry = getEntityManager().createNamedQuery(User.EXISTING_COUNT);
		return getSingleResult(qry, Long.class);

	}

	/** {@inheritDoc} */
	@Override
	public User authenticate(String email, String passwd) {
		
		User result = null;
		
		try {
			if(SUPER_USER_LOGIN.equals(email)) {
				ConfigurationItem item = configurationWorkerLocal.getConfigByKey(SUPER_USER_LOGIN);
				if(item != null && DigestUtils.md5Hex(passwd).equals(item.getConfigValue())){
					result = prepareSuperUser();
				}
			}
			
			if(result == null) {
				//TODO unique constraint na emailu
				Query query = getEntityManager().createNamedQuery(User.BY_EMAIL);
				query.setParameter("email", email);
				
				try {
					User user = getSingleResult(query, User.class);
					user.setUserType(User.NORMAL);
					result =  (DigestUtils.md5Hex(passwd).equals(user.getPassword()))?user:null;			
				} catch (NoResultException e) {
					LOG.error("User trying to authenticate not found");
				}
			}			
		} catch (NoResultException e) {
			LOG.info("User not found - authentication failed for user {}", email);
		}
		
	
		return result;
		
	}
	
	/**
	 * Prepares Super User.
	 * @return create enitity
	 */
	private User prepareSuperUser() {
		User user = new User();
		user.setEmail("super");
		String superUser = "Super user";
		user.setFirstName(superUser);
		user.setLastName(superUser);
		user.setUserType(User.SUPER);
		return user;
	}

	/** {@inheritDoc} */
	@Override
	public void deleteUser(int userId) {
		User userToDelete = find(userId, User.class);
		regUserWorkerLocal.deleteRegistrationByUserId(userId);
		remove(userToDelete);
		
	}

	/** {@inheritDoc} */
	@Override
	public User saveUser(User user) {
		String hashedPasswd = DigestUtils.md5Hex(user.getPassword());
		user.setPassword(hashedPasswd);
		return save(user);
	}

	/** {@inheritDoc} */
	@Override
	public User find(int id) {
		return find(id, User.class);
	}
}
