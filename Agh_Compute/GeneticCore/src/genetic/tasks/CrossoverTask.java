package genetic.tasks;

import genetic.Individual;
import genetic.util.RandomUtil;

import java.util.ArrayList;
import java.util.List;

import api.annotations.Computable;
import api.annotations.Value;
import api.computation.ComputableTask;

/**
 * @author malczyk.pawel@gmail.com
 *
 */
@Computable(taskName="CrossoverTask")
public class CrossoverTask implements ComputableTask{
	
	/** DEFAULT_INDEX_CROSSOVER defines a default index crossover */
	public static final double DEFAULT_INDEX_CROSSOVER = 20.0;

	/** Minimalna roznica pomiedzy wartosciami zeby moglo zaistniec krzyzowanie. */
	private static final double EPS = 1.0e-14;
	
	@Value(name="parent1")
	private Individual firstParent;
	
	@Value(name="parent2")
	private Individual secondParent;
	
	@Value(name="offspring1")
	private Individual firstOffspring;
	
	@Value(name="offspring2")
	private Individual secondOffspring;
	
	@Value(name="offspring")
	private List<Individual> offspring;
	
	@Value(name="probability")
	private Double probability = 0.8;
	

	/**
	 * Indek uzwany w procesie krzyzowania.
	 */
	private double eta_c;
	
	public CrossoverTask() {
		eta_c = DEFAULT_INDEX_CROSSOVER;
	}
	
	/** {@inheritDoc} */
	public void doComputation() {

		firstOffspring = new Individual(firstParent);
		secondOffspring = new Individual(secondParent);

		int i;
		double rand;
		double y1, y2, yL, yu;
		double c1, c2;
		double alpha, beta, betaq;
		double valueX1, valueX2;
		if (RandomUtil.randDouble() <= probability) {
			
			for (i = 0; i < firstParent.getProblemVariables().length; i++) {
				valueX1 = firstParent.getProblemVariables()[i];
				valueX2 = secondParent.getProblemVariables()[i];
				
				if (RandomUtil.randDouble() <= 0.5) {

					if (java.lang.Math.abs(valueX1 - valueX2) > EPS) {

						if (valueX1 < valueX2) {
							y1 = valueX1;
							y2 = valueX2;
						} else {
							y1 = valueX2;
							y2 = valueX1;
						} 

						yL = firstParent.getlBound();
						yu = secondParent.getuBound();
						rand = RandomUtil.randDouble();
						beta = 1.0 + (2.0 * (y1 - yL) / (y2 - y1));
						alpha = 2.0 - java.lang.Math.pow(beta, -(eta_c + 1.0));

						if (rand <= (1.0 / alpha)) {
							betaq = java.lang.Math.pow((rand * alpha), (1.0 / (eta_c + 1.0)));
						} else {
							betaq = java.lang.Math.pow((1.0 / (2.0 - rand * alpha)), (1.0 / (eta_c + 1.0)));
						} // if

						c1 =( 0.5 * ((y1 + y2) - betaq * (y2 - y1)));
						beta = 1.0 + (2.0 * (yu - y2) / (y2 - y1));
						alpha = 2.0 - java.lang.Math.pow(beta, -(eta_c + 1.0));

						if (rand <= (1.0 / alpha)) {
							betaq = java.lang.Math.pow((rand * alpha), (1.0 / (eta_c + 1.0)));
						} else {
							betaq = java.lang.Math.pow((1.0 / (2.0 - rand * alpha)), (1.0 / (eta_c + 1.0)));
						} // if

						c2 =( 0.5 * ((y1 + y2) + betaq * (y2 - y1)));

						//zabezpieczenie dla prawidlowych wartosci
						if (c1 < yL)
							c1 = yL;

						if (c2 < yL)
							c2 = yL;

						if (c1 > yu)
							c1 = yu;

						if (c2 > yu)
							c2 = yu;

						if (RandomUtil.randDouble() <= 0.5) {
							firstOffspring.getProblemVariables()[i]=c2;
							secondOffspring.getProblemVariables()[i]=c1;
						} else {
							firstOffspring.getProblemVariables()[i]=c1;
							secondOffspring.getProblemVariables()[i]=c2;
						} // if
					} else {
						firstOffspring.getProblemVariables()[i]=valueX1;
						secondOffspring.getProblemVariables()[i]=valueX2;
					} // if
				} else {
					firstOffspring.getProblemVariables()[i]=valueX2;
					secondOffspring.getProblemVariables()[i]=valueX1;
				}
			}
		}
		offspring = new ArrayList<Individual>();
		offspring.add(firstOffspring);
		offspring.add(secondOffspring);
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
	 * @return the fisrtOffspring
	 */
	public Individual getFirstOffspring() {
		return firstOffspring;
	}

	/**
	 * Setter.
	 * @param fisrtOffspring the fisrtOffspring to set
	 */
	public void setFirstOffspring(Individual fisrtOffspring) {
		this.firstOffspring = fisrtOffspring;
	}

	/**
	 * Getter.
	 * @return the secondOffspring
	 */
	public Individual getSecondOffspring() {
		return secondOffspring;
	}

	/**
	 * Setter.
	 * @param secondOffspring the secondOffspring to set
	 */
	public void setSecondOffspring(Individual secondOffspring) {
		this.secondOffspring = secondOffspring;
	}

	/**
	 * Getter.
	 * @return the probability
	 */
	public Double getProbability() {
		return probability;
	}

	/**
	 * Setter.
	 * @param probability the probability to set
	 */
	public void setProbability(Double probability) {
		this.probability = probability;
	}

	/**
	 * Getter.
	 * @return the offspring
	 */
	public List<Individual> getOffspring() {
		return offspring;
	}

	/**
	 * Setter.
	 * @param offspring the offspring to set
	 */
	public void setOffspring(List<Individual> offspring) {
		this.offspring = offspring;
	}

}
