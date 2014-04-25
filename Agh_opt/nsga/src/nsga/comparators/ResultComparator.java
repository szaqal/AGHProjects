package nsga.comparators;

import java.util.Comparator;

import nsga.core.Individual;

/**
 * Komparartor wartosci rozwiazania.
 * 
 * @author malczyk.pawel@gmail.com
 */
public class ResultComparator implements Comparator<Individual> {

	/**
	 * Stores the index of the objective to compare
	 */
	private int objIndex;

	/**
	 * Konstruktor.
	 */
	public ResultComparator(int nObj) {
		this.objIndex = nObj;
	}

	/** {@inheritDoc} */
	public int compare(Individual o1, Individual o2) {
		if (o1 == null)
			return 1;
		else if (o2 == null)
			return -1;

		double objetive1 = o1.getResult(this.objIndex);
		double objetive2 = o2.getResult(this.objIndex);
		if (objetive1 < objetive2) {
			return -1;
		} else if (objetive1 > objetive2) {
			return 1;
		} else {
			return 0;
		}
	}
}
