package turistcompany.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JToolBar;

import turistcompany.gui.panels.AdminPanel;
import turistcompany.gui.panels.AttractionsPanel;
import turistcompany.gui.panels.ClientsPanel;
import turistcompany.gui.panels.OfferPanel;
import turistcompany.gui.panels.ReservationsPanel;

/**
 * Glowne okno
 * @author malczyk.pawel@gmail.com
 * @author gchmiel@lkn.pl
 *
 */
public class MainFrame extends JFrame implements ActionListener {
	
	/** Serial. */	
	private static final long serialVersionUID = 1L;
	
	/** Panel zawierajacy kontener kart. */
	private BodyPanel bodyPanel;

	/** Konstruktor. */
	public MainFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 600);
		setVisible(true);
		setLayout(new BorderLayout());
		add(setupToolBar(), BorderLayout.NORTH);
		bodyPanel = new BodyPanel();
		add(bodyPanel, BorderLayout.CENTER);
	}
	
	/** 
	 * Ustawia menu przyciskow
	 * @return przygotowany toolBar {@link JToolBar} 
	 */
	private JToolBar setupToolBar() {
		JToolBar toolBar = new JToolBar();
		for (JButton btn : setupToolBarButtons() ) {
			toolBar.add(btn);
		}
		return toolBar;
	}
	
	/**
	 * Przygotowuje i zwraca liste przyciskow dla {@link JToolBar}
	 * @return lista przyciskow
	 */
	private List<JButton> setupToolBarButtons() { 
		String [] icons = {"/im-msn.png","/text-editor.png","/xfce-games.png","/applications-internet.png","/settings.png","/xfce-system-exit.png"};
		List<JButton> buttons = new ArrayList<JButton>();
		int i=0;
		for (ToolBarOperations operation : ToolBarOperations.values() ) {
			JButton button = new JButton(operation.getValue());
			button.addActionListener(this);
			button.setIcon(new ImageIcon(getClass().getResource(icons[i])));
			buttons.add(button);
			i++;
		}
		return buttons;
	}
	
	/** ActionListener obsługujący przyciski menu. */
	@Override
	public void actionPerformed(ActionEvent e) {
		if ( e.getSource() instanceof JButton ) {
			String command = ((JButton)e.getSource()).getActionCommand();
			if ( command.equals(ToolBarOperations.CLOSE.getValue() ) ) {
				System.exit(0);
			} else if (command.equals(ToolBarOperations.MANAGE_CLIENTS.getValue())){
				((CardLayout)bodyPanel.getCardContainer().getLayout()).show(bodyPanel.getCardContainer(), ClientsPanel.CLIENTS_CARD);
			} else if (command.equals(ToolBarOperations.MANAGE_SYSTEM.getValue())) {
				((CardLayout)bodyPanel.getCardContainer().getLayout()).show(bodyPanel.getCardContainer(), AdminPanel.ADMIN_CARD);
			} else if (command.equals(ToolBarOperations.MANAGE_OFFERS.getValue())) {
				((CardLayout)bodyPanel.getCardContainer().getLayout()).show(bodyPanel.getCardContainer(), OfferPanel.OFFER_CARD);
			}else if (command.equals(ToolBarOperations.MANAGE_ATTRACTIONS.getValue())) {
				((CardLayout)bodyPanel.getCardContainer().getLayout()).show(bodyPanel.getCardContainer(), AttractionsPanel.ATTRACTION_CARD);
			}else if (command.equals(ToolBarOperations.MANAGE_RESERVATIONS.getValue())) {
				((CardLayout)bodyPanel.getCardContainer().getLayout()).show(bodyPanel.getCardContainer(), ReservationsPanel.RESERVATION_CARD);
			}
		}
	}
}
