package grid.ws.impl;


import grid.ws.stubs.BankAccountService_instance.CloseAccountResponse;
import grid.ws.stubs.BankAccountService_instance.DepositWithDrawResponse;
import grid.ws.stubs.BankAccountService_instance.GetValueRP;
import grid.ws.stubs.BankAccountService_instance.TransferResponse;

import java.rmi.RemoteException;

import org.globus.wsrf.Resource;
import org.globus.wsrf.ResourceProperties;
import org.globus.wsrf.ResourceProperty;
import org.globus.wsrf.ResourcePropertySet;
import org.globus.wsrf.impl.ReflectionResourceProperty;
import org.globus.wsrf.impl.SimpleResourcePropertySet;

/**
 * Serwis obslugi konta.
 * @author malczyk
 *
 */
public class BankAccountService implements Resource, ResourceProperties {
	
	//Caused by: [CORE] Having more than one input parameter is not allowed (jeden parametr)
	
	
	/** Resource Property set */
	private ResourcePropertySet propSet;

	/** Resource properties */
	private int value;
	private String lastOp;
	private String accountList;

	/** 
	 *  Constructor. Initializes RPs 
	 */
	public BankAccountService() throws RemoteException {

		
		this.propSet = new SimpleResourcePropertySet(BankAccountQNames.RESOURCE_PROPERTIES);

		try {
			ResourceProperty valueRP = new ReflectionResourceProperty(BankAccountQNames.RP_VALUE, BankAccountQNames.VALUE, this);
			this.propSet.add(valueRP);
			setValue(0);

			ResourceProperty lastOpRP = new ReflectionResourceProperty(BankAccountQNames.RP_LASTOP, BankAccountQNames.LAST_OP, this);
			this.propSet.add(lastOpRP);
			setLastOp("NONE");
			
			
			ResourceProperty accountListResourceRP = new ReflectionResourceProperty(BankAccountQNames.RP_ACCOUNT_LIST, BankAccountQNames.ACCOUNT_LIST, this);
			this.propSet.add(accountListResourceRP);
			
			setAccountList(Accounts.getInstance().getAccouts());
		
			
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}
	
	private void update() {
		setAccountList(Accounts.getInstance().getAccouts());
	}


	/* Get/Setters for the RPs */
	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String getLastOp() {
		return lastOp;
	}

	public void setLastOp(String lastOp) {
		this.lastOp = lastOp;
	}


	/** {@inheritDoc} */
	public ResourcePropertySet getResourcePropertySet() {
		return this.propSet;
	}
	

	public String getAccounts() {
		return accountList;
	}

	public void setAccountList(String accountList) {
		this.accountList = accountList;
	}
	
	public int getValueRP(GetValueRP params) throws RemoteException {
		return value;
	}

	public final ResourcePropertySet getPropSet() {
		return propSet;
	}

	public final void setPropSet(ResourcePropertySet propSet) {
		this.propSet = propSet;
	}

	public final String getAccountList() {
		return accountList;
	}
	
	public void openAccount(String param) throws RemoteException {
		System.out.println("OpenAccount");
		Accounts.getInstance().addAccount(new Account());
		update();
	}
	
	public CloseAccountResponse closeAccount(String accountId) throws RemoteException {
		System.out.println("CloseAccount " + accountId);
		Accounts.getInstance().closeAccount(Integer.valueOf(accountId));
		update();
		return new CloseAccountResponse();
	}
	
	public TransferResponse transfer(String parameters) throws RemoteException {
		System.out.println("Transfering");
		Accounts.getInstance().transfer(parameters);
		update();
		return new TransferResponse();
	}
	
	public DepositWithDrawResponse deposit(String parameters) throws RemoteException {
		System.out.println("Deposit");
		Accounts.getInstance().deposit(parameters);
		update();
		return new DepositWithDrawResponse();
	}
	
	public DepositWithDrawResponse withdraw(String parameters) throws RemoteException {
		System.out.println("WithDraw" + parameters);
		Accounts.getInstance().withdraw(parameters);
		update();
		return new DepositWithDrawResponse();
	}
	
	public String checkSaldo(String accountId) throws RemoteException {
		System.out.println("checking");
		return Accounts.getInstance().getSaldo(accountId);
	}




}
