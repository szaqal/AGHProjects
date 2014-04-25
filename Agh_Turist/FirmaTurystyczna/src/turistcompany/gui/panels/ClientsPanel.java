package turistcompany.gui.panels;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.table.AbstractTableModel;

import turistcompany.db.Dao;
import turistcompany.db.DaoImpl;
import turistcompany.gui.frames.NewClientFrame;
import turistcompany.gui.tables.ClientTableModel;
import turistcompany.gui.tables.DelButtonColumn;
import turistcompany.model.Client;

/**
 * Panel z klientami
 * @author malczyk.pawel@gmail.com
 * @author gchmiel@lkn.pl
 *
 */
public class ClientsPanel extends AbstractPanel {

	/** Serial. */
	private static final long serialVersionUID = 1L;
	/** Identyfikator w kontenerze kart */	
	public static final String CLIENTS_CARD ="clients_card";
	
	/** Głowna tabel panelu. */
	private JTable table;
	
	/** {@inheritDoc} */
	@Override
	public JPanel getBodyPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(setupToolBar(), BorderLayout.NORTH);
		panel.add(tablePanel(), BorderLayout.CENTER);
		return panel;
	}

	/** {@inheritDoc} */
	@Override
	public String getIcon() {
		return "/blobwars.png";
	}

	/** {@inheritDoc} */
	@Override
	public String getTitle() {
		return "Klienci";
	}
	
	/**
	 * Przygotowuje pasek narzędziowy.
	 * @return przygotowany pasek narzędziowy
	 */
	private JPanel setupToolBar() {
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());
		final JTextField txtSearch = new JTextField();
		txtSearch.setColumns(20);
		panel.add(new JLabel("Wyszukaj"));
		panel.add(txtSearch);
		
		JButton button = new JButton();
		button.setText("Szukaj");
		button.setIcon(new ImageIcon(getClass().getResource("/find.png")));
		button.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				List<Client> results = new DaoImpl().searchItems(txtSearch.getText(), Client.class);
				((ClientTableModel)table.getModel()).updateData(results);
				((AbstractTableModel)table.getModel()).fireTableDataChanged();
			}
			
		});
		
		
		panel.add(button);
		
		return panel;
	}
	
	/**
	 * Przygotowuje panel z tabel
	 * @return panel z tabel
	 */
	private JPanel tablePanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		
		final JTable clientTable = new JTable();
				
		ActionListener al = new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {				
				Object o = clientTable.getValueAt(clientTable.getSelectedRow(), 0);
				Dao dao = new DaoImpl();
				dao.deleteClientRow((Integer)o);
				List<Client> results = new DaoImpl().retrieveClients();
				((ClientTableModel)table.getModel()).updateData(results);
				((AbstractTableModel)table.getModel()).fireTableDataChanged();
			}
			
		};
		
		final DelButtonColumn delButtonColumn = new DelButtonColumn(al);		
		
		table = clientTable;
		clientTable.setModel(new ClientTableModel());
		clientTable.getColumnModel().getColumn(5).setCellEditor(delButtonColumn);
		clientTable.getColumnModel().getColumn(5).setCellRenderer(delButtonColumn);
		
		JScrollPane scrollPane = new JScrollPane(clientTable);
		panel.add(scrollPane, BorderLayout.CENTER);
		JToolBar toolBar = new JToolBar();
		JButton btnNew = new JButton("Nowy");
		btnNew.setIcon(new ImageIcon(getClass().getResource("/contact-new.png")));
		btnNew.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				new NewClientFrame(table);
			}
			
		});
		
		
		
		toolBar.add(btnNew);
		panel.add(toolBar, BorderLayout.NORTH);
		
		return panel;
	}
	
}
