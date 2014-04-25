package graphs.editor.domain;

import graphs.Graphs;
import graphs.GraphsConstants;
import graphs.domain.SimpleLocation;
import graphs.domain.api.Chanel;
import graphs.domain.api.Edge;
import graphs.domain.api.EdgeType;
import graphs.domain.api.Location;
import graphs.domain.api.Vertex;
import graphs.domain.api.gui.DrawableEdge;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.JPanel;

/** 
 * Reporezetuje pojedyncza linie pomiedzy wezlami
 * @author malczyk.pawel@gmail.com
 *
 */
public class SwingEdge extends JComponent implements DrawableEdge, Chanel {
	
	/** Serial. */
	private static final long serialVersionUID = 1L;
	
	/** Rozmiar czcianki */
	private static final int FONT_SIZE = 15;

	/** Datamodel krawedzi.*/
	private EdgeDataModel edgeDataModel;
	
	/**
	 * Okresla czy odwrocona krawedz tzn
	 * Nie odwrocona rysowana -> /
	 * Odwrocona rysowana -> \
	 */
	boolean inverted;	
	
	/** Czy oznaczone jako zanaczone. */
	boolean selected;
	
	/** Kolor krawedzi. */
	private Color color;
	
	/** Domyslny konstruktor */
	public SwingEdge() { 
		edgeDataModel = new EdgeDataModel();
	}
	
	/**
	 * Konstruktor.
	 * @param startVertex wezel poczatkowy 
	 * @param endVertex wezel koncowy
	 */
	public SwingEdge( Vertex startVertex, Vertex endVertex ) {
		edgeDataModel = new EdgeDataModel();
		color = Color.yellow;
		edgeDataModel.setStartingVertex(startVertex);
		edgeDataModel.setEndingVertex(endVertex);
		setForeground(Color.red);
		setup(edgeDataModel.getStartingVertex().getVertexLocation().getLocationX(), edgeDataModel.getEndingVertex().getVertexLocation().getLocationX(), 
				edgeDataModel.getStartingVertex().getVertexLocation().getLocationY(), edgeDataModel.getEndingVertex().getVertexLocation().getLocationY());
	}
	
	/**
	 * Ustawia parametry krawedzi dla zadanych punktow
	 * @param startX lokalizacja x poczatkowego punktu
	 * @param endX lokalizacja x koncowego punktu
	 * @param startY lokalizacja y pocztkowego punktu
	 * @param endY lokalizacja y koncowego punktu
	 */
	private void setup(int startX, int endX, int startY, int endY) {
		
		if (startX < endX ) {
			if (startY < endY ) {
				setLocation(startX+ SwingVertex.SWING_OVAL_WIDTH/2, startY+ SwingVertex.SWING_OVAL_HEIGHT /2); 
				setSize(Math.abs(startX - endX), Math.abs(startY - endY));
				inverted = false;
			} else {
				int offset = Math.abs(startY - endY);
				setLocation(startX+SwingVertex.SWING_OVAL_WIDTH/2, startY- offset+SwingVertex.SWING_OVAL_HEIGHT/2); 
				setSize(Math.abs(startX - endX), offset);
				inverted = true;
			}
		} else {
			if (startY < endY ) {
				int offsetX = Math.abs(endX - startX);
				setLocation(startX- offsetX+SwingVertex.SWING_OVAL_WIDTH/2, 
						startY+SwingVertex.SWING_OVAL_HEIGHT/2);
				setSize(offsetX, Math.abs(startY - endY));
				inverted = true;
			} else {
				int offsetY = Math.abs(endY - startY);
				int offsetX = Math.abs(endX - startX);
				setLocation(startX-offsetX+SwingVertex.SWING_OVAL_WIDTH/2, 
						startY- offsetY+SwingVertex.SWING_OVAL_HEIGHT/2);
				setSize(offsetX, offsetY);
				inverted = false;
			}
		}
				
	}
	
	/** Usktualnia parametry krawedzi */
	public void update() {
		setup(edgeDataModel.getStartingVertex().getVertexLocation().getLocationX(), edgeDataModel.getEndingVertex().getVertexLocation().getLocationX(), 
				edgeDataModel.getStartingVertex().getVertexLocation().getLocationY(), edgeDataModel.getEndingVertex().getVertexLocation().getLocationY());
	}
	
	/**
	 * Oblicza polozenie labela.
	 * @return miejsce gdzie trzeba wyswietlic labelke z waga dla krawedzi
	 */
	private Location computeWeightLocation() {
		int x1 = edgeDataModel.getStartingVertex().getVertexLocation().getLocationX();
		int x2 = edgeDataModel.getEndingVertex().getVertexLocation().getLocationX();
		int y1 = edgeDataModel.getStartingVertex().getVertexLocation().getLocationY();
		int y2 = edgeDataModel.getEndingVertex().getVertexLocation().getLocationY();
		
		int resultX = (Math.abs(x2-x1))/2;
		int resultY = (Math.abs(y2-y1))/2;
		
		return new SimpleLocation(resultX, resultY-5);
		
		
	}
	
	
	/** Rysowanie krawedzi. */
	@Override
	public void paint(Graphics graphics) {
		super.paint(graphics);		
		graphics.setColor(color);
		graphics.setFont(new Font("SansSerif",Font.BOLD,FONT_SIZE));
		if(!inverted) {
			graphics.drawLine(0, 0, getWidth(), getHeight());
			Location location = computeWeightLocation();
			
			String label = String.valueOf(edgeDataModel.getWeight());
			if (Graphs.getGraph().isDirected()) {
				label += " (" + edgeDataModel.getStartingVertex().getIndex() + "->" + edgeDataModel.getEndingVertex().getIndex() + ")";  
			}
			
			graphics.drawString( label, location.getLocationX(), location.getLocationY() );
		} else {
			graphics.drawLine(0, getHeight(), getWidth(), 0);
			Location location = computeWeightLocation();
			String label = String.valueOf(edgeDataModel.getWeight());
			
			if (Graphs.getGraph().isDirected()) {
				label += " (" + edgeDataModel.getStartingVertex().getIndex() + "->" + edgeDataModel.getEndingVertex().getIndex() + ")";  
			}
			
			graphics.drawString( label, location.getLocationX(), location.getLocationY() );
		}
		
	}
	
	
	/**
	 * Dodaje krawedz do nadrzednego komponentu
	 * @param component komponent do ktorego zostanie dodana krawedz.
	 */
	@Override
	public <T> void draw(T component) {		
		if (component instanceof JPanel ) {
			((JPanel) component).add(this);
		}
		
	}
	
	//gettry i settery 
	

	public final boolean isSelected() {
		return selected;
	}

	public final void setSelected(boolean selected) {				
		if (selected) {
			color = Color.RED;
		} else {
			color = Color.YELLOW;
		}
		this.selected = selected;
	}

	/**
	 * Konieczne do łatwego sortowania
	 * w algorytmie kruskala porownuje wagi krawedzi.
	 */
	@Override
	public int compareTo(Edge edge) {
		Integer weight1 = new Integer(edgeDataModel.getWeight());
		Integer weight2 = (edge.getDataModel()!= null) ? new Integer(edge.getDataModel().getWeight()):0;
		return weight1.compareTo(weight2);
	}
	
	
	@Override
	public String toString() {
		String result = null;
		if (Graphs.getGraph().isDirected()) {
			result = String.format(GraphsConstants.StringsConstants.EDGE_REPRESENTATION, String.valueOf(edgeDataModel.getStartingVertex().getIndex()), 
					EdgeType.DIRECTED.getEdgeType(), String.valueOf(edgeDataModel.getEndingVertex().getIndex()));
		} else {
			result = String.format(GraphsConstants.StringsConstants.EDGE_REPRESENTATION, String.valueOf(edgeDataModel.getStartingVertex().getIndex()), 
					EdgeType.NOT_DIRECTED.getEdgeType(), String.valueOf(edgeDataModel.getEndingVertex().getIndex()));
		}
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public int getCapacity() {
		return edgeDataModel.getCapacity();
	}

	/** {@inheritDoc} */
	@Override
	public int getFlow() {
		return edgeDataModel.getFlow();
	}

	/** {@inheritDoc} */
	@Override
	public void setCapacity(int capacity) {
		edgeDataModel.setCapacity(capacity);
	}

	/** {@inheritDoc} */
	@Override
	public void setFlow(int flow) {
		edgeDataModel.setFlow(flow);
	}
	
	/** {@inheritDoc} */
	@Override
	public final int getWeight() {
		return edgeDataModel.getWeight();
	}

	public final void setWeight(int weight) {		
		edgeDataModel.setWeight(weight);
	}

	/** {@inheritDoc} */
	@Override
	public void setEndVertex(Vertex endVertex) {
		edgeDataModel.setEndingVertex(endVertex);
	}

	/** {@inheritDoc} */
	@Override
	public void setStartVertex(Vertex startVertex) {
		edgeDataModel.setStartingVertex(startVertex);
	}
	
	/** {@inheritDoc} */
	@Override
	public final Vertex getStartVertex() {
		return edgeDataModel.getStartingVertex();
	}
	
	/** {@inheritDoc} */
	@Override
	public final Vertex getEndVertex() {
		return edgeDataModel.getEndingVertex();
	}

	/** {@inheritDoc} */
	@Override
	public int getChanellCapacity() {
		int chanelCapacity = getCapacity() - getFlow();
		return chanelCapacity;
	}

	@Override
	public EdgeDataModel getDataModel() {
		return edgeDataModel;
	}

	@Override
	public void setDataModel(EdgeDataModel edgeDataModel) {
		this.edgeDataModel = edgeDataModel;
	}

	@Override
	public void setColor(Color c) {
		SwingEdge.this.setForeground(c);
	}
	
}
