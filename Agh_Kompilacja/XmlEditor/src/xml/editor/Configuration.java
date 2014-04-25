package xml.editor;

import java.awt.Dimension;
import java.awt.Toolkit;

/**
 * Przechowuje konfiguracje aplikacji.
 * @author malczyk.pawel@gmail.com
 *
 */
public final class Configuration {

	/**
	 * Rozmiar ekranu.
	 */
	public static final Dimension SCREEN_SIZE;
	
	/**
	 * Pusty prywatny konstruktor.
	 */
	private Configuration() {
		
	}


	/**
	 * Statyczny blok inicjalizujacy
	 */
	static {
		SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
	}
}
