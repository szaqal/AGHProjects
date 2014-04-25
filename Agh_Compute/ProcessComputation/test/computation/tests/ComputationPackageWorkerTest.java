package computation.tests;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.naming.InitialContext;

import net.jmatrix.eproperties.EProperties;

import org.apache.cactus.ServletTestCase;

import computation.model.ComputationPackage;
import computation.worker.ComputationPackageWorker;

import core.workers.EjbInterfaceType;

/**
 * Test ComputationPackageWorkerTest.
 * @author  <a href="mailto:malczyk.pawel@gmail.com>malczyk.pawel@gmail.com</a>
 */
public class ComputationPackageWorkerTest extends ServletTestCase {
	
	/** Environment variable. */
	private static final String TASK_HOME = "TASK_HOME";

	/** configuration file name. */
	private static final String CONFIG_PROPERTIES = "/config.properties";

	/** Config key. */
	private static final String TEST_DATA = "test.data";

	/** MIME type of jar. */
	private static final String JAR_MIME = "application/x-java-application";

	/** Error message. */
	private static final String COULD_NOT_COMPLETELY_READ_FILE = "Could not completely read file ";
	
	/**
	 * Tested worker.
	 * @uml.property  name="worker"
	 * @uml.associationEnd  
	 */
	private ComputationPackageWorker worker;
	
	/**
	 * 
	 * Constructor.
	 */
	public ComputationPackageWorkerTest() { 
		super();
	}
	
	/** {@inheritDoc} */
	@Override
	protected void setUp() throws Exception {
		Class<?> workerClass = ComputationPackageWorker.class;
		InitialContext context = new InitialContext();
		worker = (ComputationPackageWorker) context.lookup(workerClass.getName() + "$" + workerClass.getSimpleName() + EjbInterfaceType.REMOTE );
	}
	
	
	/**
	 * Tests if worker could be located.
	 */
	public void testWorkerFound() {
		assertNotNull(worker);
	}

	/**
	 * Uploads computation package.
	 */
	public void testSaveComputationPackage() {
		String fileName="/computation.xml"; 
		try {
			EProperties props =new EProperties();
			props.load(new FileInputStream(new File(System.getenv(TASK_HOME) + CONFIG_PROPERTIES)));
			File file = new File(props.getProperty(TEST_DATA) + fileName);
			System.out.println(file.getAbsolutePath());
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

			
			int keyId = worker.saveComputationPackage(bytes, fileName, fileName, JAR_MIME).getUniqueId();
			System.out.println("KEY ID "+ keyId);
			ComputationPackage saved = worker.retrieveComputationPackage(keyId);
			assertNotNull(saved);
			
			is.close();
		} catch (IOException e) {
			assertNotNull(null);
			e.printStackTrace();
		}
	}
	
	/**
	 * There should be one item already added.
	 */
	public void testComputationPackageCount() {
		List<ComputationPackage> packages = worker.retrieveComputationPackages();
		assertNotNull(packages);
		assertEquals(1, packages.size());
	}
	
	/**
	 * Tests if uploaded package is valid.
	 */
	public void testPackageValid() {
		List<ComputationPackage> packages = worker.retrieveComputationPackages();
		assertNotNull(packages);
		assertEquals(1, packages.size());
		//jpg
		ComputationPackage invalidPackage = packages.iterator().next();
		boolean result = worker.isPackageValid(invalidPackage);
		assertEquals(false, result);
	}
	
	/**
	 * Another test, this time package is valid.
	 */
	public void testPackageValid2() {
		String filename="GeneticCore.jar";
		
		try {

			EProperties props =new EProperties();
			props.load(new FileInputStream(new File(System.getenv(TASK_HOME) + CONFIG_PROPERTIES)));
			File file = new File(props.getProperty(TEST_DATA) + "/" +filename);
			
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

			
			int keyId = worker.saveComputationPackage(bytes, filename, filename, JAR_MIME).getUniqueId();
			ComputationPackage saved = worker.retrieveComputationPackage(keyId);
			assertNotNull(saved);
			
			boolean isValid = worker.isPackageValid(saved);
			assertEquals(true, isValid);
			
			is.close();
		} catch (IOException e) {
			assertNotNull(null);
			e.printStackTrace();
		}
	}
	
	/**
	 * Test method.
	 */
	public void testRetrieveConfigFromPackage() {
		List<ComputationPackage> packages = worker.retrieveComputationPackages();
		assertEquals(2, packages.size());
		byte [] data=null;
		for(ComputationPackage pack : packages) {
			String fileName = pack.getContent().getFileName();
			System.out.println(fileName);
			if(fileName.contains(".jar")) {
				data = worker.retrieveConfigFromPackage(pack.getUniqueId());
			}
		}
		
		assertNotNull(data);
	}
	


}
