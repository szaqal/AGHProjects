package auth;

import java.util.Date;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.commons.codec.digest.DigestUtils;

import auth.RegUserWorker.RegUserWorkerLocal;
import auth.RegUserWorker.RegUserWorkerRemote;
import auth.model.Registration;
import auth.model.User;
import core.messaging.workers.MailerWorker.MailerWorkerLocal;
import core.workers.AbstractWorkerBean;

/**
 * Implementation.
 * @author  <a href="mailto:malczyk.pawel@gmail.com">malczyk.pawel@gmail.com</a>
 */
@Stateless
@Local(RegUserWorkerLocal.class)
@Remote(RegUserWorkerRemote.class)
public class RegUserWorkerBean extends AbstractWorkerBean implements RegUserWorker {
	
	/**
	 * Mailer worker.
	 * @uml.property  name="mailerWorker"
	 * @uml.associationEnd  
	 */
	@EJB
	private MailerWorkerLocal mailerWorker;
	
	/**
	 * 
	 * Constructor.
	 */
	public RegUserWorkerBean() {
		super();
	}

	/** {@inheritDoc} */
	@Override
	public int storeUser(User user) {
		String hashedPasswd = DigestUtils.md5Hex(user.getPassword());
		user.setPassword(hashedPasswd);
		user.setActive(false);
		user = save(user);
		
		Registration registration = new Registration();
		registration.setRegistrationDate(new Date());
		registration.setCompleteRegistration(null);
		registration.setUsr(user);
		registration = save(registration);
		mailerWorker.sendCompleteRegistration(registration.getUniqueId(), user.getEmail());
		
		return user.getUniqueId();
	}

	/** {@inheritDoc} */
	@Override
	public boolean completeRegistration(int registrationId) {
		boolean result = true;
		try {
			Registration registration = find(registrationId, Registration.class);
			registration.setCompleteRegistration(new Date());
			save(registration);
			User user = registration.getUsr();
			user.setActive(true);
			save(user);
		} catch (NoResultException e) {
			result = false;
		} catch (NullPointerException e) {
			result = false;
		}
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public Registration getByUser(int userId) {
		Query query = getEntityManager().createNamedQuery(Registration.BY_USER);
		query.setParameter("usrId", userId);
		Registration result = (Registration) query.getSingleResult();
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public void deleteRegistrationByUserId(int userId) {
		remove(getByUser(userId));
	}

}
