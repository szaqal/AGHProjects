package graphs.editor.gui;

import graphs.Graphs;
import graphs.algorithms.AlgorithmFactory;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

/**
 * Wybor algorytmu do uruchomienia
 * @author malczyk.pawel@gmail.com
 *
 */
public class AlgSelectFrame extends AbstractCommonFrame implements ActionListener {
	
	/** Serial. */
	private static final long serialVersionUID = -5515659601440761557L;
	
	/** Szerokosc ramki*/
	private static final int WINDOW_WIDTH = 400;
	
	/** Wysokoscsc ramki*/
	private static final int WINDOW_HEIGHT = 400;
	
	/** Nzwa ramki*/
	private static final String WINDOW_TITLE = "Wybor algorymu";
	
	/** Labelki przyciskow. */
	private static final String [] labels = {"Uruchom","Anuluj"};
	
	/** Ikony przyciskow. */
	//private static final String [] icons = {"/gksu.png","/cancel.png"};
	
	/** Panel radiobuttonow*/
	private JPanel radioPanel = new JPanel();
	
	/** Panel przyciskow*/
	private JPanel buttonPanel = new JPanel();
	
	/** Tablica przyciskow*/
	private JButton [] buttons;
	
	/** Wybrany algorytm*/
	private String selectedAlgorithm;
	
	/** plotno*/
	private JPanel canvas;
	
	
	/**
	 * Konstruktor.
	 * @param canvasPanel
	 */
	public AlgSelectFrame(JPanel canvasPanel) {
		super( new Dimension(WINDOW_WIDTH,WINDOW_HEIGHT), WINDOW_TITLE, new BorderLayout() );
		canvas = canvasPanel;	
		prepareChoises();		
		add(radioPanel, BorderLayout.CENTER);
		
		buttons = setupButtons();
		for (JButton button : buttons) {
			buttonPanel.add(button);		
		}
		add(buttonPanel, BorderLayout.SOUTH);
	}
	
	/**
	 * Przygototwuje liste algorytmow
	 */
	private void prepareChoises() {
		ButtonGroup group = new ButtonGroup();
		for (String algName : AlgorithmFactory.getAlgorithms() ){
			JRadioButton radioButton = new JRadioButton(algName);
			radioButton.setActionCommand(algName);
			radioButton.addActionListener(this);
			group.add(radioButton);
			radioPanel.add(radioButton);
		}
		
	}

	/**
	 * Ustawia przyciski
	 * @return tablica przygotowanych przyciskow
	 */
	private JButton [] setupButtons() {
		JButton [] btns = new JButton[labels.length];
		for (int i=0;i<labels.length;i++){
			JButton btn = new JButton();
			btn.setText(labels[i]);
			//btn.setIcon(new ImageIcon(this.getClass().getResource(icons[i])));
			btn.addActionListener(this);
			btns[i] = btn;
		}
		return btns;
	}
	

	/** {@inheritDoc} */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == buttons[1]) {
			dispose();
		} else if (arg0.getSource() == buttons[0] ) {
			if (selectedAlgorithm != null) {
				AlgorithmFactory.getAlgorithm(selectedAlgorithm).processAlgorithm(Graphs.getGraph());
				dispose();
				canvas.repaint();
				
				ContainerPanel panel = (ContainerPanel) canvas.getParent();
				panel.getWieghtPanel().getEdgesPanel().update();
			}
		} else {
			if (arg0.getSource() instanceof JRadioButton ) {
				selectedAlgorithm = ((JRadioButton)arg0.getSource()).getText();
			}			
		}
		
	}

}
