package jmetal.base.variable;

import java.io.Serializable;

import jmetal.util.RandomUtil;

/**
 * Opakowuje zmienna rzeczywista.
 */
public class Real implements Serializable {

	/** Serial. */
	private static final long serialVersionUID = -5286666692378861514L;

	/** Wlasciwa przchowywana wartosc  */
	private double value;

	/** Dolne ograniczenie dla wartosci zmiennej */
	private double lBound;

	/** Gorne ograniczenie wartosci zmiennej */
	private double uBound;

	/** Konstruktor */
	public Real() { }

	/** Konstruktor. */
	public Real(double lowerBound, double upperBound) {
		lBound = lowerBound;
		uBound = upperBound;
		value = RandomUtil.randDouble() * (upperBound - lowerBound) + lowerBound;
	} 

	/** Konstruktor. */
	public Real(Real variable) throws Exception {
		lBound = variable.getLowerBound();
		uBound = variable.getUpperBound();
		value = variable.getValue();
	}

	/** Getter. */
	public double getValue() {
		return value;
	} 

	/** Setter. */
	public void setValue(double val) {
		value = val;
	} 

	/** Kopuje obiekt.*/
	public Real deepCopy() {
		try {
			return new Real(this);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/** Getter. */
	public double getLowerBound() {
		return lBound;
	} 

	/** Getter. */
	public double getUpperBound() {
		return uBound;
	} 

	/**Setter. */
	public void setLowerBound(double lowerBound) {
		lBound = lowerBound;
	} 

	/** Setter. */
	public void setUpperBound(double upperBound) {
		uBound = upperBound;
	} 

	/**
	 * Returns a string representing the object
	 * 
	 * @return the string
	 */
	public String toString() {
		return value + "";
	}
}
