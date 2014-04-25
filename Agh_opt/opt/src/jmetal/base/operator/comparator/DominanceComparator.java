package jmetal.base.operator.comparator;

import jmetal.base.Solution;
import java.util.Comparator;

/**
 * Comparator dominanacji.
 */
public class DominanceComparator implements Comparator<Solution> {

	/**
	 * Porowonje dwa rozwiazanie pod katem zdominowania
	 * 
	 * @param object1
	 *            Pierwsze rozwiazanie
	 * @param object2
	 *            Drugir rowziazanie.
	 * @return -1 solution1 dominuje solution2, 0,oba nie zdominowane lub 1 or solution1 jest zdominowane przez solution2.
	 */
	public int compare(Solution object1, Solution object2) {
		if (object1 == null)
			return 1;
		else if (object2 == null)
			return -1;

		int dominate1; // dominate1 indicates if some objective of solution1
		// dominates the same objective in solution2. dominate2
		int dominate2; // is the complementary of dominate1.
		Solution solution1 = object1;
		Solution solution2 = object2;

		dominate1 = 0;
		dominate2 = 0;

		int flag; // stores the result of the comparation


		// Equal number of violated constraint. Apply a dominance Test
		double value1, value2;
		for (int i = 0; i < solution1.numberOfObjectives(); i++) {
			value1 = solution1.getObjective(i);
			value2 = solution2.getObjective(i);
			if (value1 < value2) {
				flag = -1;
			} else if (value1 > value2) {
				flag = 1;
			} else {
				flag = 0;
			}

			if (flag == -1) {
				dominate1 = 1;
			}

			if (flag == 1) {
				dominate2 = 1;
			}
		}

		if (dominate1 == dominate2) {
			return 0; 
		}
		if (dominate1 == 1) {
			return -1; 
		}
		return 1;
	}
} 
