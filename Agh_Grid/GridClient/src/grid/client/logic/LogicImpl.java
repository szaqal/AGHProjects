package grid.client.logic;

import grid.ws.stubs.BankAccountService_instance.BankAccountPortType;
import grid.ws.stubs.BankAccountService_instance.GetAccounts;
import grid.ws.stubs.BankAccountService_instance.service.BankAccountServiceAddressingLocator;

import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import org.apache.axis.message.addressing.Address;
import org.apache.axis.message.addressing.EndpointReferenceType;
import org.apache.axis.types.URI.MalformedURIException;

/**
 * Implementacja.
 * @author malczyk
 *
 */
public class LogicImpl implements Logic {
	
	/** Lokator uslugi. */
	private BankAccountServiceAddressingLocator locator;
	
	/** cos*/
	private EndpointReferenceType endpoint;
	
	/**
	 * Konstruktor.
	 */
	public LogicImpl() {
		locator = new BankAccountServiceAddressingLocator();
		endpoint = new EndpointReferenceType();
		try {
			endpoint.setAddress(new Address(SERVICE_URI));
		} catch (MalformedURIException e) {
			e.printStackTrace();
		}

	}

	/** {@inheritDoc} */
	@Override
	public String listAccount() {
		try {
			BankAccountPortType bankAccount = getPortType();
			return bankAccount.getAccounts(new GetAccounts()) ;
			
			
		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return "";
	}

	private BankAccountPortType getPortType() throws ServiceException {
		return locator.getBankAccountPortTypePort(endpoint);
	}

	@Override
	public void openAccount() {
		try {
			BankAccountPortType bankAccountPortType = getPortType();
			bankAccountPortType.openAccount("");
		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void closeAccount(String accountId) {
		try {
			BankAccountPortType bankAccountPortType = getPortType();
			bankAccountPortType.closeAccount(accountId);
		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deposit(String accountId, String amount) {
		try {
			BankAccountPortType bankAccountPortType = getPortType();
			String param = accountId+"#"+amount;
			System.out.println(param);
			bankAccountPortType.deposit(param);
		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void widthdraw(String accountId, String amount) {
		try {
			BankAccountPortType bankAccountPortType = getPortType();
			String param = accountId+"#"+amount;
			System.out.println(param);
			bankAccountPortType.withdraw(param);
		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void trasfer(String params) {
		try {
			BankAccountPortType bankAccountPortType = getPortType();
			System.out.println(params);
			bankAccountPortType.transfer(params);
		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getSaldo(String accountId) {
		try {
			BankAccountPortType bankAccountPortType = getPortType();
			return bankAccountPortType.checkSaldo(accountId);
		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return null;
	}
}
