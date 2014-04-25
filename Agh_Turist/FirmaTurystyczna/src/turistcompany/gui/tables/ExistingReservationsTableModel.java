package turistcompany.gui.tables;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import turistcompany.db.DaoImpl;
import turistcompany.model.ReservationData;


/**
 * Table model dla istniejacych rezerwacji.
 * @author malczyk.pawel@gmail.com
 * @author gchmiel@lkn.pl
 *
 */
public class ExistingReservationsTableModel extends AbstractTableModel {
	
	/** Serial.*/
	private static final long serialVersionUID = -5941764691199247233L;
	
	/** Nazwy kolumn. */
	private String [] columnNames={"id","Klient","Data rezerwacji","?"};
	
	/** Dane dla tabeli */
	private Object[][]data;
	
	/** Konstruktor */
	public ExistingReservationsTableModel() {
		List<ReservationData> reservations = new DaoImpl().retrieveReservations();
		updateData(reservations);		
	}
	
	/** Uaktualnia dane */
	public void updateData() {
		List<ReservationData> reservations = new DaoImpl().retrieveReservations();
		updateData(reservations);
	}
	
	/**
	 * Uaktualnia dane
	 * @param reservations dane
	 */
	public void updateData(List<ReservationData> reservations) {
		data = new Object[reservations.size()][];
		for (int i=0;i<reservations.size();i++) {
			data[i] = new Object[4];
			data[i][0] = reservations.get(i).getUniqueId();
			data[i][1] = reservations.get(i).getClientName();
			data[i][2] = reservations.get(i).getReservationDate();
		}
	}
	
	/** {@inheritDoc} */
	@Override
	public String getColumnName(int column) {
		return columnNames[column];
	}

	/** {@inheritDoc} */
	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	/** {@inheritDoc} */
	@Override
	public int getRowCount() {
		return data.length;
	}

	/** {@inheritDoc} */
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return data[rowIndex][columnIndex];
	}
	
	/** {@inheritDoc} */
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return columnIndex==3;
	}

	
}
