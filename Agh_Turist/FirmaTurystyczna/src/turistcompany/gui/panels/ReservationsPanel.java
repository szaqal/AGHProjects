package turistcompany.gui.panels;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 * Panel Rezerwacji
 * @author malczyk.pawel@gmail.com
 *
 */
public class ReservationsPanel extends AbstractPanel{

	/**
	 * Serial.
	 */
	private static final long serialVersionUID = 1L;
	/** Identyfikator w kontenerze kart */	
	public static final String RESERVATION_CARD ="reservation_card";

	/** {@inheritDoc} */
	@Override
	public JPanel getBodyPanel() {
		return setupPanel();
	}

	/** {@inheritDoc} */
	@Override
	public String getIcon() {
		return "/gworldclock.png";
	}

	/** {@inheritDoc} */
	@Override
	public String getTitle() {
		return "Rezerwacje";
	}
	
	/**
	 * Przygotowuje zakładki
	 * @return przygotowany panel z zakładkiami
	 */
	private JPanel setupPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		JTabbedPane pane = new JTabbedPane();
		pane.addTab("Bieżąca rezerwacja", currentReservationPanel());
		pane.addTab("Istniejace rezerwacje", existingReservations());
		panel.add(pane, BorderLayout.CENTER);
				
		return panel;
	}
	
	/**
	 * Zwraca panel z biezaca rezerwacja.
	 * @return panel z biezaca rezerwacja.
	 */
	private JPanel currentReservationPanel() {
		return new CurrentReservationPanel();
	}
	
	/**
	 * Zwraca panel z istniejacymi rezerwacjami
	 * @return panel z istniejacymi rezerwacjami
	 */
	private JPanel existingReservations() {
		return new ExistingReservationPanel();
	}

}
