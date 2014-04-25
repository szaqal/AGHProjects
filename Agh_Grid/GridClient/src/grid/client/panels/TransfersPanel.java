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
 * Panel przelewow.
 * @author malczyk
 *
 */
public class TransfersPanel extends AbstractPanel {

	/**
	 * Serial.
	 */
	private static final long serialVersionUID = 5067683704891170012L;
	
	/** Identyfikator w kontenerze kart */
	public static final String TRANSFERS_PANEL ="transfers_card";
	
	private JTextField textField;
	
	private JComboBox comboFrom;
	
	private JComboBox comboTo;

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
		
		comboFrom = new AccountsCombo();
		comboTo = new AccountsCombo();
		
		Comboboxes.add((AccountsCombo)comboFrom);
		Comboboxes.add((AccountsCombo)comboTo);
		
		comboFrom.setLocation(10, 10);
		comboTo.setLocation(180, 10);
		panel.add(comboFrom);
		panel.add(comboTo);
		
		
		textField = new JTextField();
		textField.setLocation(350, 10);
		textField.setSize(100, 30);
		panel.add(textField);
		
		
		JButton button = new JButton("Przelej");
		button.setLocation(470,10);
		button.setSize(100,30);
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String param = (String)comboFrom.getSelectedItem() + "#" + (String)comboTo.getSelectedItem() + "#"+ textField.getText();
				new LogicImpl().trasfer(param);
				
			}
		});
		panel.add(button);
		
		return panel;
	}

	/** {@inheritDoc}*/
	@Override
	public String getTitle() {
		return "Przelewy";
	}

}
