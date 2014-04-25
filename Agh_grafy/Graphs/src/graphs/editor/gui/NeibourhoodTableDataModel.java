package graphs.editor.gui;

import graphs.Graphs;

import javax.swing.table.AbstractTableModel;

/**
 * Data model tabeli.
 * @author malczyk.pawel@gmail.com
 *
 */
public class NeibourhoodTableDataModel extends AbstractTableModel {
	
	/** Serial. */
	private static final long serialVersionUID = 5143968481660707636L;
	
	/** Dane macierzy wygenerowane z grafu */
	private int[][] data= Graphs.getGraph().generateMatrix();
	
	/** Konstruktor. */
	public NeibourhoodTableDataModel( ) {
		super();
	}

	/** {@inheritDoc} */
	@Override
	public int getColumnCount() {
		return data.length;
	}
	
	/** {@inheritDoc} */
	@Override
	public String getColumnName(int arg0) {
		return String.valueOf(arg0);
	}

	/** {@inheritDoc} */
	@Override
	public int getRowCount() {
		return data.length;
	}

	/** {@inheritDoc} */
	@Override
	public Object getValueAt(int row, int col) {
		return data[row][col];
	}	
	
	/** {@inheritDoc} */
	@Override
	public void setValueAt(Object value, int row, int col) {
		data[row][col]=Integer.parseInt((String)value);
	}
}
