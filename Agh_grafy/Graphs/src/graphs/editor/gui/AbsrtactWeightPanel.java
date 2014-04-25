package graphs.editor.gui;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JPanel;

/**
 * Abstrakcyjna klasa obiektow ktore odpowidzialne sa za zmiane wartosci wag.
 * (wierzcholki/krawedzie)
 * @author malczyk.pawel@gmail.com
 *
 */
public class AbsrtactWeightPanel extends JPanel {
	
	/** Serial. */
	private static final long serialVersionUID = 5275490191237490962L;
	
	/** Komponent parenta. */
	private JPanel parent;
	
	/**
	 * Konstruktor
	 * @param parentContainer komponenet parenta.
	 */
	public AbsrtactWeightPanel(JPanel parentContainer) {
		parent  = parentContainer;
		setLayout(new BorderLayout());
		setSize(100, (int)parent.getSize().getHeight());
	}
	
	@Override
	public Container getParent() {
		return parent;
	}
	
}
