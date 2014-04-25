package process.actions.test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import junit.framework.TestCase;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

/**
 * Action test.
 * @author malczyk.pawel@gmail.com
 *
 */
public class EditMappingActionTest extends TestCase {
	
	/**
	 * Node 00.
	 */
	private static final String NODE00 = "node00";

	/**
	 * Constant.
	 */
	private static final String EDIT_MAPPING = "EditMapping";

	/**
	 * Constant.
	 */
	private static final String NODE_INPUT = "node";

	/**
	 * Constant.
	 */
	private static final String PERFORMER_INPUT = "performer";

	/**
	 * Constant.
	 */
	private static final String WINDOW_TITLE_MAPPING_EDIT = "Taskflow-Edycja mapowania";

	/**
	 * Constant.
	 */
	private static final String WINDOW_TITLE_LOGIN = "Taskflow-Logowanie";

	/**
	 * Constant.
	 */
	private static final String WINDOW_TITLE_MAIN = "Taskflow-Main";

	/**
	 * Constant.
	 */
	private static final String PASSWD = "malczyk123";

	/**
	 * Constant.
	 */
	private static final String EMAIL = "malczyk.pawel@gmail.com";

	/**
	 * Constant.
	 */
	private static final String LOGIN_USER_URL = "http://localhost:8080/procc/LoginUser";

	/**
	 * Constant.
	 */
	private static final String ADD_BUTTON = "Dodaj";

	/**
	 * Constant.
	 */
	private static final String PERFORMER = "http://localhost:8081";

	/**
	 * Constant.
	 */
	private static final String WINDOW_TITLE = "Taskflow-List mapowan";

	/**
	 * Constant.
	 */
	private static final String LOGIN_SUBMIT_NAME = "form.login.perform";
	
	/**
	 * Constant.
	 */
	private static final String PASSWD_INPUT_NAME = "passwd";
	
	/**
	 * Constant.
	 */
	private static final String EMAIL_INPUT_NAME = "email";
	
	/**
	 * Constant.
	 */
	private static final String LOGIN_USER_FORM_NAME = "LoginUser";
	
	/**
	 * Constructor.
	 */
	public EditMappingActionTest() {
		super();
	}
	

	/**
	 * Test method.
	 * @throws IOException exception
	 */
	public void testAddMapping() throws IOException {
		final WebClient webClient = new WebClient();
		//XXX: issues with jquery
		webClient.setJavaScriptEnabled(false);
		HtmlPage page = webClient.getPage(LOGIN_USER_URL);
		assertEquals(WINDOW_TITLE_LOGIN, page.getTitleText());
		
		HtmlForm form = page.getFormByName(LOGIN_USER_FORM_NAME);
		HtmlTextInput textEmail = form.getInputByName(EMAIL_INPUT_NAME);
		HtmlPasswordInput textPassword = form.getInputByName(PASSWD_INPUT_NAME);
		HtmlSubmitInput btnLogin = form.getInputByName(LOGIN_SUBMIT_NAME);
		
		textEmail.setValueAttribute(EMAIL);
		textPassword.setValueAttribute(PASSWD);
		
		final HtmlPage nextPage = btnLogin.click();
		assertTrue(nextPage.getTitleText().equals(WINDOW_TITLE_MAIN));
		
		page = webClient.getPage("http://localhost:8080/procc/EditMapping.action?operation=new");
		assertEquals(WINDOW_TITLE_MAPPING_EDIT, page.getTitleText());
		
		
		form = page.getFormByName(EDIT_MAPPING);
		HtmlTextInput textNode = form.getInputByName(NODE_INPUT);
		HtmlTextInput textPerformer  = form.getInputByName(PERFORMER_INPUT);
		textNode.setValueAttribute(NODE00);
		textPerformer.setValueAttribute(PERFORMER);
		HtmlSubmitInput addBtn = form.getInputByValue(ADD_BUTTON);
		page = addBtn.click();
		
		
		assertTrue(page.getTitleText().contains(WINDOW_TITLE));
		
		webClient.closeAllWindows();
	}
	
	
	/**
	 * Test method.
	 * @throws IOException test method
	 */
	public void testEditMapping() throws IOException { 
		final WebClient webClient = new WebClient();
		//XXX: issues with jquery
		webClient.setJavaScriptEnabled(false);
		HtmlPage page = webClient.getPage(LOGIN_USER_URL);
		assertEquals(WINDOW_TITLE_LOGIN, page.getTitleText());
		
		HtmlForm form = page.getFormByName(LOGIN_USER_FORM_NAME);
		HtmlTextInput textEmail = form.getInputByName(EMAIL_INPUT_NAME);
		HtmlPasswordInput textPassword = form.getInputByName(PASSWD_INPUT_NAME);
		HtmlSubmitInput btnLogin = form.getInputByName(LOGIN_SUBMIT_NAME);
		
		textEmail.setValueAttribute(EMAIL);
		textPassword.setValueAttribute(PASSWD);
		
		final HtmlPage nextPage = btnLogin.click();
		assertTrue(nextPage.getTitleText().equals(WINDOW_TITLE_MAIN));
		
		int id= 0;
		try {
			String query = "SELECT unique_id FROM node_mappings WHERE node='node00' LIMIT 1";
			Class.forName("org.postgresql.Driver");
			Connection conn = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/processdb", "proc", "procc321");
			PreparedStatement statement = conn.prepareStatement(query);
			ResultSet rs = statement.executeQuery();
			assertTrue(rs.next());
			id = rs.getInt(1);
			assertEquals(2, id);
			statement.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			assertTrue(false);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			assertTrue(false);
		}
		
		page = webClient.getPage("http://localhost:8080/procc/EditMapping.action?operation=edit&uniqueId="+id);
		assertEquals(WINDOW_TITLE_MAPPING_EDIT, page.getTitleText());
		
		
		form = page.getFormByName(EDIT_MAPPING);
		HtmlTextInput textNode = form.getInputByName(NODE_INPUT);
		HtmlTextInput textPerformer  = form.getInputByName(PERFORMER_INPUT);
		textNode.setValueAttribute(PERFORMER);
		textPerformer.setValueAttribute(NODE00);
		HtmlSubmitInput addBtn = form.getInputByValue(ADD_BUTTON);
		page = addBtn.click();
		
		
		assertTrue(page.getTitleText().contains(WINDOW_TITLE));
		
		webClient.closeAllWindows();
	}
	
}
