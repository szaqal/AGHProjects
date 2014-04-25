package xml.editor.engine;

import javax.swing.text.StyledEditorKit;
import javax.swing.text.ViewFactory;

/**
 *
 * @author malczyk.pawel@gmail.com
 *
 */
public class XmlEditorKit extends StyledEditorKit {

	/**
	 * Serial.
	 */
	private static final long serialVersionUID = -1564344310582150105L;

	/**
	 * Factory widokow.
	 */
	private ViewFactory viewFactory;

	/**
	 * Mime pliku.
	 */
	public static final String MIME_XML = "application/xml";

	/** {@inheritDoc} */
	@Override
	public String getContentType() {
		return MIME_XML;
	}

	/** {@inheritDoc} */
	@Override
	public ViewFactory getViewFactory() {
		if (viewFactory == null) {
			viewFactory = new XmlEditViewFactory();
		}
		return viewFactory;
	}
	
}
