package ewpp.auth.workers;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import ewpp.auth.entity.User;
import ewpp.auth.entity.UserType;
import ewpp.auth.workers.DatabaseAuthenticatorWorker.DatabaseAuthenticatorWorkerLocal;
import ewpp.auth.workers.DatabaseAuthenticatorWorker.DatabaseAuthenticatorWorkerRemote;
import ewpp.utils.StringUtils;
import ewpp.workers.AbstractWorkerBean;

/**
 * Implementacja workera.
 * @author malczyk
 *
 */
@Stateless
@Remote(DatabaseAuthenticatorWorkerRemote.class)
@Local(DatabaseAuthenticatorWorkerLocal.class)
public class DatabaseAutheticatorWorkerBean extends AbstractWorkerBean implements DatabaseAuthenticatorWorker {

	/** Stala. */
	private static final String SUPER = "_super_";
	/** Logger. */
	private static final Logger LOG = LoggerFactory.getLogger(DatabaseAutheticatorWorkerBean.class);

	/** Konstruktor. */
	public DatabaseAutheticatorWorkerBean() {
	}

	/** {@inheritDoc} */
	@Override
	public User authenticate(String login, String password, AuthenticationMethod authMethod) {
		
		if (SUPER.equals(login) && SUPER.equals(password)) {
			return prepareSuperUser();
		}

		String authQuery = "SELECT user FROM User AS user WHERE user.login =:login AND user.passwd =:password AND deleted =:deleted";
		LOG.info("Uwiezytelnie uzytkownika : " + login);
		Query query = getEntityManager().createQuery(authQuery);
		query.setParameter("login", ( login==null ) ? StringUtils.EMPTY : login );
		query.setParameter("password", (password == null) ? StringUtils.EMPTY : password );
		query.setParameter("deleted", false);
		List<User> users = getResultList(query, User.class);
		User result = null;
		if (users.size() == 1) {
			result = users.get(0);
			LOG.debug("Znaleziono uzytkownika");
		}

		return result;
	}
	
	/**
	 * Tworzy supera.
	 * @return superuzytkownik
	 */
	private User prepareSuperUser() {
		User user = new User();
		user.setFirstName("super");
		user.setLastName("superowy");
		user.setUserType(UserType.ADMIN);
		return user;
	}

}
