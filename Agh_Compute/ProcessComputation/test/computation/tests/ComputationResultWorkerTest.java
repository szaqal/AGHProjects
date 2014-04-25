package computation.tests;

import java.util.Date;
import java.util.List;

import javax.naming.InitialContext;

import org.apache.cactus.ServletTestCase;

import auth.UsersWorker;
import auth.model.User;

import computation.model.ComputationResult;
import computation.model.PerformedComputation;
import computation.worker.ComputationResultWorker;
import computation.worker.PerformedComputationWorker;

import core.workers.EjbInterfaceType;

/**
 * Worker Test ( {@link ComputationResultWorker} ).
 * @author  malczyk.pawel@gmail.com
 */
public class ComputationResultWorkerTest extends ServletTestCase {
	
	/**
	 * Constant.
	 */
	private static final int ARR_SIZE = 10;


	/** Constant. */
	private static final String DOLLAR = "$";

	
	/**
	 * Tested worker.
	 * @uml.property  name="worker"
	 * @uml.associationEnd  
	 */
	private ComputationResultWorker worker;
	
	/**
	 * {@link PerformedComputationWorker}  .
	 * @uml.property  name="performedComputationWorker"
	 * @uml.associationEnd  
	 */
	private PerformedComputationWorker performedComputationWorker;
	
	/**
	 * User worker.
	 * @uml.property  name="userWorker"
	 * @uml.associationEnd  
	 */
	private UsersWorker userWorker;
	
	/**
	 * Constructor.
	 */
	public ComputationResultWorkerTest() {
		super();
	}
	
	/** {@inheritDoc} */
	@Override
	protected void setUp() throws Exception {
		Class<?> workerClass = ComputationResultWorker.class;
		Class<?> userWorkerClass = UsersWorker.class;
		InitialContext context = new InitialContext();
		worker = (ComputationResultWorker) context.lookup(workerClass.getName() + DOLLAR
						+ workerClass.getSimpleName() + EjbInterfaceType.REMOTE);
		
		userWorker = (UsersWorker) context.lookup(userWorkerClass.getName() + DOLLAR 
						+ userWorkerClass.getSimpleName() + EjbInterfaceType.REMOTE);
		
		Class<?> workerClass2 = PerformedComputationWorker.class;
		performedComputationWorker = (PerformedComputationWorker) context.lookup(workerClass2.getName() + DOLLAR
				+ workerClass2.getSimpleName() + EjbInterfaceType.REMOTE); 
	}
	
	/**
	 * Test method.
	 * @throws Exception exception
	 */
	public void testWorkerFound() throws Exception {
		assertNotNull(worker);
		assertNotNull(performedComputationWorker);
		assertNotNull(userWorker);
	}
	
	/**
	 * Test method.
	 * @throws Exception exception
	 */
	public void testSave() throws Exception {
		ComputationResult result= new ComputationResult(new byte[ARR_SIZE]);
		PerformedComputation performedComputation = performedComputationWorker.retrievePerformedComputations(null).iterator().next();
		result.setPerformedComputation(performedComputation);
		User user = userWorker.retrieveUsers(null).iterator().next();
		result.setCreateDate(new Date());
		
		assertNotNull(worker.saveResult(result, user.getUniqueId()));
	}
	
	/**
	 * Test method.
	 * @throws Exception exception
	 */
	public void testRetrieve() throws Exception {
		List<ComputationResult> results = worker.retrieveResults();		
		assertEquals(1, results.size());
	}
	
}
