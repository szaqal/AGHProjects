package graphs.editor.gui;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

/**
 * Panel gdzie definiowane sa wagi wezlow.
 * @author malczyk.pawel@gmail.com
 *
 */
public class VertexPanel extends AbsrtactWeightPanel {

	/**	Serial. */
	private static final long serialVersionUID = -7308940327488614394L;
	
	/** Tabela*/
	private JTable vertexTable;
	
	public VertexPanel( JPanel parentComponent ) {
		super(parentComponent);
		add(new JLabel("Wagi wierzchołkow"), BorderLayout.NORTH);
		vertexTable = setupTable();
		add(new JScrollPane(vertexTable), BorderLayout.CENTER);
		
		
		if (getParent().getLayout() instanceof BorderLayout) {
			getParent().add(this, BorderLayout.SOUTH);
		}	
	}
	
	/**
	 * Ustawia tabele
	 * @return ustawiona tabela
	 */
	private JTable setupTable() {
		JTable table = new JTable();
		System.out.println(getParent());
		CanvasPanel canvasPanel = ((ContainerPanel)getParent().getParent()).getCanvasPanel();
		
		table.setModel(new VertexTableModel(canvasPanel));
		return table;
	}
	
	/** Uaktualnia model tabeli */
	public void update() {
		((VertexTableModel)vertexTable.getModel()).update();		
		((AbstractTableModel)vertexTable.getModel()).fireTableDataChanged();
	}
	

}
