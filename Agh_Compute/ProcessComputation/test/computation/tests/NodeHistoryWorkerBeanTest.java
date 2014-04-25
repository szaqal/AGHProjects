package computation.tests;

import java.util.Collection;

import javax.naming.InitialContext;

import org.apache.cactus.ServletTestCase;

import computation.model.ComputationNodeHistory;
import computation.worker.NodeHistoryWorker;
import core.workers.EjbInterfaceType;

/**
 * Worker test.
 * @author  malczyk.pawel@gmail.com
 */
public class NodeHistoryWorkerBeanTest extends ServletTestCase {
	
	/**
	 * Localhost address.
	 */
	private static final String LOCALHOST = "127.0.0.1";

	/** Constant. */
	private static final String DOLLAR = "$";
	
	/**
	 * Node history worker.
	 * @uml.property  name="nodeHistoryWorker"
	 * @uml.associationEnd  
	 */
	private NodeHistoryWorker nodeHistoryWorker;
	
	/**
	 * Constructor.
	 */
	public NodeHistoryWorkerBeanTest() {
		super();
	};
	
	
	/** {@inheritDoc} */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		Class<?> workerClass = NodeHistoryWorker.class;
		InitialContext context = new InitialContext();
		nodeHistoryWorker = (NodeHistoryWorker) context.lookup(workerClass.getName() + DOLLAR + workerClass.getSimpleName() + EjbInterfaceType.REMOTE );
	}
	
	/**
	 * Tests if worker could be located.
	 */
	public void testWorkerFound() {
		assertNotNull(nodeHistoryWorker);
	}
	
	/**
	 * Tests item save.
	 */
	public void testSave() {
		ComputationNodeHistory computationNodeHistory = new ComputationNodeHistory();
		computationNodeHistory.setNodeIp(LOCALHOST);
		nodeHistoryWorker.storeHistoryItem(computationNodeHistory);
	}
	
	/**
	 * Tests item retrieve.
	 */
	public void testRetrieve() {
		Collection<ComputationNodeHistory> items = nodeHistoryWorker.retrieveHistory(LOCALHOST);
		assertEquals(1, items.size());
	}

}
