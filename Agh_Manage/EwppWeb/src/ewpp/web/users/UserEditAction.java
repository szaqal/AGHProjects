package ewpp.web.users;

import ewpp.auth.entity.User;
import ewpp.auth.entity.UserType;
import ewpp.business.workers.UsersWorker;
import ewpp.web.AbstractEwppAction;

/**
 * Akcja edycji uzytkownika.
 * @author malczyk.pawel@gmail.com
 *
 */
public class UserEditAction extends AbstractEwppAction {

	/** Student. */
	private static final int STUDENT_TYPE = 1;

	/** Wykladowca. */
	private static final int LECTURER_TYPE = 2;

	/** Admin. */
	private static final int ADMIN_TYPE = 3;

	/** Serial. */
	private static final long serialVersionUID = -6274623147948217509L;

	/** Edytowany uzytkownik. */
	private User user;

	/** Wybrany typ uzytkownika w formularzu. */
	private int selectedType;

	/**
	 * Wybrany typ uzytkownika wykladowca/student ktory
	 * odzwierciedla stan sprzed zmiany.
	 */
	private int currentType;

	/** Identyfikator uzytkownika. */
	private int userId;

	/** Konstruktor. */
	public UserEditAction() { };


	/** {@inheritDoc} */
	@Override
	protected String doEdit() {
		user = locate(UsersWorker.class).retrieveUser(userId);
		UserType userType = user.getUserType();
		if (userType == null) {
			currentType = 1;
		} else {

			switch (userType) {
				case STUDENT:
					currentType = STUDENT_TYPE;
					break;
				case LECTURER:
					currentType = LECTURER_TYPE;
					break;
				case ADMIN:
					currentType = ADMIN_TYPE;
					break;
				default:
					break;
			}
		}

		return EDIT;
	}

	/** {@inheritDoc} */
	@Override
	protected String doSave() {
		if (selectedType == STUDENT_TYPE) {
			user.setUserType(UserType.STUDENT);
		} else if (selectedType == LECTURER_TYPE) {
			user.setUserType(UserType.LECTURER);
		} else if (selectedType == ADMIN_TYPE) {
			user.setUserType(UserType.ADMIN);
		}
		locate(UsersWorker.class).saveUser(user);
		return "saved";
	}

	/** {@inheritDoc} */
	@Override
	protected String doDelete() {
		locate(UsersWorker.class).removeUser(userId);
		return "deleted";
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param usr the user to set
	 */
	public void setUser(User usr) {
		this.user = usr;
	}

	/**
	 * @return the userId
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 * @param usrId the userId to set
	 */
	public void setUserId(int usrId) {
		this.userId = usrId;
	}


	/**
	 * @return the selectedType
	 */
	public int getSelectedType() {
		return selectedType;
	}

	/**
	 * @param type the selectedType to set
	 */
	public void setSelectedType(int type) {
		this.selectedType = type;
	}

	/**
	 * @return the currrentType
	 */
	public int getCurrentType() {

		return currentType;
	}

	/**
	 * @param currrentType the currrentType to set
	 */
	public void setCurrentType(int currrentType) {
		this.currentType = currrentType;
	}

}
