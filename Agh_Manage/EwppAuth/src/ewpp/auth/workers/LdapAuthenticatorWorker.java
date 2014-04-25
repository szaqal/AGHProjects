package ewpp.auth.workers;


/**
 * Implementacja interfejsu przeprowadza autentykacje w oparciu o repozytorium
 * LDAP.
 *
 * @author malczyk.pawel@gmail.com
 *
 */
public interface LdapAuthenticatorWorker extends Authenticator {

	/** Interfejs zdalny. */
	public interface LdapAuthenticatorRemote extends LdapAuthenticatorWorker {
	}

	/** Interfejs lokalny. */
	public interface LdapAuthenticatorLocal extends LdapAuthenticatorWorker {
	}

}

