package core.tests;

import javax.naming.InitialContext;

import org.apache.cactus.ServletTestCase;

import core.model.ConfigurationItem;
import core.workers.ConfigurationWorker;
import core.workers.EjbInterfaceType;

/**
 * Test for worker. 
 * @author  malczyk.pawel@gmail.com
 */
public class ConfigurationWorkerTest extends ServletTestCase {
	
	/**
	 * {@link ConfigurationWorker}   .
	 * @uml.property  name="worker"
	 * @uml.associationEnd  
	 */
	private ConfigurationWorker worker;
	
	/**
	 * Constructor.
	 */
	public ConfigurationWorkerTest() {
		super();
		
	}
	
	/** {@inheritDoc}*/
	@Override
	protected void setUp() throws Exception {
		Class<?> workerClass = ConfigurationWorker.class;
		InitialContext context = new InitialContext();
		worker = (ConfigurationWorker) context.lookup(workerClass.getName() + "$" + workerClass.getSimpleName() + EjbInterfaceType.REMOTE );
	}
	
	/**
	 * Tests if worker could be located.
	 */
	public void testWorkerFound() {
		assertNotNull(worker);
	}
	
	/**
	 * Test saving.
	 */
	public void testAddValues() {
		ConfigurationItem item = new ConfigurationItem();
		String applicationUl = "application.url";
		item.setConfigKey(applicationUl);
		item.setConfigValue("http://localhost:8080/procc");
		worker.store(item);
		
		item = new ConfigurationItem();
		item.setConfigKey("application.mail.from");
		item.setConfigValue("taskflow@o2.pl");
		worker.store(item);
		
		item = new ConfigurationItem();
		item.setConfigKey("smtp.user");
		item.setConfigValue("taskflow");
		worker.store(item);
		
		
		item = new ConfigurationItem();
		item.setConfigKey("smtp.port");
		item.setConfigValue("587");
		worker.store(item);
		
		item = new ConfigurationItem();
		item.setConfigKey("smtp.host");
		item.setConfigValue("poczta.o2.pl");
		worker.store(item);
		
		item = new ConfigurationItem();
		item.setConfigKey("smtp.password");
		item.setConfigValue("taskflow321");
		worker.store(item);
		
		assertNotNull(worker.getConfigByKey(applicationUl));
		
	}

}
