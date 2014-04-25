package nsga.core;

import java.io.Serializable;

import nsga.problems.Problem;

/**
 * Reprezentuje ona potencjalne rozwiązanie, posiadające pewną miarę swojej wartości.
 * 
 * @author malczyk.pawel@gmail.com
 */
public class Individual implements Serializable {

	/** Serial. */
	private static final long serialVersionUID = 8458216795302135603L;

	/** Zmienne (xi) i=i,1,2,... */
	private ProblemVariables problemVariables;

	/** Przchowuje wyniki. */
	private double[] results;

	/** Ilosc wynikow. */
	private int numberOfResults;

	/** Rank. */
	private int rank;

	/** "Crowding distance" czyli (Idistance) */
	private double crowdingDistance;

	/** Konstruktor. */
	public Individual(Problem problem) {
		numberOfResults = problem.getNumberOfResults();
		results = new double[numberOfResults];
		crowdingDistance = 0.0;
		problemVariables = new ProblemVariables(problem);
	}

	/** Konstruktor. */
	public Individual(Individual individual) {

		numberOfResults = individual.numberOfResults();
		results = new double[numberOfResults];
		for (int i = 0; i < results.length; i++) {
			results[i] = individual.getResult(i);
		}
		problemVariables = new ProblemVariables(individual.getProblemVariables());
		crowdingDistance = individual.getCrowdingDistance();
		rank = individual.getRank();
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
	public void setResult(int i, double value) {
		results[i] = value;
	} 

	/** Getter.*/
	public double getResult(int i) {
		return results[i];
	} 

	/** Getter. */
	public int numberOfResults() {
		if (results == null){
			return 0;
		} else {
			return numberOfResults;
		}
	} 

	/** {@inheritDoc} */
	public String toString() {
		String resut = "";
		for (int i = 0; i < this.numberOfResults; i++)
			resut = resut + this.getResult(i) + " ";

		return resut;
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
