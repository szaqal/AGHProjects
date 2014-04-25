package turistcompany.gui.tables;

import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.table.AbstractTableModel;

import turistcompany.db.DaoImpl;
import turistcompany.model.Client;

/**
 * Table model listy klientow
 * @author malczyk.pawel@gmail.com
 * @author gchmiel@lkn.pl
 *
 */
public class ClientTableModel extends AbstractTableModel {

	/** Serial. */
	private static final long serialVersionUID = -1903011105639974515L;

	/** Nazwy kolumn. */
	private String [] columnNames = {"id","Nazwa klienta","Adres", "Nip" ,"Kod pocztowy","?"};
	
	/** Klasa kolumny*/
	private Class<?> [] columnClass = {String.class,String.class,String.class,String.class,String.class,JCheckBox.class};
	
	/** Dane dla tabeli */
	private Object [][] data;

	
	/** Konstruktor */
	public ClientTableModel() {
		List<Client> clientsList = new DaoImpl().retrieveClients();
		updateData(clientsList);
		
	}
	
	/**
	 * Uaktualnia dane
	 * @param clientsList dane
	 */
	public void updateData(List<Client> clientsList) {
		data = new Object[clientsList.size()][];
		for (int i=0;i<clientsList.size();i++) {
			data[i]=new Object[6];
			data[i][0] = clientsList.get(i).getUniqueId();
			data[i][1] = clientsList.get(i).getName();
			data[i][2] = clientsList.get(i).getAdress();
			data[i][3] = clientsList.get(i).getNip();
			data[i][4] = clientsList.get(i).getZipCode();
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
	public Class<?> getColumnClass(int columnIndex) {
		return columnClass[columnIndex];
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
		
		Client client = new Client();
		client.setUniqueId((Integer)data[rowIndex][0]);
		client.setName((String)data[rowIndex][1]);
		client.setAdress((String)data[rowIndex][2]);
		client.setNip((String)data[rowIndex][3]);
		client.setZipCode((String)data[rowIndex][4]);
		new DaoImpl().updateClient(client);
	}

}
