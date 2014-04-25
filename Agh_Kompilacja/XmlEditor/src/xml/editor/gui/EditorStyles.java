package xml.editor.gui;

import java.awt.Color;

import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

/**
 * Style edytora sluzace do odpowiedniego wyswietlania.
 * elementow (atrybutow, elementow itp..)
 * @author malczyk.pawel@gmail.com
 *
 * TU NALEZY DOPISYWAC KOLEJNE
 *
 */
public final class EditorStyles {
	
	
	/**
	 * Konstruktor.
	 */
	private EditorStyles() { }
	
	
	/**
	 * Tworzy styl elementow XML.
	 * @param document dokument
	 * @return styl
	 */
	public static Style createElementStyle(final StyledDocument document) {
		Style italic = document.addStyle("italic", createDefaultStyle(document));
		StyleConstants.setItalic(italic, true);
		StyleConstants.setForeground(italic, Color.BLUE);
		return italic;
	}
	
	/**
	 * Typowy domyslny styl.
	 * @param document dokument
	 * @return domyslny styl
	 */
	private static Style createDefaultStyle(final StyledDocument document) {
		Style defaultStyle = StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE);
		return document.addStyle("regular", defaultStyle);
	}
	
	/**
	 * Resetuje styl na calym dokumencie.
	 * @param document dokument
	 */
	public static void resetStyle(final StyledDocument document) {
		document.setCharacterAttributes(0, document.getLength(), createDefaultStyle(document), true);
	}

}
