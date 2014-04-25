package graphs.editor.gui;

import graphs.Graphs;
import graphs.domain.api.Vertex;
import graphs.editor.domain.SwingVertex;

import java.util.Collection;

import javax.swing.table.AbstractTableModel;


/**
 * Table model dla wag wierzcholkow
 * @author malczyk.pawel@gmail.com
 *
 */
public class VertexTableModel extends AbstractTableModel{

	/** Serial. */
	private static final long serialVersionUID = 4227327736441679381L;
	
	/** Nazwy kolumn, */
	private String [] columnNames = {"Wierzcholek","Waga"};
	
	/** Klasa obiektow kolumny, */
	private Class<?> [] columnClass = {String.class, String.class};

	/** Dane. */
	private Object [][] data;
	
	/** Konstruktor. */
	public VertexTableModel(CanvasPanel panel) {
		update();		
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
	public Object getValueAt(int arg0, int arg1) {
		return data[arg0][arg1];
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
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return columnIndex == 1;
	}
	
	public void update() {
		Collection<SwingVertex> vertexes = Graphs.getGraph().getVertexes();
		data = new Object[vertexes.size()][];
		
		int i=0;				
		for (Vertex vertex : vertexes) {
			data[i] = new Object[2];
			data[i][0] = vertex.toString();
			data[i][1] = vertex.getWeight();
			i++;
		}
	}
	
	@Override
	public void setValueAt(Object value, int rowIndex, int columnIndex) {
		data[rowIndex][columnIndex] = value;
		int vertexIndex = Integer.parseInt((String)getValueAt(rowIndex, 0));
		
		Vertex vertex = Graphs.getGraph().getVertexByIndex(vertexIndex);
		
		if ( value instanceof String ){
			vertex.setWeight(Integer.parseInt((String)value));	
		} else if ( value instanceof Integer ) {
			vertex.setWeight((Integer)value);
		}
		
		Graphs.getGraph().update();		
	}
	
	
	
}
