package grid.client;

import grid.client.panels.AccountQuantity;
import grid.client.panels.ListAccountPanel;
import grid.client.panels.ManageAccountPanel;
import grid.client.panels.TransfersPanel;
import grid.client.panels.WithdrawDepositPanel;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;

import javax.swing.JPanel;

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
		cardContainer.add(new ListAccountPanel(), ListAccountPanel.LIST_ACCOUNTS);
		cardContainer.add(new ManageAccountPanel(), ManageAccountPanel.MANAGE_ACCOUNTS);		
		cardContainer.add(new TransfersPanel(), TransfersPanel.TRANSFERS_PANEL);
		cardContainer.add(new WithdrawDepositPanel(), WithdrawDepositPanel.DEPOSIT_WITHDRAW);
		cardContainer.add(new AccountQuantity(), AccountQuantity.ACCOUNT_QTY);
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

