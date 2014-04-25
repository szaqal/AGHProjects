package graphs.editor.gui;

import graphs.domain.SimpleLocation;
import graphs.utils.Utils;

import java.awt.Dimension;
import java.awt.LayoutManager;

import javax.swing.JFrame;

/**
 * Abstrakcyjna ramka inicjalizujaca to co jest wspolne dla wszystkich 
 * ramek w aplikacji.
 * @author malczyk.pawel@gmail.com
 *
 */
public abstract class AbstractCommonFrame extends JFrame {
	
	/** Serial. */
	private static final long serialVersionUID = -4664862477316669484L;

	/**
	 * Konstruktor 
	 * @param dimension wielkosc ramki
	 * @param title etykieta ramki
	 * @param layout
	 */
	public AbstractCommonFrame(Dimension dimension, final String title, LayoutManager layout) {
		setVisible(true);
		SimpleLocation location = Utils.computeFrameLocation(dimension.width, dimension.height);
		setTitle(title);
		setLocation(location.getLocationX(),location.getLocationY());
		setLayout(layout);
		setSize(dimension);
		setAlwaysOnTop(true);
	}
}
