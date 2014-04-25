package xml.editor.engine.parser;

import java.io.Reader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;

import parser.ParseException;
import parser.XMLDTDParser;
import xml.editor.gui.EditorPane;
import xml.editor.gui.EditorStyles;

/**
 * Customizowalny "w³asny" parser.
 * @author malczyk.pawel@gmail.com
 * 
 * Jako ze zawsze po zmianie gramatyki trzeba parser
 * wygenerowac nie mozemy tam nic imlementowac
 * (tracili bysmy za kazdym razem wszystko.)
 * Ta klasa jest po to zeby przechwytywac wywolania metod parsera
 * 
 * skrypt perlowy helper.pl usuwa modyfikatory 
 * final umozliwjajac przesloniecie implementacji metod
 * 
 *
 */
public class MyParser extends XMLDTDParser {
	
	/**
	 * Uzywany komponent edytora.
	 */
	private EditorPane editorPane;

	/**
	 * Konstruktor.
	 * @param stream strumien wejsciowy
	 * @param editPane edytor
	 */
	public MyParser(final Reader stream, final EditorPane editPane) {
		super(stream);
		this.editorPane = editPane;
	}
	

//	/** {@inheritDoc} */
//	@Override
//	public String tag() throws ParseException {
//		String tagVal = super.tag();
//		String fullTagName = "<" + tagVal + ">";
//		processTag(fullTagName);
//		System.out.println("Token : " + tagVal);
//		return tagVal;
//	}

	/**
	 * Oznacza element xml'owy (Otwieracy).
	 * @param tagVal nazwa taga z "<" i ">"
	 * @param matcher {@linkplain Matcher} matcher
	 */
	private void markTag(final String tagVal, final Matcher matcher) {
		AttributeSet attrs = EditorStyles.createElementStyle((StyledDocument) editorPane.getDocument());
		((StyledDocument) editorPane.getDocument()).setCharacterAttributes(matcher.start(), tagVal.length(), attrs, true);
	}
	
//	/** {@inheritDoc} */
//	@Override
//	public String endTag() throws ParseException {
//		String endTagVal = super.endTag();
//		String fullTagName = "</" + endTagVal + ">";
//		processTag(fullTagName);
//		return endTagVal;
//	}

	/**
	 * Zaznacza taga w edytorze.
	 * @param fullTagName pelna nazwa taga do oznaczenia
	 */
	private void processTag(final String fullTagName) {
		try {
			String text = editorPane.getDocument().getText(0, editorPane.getDocument().getLength());
			Pattern pattern = Pattern.compile(fullTagName);
			Matcher matcher = pattern.matcher(text);
			while (matcher.find()) {
				markTag(fullTagName, matcher);
			}
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}
	

	
	

}
