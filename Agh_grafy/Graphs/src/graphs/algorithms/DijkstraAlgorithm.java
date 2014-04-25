package graphs.algorithms;

import graphs.algorithms.api.Algorithm;
import graphs.domain.api.Edge;
import graphs.domain.api.EdgeType;
import graphs.domain.api.Graph;
import graphs.domain.api.Vertex;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

/**
 * Implementacja algorytmu Djikstry
 * @author malczyk.pawel@gmail.com
 *
 */
public class DijkstraAlgorithm implements Algorithm {
	
	
	/** przetwarzany graf. */
	private Graph< Vertex > graph;
	
	/** zawiera koszty przejscia*/
	private int [] dist;
	
	/** zawiera indeksy porzedzajacych o najkrotszej sciezce. */
	private int [] pred;
	
	/** Odwiedzone*/
	private boolean [] visited;

	@Override
	public void processAlgorithm(Graph<? extends Vertex> graph) {
		System.out.println("Dijikstra");
				
		
		init(graph);
		
		//dla kazdego z wierzcholkow dist.length jest rownowazne ilosci wierzcholkow
		for (int i=0;i<dist.length;i++) {
		
			print();
						
			// znajdowanie wierzcholka o najmneijszym d i przenisienie do przetworzonego
			int next = extractMinOfD(dist, visited);
			if (next != -1) {
				visited[next] = true;			
				Vertex v = graph.getVertexByIndex(next);			
				
				Collection<? extends Vertex> neighbours = this.graph.getNeighbours(v);
				for (Vertex neighbour : neighbours ) {				
					
					Edge edge = this.graph.findEdge(v.getIndex(), neighbour.getIndex(), EdgeType.DIRECTED );
					int neibourSum = (neighbour.isTarget()) ? 0 : neighbour.getWeight();
					
					int sum = dist[v.getIndex()] + edge.getWeight() + neibourSum;
					if ( dist[neighbour.getIndex()] > sum ) {
						dist[neighbour.getIndex()] = sum;
						pred[neighbour.getIndex()] = v.getIndex();								
					}
				}	
			}								
		}		
		
		highlightEdges(); //zaznaczenie sciezki na grafie

	}
	
	/** Inicjalizacja. */
	@SuppressWarnings("unchecked")
	private void init( Graph<? extends Vertex > graph ) {
		
		this.graph = (Graph<Vertex>) graph;
		
		clearHighLightedEdges(); //czysci zaznacznia
		
		dist = new int[graph.getVertexes().size()]; //tablica biezacych wag sciezki
		pred = new int[graph.getVertexes().size()]; //tablica poprzdnikow
		visited = new boolean[graph.getVertexes().size()];
		
		Arrays.fill(dist, Integer.MAX_VALUE); 
		Arrays.fill(pred, -1); // -1 rownowazne niezdefiniowane
				
		int sourceIndex = -1;		
		sourceIndex = ( graph.getSource() != null ) ? graph.getSource().getIndex() : sourceIndex;
		
		//ustawienie dla zrodla 0 (jedyny w tablicy zdefiniowany na ta chwile)
		dist[sourceIndex] = 0;				
	}
	
	/**
	 * znajduje kolejny wezel o najnizszym koszcie 
	 * @param dist tablica z biezaca odlegloscia (waga)
	 * @param visited czy odwiedzony
	 * @return 
	 */
	private int extractMinOfD(int [] dist, boolean [] visited) {
		int min = Integer.MAX_VALUE;
		int result = -1;
		for(int i=0;i<dist.length;i++) {
			if (!visited[i] && dist[i]<min) {
				min = dist[i];
				result = i;
			}
				
		}
		return result;
	}
	
	
	private void highlightEdges() {
		int endIndex = graph.getTarget().getIndex();		
		int predecessor = endIndex;
		while ( pred[predecessor] != -1 ) {			
			System.out.println( " " + pred[predecessor] + " " + predecessor);
			Edge edge = graph.findEdge(pred[predecessor], predecessor, EdgeType.DIRECTED);			
			edge.setSelected(true);
			predecessor = pred[predecessor];	
		}
		
	}
	
	private void clearHighLightedEdges() {
		Collection<Edge> edges = graph.getEdges();
		Iterator<Edge> iter = edges.iterator();
		while(iter.hasNext()){
			iter.next().setSelected(false);
		}
	}
	
	
	private void print() {
		//dla pomocy
		
		System.out.println("Index");
		for ( int i=0;i< dist.length;i++ ) {
			System.out.print(i+",");
		}
		System.out.println("\n--------------------\n Dist");
		for ( int i=0;i< dist.length;i++ ) {
			System.out.print(dist[i]+",");
		}
		System.out.println("\n-------------------- \n Pred");
		for ( int i=0;i< dist.length;i++ ) {
			System.out.print(pred[i]+",");
				
		}
		System.out.println("\n-----------------------------");
	}
	
	
	@Override
	public String toString() {
		return "Algorytm Dijikstry";
	}

}
