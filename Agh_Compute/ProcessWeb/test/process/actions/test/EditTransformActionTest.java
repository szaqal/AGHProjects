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
 * Action test.
 * @author malczyk.pawel@gmail.com
 *
 */
public class EditTransformActionTest extends AbstractActionTest {
	
	/**
	 * URL.
	 */
	private static final String URL ="http://localhost:8080/procc/EditTransform.action?operation=new";
	
	
	/**
	 * Constructor.
	 */
	public EditTransformActionTest() {
		super();
	}
	
	/**
	 * Test method.
	 */
	public void testAddTransform() {
		
		WebClient webClient = new WebClient();
		webClient.setJavaScriptEnabled(false);
		try {
			doLogin(webClient);
			
			
			HtmlPage page = webClient.getPage(URL);
			assertEquals("Taskflow-Dodawanie Transformaty", page.getTitleText());
			
			
			HtmlForm form = page.getFormByName("AddTransform");
			HtmlFileInput fileInput = form.getInputByName("file");
			
			EProperties props = new EProperties();
			props.load(new FileInputStream(new File(System.getenv("TASK_HOME") + "/config.properties")));
			
			String fileName = props.getProperty("test.data")+"/tohtml.xsl";
			fileInput.setValueAttribute(fileName);
			fileInput.setContentType("application/xml");
			
			HtmlTextInput txtTitle = form.getInputByName("title");
			txtTitle.setValueAttribute("tohtml");
			
			HtmlTextInput resultMime = form.getInputByName("resultMime");
			resultMime.setValueAttribute("text/html");
			
			HtmlTextInput extension = form.getInputByName("extension");
			extension.setValueAttribute("html");
			
			HtmlSubmitInput submit = form.getInputByValue("Wczytaj");
			page =submit.click();
			
			assertEquals("Taskflow-Lista Transformat", page.getTitleText());
			
		} catch (IOException e) {
			e.printStackTrace();
			assertTrue(false);
		}
		
	}

}
