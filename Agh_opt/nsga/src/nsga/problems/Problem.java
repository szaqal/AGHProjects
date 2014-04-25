package nsga.problems;

import java.io.Serializable;

import nsga.core.Individual;

/**
 * Abstract class representing a multiobjective optimization problem
 */
public abstract class Problem implements Serializable {

	/** Serial. */
	private static final long serialVersionUID = -6033690904402492022L;

	/** (n we wzorach ZDT)  */
	protected int n;

	/**  Stores the number of objectives of the problem */
	protected int numberOfResults;

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
	public int getNumberOfResults() {
		return numberOfResults;
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
	public abstract void evaluate(Individual individual) throws Exception;

} 
