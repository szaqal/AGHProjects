package xml.editor.engine;

import java.awt.Color;

import javax.swing.text.Element;
import javax.swing.text.LabelView;

/**
 * Obsluguje wyswietalanie zwyklych kawalkow tekstu.
 * @author malczyk.pawel@gmail.com
 *
 */
public class XmlEditContentView extends LabelView {

	/** Sta³a. */
	private static final int BLUE = 253;
	
	/** Sta³a. */
	private static final int GREEN = 247;
	
	/** Sta³a. */
	private static final int RED = 242;

	/**
	 * Konstruktor.
	 * 
	 * @param elem
	 * 			element
	 */
	public XmlEditContentView(final Element elem) {
		super(elem);
	}

	/** {@inheritDoc} */
	@Override
	public Color getBackground() {
		return new Color(RED, GREEN, BLUE);
	}

}
