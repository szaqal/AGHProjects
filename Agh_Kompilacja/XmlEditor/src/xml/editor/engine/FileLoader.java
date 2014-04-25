package xml.editor.engine;

import java.io.File;

import javax.swing.JFileChooser;

import xml.editor.gui.EditorPane;

/**
 * Wczytuje i przechowuje pliki (singleton).
 *
 * @author malczyk.pawel@gmail.com
 *
 */
public final class FileLoader {

	/** Instancja. */
	private static FileLoader instance;
	
	/**
	 * Prefiks protoko³u.
	 */
	public static final String FILE_PROTOCOL = "file://";

	/**
	 * Prywatny konstruktor.
	 */
	private FileLoader() { }

	/**
	 * Tworzy i zwraca dialog otwierania.
	 *
	 * @return dialog otwierania
	 */
	public JFileChooser createOpenDialog() {
		JFileChooser fileChooser = new JFileChooser();
		return fileChooser;
	}

	/**
	 * Zwraca instancje {@link FileLoader}.
	 *
	 * @return jedyna istniejaca instancja {@link FileLoader}
	 */
	public static FileLoader getInstance() {
		if (instance == null) {
			instance = new FileLoader();
		}
		return instance;
	}
	
	/**
	 *  Wczytuje dokument.
	 *  @param file plik
	 */
	public void loadDocument(final File file) {
		EditorPane.getInstance().setPage(file);
	}

}
