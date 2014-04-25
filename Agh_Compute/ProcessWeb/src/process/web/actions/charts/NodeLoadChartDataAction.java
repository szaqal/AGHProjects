package process.web.actions.charts;


import java.util.Collection;

import process.web.actions.AbstractProcessAction;

import computation.model.ComputationNodeHistory;
import computation.worker.NodeHistoryWorker;

import core.utils.StringUtils;

/**
 * Generates data used by charts.
 * @author  malczyk.pawel@gmail.com
 */
public class NodeLoadChartDataAction extends AbstractProcessAction {

	/**
	 * Serial. 
	 */
	private static final long serialVersionUID = 896984287993613534L;
	
	/**
	 * Determines which data should be loaded to populate chart.
	 * @uml.property  name="action"
	 */
	private String action;
	
	/**
	 * Node ip.
	 * @uml.property  name="nodeIp"
	 */
	private String nodeIp;
	
	/**
	 * Resource usage type.
	 */
	private String type;
	
	/**
	 * Collection of node load values.
	 * @uml.property  name="items"
	 */
	private Collection<ComputationNodeHistory> items;

	
	/**
	 * Constructor.
	 */
	public NodeLoadChartDataAction() {
		super();
	}
	
	/** {@inheritDoc} */
	@Override
	public String execute() throws Exception {
		String result = null;
		if(StringUtils.isEmpty(action)) {
			result = SUCCESS;
		} else if("nodeload".equals(action)){
			items = locate(NodeHistoryWorker.class).retrieveHistory(nodeIp);
			result = type;
		} 
		return result;
	}

	/**
	 * Getter.
	 * @return  the action
	 * @uml.property  name="action"
	 */
	public String getAction() {
		return action;
	}

	/**
	 * Setter.
	 * @param action  the action to set
	 * @uml.property  name="action"
	 */
	public void setAction(String action) {
		this.action = action;
	}

	/**
	 * Getter.
	 * @return  the nodeIp
	 * @uml.property  name="nodeIp"
	 */
	public String getNodeIp() {
		return nodeIp;
	}

	/**
	 * Setter.
	 * @param nodeIp  the nodeIp to set
	 * @uml.property  name="nodeIp"
	 */
	public void setNodeIp(String nodeIp) {
		this.nodeIp = nodeIp;
	}

	/**
	 * Getter.
	 * @return  the items
	 * @uml.property  name="items"
	 */
	public Collection<ComputationNodeHistory> getItems() {
		return items;
	}

	/**
	 * Setter.
	 * @param items  the items to set
	 * @uml.property  name="items"
	 */
	public void setItems(Collection<ComputationNodeHistory> items) {
		this.items = items;
	}

	/**
	 * Getter.
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * Setter.
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	
}
