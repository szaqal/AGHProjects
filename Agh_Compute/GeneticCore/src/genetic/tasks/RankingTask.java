package genetic.tasks;

import genetic.Individual;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import api.annotations.Computable;
import api.annotations.Routable;
import api.annotations.RouteTo;
import api.annotations.Value;
import api.computation.ComputableTask;
import api.computation.ComputationContext;

@Computable(taskName="RankingTask")
@Routable
public class RankingTask implements ComputableTask {
	
	/**
	 * Computation context parameter.
	 */
	private static final String CURRENT_EPOCH = "current_epoch";
	
	@Value(name="population")
	private List<Individual> population;
	
	@Value(name="populationSize")
	private int populationSize;
	
	@api.annotations.ComputationContext
	private ComputationContext computationContext;

	@Override
	public void doComputation() {
		Collections.sort(population);
		int i=population.size();
		for(Individual individual:population) {
			individual.setRank(i);
			i--;
		}
		
		while(population.size()>populationSize) {
			population.remove(populationSize-1);
		}
	}
	
	/**
	 * Returns route directions.
	 * @return where to go to next
	 */
	@RouteTo
	public String route() {
		String currentEpoch = computationContext.getSetting(CURRENT_EPOCH);
		int current = Integer.parseInt(currentEpoch == null ? "0" : currentEpoch);
		String iterations = computationContext.getSetting("generationsCount");
		int maxIter = Integer.parseInt(iterations == null ? "0" : iterations);
		ArrayList<String> nextInput = computationContext.getPerformable().getNextInput("out_pop");
		String result = (current >= maxIter)?"end": nextInput.iterator().next();
		computationContext.incrementSetting(CURRENT_EPOCH);
		return result;
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

	/**
	 * Getter.
	 * @return the computationContext
	 */
	public ComputationContext getComputationContext() {
		return computationContext;
	}

	/**
	 * Setter.
	 * @param computationContext the computationContext to set
	 */
	public void setComputationContext(ComputationContext computationContext) {
		this.computationContext = computationContext;
	}

	/**
	 * Getter.
	 * @return the populationSize
	 */
	public int getPopulationSize() {
		return populationSize;
	}

	/**
	 * Setter.
	 * @param populationSize the populationSize to set
	 */
	public void setPopulationSize(int populationSize) {
		this.populationSize = populationSize;
	}

}
