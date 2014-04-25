package genetic.tasks;

import genetic.Individual;
import genetic.util.RandomUtil;

import java.util.List;

import api.annotations.Computable;
import api.annotations.Value;
import api.computation.ComputableTask;

/**
 * 
 * @see http://www.complexity.org.au/conference/upload/raghuw01/raghuw01.pdf
 * 
 * @author malczyk.pawel@gmail.com
 *
 */
@Computable(taskName="MutationTask")
public class MutationTask implements ComputableTask {
	
	/**
	 * DEFAULT_INDEX_MUTATION defines a default index for mutation
	 */
	public static final double DEFAULT_INDEX_MUTATION = 20.0;
	
	/** Indeks wykorzystywanu w obliczaniu mutacji. */
	private double eta_m;
	
	@Value(name="individuals")
	private List<Individual> individuals;
	
	@Value(name="probability")
	private double probability = 0.8;
	
	/**
	 * Constructor.
	 */
	public MutationTask() {
		eta_m = DEFAULT_INDEX_MUTATION;
	} 

	/** {@inheritDoc} */  
	public void doComputation() {
		for(Individual individual: individuals) {
			mutate(individual);
		}
	}
	
	public void mutate(Individual individual) {
		double rnd, delta1, delta2, mut_pow, deltaq;
		double y, yl, yu, val, xy;
		for (int var = 0; var < individual.getProblemVariables().length; var++) {
			if (RandomUtil.randDouble() <= probability) {
				y = individual.getProblemVariables()[var];
				yl = individual.getlBound();
				yu = individual.getuBound();
				delta1 = (y - yl) / (yu - yl);
				delta2 = (yu - y) / (yu - yl);
				rnd = RandomUtil.randDouble();
				mut_pow = (1.0 / (eta_m + 1.0));
				if (rnd <= 0.5) {
					xy = 1.0 - delta1;
					val = (2.0 * rnd + (1.0 - 2.0 * rnd) * (Math.pow(xy, (eta_m + 1.0))));
					deltaq = (java.lang.Math.pow(val, mut_pow) - 1.0);
				} else {
					xy = (1.0 - delta2);
					val = (2.0 * (1.0 - rnd) + 2.0 * (rnd - 0.5)* (java.lang.Math.pow(xy, (eta_m + 1.0))));
					deltaq = (1.0 - (java.lang.Math.pow(val, mut_pow)));
				}
				
				//wlasciwy wzor obliczenia mutacji wielomianowej
				y = y +  (yu - yl) * deltaq;
				
				//zeby miescilo sie w zadanych granicach
				if (y < yl)
					y = yl;
				if (y > yu)
					y = yu;
				individual.getProblemVariables()[var]=y;
			}
		}
	}

	/**
	 * Getter.
	 * @return the individuals
	 */
	public List<Individual> getIndividuals() {
		return individuals;
	}

	/**
	 * Setter.
	 * @param individuals the individuals to set
	 */
	public void setIndividuals(List<Individual> individuals) {
		this.individuals = individuals;
	}

	/**
	 * Getter.
	 * @return the probability
	 */
	public double getProbability() {
		return probability;
	}

	/**
	 * Setter.
	 * @param probability the probability to set
	 */
	public void setProbability(double probability) {
		this.probability = probability;
	}




}
