package graphs.editor.gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * G��wne okno
 * @author malczyk.pawel@gmail.com
 */
public class MainFrame extends JFrame {

	/** Uzywany look and feel. */
	private static final String GTK_LOOK_AND_FEEL = "com.sun.java.swing.plaf.gtk.GTKLookAndFeel";
	
	/** Etykieta okna*/
	private static final String WINDOW_TITLE = "Grafy Paweł Malczyk";
	
	/** Zakladki*/
	private JTabbedPane tabbedPane;
	
	/** Serial. */
	private static final long serialVersionUID = 1552303426604156398L;
	
	public MainFrame(){
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(600,500);
		setLocation(0, 0);
		setVisible(true);
		setTitle(WINDOW_TITLE);
		setLayout(new BorderLayout());
		new ToolBar(this);
		
		tabbedPane = new JTabbedPane();
		tabbedPane.add("Graf", new ContainerPanel(tabbedPane));
		tabbedPane.add("Macierz sąsiedztwa", new NeibourhoodMatrixPanel(tabbedPane));
		
		add(tabbedPane);
		
	}
	
	public static void setLookAndFeel() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		UIManager.setLookAndFeel(GTK_LOOK_AND_FEEL);
	}

}
