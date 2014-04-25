package jmetal.base.operator.comparator;

import jmetal.base.Solution;
import java.util.Comparator;

/**
 * Komparatpr po crowdingu
 */
public class CrowdingComparator implements Comparator<Solution> {

	/** stores a comparator for check the rank of solutions */
	private static final Comparator<Solution> comparator = new RankComparator();

	/** {@inheritDoc} */
	public int compare(Solution o1, Solution o2) {
		if (o1 == null)
			return 1;
		else if (o2 == null)
			return -1;

		int rankCompareResult = comparator.compare(o1, o2);
		if (rankCompareResult != 0)
			return rankCompareResult;

	
		double distance1 = o1.getCrowdingDistance();
		double distance2 = o2.getCrowdingDistance();
		if (distance1 > distance2)
			return -1;

		if (distance1 < distance2)
			return 1;

		return 0;
	}
}
