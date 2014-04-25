package genetic.tasks;

import genetic.Individual;

import java.util.List;

import api.annotations.Computable;
import api.annotations.Partitionable;
import api.annotations.Value;
import api.computation.ComputableTask;
import api.computation.ComputationContext;

/**
 * 
 * @author malczyk.pawel@gmail.com
 *
 */
@Computable(taskName="DemeTask")
public class DemeTask implements ComputableTask {
	
	
	@Partitionable
	@Value(name="population")
	private List<Individual> population;
	
	@api.annotations.ComputationContext
	private ComputationContext computationContext;

	/**{@inheritDoc} */
	@Override
	public void doComputation() {
		int populationSize = population.size();
		String iterations = computationContext.getSetting("generationsCount");
		int maxIter = Integer.parseInt(iterations == null ? "0" : iterations);
		ChooseParentsTask chooseParents = new ChooseParentsTask();
		CrossoverTask crossoverTask = new CrossoverTask();
		MutationTask mutationTask = new MutationTask();
		DeJongEvaluator deJongEvaluator = new DeJongEvaluator();
		RankingTask rankingTask = new RankingTask();
		for(int i=0;i<maxIter;i++) {
			chooseParents.setPopulation(population);
			chooseParents.doComputation();
			Individual parent1 = chooseParents.getFirstParent();
			Individual parent2 = chooseParents.getSecondParent();
			crossoverTask.setFirstParent(parent1);
			crossoverTask.setSecondParent(parent2);
			crossoverTask.doComputation();
			mutationTask.setIndividuals(crossoverTask.getOffspring());
			mutationTask.doComputation();
			population.addAll(mutationTask.getIndividuals());
			deJongEvaluator.setPopulation(population);
			deJongEvaluator.doComputation();
			population = deJongEvaluator.getPopulation();
			rankingTask.setPopulation(population);
			rankingTask.doComputation();
			population = rankingTask.getPopulation();
			while(population.size()>populationSize) {
				population.remove(populationSize-1);
			}
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

}
