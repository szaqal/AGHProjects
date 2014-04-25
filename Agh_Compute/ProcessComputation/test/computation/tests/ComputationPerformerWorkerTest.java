package computation.tests;

import javax.naming.InitialContext;

import org.apache.cactus.ServletTestCase;

import computation.worker.ComputationPerformerWorker;
import computation.worker.ComputationWorker;

import core.workers.EjbInterfaceType;

/**
 * Tests computation performer.
 * @author  malczyk.pawel@gmail.com
 */
public class ComputationPerformerWorkerTest extends ServletTestCase {
	

	/**
	 * Constant.
	 */
	private static final String SEPARATOR = "$";

	/**
	 * Tested worker.
	 * @uml.property  name="computationPerformerworker"
	 * @uml.associationEnd  
	 */
	private ComputationPerformerWorker computationPerformerworker;
	
	/**
	 * Computation package worker.
	 * @uml.property  name="computationWorker"
	 * @uml.associationEnd  
	 */
	private ComputationWorker computationWorker;
	
	
	/**
	 * Constructor.
	 */
	public ComputationPerformerWorkerTest() {
		super();
	}
	
	/** {@inheritDoc} */
	@Override
	protected void setUp() throws Exception {
		Class<?> performerWorkerClass = ComputationPerformerWorker.class;
		Class<?> computationWorkerClass = ComputationWorker.class;
		InitialContext context = new InitialContext();
		computationPerformerworker = (ComputationPerformerWorker) context.lookup(performerWorkerClass.getName() + SEPARATOR + 
				performerWorkerClass.getSimpleName() + EjbInterfaceType.REMOTE );
		computationWorker =  (ComputationWorker) context.lookup(computationWorkerClass.getName() + SEPARATOR + 
				computationWorkerClass.getSimpleName() + EjbInterfaceType.REMOTE );
	}
	
	
	/**
	 * Initial test.
	 */
	public void testWorkerFound() {
		assertNotNull(computationPerformerworker);
		assertNotNull(computationWorker);
	}
	
	/**
	 * Test method.
	 */
	public void testCreateRunnableComputation() {
//		assertEquals(1l, computationWorker.retrieveComputationsCount().longValue());
//		Computation computation = computationWorker.retrieveComputations(null).iterator().next();
//		assertNotNull(computation);
//		runableComputation = computationPerformerworker.createRunnableComputation(computation.getUniqueId());
//		assertNotNull(runableComputation);
	}
	
	/**
	 * tests order of computation tasks.
	 */
	public void testComputationsOrder(){
//		Computation computation = computationWorker.retrieveComputations(null).iterator().next();
//		assertNotNull(computation);
//		runableComputation = computationPerformerworker.createRunnableComputation(computation.getUniqueId());
//		assertNotNull(runableComputation);
//		List<ComputationTask> tasks = runableComputation.getTasks();
//		assertNotNull(tasks);
//		ComputationTask task = tasks.get(0);
//		assertEquals("id00", task.getTaskId());
//		task = tasks.get(1);
//		assertEquals("id01", task.getTaskId());
//		task = tasks.get(2);
//		assertEquals("id02", task.getTaskId());
//		task = tasks.get(DIVISION);
//		assertEquals("id03", task.getTaskId());
	}
	
	/**
	 * Tests actual computation.
	 */
	public void testDoComputation() {
//		Computation computation = computationWorker.retrieveComputations(null).iterator().next();
//		assertNotNull(computation);
//		Map<String, Number> initParams = new HashMap<String, Number>();
//		initParams.put("addInput1", Integer.valueOf(5));
//		initParams.put("addInput2", Integer.valueOf(3));
//		initParams.put("subInput1", Integer.valueOf(5));
//		initParams.put("subInput2", Integer.valueOf(3));
//		initParams.put("mulInput1", Integer.valueOf(5));
//		initParams.put("mulInput2", Integer.valueOf(3));
//		initParams.put("divInput1", Integer.valueOf(10));
//		initParams.put("divInput2", Integer.valueOf(5));
//		computationPerformerworker.setup(initParams);
//		ComputationResult<Integer> result = computationPerformerworker.doComputation(Integer.class, computation.getUniqueId(), null);
//		assertNotNull(result);
//		assertEquals(Integer.valueOf(2), result.getResult());
		
	}
	
}
