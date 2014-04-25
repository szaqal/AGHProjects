package ewpp.web.users.ajax;

import ewpp.auth.entity.User;
import ewpp.business.workers.UsersWorker;
import ewpp.utils.PagingInfo;

import java.util.Collection;

/**
 * Akcja zwracajaca Ajaxowa odpowiedz z lista uzytkownikow.
 * @author malczyk.pawel@gmail.com
 *
 */
public class LecturersListAction extends UsersListAction {

	/**
	 * Serial.
	 */
	private static final long serialVersionUID = -279012150269998885L;
	
	/**
	 * Konstruktor.
	 */
	public LecturersListAction() { }
	
	/** {@inheritDoc} */
	@Override
	protected Collection<User> getContents(PagingInfo pagingInfo) {
		return locate(UsersWorker.class).retrieveLecturers();
	}

}
