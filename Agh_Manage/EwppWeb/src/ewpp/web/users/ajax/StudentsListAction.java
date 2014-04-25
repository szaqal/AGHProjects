package ewpp.web.users.ajax;

import ewpp.auth.entity.User;
import ewpp.auth.entity.UserType;
import ewpp.business.workers.UsersWorker;
import ewpp.utils.PagingInfo;
import ewpp.utils.SearchParameters;
import ewpp.utils.StringUtils;

import java.util.Collection;

/**
 * Akcja zwracajaca Ajaxowa odpowiedz z lista uzytkownik�w.
 * @author malczyk.pawel@gmail.com
 *
 */
public class StudentsListAction extends UsersListAction {

	/**
	 * Sta�a.
	 */
	private static final String USER_TYPE = "userType";
	/**
	 * Serial.
	 */
	private static final long serialVersionUID = 1099485141157709525L;
	
	/**
	 * Konstruktor domylny.
	 */
	public StudentsListAction() { };
	
	/** {@inheritDoc} */
	@Override
	protected Collection<User> getContents(PagingInfo pagingInfo) {
		if (StringUtils.isEmpty(getFnameParam()) && StringUtils.isEmpty(getLnameParam())) {
			return locate(UsersWorker.class).retrieveStudents(pagingInfo);
		} else {
			SearchParameters<User> searchParams = extractParamsFromRequest();
			searchParams.addEqualsSearchParam(USER_TYPE, UserType.STUDENT);
			return locate(UsersWorker.class).retrieveUsers(searchParams, pagingInfo);
		}
	}
	
	/** {@inheritDoc} */
	@Override
	protected long getUsersCount() {
		if (StringUtils.isEmpty(getFnameParam()) && StringUtils.isEmpty(getLnameParam())) {
			return locate(UsersWorker.class).retrieveStudentsCount();
		} else {
			SearchParameters<User> searchParams = extractParamsFromRequest();
			searchParams.addEqualsSearchParam(USER_TYPE, UserType.STUDENT);
			return locate(UsersWorker.class).retrieveUsersCount(searchParams);
		}
	}
	

}
