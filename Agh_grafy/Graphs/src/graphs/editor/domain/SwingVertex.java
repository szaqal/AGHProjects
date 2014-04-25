package graphs.editor.domain;

import graphs.domain.SimpleLocation;
import graphs.domain.api.gui.DrawableVertex;
import graphs.editor.gui.CanvasPanel;
import graphs.editor.gui.ConntectToFrame;
import graphs.editor.gui.RemoveEdgesFrame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.BevelBorder;

/**
 * Implementacja pojedynczego wezla dla biblioteki Swing.
 * @author malczyk.pawel@gmail.com
 *
 */
public class SwingVertex extends JComponent implements DrawableVertex {
	
	/** Serial. */
	private static final long 	serialVersionUID = 1L;
	
	/** Szerokosc owala. */
	public static final int 	SWING_OVAL_HEIGHT = 70;
	
	/** Wysokosc owala. */
	public static final int 	SWING_OVAL_WIDTH = 70;
	
	/** Wielkosc czcionki*/
	public static final int 	FONT_SIZE = 40;
	
	/** Przesuniecie napisu z numerem w osi X (dla <10)*/
	private static final int 	X_AXIS_SINGLE_DIGIT_COUNT_OFFSET = 10;
	
	/** Przesuniecie napisu z numerem w osi X (dla >= 10)*/
	private static final int 	X_AXIS_DOUBLE_DIGIT_COUNT_OFFSET = 16;
	
	/** Przesuniecie napisu z numerem w osi Y*/
	private static final int 	Y_AXIS_OFFSET = 15;
	
	/** DataModel wierzcholka*/
	private VertexDataModel vertexDataModel;
	
	/** Menu kontekstowe dla wierzcholka*/
	private JPopupMenu popupMenu;
	
	/** Nasluchiwacz wybranej opcji {@link PopupSelectionListener}. */
	private PopupSelectionListener popupSelectionListener;
	
	
	/** Konstruktor. */
	public SwingVertex(){
		vertexDataModel = new VertexDataModel();
		popupSelectionListener = this.new PopupSelectionListener();
		setupPopupMenu();
		popupMenu.setBorder(new BevelBorder(BevelBorder.RAISED));
		this.addMouseListener(this.new NodeMouseListener());		
		setToolTipText("Węzeł grafu");
	}
	
	/** Ustawia menu kontekstowe.*/
	private void setupPopupMenu(){
		String [] labels = new String[]{ "Usuń", "Odblokuj", "Zablokuj", "Połącz z...", "Usuń krawędzie","Oznacz jako źródło","Oznacz jako cel"};
		popupMenu = new JPopupMenu();		
		for(String label : labels){
			JMenuItem menuItem = new JMenuItem(label);
			menuItem.addActionListener(popupSelectionListener);
			popupMenu.add(menuItem);
		}		
	}

	

	/** 
	 * Rysowanie pojedycnzego wierzcholka
	 * Wywaolywane przez wszelakie swingowe listenery.
	 */
	@Override
	public void paint(Graphics graphics) {
		super.paint(graphics);						
		graphics.fillOval(0,0, (int)this.getSize().getWidth(),(int)this.getSize().getHeight());
		graphics.setColor(Color.blue);
		graphics.setFont(new Font("SansSerif",Font.BOLD,FONT_SIZE));
		int offset = (this.getParent().getComponentCount()<10) ? X_AXIS_SINGLE_DIGIT_COUNT_OFFSET : X_AXIS_DOUBLE_DIGIT_COUNT_OFFSET; 
		graphics.drawString(String.valueOf(vertexDataModel.getIndex()), SWING_OVAL_WIDTH/2 - offset, SWING_OVAL_HEIGHT/2+Y_AXIS_OFFSET);
	}

	
	/**{@inheritDoc} */
	@Override
	public <T> void draw(T component, int x, int y) {
		if (component instanceof JPanel ) {
			((JPanel) component).add(this);			
		}
	}

	
	/** 
	 * Zwraca polozenie wierzcholka
	 * @return obiekt lokalizacji {@link SimpleLocation}
	 */
	@Override
	public SimpleLocation getVertexLocation() {
		SimpleLocation result =new SimpleLocation(this.getX(), this.getY()); 
		return result;
	}
	
	
	/**
	 * Obsluguje klikniecia na wierzcholkach
	 * @author malczyk.pawel@gmail.com
	 *
	 */
	class NodeMouseListener extends MouseAdapter {
		
		
		@Override
		public void mousePressed(MouseEvent e) {
			popup(e);
			SwingVertex.this.setForeground(Color.YELLOW);
		}
		
		
		@Override
		public void mouseReleased(MouseEvent e) {
			popup(e);
			if ( vertexDataModel.isSource() ) {
				SwingVertex.this.setForeground(Color.RED);
			} else if (vertexDataModel.isTarget() ) {
				SwingVertex.this.setForeground(Color.GREEN);
			} else {
				SwingVertex.this.setForeground(Color.WHITE);	
			}
			
		}
	
		private void popup(MouseEvent e) {
			if (e.isPopupTrigger()) {
	            popupMenu.show(e.getComponent(), e.getX(), e.getY());
	        }	
		}
	}
	
	
	class PopupSelectionListener implements ActionListener {
				
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			CanvasPanel canvasPanel = (CanvasPanel)SwingVertex.this.getParent().getParent();
			
			if( e.getSource() == popupMenu.getComponent(0)){
				canvasPanel.removeVertex(SwingVertex.this);
			} else if( e.getSource() == popupMenu.getComponent(1) ) {
				canvasPanel.moveTo(SwingVertex.this);
			} else if( e.getSource() == popupMenu.getComponent(2) ) {
				canvasPanel.moveTo(null);
			} else if( e.getSource() == popupMenu.getComponent(3) ) {
				new ConntectToFrame(SwingVertex.this, canvasPanel);
			} else if (e.getSource() == popupMenu.getComponent(4) ) {
				new RemoveEdgesFrame(SwingVertex.this, canvasPanel);
			} else if (e.getSource() == popupMenu.getComponent(5)) {
				SwingVertex.this.setForeground(Color.RED);
				setAsSource(true);
				canvasPanel.repaint();
			} else if (e.getSource() == popupMenu.getComponent(6)) {
				SwingVertex.this.setForeground(Color.GREEN);
				setAsTarget(true);
				canvasPanel.repaint();
			}
			
		}
		
	}

	/**
	 * Zwraca index;
	 * @return
	 */
	public int getIndex() {
		return vertexDataModel.getIndex();
	}

	/** {@inheritDoc} */
	@Override
	public void setIndex(int idx) {
		vertexDataModel.setIndex(idx);
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return String.valueOf(vertexDataModel.getIndex());
	}

	/** {@inheritDoc} */
	@Override
	public boolean isSource() {
		return vertexDataModel.isSource();
	}

	/** {@inheritDoc} */
	@Override
	public boolean isTarget() {
		return vertexDataModel.isTarget();
	}

	/** {@inheritDoc} */
	@Override
	public void setAsSource(boolean isSource) {
		vertexDataModel.setSource(isSource);
	}

	/** {@inheritDoc} */
	@Override
	public void setAsTarget(boolean isTarget) {
		vertexDataModel.setTarget(isTarget);
	}

	/**
	 * Getter wagi.
	 * @return wartosc wagi wierzcholka
	 */
	public final int getWeight() {
		return vertexDataModel.getWeight();
	}

	/**
	 * Setter wagi
	 * @param weight waga wierzcholka
	 */
	public final void setWeight(int weight) {
		vertexDataModel.setWeight(weight);
	}

	/** {@inheritDoc} */
	@Override
	public VertexDataModel getDataModel() {
		return vertexDataModel;
	}

	/** {@inheritDoc} */
	@Override
	public void setDataModel(VertexDataModel vertexDataModel) {
		this.vertexDataModel = vertexDataModel;
	}

	/** {@inheritDoc} */
	@Override
	public void setColor(Color c) {
		SwingVertex.this.setForeground(c);
	}

	/** {@inheritDoc} */
	@Override
	public boolean isColoured() {
		return vertexDataModel.isColoured();
	}

	/** {@inheritDoc} */
	@Override
	public void setAsColored(boolean isColored) {
		vertexDataModel.setColoured(isColored);
	}

}
