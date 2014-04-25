package grid.client.panels;

import grid.client.Comboboxes;
import grid.client.logic.LogicImpl;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Panel wplat i wyplat
 * @author malczyk
 *
 */
public class WithdrawDepositPanel extends AbstractPanel {

	/**
	 * Serial.
	 */
	private static final long serialVersionUID = -9018316693649714819L;
	
	/** Identyfikator w kontenerze kart */
	public static final String DEPOSIT_WITHDRAW ="deposit_withdraw_card";
	
	private JComboBox accountsCombo;
	
	private JTextField amountTxt;

	/** {@inheritDoc}*/
	@Override
	public JPanel getBodyPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(mainPanel(), BorderLayout.CENTER);
		return panel;
	}
	
	private JPanel mainPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(null);
		accountsCombo = new AccountsCombo();
		Comboboxes.add((AccountsCombo)accountsCombo);
		accountsCombo.setLocation(10, 10);
		panel.add(accountsCombo);

		
		amountTxt = new JTextField();
		amountTxt.setLocation(180, 10);
		amountTxt.setSize(150, 30);
		panel.add(amountTxt);
		
		JButton jButton = new JButton("Wplac");
		jButton.setLocation(350,10);
		jButton.setSize(100,30);
		jButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String accountId = (String)accountsCombo.getSelectedItem();
				new LogicImpl().deposit(accountId, amountTxt.getText());
				
			}
		});
		panel.add(jButton);
		
		jButton = new JButton("Wyplac");
		jButton.setLocation(350,40);
		jButton.setSize(100,30);
		jButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String accountId = (String)accountsCombo.getSelectedItem();
				new LogicImpl().widthdraw(accountId, amountTxt.getText());
			}
		});
		panel.add(jButton);
		
		return panel;
	}
	
	

	/** {@inheritDoc}*/
	@Override
	public String getTitle() {
		return "Wplaty / Wyplaty";
	}

}
