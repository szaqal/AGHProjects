package graphs.editor.gui;

import graphs.Graphs;
import graphs.GraphsConstants;
import graphs.domain.api.Edge;
import graphs.domain.api.EdgeType;

import java.util.Collection;

import javax.swing.table.AbstractTableModel;

/**
 * Table model dla tabeli krawedzi i ich wag. 
 * @author malczyk.pawel@gmail.com
 *
 */
public class EdgesTableModel extends AbstractTableModel {

	/** Serial. */
	private static final long serialVersionUID = 2379777018502534306L;
	
	/** Nazwy kolumn, */
	private String [] columnNames = {"Krawedz","Waga","Przepływ","Przepustowość"};
	
	/** Klasa obiektow kolumny, */
	private Class<?> [] columnClass = {String.class, String.class, String.class, String.class};

	/** Dane. */
	private Object [][] data;
	
	/** Panel ktory bedzie przerysowany*/
	private CanvasPanel panel;
	
	/** Konstruktor. */
	public EdgesTableModel(CanvasPanel panel) {
		this.panel = panel;
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
	
	
	/** {@inheritDoc} */
	@Override
	public void setValueAt(Object value, int rowIndex, int columnIndex) {					
		data[rowIndex][columnIndex] = value;
		
		Object val =  getValueAt(rowIndex, 0);
		String strVal = (String)val;		
		String [] splitted = strVal.split(GraphsConstants.StringsConstants.SPACE);
		
		int startIndex = Integer.parseInt(splitted[0]);
		int endIndex = Integer.parseInt(splitted[2]);
		String type = splitted[1];

		
		Edge edge = null;
		if (type.equals(EdgeType.DIRECTED.getEdgeType())){
			edge = Graphs.getGraph().findEdge(startIndex, endIndex, EdgeType.DIRECTED);
		} else if (type.equals(EdgeType.NOT_DIRECTED.getEdgeType())) {
			edge = Graphs.getGraph().findEdge(startIndex, endIndex, EdgeType.NOT_DIRECTED);
		}
		
		Integer checkedValue = null;
		if ( value instanceof String ){
			checkedValue = Integer.parseInt((String)value);	
		} else if ( value instanceof Integer ) {
			checkedValue=(Integer)value;
		}
		
		switch (columnIndex) {
		case 1:
			edge.setWeight(checkedValue);
			break;
		case 2:
			edge.setFlow(checkedValue);
			break;
		case 3:	
			edge.setCapacity(checkedValue);
			break;
		default:
			break;
		}
		
		
		Graphs.getGraph().update();
		panel.repaint();
		
	}
	

	public void update() {
		Collection<Edge> edges = Graphs.getGraph().getEdges();
		data = new Object[edges.size()][];
		
		int i=0;				
		for (Edge edge : edges) {
			data[i] = new Object[columnClass.length];
			data[i][0] = edge.toString();
			data[i][1] = edge.getWeight();
			data[i][2] = edge.getFlow();
			data[i][3] = edge.getCapacity();
			i++;
		}
	}
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return columnIndex == 1 || columnIndex == 2 || columnIndex == 3 ;
	}
	
	

}
