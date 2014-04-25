package xml.editor.engine.parser;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.text.Document;

import xml.editor.gui.EditorPanel;

/**
 * Obsererver zmian w trakcie edycji.
 * @author malczyk.pawel@gmail.com
 *
 */
public class EditorObeserver implements Observer {

	/**
	 * Panel edycji.
	 */
	private EditorPanel editorPanel;

	/**
	 * Konstuktor.
	 *
	 * @param argPanel
	 *            panel edycji
	 */
	public EditorObeserver(final EditorPanel argPanel) {
		this.editorPanel = argPanel;
	}

	/** {@inheritDoc} */
	@Override
	public void update(final Observable observable, final Object content) {
//		if (content instanceof Document) {
//			List<ParseViolation> parseViolations = new XmlParser().parse((Document) content);
//			editorPanel.processViolations(parseViolations);
//		}
	}

}
