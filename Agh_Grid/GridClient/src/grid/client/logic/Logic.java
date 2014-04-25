package grid.client.logic;

/**
 * Interfejs logiki
 * @author malczyk.pawel@gmail.com
 *
 */
public interface Logic {
	
	/** Adres uslugi. */
	String SERVICE_URI = "http://127.0.0.2:8080/wsrf/services/grid/ws/impl/BankAccountService";
	
	/**lista kont*/
	String listAccount();
	
	void openAccount();
	
	void closeAccount(String accountId);
	
	void widthdraw(String accountId, String amount);
	
	void deposit(String accountId, String amount);
	
	void trasfer(String params);
	
	String getSaldo(String accountId);
}
