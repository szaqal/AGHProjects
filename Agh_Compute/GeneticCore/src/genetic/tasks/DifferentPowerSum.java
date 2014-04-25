package genetic.tasks;

import genetic.Individual;

import java.util.List;

import api.annotations.Computable;
import api.annotations.Partitionable;
import api.annotations.Value;
import api.computation.ComputableTask;

/**
 * @author malczyk.pawel@gmail.com
 * 
 * function definition
 *		f9(x)=sum(abs(x(i))^(i+1)), i=1:n;
 *		-1<=x(i)<=1.
 *
 * global minimum
 * 		f(x)=0; x(i)=0, i=1:n.
 */
@Computable(taskName="DifferentPowerSum")
public class DifferentPowerSum implements ComputableTask{
	
	@Partitionable
	@Value(name="population")
	private List<Individual> population;

	/** {@inheritDoc} */
	@Override
	public void doComputation() {
		for(Individual individual : population) {
			double res = 0.0;
			double [] variables = individual.getProblemVariables();
			for(int i=0;i<variables.length;i++) {
				res+=Math.pow(Math.abs(variables[i]), i+1);
			}
			individual.setResult(res);
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
