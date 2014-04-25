package graphs.editor.gui;

import graphs.Graphs;
import graphs.domain.api.Vertex;
import graphs.editor.domain.SwingEdge;
import graphs.editor.domain.SwingVertex;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Pozwala zdecydowac do ktorego wezla sie polaczyc
 * @author malczyk.pawel@gmail.com
 *
 */
public class ConntectToFrame extends AbstractCommonFrame implements ActionListener {

	/** Serial. */
	private static final long serialVersionUID = 1L;
	
	/** Labelka.*/
	private static final String lblEdgeWeight = "Waga krawędzi";
	
	private static final String WINDOW_TITLE = "Połącz z... ";
	
	/** Szerokosc ramki. */
	private static final int WINDOW_WIDTH = 250;
	
	/** Wysokosc ramki. */
	private static final int WINDOW_HEIGHT = 180;	
	
	/** Tablica labelek przyciskow*/
	private static final String [] BUTTON_LABELS = {"Akceptuj","Anuluj"};
	
	/** Talica ikon. */
	//public static final String [] TOOLBAR_ICONS = {"/dialog-apply.png","/cancel.png"};
	
	/** Tablica przyciskow na ramce*/
	private JButton [] buttons;
	
	/** Combox z wezlami. */
	private JComboBox availableNodeList;
	
	/** Panel glowny. */
	private JPanel mainPanel = new JPanel();
	
	/** Panel z przyciskami. */
	private JPanel buttonPanel;
	
	/** TextBox dla wagi krawedzi*/
	private JTextField txtWeight;
	
	
	/** Wezel do ktorego ktory bedzie podlaczony */
	private Vertex node;	
	
	/** To na czym  bedzie rysowana linia*/
	private CanvasPanel canvasPanel;

	/**
	 * Konstruktor
	 * @param node wezelz ktorego tworzymy krawedz
	 * @param canvas plotno n aktorym rysowany jest graf
	 */
	public ConntectToFrame(Vertex node, CanvasPanel canvas ){
		super( new Dimension(WINDOW_WIDTH,WINDOW_HEIGHT), WINDOW_TITLE, new BorderLayout() );
		this.node = node;
		this.canvasPanel = canvas;
				
				
		buttonPanel = new JPanel();
		buttonPanel.setLayout(null);
		mainPanel.setLayout(null);
		buttonPanel.setForeground(Color.blue);
				
		
		availableNodeList = setupNodesList();
		txtWeight = setupWeightField();
		mainPanel.add(availableNodeList);
		mainPanel.add(setupLbl());
		mainPanel.add(txtWeight);		
		
		buttons = setupButtons();
		for (JButton btn : buttons ) {
			mainPanel.add(btn);
		}		
		
		add(mainPanel, BorderLayout.CENTER);
		
	}
	
	/**
	 * Ustawia liste rozwijalna.
	 * @return wypelniona lista rozwijalna
	 */
	private JComboBox setupNodesList() {
		JComboBox resultList = new JComboBox();
		Collection<SwingVertex> graphNodes = Graphs.getGraph().getVertexes();
		Iterator<SwingVertex> iter = graphNodes.iterator();
		while(iter.hasNext()) {
			SwingVertex node = iter.next();
			resultList.addItem(String.valueOf(node.getIndex()));
		}
		resultList.setBounds(10, 10, 150, 25);
		return resultList;
	}
	
	/**
	 * Ustawia etykiete
	 * @return przygotowana etykieta
	 */
	private JLabel setupLbl() {
		JLabel label = new JLabel();
		label.setText(lblEdgeWeight);
		label.setBounds(10, 40, 110, 25);
		return label;
	}
	
	/**
	 * Ustawia pole tekstowe.
	 * @return ustawione pole tekstowe
	 */
	private JTextField setupWeightField() {
		JTextField txtField = new JTextField();
		txtField.setBounds(120, 40, 40, 25);
		txtField.setText("0");
		return txtField;
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
			button.setBounds(105*i+10, 80, 100, 35);
			//button.setIcon(new ImageIcon(this.getClass().getResource(TOOLBAR_ICONS[i])));
			buttonArray[i] = button;
		}		
		return buttonArray;
	}

	/** {@inheritDoc} */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == buttons[0]) { //dodaj
			int selectedVertexIndex = Integer.valueOf(availableNodeList.getSelectedItem().toString());
			SwingEdge swingLine = new SwingEdge(node, Graphs.getGraph().getVertexByIndex(selectedVertexIndex));
			swingLine.setWeight(Integer.parseInt(txtWeight.getText()));
			Graphs.getGraph().addEdge(swingLine);
			swingLine.draw(ConntectToFrame.this.canvasPanel.getCanvas() );
			NeibourhoodMatrixPanel matrixPanel = (NeibourhoodMatrixPanel)ConntectToFrame.this.canvasPanel.getParent().getParent().getComponent(1);
			matrixPanel.getNebourhoodMatrixTable().setModel(new NeibourhoodTableDataModel());
			canvasPanel.repaint();		
			
			((ContainerPanel)canvasPanel.getParent()).getWieghtPanel().getEdgesPanel().update();
			
			dispose();
		} else if (e.getSource() == buttons[1]) { //anuluj
			dispose();
		}
	}
}
