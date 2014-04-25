package xml.editor.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JToolBar;

import xml.editor.gui.commands.EditorCommand;
import xml.editor.gui.commands.FormatCommand;
import xml.editor.gui.commands.LoadXmlFileCommand;

/**
 * Tworzy toolbar aplikacji.
 * @author malczyk.pawel@gmail.com
 *
 */
public class ToolBar extends JToolBar implements ActionListener {

	/**
	 * Serial.
	 */
	private static final long serialVersionUID = -379223902760027937L;

	/**
	 * Stala.
	 */
	private static final String OPEN_XML = "openxml";

//	/**
//	 * Stala.
//	 */
//	private static final String OPEN_DTD = "opendtd";
//	
//	/**
//	 * Sta³a.
//	 */
//	private static final String VALIDATE = "validate";
	
	/**
	 * Sta³a.
	 */
	private static final String FORMAT = "format";

	/**
	 * Przycisk otwierania pliku XML.
	 */
	private JButton openXmlFileBtn;

//	/**
//	 * Przycisk otwierania pliku DTD.
//	 */
//	private JButton openDtdFileBtn;
//	
//	/**
//	 * Przycisk walidacji.
//	 */
//	private JButton validateBtn;
//	
//	/**
//	 * Przycisk formatownia.
//	 */
//	private JButton formatBtn;

	/**
	 * Mapa przchowujeca komendy..
	 */
	private final Map<String, EditorCommand> commands;

	/**
	 * Konstruktor.
	 *
	 * @param parent
	 *            komponent na ktorym osadzony bedzie ten komponent
	 */
	public ToolBar(final JFrame parent) {
		add(setupXmlButton());
		//add(setupDtdButton());
		//add(setupValidateButton());
		//add(setupFormatButton());
		parent.add(this, BorderLayout.NORTH);
		commands = new HashMap<String, EditorCommand>();
		commands.put(OPEN_XML, new LoadXmlFileCommand(parent));
		//commands.put(OPEN_DTD, new LoadDtdFileCommand(parent));
		//commands.put(VALIDATE, new ValidateCommand());
		commands.put(FORMAT, new FormatCommand());
	}

	/**
	 * Ustawia przcisk menu.
	 *
	 * @return przygotowany przycisk
	 */
	private JButton setupXmlButton() {
		openXmlFileBtn = new JButton();
		openXmlFileBtn.setText("Otworz XML");
		openXmlFileBtn.setActionCommand(OPEN_XML);
		//openXmlFileBtn.setIcon(new ImageIcon(getClass().getResource("/text-xml.png")));
		openXmlFileBtn.addActionListener(this);
		return openXmlFileBtn;
	}

	/**
	 * Ustawia przycisk menu.
	 *
	 * @return przygotowany przycisk
	 */
//	private JButton setupDtdButton() {
//		openDtdFileBtn = new JButton();
//		openDtdFileBtn.setText("Otworz DTD");
//		openDtdFileBtn.setActionCommand(OPEN_DTD);
//		//openDtdFileBtn.setIcon(new ImageIcon(getClass().getResource("/text-x-dtd.png")));
//		openDtdFileBtn.addActionListener(this);
//		return openDtdFileBtn;
//	}
	
	/**
	 * Ustawia przycisk menu.
	 * @return przcisk menu
	 */
//	private JButton setupValidateButton() {
//		validateBtn = new JButton();
//		validateBtn.setText("Waliduj dokument");
//		validateBtn.setActionCommand(VALIDATE);
//		// validateBtn.setIcon(new
//		// ImageIcon(getClass().getResource("/apply.png")));
//		validateBtn.addActionListener(this);
//		return validateBtn;
//	}
	
//	/**
//	 * Ustawia przycisk formatowania.
//	 * @return przycisk formatowania
//	 */
//	private JButton setupFormatButton() {
//		formatBtn = new JButton();
//		formatBtn.setText("Formatuj");
//		formatBtn.setActionCommand(FORMAT);
//		formatBtn.addActionListener(this);
//		return formatBtn;
//	}

	/** {@inheritDoc} */
	@Override
	public void actionPerformed(final ActionEvent e) {
		commands.get(e.getActionCommand()).perform();

	}


}
