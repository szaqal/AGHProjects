package genetic.tasks;

import genetic.Individual;

import java.util.Collections;
import java.util.List;

import api.annotations.Value;
import api.computation.ComputableTask;

public class NonRoutableRanking implements ComputableTask {
	
	@Value(name="population")
	private List<Individual> population;

	@Override
	public void doComputation() {
		Collections.sort(population);
		int i=population.size();
		for(Individual individual:population) {
			individual.setRank(i);
			i--;
		}
	}

	/**
	 * Getter.
	 * @return the population
	 */
	public List<Individual> getPopulation() {
		return population;
	}

	/**
	 * Setter.
	 * @param population the population to set
	 */
	public void setPopulation(List<Individual> population) {
		this.population = population;
	}

}
