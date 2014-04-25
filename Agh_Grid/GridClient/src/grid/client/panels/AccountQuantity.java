package grid.client.panels;

import grid.client.Comboboxes;
import grid.client.logic.LogicImpl;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Stan konta.
 * @author malczyk
 *
 */
public class AccountQuantity extends AbstractPanel{

	/**
	 * Serial.
	 */
	private static final long serialVersionUID = 1L;
	
	/** Identyfikator w kontenerze kart */
	public static final String ACCOUNT_QTY ="account_qty";
	
	private JComboBox comboBox;
	
	private JLabel label;

	/** {@inheritDoc} */
	@Override
	public JPanel getBodyPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(mainPanel(), BorderLayout.CENTER);
		return panel;
	}
	
	private JPanel mainPanel() {
		JPanel result = new JPanel();
		result.setLayout(null);
		
		
		comboBox = new AccountsCombo();
		Comboboxes.add((AccountsCombo)comboBox);
		comboBox.setLocation(10, 10);
		result.add(comboBox);
		
		label = new JLabel();
		label.setLocation(180, 10);
		label.setSize(100, 30);
		result.add(label);
		
		
		JButton jButton = new JButton("Pobierz");
		jButton.setLocation(350,10);
		jButton.setSize(100,30);
		jButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String saldo = new LogicImpl().getSaldo((String)comboBox.getSelectedItem());
				label.setText(saldo);
				
			}
		});
		result.add(jButton);
		
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public String getTitle() {
		return "Stan konta";
	}

}
