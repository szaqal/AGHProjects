package graphs.editor.domain;

import graphs.domain.api.Vertex;

/**
 * Model danych krawedzi
 * @author malczyk.pawel@gmail.com
 *
 */
public class EdgeDataModel implements Cloneable {
	
	/** Waga kraewdzi */
	int weight;
	
	/** Przepustowosc.*/
	int capacity;
	
	/** Biezacy przeplyw. */
	int flow;
	
	/** Wierzcholek poczatkowy krawedzi */
	private Vertex startingVertex;
	
	/** Wierzcholek koncowy krawedzi */
	private Vertex endingVertex;

	//-- gettery i settery
	public final int getWeight() {
		return weight;
	}

	public final void setWeight(int weight) {
		this.weight = weight;
	}

	public final int getCapacity() {
		return capacity;
	}

	public final void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public final int getFlow() {
		return flow;
	}

	public final void setFlow(int flow) {
		this.flow = flow;
	}

	public final Vertex getStartingVertex() {
		return startingVertex;
	}

	public final void setStartingVertex(Vertex startingVertex) {
		this.startingVertex = startingVertex;
	}

	public final Vertex getEndingVertex() {
		return endingVertex;
	}

	public final void setEndingVertex(Vertex endingVertex) {
		this.endingVertex = endingVertex;
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		EdgeDataModel clone = (EdgeDataModel) super.clone();
		
		VertexDataModel startingVertexDataModel = (VertexDataModel)startingVertex.getDataModel().clone();	
		/*System.out.println("StartVertexDataModelClone "+startingVertexDataModel + " " + startingVertexDataModel.getIndex() + " "+startingVertexDataModel.isTarget());
		System.out.println("StartVertexDataModel "+startingVertex.getDataModel() + " " + startingVertex.getDataModel().getIndex()+ " "+startingVertex.getDataModel().isTarget());*/
		Vertex startV = new SwingVertex();		
		startV.setDataModel(startingVertexDataModel);		
		clone.startingVertex = startV;
		
		VertexDataModel endingVertexDataModel = (VertexDataModel)endingVertex.getDataModel().clone();
		/*System.out.println("EndingVertexDataModelClone "+endingVertexDataModel + " " + endingVertexDataModel.getIndex() + " "+endingVertexDataModel.isTarget());
		System.out.println("EndingVertexDataModel "+endingVertex.getDataModel() + " " + endingVertex.getDataModel().getIndex()+ " "+endingVertex.getDataModel().isTarget());*/
		
		Vertex endV = new SwingVertex();
		endV.setDataModel(endingVertexDataModel);
		clone.endingVertex = endV;
		
		return clone;
	}
	
}
