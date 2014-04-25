package jmetal.metaheuristics.nsgaII;

import java.io.Serializable;

import jmetal.base.Problem;
import jmetal.base.Solution;
import jmetal.base.SolutionSet;
import jmetal.base.operator.crossover.SBXCrossover;
import jmetal.base.operator.mutation.PolynomialMutation;
import jmetal.base.operator.selection.SimpleSelection;
import jmetal.util.Distance;
import jmetal.util.Ranking;

/** Implementacja algorytmu NSGAII */
public class NSGAII implements Serializable {
	
	/** Wielkosc popuacji */
	public static final int POPULATION_SIZE = 100;
	
	/** Maksymalna ilosc evaulacji. */
	public static final int MAX_EVALUATIONS = 25000;

	/** Serial. */
	private static final long serialVersionUID = 4970928169851043408L;
	
	/** Rozwiazywany problem */
	private Problem problem;

	/** Konstruktor */
	public NSGAII(Problem problem) {
		this.problem = problem;
	}

	/** Obliczanie algorytmu. */
	public SolutionSet execute() throws Exception {
		
		int evaluations= 0;
		double probability= 1.0/problem.getN();
		
		PolynomialMutation mutation = new PolynomialMutation(20);
		SBXCrossover crossoverOperator = new SBXCrossover(20);
		SimpleSelection selection = new SimpleSelection(); ;

		Distance distance = new Distance();
		SolutionSet population = new SolutionSet(POPULATION_SIZE);

		// Tworzenie pocztkowej populacji (pierwszej generacji !) i pierwszy przebieg
		Solution newSolution;
		for (int i = 0; i < POPULATION_SIZE; i++) {
			newSolution = new Solution(problem);
			problem.evaluate(newSolution);
			evaluations++;
			population.add(newSolution);
		} 

		// Kolejne pokolenia.
		while (evaluations < MAX_EVALUATIONS) {

			// potomkowie
			SolutionSet offspringPopulation = new SolutionSet(POPULATION_SIZE);
			Solution[] parents = new Solution[2];
			
			for (int i = 0; i < (POPULATION_SIZE / 2); i++) {
				if (evaluations < MAX_EVALUATIONS) {
					// dobor rodzicow.
					parents[0] = selection.select(population);
					parents[1] = selection.select(population);
					// krzyzowanie i ktore zwraca potomostwo.
					Solution[] offSpring = (Solution[]) crossoverOperator.doCrossover(0.9, parents[0], parents[1]);
					mutation.doMutation(probability, offSpring[0]);
					mutation.doMutation(probability, offSpring[1]);
					problem.evaluate(offSpring[0]);
					problem.evaluate(offSpring[1]);
					offspringPopulation.add(offSpring[0]);
					offspringPopulation.add(offSpring[1]);
					evaluations += 2;
				} 
			} 

			// laczenie zbiou populacji osobnikow i potomstwa
			SolutionSet union = ((SolutionSet) population).union(offspringPopulation);

			// Ranking the union
			Ranking ranking = new Ranking(union);

			int remain = POPULATION_SIZE;
			int index = 0;
			SolutionSet front = null;
			population.clear();

			// Obtain the next front
			front = ranking.getSubfront(index);

			while ((remain > 0) && (remain >= front.size())) {
				
				// Assign crowding distance to individuals
				distance.assignCrowdingDistance(front, problem.getNumberOfObjectives());
				// Add the individuals of this front
				for (int k = 0; k < front.size(); k++) {
					population.add(front.get(k));
				} 
				
				remain -= front.size();
				index++;
				if (remain > 0) {
					front = ranking.getSubfront(index);
				}
			} 

			// Remain is less than front(index).size, insert only the best one
			if (remain > 0) { // front contains individuals to insert
				distance.assignCrowdingDistance(front, problem.getNumberOfObjectives());
				front.sort(new jmetal.base.operator.comparator.CrowdingComparator());
				for (int k = 0; k < remain; k++) {
					population.add(front.get(k));
				}
				remain = 0;
			}
		} 

		// Zwrocenie niezdominowanego frontu jako zbioru rozwiazan
		Ranking ranking = new Ranking(population);
		return ranking.getSubfront(0);
	}
}
