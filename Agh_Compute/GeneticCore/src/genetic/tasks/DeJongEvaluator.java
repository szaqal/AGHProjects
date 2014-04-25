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
 * f1a(x)=sum(i·x(i)^2), i=1:n;
 * -5.12<=x(i)<=5.12.
 *
 * global minimum
 *   f(x)=0; x(i)= 0, i=1:n.
 *
 */
@Computable(taskName="DeJongEvaluator")
public class DeJongEvaluator implements ComputableTask {
	
	@Partitionable
	@Value(name="population")
	private List<Individual> population;

	
	/** {@inheritDoc} */
	public void doComputation() {
		for (Individual individual : population) {
			double res = 0.0;
			// f1(x)=sum(x(i)^2), i=1:n, -5.12<=x(i)<=5.12.
			double[] problemVars = individual.getProblemVariables();
			for (double var : problemVars) {
				res += Math.pow(var, 2);
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
