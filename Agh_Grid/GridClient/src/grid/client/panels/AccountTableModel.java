package grid.client.panels;

import grid.client.logic.LogicImpl;

import javax.swing.table.AbstractTableModel;

public class AccountTableModel extends AbstractTableModel {
	
	/** Serial. */
	private static final long serialVersionUID = -1903011105639974515L;

	/** Nazwy kolumn. */
	private String [] columnNames = {"id","Saldo"};
	
	/** Klasa kolumny*/
	private Class<?> [] columnClass = {String.class,String.class};
	
	/** Dane dla tabeli */
	private Object [][] data;

	
	/** Konstruktor */
	public AccountTableModel() {
		String data=new LogicImpl().listAccount();
		System.out.println(data);
		parseData(data);
	}
	
	public void refresh() {
		String data = new LogicImpl().listAccount();
		parseData(data);
		fireTableDataChanged();
	}
	
	private void parseData(String strData) {
		String [] items = strData.split("#");
		data = new Object[items.length][];
		for(int i=0;i<items.length;i++) {
			data[i] = items[i].split(":");
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

}
