package process.web.actions.edit;

import java.util.HashMap;
import java.util.Map;

import process.web.actions.AbstractEditAction;
import process.web.actions.ActionHandler;

import computation.model.ComputationPackage;

/**
 * Action that handles process addition.
 * @author  <a href="mailto:malczyk.pawel@gmail.com">malczyk.pawel@gmail.com</a>
 */
public class EditProcessAction extends AbstractEditAction<ComputationPackage> {

	/**
	 * Serial.
	 */
	private static final long serialVersionUID = 3460351023563755650L;
	
	/**
	 * Handler map.
	 * @uml.property  name="handlers"
	 */
	private Map<String, ActionHandler> handlers;

	/**
	 * 
	 * Constructor.
	 */
	public EditProcessAction() {
		super();
		handlers = new HashMap<String, ActionHandler>();
		handlers.put(NEW, new NewHandler());
		handlers.put(SAVE, new SaveHandler());
	}

	
	/** {@inheritDoc} */
	@Override
	protected String defaultOperation() {
		return NEW;
	}

	/** {@inheritDoc} */
	@Override
	public String doEdit() {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public String doSave() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public ComputationPackage load(int uniqueId) {
		// TODO Auto-generated method stub
		return null;
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

}
