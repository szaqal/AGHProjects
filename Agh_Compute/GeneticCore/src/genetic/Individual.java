package genetic;

import genetic.util.RandomUtil;

import java.io.Serializable;

/**
 * Represents single population individual.
 * 
 * @author malczyk.pawel@gmail.com
 *
 */
public class Individual implements Serializable, Comparable<Individual> {

	/**
	 * Serial.
	 */
	private static final long serialVersionUID = 2561677335180476581L;
	
	private double [] problemVariables;
	
	private double result;
	
	int lBound;
	int uBound; 
	
	/** Rank. */
	private int rank;
	
	public Individual() {
	}
	
	public Individual(String lowerBound, String upperBound, String size) {
		int varSize = Integer.parseInt(size);
		problemVariables = new double[varSize];
		lBound = Integer.parseInt(lowerBound);
		uBound = Integer.parseInt(upperBound);
		
		for(int i=0;i<varSize;i++) {
			double randDouble = RandomUtil.randDouble();
			double value = randDouble * (uBound - lBound) + lBound;
			problemVariables[i] = value;
		}
		
		
		
	}
	
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		for(double data : problemVariables){
			buffer.append(data).append(";");
		}
		return String.format("Data [%s]Result: %s Rank: %s", result, rank, buffer);
	}
	
	public Individual(Individual individual) {
		result = individual.getResult();
		problemVariables = individual.getProblemVariables();
		rank = individual.getRank();
		uBound = individual.getuBound();
		lBound = individual.getlBound();
	}

	/**
	 * Getter.
	 * @return the restult
	 */
	public double getResult() {
		return result;
	}

	/**
	 * Setter.
	 * @param restult the restult to set
	 */
	public void setResult(double restult) {
		this.result = restult;
	}

	/**
	 * Getter.
	 * @return the rank
	 */
	public int getRank() {
		return rank;
	}

	/**
	 * Setter.
	 * @param rank the rank to set
	 */
	public void setRank(int rank) {
		this.rank = rank;
	}

	/** {@inheritDoc} */
	@Override
	public int compareTo(Individual o) {
		return Double.valueOf(result).compareTo(Double.valueOf(o.getResult()));
	}

	/**
	 * Getter.
	 * @return the problemVariables
	 */
	public double[] getProblemVariables() {
		return problemVariables;
	}

	/**
	 * Setter.
	 * @param problemVariables the problemVariables to set
	 */
	public void setProblemVariables(double[] problemVariables) {
		this.problemVariables = problemVariables;
	}

	/**
	 * Getter.
	 * @return the lBound
	 */
	public int getlBound() {
		return lBound;
	}

	/**
	 * Setter.
	 * @param lBound the lBound to set
	 */
	public void setlBound(int lBound) {
		this.lBound = lBound;
	}

	/**
	 * Getter.
	 * @return the uBound
	 */
	public int getuBound() {
		return uBound;
	}

	/**
	 * Setter.
	 * @param uBound the uBound to set
	 */
	public void setuBound(int uBound) {
		this.uBound = uBound;
	}
	
	

	
}
