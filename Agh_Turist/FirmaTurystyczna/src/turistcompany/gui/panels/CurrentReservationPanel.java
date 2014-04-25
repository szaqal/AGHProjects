package turistcompany.gui.panels;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import turistcompany.ReservationSingleton;
import turistcompany.gui.frames.ChooseClientFrame;
import turistcompany.gui.tables.DelButtonColumn;
import turistcompany.gui.tables.ReservationTableModel;

/**
 * Zaweriwa informacje o bieżace rezerawacji
 * @author malczyk.pawel@gmail.com
 * @author gchmiel@lkn.pl
 *
 */
public class CurrentReservationPanel extends JPanel{
	
	/** Serial. */
	private static final long serialVersionUID = 6395965119872140344L;

	/** Table zarezerwowanych elementow */
	private JTable reservationItemsTable;
	
	/** Konstruktor */
	public CurrentReservationPanel() {
		setLayout(new BorderLayout());
		
		reservationItemsTable = new JTable();
		reservationItemsTable.setModel(new ReservationTableModel());
		JScrollPane scroll = new JScrollPane(reservationItemsTable);
		
		
		ActionListener al = new ActionListener(){
			
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = reservationItemsTable.getSelectedRow();
				if (selectedRow != -1) {
					Integer id = (Integer)reservationItemsTable.getValueAt(selectedRow, 0);
					String type = (String)reservationItemsTable.getValueAt(selectedRow, 3);
					
					if(e.getActionCommand().equals("delete")){
						if (type.equals("Oferta")) {
							List<Integer> offers = ReservationSingleton.getInstance().getCurrentReservation().getReservedOffers();
							for(int i=0;i<offers.size();i++) {
								int currId = offers.get(i);
								if (currId == id ) {
									ReservationSingleton.getInstance().getCurrentReservation().getReservedOffers().remove(i);
									update();
									break;
								}
									
							}
						} else if (type.equals("Atrakcja")){
							List<Integer> attrs = ReservationSingleton.getInstance().getCurrentReservation().getReservedAttrations();
							for(int i=0;i<attrs.size();i++) {
								int currId = attrs.get(i);
								if (currId == id ) {								
									ReservationSingleton.getInstance().getCurrentReservation().getReservedAttrations().remove(i);
									update();
									break;
								}
																
							}
							
						}
						
					}					
				}

			}
			
		};
		
		final DelButtonColumn delButtonColumn = new DelButtonColumn(al);
		reservationItemsTable.getColumnModel().getColumn(4).setCellEditor(delButtonColumn);
		reservationItemsTable.getColumnModel().getColumn(4).setCellRenderer(delButtonColumn);
		
		
		add(scroll, BorderLayout.CENTER);		
		JButton submit = new JButton("Zatwierdz");
		submit.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {				
				ExistingReservationPanel panel = (ExistingReservationPanel)getParent().getComponent(1);
				new ChooseClientFrame(reservationItemsTable, panel.getReservationsTable());
			}
			
		});
		add(submit, BorderLayout.SOUTH);
	}
	
	private void update() {
		((ReservationTableModel)reservationItemsTable.getModel()).update();
		((ReservationTableModel)reservationItemsTable.getModel()).fireTableDataChanged();
	}
	

	public JTable getReservationItemsTable() {
		return reservationItemsTable;
	}
		
	
	
}
