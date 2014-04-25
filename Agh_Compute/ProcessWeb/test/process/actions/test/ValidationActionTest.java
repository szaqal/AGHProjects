package process.actions.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import net.jmatrix.eproperties.EProperties;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlFileInput;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

/**
 * Action test.
 * @author malczyk.pawel@gmail.com
 *
 */
public class ValidationActionTest extends AbstractActionTest {
	
	/** Config file name.*/
	private static final String CONFIG_PROPERTIES = "/config.properties";

	/** Config key. */
	private static final String TEST_DATA = "test.data";

	/** Environment variable.  */
	private static final String TASK_HOME = "TASK_HOME";

	/** Constant. */
	private static final String EDIT_VALIDATION = "EditValidation";
	
	/** Constant. */
	private static final String FILE_INPUT = "file";
	
	/** Constant. */
	private static final String TITLE_INPUT = "title";
	
	/** Constant. */
	private static final String ADD_TITLE = "Taskflow-Dodawanie walidacji";
	
	/** Constant. */
	private static final String URL = "http://localhost:8080/procc/EditValidation.action?operation=new";
	
	/** Constant. */
	private static final String UPLOAD = "Wczytaj";

	/**
	 * Constructotor.
	 */
	public ValidationActionTest() {
		super();
	}
	
	/**
	 * Test method.
	 */
	public void testValidationUpload() {
		try {
			WebClient webClient = new WebClient();
			webClient.setJavaScriptEnabled(false);
			doSuperLogin(webClient);

			
			HtmlPage page = webClient.getPage(URL);
			assertEquals(ADD_TITLE, page.getTitleText());
			HtmlForm form = page.getFormByName(EDIT_VALIDATION);
			
			HtmlFileInput fileInput = form.getInputByName(FILE_INPUT);
			
			EProperties props = new EProperties();
			props.load(new FileInputStream(new File(System.getenv(TASK_HOME) + CONFIG_PROPERTIES)));
			String fileName = props.getProperty(TEST_DATA)+"/computation.xsd";
			
			fileInput.setValueAttribute(fileName);
			fileInput.setContentType("application/xml");
			
			HtmlTextInput txtTitle = form.getInputByName(TITLE_INPUT);
			txtTitle.setValueAttribute("Valid one");
			
			HtmlSubmitInput submit = form.getInputByValue(UPLOAD);
			page =submit.click();
			assertEquals("Taskflow-Lista Walidacji", page.getTitleText());
			webClient.closeAllWindows();
			
		} catch (IOException e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	/**
	 * Test method.
	 */
	public void testInvalidFileUpload() {
		try {
			WebClient webClient = new WebClient();
			webClient.setJavaScriptEnabled(false);
			doSuperLogin(webClient);
			
			HtmlPage page = webClient.getPage(URL);
			assertEquals(ADD_TITLE, page.getTitleText());
			HtmlForm form = page.getFormByName(EDIT_VALIDATION);
			HtmlFileInput fileInput = form.getInputByName(FILE_INPUT);
			
			Properties props = new Properties();
			props.load(new FileInputStream(new File(System.getenv(TASK_HOME) + CONFIG_PROPERTIES)));
			String fileName = props.getProperty(TEST_DATA)+"/computation.xml";
			
			fileInput.setValueAttribute(fileName);
			
			HtmlTextInput txtTitle = form.getInputByName(TITLE_INPUT);
			txtTitle.setValueAttribute("Invalid one");
			
			HtmlSubmitInput submit = form.getInputByValue(UPLOAD);
			page = submit.click();
			assertEquals(ADD_TITLE, page.getTitleText());
			webClient.closeAllWindows();
			
		} catch (IOException e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
}
