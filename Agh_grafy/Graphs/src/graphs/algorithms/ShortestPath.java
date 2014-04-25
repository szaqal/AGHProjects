package graphs.algorithms;

import graphs.algorithms.api.Algorithm;
import graphs.domain.api.Graph;
import graphs.domain.api.Vertex;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Pomozcniczy algorytm uzywny w przeplywach do znajdywania najtrotszej sciezki
 * (najkrotszejw sensie przeskokow a nie najmniej kosztownej) (PRZESZUKIWANIE WSZERZ)
 * @author malczyk.pawel@gmail.com
 *
 */
public class ShortestPath implements Algorithm {
	
	enum VertexStatus {
		NOT_VISITED, VISITED, USED
	}
	
	/** tablica biezacej odleglosci*/
	private int [] dist;
	
	/** poprzednicy.*/
	private int [] pred;
	
	/** tablica odwiedzonych*/
	private VertexStatus [] status;
	
	/** przetwarzany graf */
	private Graph<Vertex> graph;
		
	/** Kolejka.*/
	private Queue<Vertex> queue;
	
	/** Lista wierzcholkow bedacych */
	private Vertex currentSource;

	@SuppressWarnings("unchecked")
	@Override
	public void processAlgorithm(Graph<? extends Vertex> graph) {
		this.graph = (Graph<Vertex>)graph;
		init();
		
		int sourceVertexIndex = currentSource.getIndex();
		status[sourceVertexIndex] = VertexStatus.VISITED;
		dist[sourceVertexIndex]=0;
		queue.add(graph.getVertexByIndex(sourceVertexIndex));
			
		while(!queue.isEmpty()) {
			Vertex vertex = queue.poll();
			Collection<Vertex> neighbours = this.graph.getNeighbours(vertex);
			for(Vertex neighbour : neighbours) {
				int neighbourIndex = neighbour.getIndex();
				if (status[neighbourIndex].equals(VertexStatus.NOT_VISITED) ) {
					status[neighbourIndex]= VertexStatus.VISITED;
					dist[neighbourIndex] = dist[vertex.getIndex()]+1;
					pred[neighbourIndex] = vertex.getIndex();
					queue.add(neighbour);
				}
				}
			status[vertex.getIndex()]=VertexStatus.USED;
		}							
	}
	
	/** Wstepna inicjalizacja. */
	private void init() {
		
		int count = graph.getVertexes().size();
		dist = new int[count];
		pred = new int[count];
		status = new VertexStatus[count];
		Arrays.fill(dist, Integer.MAX_VALUE);
		Arrays.fill(pred, -1); //indeksowane wierzcholko od 0 stad tutaj -1
		for(int i=0;i<count;i++) {
			status[i]= VertexStatus.NOT_VISITED;
		}
		
		queue = new LinkedList<Vertex>();
	}
	
	/**
	 * Zwraca indeksty poprzedzajacyh wierzcholkow
	 * @param graph graf przeszukiwany wszerz
	 * @return tablica przechowujaca najkrotsze sciezki
	 */
	public int [] findShortestPath(Graph<? extends Vertex> graph, Vertex source) {
		currentSource = source;
		processAlgorithm(graph);
		return pred;
	}

}
