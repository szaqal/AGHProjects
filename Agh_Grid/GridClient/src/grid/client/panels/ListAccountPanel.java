package grid.client.panels;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 * Panel listy kont
 * @author malczyk
 *
 */
public class ListAccountPanel extends AbstractPanel {
	/** Serial.  */
	private static final long serialVersionUID = 1L;
	/** Identyfikator w kontenerze kart */
	public static final String LIST_ACCOUNTS ="list_accounts_card";
	
	/** Wlasciwa tablela*/
	private JTable accountTable;
	
	/** {@inheritDoc} */
	@Override
	public JPanel getBodyPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(refreshButton(), BorderLayout.NORTH);
		panel.add(tablePanel(), BorderLayout.CENTER);
		return panel;
	}
	
	private JButton refreshButton() {
		JButton refreshBtn = new JButton("Odswierz");
		refreshBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				((AccountTableModel)accountTable.getModel()).refresh();
			}
		});
		return refreshBtn;
	}

	/** {@inheritDoc} */
	@Override
	public String getTitle() {
		return "Lista kont";
	}
	
	
	/**
	 * Przygotowuje panel z tabel
	 * @return panel z tabel
	 */
	private JPanel tablePanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		accountTable = new JTable();
		accountTable.setModel(new AccountTableModel());
		JScrollPane scrollPane = new JScrollPane(accountTable);
		panel.add(scrollPane, BorderLayout.CENTER);

		return panel;
	}



}
