package xml.editor.gui.commands;

import javax.swing.JFileChooser;
import javax.swing.JFrame;


/**
 * Command ladowania pliku DTD.
 *
 * @author malczyk.pawel@gmail.com
 *
 * @deprecated NIE UZYWANE
 */
@Deprecated
public class LoadDtdFileCommand extends AbstractOpenFileCommand implements EditorCommand {

	/**
	 * Glowwna ramka.
	 */
	private JFrame frame;

	/**
	 * Konstruktor.
	 *
	 * @param parentFrame
	 *            toolBar
	 */
	public LoadDtdFileCommand(final JFrame parentFrame) {
		this.frame = parentFrame;
	}

	/** {@inheritDoc} */
	@Override
	public void perform() {
		System.out.println("Loading DTD");
		JFileChooser fileChooser = createDialog(DocumentType.DTD);
		processDialogFeedBack(fileChooser.showOpenDialog(frame), fileChooser.getSelectedFile());
	}


}
