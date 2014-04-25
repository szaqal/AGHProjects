package graphs.algorithms;

import graphs.algorithms.api.Algorithm;
import graphs.domain.api.Edge;
import graphs.domain.api.EdgeType;
import graphs.domain.api.Graph;
import graphs.domain.api.Vertex;
import graphs.domain.api.gui.Drawable;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Kolorowanie grafu
 * @author malczyk.pawel@gmail.com
 *
 */
public class GraphColoring implements Algorithm {


	private Graph<Vertex> graph;

	@SuppressWarnings("unchecked")
	@Override
	public void processAlgorithm(Graph<? extends Vertex> graph) {
		this.graph =  (Graph<Vertex>)graph;
		SortedMap<Integer,Vertex> map =orderVertexesByDegree();
		
		List<Integer> keys = new ArrayList<Integer>();
		for (Integer i : map.keySet() ) {
			keys.add(i);
		}
		Collections.sort(keys);
		Collections.reverse(keys);
		
		
		greedy();	
		
	}
	
	private void greedy() {
		Random random = new Random();
		while (true ) {
			
			if ( graph.getUnColoredVertexes().isEmpty() ) {
				return;
			}
			
			double col = random.nextDouble()*250;
			int a = (int)col;
			int b = (int)(a-(0.5)*a);
			int c = (int)(0.7*a);
			Color color = new Color(a,b,c);
					
			Set<Vertex> vertexSet = new HashSet<Vertex>();
		
			Collection<Vertex> uncoloredVertexes = graph.getUnColoredVertexes();
			System.out.println("Uncolored : "+uncoloredVertexes);
		
			for ( Vertex unColoredVertex :uncoloredVertexes ) {
							
			
				boolean found = false;
				for ( Vertex w : vertexSet ) {
					Edge e = graph.findEdge(unColoredVertex.getIndex(), w.getIndex(), EdgeType.NOT_DIRECTED);
					if (e != null ){
						found = true;
					}				
				}
			
				if (!found) {
					unColoredVertex.setAsColored(true);
					System.out.println(unColoredVertex.getIndex() + " color "+color);
					((Drawable)unColoredVertex).setColor(color);
					vertexSet.add(unColoredVertex);
				}
			}
							
			
		}				
		
	}
	
	@Override
	public String toString() {
		return "Kolorowanie grafu";
	}
	
	/**
	 * Sortuje liste
	 * @return
	 */
	private SortedMap<Integer,Vertex> orderVertexesByDegree() {
		// rosnaco klucze w mapie
		SortedMap<Integer,Vertex> map= new TreeMap<Integer,Vertex>();
		for (Vertex v : graph.getVertexes() ) {
			int vertexDegree = graph.getDegree(v);
			map.put(vertexDegree, v);
		}
		return map;
	}
	
}
