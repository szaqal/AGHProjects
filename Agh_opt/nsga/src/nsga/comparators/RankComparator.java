package nsga.comparators;

import java.util.Comparator;

import nsga.core.Individual;

/**
 * Porownywacz po ranku.
 * 
 * @author malczyk.pawel@gmail.com
 */
public class RankComparator implements Comparator<Individual> {

	/** {@inheritDoc} */
	public int compare(Individual o1, Individual o2) {

		if (o1 == null) {
			return 1;
		} else if (o2 == null) {
			return -1;
		}

		Individual ind1 = o1;
		Individual ind2 = o2;
		if (ind1.getRank() < ind2.getRank()) {
			return -1;
		}

		if (ind1.getRank() > ind2.getRank()) {
			return 1;
		}

		return 0;
	}
}
