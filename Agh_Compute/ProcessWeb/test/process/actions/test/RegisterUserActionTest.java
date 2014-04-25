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
 * Test.
 * @author malczyk.pawel@gmail.com
 *
 */
public class RegisterUserActionTest extends TestCase {
	
	/**
	 * Constant.
	 */
	private static final String ACTIVATION_LINK = "http://localhost:8080/procc/CompleteRegistration?registrationId=";

	/**
	 * Constant.
	 */
	private static final String REJESTRACJA = "Rejestracja";
	
	/**
	 * Constant.
	 */
	private static final String USER_LAST_NAME = "user.lastName";
	
	/**
	 * Constant.
	 */
	private static final String USER_FIRST_NAME = "user.firstName";
	
	/**
	 * Constant.
	 */
	private static final String USER_PASSWORD = "user.password";
	
	/**
	 * Constant.
	 */
	private static final String USER_EMAIL = "user.email";
	
	/**
	 * Constant.
	 */
	private static final String FORM_NAME = "RegisterUser";
	/**
	 * URl.
	 */
	private static final String HTTP_PROCC_REGISTER_USER = "http://localhost:8080/procc/RegisterUser";
	
	
	
	/**
	 * Constructor.
	 */
	public RegisterUserActionTest() {
		super();
	}
	
	/**
	 * Test method.
	 * @throws IOException 
	 */
	public void testRegister() throws IOException  {
		WebClient webClient = new WebClient();
		//XXX: issues with jquery
		webClient.setJavaScriptEnabled(false);
		HtmlPage page =  webClient.getPage(HTTP_PROCC_REGISTER_USER);
		assertEquals("Taskflow-Rejestracja Użytkownika", page.getTitleText());
		webClient.closeAllWindows();
	}
	
	/**
	 * Test Method.
	 * @throws IOException 
	 */
	public void testInvalidRegistration() throws IOException {
		WebClient webClient = new WebClient();
		//XXX: issues with jquery
		webClient.setJavaScriptEnabled(false);
		HtmlPage page =  webClient.getPage(HTTP_PROCC_REGISTER_USER);
		HtmlForm form = page.getFormByName(FORM_NAME);
		
		HtmlTextInput textEmail = form.getInputByName(USER_EMAIL);
		HtmlPasswordInput textPassword = form.getInputByName(USER_PASSWORD);
		HtmlTextInput textFirstName = form.getInputByName(USER_FIRST_NAME);
		HtmlTextInput textLastName = form.getInputByName(USER_LAST_NAME);
		
		final HtmlSubmitInput btnLogin = form.getInputByValue(REJESTRACJA);
		textEmail.setValueAttribute("");
		textPassword.setValueAttribute("");
		textFirstName.setValueAttribute("user firstname");
		textLastName.setValueAttribute("user lastname");
		
		HtmlPage nextPage = btnLogin.click();
		 
		assertTrue(nextPage.asText().contains("Email wymagany"));
		assertTrue(nextPage.asText().contains("Hasło wymagane"));
		
		
		webClient.closeAllWindows();
	}
	
	/**
	 * Test method.
	 * @throws IOException io exception
	 */
	public void testValidRegistration() throws IOException {
		WebClient webClient = new WebClient();
		//XXX: issues with jquery
		webClient.setJavaScriptEnabled(false);
		HtmlPage page =  webClient.getPage(HTTP_PROCC_REGISTER_USER);
		HtmlForm form = page.getFormByName(FORM_NAME);
		
		HtmlTextInput textEmail = form.getInputByName(USER_EMAIL);
		HtmlPasswordInput textPassword = form.getInputByName(USER_PASSWORD);
		HtmlTextInput textFirstName = form.getInputByName(USER_FIRST_NAME);
		HtmlTextInput textLastName = form.getInputByName(USER_LAST_NAME);
		
		final HtmlSubmitInput btnLogin = form.getInputByValue(REJESTRACJA);
		textEmail.setValueAttribute("malczykp@interia.pl");
		textPassword.setValueAttribute("malczyk123");
		textFirstName.setValueAttribute("Paweł");
		textLastName.setValueAttribute("Malczyk");
		
		HtmlPage nextPage = btnLogin.click();
		assertTrue(nextPage.getTitleText().equals("Taskflow-Logowanie"));
		
		
		webClient.closeAllWindows();
	}
	
	/**
	 * Test method.
	 * @throws IOException exception
	 */
	public void testContinueRegistration() throws IOException {
		try {
			String query = "SELECT unique_id FROM registrations ORDER BY registration_date DESC LIMIT 1";
			Class.forName("org.postgresql.Driver");
			Connection conn = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/processdb", "proc", "procc321");
			PreparedStatement statement = conn.prepareStatement(query);
			ResultSet rs = statement.executeQuery();
			assertTrue(rs.next());
			String id = rs.getString(1);
			assertNotNull(id);
			statement.close();
			conn.close();
			
			WebClient webClient = new WebClient();
			//XXX: issues with jquery
			webClient.setJavaScriptEnabled(false);
			HtmlPage page =  webClient.getPage(ACTIVATION_LINK+id);
			assertTrue(page.asText().contains("Aktywowano użytkownika"));
			webClient.closeAllWindows();
			
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			assertTrue(false);
		} catch (SQLException e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	/**
	 * Test method.
	 * @throws IOException exception
	 */
	public void testInvalidContinueRegistration() throws IOException {
		WebClient webClient = new WebClient();
		//XXX: issues with jquery
		webClient.setJavaScriptEnabled(false);
		HtmlPage page =  webClient.getPage(ACTIVATION_LINK+"invalid");
		assertTrue(page.asText().contains("Nie udało się aktywować użytkownika"));
		webClient.closeAllWindows();
	}

}
