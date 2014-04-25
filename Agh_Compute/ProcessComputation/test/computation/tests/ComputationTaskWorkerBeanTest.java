package computation.tests;

import java.util.Collection;
import java.util.List;

import javax.naming.InitialContext;

import org.apache.cactus.ServletTestCase;

import computation.model.Computation;
import computation.model.ComputationTask;
import computation.model.ComputationTaskInput;
import computation.worker.ComputationTaskWorker;
import computation.worker.ComputationWorker;

import core.workers.EjbInterfaceType;

/**
 * Worker test.
 * @author  malczyk.pawel@gmail.com
 */
public class ComputationTaskWorkerBeanTest extends ServletTestCase {
	
	/** Constant. */
	private static final int EXPECED_TASK_COUNT = 7;
	
	/** Constant. */
	private static final String DOLLAR = "$";
	/**
	 * Computation worker.
	 * @uml.property  name="computationWorker"
	 * @uml.associationEnd  
	 */
	private ComputationWorker computationWorker;
	
	/**
	 * Computation task worker.
	 * @uml.property  name="computationTaskWorker"
	 * @uml.associationEnd  
	 */
	private ComputationTaskWorker computationTaskWorker;
	
	/**
	 * Constructor.
	 */
	public ComputationTaskWorkerBeanTest() {
		super();
	}
	
	/** {@inheritDoc} */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		Class<?> workerClass = ComputationWorker.class;
		InitialContext context = new InitialContext();
		computationWorker = (ComputationWorker) context.lookup(workerClass.getName() + DOLLAR + workerClass.getSimpleName() + EjbInterfaceType.REMOTE );
		
		workerClass=ComputationTaskWorker.class;
		computationTaskWorker = (ComputationTaskWorker) context.lookup(workerClass.getName() + DOLLAR + workerClass.getSimpleName() + EjbInterfaceType.REMOTE );
	}
	
	
	/**
	 * Test.
	 */
	public void testWorkerFound() {
		assertNotNull(computationWorker);
		assertNotNull(computationTaskWorker);
	}
	
	/**
	 * Test.
	 */
	public void testTasksByComputation() {
		Computation computation = computationWorker.retrieveComputations(null).iterator().next();
		assertNotNull(computation);
		List<ComputationTask> tasks = computationTaskWorker.getByComputation(computation.getUniqueId());
		assertEquals(EXPECED_TASK_COUNT, tasks.size());
	}
	
	/**
	 * Test.
	 */
	public void testInputus() {
		Computation computation = computationWorker.retrieveComputations(null).iterator().next();
		assertNotNull(computation);
		Collection<ComputationTaskInput> tasks = computationTaskWorker.firstTaskInputs(computation.getUniqueId());
		assertEquals(1, tasks.size());
	}

}
