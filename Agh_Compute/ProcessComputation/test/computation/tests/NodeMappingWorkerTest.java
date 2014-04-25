package computation.tests;

import javax.naming.InitialContext;

import org.apache.cactus.ServletTestCase;

import computation.model.NodeMapping;
import computation.worker.NodeMappingWorker;

import core.workers.EjbInterfaceType;

/**
 * WorkerTest.
 * @author  malczyk.pawel@gmail.com
 */
public class NodeMappingWorkerTest extends ServletTestCase {
	
	/** Constant. */
	private static final int OWNER_ID_TEST = 1;

	/** Constant. */
	private static final String DOLLAR = "$";
	
	/**
	 * Node mapping woerkker.
	 * @uml.property  name="nodeMappingWorker"
	 * @uml.associationEnd  
	 */
	private NodeMappingWorker nodeMappingWorker;
	
	
	/**
	 * Constructor.
	 */
	public NodeMappingWorkerTest() {
		super();
	}
	
	
	/** {@inheritDoc} */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		Class<?> workerClass = NodeMappingWorker.class;
		InitialContext context = new InitialContext();
		nodeMappingWorker = (NodeMappingWorker) context.lookup(workerClass.getName() + DOLLAR + workerClass.getSimpleName() + EjbInterfaceType.REMOTE );
	}
	
	
	/**
	 * Tests if worker could be located.
	 */
	public void testWorkerFound() {
		assertNotNull(nodeMappingWorker);
	}
	
	/**
	 * Testing method.
	 */
	public void testAddMapping() {
		NodeMapping mapping = new NodeMapping();
		mapping.setOwnerId(OWNER_ID_TEST);
		mapping.setMapping("node01");
		mapping.setNode("malczyk.kicks-ass.net");
		int id = nodeMappingWorker.store(mapping).getUniqueId();
		assertTrue(id>0);
	}
	
	/**
	 * Tests count.
	 */
	public void testCount() {
		Long mappingsCount = nodeMappingWorker.retrieveByOwnerCount(OWNER_ID_TEST);
		assertEquals(Long.valueOf(1), mappingsCount);
		
	}

}
