package ewpp.web.users;

import ewpp.auth.entity.User;
import ewpp.auth.workers.DatabaseRegistratorWorker;
import ewpp.web.AbstractEwppAction;



/**
 * Akcja rejestrowania uzytkownikow.
 * @author malczyk.pawel@gmail.com
 *
 */
public class RegisterUserAction extends AbstractEwppAction {


	/** Serial. */
	private static final long serialVersionUID = 2509222106305580567L;


	/** Obiekt rejestrowanego uzytkownika. */
	private User user;

	/** Kontruktor. */
	public RegisterUserAction() { }


	/** {@inheritDoc} */
	@Override
	protected String doEdit() {
		return EDIT;
	}

	/** {@inheritDoc} */
	@Override
	protected String doSave() {
		locate(DatabaseRegistratorWorker.class).storeNewUser(user);
		return LOGIN;
	}

	/** {@inheritDoc} */
	@Override
	public String getDefaultOpertation() {
		return EDIT;
	}

	/**
	 * Getter.
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * Setter.
	 * @param usr the user to set
	 */
	public void setUser(User usr) {
		this.user = usr;
	}

}
