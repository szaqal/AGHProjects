package graphs;

import graphs.domain.GraphFactory;
import graphs.domain.api.Graph;
import graphs.editor.domain.SwingVertex;
import graphs.editor.gui.MainFrame;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * Glowna klasa
 * @author malczyk.pawel@gmail.com
 *
 */
public class Graphs {
	
	/** Reprezentuje przetwarzany graf */
	private static Graph<SwingVertex> graph = GraphFactory.getInstance().createGraph(SwingVertex.class); 

	public Graphs(){
		JFrame frame = new MainFrame();
		
		int result=JOptionPane.showOptionDialog(frame, "Czy graf ma być skierowany", "Zapytanie",  JOptionPane.YES_NO_OPTION, 
				JOptionPane.QUESTION_MESSAGE, null, null, null);
		
		// Ustawia graf skierowany albo nie
		switch(result) {
			case JOptionPane.YES_OPTION:graph.setDirected(true); break;
			case JOptionPane.NO_OPTION:graph.setDirected(false); break;
			default: break;
		};		
	}
	
	public static void main(String[]args){
		
		// ustawienie look and feel aplikacji
		try {			
			MainFrame.setLookAndFeel();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		new Graphs();
	}
	
	public static Graph<SwingVertex> getGraph() {
		return graph;
	}
	
	public static void setGraph(Graph<SwingVertex> arg) {
		graph = arg;
	}
}

