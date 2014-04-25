package process.actions.test;

import java.io.IOException;
import java.util.List;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlCheckBoxInput;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

/**
 * Action test.
 * @author malczyk.pawel@gmail.com
 *
 */
public class EditGroupActionTest extends AbstractActionTest {
	
	/**
	 * Constructor.
	 */
	public EditGroupActionTest() {
		super();
	}
	
	/**
	 * Test method.
	 */
	public void testCreateGroup() {
		try {
			WebClient webClient = new WebClient();
			webClient.setJavaScriptEnabled(false);
			doLogin(webClient);
			
			HtmlPage page = webClient.getPage("http://localhost:8080/procc/GroupEdit.action?operation=new");
			assertEquals("Taskflow-Tworzenie Grupy", page.getTitleText());
			
			
			HtmlForm form = page.getFormByName("GroupEdit");
			HtmlTextInput textNode = form.getInputByName("group.name");
			textNode.setValueAttribute("AdminGroups");
			
			List<HtmlInput> rights = form.getInputsByName("rights");
			for(HtmlInput right : rights) {
				((HtmlCheckBoxInput) right).setChecked(true);
			}
			
			HtmlSubmitInput addBtn = form.getInputByValue("Dodaj");
			page = addBtn.click();
			
			assertTrue(page.getTitleText().contains("Taskflow-Lista Grup"));
			
		} catch (IOException e) {
			e.printStackTrace();
			assertTrue(false);
		}
		
	}
	
}
