package turistcompany.gui.tables;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import turistcompany.TuristCompanyConstants;
import turistcompany.db.DaoImpl;
import turistcompany.model.Offer;

/**
 * Model danych dla tabeli ofert
 * @author malczyk.pawel@gmail.com
 *
 */
public class OffersTableModel extends AbstractTableModel {
	
	/** Serial. */
	private static final long serialVersionUID = -6238199386717097154L;

	/** Nazwy kolumn. */
	private String [] columnNames = {"id","Nazwa","Opis", "Cena" ,"Data poczatkowa", "Data koncowa", "?","?"};
	
	/** Dane dla tabeli */
	private Object [][] data;

	/** Konstruktor */
	public OffersTableModel() {
		List<Offer> offersList = new DaoImpl().retrieveOffers();
		updateData(offersList);
	}
	
	/**
	 * Uaktualnia dane.
	 * @param offersList dane
	 */
	public void updateData(List<Offer> offersList) {
		data = new Object[offersList.size()][];
		for (int i=0;i<offersList.size();i++) {
			data[i]=new Object[8];
			data[i][0] = offersList.get(i).getUniqueId();
			data[i][1] = offersList.get(i).getName();
			data[i][2] = offersList.get(i).getDesription();
			data[i][3] = offersList.get(i).getPrice();
			data[i][4] = offersList.get(i).getStartDate();
			data[i][5] = offersList.get(i).getEndDate();
		}
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
	public String getColumnName(int column) {
		return columnNames[column];
	}
	
	/** {@inheritDoc} */
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return columnIndex!=0;
	}
	
	/** {@inheritDoc} */
	@Override
	public void setValueAt(Object value, int rowIndex, int columnIndex) {
		data[rowIndex][columnIndex] = value;
		
		Offer offer = new Offer();
		offer.setUniqueId((Integer)data[rowIndex][0]);
		offer.setName((String)data[rowIndex][1]);
		offer.setDesription((String)data[rowIndex][2]);
		
		Object price = data[rowIndex][3]; //normalnie jest to double ale jak sie wyedytuje staje sie Stringiem
		
		if ( price instanceof Double) {
			offer.setPrice((Double)price);	
		} else {
			offer.setPrice(Double.parseDouble((String)price));
		}
		
		Object startDate = data[rowIndex][4];
		if (startDate instanceof Date) {
			offer.setStartDate((Date)startDate);	
		} else {
			try {
				offer.setStartDate(new SimpleDateFormat(TuristCompanyConstants.SIMPLE_DATE_FORMAT).parse((String)startDate));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		
		Object endDate = data[rowIndex][5];
		if (endDate instanceof Date) {
			offer.setEndDate((Date)endDate);
		} else {
			try {
				offer.setEndDate(new SimpleDateFormat(TuristCompanyConstants.SIMPLE_DATE_FORMAT).parse((String)endDate));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		
		
		new DaoImpl().updateOffer(offer);
	}
	
}
