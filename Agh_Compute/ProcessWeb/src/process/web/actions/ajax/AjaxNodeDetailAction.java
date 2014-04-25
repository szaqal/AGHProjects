package process.web.actions.ajax;

import process.web.actions.AbstractProcessAction;

import computation.model.ComputingNode;
import computation.worker.NodesWorker;

/**
 * Action handles details abut connected computing nodes.
 * @author  <a href="mailto:malczyk.pawel@gmail.com">malczyk.pawel@gmail.com</a>
 */
public class AjaxNodeDetailAction extends AbstractProcessAction {

	/**
	 * Serial.
	 */
	private static final long serialVersionUID = -8589619949557307447L;
	
	/**
	 * Computing node identifier (id is the key/inetaddress).
	 * @uml.property  name="id"
	 */
	private String id;
	
	/**
	 * Computing node.
	 * @uml.property  name="computingNode"
	 * @uml.associationEnd  
	 */
	private ComputingNode computingNode;
	
	/**
	 * 
	 * Constructor.
	 */
	public AjaxNodeDetailAction() { }
	
	/** {@inheritDoc} */
	@Override
	public String execute() throws Exception {
		computingNode = locate(NodesWorker.class).retrieveRegisteredComputingNode(id);
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
	 * @return  the computingNode
	 * @uml.property  name="computingNode"
	 */
	public ComputingNode getComputingNode() {
		return computingNode;
	}

	/**
	 * Setter.
	 * @param computingNode  the computingNode to set
	 * @uml.property  name="computingNode"
	 */
	public void setComputingNode(ComputingNode computingNode) {
		this.computingNode = computingNode;
	}

}
