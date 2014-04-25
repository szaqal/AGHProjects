package nsga.utils;

import nsga.comparators.ResultComparator;
import nsga.core.Population;


/**
 * Wielkosc o nazwie Crowding Distance ((i)distance) okresla najwiekszy<br> 
 * cuboid obiejmujacy punkt i i zaden inny.(see opis)
 * 
 * 
 * @author malczyk.pawel@gmail.com
 *
 */
public class Distance {

	/** Konstruktor.. */
	public Distance() { }

	/**
	 * Strona 5 opisu !!
	 * Oblicza crowding distance dla wszystkich rozwiazan w zbiorze
	 */
	public void assignCrowdingDistance(Population population, int nObjs) {
		
		int size = population.size();

		if (size == 0)
			return;

		if (size == 1) {
			population.get(0).setCrowdingDistance(Double.POSITIVE_INFINITY);
			return;
		} 

		if (size == 2) {
			population.get(0).setCrowdingDistance(Double.POSITIVE_INFINITY);
			population.get(1).setCrowdingDistance(Double.POSITIVE_INFINITY);
			return;
		}

		//The crowdng distance of i-th individual is in front (Z opisu)
		Population front = new Population(size);
		for (int i = 0; i < size; i++) {
			front.add(population.get(i));
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
			front.sort(new ResultComparator(i));
			objetiveMinn = front.get(0).getResult(i);
			objetiveMaxn = front.get(front.size() - 1).getResult(i);

			//I[1]distance = I[l]distance = infinity
			front.get(0).setCrowdingDistance(Double.POSITIVE_INFINITY);
			front.get(size - 1).setCrowdingDistance(Double.POSITIVE_INFINITY);

			//wlasciwe obliczenie
			for (int j = 1; j < size - 1; j++) {
				distance = front.get(j + 1).getResult(i) - front.get(j - 1).getResult(i);
				distance = distance / (objetiveMaxn - objetiveMinn);
				distance += front.get(j).getCrowdingDistance();
				front.get(j).setCrowdingDistance(distance);
			}
		} 
	} 
}

