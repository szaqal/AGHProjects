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
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

/**
 * Test.
 * @author malczyk.pawel@gmail.com
 *
 */
public class EditProcessActionTest extends AbstractActionTest {
	
	/**
	 * Action url.
	 */
	private static final String URL = "http://localhost:8080/procc/EditProcess.action";
	
	/** Constant. */
	private static final String FILE_INPUT = "file";
	
	/** Constant. */
	private static final String TITLE_INPUT = "title";

	/** Constant. */
	private static final String UPLOAD = "Wczytaj";
	
	/**
	 * Constructor.
	 */
	public EditProcessActionTest() {
		super();
	}
	
	/**
	 * Test method.
	 */
	public void testUploadProcess() {
		try {
			WebClient webClient = new WebClient();
			webClient.setJavaScriptEnabled(false);
			doSuperLogin(webClient);
			
			HtmlPage page = webClient.getPage(URL);
			assertEquals("Taskflow-Dodawanie obliczenia", page.getTitleText());
			
			EProperties props = new EProperties();
			props.load(new FileInputStream(new File(System.getenv("TASK_HOME") + "/config.properties")));
			
			HtmlForm form = page.getFormByName("Upload");
			HtmlFileInput fileInput = form.getInputByName(FILE_INPUT);
			String fileName = props.getProperty("test.data")+"/computation.xsd";
			fileInput.setValueAttribute(fileName);
			fileInput.setContentType("application/xml");
			
			HtmlTextInput txtTitle = form.getInputByName(TITLE_INPUT);
			txtTitle.setValueAttribute("testpackage");
			
			HtmlSubmitInput submit = form.getInputByValue(UPLOAD);
			page =submit.click();
			assertEquals("Taskflow-Main", page.getTitleText());
			webClient.closeAllWindows();
			
		} catch (IOException e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
}
