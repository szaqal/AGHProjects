package graphs.editor.gui;

import graphs.Graphs;
import graphs.domain.api.Edge;
import graphs.domain.api.Graph;
import graphs.editor.domain.SwingEdge;
import graphs.editor.domain.SwingVertex;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * Panel na ktorym jest plotno 
 * @author malczyk.pawel@gmail.com
 *
 */
public class CanvasPanel extends JPanel{
	
	/** Okresla czy mozna dodwac wierzcholki do plotna. */
	private boolean allowNodeInsertion;

	/** Panel na ktorym rysowany jest graf*/
	private JPanel canvas;
	
	/** Wezel ktory aktualnie jest przesuwany*/
	private Component movingVertex;	
	
	/** Popup listener*/
	MouseListener popupListener = new MouseEventHandler();
	
	/** Serial. */
	private static final long serialVersionUID = 1L;

	/**
	 * Konstruktor	
	 * @param parentContainer komponent na ktorym jest dodawane plotno
	 */
	public CanvasPanel(Container parentContainer) {
		super();
		setLayout(new BorderLayout());
		canvas = setupCanvas();		
		canvas.addMouseListener(popupListener);
		add(canvas, BorderLayout.CENTER);
		parentContainer.add(this, BorderLayout.CENTER);		
	}
	

	
	/** 
	 * Usuwa wierzcholek. (w gui)
	 * @param component krawedz.  
	 */
	public void removeVertex(Component component) {
		//FIXME: przniesc to co sie da do menu a zosawic tylko to co rzeczywiscie usuwa z gui elementy.
		//zbiera wszystkie krawedzie podlaczone do usuwanego wezla
		List<Edge> edgesToDelete = new ArrayList<Edge>();
		
		Graph<SwingVertex> graph = Graphs.getGraph();
		Collection<Edge> connectedEdges = graph.getEdges();
		SwingVertex vertex = (SwingVertex)component;
		// usuwanie powiazanych krawedzi
		Iterator<Edge> iterator = connectedEdges.iterator();
		while( iterator.hasNext() ) {
			//usuwanie z widoku krawedzi
			Edge edge = iterator.next();
			if(edge.getStartVertex().getIndex() == vertex.getIndex() ) {
				canvas.remove((SwingEdge)edge);
				edgesToDelete.add(edge);
			}
			if(edge.getEndVertex().getIndex() == vertex.getIndex() ) {
				canvas.remove((SwingEdge)edge);
				edgesToDelete.add(edge);
			}			
		}
		
		graph.removeEdges(edgesToDelete); //usuwa z grafu powiazane krawedzie
		graph.removeNode(vertex);		//usuwa z grafu wezel.
						
		canvas.remove(component);			//usuwa naarysowany wezel.					
		updateTableDataModel();
		repaint();
		((ContainerPanel)getParent()).getWieghtPanel().getEdgesPanel().update();
		((WeightPanel)getParent().getComponent(1)).getVertexPanel().update(); //uaktualnienie tabeli
	}
	
	/**
	 * Uaktualnie dataModel macierzy sasiwedztwa
	 */
	public void updateTableDataModel() {
		NeibourhoodMatrixPanel matrixPanel = (NeibourhoodMatrixPanel)this.getParent().getParent().getComponent(1);
		matrixPanel.getNebourhoodMatrixTable().setModel(new NeibourhoodTableDataModel());
	}
	
	/**
	 * Usuwa krawedz. (w gui)
	 * @param component krawedz.
	 */
	public void removeEdge(Component component) {
		canvas.remove(component);
		repaint();
	}
	
	
	/**
	 * Przesuwanie wezla;
	 * @param przesuwany komponent (wezel)
	 *
	 */
	public void moveTo(Component comp) {
		//FIXME: jakos inaczej to wymyslec
		this.movingVertex=comp;
	}
	
	
	/** Ustawia plotno. */
	private JPanel setupCanvas(){
		JPanel result = new JPanel();
		result.setLayout(null);
		result.setBackground(Color.BLACK);
		result.setForeground(Color.WHITE);			
		return result;
	}
	

	
	/**
	 * Ustawia flage czy mozna dodawac kolejne wezly.
	 * @param allowNodeInsertion
	 */
	public void setAllowNodeInsertion(boolean allowNodeInsertion) {
		this.allowNodeInsertion = allowNodeInsertion;
	}
	


	/**
	 * Handler oblugujacy mysze.
	 * @author malczyk.pawel@gmail.com
	 *
	 */
	class MouseEventHandler extends MouseAdapter {
		 public void mousePressed(MouseEvent e) {			 			        
		        
		        if (allowNodeInsertion) {
					SwingVertex node = new SwingVertex();
					
					((SwingVertex)node).setBounds(e.getX()-SwingVertex.SWING_OVAL_WIDTH/2, e.getY()-SwingVertex.SWING_OVAL_HEIGHT/2,
							SwingVertex.SWING_OVAL_HEIGHT, SwingVertex.SWING_OVAL_WIDTH);
					
					Graphs.getGraph().addNode( (SwingVertex)node ); 					
					node.draw(canvas, e.getX(), e.getY() );															
					canvas.repaint();
					allowNodeInsertion = false;
					
					CanvasPanel.this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));														
																	
					
					//ponowne wlaczenie przycisku
					Object object =  CanvasPanel.this.getParent().getParent().getParent().getComponent(0);
					if ( object instanceof ToolBar) {
						JButton button = ((ToolBar)object).getToolbarButtons().get(ToolBar.TOOLBAR_LABELS[1]);
						button.setEnabled(true);
												
						((WeightPanel)getParent().getComponent(1)).getVertexPanel().update(); //uaktualnienie tabeli
					}				
					
				} else if( movingVertex != null ){
					for (Component comp:canvas.getComponents()){
						if (comp == movingVertex ){
							comp.setLocation(e.getX()-SwingVertex.SWING_OVAL_WIDTH/2, e.getY()-SwingVertex.SWING_OVAL_HEIGHT/2);
							
						}
					}
					Graphs.getGraph().update();
					CanvasPanel.this.repaint();
				}
		    }

	}

	public final void clearCavnas() {
		canvas.removeAll();
		canvas.repaint();
	}
	

	/**
	 * Zwraca panel z plotnem
	 * @return panel z plotnem
	 */
	public final JPanel getCanvas() {
		return canvas;
	}

	
}
