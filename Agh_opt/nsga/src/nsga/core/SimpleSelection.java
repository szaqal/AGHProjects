package nsga.core;

import nsga.utils.RandomUtil;

/**
 * Klasa obiektor dokonujacych selecji
 * 
 * @author malczyk.pawel@gmail.com
 */
public class SimpleSelection {

	/** Serial. */
	private static final long serialVersionUID = 5757513222651885762L;

	/** Konstruktor. */
	public SimpleSelection() { } 

	/**
	 * Dokonuje selekcji osobnika z popualacji
	 * 
	 * @param population
	 *            populacja osobnikow z ktorej dokonywana jest selekcja 
	 * @return wybrany osobnik
	 */
	public Individual select(Population population) {

		int index1 = RandomUtil.randInt(population.size());
		int index2 = RandomUtil.randInt(population.size());

		Individual individual1, individual2;
		individual1 = population.get(index1);
		individual2 = population.get(index2);

		return (RandomUtil.randDouble() < 0.5) ? individual1 : individual2;
	}
} 
