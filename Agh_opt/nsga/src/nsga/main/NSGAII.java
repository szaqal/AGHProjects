package nsga.main;

import java.io.Serializable;

import nsga.comparators.CrowdingComparator;
import nsga.core.PolynomialMutation;
import nsga.core.SBXCrossover;
import nsga.core.SimpleSelection;
import nsga.core.Individual;
import nsga.core.Population;
import nsga.problems.Problem;
import nsga.utils.Distance;
import nsga.utils.Ranking;

/** 
 * Implementacja algorytmu NSGAII
 * @author malczyk.pawel@gmail.com 
 */
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
	public Population execute() throws Exception {
		
		int evaluations= 0;
		double probability= 1.0/problem.getN();
		
		PolynomialMutation mutation = new PolynomialMutation(20);
		SBXCrossover crossoverOperator = new SBXCrossover(20);
		SimpleSelection selection = new SimpleSelection(); ;
		Distance distance = new Distance();
		Population population = new Population(POPULATION_SIZE);

		// Tworzenie pocztkowej populacji (pierwszej generacji !) i pierwszy przebieg
		Individual newIndividual;
		for (int i = 0; i < POPULATION_SIZE; i++) {
			newIndividual = new Individual(problem);
			problem.evaluate(newIndividual);
			evaluations++;
			population.add(newIndividual);
		} 

		// Kolejne pokolenia.
		while (evaluations < MAX_EVALUATIONS) {

			// potomkowie
			Population offspringPopulation = new Population(POPULATION_SIZE);
			Individual[] parents = new Individual[2];
			
			for (int i = 0; i < (POPULATION_SIZE / 2); i++) {
				if (evaluations < MAX_EVALUATIONS) {
					// dobor rodzicow.
					parents[0] = selection.select(population);
					parents[1] = selection.select(population);
					// krzyzowanie i ktore zwraca potomostwo.
					Individual[] offSpring = (Individual[]) crossoverOperator.doCrossover(0.9, parents[0], parents[1]);
					mutation.doMutation(probability, offSpring[0]);
					mutation.doMutation(probability, offSpring[1]);
					problem.evaluate(offSpring[0]);
					problem.evaluate(offSpring[1]);
					offspringPopulation.add(offSpring[0]);
					offspringPopulation.add(offSpring[1]);
					evaluations += 2;
				} 
			} 
			preformMainLoop(distance, population, offspringPopulation);
		} 

		// Zwrocenie niezdominowanego frontu jako zbioru rozwiazan
		Ranking ranking = new Ranking(population);
		return ranking.getSubfront(0);
	}

	/** To jest metoda realizaujaca main loop opisu (p 3.4) */
	private void preformMainLoop(Distance distance, Population population, Population offspringPopulation) {
		//Punkt 3.4 opisu Main loop
		// laczenie zbiou populacji osobnikow i potomstwa
		Population union = ((Population) population).union(offspringPopulation);

		// Wykonanie fast non-dominating-sort
		Ranking ranking = new Ranking(union);

		int remain = POPULATION_SIZE;
		int index = 0;
		Population front = null;
		population.clear();

		// Obtain the next front
		front = ranking.getSubfront(index);

		while ((remain > 0) && (remain >= front.size())) {
		
			distance.assignCrowdingDistance(front, problem.getNumberOfResults());
			for (int k = 0; k < front.size(); k++) {
				population.add(front.get(k));
			} 
			
			remain -= front.size();
			index++;
			if (remain > 0) {
				front = ranking.getSubfront(index);
			}
		} 

		if (remain > 0) { 
			distance.assignCrowdingDistance(front, problem.getNumberOfResults());
			front.sort(new CrowdingComparator());
			for (int k = 0; k < remain; k++) {
				population.add(front.get(k));
			}
			remain = 0;
		}
	}
}
