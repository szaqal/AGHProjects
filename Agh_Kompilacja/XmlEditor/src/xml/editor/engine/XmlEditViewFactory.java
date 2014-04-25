package xml.editor.engine;

import javax.swing.text.AbstractDocument;
import javax.swing.text.BoxView;
import javax.swing.text.ComponentView;
import javax.swing.text.Element;
import javax.swing.text.IconView;
import javax.swing.text.LabelView;
import javax.swing.text.StyleConstants;
import javax.swing.text.View;
import javax.swing.text.ViewFactory;

/**
 * Factory widokow edytora.
 *<ol></ol>
 * @see {@link Document}
 * @see {@link ViewFactory}
 * @see {@link Element}
 *
 * @author malczyk.pawel@gmail.com
 *
 */
public class XmlEditViewFactory implements ViewFactory {

	/** {@inheritDoc} */
	@Override
	public View create(final Element elem) {

		//przeniesione z StyledViewFactory
		String kind = elem.getName();
		if (kind != null) {
			if (kind.equals(AbstractDocument.ContentElementName)) {
				return new XmlEditContentView(elem);
			} else if (kind.equals(AbstractDocument.ParagraphElementName)) {
				//wlasna implemetacja !!!
				return new XmlEditParagraphView(elem);
			} else if (kind.equals(AbstractDocument.SectionElementName)) {
				return new BoxView(elem, View.Y_AXIS);
			} else if (kind.equals(StyleConstants.ComponentElementName)) {
				return new ComponentView(elem);
			} else if (kind.equals(StyleConstants.IconElementName)) {
				return new IconView(elem);
			}
		}
		return new LabelView(elem);
	}

}
