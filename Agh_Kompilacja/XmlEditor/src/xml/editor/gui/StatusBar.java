package xml.editor.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Tworzy panel statusu.
 * @author malczyk.pawel@gmail.com
 *
 */
public class StatusBar extends JPanel {

	/**
	 * Serial.
	 */
	private static final long serialVersionUID = -5753622671981005109L;

	/**
	 * Labelka z polozeniem karetki.
	 */
	private JLabel locationLabel;

	/**
	 * Konstruktor.
	 *
	 * @param parent
	 *            komponenet na ktorym osadzony bedzie biezacy koponent
	 */
	public StatusBar(final JFrame parent) {
		setLayout(new FlowLayout());
		locationLabel = prepareLocationLabel();
		add(locationLabel);
		parent.add(this, BorderLayout.SOUTH);
	}

	/**
	 * Wyswietla polaczenie karetki.
	 *
	 * @return labelka z polozeniem karetki
	 */
	private JLabel prepareLocationLabel() {
		JLabel label = new JLabel();
		return label;
	}

	/**
	 * Ustawia labelke wyswietalajaca lokalizacje karetki.
	 *
	 * @param newLabel
	 *            nowa wartosc
	 */
	public void updateLocationLabel(final String newLabel) {
		locationLabel.setText(newLabel);
	}
}
