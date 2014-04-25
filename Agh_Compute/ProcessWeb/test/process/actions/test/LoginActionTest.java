package process.actions.test;

import java.io.IOException;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

import junit.framework.TestCase;

/**
 * Test for login page.
 * @author malczyk.pawel@gmail.com
 *
 */
public class LoginActionTest extends TestCase {
	
	
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
	 * URl.
	 */
	private static final String HTTP_PROCC_LOGIN_USER = "http://localhost:8080/procc/LoginUser";

	/**
	 * Constructor.
	 */
	public LoginActionTest() {
		super();
	}

	/**
	 * Test method.
	 * @throws IOException exception
	 */
	public void testLogin() throws IOException {
		final WebClient webClient = new WebClient();
		//XXX: issues with jquery
		webClient.setJavaScriptEnabled(false);
		final HtmlPage page = webClient.getPage(HTTP_PROCC_LOGIN_USER);
		assertEquals("Taskflow-Logowanie", page.getTitleText());
		webClient.closeAllWindows();
	}
	
	/**
	 * Test method.
	 * @throws IOException exception
	 */
	public void testLoginInvalid() throws IOException {
		final WebClient webClient = new WebClient();
		// XXX: issues with jquery
		webClient.setJavaScriptEnabled(false);
		final HtmlPage page = webClient.getPage(HTTP_PROCC_LOGIN_USER);
		final HtmlForm form = page.getFormByName(LOGIN_USER_FORM_NAME);

		final HtmlTextInput textEmail = form.getInputByName(EMAIL_INPUT_NAME);
		final HtmlPasswordInput textPassword = form.getInputByName(PASSWD_INPUT_NAME);
		final HtmlSubmitInput btnLogin = form.getInputByName(LOGIN_SUBMIT_NAME);
		
		textEmail.setValueAttribute("invalidLogin");
		textPassword.setValueAttribute("invalidPasswd");
		
		final HtmlPage nextPage = btnLogin.click();
		assertTrue(nextPage.asText().contains("Błędny login bądź hasło"));


		webClient.closeAllWindows();
	}
	
	/**
	 * Test method.
	 * @throws IOException exception
	 */
	public void testLoginValid()  throws IOException {
		final WebClient webClient = new WebClient();
		// XXX: issues with jquery
		webClient.setJavaScriptEnabled(false);
		final HtmlPage page = webClient.getPage(HTTP_PROCC_LOGIN_USER);
		final HtmlForm form = page.getFormByName(LOGIN_USER_FORM_NAME);

		final HtmlTextInput textEmail = form.getInputByName(EMAIL_INPUT_NAME);
		final HtmlPasswordInput textPassword = form.getInputByName(PASSWD_INPUT_NAME);
		final HtmlSubmitInput btnLogin = form.getInputByName(LOGIN_SUBMIT_NAME);
		
		textEmail.setValueAttribute("malczyk.pawel@gmail.com");
		textPassword.setValueAttribute("malczyk123");
		
		final HtmlPage nextPage = btnLogin.click();
		assertTrue(nextPage.getTitleText().equals("Taskflow-Main"));


		webClient.closeAllWindows();
	}
	
}
