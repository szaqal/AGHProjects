package process.web.actions.edit;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import process.web.Require;
import process.web.actions.AbstractEditAction;
import process.web.actions.ActionHandler;
import auth.UserGroupWorker;
import auth.UsersWorker;
import auth.model.Group;
import auth.model.User;

/**
 * Handles edit user operations.
 * @author  malczyk.pawel@gmail.com
 */
@Require(permission="user_admin")
public class EditUserAction  extends AbstractEditAction<User> {
	
	/**
	 * Constant.
	 */
	public static final String ADD_TO_GROUP = "Dodaj do grupy";
	
	/**
	 * Constant.
	 */
	public static final String REMOVE_FROM_GROUP = "deleteGroup";

	/**
	 * Serial.
	 */
	private static final long serialVersionUID = -3847082207689527073L;
	
	/**
	 * Handler map.
	 * @uml.property  name="handlers"
	 */
	private Map<String, ActionHandler> handlers;
	
	/**
	 * Identifier for edited user.
	 * @uml.property  name="id"
	 */
	private String id;
	
	/**
	 * Edited created user.
	 * @uml.property  name="user"
	 * @uml.associationEnd  
	 */
	private User user;
	
	/**
	 * Groups.
	 * @uml.property  name="group"
	 */
	private String group;
	
	/**
	 * Users worker used.
	 * @uml.property  name="worker"
	 * @uml.associationEnd  
	 */
	private UsersWorker worker;

	
	
	/**
	 * {@linkplain UserGroupWorker}  .
	 * @uml.property  name="userGroupWorker"
	 * @uml.associationEnd  
	 */
	private UserGroupWorker userGroupWorker;
	
	/**
	 * Users group entries.
	 * @uml.property  name="usersGroup"
	 */
	private Collection<Group> usersGroup;
	
	/**
	 * List of all available groups.
	 * @uml.property  name="allGroups"
	 */
	private Collection<Group> allGroups;
	
	/**
	 * Constructor.
	 */
	public EditUserAction() {
		super();
		handlers = new HashMap<String, ActionHandler>();
		handlers.put(NEW, new NewHandler());
		handlers.put(SAVE_PL, new SaveHandler());
		handlers.put(EDIT, new EditHandler());
		handlers.put(DELETE, new DeleteHandler());
		handlers.put(ADD_TO_GROUP, new AddToGroupHandler());
		handlers.put(REMOVE_FROM_GROUP, new RemoveFromGroupHandler());
		worker = locate(UsersWorker.class);
		userGroupWorker = locate(UserGroupWorker.class);	
	}

	/** {@inheritDoc} */
	@Override
	protected String defaultOperation() {
		return EDIT;
	}

	/** {@inheritDoc} */
	@Override
	public String doEdit() {
		user = getWorker().find(Integer.valueOf(id));
		usersGroup = userGroupWorker.retrieveByUser(Integer.valueOf(id));
		allGroups = userGroupWorker.retrieveAvailable(Integer.valueOf(id));
		return EDIT;
	}

	/** {@inheritDoc} */
	@Override
	public String doSave() {
		getWorker().saveUser(user);
		return  SAVE;
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
	public User load(int uniqueId) {
		return getWorker().find(uniqueId);
	}

	/** {@inheritDoc} */
	@Override
	public String doDelete() {
		getWorker().deleteUser(Integer.valueOf(id));
		return DELETE;
	}
	
	/**
	 * Handler new group event.
	 * @author <a href="mailto:malczyk.pawel@gmail.com">malczyk.pawel@gmail.com</a>
	 *
	 */
	private class NewHandler implements ActionHandler {
		
		/**
		 * 
		 * Constructor.
		 */
		public NewHandler() { }

		/** {@inheritDoc} */
		@Override
		public String handle() {
			return doNew();
		}
		
	}
	
	/**
	 * Handles add to group operation.
	 * @author malczyk.pawel@gmail.com
	 *
	 */
	private class AddToGroupHandler implements ActionHandler {
		
		/**
		 * Constructor.
		 */
		public AddToGroupHandler() { }

		/** {@inheritDoc} */
		@Override
		public String handle() {
			userGroupWorker.addUserToGroup(Integer.valueOf(group), user);
			id=String.valueOf(user.getUniqueId());
			return doEdit();
		}	
	}
	
	/**
	 * Handles removing user from group.
	 * @author malczyk.pawel@gmail.com
	 *
	 */
	private class RemoveFromGroupHandler implements ActionHandler {

		/**
		 * Constructor.
		 */
		public RemoveFromGroupHandler() {
		}

		/** {@inheritDoc} */
		@Override
		public String handle() {
			User user = getWorker().find(Integer.valueOf(id));
			userGroupWorker.removeUserFromGroup(Integer.valueOf(group), user);
			return doEdit();
		}
		
	}
	
	/**
	 * Handler for delete event.
	 * @author malczyk.pawel@gmail.com
	 *
	 */
	private class DeleteHandler implements ActionHandler {
		
		/**
		 * Constructor.
		 */
		public DeleteHandler() { }

		/** {@inheritDoc} */
		@Override
		public String handle() {
			return doDelete();
		}
		
	}
	
	/**
	 * Handler edit.
	 * @author malczyk.pawel@gmail.com
	 *
	 */
	private class EditHandler implements ActionHandler {
		
		/**
		 * Constructor.
		 */
		public EditHandler() { }
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		public String handle() {
			return doEdit();
		}
	}
	
	/**
	 * Handler for save event.
	 * @author malczyk.pawel@gmail.com
	 *
	 */
	private class SaveHandler implements ActionHandler {
		
		/**
		 * Constructor.
		 */
		public SaveHandler() { }
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		public String handle() {
			return doSave();
		}
		
	}

	/**
	 * Getter.
	 * @return  the worker
	 * @uml.property  name="worker"
	 */
	public UsersWorker getWorker() {
		return worker;
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

	/**
	 * Getter.
	 * @return  the usersGroup
	 * @uml.property  name="usersGroup"
	 */
	public Collection<Group> getUsersGroup() {
		return usersGroup;
	}

	/**
	 * Setter.
	 * @param usersGroup  the usersGroup to set
	 * @uml.property  name="usersGroup"
	 */
	public void setUsersGroup(Collection<Group> usersGroup) {
		this.usersGroup = usersGroup;
	}

	/**
	 * Getter.
	 * @return  the allGroups
	 * @uml.property  name="allGroups"
	 */
	public Collection<Group> getAllGroups() {
		return allGroups;
	}

	/**
	 * Setter.
	 * @param allGroups  the allGroups to set
	 * @uml.property  name="allGroups"
	 */
	public void setAllGroups(Collection<Group> allGroups) {
		this.allGroups = allGroups;
	}

	/**
	 * Getter.
	 * @return  the group
	 * @uml.property  name="group"
	 */
	public String getGroup() {
		return group;
	}

	/**
	 * Setter.
	 * @param group  the group to set
	 * @uml.property  name="group"
	 */
	public void setGroup(String group) {
		this.group = group;
	}


}
