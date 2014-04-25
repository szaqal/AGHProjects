package graphs.editor.gui;

import graphs.Graphs;
import graphs.algorithms.AlgorithmFactory;
import graphs.algorithms.KruskalAlgorithm;
import graphs.domain.api.Edge;
import graphs.domain.api.Graph;
import graphs.domain.api.Vertex;
import graphs.editor.domain.SwingVertex;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

/**
 * Panel usuwania krawedzi.
 * @author malczyk.pawel@gmail.com
 *
 */
public class RemoveEdgesFrame extends AbstractCommonFrame implements ActionListener {

	/** Serial. */
	private static final long serialVersionUID = -3085147280115278347L;
	
	/** Labelki przyciskow. */
	private static final String [] BUTTON_LABELS = {"Akceptuj","Anuluj"};
	
	/** Labelki ikon */
	//private static final String [] TOOLBAR_ICONS = {"/dialog-apply.png","/cancel.png"};
	
	/** Szerokosc ramki */
	private static final int WINDOW_WIDTH = 250;
	
	/** Wysokosc ramki*/
	private static final int WINDOW_HEIGHT = 200;
	
	/** Etykieta ramki. */
	private static final String WINDOW_TITLE = "Usuwanie krawedzi";
	
	/** Tablica przyciskow na ramce */
	private JButton [] buttons;
	
	/** Glowny panel */
	private JPanel mainPanel = new JPanel();
	
	/** Wezl od ktorego usuwane beda krawedzie. */
	private Vertex startingVertex;
	
	/** Plotno. */
	private CanvasPanel canvas;
	
	/** Lista checbox ktora zaweira polaczone z danym wierzcholkiem krawedzie*/
	private List<JCheckBox> checkBoxes = new ArrayList<JCheckBox>();
	
	
	/**
	 * Konstruktor.
	 * @param vertex weierzcholek
	 * @param canvas plotno
	 */
	public RemoveEdgesFrame(Vertex vertex, CanvasPanel canvas){
		super( new Dimension(WINDOW_WIDTH,WINDOW_HEIGHT), WINDOW_TITLE, new BorderLayout() );
		startingVertex = vertex;
		this.canvas = canvas;					
		mainPanel.setLayout(null);		
		
		JScrollPane scrollPane = new JScrollPane(setupListPanel(),ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);	
		scrollPane.setBounds(10, 10, 200, 100);
		
		
		buttons = setupButtons();
		for (JButton btn : buttons ) {
			mainPanel.add(btn);
		}
		mainPanel.add(scrollPane);
		
		add(mainPanel, BorderLayout.CENTER);
	}
	
	private JPanel setupListPanel() {
		JPanel resultPanel = new JPanel();
		resultPanel.setLayout(new GridLayout(10,1));
		Collection<Edge> edges = Graphs.getGraph().getEdges();
		
		for ( Edge edge :edges ) {
			//Dla nie skierowanego !!!
			if (edge.getStartVertex() == startingVertex ) {
				JCheckBox checkBox = new JCheckBox("-> " + edge.getEndVertex().getIndex() );
				resultPanel.add(checkBox);	
				checkBoxes.add(checkBox);
			} else if ( edge.getEndVertex() == startingVertex ) {
				JCheckBox checkBox = new JCheckBox("-> " + edge.getStartVertex().getIndex() );
				resultPanel.add(checkBox);
				checkBoxes.add(checkBox);
			}
		}		
		return resultPanel;
	}
	
	
	
	/**
	 * Ustawia tablice przyciskow;
	 * @return tablica przygotowanych buttonow
	 */
	private JButton [] setupButtons() {
		JButton [] buttonArray = new JButton[BUTTON_LABELS.length];
		
		for (int i=0;i<BUTTON_LABELS.length;i++) {
			JButton button = new JButton(BUTTON_LABELS[i]);
			button.addActionListener(this);
			button.setBounds(105*i+10, 120, 100, 35);
			//button.setIcon(new ImageIcon(this.getClass().getResource(TOOLBAR_ICONS[i])));
			buttonArray[i] = button;
		}		
		return buttonArray;
	}
	
	/**
	 * Usuwa krawedzie do od biezacego wezla do wskazanych indeksami.
	 * @param indexes lista indeksow wezlow
	 */
	private void removeSelectedEdges(List<Integer> indexes) {
		Graph<SwingVertex> graph = Graphs.getGraph();
		Collection<Edge> toDelete = new ArrayList<Edge>();
		Collection<Edge> edges= graph.getEdges();
		for ( Integer index : indexes ) {
			for ( Edge edge: edges ) {
				if ( edge.getStartVertex().getIndex() == startingVertex.getIndex() &&  edge.getEndVertex().getIndex() == index ) {
										
					if (edge instanceof Component ) {						
						canvas.removeEdge((Component)edge);
					}					
					toDelete.add(edge);					
				} else if ( edge.getEndVertex().getIndex() == startingVertex.getIndex() && edge.getStartVertex().getIndex() == index ) {
					if (edge instanceof Component ) {						
						canvas.removeEdge((Component)edge);
					}					
					toDelete.add(edge);
				}
				
			}
			
		}
	
		for (Edge edge : toDelete){
			graph.removeEdge(edge);	
		}
		
		((ContainerPanel)canvas.getParent()).getWieghtPanel().getEdgesPanel().update();
		canvas.updateTableDataModel();
		//FIXME: poprawic
		((KruskalAlgorithm)AlgorithmFactory.getAlgorithm(KruskalAlgorithm.class)).findAlternatives(graph);
	}
		

	/**{@inheritDoc} */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == buttons[0] ) {
			List<Integer> indexes = new ArrayList<Integer>();
			for ( JCheckBox checkbox:checkBoxes ) {
				if ( checkbox.isSelected() ) {					
					indexes.add(Integer.valueOf(checkbox.getText().substring(3)));
				}
			}
			removeSelectedEdges(indexes);
			dispose();
			
		} else if ( e.getSource() == buttons [1] ) {
			dispose();
		}
	}

}
