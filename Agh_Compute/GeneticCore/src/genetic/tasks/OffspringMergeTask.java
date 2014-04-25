package genetic.tasks;

import genetic.Individual;

import java.util.Collection;

import api.annotations.Computable;
import api.annotations.Value;
import api.computation.ComputableTask;

@Computable(taskName="PopulationMergeTask")
public class OffspringMergeTask implements ComputableTask {
	
	@Value(name="population")
	private Collection<Individual> population;
	
	@Value(name="offspring")
	private Collection<Individual> offspring;

	@Override
	public void doComputation() {
		population.addAll(offspring);
	}

	/**
	 * Getter.
	 * @return the population
	 */
	public Collection<Individual> getPopulation() {
		return population;
	}

	/**
	 * Setter.
	 * @param population the population to set
	 */
	public void setPopulation(Collection<Individual> population) {
		this.population = population;
	}

	/**
	 * Getter.
	 * @return the offspring
	 */
	public Collection<Individual> getOffspring() {
		return offspring;
	}

	/**
	 * Setter.
	 * @param offspring the offspring to set
	 */
	public void setOffspring(Collection<Individual> offspring) {
		this.offspring = offspring;
	}

}
