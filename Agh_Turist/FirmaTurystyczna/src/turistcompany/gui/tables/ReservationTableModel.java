package turistcompany.gui.tables;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import turistcompany.ReservationSingleton;
import turistcompany.db.Dao;
import turistcompany.db.DaoImpl;
import turistcompany.model.Attraction;
import turistcompany.model.Offer;
import turistcompany.model.Reservation;

/**
 * Table model dla rezerwacji.
 * @author malczyk.pawel@gmail.com
 *
 */
public class ReservationTableModel extends AbstractTableModel {

	/** Serial. */
	private static final long serialVersionUID = -6226877081001753074L;
	
	/** Nazwy kolumn. */
	private String [] columnNames = {"id","Nazwa","Opis","Typ","?"};
	
	/** Dane dla tabeli */
	private Object [][] data;
	
	/** Konstruktor */
	public ReservationTableModel() {
		Reservation reservation = ReservationSingleton.getInstance().getCurrentReservation();
		populateData(reservation);
	}
	
	/** Uaktualnia dane. */
	public void update() {
		Reservation reservation = ReservationSingleton.getInstance().getCurrentReservation();
		populateData(reservation);
	}
	
	/**
	 * Wypelnia dane
	 * @param reservation dane
	 */
	private void populateData(Reservation reservation) {		
		List<Integer> reservedAttractions = reservation.getReservedAttrations();
		List<Integer> reservedOffers = reservation.getReservedOffers();
		int totalSize = reservedAttractions.size() + reservedOffers.size();
		data = new Object[totalSize][];
		Dao dao = new DaoImpl();
		int counter = 0;
		for (Integer i : reservedAttractions) {
			data[counter] = new Object[5];
			Attraction attraction = dao.retrieveAttraction(i);
			data[counter][0] = attraction.getUniqueId();
			data[counter][1] = attraction.getName();
			data[counter][2] = attraction.getDescrption();
			data[counter][3] = "Atrakcja";
			counter++;
		}
		
		for (Integer i: reservedOffers) {
			data[counter] = new Object[5];
			Offer offer =  dao.retrieveOffer(i);
			data[counter][0] = offer.getUniqueId();
			data[counter][1] = offer.getName();
			data[counter][2] = offer.getDesription();
			data[counter][3] = "Oferta";
			counter++;
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
		return columnIndex == 4;
	}

}
