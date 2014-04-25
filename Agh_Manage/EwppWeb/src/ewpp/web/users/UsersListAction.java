package ewpp.web.users;

import ewpp.web.AbstractEwppAction;

/**
 *
 * Akcja listy uztkownikow.
 * @author malczyk.pawel@gmail.com
 *
 */
public class UsersListAction extends AbstractEwppAction {

	/**
	 * Serial.
	 */
	private static final long serialVersionUID = -3850348701217410057L;
	
	/** Stala. */
	private static final String PICK = "pick";
	
	/** Stala. */
	private static final String PICK_STUDENT = "pickstudent";

	/** Konstruktor. */
	public UsersListAction() { };
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String execute() throws Exception {
		String operation = super.execute();
		
		if(PICK.equals(operation)) {
			operation = PICK;
		} else if( PICK_STUDENT.equals(operation)) {
			operation = PICK_STUDENT;
		}
		
		return operation;
	}


	/** {@inheritDoc} */
	@Override
	public String getDefaultOpertation() {
		return LIST;
	}

	/** {@inheritDoc} */
	@Override
	protected String doList() {
		return LIST;
	}


}
