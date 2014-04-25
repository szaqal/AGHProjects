package jmetal.util;

import jmetal.base.SolutionSet;
import jmetal.base.operator.comparator.ObjectiveComparator;


public class Distance {

	/** Konstruktor.. */
	public Distance() { }

	/**
	 * Strona 5 opisu !!
	 * Oblicza crowding distance dla wszystkich rozwiazan w zbiorze
	 */
	public void assignCrowdingDistance(SolutionSet solutionSet, int nObjs) {
		
		int size = solutionSet.size();

		if (size == 0)
			return;

		if (size == 1) {
			solutionSet.get(0).setCrowdingDistance(Double.POSITIVE_INFINITY);
			return;
		} 

		if (size == 2) {
			solutionSet.get(0).setCrowdingDistance(Double.POSITIVE_INFINITY);
			solutionSet.get(1).setCrowdingDistance(Double.POSITIVE_INFINITY);
			return;
		}

		//The crowdng distance of i-th solution is in front (Z opisu)
		SolutionSet front = new SolutionSet(size);
		for (int i = 0; i < size; i++) {
			front.add(solutionSet.get(i));
		}

		//wyzerowanie (set  I[i]distance=0)
		for (int i = 0; i < size; i++)  {
			front.get(i).setCrowdingDistance(0.0);
		}

		double objetiveMaxn;
		double objetiveMinn;
		double distance;

		for (int i = 0; i < nObjs; i++) {
			
			// Sort(I) m pososrtowanie po objective m
			front.sort(new ObjectiveComparator(i));
			objetiveMinn = front.get(0).getObjective(i);
			objetiveMaxn = front.get(front.size() - 1).getObjective(i);

			//I[1]distance = I[l]distance = infinity
			front.get(0).setCrowdingDistance(Double.POSITIVE_INFINITY);
			front.get(size - 1).setCrowdingDistance(Double.POSITIVE_INFINITY);

			//wlasciwe obliczenie
			for (int j = 1; j < size - 1; j++) {
				distance = front.get(j + 1).getObjective(i) - front.get(j - 1).getObjective(i);
				distance = distance / (objetiveMaxn - objetiveMinn);
				distance += front.get(j).getCrowdingDistance();
				front.get(j).setCrowdingDistance(distance);
			}
		} 
	} 
}

