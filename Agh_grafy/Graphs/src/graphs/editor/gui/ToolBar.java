package graphs.editor.gui;

import graphs.Graphs;
import graphs.domain.GraphFactory;
import graphs.editor.domain.SwingVertex;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;

/**
 * ToolBar aplikacji
 * @author malczyk.pawel@gmail.com
 *
 */
public class ToolBar extends JToolBar {
	
	/** Serial. */
	private static final long serialVersionUID = 4499690378267212329L;
	public static final String [] TOOLBAR_LABELS = {"Wyjdź","Dodaj Węzeł","Uruchom","Nowy Graf"};
	//public static final String [] TOOLBAR_ICONS = {"/shutdown.png","/stock_add-bookmark.png","/gksu.png","/gksu.png"};
	private Map<String, JButton> toolbarButtons;
	private ActionListener actionListener;
	
	public ToolBar(JFrame parent) {
		actionListener = this.new ToolBarButtonListener();
		prepareButtons();
		for(JButton btn:toolbarButtons.values()){
			add(btn);
		}
		parent.add(this, BorderLayout.NORTH);
	}
	
	
	private class ToolBarButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
		    if (e.getSource() == toolbarButtons.get(TOOLBAR_LABELS[0]) ) {		    	
		    	System.exit(0);
		    } else if (e.getSource() == toolbarButtons.get(TOOLBAR_LABELS[1])) {
		    	if (e.getSource() instanceof JButton ) {
		    		((JButton)e.getSource()).setEnabled(false);
		    		JTabbedPane tabbedPane = (JTabbedPane)ToolBar.this.getParent().getComponent(1);		    		
		    		ContainerPanel graphPanel = (ContainerPanel)tabbedPane.getComponent(0);
		    		CanvasPanel canvas = graphPanel.getCanvasPanel();
		    		canvas.setAllowNodeInsertion(true);
		    		canvas.setCursor(new Cursor(Cursor.HAND_CURSOR));
		    	}		    	
		    } else if (e.getSource() == toolbarButtons.get(TOOLBAR_LABELS[2])) {
		    	JTabbedPane tabbedPane = (JTabbedPane)ToolBar.this.getParent().getComponent(1);
		    	ContainerPanel graphPanel = (ContainerPanel)tabbedPane.getComponent(0);
	    		CanvasPanel canvas = graphPanel.getCanvasPanel();
		    	new AlgSelectFrame(canvas);
		    } else if (e.getSource() == toolbarButtons.get(TOOLBAR_LABELS[3])) {
		    	Graphs.setGraph(GraphFactory.getInstance().createGraph(SwingVertex.class));		    	
		    	JTabbedPane tabbedPane = (JTabbedPane)ToolBar.this.getParent().getComponent(1);
		    	ContainerPanel graphPanel = (ContainerPanel)tabbedPane.getComponent(0);
	    		CanvasPanel canvas = graphPanel.getCanvasPanel();
	    		EdgesPanel edgesPanel = graphPanel.getWieghtPanel().getEdgesPanel();
	    		VertexPanel vertexPanel = graphPanel.getWieghtPanel().getVertexPanel();
	    		canvas.clearCavnas();
	    		edgesPanel.update();
	    		vertexPanel.update();
		    	
		    }
		    
		}		
	}

	/**
	 * Przygotowuje buttony na toolbar
	 */
	private void prepareButtons(){
		toolbarButtons = new HashMap<String, JButton>();
		int i=0;
		for (String label: TOOLBAR_LABELS){
			JButton button = new JButton(label);
			//button.setIcon(new ImageIcon(this.getClass().getResource(TOOLBAR_ICONS[i])));
			button.setVisible(true);
			button.addActionListener(actionListener);
			toolbarButtons.put(label,button);
			i++;
		}
		
		
		
	}

	public Map<String, JButton> getToolbarButtons() {
		return toolbarButtons;
	}

}
