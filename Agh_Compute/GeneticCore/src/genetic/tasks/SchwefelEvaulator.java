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
 *	f7(x)=sum(-x(i)·sin(sqrt(abs(x(i))))), i=1:n;
 * 	-500<=x(i)<=500.
 * 
 * global minimum
 * 		f(x)=-n·418.9829; x(i)=420.9687, i=1:n.
 *
 */
@Computable(taskName="SchwefelEvaulator")
public class SchwefelEvaulator implements ComputableTask {
	
	@Partitionable
	@Value(name="population")
	private List<Individual> population;

	@Override
	public void doComputation() {
		for(Individual individual : population) {
			double res = 0.0;
			double [] variables = individual.getProblemVariables();
			res = 10;
			for(int i=0,j=1;i<variables.length;i++,j++) {
				res+= -variables[i]*Math.sin(Math.sqrt(Math.abs(variables[i])));
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
