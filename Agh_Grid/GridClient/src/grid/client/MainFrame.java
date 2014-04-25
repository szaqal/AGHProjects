package grid.client;
import grid.client.panels.AccountQuantity;
import grid.client.panels.ListAccountPanel;
import grid.client.panels.ManageAccountPanel;
import grid.client.panels.TransfersPanel;
import grid.client.panels.WithdrawDepositPanel;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JToolBar;

/**
 * Glowna ramka.
 * @author malczyk.pawel@gmail.com
 *
 */
public class MainFrame extends JFrame implements ActionListener {
	
	/**
	 * Serial. 
	 */
	private static final long serialVersionUID = -2744786399622520979L;
	
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
		
		List<JButton> buttons = new ArrayList<JButton>();
		int i=0;
		for (ToolBarOperations operation : ToolBarOperations.values() ) {
			JButton button = new JButton(operation.getValue());
			button.addActionListener(this);
			buttons.add(button);
			i++;
		}
		return buttons;
	}

	/** ActionListener obsĹ�ugujÄ�cy przyciski menu. */
	@Override
	public void actionPerformed(ActionEvent e) {
		if ( e.getSource() instanceof JButton ) {
			String command = ((JButton)e.getSource()).getActionCommand();
			if ( command.equals(ToolBarOperations.CLOSE.getValue() ) ) {
				System.exit(0);
			} else if (command.equals(ToolBarOperations.LIST_ACCOUTS.getValue())){
				((CardLayout)bodyPanel.getCardContainer().getLayout()).show(bodyPanel.getCardContainer(), ListAccountPanel.LIST_ACCOUNTS);
			} else if (command.equals(ToolBarOperations.NEW_CLOSE_ACCOUNT.getValue())){
				((CardLayout)bodyPanel.getCardContainer().getLayout()).show(bodyPanel.getCardContainer(), ManageAccountPanel.MANAGE_ACCOUNTS);
			} else if (command.equals(ToolBarOperations.TRANSFERS.getValue())){
				((CardLayout)bodyPanel.getCardContainer().getLayout()).show(bodyPanel.getCardContainer(), TransfersPanel.TRANSFERS_PANEL);
			} else if (command.equals(ToolBarOperations.DEPOSIT_WITHDRAW.getValue())){
				((CardLayout)bodyPanel.getCardContainer().getLayout()).show(bodyPanel.getCardContainer(), WithdrawDepositPanel.DEPOSIT_WITHDRAW);
			} else if (command.equals(ToolBarOperations.CHECK.getValue())){
				((CardLayout)bodyPanel.getCardContainer().getLayout()).show(bodyPanel.getCardContainer(), AccountQuantity.ACCOUNT_QTY);
			} 
		}
	}




}
