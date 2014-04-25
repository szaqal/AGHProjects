package core.tests;

import javax.naming.InitialContext;

import org.apache.cactus.ServletTestCase;

import core.workers.EjbInterfaceType;
import core.workers.TransformWorker;

/**
 * Worker test.
 * @author  malczyk.pawel@gmail.com
 */
public class TransformWorkerTest extends ServletTestCase {
	
	/**
	 * Tested worker.
	 * @uml.property  name="transformWorker"
	 * @uml.associationEnd  
	 */
	private TransformWorker transformWorker;
	
	/**
	 * Constructor.
	 */
	public TransformWorkerTest() {
		super();
	}
	
	/** {@inheritDoc}*/
	@Override
	protected void setUp() throws Exception {
		Class<?> workerClass = TransformWorker.class;
		InitialContext context = new InitialContext();
		transformWorker = (TransformWorker) context.lookup(workerClass.getName() + "$" + workerClass.getSimpleName() + EjbInterfaceType.REMOTE );
	}
	
	/**
	 * Tests if worker could be located.
	 */
	public void testWorkerFound() {
		assertNotNull(transformWorker);
	}

}
