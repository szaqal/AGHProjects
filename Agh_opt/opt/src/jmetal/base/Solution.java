package jmetal.base;

import java.io.Serializable;

/**
 * Reprezentuje ona potencjalne rozwiązanie, posiadające pewną miarę swojej wartości, 
 * wyliczaną przy pomocy funkcji dopasowania.
 */
public class Solution implements Serializable {

	/** Serial. */
	private static final long serialVersionUID = 8458216795302135603L;

	/** Zmienne (xi) i=i,1,2,... */
	private ProblemVariables problemVariables;

	/** Stores the objectives values of the solution. */
	private double[] objective;

	/** Stores the number of objective values of the solution */
	private int numberOfObjectives;

	/** Stores the so called rank of the solution. Used in NSGA-II  */
	private int rank;

	/** Crowding distance (Idistance) */
	private double crowdingDistance;

	/** Konstruktor. */
	public Solution(Problem problem) {
		numberOfObjectives = problem.getNumberOfObjectives();
		objective = new double[numberOfObjectives];
		crowdingDistance = 0.0;
		problemVariables = new ProblemVariables(problem);
	}

	/** Konstruktor. */
	public Solution(Solution solution) {

		numberOfObjectives = solution.numberOfObjectives();
		objective = new double[numberOfObjectives];
		for (int i = 0; i < objective.length; i++) {
			objective[i] = solution.getObjective(i);
		}
		problemVariables = new ProblemVariables(solution.getProblemVariables());
		crowdingDistance = solution.getCrowdingDistance();
		rank = solution.getRank();
	} 


	/** Setter. */
	public void setCrowdingDistance(double distance) {
		crowdingDistance = distance;
	} 

	/** Getter. */
	public double getCrowdingDistance() {
		return crowdingDistance;
	} 

	/** Setter. */
	public void setObjective(int i, double value) {
		objective[i] = value;
	} 

	/** Getter.*/
	public double getObjective(int i) {
		return objective[i];
	} 

	/** Getter. */
	public int numberOfObjectives() {
		if (objective == null)
			return 0;
		else
			return numberOfObjectives;
	} 

	/** {@inheritDoc} */
	public String toString() {
		String aux = "";
		for (int i = 0; i < this.numberOfObjectives; i++)
			aux = aux + this.getObjective(i) + " ";

		return aux;
	}

	/** Getter. */
	public ProblemVariables getProblemVariables() {
		return this.problemVariables;
	}


	/**Setter. */
	public void setRank(int value) {
		this.rank = value;
	}

	/** Getter. */
	public int getRank() {
		return this.rank;
	}
}
