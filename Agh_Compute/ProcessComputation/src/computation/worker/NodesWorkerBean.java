package computation.worker;

import java.util.Collection;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import computation.NodesHolder;
import computation.model.ComputingNode;
import computation.model.RegistrationData;
import computation.worker.NodesWorker.NodesWorkerLocal;
import computation.worker.NodesWorker.NodesWorkerRemote;

import core.workers.AbstractWorkerBean;

/**
 * Implementation of {@link NodesWorker}.
 * @author <a href="mailto:malczyk.pawel@gmail.com">malczyk.pawel@gmail.com</a>
 *
 */
@Stateless
@Local(NodesWorkerLocal.class)
@Remote(NodesWorkerRemote.class)
public class NodesWorkerBean extends AbstractWorkerBean implements NodesWorker {
	
	/** Constant. */
	private static final String REGISTERING_NODE = "Registering node";
	
	/** Logger. */
	private static final Logger LOG = LoggerFactory.getLogger(NodesWorkerBean.class);
	
	/**
	 * Constructor.
	 */
	public NodesWorkerBean() { }

	/** {@inheritDoc} */
	@Override
	public void registerNode(RegistrationData registrationData) {
		LOG.debug( REGISTERING_NODE );
		NodesHolder.getInstance().registerNode(new ComputingNode(registrationData));
	}
	
	
	/** {@inheritDoc} */
	@Override
	public void registerComputingNode(ComputingNode computingNode) {
		LOG.debug( REGISTERING_NODE );
		NodesHolder.getInstance().registerNode(computingNode);
	}
	

	/** {@inheritDoc} */
	@Override
	public Collection<ComputingNode> retrieveComputingNodes() {
		return NodesHolder.getInstance().retrieveComputingNodes();
	}

	/** {@inheritDoc} */
	@Override
	public void registerNodes(Collection<RegistrationData> registrationDatas) {
		for(RegistrationData registrationData: registrationDatas) {
			registerNode(registrationData);
		}
	}

	/** {@inheritDoc} */
	@Override
	public ComputingNode retrieveRegisteredComputingNode(String key) {
		return NodesHolder.getInstance().retrieveNode(key);
	}

	/** {@inheritDoc} */
	@Override
	public void removeComputingNode(ComputingNode computingNode) {
		NodesHolder.getInstance().removeNode(computingNode);
	}


	
	

}
