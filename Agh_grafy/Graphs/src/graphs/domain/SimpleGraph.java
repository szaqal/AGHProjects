package graphs.domain;

import graphs.domain.api.Edge;
import graphs.domain.api.EdgeType;
import graphs.domain.api.Graph;
import graphs.domain.api.Vertex;
import graphs.editor.domain.SwingEdge;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Vector;

/**
 * Najprostrza implementacja grafu
 * @author malczyk.pawel@gmail.com
 *
 * @param <T> parametr typu grafu
 */
public class SimpleGraph<T> implements Graph<T> {
	
	/** Serial. */
	private static final long serialVersionUID = -5542476664908080099L;

	/** Lista wierzchołków. */
	private List<T> vertexes = new Vector<T>();
	
	/** Lista krawedzi. */
	private List<Edge> edges = new Vector<Edge>();
	
	/** Określa czy graf jest skierowany. */
	boolean directed;

	/**
	 * Dodaje do wierzcholek do grafu.
	 * <T> typ wezla
	 */
	@Override
	public int addNode(T arg) {
		if (arg instanceof Vertex ) {
			Vertex vtx = (Vertex)arg;
			if ( extractIndex() != -1 ) {
				vtx.setIndex( extractIndex() );
				vertexes.add(vtx.getIndex(), arg);
			} else {
				vertexes.add(arg);
			}			
		}				
		return vertexes.size();
	}
	
	/**
	 * Oblicza kolejny indeks wierzcholka (z uwzględnidniem usuwania)
	 * @return index kolejny indeks wierzcholka
	 */
	private int extractIndex() {
		int result = vertexes.size();
		for (int i=0; i<vertexes.size(); i++) {
			T obj =  vertexes.get(i);
			if (obj instanceof Vertex ) {
				 int indexVal = ((Vertex) obj).getIndex();
				 if ( indexVal != i) {
					result = i;
					break;
				 }
			}
		}	
		return result;
	}

	/**
	 * Dodaje Kolekcje wezlow.
	 * @param nodes kolekcja wezlow
	 */
	@Override
	public void addNodes(Collection<T> nodes) {
		for (T arg:nodes) {
			addNode(arg);
		}
	}

	
	
	/**
	 * Generuje i zwraca macierz sasiedztwa
	 * @return wypelniona maciez sasiedztwa.
	 */
	@Override
	public int[][] generateMatrix() {
		
		int nodesCount = vertexes.size();
		int [][] resultMatrix = new int[nodesCount][];
		for (int i=0;i<resultMatrix.length;i++) {
			resultMatrix[i]=new int[nodesCount];
			Arrays.fill(resultMatrix[i], 0);
		}
		
		/*
		 * Nie mozna sprawdzac po indeksach wierzcholkow z uwagi na to
		 * ze jesli mielismy wierzcholki 1,2,3 i usunelismy 2 i zostanie 1,3 to 
		 * mogly byc odwolania do indeksu 3 w tablicy wielkosci 2.
		 * dlatego trzeba sprawdzac indeks w w kolekcji wierzcholkow a nie indeks "napisany" 
		 *  na wierzcholku.
		 */
		
		for (Edge edge : edges ){
			Vertex startvertex = edge.getStartVertex();
			Vertex endVertex = edge.getEndVertex();
			
			int x = getVertexIndexInTable(startvertex);
			int y = getVertexIndexInTable(endVertex);
			resultMatrix[x][y]=1;
			resultMatrix[y][x]=1;
		}
		return resultMatrix;
	}
	
	
	/**
	 * Metoda sprawdza jaki index w kolekcji  wierzcholkow ma dany wierzcholek
	 * @param vertex wierzcholek do sprawdzenia
	 * @return indeks w kolekcji
	 */
	private int getVertexIndexInTable(Vertex vertex) {		
		for (int i=0;i<vertexes.size();i++) {
			if (vertexes.get(i) == vertex )
				return i;
		}
		return -1;
	}
	
	/** Wypisuje maciez na konsole (Nie uzywane)*/
	public void printNeibourhoodMatrix() {
		int [][] arg = generateMatrix();
		for (int x=0;x<arg.length;x++) {
			for(int y=0;y<arg[x].length;y++){
				System.out.print(arg[x][y]);
			}
			System.out.println();
		}
	}

	/**
	 * Usuwa wierzcholek z grafu
	 * @param arg wierzcholek do usuniecia
	 */
	@Override
	public void removeNode(T arg) {		
		if (arg instanceof Vertex) {
			vertexes.remove( (Vertex) arg );
		}
	}


	/**
	 * Usuwa krawedz z grafu.
	 * @param arg krawedz do usuniecia.
	 */
	@Override
	public void removeEdge(Edge arg) {
		edges.remove(arg);
	}

	/**
	 * Zwraca wierzcholek po indeksie
	 * @param index indeks wierzcholka
	 * @return <T> wierzcholek o okreslonym indeksie
	 */
	@Override
	public T getVertexByIndex(int index) {
		
		for (T vertex : vertexes ) {
			if ( (vertex instanceof Vertex) &&  ((Vertex)vertex).getIndex() == index ) {
				return vertex;
			}
		}
		return null;
	}

	/** Przrysowywuje graf. */
	@Override
	public void update() {
		/*
		 * Ta implementacja przerysowywuje wszystkie krawedzie
		 * jezli jakis wierzcholek zmienil polozenie
		 */
		for (Edge edge: edges) {
			if (edge instanceof SwingEdge ) {
				((SwingEdge) edge).update();
			}
		}
	}

	/** {@inheritDoc} */
	@Override
	public void removeEdges(List<Edge> edges) {		
		for ( Edge edge : edges ) {
			System.out.println("Usuwanie laczacego " + edge.getStartVertex().getIndex() + " i " + edge.getEndVertex().getIndex() );
			this.edges.remove(edge);
		}
	}
	
	
	
	/**
	 * Zwraca wszystkie wezly
	 * @param <T> zwraca kolekcje wierzchołków
	 */
	@Override
	public List<T> getVertexes() {
		return vertexes;
	}

	/** {@inheritDoc} */
	@Override
	public void addEdge(Edge arg) {
		edges.add(arg);
	}

	/** {@inheritDoc} */
	@Override
	public Collection<Edge> getEdges() {
		return edges;
	}

	/** {@inheritDoc} */
	@Override
	public boolean isDirected() {
		return directed;
	}

	/** {@inheritDoc} */
	@Override
	public void setDirected(boolean directed) {
		this.directed = directed;	
	}

	/** {@inheritDoc}*/
	@Override
	public Edge findEdge(int startVertexIndex, int endVertexIndex, EdgeType edgeType) {
		Edge edge = null;
		switch (edgeType) {
		case DIRECTED:
			edge = findDirectedEdge(startVertexIndex, endVertexIndex);
			break;
		case NOT_DIRECTED:
			edge = findNotDirectedEdge(startVertexIndex, endVertexIndex);
			break;
		default:
			break;
		}
		return edge;
	}
	
	/**
	 * Wyszukuje krawedz o zadanych indeksach wierzcholkow z uwzglednieniem kolejnosci
	 * @return krawedz
	 */
	private Edge findDirectedEdge( int startVertexIndex, int endVertexIndex ) {
		
		Collection<Edge> edges = getEdges();
		for ( Edge edge : edges ) {
			if (edge.getStartVertex().getIndex() == startVertexIndex && edge.getEndVertex().getIndex() == endVertexIndex )
				return edge;
		}				
		return null;
	}

	/**
	 * Wyszukuje krawedz o zadanych indeksach wierzcholkow bez uwzglednienia kolejnosci
	 * @return krawedz
	 */
	private Edge findNotDirectedEdge( int startVertexIndex, int endVertexIndex ) {
		
		Collection<Edge> edges = getEdges();
		for ( Edge edge : edges ) {
			if ((edge.getStartVertex().getIndex() == startVertexIndex && edge.getEndVertex().getIndex() == endVertexIndex) || 
					(edge.getStartVertex().getIndex() == endVertexIndex && edge.getEndVertex().getIndex() == startVertexIndex) )
				return edge;
		}
		
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override 
	public Collection<T> getNeighbours(T arg) {
		List<T> result = new ArrayList<T>();
		Collection<Edge> edges = getEdges();
		
		for (Edge edge : edges) {
			if (isDirected()) {
				if(edge.getStartVertex() == arg ) {
					result.add((T)edge.getEndVertex());
				}
			} else {
				if(edge.getStartVertex() == arg ) {
					result.add((T)edge.getEndVertex());
				} else if (edge.getEndVertex() == arg ) {
					result.add((T)edge.getStartVertex());
				}
			}
		}
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public T getSource() {
		for (T vertex :  getVertexes() ) { 
				if (vertex instanceof Vertex && ((Vertex)vertex).isSource()) 
					return vertex;
		}
		return null;
	}
	
	/** {@inheritDoc} */
	@Override
	public List<T> getSources() {
		List<T> result = new ArrayList<T>();
		for ( T vertex : getVertexes() ) {
			if (vertex instanceof Vertex && ((Vertex)vertex).isSource() ) {
				result.add(vertex);
			}
			
		}
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public T getTarget() {
		for (T vertex :  getVertexes() ) { 
			if (vertex instanceof Vertex && ((Vertex)vertex).isTarget()) 
				return vertex;
	}
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public boolean contains(T arg) {
		if (arg instanceof Vertex ) {
			int index = ((Vertex)arg).getIndex();
			
			try {
				T vertex = vertexes.get(index);
				return vertex != null;
				
			} catch (ArrayIndexOutOfBoundsException e) {
				System.out.println("Exception " + e.getMessage() );
				return false;
			}					
		}
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public int insertNode(T arg) {
		int result = -1;
		if (arg instanceof Vertex ) {
			Vertex v = ((Vertex)arg);
			result = v.getIndex();
			if (result> vertexes.size() ) {
				((Vector<T>)vertexes).setSize((v.getIndex()));	
			}
			if (result<vertexes.size()) {
				vertexes.remove(result);	
			}
						
			vertexes.add(result, arg);
			
		}
		System.out.println(vertexes);
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public void insertNodes(Collection<T> nodes) {		
		for (T vertex :nodes) {
			insertNode(vertex);
		}
			
	}

	@Override
	public int getDegree(T vertex) {		
		return getNeighbours(vertex).size();
	}

	@Override
	public Collection<T> getUnColoredVertexes() {
		List<T> result = new ArrayList<T>();
		for (T vertex : getVertexes() ) {
			if (vertex instanceof Vertex && !((Vertex)vertex).isColoured()) {
				result.add(vertex);
			}
		}
		return result;
	}
	
	
}
