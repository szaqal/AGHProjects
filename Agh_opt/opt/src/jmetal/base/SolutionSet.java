package jmetal.base;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

/**
 * Class representing a SolutionSet (a set of solutions)
 */
public class SolutionSet implements Serializable {
	
	/**  Logger. */
	public static Logger logger_ = Logger.getLogger("opt");

	/** Serial. */
	private static final long serialVersionUID = 4113136899421961879L;

	/** Przechowuje liste rozwiazan,. */
	protected List<Solution> solutionsList;

	/** Maksymalna pojemnosc zbioru rozwiazan */
	private int capacity = 0;

	/** Konstruktor. */
	public SolutionSet() {
		solutionsList = new ArrayList<Solution>();
	}

	/** Konstruktor.*/
	public SolutionSet(int maximumSize) {
		solutionsList = new ArrayList<Solution>();
		capacity = maximumSize;
	} 

	/** Dodaje pojedyncze rozwiazanie do zbioru. */
	public boolean add(Solution solution) {
		if (solutionsList.size() == capacity) {
			System.err.println("Poplaction is full");
			return false;
		}

		solutionsList.add(solution);
		return true;
	} 

	/** Returns the ith solution in the set. */
	public Solution get(int i) {
		if (i >= solutionsList.size()) {
			throw new IndexOutOfBoundsException("Index out of Bound " + i);
		}
		return solutionsList.get(i);
	} 


	/** Sortuje rozwiazania w zbiorze. */
	public void sort(Comparator<Solution> comparator) {
		if (comparator == null) {
			System.err.println("Brak komparatora.");
			return;
		}
		Collections.sort(solutionsList, comparator);
	}

	/** Zwraca rozmiar kolekcji */
	public int size() {
		return solutionsList.size();
	}

	/**
	 * Writes the objective funcion values of the <code>Solution</code> objects
	 * into the set in a file.
	 * 
	 * @param path
	 *            The output file name
	 */
	public void printObjectivesToFile(String path) {
		try {
			/* Open the file */
			FileOutputStream fos = new FileOutputStream(path);
			OutputStreamWriter osw = new OutputStreamWriter(fos);
			BufferedWriter bw = new BufferedWriter(osw);

			for (int i = 0; i < solutionsList.size(); i++) {
				bw.write(solutionsList.get(i).toString());
				bw.newLine();

			}


			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/** Czyszczenie. */
	public void clear() {
		solutionsList.clear();
	} 

	/** Zwraca iterator.*/
	public Iterator<Solution> iterator() {
		return solutionsList.iterator();
	}

	/**
	 * Łączy dwa zbory rozwiazan (dwie populacje)
	 * 
	 * @param solutionSet
	 *           populacja ktora bedzie dolaczona do tej opulacji.
	 * @return wynik polaczenia.
	 */
	public SolutionSet union(SolutionSet solutionSet) {

		int newSize = this.size() + solutionSet.size();
		if (newSize < capacity) {
			newSize = capacity;
		}

		//dodawanie osobnikow z biezacej populacji
		SolutionSet union = new SolutionSet(newSize);
		for (int i = 0; i < this.size(); i++) {
			union.add(this.get(i));
		}

		//dodawanie osobnikow z populacji przekazanej jako argument
		for (int i = this.size(); i < (this.size() + solutionSet.size()); i++) {
			union.add(solutionSet.get(i - this.size()));
		}

		return union;
	} 
}

