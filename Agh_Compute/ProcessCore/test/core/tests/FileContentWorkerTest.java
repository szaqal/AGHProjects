package core.tests;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.naming.InitialContext;

import org.apache.cactus.ServletTestCase;

import core.model.FileContent;
import core.workers.EjbInterfaceType;
import core.workers.FilesContentWorker;


/**
 * Worker test.
 * @author  malczyk.pawel@gmail.com
 */
public class FileContentWorkerTest extends ServletTestCase {
	
	/**
	 * File name.
	 */
	private static final String TRANSFORM_XSL = "transform.xsl";

	/**
	 * File content worker.
	 * @uml.property  name="filesContentWorker"
	 * @uml.associationEnd  
	 */
	private FilesContentWorker filesContentWorker;
	
	/**
	 * Identifier.
	 */
	private int id; 
	
	/**
	 * 
	 * Constructor.
	 */
	public FileContentWorkerTest() {
		super();
	}
	
	
	/** {@inheritDoc}*/
	@Override
	protected void setUp() throws Exception {
		Class<?> workerClass = FilesContentWorker.class;
		InitialContext context = new InitialContext();
		filesContentWorker = (FilesContentWorker) context.lookup(workerClass.getName() + "$" + workerClass.getSimpleName() + EjbInterfaceType.REMOTE );
	}
	
	/**
	 * Tests if worker could be located.
	 */
	public void testWorkerFound() {
		assertNotNull(filesContentWorker);
	}
	
	/**
	 * Test method.
	 */
	public void testStore() {
		try {
			
			Properties props = new Properties();
			props.load(new FileInputStream(new File(System.getenv("TASK_HOME") + "/config.properties")));
			File file = new File(props.getProperty("test.data")+"/topdf.xsl");
			
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
				System.out.println("Could not read"
						+ file.getName());
			}

			FileContent content  = new FileContent();
			content.setContent(bytes);
			content.setFileName(TRANSFORM_XSL);
			content.setMimeType("application/xsl");
			content = filesContentWorker.saveFileContent(content);
			id = content.getUniqueId(); 
			assertTrue(id>0);
			is.close();
			
			byte [] data = filesContentWorker.retrieveContent(id);
			assertNotNull(data);
			assertTrue(data.length > 0);
			
			FileContent fileCnt = filesContentWorker.retrieveFileContent(id);
			assertEquals(TRANSFORM_XSL, fileCnt.getFileName());
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
