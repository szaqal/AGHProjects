package process.web.actions;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.utils.StringUtils;

import auth.RegUserWorker;
import auth.model.User;

/**
 * Action or user registration.
 * @author  <a href="mailto:malczyk.pawel@gmail.com">malczyk.pawel@gmail.com</a>
 */
public class RegisterUserAction extends AbstractEditAction<User> {

	/** Register. */
	private static final String REGISTER = "register";
	
	/** Logger. */
	private static final Logger LOG = LoggerFactory.getLogger(RegisterUserAction.class);
	
	/**
	 * Serial.
	 */
	private static final long serialVersionUID = -8958756058673154947L;
	
	/**
	 * Handlers for action commands.
	 * @uml.property  name="handlers"
	 */
	private Map<String, ActionHandler> handlers;
	
	/**
	 * User being created.
	 * @uml.property  name="user"
	 * @uml.associationEnd  
	 */
	private User user;
	
	/**
	 * Constructor.
	 */
	public RegisterUserAction() { 
		super();
		handlers = new HashMap<String, ActionHandler>();
		handlers.put(SAVE, new SaveHandler());
		handlers.put(REGISTER, new ActionHandler() {
			
			@Override
			public String handle() {
				LOG.info("Executing Register Action");
				return REGISTER;
			}
		});
	}
	
	
	/** {@inheritDoc} */
	@Override
	public String doEdit() {
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public String doSave() {
		locate(RegUserWorker.class).storeUser(user);
		return LOGIN;
	}

	/** {@inheritDoc} */
	@Override
	public User load(int uniqueId) {
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public void validate() {
		if(user !=null){
			if(StringUtils.isEmpty(user.getEmail())) {
				addFieldError("user.email", getText("form.error.email"));
			}
			if(StringUtils.isEmpty(user.getPassword())) {
				addFieldError("user.password", getText("form.error.passwd"));
			}
			if(StringUtils.isEmpty(user.getFirstName())) {
				addFieldError("user.firstName", getText("form.error.user.fname"));
			}
			if(StringUtils.isEmpty(user.getLastName())) {
				addFieldError("user.lastName", getText("form.error.user.lname"));
			}
		}
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


	/**
	 * {@inheritDoc} 
	 * @uml.property  name="handlers"
	 */
	@Override
	protected Map<String, ActionHandler> getHandlers() {
		return handlers;
	}


	/** {@inheritDoc} */
	@Override
	protected String defaultOperation() {
		return REGISTER;
	}
	
	/**
	 * Save handler.
	 * @author <a href="mailto:malczyk.pawel@gmail.com">malczyk.pawel@gmail.com</a>
	 *
	 */
	private class SaveHandler implements ActionHandler {
		
		/**
		 * Constructor.
		 */
		public SaveHandler() { }

		/** {@inheritDoc} */
		@Override
		public String handle() {
			return doSave();
		}
		
	}

}
