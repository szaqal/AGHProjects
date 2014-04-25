package computation.tests;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.naming.InitialContext;

import net.jmatrix.eproperties.EProperties;

import org.apache.cactus.ServletTestCase;

import computation.model.ValidationSchema;
import computation.worker.ValidationWorker;

import core.workers.EjbInterfaceType;

/**
 * Worker test.
 * @author  malczyk.pawel@gmail.com
 */
public class ValidationWorkerTest extends ServletTestCase {
	
	/** Error message. */
	private static final String COULD_NOT_COMPLETELY_READ_FILE = "Could not completely read file ";
	
	/** Constant. */
	private static final String DOLLAR = "$";
	
	/**
	 * @uml.property  name="validationWorker"
	 * @uml.associationEnd  
	 */
	private ValidationWorker validationWorker;
	
	/**
	 * constructor.
	 */
	public ValidationWorkerTest() {
		super();
	}
	
	/** {@inheritDoc}*/
	@Override
	protected void setUp() throws Exception {
		Class<?> workerClass = ValidationWorker.class;
		InitialContext context = new InitialContext();
		validationWorker = (ValidationWorker) context.lookup(workerClass.getName() + DOLLAR
				+ workerClass.getSimpleName() + EjbInterfaceType.REMOTE);
	}
	
	
	/**
	 * Test method.
	 * @throws Exception exception.
	 */
	public void testWorkerFound() throws Exception {
		assertNotNull(validationWorker);
	}
	
	/**
	 * Test method.
	 * @throws Exception exception.
	 */
	public void testStore() throws Exception {
		String fileName="computation.xml"; 
		try {

			EProperties props = new EProperties();
			props.load(new FileInputStream(new File(System.getenv("TASK_HOME") + "/config.properties")));
			File file = new File(props.getProperty("test.data") + "/" +fileName);
			InputStream is = new FileInputStream(file);

			long length = file.length();

			byte[] bytes = new byte[(int) length];

			int offset = 0;
			int numRead = 0;
			while (offset < bytes.length
					&& (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
				offset += numRead;
			}

			if (offset < bytes.length) {
				System.out.println(COULD_NOT_COMPLETELY_READ_FILE
						+ file.getName());
			}

			
			int keyId = validationWorker.store("test validation file", fileName, bytes).getUniqueId();
			assertTrue(keyId>0);
			
			is.close();
		} catch (IOException e) {
			assertNotNull(null);
			e.printStackTrace();
		}
	}
	
	/**
	 * Test method.
	 * @throws Exception exception
	 */
	public void testRetrieve() throws Exception {
		ValidationSchema schema = validationWorker.retrieveSchemas(null).iterator().next();
		assertNotNull(schema);
		schema = validationWorker.retrieveSchema(schema.getUniqueId());
		assertNotNull(schema);
		
	}
	
	/**
	 * Test method.
	 */
	public void validateConfigutaion() {
		//XXX: implement
		assertTrue(true);
	}

}
