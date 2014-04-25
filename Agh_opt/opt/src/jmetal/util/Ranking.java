package jmetal.util;

import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import jmetal.base.Solution;
import jmetal.base.SolutionSet;
import jmetal.base.operator.comparator.DominanceComparator;

/**
 * This class implements some facilities for ranking solutions. Given a
 * <code>SolutionSet</code> object, their solutions are ranked according to
 * scheme proposed in NSGA-II; as a result, a set of subsets are obtained. The
 * subsets are numbered starting from 0 (in NSGA-II, the numbering starts from
 * 1); thus, subset 0 contains the non-dominated solutions, subset 1 contains
 * the non-dominated solutions after removing those belonging to subset 0, and
 * so on.
 */
public class Ranking {

	/** Polaczone zbiory przez union */
	private SolutionSet solutionSet;

	/** Tablica przechowuje wszystkie fronty znalezione. */
	private SolutionSet[] ranking;

	/**
	 * stores a <code>Comparator</code> for dominance checking
	 */
	private static final Comparator<Solution> dominanceComparator = new DominanceComparator();

	/** Konstruktor. */
	@SuppressWarnings("unchecked")
	public Ranking(SolutionSet slnSet) {
		solutionSet = slnSet;

		// dominateMe[i] contains the number of solutions dominating i
		int[] dominated = new int[solutionSet.size()];

		// iDominate[k] contains the list of solutions dominated by k
		List<Integer>[] dominating = new List[solutionSet.size()];

		// front[i] contains the list of individuals belonging to the front i
		List<Integer>[] front = new List[solutionSet.size() + 1];

		// wynik porowaniania rozwiazan pod katem dominancji
		int dominanceCompareResult;

		// Initialize the fronts
		for (int i = 0; i < front.length; i++) {
			front[i] = new LinkedList<Integer>();
		}

		// -> Fast non dominated sorting algorithm
		for (int p = 0; p < solutionSet.size(); p++) {
			// Initialice the list of individuals that i dominate and the number
			// of individuals that dominate me
			dominating[p] = new LinkedList<Integer>();
			dominated[p] = 0;
			
			//Kazdy z kazy p i q ktory dominuje nad ktorym
			for (int q = 0; q < solutionSet.size(); q++) {
				
				dominanceCompareResult = dominanceComparator.compare(slnSet.get(p), slnSet.get(q));
				
				if (dominanceCompareResult == -1) {
					dominating[p].add(new Integer(q));
				} else if (dominanceCompareResult == 1) {
					dominated[p]++;
				}
			}

			// p nie jest zdominowane trafia do frontu
			if (dominated[p] == 0) {
				front[0].add(new Integer(p));
				slnSet.get(p).setRank(0);
			}
		}

		// Obtain the rest of fronts
		int i = 0;
		Iterator<Integer> it1, it2; 
		while (front[i].size() != 0) {
			i++;
			it1 = front[i - 1].iterator();
			while (it1.hasNext()) {
				it2 = dominating[it1.next().intValue()].iterator();
				while (it2.hasNext()) {
					int index = it2.next().intValue();
					dominated[index]--;
					if (dominated[index] == 0) {
						front[i].add(new Integer(index));
						solutionSet.get(index).setRank(i);
					}
				}
			}
		}
		// <-

		ranking = new SolutionSet[i];
		// 0,1,2,....,i-1 are front, then i fronts
		for (int j = 0; j < i; j++) {
			ranking[j] = new SolutionSet(front[j].size());
			it1 = front[j].iterator();
			while (it1.hasNext()) {
				ranking[j].add(slnSet.get(it1.next().intValue()));
			}
		}

	}

	/** Getter. */
	public SolutionSet getSubfront(int rank) {
		return ranking[rank];
	}

} 
