package graphs.domain.api;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * Parametryzowany graf
 * @author malczyk.pawel@gmail.com
 *
 * @param <T> typ grafu.
 */
public interface Graph<T> extends Serializable {
	
	/** Maksymalna liczba wezlow.*/
	int MAXIMUM_NODES_COUNT = 20;
	
	/**
	 * Dodawanie wezla.
	 * @param arg wezel
	 * @return zwraca numer nowego wezla
	 */
	int addNode(T arg);
	
	/**
	 * Dodaje wierzcholek pod zdefiniowany index
	 * @param arg wezel
	 * @return zwraca numer wezla
	 */
	int insertNode(T arg);
	
	/**
	 * Dodawanie krawedzi
	 * @param arg krawedz
	 */
	void addEdge(Edge arg);
	
	/**
	 * Pobiera kolekcje wezlow
	 * @return kolekcja welow
	 */
	Collection<T> getVertexes();
	
	/**
	 * Zwraca kolekcje sasiadujacych wierzcholkow
	 * @param arg wierzcholek ktorego sasiadow szukamy
	 * @return
	 */
	Collection<T> getNeighbours(T arg);
	
	/**
	 * Zwraca niepokolorowane wierzcholki
	 * @return lista niepokolorowanych wierzcholkow
	 */
	Collection<T> getUnColoredVertexes();
	
	/**
	 * Pobiera wezel po indeksie
	 * @param index
	 * @return odnaleziony wezel
	 */
	T getVertexByIndex(int index);
	
	/**
	 * Zwraca wierzcholek oznaczony jako zrodlowy (pierwszy natrafiany jesli jest wiecej)
	 * @return wierzcholek zrodlowy
	 */
	T getSource();
	
	/**
	 * Zwraca wierzcholek oznaczony jako docelowy
	 * @return wiezcholek docelowy
	 */
	T getTarget();
	
	/**
	 * Zwraca wszystkie zrodla.
	 * @return zrodla w grafie
	 */
	List<T> getSources();	
	
	/**
	 * sprawdza czy wierzcholek nalezy do grafu.
	 * @return nalezy/nie nalezy
	 */
	boolean contains(T arg);
	
	/**
	 * Pobiera kolekcje krawedzi
	 * @return kolekcja krawedzi
	 */
	Collection<Edge> getEdges();
	
	/**
	 * Wpisuje na konsole
	 * macierz sasiedztwa
	 */
	void printNeibourhoodMatrix();
	
	/**
	 * Dodawanie kolekcji wezlow.
	 * @param nodes kolekcja wezlow
	 */
	
	void addNodes(Collection<T> nodes);
	
	/**
	 * Dodaje kolekcje wierzcholkow z uwzglenieniema indeksu ktore wierzcholki 
	 * powinny miec ustawione.
	 * @param nodes kolekcja wierzcholkow
	 */
	void insertNodes(Collection<T> nodes);
	
	/**
	 * Usuwanie wezla
	 * @param arg wezel do usuniecia
	 */
	void removeNode(T arg); 
	
	/**
	 * Usuwa krawedz
	 * @param arg krawedz do usuniecia.
	 */
	void removeEdge(Edge arg);
	
	/**
	 * wyszukuje krawedz o zadanych indeksach wierzcholkow
	 * @param startVertexIndex indekst poczatkowego wierzcholka
	 * @param endVertexIndex indeks koncowego wierzcholka
	 * @param edgeType okresla czy kolejnosc ma znaczenie
	 * @return  krawedz
	 */
	Edge findEdge(int startVertexIndex, int endVertexIndex, EdgeType edgeType);
	
	/**
	 * Usuwa liste krawedzi (np. wszystkie powiazane do usunietego wezla)
	 * @param edges
	 */
	void removeEdges( List<Edge> edges );
	
	/**
	 * Uaktualnia graf.
	 */
	void update();
	
	/**
	 * Zwraca wage wierzcholka (Uzywane przy Djikstrze)
	 * @return waga wierzcholka
	 */
	int getDegree(T vertex);
	
	/**
	 * Ustawia wlasnosc czy skierowany.
	 * @param directed wartosc znaczajaca czy graf ma byc skierowany.
	 */
	void setDirected(boolean directed);
	
	
	/**
	 * Czy graf skierowany.
	 * @return wartosc okreslajaca czy graf jest skierowany.
	 */
	boolean isDirected();
	
	/**
	 * Zwraca maciez incydencji Object zeby latwo w (JTable )tabeli mozna bylo uzywac
	 * @return maciez incydencji
	 */
	int[][] generateMatrix();	
	
	
}
