package xml.editor.gui;

import java.awt.BorderLayout;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringBufferInputStream;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import xml.editor.engine.parser.EditorContent;
import xml.editor.engine.parser.EditorObeserver;

/**
 * Glowny panel edytora.
 * @author malczyk.pawel@gmail.com
 *
 */
public class EditorPanel extends JPanel {

	/**
	 * Serial.
	 */
	private static final long serialVersionUID = -4135143937091710686L;

	/**
	 * Wlasciwy edytor.
	 */
	private EditorPane editorPane;

	/**
	 * Zawartosc edytowaneog dokumentu.
	 */
	private EditorContent editorContent;

	/**
	 * Kontenera umozliwiajacy scrollowanie tekstu.
	 */
	private JScrollPane container;
	
	static boolean carredLocked = false;

	/**
	 * Konstruktor.
	 * @param parent rodzic komponentu
	 */
	public EditorPanel(final JFrame parent,final StatusBar statusBar) {
		System.out.println("Creating editor panel... ");
		setLayout(new BorderLayout());
		editorPane = EditorPane.getInstance();
		container = new JScrollPane(editorPane);
//		final CaretWaiter caretWaiter = new CaretWaiter(editorPane, statusBar);
//		new Thread(caretWaiter).start();
		editorPane.addCaretListener(new CaretListener() {

			/** {@inheritdoc}*/
			@Override
			public void caretUpdate(final CaretEvent e) {
				
				if( carredLocked )
					return;
				carredLocked = true;
				getEditorContent().setDocContent(editorPane.getDocument());
				boolean changed = false;
				String text = editorPane.getText();
				for( int i = 0; i != editorPane.getDocument().getLength(); i++ )
					if( text.charAt( i ) == '\t' )
					{
						String newText = text.substring( 0, i - 1 );
						newText += "        ";
						newText += text.substring( i + 1, text.length() );
						changed = true;
						text = newText;
					}
				
				if( changed )
				{
					int carretPosition = editorPane.getCaretPosition();
					InputStream stream = new StringBufferInputStream( text );
					try {
						editorPane.read( stream, null );
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					
					editorPane.setCaretPosition( carretPosition );
				}
				carredLocked = false;
				editorPane.processParsing();
				((EditorFrame) parent).getStatusBar().updateLocationLabel(editorPane.getResult());

//				caretWaiter.reset();
			}

		});
		
		add(container);
		parent.add(this, BorderLayout.CENTER);
	}

	/**
	 * Zwraca edytor.
	 *
	 * @return edytor
	 */
	public EditorContent getEditorContent() {
		if (editorContent == null) {
			editorContent = new EditorContent();
			editorContent.addObserver(new EditorObeserver(this));
		}
		return editorContent;
	}
/*
	public void processViolations(final List<ParseViolation> parseViolations) {
		for (ParseViolation parseViolation : parseViolations) {
			highlightViolation(parseViolation);
		}
	}

	private void highlightViolation(final ParseViolation parseViolation) {
		try {
			int startLoc = parseViolation.getLocation();
			int endLoc = startLoc + 4;
			editorPane.getHighlighter().addHighlight(startLoc, endLoc, new DefaultHighlightPainter(Color.RED));
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}
*/


}
