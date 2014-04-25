package turistcompany.gui.tables;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import turistcompany.db.DaoImpl;
import turistcompany.model.Attraction;

/**
 * Model danych dla tabeli aktrakcji
 * @author malczyk.pawel@gmail.com
 *
 */
public class AttractionTableModel extends AbstractTableModel{
	
	/** Serial. */
	private static final long serialVersionUID = 1L;
	
	/** Nazwy kolumn. */
	private String [] columnNames = {"id","Nazwa Atrakcji","Opis", "Cena","?","?"};
	
	/** Dane dla tabeli */
	private Object [][] data;
	
	/** Konstruktor */
	public AttractionTableModel() {
		List<Attraction> attractionsList = new DaoImpl().retrieveAttractions();
		updateData(attractionsList);
	}
	
	/**
	 * Uaktualnia dane.
	 * @param attractionsList dane
	 */
	public void updateData(List<Attraction> attractionsList) {
		data = new Object[attractionsList.size()][];
		for (int i=0;i<attractionsList.size();i++) {
			data[i]=new Object[6];
			data[i][0] = attractionsList.get(i).getUniqueId();
			data[i][1] = attractionsList.get(i).getName();
			data[i][2] = attractionsList.get(i).getDescrption();
			data[i][3] = attractionsList.get(i).getPrice();
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
	public void setValueAt(Object value, int rowIndex, int columnIndex) {
		data[rowIndex][columnIndex] = value;
		
		Attraction attraction = new Attraction();
		attraction.setUniqueId((Integer)data[rowIndex][0]);
		attraction.setName((String)data[rowIndex][1]);
		attraction.setDescrption((String)data[rowIndex][2]);
		
		Object price = data[rowIndex][3]; //normalnie jest to double ale jak sie wyedytuje staje sie Stringiem
		
		if ( price instanceof Double) {
			attraction.setPrice((Double)price);	
		} else {
			attraction.setPrice(Double.parseDouble((String)price));
		}
		
		
		new DaoImpl().updateAttractions(attraction);
	}
	
	/** {@inheritDoc} */
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return columnIndex!=0;
	}

}
