package turistcompany.gui.panels;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import turistcompany.gui.frames.ReservationDetailFrame;
import turistcompany.gui.tables.DetailButtonColumn;
import turistcompany.gui.tables.ExistingReservationsTableModel;

/**
 * Wyswietla liste rezerwacji.
 * @author malczyk.pawel@gmail.com
 * @author gchmiel@lkn.pl
 *
 */
public class ExistingReservationPanel extends JPanel {

	/** Serial. */
	private static final long serialVersionUID = 1188517926952513121L;
	
	private JTable reservationsTable;
	
	public ExistingReservationPanel() {
		setLayout(new BorderLayout());
		
		ActionListener al = new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {				
				Object o = reservationsTable.getValueAt(reservationsTable.getSelectedRow(), 0);
				new ReservationDetailFrame((Integer)o);
			}
			
		};
		
		
		final DetailButtonColumn delButtonColumn = new DetailButtonColumn(al);
		
		reservationsTable = new JTable();
		reservationsTable.setModel(new ExistingReservationsTableModel());
		
		reservationsTable.getColumnModel().getColumn(3).setCellEditor(delButtonColumn);
		reservationsTable.getColumnModel().getColumn(3).setCellRenderer(delButtonColumn);
		
		JScrollPane scrollPane = new JScrollPane(reservationsTable);
		add(scrollPane, BorderLayout.CENTER);
	}

	public JTable getReservationsTable() {
		return reservationsTable;
	}

}
