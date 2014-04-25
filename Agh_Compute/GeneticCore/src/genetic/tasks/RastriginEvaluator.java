package genetic.tasks;

import genetic.Individual;

import java.util.List;

import api.annotations.Computable;
import api.annotations.Partitionable;
import api.annotations.Value;
import api.computation.ComputableTask;

@Computable(taskName="RastriginEvaluator")
public class RastriginEvaluator implements ComputableTask {
	
	@Partitionable
	@Value(name="population")
	private List<Individual> population;

	/** {@inheritDoc} */
	@Override
	public void doComputation() {
		for(Individual individual : population) {
			double res = 0.0;
			double [] variables = individual.getProblemVariables();
			res = 10;
			for(int i=0,j=1;i<variables.length;i++,j++) {
				int a = 10 * i;
				res+=Math.pow(variables[i], 2) - 10 * Math.cos(2*Math.PI*variables[i]);
				res = a + res;
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
