package process.web.actions.ajax;

import process.web.actions.AbstractProcessAction;
import auth.GroupsWorker;
import auth.model.Group;

/**
 * Show details of groups.
 * @author  malczyk.pawel@gmail.com
 */
public class AjaxGroupDetailAction extends AbstractProcessAction {

	/**
	 * Serial.
	 */
	private static final long serialVersionUID = -3198209894947209155L;
	
	/**
	 * Group id used.
	 * @uml.property  name="id"
	 */
	private String id;
	
	/**
	 * Group for which details are being showed.
	 * @uml.property  name="group"
	 * @uml.associationEnd  
	 */
	private Group group;
	
	/**
	 * Constructor.
	 */
	public AjaxGroupDetailAction() {
		super();
	}
	
	/** {@inheritDoc} */
	@Override
	public String execute() throws Exception {
		group = locate(GroupsWorker.class).retrieveGroup(Integer.valueOf(id));
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

}
