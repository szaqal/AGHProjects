package xml.editor;

import xml.editor.gui.EditorFrame;

/**
 * Klasa g³ówna aplikacji.
 * @author malczyk.pawel@gmail.com
 *
 */
public final class XmlEditor {
	
	/**
	 * Konstruktor.
	 */
	private XmlEditor() { 
	}

	/**
	 * Punkt wejscia programu.
	 *
	 * @param args
	 *            argumenty z linii polecen
	 */
	public static void main(final String[]args) {
		new EditorFrame();
	}

}
