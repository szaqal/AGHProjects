package process.web.actions.ajax;

import process.web.actions.AbstractProcessAction;
import auth.UsersWorker;
import auth.model.User;

/**
 * Gets user details.
 * @author  malczyk.pawel@gmail.com
 */
public class AjaxUserDetailsAction extends AbstractProcessAction {

	/**
	 * Serial.
	 */
	private static final long serialVersionUID = -107255468506090806L;
	
	/**
	 * User id.
	 * @uml.property  name="id"
	 */
	private String id;
	
	/**
	 * User whose details are being displayed.
	 * @uml.property  name="user"
	 * @uml.associationEnd  
	 */
	private User user;
	
	/**
	 * Constructor.
	 */
	public AjaxUserDetailsAction() { 
		super();
	}
	
	/** {@inheritDoc} */
	@Override
	public String execute() throws Exception {
		user = locate(UsersWorker.class).find(Integer.valueOf(id));
		return SUCCESS;
	}

	/**
	 * Getter.
	 * @return  the id
	 * @uml.property  name="id"
	 */
	public String getId() {
		return id;
	}

	/**
	 * Setter.
	 * @param id  the id to set
	 * @uml.property  name="id"
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Getter.
	 * @return  the user
	 * @uml.property  name="user"
	 */
	public User getUser() {
		return user;
	}

	/**
	 * Setter.
	 * @param user  the user to set
	 * @uml.property  name="user"
	 */
	public void setUser(User user) {
		this.user = user;
	}
	
	

}
