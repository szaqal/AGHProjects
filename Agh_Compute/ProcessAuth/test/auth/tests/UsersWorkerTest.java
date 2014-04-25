package auth.tests;

import javax.naming.InitialContext;

import org.apache.cactus.ServletTestCase;

import auth.UsersWorker;
import auth.model.User;
import core.workers.EjbInterfaceType;

/**
 * Test for users worker.
 * @author  <a href="mailto:malczyk.pawel@gmail.com>malczyk.pawel@gmail.com</a>
 */
public class UsersWorkerTest extends ServletTestCase {
	
	
	/** Constant. */
	public static final int EXPECTED_USER_COUNT  = 3;
	
	/**
	 * Tested worker.
	 * @uml.property  name="worker"
	 * @uml.associationEnd  
	 */
	private UsersWorker worker;
	
	/**
	 * Constructor.
	 */
	public UsersWorkerTest() {
		super();
	}
	
	/** {@inheritDoc} */
	@Override
	protected void setUp() throws Exception {
		Class<?> workerClass = UsersWorker.class;
		InitialContext context = new InitialContext();
		worker = (UsersWorker) context.lookup(workerClass.getName() + "$" + workerClass.getSimpleName() + EjbInterfaceType.REMOTE );
	}
	
	
	/**
	 * Tests if worker could be located.
	 */
	public void testWorkerFound() {
		assertNotNull(worker);
	}
	
	/**
	 * Tests user authentication.
	 */
	public void testUserAuthenticate() {
		User user = worker.authenticate("testl@gmail.com", "test!");
		assertNull(user);
		user = worker.authenticate("malczyk.pawel@gmail.com", "malczyk123");
		assertNotNull(user);
	}
	
	/**
	 * Tests users count.
	 */
	public void testUsersCount() {
		Long result = worker.retrieveUsersCount();
		assertEquals(Long.valueOf(EXPECTED_USER_COUNT), result);
	}
	
	/**
	 * Tests user deletion.
	 */
	public void testUserDelete() {
		Long coutnBefore = worker.retrieveUsersCount();
		User user = worker.authenticate("andrzej.lepper@testmail.pl", "lepper123");
		worker.deleteUser(user.getUniqueId());
		Long countAfter = worker.retrieveUsersCount();
		assertEquals(--coutnBefore, countAfter);
	}
	
	/**
	 * Tests user edit data.
	 */
	public void testUserUpdate() {
		String passwd = "hajzer123";
		String email = "zygmunt.hajzer@testmail.pl";
		User user = worker.authenticate(email, passwd);
		assertNotNull(user);
		String email2 = "zygmunt.hajzer@testmail2.pl";
		user.setEmail(email2);
		user.setPassword(passwd);
		worker.saveUser(user);
		user = worker.authenticate(email2, passwd);
		assertNotNull(user);
	}
		
	
}
