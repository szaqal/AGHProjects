package process.web.actions;

import java.util.Map;

/**
 * Base class for all actions doing edition.
 * @author  <a href="mailto:malczyk.pawel@gmail.com">malczyk.pawel@gmail.com</a>
 * @param  <T>  type of entity being edited
 */
public abstract class AbstractEditAction<T> extends AbstractProcessAction {
	
	/** SAVE. **/
	public static final String SAVE = "save"; 
	
	/** NEW. */
	public static final String NEW = "new";
	
	/** Edit. */
	public static final String EDIT = "edit";
	
	/** Load. */
	public static final String LOAD = "load";
	
	/** DELETE. */
	public static final String DELETE = "delete";
	
	/** SAVE. */
	public static final String SAVE_PL = "Zapisz";
	
	/**
	 * Serial.
	 */
	private static final long serialVersionUID = 4583742810966944710L;
	
	/**
	 * Operation.
	 * @uml.property  name="operation"
	 */
	private String operation;

	
	/**
	 * Method used for edit.
	 * @return result.
	 */
	public abstract String doEdit();
	
	/**
	 * Method user for editing.
	 * @return result view.
	 */
	public String doDelete() {
		return DELETE;
	}
	
	/**
	 * Method used by saving.
	 * @return result.
	 */
	public abstract String doSave();
	
	/**
	 * Method used by new event.
	 * @return result
	 */
	public String doNew() { 
		return NEW;
	}
	
	/**
	 * true is edit's action save action invoked.
	 * @return true/false
	 */
	protected boolean isSave() {
		return SAVE.equals(getOperation());
	}
	
	/**
	 * Handles request.
	 * @return return result of handling.
	 */
	public String handle() {
		return  getHandlers().get(getOperation()).handle();
	}
	
	/**
	 * Returns default handler when none of the results specified.
	 * @return default result
	 */
	protected abstract String defaultOperation();
	
	/**
	 * Loads entity to edit.
	 * @param uniqueId entity identifier.
	 * @return found entity.
	 */
	public abstract T load(int uniqueId);

	/**
	 * Getter.
	 * @return  the operation
	 * @uml.property  name="operation"
	 */
	public String getOperation() {
		if(operation == null) {
			operation = defaultOperation();
		}
		return operation;
	}

	/**
	 * Setter.
	 * @param operation  the operation to set
	 * @uml.property  name="operation"
	 */
	public void setOperation(String operation) {
		this.operation = operation;
	}
	
	/**
	 * Returns handlers for action.
	 * @return handlers map
	 */
	protected abstract Map<String, ActionHandler> getHandlers();
}
