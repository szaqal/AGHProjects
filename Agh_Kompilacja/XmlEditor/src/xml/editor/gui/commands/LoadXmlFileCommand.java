package xml.editor.gui.commands;

import javax.swing.JFileChooser;
import javax.swing.JFrame;


/**
 * Command wczytywania pliku xml.
 * @author malczyk.pawel@gmail.com
 *
 */
public class LoadXmlFileCommand extends AbstractOpenFileCommand implements EditorCommand {

	/**
	 * Glowna ramka.
	 */
	private JFrame frame;

	/**
	 * Konstruktor.
	 *
	 * @param parentFrame
	 *            ramka.
	 */
	public LoadXmlFileCommand(final JFrame parentFrame) {
		this.frame = parentFrame;
	}

	/** {@inheritDoc} */
	@Override
	public void perform() {
		System.out.println("Loading XML");
		JFileChooser fileChooser = createDialog(DocumentType.XML);
		processDialogFeedBack(fileChooser.showOpenDialog(frame), fileChooser.getSelectedFile());
	}

}
