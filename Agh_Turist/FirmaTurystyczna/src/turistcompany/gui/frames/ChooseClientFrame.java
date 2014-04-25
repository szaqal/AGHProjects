package turistcompany.gui.frames;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;

import turistcompany.ReservationSingleton;
import turistcompany.db.DaoImpl;
import turistcompany.gui.tables.ExistingReservationsTableModel;
import turistcompany.gui.tables.ReservationTableModel;
import turistcompany.model.Client;

/**
 * Ramka wybierania klienta dla zatwierdzenia rezerwacji.
 * @author malczyk.pawel@gmail.com
 * @author gchmiel@lkn.pl
 *
 */
public class ChooseClientFrame extends JFrame{
	
	/** Serial. */
	private static final long serialVersionUID = -1447212579429550957L;
	
	/** Główny panel. */
	private JPanel mainPanel = new JPanel();

	/**
	 * Konstruktor.
	 * @param table  tabela do odświeżenia (usuniecie wpisow z biezacej rezerwacji)
	 * @param existingReservationTable tabela do odświeżenia (dodanie pozycji do istniejacych rezerwacji)
	 */
	public ChooseClientFrame(JTable table, JTable existingReservationTable) {
		
		setSize(new Dimension(270,200));
		setVisible(true);
		
		Vector<String> data = new Vector<String>();		
		List<Client> clients = new DaoImpl().retrieveClients();
		
		final JTable tab = table;
		final JComboBox comboClient = new JComboBox(data);		
		final JTable tab2 = existingReservationTable;
		
		for(Client client : clients ) {
			String val = String.format("%s - %s", client.getUniqueId() ,client.getName());
			data.add(val);
		}
			
		JButton btnOk = new JButton("Zatwierdz");
		btnOk.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				String selectedClientId = ((String)comboClient.getSelectedItem());
				String id = selectedClientId.substring(0, selectedClientId.indexOf("-")).trim();
				new DaoImpl().storeReservation(ReservationSingleton.getInstance().getCurrentReservation(), id );				
				
				ReservationSingleton.getInstance().getCurrentReservation().clearAll();
				
				((ReservationTableModel)tab.getModel()).update();
				((ReservationTableModel)tab.getModel()).fireTableDataChanged();
				
				((ExistingReservationsTableModel)tab2.getModel()).updateData();
				((ExistingReservationsTableModel)tab2.getModel()).fireTableDataChanged();
				
				ChooseClientFrame.this.dispose();
			}
			
		});
		
		mainPanel.add(new JLabel("Klient"));		
		mainPanel.add(comboClient);
		mainPanel.add(btnOk);
		
		
		
		add(mainPanel);
	}
	
	
}
