package process.actions.test;

import java.io.IOException;

import junit.framework.TestCase;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

/**
 * Abstract test class.
 * @author malczyk.pawel@gmail.com
 *
 */
public abstract class AbstractActionTest extends TestCase {
	
	/**
	 * Constant.
	 */
	protected static final String LOGIN_USER_URL = "http://localhost:8080/procc/LoginUser";
	
	/**
	 * Constant.
	 */
	protected static final String LOGIN_SUBMIT_NAME = "form.login.perform";
	
	/**
	 * Constant.
	 */
	protected static final String PASSWD_INPUT_NAME = "passwd";
	
	/**
	 * Constant.
	 */
	protected static final String EMAIL_INPUT_NAME = "email";
	
	/**
	 * Constant.
	 */
	protected static final String LOGIN_USER_FORM_NAME = "LoginUser";
	
	/**
	 * Constant.
	 */
	protected static final String WINDOW_TITLE_MAIN = "Taskflow-Main";
	

	/**
	 * Constant.
	 */
	protected static final String PASSWD = "malczyk123";

	/**
	 * Constant.
	 */
	protected static final String EMAIL = "malczyk.pawel@gmail.com";
	
	
	/**
	 * Logins user.
	 * @param webClient web client
	 * @throws IOException  exception throwed
	 */
	void doLogin(WebClient webClient) throws  IOException {
		//XXX: issues with jquery
		HtmlPage page = webClient.getPage(LOGIN_USER_URL);
		
		HtmlForm form = page.getFormByName(LOGIN_USER_FORM_NAME);
		HtmlTextInput textEmail = form.getInputByName(EMAIL_INPUT_NAME);
		HtmlPasswordInput textPassword = form.getInputByName(PASSWD_INPUT_NAME);
		HtmlSubmitInput btnLogin = form.getInputByName(LOGIN_SUBMIT_NAME);
		
		textEmail.setValueAttribute(EMAIL);
		textPassword.setValueAttribute(PASSWD);
		
		final HtmlPage nextPage = btnLogin.click();
		assertTrue(nextPage.getTitleText().equals(WINDOW_TITLE_MAIN));
	}
	
	/**
	 * Logins super user.
	 * @param webClient web client
	 * @throws IOException exception throwed
	 */
	void doSuperLogin(WebClient webClient) throws IOException {
		HtmlPage page = webClient.getPage(LOGIN_USER_URL);
		
		HtmlForm form = page.getFormByName(LOGIN_USER_FORM_NAME);
		HtmlTextInput textEmail = form.getInputByName(EMAIL_INPUT_NAME);
		HtmlPasswordInput textPassword = form.getInputByName(PASSWD_INPUT_NAME);
		HtmlSubmitInput btnLogin = form.getInputByName(LOGIN_SUBMIT_NAME);
		
		textEmail.setValueAttribute("__super__");
		textPassword.setValueAttribute("super321");
		
		final HtmlPage nextPage = btnLogin.click();
		assertTrue(nextPage.getTitleText().equals(WINDOW_TITLE_MAIN));
	}
	
}
