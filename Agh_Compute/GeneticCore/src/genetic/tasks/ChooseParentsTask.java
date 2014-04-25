package genetic.tasks;

import genetic.Individual;
import genetic.util.RandomUtil;

import java.util.List;

import api.annotations.Computable;
import api.annotations.Value;
import api.computation.ComputableTask;

/**
 * @author malczyk.pawel@gmail.com
 *
 */
@Computable(taskName="ParentChooser")
public class ChooseParentsTask implements ComputableTask {

	@Value(name="parent1")
	private Individual firstParent;
	
	@Value(name="parent2")
	private Individual secondParent;
	
	@Value(name="population")
	private List<Individual> population;
	

	/** {@inheritDoc} */
	@Override
	public void doComputation() {
		int index1 = RandomUtil.randInt(population.size());
		int index2 = RandomUtil.randInt(population.size());
		while(index1==index2) {
			index2 = RandomUtil.randInt(population.size());
		}
		firstParent = population.get(index1);
		secondParent = population.get(index2);
	}


	/**
	 * Getter.
	 * @return the firstParent
	 */
	public Individual getFirstParent() {
		return firstParent;
	}


	/**
	 * Setter.
	 * @param firstParent the firstParent to set
	 */
	public void setFirstParent(Individual firstParent) {
		this.firstParent = firstParent;
	}


	/**
	 * Getter.
	 * @return the secondParent
	 */
	public Individual getSecondParent() {
		return secondParent;
	}


	/**
	 * Setter.
	 * @param secondParent the secondParent to set
	 */
	public void setSecondParent(Individual secondParent) {
		this.secondParent = secondParent;
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
