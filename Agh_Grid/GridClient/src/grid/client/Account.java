package grid.client;

import java.io.Serializable;

/**
 * Pojedyncze konto
 * @author malczyk
 *
 */
public class Account implements Serializable {
	
	/**
	 * Serial.
	 */
	private static final long serialVersionUID = -6446974093003358747L;

	/** Numer konta. */
	private int accoutNumber;
	
	/** Saldo. */
	private double saldo;
	
	public Account() {
		accoutNumber = hashCode();
	}

	public int getAccoutNumber() {
		return accoutNumber;
	}

	public void setAccoutNumber(int accoutNumber) {
		this.accoutNumber = accoutNumber;
	}

	public double getSaldo() {
		return saldo;
	}

	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}
	
	/** {@inheritDoc} */
	@Override
	public String toString() {
		return accoutNumber+":"+saldo;
	}
	
}
