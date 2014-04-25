package process.actions.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import net.jmatrix.eproperties.EProperties;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlFileInput;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;

/**
 * Test.
 * @author malczyk.pawel@gmail.com
 *
 */
public class ProcessConfigurationActionTest extends AbstractActionTest {
	
	
	/** Constant. */
	private static final String FILE_INPUT = "file";
	
	/** Constant. */
	private static final String URL = "http://localhost:8080/procc/EditProcessConfiguration.action";
	
	/** Constant. */
	private static final String UPLOAD = "Wczytaj";
	
	
	/**
	 * Constructor.
	 */
	public ProcessConfigurationActionTest() {
		super();
	}

	/**
	 * Test method.
	 */
	public void testUploadConfig() {
		try {
			WebClient webClient = new WebClient();
			webClient.setJavaScriptEnabled(false);
			doSuperLogin(webClient);
			
			HtmlPage page = webClient.getPage(URL);
			assertEquals("Taskflow-Dodawanie konfiguracji obliczenia", page.getTitleText());
			
			
			HtmlForm form = page.getFormByName("EditProcessConfiguration");
			HtmlFileInput fileInput = form.getInputByName(FILE_INPUT);
			
			EProperties props = new EProperties();
			props.load(new FileInputStream(new File(System.getenv("TASK_HOME") + "/config.properties")));
			String fileName = props.getProperty("test.data")+"/computation.xml";
			fileInput.setValueAttribute(fileName);
			fileInput.setContentType("application/xml");
			
			HtmlSubmitInput submit = form.getInputByValue(UPLOAD);
			page =submit.click();
			assertEquals("Taskflow-Lista konfiguracji obliczenia", page.getTitleText());
			webClient.closeAllWindows();
			
		} catch (IOException e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
}
