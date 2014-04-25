package grid.ws.impl;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public final class Accounts {
	


	private static Accounts instnace;
	
	private static Set<Account> accountsSet = new HashSet<Account>();
	
	private Accounts() { 
		accountsSet = Collections.synchronizedSet(accountsSet);
		for(int i=0;i<5;i++) {
			//10 testowych kont
			addAccount(new Account());
		}
	}
	
	
	
	public static Accounts getInstance() {
		synchronized(Accounts.class) {
			if(instnace==null) {
				instnace = new Accounts();
			}
			return instnace;
		}
	}
	
	public void addAccount(Account account) {
		accountsSet.add(account);
	}
	
	public void closeAccount(Integer accountId) {
		System.out.println("-> "+accountsSet);
		Iterator<Account> iter = accountsSet.iterator();
		while(iter.hasNext()) {
			Account iterated = iter.next();
			Integer id = iterated.getId();
			if(id.equals(accountId)) {
				iter.remove();
			}
		}
	}
	
	public void deposit(String params) {
		String[] splitted = params.split("#");
		Account account = getAccount(splitted[0]);
		double saldo = account.getSaldo() + Double.valueOf(splitted[1]);
		account.setSaldo(saldo);
	}
	
	public void withdraw(String params) {
		String[] splitted = params.split("#");
		Account account = getAccount(splitted[0]);
		
		double saldo = account.getSaldo() - Double.valueOf(splitted[1]);
		account.setSaldo(saldo);
	}
	
	public String getSaldo(String params) {
		return String.valueOf(getAccount(params).getSaldo());
	}
	
	public void transfer(String params) {
		String [] splitted = params.split("#");
		String accountFrom = splitted[0];
		String accounTo = splitted[1];
		String amount = splitted[2];
		System.out.println(accountFrom+"->"+accounTo+":"+amount);
		
		withdraw(accountFrom+"#"+amount);
		deposit(accounTo+"#"+amount);
		
	}
	
	private Account getAccount(String accId) {
		Iterator<Account> iter = accountsSet.iterator();
		while(iter.hasNext()) {
			Account iterated = iter.next();
			Integer id = iterated.getId();
			Integer intAccId = Integer.valueOf(accId);
			if(id.equals(intAccId)) {
				return iterated;
			}
		}
		return null;
	}
	
	public String getAccouts() {
		StringBuffer buffer = new StringBuffer();
		for(Account account : accountsSet) {
			buffer.append(account.toString()).append("#");
		}
		
		return buffer.substring(0, buffer.length()-1);
	}
	

	
}
