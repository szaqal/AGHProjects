package jmetal.base.operator.selection;

import jmetal.base.Solution;
import jmetal.base.SolutionSet;
import jmetal.util.RandomUtil;

/**
 * Klasa obiektor dokonujacych selecji
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
	public Solution select(SolutionSet population) {

		int index1 = RandomUtil.randInt(population.size());
		int index2 = RandomUtil.randInt(population.size());

		Solution solution1, solution2;
		solution1 = population.get(index1);
		solution2 = population.get(index2);

		return (RandomUtil.randDouble() < 0.5) ? solution1 : solution2;
	}
} 
