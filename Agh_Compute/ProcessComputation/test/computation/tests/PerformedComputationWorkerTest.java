package computation.tests;

import java.util.Date;
import java.util.List;

import javax.naming.InitialContext;

import org.apache.cactus.ServletTestCase;

import computation.model.Computation;
import computation.model.PerformedComputation;
import computation.worker.ComputationWorker;
import computation.worker.PerformedComputationWorker;

import core.workers.EjbInterfaceType;

/**
 * Test.
 * @author  malczyk.pawel@gmail.com
 */
public class PerformedComputationWorkerTest extends ServletTestCase {

	/** Constant. */
	private static final String DOLLAR = "$";
	
	
	/**
	 * {@link PerformedComputationWorker}  .
	 * @uml.property  name="performedComputationWorker"
	 * @uml.associationEnd  
	 */
	private PerformedComputationWorker performedComputationWorker;
	
	/**
	 * {@link ComputationWorker}  .
	 * @uml.property  name="computationWorker"
	 * @uml.associationEnd  
	 */
	private ComputationWorker computationWorker;
	
	
	/**
	 * Constructor.
	 */
	public PerformedComputationWorkerTest() {
		super();
	}
	
	/** {@inheritDoc} */
	@Override
	protected void setUp() throws Exception {
		Class<?> workerClass = PerformedComputationWorker.class;
		InitialContext context = new InitialContext();
		performedComputationWorker = (PerformedComputationWorker) context.lookup(workerClass.getName() + DOLLAR
						+ workerClass.getSimpleName() + EjbInterfaceType.REMOTE);
		
		
		Class<?> compWorkerClass = ComputationWorker.class;
		computationWorker = (ComputationWorker) context.lookup(compWorkerClass.getName() + DOLLAR
				+ compWorkerClass.getSimpleName() + EjbInterfaceType.REMOTE);
	}
	
	/**
	 * Test method.
	 * @throws Exception exception
	 */
	public void testWorkerFound() throws Exception {
		assertNotNull(performedComputationWorker);
		assertNotNull(computationWorker);
	}
	
	/**
	 * Test method.
	 * @throws Exception exception
	 */
	public void testCreatePerformedComputation() throws Exception {
		
		List<Computation> computations = computationWorker.retrieveComputations(null);
		assertNotNull(computations);		
		PerformedComputation performedComputation = new PerformedComputation();
		performedComputation.setStartDate(new Date());
		performedComputation.setEndDate(new Date());
		Computation computation = computations.iterator().next();
		assertNotNull(computation);
		performedComputation.setComputation(computation);
		int key = performedComputationWorker.savePerformedComputation(performedComputation).getUniqueId();
		assertTrue(key > 0);
	}
	
}
