package core.tests;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.naming.InitialContext;

import net.jmatrix.eproperties.EProperties;

import org.apache.cactus.ServletTestCase;

import core.model.ApplicationConfigurationFile;
import core.workers.ApplicationConfigurationFilesWorker;
import core.workers.EjbInterfaceType;

/**
 * Worker test.
 * @author malczyk.pawel@gmail.com
 *
 */
public class ApplicationConfigurationFilesWorkerTest extends ServletTestCase {
	
	/**
	 * Worker.
	 */
	private ApplicationConfigurationFilesWorker worker;
	
	/**
	 * Constructor.
	 */
	public ApplicationConfigurationFilesWorkerTest() {
		super();
	}
	
	/** {@inheritDoc} */
	@Override
	protected void setUp() throws Exception {
		Class<?> workerClass = ApplicationConfigurationFilesWorker.class;
		InitialContext context = new InitialContext();
		worker = (ApplicationConfigurationFilesWorker) context.lookup(workerClass.getName() + "$" + workerClass.getSimpleName() + EjbInterfaceType.REMOTE );
	}
	
	/**
	 * Tests if worker could be located.
	 */
	public void testWorkerFound() {
		assertNotNull(worker);
	}
	
	/**
	 * Test method.
	 */
	public void testStore() {
		EProperties props = new EProperties();
		String fileName="computationStart.xsd"; 
		try {
			props.load(new FileInputStream(new File(System.getenv("TASK_HOME") + "/config.properties")));

			File file = new File(props.getProperty("test.data")+"/"+fileName);
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
				System.out.println("Error reading" + file.getName());
			}

			long keyId = worker.storeComputationStartValidation(bytes, fileName).getUniqueId();
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
		ApplicationConfigurationFile file = worker.retrieveStartValidation();
		assertNotNull(file);
	}

}
