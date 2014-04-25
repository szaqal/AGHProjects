package nsga.utils;

import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import nsga.comparators.DominanceComparator;
import nsga.core.Individual;
import nsga.core.Population;

/**
 * Tu sa identyfikowane fronty, oraz fast non-dominated sort.
 * 
 * @author malczyk.pawel@gmail.com
 */
public class Ranking {

	/** Polaczone zbiory przez union (rodzice + dzieci)*/
	private Population population;

	/** Tablica przechowuje wszystkie fronty znalezione. */
	private Population[] ranking;

	/** Komparator. */
	private static final Comparator<Individual> dominanceComparator = new DominanceComparator();

	/** Konstruktor. */
	@SuppressWarnings("unchecked")
	public Ranking(Population pop) {
		population = pop;

		int[] qDominatesP = new int[population.size()];

		List<Integer>[] pDominatesQ = new List[population.size()];

		List<Integer>[] front = new List[population.size() + 1];

		int dominanceCompareResult;

		for (int i = 0; i < front.length; i++) {
			front[i] = new LinkedList<Integer>();
		}

		// Wlasciwy algorytm Fast non dominated sort
		for (int p = 0; p < population.size(); p++) {
			pDominatesQ[p] = new LinkedList<Integer>();
			qDominatesP[p] = 0;
			
			//Kazdy z kazy p i q ktory dominuje nad ktorym
			for (int q = 0; q < population.size(); q++) {
				dominanceCompareResult = dominanceComparator.compare(pop.get(p), pop.get(q));
				if (dominanceCompareResult == -1) {
					pDominatesQ[p].add(new Integer(q));
				} else if (dominanceCompareResult == 1) {
					qDominatesP[p]++;
				}
			}
			// p nie jest zdominowane prze q i vice versa trafia do frontu
			if (qDominatesP[p] == 0) {
				front[0].add(p);
				pop.get(p).setRank(0);
			}
		}

		int i = 0;
		Iterator<Integer> it1, it2; 
		while (front[i].size() != 0) {
			i++;
			it1 = front[i - 1].iterator();
			while (it1.hasNext()) {
				it2 = pDominatesQ[it1.next()].iterator();
				while (it2.hasNext()) {
					int index = it2.next();
					qDominatesP[index]--;
					if (qDominatesP[index] == 0) {
						front[i].add(new Integer(index));
						population.get(index).setRank(i);
					}
				}
			}
		}

		ranking = new Population[i];
		for (int j = 0; j < i; j++) {
			ranking[j] = new Population(front[j].size());
			it1 = front[j].iterator();
			while (it1.hasNext()) {
				ranking[j].add(pop.get(it1.next()));
			}
		}

	}

	/** Getter. */
	public Population getSubfront(int rank) {
		return ranking[rank];
	}

} 
