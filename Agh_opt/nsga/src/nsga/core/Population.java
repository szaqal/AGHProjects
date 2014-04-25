package nsga.core;

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

/**
 * Opakowuje zbior rozwiazan (zwykle poprostu populacje).
 * 
 * @author malczyk.pawel@gmail.com
 */
public class Population implements Serializable {

	/** Serial. */
	private static final long serialVersionUID = 4113136899421961879L;

	/** Przechowuje liste rozwiazan,. */
	protected List<Individual> individualList;

	/** Maksymalna pojemnosc zbioru rozwiazan */
	private int capacity = 0;

	/** Konstruktor. */
	public Population() {
		individualList = new ArrayList<Individual>();
	}

	/** Konstruktor.*/
	public Population(int maximumSize) {
		individualList = new ArrayList<Individual>();
		capacity = maximumSize;
	} 

	/** Dodaje pojedyncze rozwiazanie do zbioru. */
	public boolean add(Individual individual) {
		if (individualList.size() == capacity) {
			System.err.println("Pelny");
			return false;
		}

		individualList.add(individual);
		return true;
	} 

	/** Zwraca pojedyncze rozwiazaine (osobnika populacji). */
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

	/**
	 * Zapisuje wyniki w pliku.
	 */
	public void printResultsToFile(String path) {
		try {
	
			FileOutputStream fos = new FileOutputStream(path);
			OutputStreamWriter osw = new OutputStreamWriter(fos);
			BufferedWriter bw = new BufferedWriter(osw);

			for (int i = 0; i < individualList.size(); i++) {
				bw.write(individualList.get(i).toString());
				bw.newLine();

			}

			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/** Czyszczenie. */
	public void clear() {
		individualList.clear();
	} 

	/** Zwraca iterator.*/
	public Iterator<Individual> iterator() {
		return individualList.iterator();
	}

	/**
	 * Laczy dwa zbory rozwiazan (dwie populacje)
	 * 
	 * @param population
	 *           populacja ktora bedzie dolaczona do tej opulacji.
	 * @return wynik polaczenia.
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

