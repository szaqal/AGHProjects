package process.web.actions.edit;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import process.web.actions.AbstractEditAction;
import process.web.actions.ActionHandler;
import auth.GroupsWorker;
import auth.model.Group;


/**
 * Group editor.
 * @author  <a href="mailto:malczyk.pawel@gmail.com">malczyk.pawel@gmail.com</a>
 */
public class EditGroupAction extends AbstractEditAction<Group> {

	/**
	 * Serial.
	 */
	private static final long serialVersionUID = -8077451621417913206L;
	
	
	/**
	 * Group being edited.
	 * @uml.property  name="group"
	 * @uml.associationEnd  
	 */
	private Group group;
	
	/**
	 * Handler map.
	 * @uml.property  name="handlers"
	 */
	private Map<String, ActionHandler> handlers;
	
	/**
	 * Array of checked permissions.
	 * @uml.property  name="rights"
	 */
	private String [] rights;
	
	/**
	 * 
	 * Constructor.
	 */
	public EditGroupAction() { 
		super();
		handlers = new HashMap<String, ActionHandler>();
		handlers.put(NEW, new NewHandler());
		handlers.put(SAVE, new SaveHandler());
	}

	/** {@inheritDoc} */
	@Override
	public String doEdit() {
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public String doSave() {
		String rightsSerparator = ";";
		final String strRights = StringUtils.join(rights, rightsSerparator);
		group.setRights(strRights);
		locate(GroupsWorker.class).saveGroup(group);
		return SUCCESS;
	}
	
	/** {@inheritDoc} */
	@Override
	public void validate() {
		if(isSave() && core.utils.StringUtils.isEmpty(group.getName()) ) {
			addFieldError("group.name", getText("common.requiredField"));
		}
	}

	/** {@inheritDoc} */
	@Override
	public Group load(int uniqueId) {
		return locate(GroupsWorker.class).retrieveGroup(uniqueId);
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
	 * Handler that handles save event.
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
		return NEW;
	}

	/**
	 * Getter.
	 * @return  the group
	 * @uml.property  name="group"
	 */
	public Group getGroup() {
		return group;
	}

	/**
	 * Setter.
	 * @param group  the group to set
	 * @uml.property  name="group"
	 */
	public void setGroup(Group group) {
		this.group = group;
	}

	/**
	 * Getter.
	 * @return  the rights
	 * @uml.property  name="rights"
	 */
	public String[] getRights() {
		return rights;
	}

	/**
	 * Setter.
	 * @param rights  the rights to set
	 * @uml.property  name="rights"
	 */
	public void setRights(String[] rights) {
		this.rights = rights;
	}

}
