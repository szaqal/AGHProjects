package grid.ws.impl;

import java.io.Serializable;

/**
 * Pojedyncze konto.
 * @author malczyk
 *
 */
public class Account implements Serializable {
	
	private static final long serialVersionUID = 6441955684854225003L;

	private int id;
	
	private double saldo;
	
	public Account() {
		id = hashCode();
	}


	
	public final int getId() {
		return id;
	}


	public final void setId(int id) {
		this.id = id;
	}


	public final double getSaldo() {
		return saldo;
	}


	public final void setSaldo(double saldo) {
		this.saldo = saldo;
	}
	
	@Override
	public String toString() {
		return id+":"+saldo;
	}
	
}
