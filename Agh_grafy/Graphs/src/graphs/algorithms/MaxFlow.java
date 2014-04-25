package graphs.algorithms;

import graphs.algorithms.api.Algorithm;
import graphs.domain.SimpleGraph;
import graphs.domain.api.Edge;
import graphs.domain.api.EdgeType;
import graphs.domain.api.Graph;
import graphs.domain.api.Vertex;
import graphs.editor.domain.EdgeDataModel;
import graphs.editor.domain.SwingEdge;
import graphs.editor.domain.SwingVertex;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Vector;

/**
 * Maksymalny przeplyw (algorytm Forda-Fulkersona).
 * @author malczyk.pawel@gmail.com
 *
 */
public class MaxFlow implements Algorithm {

	
	/** wlasciwy graf */
	private Graph<SwingVertex> graph;
	
	/** siec residualna dla grafu. */
	private Graph<SwingVertex> residualNetwork;
	
	private int flowValue; 
	
	@SuppressWarnings("unchecked")
	@Override
	public void processAlgorithm(Graph<? extends Vertex> argGraph) {
		flowValue = 0;
		graph = (Graph<SwingVertex>)argGraph;
		residualNetwork = prepareResidualNetwork();
		check();		
		resetFlows();				
		
		List<SwingVertex> sources = this.residualNetwork.getSources();
		
		while( true ) {					
			int i=0;
			int index=-1;
			for (Vertex currentSource : sources ) {
				List<Edge> path = getPath(currentSource);
				if (path != null && !path.isEmpty()) {
					int minCapacity = extractMinCapacityFromPath(path);
					applyFlow(path, minCapacity);	
				} else {
					index = i;
					break;
				}			
			}
			
			if (index != -1 ) {
				sources.remove(index);	
			}
			
			if (sources.size() == 0) {
				break;
			}	
		}
		System.out.println("Przeplyw - > " + flowValue);
	}
	

	
	/**
	 * Sprawdza czy istnieje sciezka powiekszajaca w sieci residualnej
	 * @param source biezace zrodlo
	 * @return jak null znaczy ze nie istnieje
	 */
	private List<Edge> getPath(Vertex source) {
		int [] predecessors = new ShortestPath().findShortestPath(this.residualNetwork, source);
		List<Edge> path = retrieveEdgesFromPredecessorArray(predecessors);
		return path;
	}
	
	/**
	 * Przygotowuje siec resydualna dla zadanego grafu
	 * przez klonowanie datamodelu poszczegolnych krawedzi.
	 * @return przygotowana sieci resydualna
	 */
	private Graph<SwingVertex> prepareResidualNetwork() {
		Graph<SwingVertex> residualNetwork = new SimpleGraph<SwingVertex>();
		residualNetwork.setDirected(true);
		Collection<Edge> edges = graph.getEdges();
		
		for (Edge edge: edges) {
			EdgeDataModel edgeDataModel;
			try {
				edgeDataModel = (EdgeDataModel)edge.getDataModel().clone();

				List<SwingVertex> vertexes = new ArrayList<SwingVertex>();
				
				SwingVertex startVertex = (SwingVertex)edgeDataModel.getStartingVertex();
				SwingVertex endVertex = (SwingVertex)edgeDataModel.getEndingVertex();
				if(!residualNetwork.contains(startVertex)) {
					vertexes.add(startVertex); //klonowanie datamodelu dla sieci residualnej
				} else {
					edgeDataModel.setStartingVertex(residualNetwork.getVertexByIndex(startVertex.getIndex()));
				}
				if (!residualNetwork.contains(endVertex)) {
					vertexes.add(endVertex);	
				} else {
					edgeDataModel.setEndingVertex(residualNetwork.getVertexByIndex(endVertex.getIndex()));
				}				
				residualNetwork.insertNodes(vertexes);
				Edge newEdge = new SwingEdge();
				newEdge.setDataModel(edgeDataModel);
				residualNetwork.addEdge(newEdge);
				
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			} 
			
		}
		
		return residualNetwork;
	}
	
	
	/** Zeruje wszystkie przeplywy	 */
	private void resetFlows() {
		for (Edge edge : graph.getEdges() ) {
			edge.setFlow(0);
		}
	}
	
	private List<Edge> retrieveEdgesFromPredecessorArray(int [] predecessorArray) {
		List<Edge> result = new ArrayList<Edge>();
		
		int endIndex = residualNetwork.getTarget().getIndex();			
		int predecessor = endIndex;
		while ( predecessorArray[predecessor] != -1 ) {			
			Edge edge = residualNetwork.findEdge(predecessorArray[predecessor], predecessor, EdgeType.DIRECTED);
			if (edge == null) {
				result = null;
				
				System.out.println("NULL");
				for (int i: predecessorArray) {
					System.out.print(i+",");	
				}
				System.out.println(result);
				
				return result;
			}
			result.add(edge);
			predecessor = predecessorArray[predecessor];	
		}
		
		for (int i: predecessorArray) {
			System.out.print(i+",");	
		}
		System.out.println(result);
		
		
		
		return result;
	}
	
	/**
	 * Wyciaga minimalna przepustowosc w sciezce
	 * @param path sciezka
	 * @return minimalna wartosc przepustowowsci.
	 */
	private int extractMinCapacityFromPath(List<Edge> path) {
		int result = Integer.MAX_VALUE;
		
		for (Edge e : path) {
			if (e.getCapacity() < result) {
				result = e.getCapacity();
			}
		}
		flowValue+=result;
		return result;
	}
	
	/**
	 * Ustawia minimalna przepustowosc krawedzi w grafie dla odpowidajacych sciezce w sieci rezydualnej 
	 * @param edges krawedzi wystepujace w sciezce
	 * @param capacity minimalna przepustowosc w sciezce
	 */
	private void applyFlow(List<Edge> edges, int capacity) {
		
		for (Edge edge : edges) {	
			int startVertexIndex = edge.getStartVertex().getIndex();
			int endVertexIndex = edge.getEndVertex().getIndex();
			//ustawienie przeplywu we wlasciwym grafie
			Edge graphEdge = graph.findEdge(startVertexIndex, endVertexIndex, EdgeType.DIRECTED);
			int graphEdgeFlow = graphEdge.getFlow();
			int resultFlow = graphEdgeFlow+capacity;
			graphEdge.setFlow(resultFlow);
			
			Edge residualEdge = residualNetwork.findEdge(startVertexIndex, endVertexIndex, EdgeType.DIRECTED);
			if(residualEdge!=null) {
				int residualEdgeCapacity = residualEdge.getCapacity();
				residualEdge.setCapacity(residualEdgeCapacity-capacity); //odjecie
				if (residualEdge.getCapacity() == 0){
					residualNetwork.removeEdge(residualEdge);
				}
			}
			residualEdge = residualNetwork.findEdge(endVertexIndex, startVertexIndex, EdgeType.DIRECTED);
			if(residualEdge!=null){
				int residualEdgeCapacity = residualEdge.getCapacity();
				residualEdge.setCapacity(residualEdgeCapacity+capacity); //dodanie 
			} else {
				Edge tmpEdge= new SwingEdge(); //kanal przeciwyny
				tmpEdge.getDataModel().setStartingVertex(edge.getEndVertex());
				tmpEdge.getDataModel().setEndingVertex(edge.getStartVertex());
				tmpEdge.setCapacity(capacity);
				residualNetwork.addEdge(tmpEdge);
			}
			
		}
	}
	
	private void check() {
		for (int i=0;i<graph.getEdges().size();i++) {
			String graphEdge = ((Vector<Edge>)graph.getEdges()).get(i).toString();
			String residEdge = ((Vector<Edge>)residualNetwork.getEdges()).get(i).toString();
			boolean theSame = graphEdge == residEdge;
			System.out.println("Graph " + graphEdge + " Resid" + residEdge + " " + theSame);
		}
		
		for (int i=0;i<graph.getVertexes().size();i++) {
			String graphEdge = ((Vector<SwingVertex>)graph.getVertexes()).get(i).toString();
			String residEdge = ((Vector<SwingVertex>)residualNetwork.getVertexes()).get(i).toString();
			boolean theSame = graphEdge == residEdge;
			System.out.println("Graph ver " + graphEdge + " Resid ver" + residEdge + " " + theSame);
		}
	}
	
	
	@Override
	public String toString() {
		return "Algorytm Forda-Fulkersona";
	}

}
