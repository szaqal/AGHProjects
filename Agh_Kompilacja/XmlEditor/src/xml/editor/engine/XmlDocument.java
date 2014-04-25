package xml.editor.engine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import javax.swing.event.DocumentEvent;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.undo.UndoableEdit;

import xml.editor.Strings;

/**
 * Reprezentuje edytowany dokument XML.
 * {@link DefaultStyledDocument}
 *
 * @author malczyk.pawel@gmail.com
 *
 */
public class XmlDocument extends DefaultStyledDocument {

	/**
	 * Serial.
	 */
	private static final long serialVersionUID = -7557369678521456497L;


	/**
	 * Zwraca pelna stringowa reprezentacje dokumentu.
	 * @return
	 * 	pelny zapisany w dokumencie string
	 */
	public String getFullText() {
		try {
			return getText(0, this.getLength());
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		return Strings.EMPTY;
	}

	/**
	 * Oblicza polozenie w dokumencie elementu.
	 * (TODO Raczej mi sie to nie podoba, musi byc lepszy sposob)
	 *
	 * @param lineNumber
	 *            numer linii
	 * @param positionInLine
	 *            polozenie w linii.
	 * @return polozenie wzgledem calego dokumentu
	 */
	public int computeLocation(final int lineNumber, final int positionInLine) {

		int position = 0;

		BufferedReader reader = new BufferedReader(new StringReader(getFullText()));

		String line = null;
		int lineNr = 0;
		try {
			while ((line = reader.readLine()) != null) {
				if (lineNr == lineNumber) {
					position += positionInLine;
					break;
				} else {
					position += line.length() + 1; //1 za przejscie do nowej linii inaczej jest gubiona
					lineNr++;
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
 
		return position;
	}

	/** {@inheritDoc} */
	@Override
	protected void fireRemoveUpdate(DocumentEvent evt) {
		super.fireRemoveUpdate(evt);
	}

	/** {@inheritDoc} */
	@Override
	protected void fireInsertUpdate(DocumentEvent evt) {
		super.fireInsertUpdate(evt);
	}
	
	public void beginCompoundEdit() {}

	public void endCompoundEdit() {}

	public void addUndoableEdit(UndoableEdit edit) {}





}
