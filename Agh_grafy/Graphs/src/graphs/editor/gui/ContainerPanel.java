package graphs.editor.gui;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JPanel;

/**
 * Panel.
 * @author malczyk.pawel@gmail.com
 *
 */
public class ContainerPanel extends JPanel{
	
	/** Serial. */
	private static final long serialVersionUID = 6538977973802217289L;
	
	private CanvasPanel canvasPanel;
	private WeightPanel weightPanel;
	
	public ContainerPanel( Container parent ) {
		setLayout( new BorderLayout() );
		canvasPanel = new CanvasPanel(this);
		weightPanel = new WeightPanel(this);
	}

	public final CanvasPanel getCanvasPanel() {
		return canvasPanel;
	}

	public final WeightPanel getWieghtPanel() {
		return weightPanel;
	}
	
}
