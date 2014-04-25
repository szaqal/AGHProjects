package computation.tests;

import java.util.Date;
import java.util.List;

import javax.naming.InitialContext;

import org.apache.cactus.ServletTestCase;

import computation.model.ComputationLogEntry;
import computation.model.PerformedComputation;
import computation.worker.ComputationLogEntryWorker;
import computation.worker.PerformedComputationWorker;

import core.workers.EjbInterfaceType;

/**
 * Worker test.
 * @author  malczyk.pawel@gmail.com
 */
public class ComputationLogWorkerTest extends ServletTestCase {
	
	/**
	 * Constant.
	 */
	private static final String DOLLAR = "$";


	/**
	 * Tested worker.
	 * @uml.property  name="worker"
	 * @uml.associationEnd  
	 */
	private ComputationLogEntryWorker worker;
	
	
	/**
	 * Performed computation worker.
	 * @uml.property  name="computationWorker"
	 * @uml.associationEnd  
	 */
	private PerformedComputationWorker computationWorker;
	
	/**
	 * Performd computation id.
	 */
	private int compId;
	
	/**
	 * Constructor.
	 */
	public ComputationLogWorkerTest() {
		super();
	}
	
	
	/** {@inheritDoc} */
	@Override
	protected void setUp() throws Exception {
		Class<?> workerClass = ComputationLogEntryWorker.class;
		Class<?> workerClass2 = PerformedComputationWorker.class;
		InitialContext context = new InitialContext();
		worker = (ComputationLogEntryWorker) context.lookup(workerClass.getName() + DOLLAR + workerClass.getSimpleName() + EjbInterfaceType.REMOTE );
		computationWorker = (PerformedComputationWorker) context.lookup(workerClass2.getName() + DOLLAR + workerClass2.getSimpleName()+EjbInterfaceType.REMOTE);
	}
	
	
	/**
	 * Tests if worker could be located.
	 */
	public void testWorkerFound() {
		assertNotNull(worker);
		assertNotNull(computationWorker);
		
	}
	
	
	/**
	 * Stores data.
	 */
	public void testStore() {
		List<PerformedComputation> computation = computationWorker.retrievePerformedComputations(null);
		PerformedComputation comp = computation.iterator().next();
		assertNotNull(comp);
		compId = comp.getUniqueId();
		ComputationLogEntry logEntry = new ComputationLogEntry();
		logEntry.setCreateTime(new Date());
		logEntry.setMessage("Test message");
		logEntry.setPerformedComputation(comp);
		worker.store(logEntry);
	}
	
	/**
	 * Retrieves data.
	 */
	public void testRetrieve() {
		assertNotNull(worker.retrieveLogs(compId, null));
	}

}
