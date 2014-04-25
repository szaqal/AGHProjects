package computation.tests;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.naming.InitialContext;

import net.jmatrix.eproperties.EProperties;

import org.apache.cactus.ServletTestCase;

import computation.worker.ComputationConfgurationWorker;

import core.workers.EjbInterfaceType;

/**
 * Worker test.
 * @author  malczyk.pawel@gmail.com
 */
public class ComputationConfigurationWorkerTest extends ServletTestCase {
	
	/** Error message. */
	private static final String COULD_NOT_COMPLETELY_READ_FILE = "Could not completely read file ";
	
	/**
	 * Tested worker.
	 * @uml.property  name="computationConfgurationWorker"
	 * @uml.associationEnd  
	 */
	private ComputationConfgurationWorker computationConfgurationWorker;
	
	/**
	 * Constructor.
	 */
	public ComputationConfigurationWorkerTest() {
		super();
	}
	
	/** {@inheritDoc} */
	@Override
	protected void setUp() throws Exception {
		Class<?> workerClass = ComputationConfgurationWorker.class;
		InitialContext context = new InitialContext();
		computationConfgurationWorker = (ComputationConfgurationWorker) context.lookup(workerClass.getName() + "$" + workerClass.getSimpleName() + EjbInterfaceType.REMOTE );
	}
	
	/**
	 * Tests if worker could be located.
	 */
	public void testWorkerFound() {
		assertNotNull(computationConfgurationWorker);
	}
	
	/**
	 * Test method.
	 */
	public void testSave() {
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

			
			long keyId = computationConfgurationWorker.store(bytes, fileName).getUniqueId();
			assertNotNull(keyId);
			
			is.close();
		} catch (IOException e) {
			assertNotNull(null);
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Test method.
	 */
	public void testRetrieve() {
		assertEquals(1, computationConfgurationWorker.retrieveComputationConfigurations().size());
	}
	
	
	/**
	 * Test method.
	 */
	public void testCount() {
		assertEquals(Long.valueOf(1l), computationConfgurationWorker.retrieveComputationConfigurationsCount());
	}
	
	

}
