package xml.editor.engine;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.text.Element;
import javax.swing.text.ParagraphView;
import javax.swing.text.View;

/**
 * Widok dla sekcji paragraph dokumentu
 * umozliwiajacy numerownie linii.
 *
 * @author malczyk.pawel@gmail.com
 *
 */
public class XmlEditParagraphView extends ParagraphView {

	/** Wciecie z gory. */
	private static final short INSET_TOP = 0;

	/** Wciecie z dolu. */
	private static final short INSET_BOTTOM = 0;

	/** Wciecie z lewej. */
	private static final short INSET_LEFT = 20;

	/** Wciecie z prawej. */
	private static final short INSET_RIGHT = 20;

	/**
	 * Konstruktor.
	 * @param elem element
	 */
	public XmlEditParagraphView(final Element elem) {
		super(elem);
		setInsets(INSET_TOP, INSET_LEFT, INSET_BOTTOM, INSET_RIGHT);
	}

	/** {@inheritDoc} */
	@Override
	protected void setInsets(final short top, final short left, final short bottom, final short right) {
		super.setInsets(top, left, bottom, right);
	}

	/** {@inheritDoc} */
	@Override
	protected void paintChild(final Graphics graphics, final Rectangle rect, final int index) {
		super.paintChild(graphics, rect, index);

		int locationX = rect.x - getLeftInset();
		int locationY = rect.y + rect.height - 4;

		graphics.setColor(Color.BLUE);
		graphics.drawString(String.valueOf(getPreviousLineCount()), locationX, locationY);
	}

	/**
	 * Zwraca numer wiersza na podstawie zagniezdzonych widokow.
	 *
	 * @return numer wiersza
	 */
	private int getPreviousLineCount() {
        int lineCount = 0;
        View parent = this.getParent();
        int count = parent.getViewCount();
        for (int i = 0; i < count; i++) {
            if (parent.getView(i) == this) {
                break;
            } else {
                lineCount += parent.getView(i).getViewCount();
            }
        }
        return lineCount;
    }



}
