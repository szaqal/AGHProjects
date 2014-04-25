package graphs.editor.gui;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JPanel;

/**
 * Jes kontenerem gdzie definiuje sie wagi (wierzcholkow/krawedzi)
 * @author malczyk.pawel@gmail.com
 */
public class WeightPanel extends JPanel{
	
	
	/** Serial. */
	private static final long serialVersionUID = 1L;
	
	/** Panel gdzie definiowane sa wartosci wag krawedzi*/
	private EdgesPanel edgesPanel;
	
	/** Panel gdzie definowane sa wartosci wag wierzcholkow. */
	private VertexPanel vertexPanel;
	 
	/** Parent. */
	private JPanel parentContainer;
	
	/**
	 * Konstruktor
	 * @param container panel parenta
	 */
	public WeightPanel( JPanel parent ) {
		setLayout( new BorderLayout());
		parentContainer = parent;
		vertexPanel = new VertexPanel(this);
		edgesPanel = new EdgesPanel(this);
		
		if (parent.getLayout() instanceof BorderLayout) {
			parent.add(this, BorderLayout.EAST);
		}				
		
	}

	// --- gettery i settery
	public final EdgesPanel getEdgesPanel() {
		return edgesPanel;
	}

	public final VertexPanel getVertexPanel() {
		return vertexPanel;
	}
	
	@Override
	public Container getParent() {
		return parentContainer;
	}
	
}
