package jmetal.base;

import java.io.Serializable;

/**
 * Abstract class representing a multiobjective optimization problem
 */
public abstract class Problem implements Serializable {

	/** Serial. */
	private static final long serialVersionUID = -6033690904402492022L;

	/** (n we wzorach ZDT)  */
	protected int n;

	/**  Stores the number of objectives of the problem */
	protected int numberOfObjectives_;

	/** dolna granica Xi we wzorach ZDT */
	protected double[] xiMin;

	/**
	 * gorna granica Xi we wzorach ZDT
	 * Stores the upper bound values for each variable (only if needed)
	 */
	protected double[] xiMax;

	/** Constructor. */
	public Problem() { }

	/** Getter. */
	public int getN() {
		return n;
	}

	/** Getter. */
	public int getNumberOfObjectives() {
		return numberOfObjectives_;
	} 

	/** Getter. */
	public double getLowerLimit(int i) {
		return xiMin[i];
	}

	/** Getter. */
	public double getUpperLimit(int i) {
		return xiMax[i];
	}

	/** Wykonanie prolemu */
	public abstract void evaluate(Solution solution) throws Exception;

} 
