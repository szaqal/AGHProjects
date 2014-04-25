package auth.tests;

import javax.naming.InitialContext;

import org.apache.cactus.ServletTestCase;

import auth.RegUserWorker;
import auth.model.Registration;
import auth.model.User;
import core.workers.EjbInterfaceType;

/**
 * Test for  {@link RegUserWorker} .
 * @author  <a href="mailto:malczyk.pawel@gmail.com>malczyk.pawel@gmail.com</a>
 */
public class RegUserWorkerTest extends ServletTestCase {
	
	
	/**
	 * Tested worker.
	 * @uml.property  name="worker"
	 * @uml.associationEnd  
	 */
	private RegUserWorker worker;
	
	
	/**
	 * Constructor.
	 */
	public RegUserWorkerTest() { 
		super();
	}	
	
	/** {@inheritDoc} */
	@Override
	protected void setUp() throws Exception {
		Class<?> workerClass = RegUserWorker.class;
		InitialContext context = new InitialContext();
		worker = (RegUserWorker) context.lookup(workerClass.getName() + "$" + workerClass.getSimpleName() + EjbInterfaceType.REMOTE );
	}
	
	/**
	 * Tests if worker could be located.
	 */
	public void testWorkerFound() {
		assertNotNull(worker);
	}

	
	/**
	 * Tests user save.
	 */
	public void testStoreUser() {
		for(User user:UsersTestsHelper.getTestUsers()) {
			int safeResult = worker.storeUser(user);
			assertTrue(safeResult>0);
			Registration reg = worker.getByUser(safeResult);
			assertTrue(worker.completeRegistration(reg.getUniqueId()));
			
		}
	}
	

}
