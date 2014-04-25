package turistcompany.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;

import javax.swing.JPanel;

import turistcompany.gui.panels.AdminPanel;
import turistcompany.gui.panels.AttractionsPanel;
import turistcompany.gui.panels.ClientsPanel;
import turistcompany.gui.panels.OfferPanel;
import turistcompany.gui.panels.ReservationsPanel;

/**
 * Glowny panel bedacy kontenerem kart dla {@link CardLayout}
 * @author malczyk.pawel@gmail.com
 * @author gchmiel@lkn.pl
 *
 */
public class BodyPanel extends JPanel {

	/** Serial */
	private static final long serialVersionUID = -8803619983472342701L;
	
	/** Kontener kart*/
	private JPanel cardContainer = new JPanel();

	/** Kostruktor. */
	public BodyPanel() {
		setLayout(new BorderLayout());
		setupContainer();
		add(cardContainer, BorderLayout.CENTER );
	}
	
	
	/** Ustaia odpowiednie karty */
	public void setupContainer() {
		cardContainer.setSize(300, 300);
		cardContainer.setForeground(Color.black);
		cardContainer.setLayout(new CardLayout());
		cardContainer.add(new AttractionsPanel(), AttractionsPanel.ATTRACTION_CARD);
		cardContainer.add(new AdminPanel(), AdminPanel.ADMIN_CARD);		
		cardContainer.add(new ClientsPanel(), ClientsPanel.CLIENTS_CARD);
		cardContainer.add(new OfferPanel(), OfferPanel.OFFER_CARD);
		cardContainer.add(new ReservationsPanel(), ReservationsPanel.RESERVATION_CARD);
	}


	/** Zwraca kontener kart*/
	public JPanel getCardContainer() {
		return cardContainer;
	}


	/**
	 * Ustawia kontner kart
	 * @param cardContainer kontener kart
	 */
	public void setCardContainer(JPanel cardContainer) {
		this.cardContainer = cardContainer;
	}
}
