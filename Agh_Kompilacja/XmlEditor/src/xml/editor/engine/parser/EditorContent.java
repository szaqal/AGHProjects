package xml.editor.engine.parser;

import java.util.Observable;

import javax.swing.text.Document;

/**
 * Zawartosc ulegajaca zmianie w trakcie edycji taki wrapper dla stringa
 * z uwzglenieniem obserwabli.
 *
 * @author malczyk.pawel@gmail.com
 *
 */
public class EditorContent extends Observable {

	/** Tresc edytowanego dokumentu. */
	private Document document;

	/**
	 * Ustawia nowa wartosc.
	 *
	 * @param docContent
	 *            textContent
	 */
	public void setDocContent(final Document docContent) {
		this.document = docContent;
		setChanged();
		notifyObservers(docContent);
	}

	/**
	 * Zwraca zawartosc dokumentu.
	 *
	 * @return zawartosc dokumentu
	 */
	public Document getTextContent() {
		return document;
	}


}
