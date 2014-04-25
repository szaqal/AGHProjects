package process.web.actions;

import java.util.Map;

import node.http.Dispatcher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.caucho.hessian.client.HessianProxyFactory;
import computation.model.ComputingNode;
import computation.worker.NodesWorker;
import computation.worker.VariableWorker;

/**
 * Lists opertion available on particular nodes.
 * @author malczyk.pawel@gmail.com
 *
 */
public class NodeOperationsAction extends AbstractProcessAction {
	
	/**
	 * Serial.
	 */
	private static final long serialVersionUID = -459349704566442561L;

	/**
	 * Logger.
	 */
	private static final Logger LOG = LoggerFactory.getLogger(NodeOperationsAction.class);

	
	/**
	 * Checking node ip.
	 */
	private String nodeIp;
	
	/**
	 * Returned operations.
	 */
	private Map<String, String> operations;
	
	/**
	 * Constructor.
	 */
	public NodeOperationsAction() {
		super();
	}
	
	
	/** {@inheritDoc} */
	@SuppressWarnings("unchecked")
	@Override
	public String execute() throws Exception {
		ComputingNode node = locate(NodesWorker.class).retrieveRegisteredComputingNode(nodeIp);
		if(node !=null) {
			node.getOperationPort();
			String serverUrl = "http://"+node.getInetAddr()+":"+node.getOperationPort()+"/node/dispatch";
			HessianProxyFactory factory = new HessianProxyFactory();
			Dispatcher basic = (Dispatcher) factory.create(Dispatcher.class, serverUrl);
			String result = basic.computationList();
			operations = locate(VariableWorker.class).deserialize(result, Map.class);
		} else {
			LOG.warn("Such computing node not found {}", nodeIp);
		}
		return SUCCESS;
	}


	/**
	 * Getter.
	 * @return the nodeIp
	 */
	public String getNodeIp() {
		return nodeIp;
	}


	/**
	 * Setter.
	 * @param nodeIp the nodeIp to set
	 */
	public void setNodeIp(String nodeIp) {
		this.nodeIp = nodeIp;
	}


	/**
	 * Getter.
	 * @return the operations
	 */
	public Map<String, String> getOperations() {
		return operations;
	}


	/**
	 * Setter.
	 * @param operations the operations to set
	 */
	public void setOperations(Map<String, String> operations) {
		this.operations = operations;
	}
}
