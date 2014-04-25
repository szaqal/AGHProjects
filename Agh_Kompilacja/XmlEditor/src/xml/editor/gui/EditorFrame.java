package xml.editor.gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import xml.editor.Configuration;

/**
 * Glowna ramka aplikacji.
 * @author malczyk.pawel@gmail.com
 *
 */
public class EditorFrame extends JFrame {

	/** Serial. */
	private static final long serialVersionUID = 8004592653072851212L;

	/** Poczatkowa szerokosc. */
	private static final int INITIAL_WIDTH = 700;

	/** Poczatkowa wysokosc. */
	private static final int INITIAL_HEIGHT = 500;

	/** ToolBar. */
	private JToolBar toolBar;

	/** StatusBar. */
	private JPanel statusBar;

	/** Panel edytora. */
	private JPanel editorPanel;



	/**
	 * Konstruktor.
	 */
	public EditorFrame() {
		setSize(INITIAL_WIDTH, INITIAL_HEIGHT);
		setLocation((int) Configuration.SCREEN_SIZE.width  / 2 - INITIAL_WIDTH / 2,
				(int) Configuration.SCREEN_SIZE.height / 2 - INITIAL_HEIGHT / 2);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		toolBar = new ToolBar(this);
		statusBar = new StatusBar(this);
		editorPanel = new EditorPanel(this, (StatusBar)statusBar);
		setTitle("Edytor XML");
		setVisible(true);
	}


	/**
	 * Getter.
	 *
	 * @return {@link ToolBar}
	 */
	public final JToolBar getToolBar() {
		return toolBar;
	}


	/**
	 * Getter.
	 * @return {@link StatusBar}
	 */
	public final StatusBar getStatusBar() {
		return (StatusBar) statusBar;
	}

	/**
	 * Getter.
	 * @return {@link EditorPanel}
	 */
	public final JPanel getEditorPanel() {
		return editorPanel;
	}

}
