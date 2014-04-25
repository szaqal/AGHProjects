package process.web.actions.edit;

import java.util.HashMap;
import java.util.Map;

import process.web.actions.AbstractEditAction;
import process.web.actions.ActionHandler;

import computation.model.NodeMapping;
import computation.worker.NodeMappingWorker;

import core.utils.StringUtils;

/**
 * Action that handles  {@link NodeMapping}  edition.
 * @author  malczyk.pawel@gmail.com
 */
public class EditMappingAction extends AbstractEditAction<NodeMapping> {

	/**
	 * Message key.
	 */
	private static final String COMMON_REQUIRED_FIELD = "common.requiredField";

	/**
	 * Serial.
	 */
	private static final long serialVersionUID = 1981923679369388493L;
	
	/**
	 * Handler map.
	 * @uml.property  name="handlers"
	 */
	private Map<String, ActionHandler> handlers;
	
	/**
	 * Unique identifier.
	 * @uml.property  name="uniqueId"
	 */
	private int uniqueId;
	
	/**
	 * Performer name.
	 * @uml.property  name="performer"
	 */
	private String performer;
	
	/**
	 * Performer node/ip.
	 * @uml.property  name="node"
	 */
	private String node;
	
	/**
	 * Constructor.
	 */
	public EditMappingAction() {
		super();
		handlers = new HashMap<String, ActionHandler>();
		handlers.put(NEW, new NewHandler());
		handlers.put(SAVE, new SaveHandler());
		handlers.put(DELETE, new DeleteHandler());
		handlers.put(EDIT, new EditHandler());
	}

	/** {@inheritDoc} */
	@Override
	protected String defaultOperation() {
		return EDIT;
	}

	
	/** {@inheritDoc} */
	@Override
	public String doEdit() {
		NodeMapping mapping = locate(NodeMappingWorker.class).retrieveById(uniqueId);
		this.performer = mapping.getMapping();
		this.node = mapping.getNode();
		return EDIT;
	}
	
	/** {@inheritDoc} */
	@Override
	public String doDelete() {
		locate(NodeMappingWorker.class).delete(Integer.valueOf(uniqueId));
		return DELETE;
	}

	/** {@inheritDoc} */
	@Override
	public String doSave() {
		NodeMapping mapping = new NodeMapping();
		mapping.setMapping(this.performer);
		mapping.setNode(this.node);
		mapping.setUniqueId(getUniqueId());
		mapping.setOwnerId(Integer.valueOf(getSessionData().getUserId()));
		locate(NodeMappingWorker.class).store(mapping);
		return SAVE;
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
	public NodeMapping load(int uniqueId) {
		return locate(NodeMappingWorker.class).retrieveById(uniqueId);
	}
	
	@Override
	public void validate() {
		if(SAVE.equals(getOperation())) {
			if(StringUtils.isEmpty(node)) {
				addFieldError("node", getText(COMMON_REQUIRED_FIELD));
			}
			
			if(StringUtils.isEmpty(performer)) {
				addFieldError("performer", getText(COMMON_REQUIRED_FIELD));
			}
		}
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
	 * Handles delete event.
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
	 * Handles edit event.
	 * @author malczyk.pawel@gmail.com
	 *
	 */
	private class EditHandler implements ActionHandler {
		
		/**
		 * Constructor.
		 */
		public EditHandler() {
		}

		/** {@inheritDoc} */
		@Override
		public String handle() {
			return doEdit();
		}
	}

	/**
	 * Getter.
	 * @return  the uniqueId
	 * @uml.property  name="uniqueId"
	 */
	public int getUniqueId() {
		return uniqueId;
	}

	/**
	 * Setter.
	 * @param uniqueId  the uniqueId to set
	 * @uml.property  name="uniqueId"
	 */
	public void setUniqueId(int uniqueId) {
		this.uniqueId = uniqueId;
	}

	/**
	 * Getter.
	 * @return  the performer
	 * @uml.property  name="performer"
	 */
	public String getPerformer() {
		return performer;
	}

	/**
	 * Setter.
	 * @param performer  the performer to set
	 * @uml.property  name="performer"
	 */
	public void setPerformer(String performer) {
		this.performer = performer;
	}

	/**
	 * Getter.
	 * @return  the node
	 * @uml.property  name="node"
	 */
	public String getNode() {
		return node;
	}

	/**
	 * Setter.
	 * @param node  the node to set
	 * @uml.property  name="node"
	 */
	public void setNode(String node) {
		this.node = node;
	}

}
