package turistcompany.gui.frames;

import java.awt.Dimension;

import javax.swing.JFrame;

import turistcompany.TuristCompanyConstants;

/**
 * Abstraktcyjna klasa dla wszystkich ramek ktore sa uzywane do dodawania 
 * roznych elementow.
 * @author malczyk.pawel@gmail.com
 *
 */
public abstract class AbstractNewItemFrame extends JFrame {

	/** Serial. */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Konstruktor.
	 * @param width szerokosc ramki
	 * @param height wysokosc ramki
	 * @param title tytul ramki
	 */
	public AbstractNewItemFrame(int width, int height, String title ) {
		super();
		setSize(new Dimension(width, height));
		setTitle(title);
		
		setLocation( (int)TuristCompanyConstants.SCREEN_SIZE.getWidth()/2 - width/2, 
				(int)TuristCompanyConstants.SCREEN_SIZE.getHeight()/2 - height/2);
		
		setVisible(true);
	}

}
