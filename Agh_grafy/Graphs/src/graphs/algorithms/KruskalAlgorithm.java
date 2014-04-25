package graphs.algorithms;

import graphs.algorithms.api.Algorithm;
import graphs.domain.api.Edge;
import graphs.domain.api.Graph;
import graphs.domain.api.Vertex;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Implentacja algorytmu kruskala.
 * @author malczyk.pawel@gmail.com
 *
 */
public class KruskalAlgorithm implements Algorithm {

	/** Lista krawedzi w drzewie rozpinajacym. */
	private List<Edge> spanningTree = new ArrayList<Edge>();
	
	/** Lista zbiorów ktore za łączone a algorytmie kruskala*/
	private List<Set<Vertex>> sets = new ArrayList<Set<Vertex>>();
	
	/** Lista krawedzi*/
	private List<Edge> edges;
	
	
	/**
	 * Znajduje minimalne drzewo rozpinajace grafu algorytmem kruskala
	 * @param graph graph na ktorym wywolywany jest algorytm. 
	 */
	@SuppressWarnings("unchecked") 	//TODO: zrobic tak zeby warniniga nie bylo
	@Override
	public void processAlgorithm(Graph<? extends Vertex> graph) {
		edges = (List<Edge>)graph.getEdges();
		List<Vertex> vertexes = (List<Vertex>)graph.getVertexes();
		//init
		
		///utworzenie pojedynczych zbiorow dla kazdego wierzcholka
		for (Vertex v:vertexes){
			Set<Vertex> set= new HashSet<Vertex>();
			set.add(v);
			sets.add(set);
		}
		
		//sortowanie krawedzi roznaco po wagach
		Collections.sort(edges);
		
		
		for (Edge e : edges ) {
			if (!checkSets(e)){
				spanningTree.add(e);
				union(e.getStartVertex(),e.getEndVertex());
			}
		}
		
		for (Edge e : spanningTree) {
			e.setSelected(true);
		}
		graph.update();
	}
	
	/** 
	 * Szuka alternatywnego drzewa po usunieciu krawedzi.
	 * @param graph graf 
	 */
	public void findAlternatives(Graph<? extends Vertex> graph) {
		Edge deletedEdge =null;
		for (Edge edge:spanningTree) {
			if ( !graph.getEdges().contains(edge) ) {
				deletedEdge = edge;				
			}
		}
		
		spanningTree.remove(deletedEdge);
				
		boolean setContains = false;
		for (Set<Vertex> vertexSet : sets) {
			if (vertexSet.contains(deletedEdge.getStartVertex()) && vertexSet.contains(deletedEdge.getEndVertex())){
				setContains = true;
			}
		}
		
		if (setContains) {
			sets.get(0).remove(deletedEdge.getStartVertex());
			sets.get(0).remove(deletedEdge.getEndVertex());						
			
			Set<Vertex> newStartVertexSet = new HashSet<Vertex>();			
			Set<Vertex> newEndVertexSet = new HashSet<Vertex>();
			
			newStartVertexSet.add(deletedEdge.getStartVertex());
			newEndVertexSet.add(deletedEdge.getEndVertex());
			
						
			for (Edge edge : spanningTree) {
				if (edge.getStartVertex() == deletedEdge.getStartVertex() ) {
					newStartVertexSet.add(edge.getEndVertex());
					sets.get(0).remove(edge.getEndVertex());
				} else if(edge.getEndVertex() == deletedEdge.getStartVertex() ) {
					newStartVertexSet.add(edge.getStartVertex());
					sets.get(0).remove(edge.getStartVertex());
				} else if (edge.getStartVertex() == deletedEdge.getEndVertex() ) {
					newEndVertexSet.add(edge.getEndVertex());
					sets.get(0).remove(edge.getEndVertex());
				} else if (edge.getEndVertex() == deletedEdge.getEndVertex() ) {
					newEndVertexSet.add(edge.getStartVertex());
					sets.get(0).remove(edge.getStartVertex());
				}
						
			}
						
			sets.add(newStartVertexSet);
			sets.add(newEndVertexSet);
							
			
			for (Edge e : edges ) {
				if (!checkSets(e)){
					spanningTree.add(e);
					union(e.getStartVertex(),e.getEndVertex());
				}
			}
			
			for (Edge e : spanningTree) {
				e.setSelected(true);
			}
			graph.update();
			
		}
				
	}
	
	
	//FIXME: powtarzanie kodu E(u,v)
	/**
	 * dokonuje złączenia zbiorów.
	 * @param start wezel poczatkowy
	 * @param end wezel koncowy
	 */
	private void union(Vertex start, Vertex end) {
		Set<Vertex> uVertex=new HashSet<Vertex>();
		Set<Vertex> vVertex=new HashSet<Vertex>();
		List<Set<Vertex>> toDelete = new ArrayList<Set<Vertex>>();
		
		for (Set<Vertex> set : sets ) {
			if (set.contains(start)) {
				uVertex = set;
				toDelete.add(set);
			}
							
			if (set.contains(end)){
				vVertex = set;
				toDelete.add(set);
			}				
		}
		
		for (Set<Vertex> set: toDelete){
			sets.remove(set);
		}
		
		uVertex.addAll(vVertex);
		sets.add(uVertex);
	}
	
	/**
	 * Sprawdza czy wierzcholki krawedzki naleza do tego samego zbioru.
	 * @param e krawedz
	 * @return tak/nie
	 */
	boolean checkSets(Edge e) {
		Set<Vertex> uVertex=new HashSet<Vertex>();
		Set<Vertex> vVertex=new HashSet<Vertex>();
		
		for (Set<Vertex> set : sets ) {
			if (set.contains(e.getStartVertex()))
				uVertex = set;
			
			if (set.contains(e.getEndVertex()))
				vVertex = set;
		}
		boolean result = (uVertex == vVertex); 
		return result;
	}
	
	@Override
	public String toString() {
		return "Algorytm Kruskala";
	}

}
