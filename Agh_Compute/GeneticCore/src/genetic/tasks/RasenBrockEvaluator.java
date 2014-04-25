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
 * 		f2(x)=sum(100·(x(i+1)-x(i)^2)^2+(1-x(i))^2), i=1:n-1;
 *		-2.048<=x(i)<=2.048.
 * 
 * global minimum
 * 		f(x)=0; x(i)=1, i=1:n.
 *
 */
@Computable(taskName="RasenbrockEvaluator")
public class RasenBrockEvaluator implements ComputableTask {

	@Partitionable
	@Value(name="population")
	private List<Individual> population;
	
	/** {@inheritDoc} */
	@Override
	public void doComputation() {
		for(Individual individual : population) {
			double res = 0.0f;
			double [] problemVars = individual.getProblemVariables();
			for(int i=0,j=1;i<problemVars.length-1;i++,j++) {
				res += 100*Math.pow(Math.pow(problemVars[i], 2)-problemVars[j], 2)+Math.pow(problemVars[i], 2);
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
