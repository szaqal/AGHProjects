package core.tests;

import java.util.HashMap;
import java.util.Map;

import javax.naming.InitialContext;

import org.apache.cactus.ServletTestCase;

import core.workers.EjbInterfaceType;
import core.workers.TemplateWorker;

/**
 * Worker test.
 * @author  malczyk.pawel@gmail.com
 */
public class TemplateWorkerTest extends ServletTestCase {

	/**
	 * Tested worker.
	 * @uml.property  name="templateWorker"
	 * @uml.associationEnd  
	 */
	private TemplateWorker templateWorker;
	
	
	/**
	 * Constructor.
	 */
	public TemplateWorkerTest() {
		super();
	}
	
	/** {@inheritDoc} */
	@Override
	protected void setUp() throws Exception {
		Class<?> workerClass = TemplateWorker.class;
		InitialContext context = new InitialContext();
		templateWorker = (TemplateWorker) context.lookup(workerClass.getName() + "$" + workerClass.getSimpleName() + EjbInterfaceType.REMOTE );
	}
	
	/**
	 * Tests if worker could be located.
	 */
	public void testWorkerFound() {
		assertNotNull(templateWorker);
	}
	
	
	/**
	 * Test create message from template.
	 */
	public void testCreate() {
		Map<String, String> params = new HashMap<String, String>();
		params.put("computationResultUrl", "http://localhost");
		String result = templateWorker.create(params, "email.vm");
		assertNotNull(result);
		assertEquals(true, result.length() > 0);
	}
	
	
	
}
