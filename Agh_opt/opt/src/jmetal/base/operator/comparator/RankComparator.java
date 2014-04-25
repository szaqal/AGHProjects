package jmetal.base.operator.comparator;

import jmetal.base.Solution;
import java.util.Comparator;

/**
 * Porownywacz po ranku.
 */
public class RankComparator implements Comparator<Solution> {

	/** {@inheritDoc} */
	public int compare(Solution o1, Solution o2) {

		if (o1 == null) {
			return 1;
		} else if (o2 == null) {
			return -1;
		}

		Solution solution1 = o1;
		Solution solution2 = o2;
		if (solution1.getRank() < solution2.getRank()) {
			return -1;
		}

		if (solution1.getRank() > solution2.getRank()) {
			return 1;
		}

		return 0;
	}
}
