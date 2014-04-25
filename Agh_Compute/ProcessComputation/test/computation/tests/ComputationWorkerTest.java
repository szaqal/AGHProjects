package computation.tests;

import java.util.List;

import javax.naming.InitialContext;

import org.apache.cactus.ServletTestCase;

import computation.model.Computation;
import computation.model.ComputationPackage;
import computation.worker.ComputationPackageWorker;
import computation.worker.ComputationWorker;

import core.workers.EjbInterfaceType;


/**
 * Test for  {@link ComputationWorker} .
 * @author  <a href="mailto:malczyk.pawel@gmail.com>malczyk.pawel@gmail.com</a>
 */
public class ComputationWorkerTest extends ServletTestCase {
	
	/** Constant. */
	private static final String DOLLAR = "$";
	/**
	 * Computation worker.
	 * @uml.property  name="computationWorker"
	 * @uml.associationEnd  
	 */
	private ComputationWorker computationWorker;
	
	/**
	 * Constructor.
	 */
	public ComputationWorkerTest() { 
		super();
	}
	
	/** {@inheritDoc} */
	@Override
	protected void setUp() throws Exception {
		Class<?> workerClass = ComputationWorker.class;
		InitialContext context = new InitialContext();
		computationWorker = (ComputationWorker) context.lookup(workerClass.getName() + DOLLAR + workerClass.getSimpleName() + EjbInterfaceType.REMOTE );
	}
	
	
	/**
	 * Tests if worker could be located.
	 */
	public void testWorkerFound() {
		assertNotNull(computationWorker);
	}
	
	/**
	 * Test.
	 * @throws Exception 
	 */
	public void testcreateComputation() throws Exception {
		InitialContext context = new InitialContext();
		Class<?> workerClass = ComputationPackageWorker.class;
		ComputationPackageWorker packageworker = (ComputationPackageWorker) context.lookup(workerClass.getName() 
				+ DOLLAR + workerClass.getSimpleName() + EjbInterfaceType.REMOTE );
		assertNotNull(packageworker);
		List<ComputationPackage> packages = packageworker.retrieveComputationPackages();
		
		ComputationPackage testPackage = null;
		for(ComputationPackage pack : packages) {
			if(pack.getTitle().equals("GeneticCore.jar")){
				testPackage = pack;
				break;
			}
		}
		int createComputation = computationWorker.createComputation(testPackage);
		assertTrue(createComputation>0);
		
	}
	
	/**
	 * Test.
	 */
	public void testRetrieveComputations() {
		List<Computation> computations = computationWorker.retrieveComputations(null);
		assertNotNull(computations);
		assertEquals(1, computations.size());
	}
	
	/**
	 * Test.
	 */
	public void testComputationCount() {
		Long count = computationWorker.retrieveComputationsCount();
		assertEquals(Long.valueOf(1l), count);
	}
	
	/**
	 * Test method.
	 */
	public void testTaskForComputation() {
		Computation comp = computationWorker.retrieveComputations(null).iterator().next();
		computationWorker.retieveTasksForComputation(comp.getUniqueId());
	}


}
