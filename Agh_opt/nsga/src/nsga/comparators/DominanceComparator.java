package nsga.comparators;

import java.util.Comparator;

import nsga.core.Individual;

/**
 * Komparator dominanacji.
 * 
 * @author malczyk.pawel@gmail.com
 */
public class DominanceComparator implements Comparator<Individual> {

	/**
	 * Porowonoje dwa osobniki pod katem zdominowania
	 * 
	 * @param object1
	 *            Pierwsze rozwiazanie
	 * @param object2
	 *            Drugie rowziazanie.
	 * @return -1 individual1 dominuje individual2, 0,oba nie zdominowane lub 1 or individual1 jest zdominowane przez individual2.
	 */
	public int compare(Individual object1, Individual object2) {
		if (object1 == null)
			return 1;
		else if (object2 == null)
			return -1;

		int dominance1; 
		int dominance2;
		Individual ind1 = object1;
		Individual ind2 = object2;

		dominance1 = 0;
		dominance2 = 0;

		int compareResult; 

		double value1, value2;
		for (int i = 0; i < ind1.numberOfResults(); i++) {
			value1 = ind1.getResult(i);
			value2 = ind2.getResult(i);
			if (value1 < value2) {
				compareResult = -1;
			} else if (value1 > value2) {
				compareResult = 1;
			} else {
				compareResult = 0;
			}

			if (compareResult == -1) {
				dominance1 = 1;
			}

			if (compareResult == 1) {
				dominance2 = 1;
			}
		}

		if (dominance1 == dominance2) {
			return 0; 
		}
		if (dominance1 == 1) {
			return -1; 
		}
		return 1;
	}
} 
