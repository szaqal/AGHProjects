package grid.client.panels;

import grid.client.Comboboxes;
import grid.client.logic.LogicImpl;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

public class ManageAccountPanel extends AbstractPanel {
	
	/**
	 * Serial. 
	 */
	private static final long serialVersionUID = 8401824352720472466L;
	
	/** Identyfikator w kontenerze kart */
	public static final String MANAGE_ACCOUNTS ="manage_accounts_card";
	
	private JComboBox accountsCombo;
	

	/** {@inheritDoc} */
	@Override
	public JPanel getBodyPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(mainPanel(), BorderLayout.CENTER);
		return panel;
	}
	
	private JPanel mainPanel() {
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(null);
		mainPanel.add(openAccountBtn());
		accountsCombo = accountsCombo();
		Comboboxes.add((AccountsCombo)accountsCombo);
		mainPanel.add(accountsCombo);
		mainPanel.add(closeAccount());
		return mainPanel;
	}

	private JButton openAccountBtn() {
		JButton result = new JButton("Otworz konto");
		result.setLocation(10, 10);
		result.setSize(100, 30);
		result.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new LogicImpl().openAccount();
				Comboboxes.refreshAll();
				
			}
		});
		return result;
	}
	
	private JComboBox accountsCombo() {
		JComboBox comboBox = new AccountsCombo();
		comboBox.setLocation(10, 50);
		return comboBox;
	}
	
	private JButton closeAccount() {
		JButton jButton = new JButton("Zamknij konto");
		jButton.setLocation(180, 50);
		jButton.setSize(120, 30);
		jButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new LogicImpl().closeAccount((String)accountsCombo.getSelectedItem());
				Comboboxes.refreshAll();
			}
		});
		return jButton;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public String getTitle() {
		return "Otwieranie / Zamykanie konta";
	}

}
