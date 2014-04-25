package process.schedulers;

import java.util.Collection;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import computation.model.ComputationNodeHistory;
import computation.model.ComputingNode;
import computation.worker.NodeHistoryWorker;
import computation.worker.NodesWorker;

import core.workers.EjbInterfaceType;

/**
 * Job used to retrieve data about current state of registered computing nodes.
 * @author  <a href="mailto:malczyk.pawel@gmail.com">malczyk.pawel@gmail.com</a>
 */
public class ServersDataJob implements Job {
	
	/** Logger. */
	private static final Logger LOG = LoggerFactory.getLogger(ServersDataJob.class);
	
	/**
	 * Computing nodes worker.
	 * @uml.property  name="nodesWorker"
	 * @uml.associationEnd  
	 */
	private NodesWorker nodesWorker;
	
	/**
	 * Computation node history worker.
	 * @uml.property  name="nodeHistoryWorker"
	 * @uml.associationEnd  
	 */
	private NodeHistoryWorker nodeHistoryWorker;
	
	/**
	 * JNDI Context.
	 * @uml.property  name="initialContext"
	 */
	private InitialContext initialContext;

	
	/** Constructor. */
	public ServersDataJob() { }

	/** {@inheritDoc} */
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		LOG.info("Gathering server data ");
		Collection<ComputingNode> registeredNodes = getNodesWorker().retrieveComputingNodes();
		LOG.info("Gathering server data for {} nodes", registeredNodes.size());
		refreshData(registeredNodes);
		
	}
	
	/**
	 * Refereshes data of every registered computing node.
	 * @param computingNodes list of currently registered computing nodes
	 */
	private void refreshData(Collection<ComputingNode> computingNodes) {
		MbeanUtils mbeanUtils = new MbeanUtils();
		for(ComputingNode computingNode : computingNodes) {
			String address = computingNode.getInetAddr();
			try {
				mbeanUtils.populateNodeData(computingNode);
			}catch(Exception e) {
				LOG.warn("Excepion ", e);
				LOG.info("Removing node from available node pool");
				getNodesWorker().removeComputingNode(computingNode);
				return;
			}
			computingNode.updateLastUpdateDate();
			computingNode.setInetAddr(address);
			
			NodesWorker worker =getNodesWorker();
			if(worker !=null) {
				worker.registerComputingNode(computingNode);
				ComputationNodeHistory computationNodeHistory = new ComputationNodeHistory();
				computationNodeHistory.setArch(computingNode.getArch());
				computationNodeHistory.setNodeIp(computingNode.nodeKey());
				computationNodeHistory.setMemoryFree(computingNode.getFreePhysicalMemory());
				computationNodeHistory.setProcessorLoad(computingNode.getCurrentLoad());
				getNodeHistoryWorker().storeHistoryItem(computationNodeHistory);
			} else {
				LOG.error("Nodes Worker is NULL");
			}
		}
	}
	
	
	/**
	 * Returns JNDI context.
	 * @return  JNDI context
	 * @uml.property  name="initialContext"
	 */
	public final InitialContext getInitialContext() {
		if (initialContext == null) {
			try {
				initialContext = new InitialContext();
			} catch (NamingException e) {
				LOG.warn("Error obtaining initial context", e);
			}
		}
		return initialContext;
	}
	
	/**
	 * lookups for worker in JNDI.
	 * @param <T>
	 * 			worker type
	 * @param workerClass
	 * 			worker class
	 * @return
	 * 		`worker
	 */
	@SuppressWarnings("unchecked")
	public final < T > T locate( Class < T > workerClass ) {
		T result = null;
		try {
			Object object = getInitialContext().lookup(workerClass.getName() + "$" + workerClass.getSimpleName() + EjbInterfaceType.REMOTE );
			result = (T) object;
		} catch (NamingException e) {
			LOG.warn(e.getMessage());
		}
		return result;
	}

	
	
	/**
	 * Returns nodes worker.
	 * @return  nodes worker
	 * @uml.property  name="nodesWorker"
	 */
	private NodesWorker getNodesWorker() {
		
		if(nodesWorker != null) {
			return nodesWorker;
		}
		nodesWorker = locate(NodesWorker.class);
		
		return nodesWorker;
	}
	
	/**
	 * Returns nodes worker.
	 * @return  nodes worker
	 * @uml.property  name="nodeHistoryWorker"
	 */
	private NodeHistoryWorker getNodeHistoryWorker() {
		
		if(nodeHistoryWorker != null) {
			return nodeHistoryWorker;
		}
		nodeHistoryWorker = locate(NodeHistoryWorker.class);
		
		return nodeHistoryWorker;
	}
}
