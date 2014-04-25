package genetic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 * 
 * Population.
 * 
 * @author malczyk.pawel@gmail.com
 *
 */
public class Population implements Serializable{

	/**
	 * Serial.
	 */
	private static final long serialVersionUID = -6512502114524076943L;
	
	/** List of individual populations. */
	protected List<Individual> individualList;

	/** 
	 * Max capacity. 
	 */
	private int capacity = 0;

	/** 
	 * Constructor. 
	 */
	public Population() {
		individualList = new ArrayList<Individual>();
	}

	/** 
	 * Constructor.
	 * @param maximum population size. 
	 */
	public Population(int maximumSize) {
		individualList = new ArrayList<Individual>();
		capacity = maximumSize;
	} 

	/** 
	 * Adds individual to population.
	 * @param individual Individual being added to population 
	 */
	public boolean add(Individual individual) {
		if (individualList.size() == capacity) {
			System.err.println("Pelny");
			return false;
		}

		individualList.add(individual);
		return true;
	} 

	/** 
	 * Returns population individual.
	 * @return one individual
	 */
	public Individual get(int i) {
		if (i >= individualList.size()) {
			throw new IndexOutOfBoundsException("Poza zkresem " + i);
		}
		return individualList.get(i);
	} 


	/** Sortuje rozwiazania w zbiorze. */
	public void sort(Comparator<Individual> comparator) {
		if (comparator == null) {
			System.err.println("Brak komparatora.");
			return;
		}
		Collections.sort(individualList, comparator);
	}

	/** Zwraca rozmiar kolekcji */
	public int size() {
		return individualList.size();
	}

//	/**
//	 * Zapisuje wyniki w pliku.
//	 */
//	public void printResultsToFile(String path) {
//		try {
//	
//			FileOutputStream fos = new FileOutputStream(path);
//			OutputStreamWriter osw = new OutputStreamWriter(fos);
//			BufferedWriter bw = new BufferedWriter(osw);
//
//			for (int i = 0; i < individualList.size(); i++) {
//				bw.write(individualList.get(i).toString());
//				bw.newLine();
//
//			}
//
//			bw.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}

	/** 
	 * Clears population. 
	 */
	public void clear() {
		individualList.clear();
	} 

	/** 
	 * Returns iterator.
	 */
	public Iterator<Individual> iterator() {
		return individualList.iterator();
	}

	/**
	 * Unions two populations.
	 * 
	 * @param population
	 *          population that will be unioned with this one.
	 * @return return new population.
	 */
	public Population union(Population population) {

		int newSize = this.size() + population.size();
		if (newSize < capacity) {
			newSize = capacity;
		}

		//dodawanie osobnikow z biezacej populacji
		Population union = new Population(newSize);
		for (int i = 0; i < this.size(); i++) {
			union.add(this.get(i));
		}

		//dodawanie osobnikow z populacji przekazanej jako argument
		for (int i = this.size(); i < (this.size() + population.size()); i++) {
			union.add(population.get(i - this.size()));
		}

		return union;
	} 

}
