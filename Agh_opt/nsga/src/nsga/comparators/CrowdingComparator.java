package nsga.comparators;

import java.util.Comparator;

import nsga.core.Individual;

/**
 * Komparator po crowdingu.
 * Ogolnie powienien dzialac o rankach wg. opisu (3.3 Crowded Comparation Operator.)
 * 
 * @author malczyk.pawel@gmail.com
 */
public class CrowdingComparator implements Comparator<Individual> {

	/** Komparator po ranku */
	private static final Comparator<Individual> comparator = new RankComparator();

	/** {@inheritDoc} */
	public int compare(Individual o1, Individual o2) {
		if (o1 == null) {
			return 1;
		} else if (o2 == null) {
			return -1;
		}

		int rankCompareResult = comparator.compare(o1, o2);
		if (rankCompareResult != 0)
			return rankCompareResult;

	
		double distance1 = o1.getCrowdingDistance();
		double distance2 = o2.getCrowdingDistance();
		if (distance1 > distance2) {
			return -1;
		}

		if (distance1 < distance2) {
			return 1;
		}

		return 0;
	}
}
