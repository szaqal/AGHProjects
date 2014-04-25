package process.web.actions.ajax;

import java.util.ArrayList;
import java.util.Collection;

import computation.model.ComputingNode;
import computation.worker.NodesWorker;

import core.utils.PagingInfo;

/**
 * Lists currently registered computing nodes.
 * @author  <a href="mailto:malczyk.pawel@gmail.com">malczyk.pawel@gmail.com</a>
 */
public class AjaxNodesListAction extends AbstractAjaxPagedListAction<ComputingNode> {

	/**
	 * Serial.
	 */
	private static final long serialVersionUID = 424278125923418483L;
	
	/**
	 * Single page of registered nodes.
	 * @uml.property  name="pageContent"
	 * @uml.associationEnd  
	 */
	private AjaxPage<ComputingNode> pageContent;
	
	/**
	 * Currently connected computing nodes. 
	 */
	private Collection<ComputingNode> computingNodes;
	
	/**
	 * Constructor.
	 */
	public AjaxNodesListAction() { }
	
	/** {@inheritDoc} */
	@SuppressWarnings("unchecked")
	@Override
	public String execute() throws Exception {
		PagingInfo paginginfo = getPagingInfo();
		computingNodes = locate(NodesWorker.class).retrieveComputingNodes();
		System.out.println("Registerd nodes : " + computingNodes.size());
		Collection<AjaxRow<ComputingNode>> rows = new ArrayList<AjaxRow<ComputingNode>>();
		for(ComputingNode computingNode : computingNodes) {
			rows.add(new AjaxRow<ComputingNode>(computingNode.getJsonData(), computingNode.getUniqueId(), false));
		}
		AjaxRow<ComputingNode> [] resultRows = rows.toArray(new AjaxRow [rows.size()]);
		pageContent = new AjaxPage<ComputingNode>(resultRows, getPage(), paginginfo.getPageCount(computingNodes.size()), computingNodes.size());

		return SUCCESS;
	}

	/**
	 * {@inheritDoc} 
	 * @uml.property  name="pageContent"
	 */
	@Override
	public AjaxPage<ComputingNode> getPageContent() {
		return pageContent;
	}

	/**
	 * Getter.
	 * @return the computingNodesPage
	 */
	public AjaxPage<ComputingNode> getComputingNodesPage() {
		return pageContent;
	}

	/**
	 * Setter.
	 * @param computingNodesPage the computingNodesPage to set
	 */
	public void setComputingNodesPage(AjaxPage<ComputingNode> computingNodesPage) {
		this.pageContent = computingNodesPage;
	}

}
