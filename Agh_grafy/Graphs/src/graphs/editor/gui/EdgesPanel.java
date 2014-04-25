package graphs.editor.gui;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

/**
 * Panel z tablea wag krawedzi
 * @author malczyk.pawel@gmail.com
 *
 */
public class EdgesPanel extends AbsrtactWeightPanel {

	/** Serial. */
	private static final long serialVersionUID = 8572959398548211797L;
	
	private JTable edgesTable;
	
	/**
	 * Konstruktor
	 * @param parentComponent komponent na ktorym osadzony bedzie panel
	 */
	public EdgesPanel(JPanel parentComponent) {
		super(parentComponent);
						
		add(new JLabel("Wagi krawedzi"), BorderLayout.NORTH);
		edgesTable = setupTable();		
		add(new JScrollPane(edgesTable), BorderLayout.CENTER);
			
		if (getParent().getLayout() instanceof BorderLayout) {
			getParent().add(this, BorderLayout.NORTH);
		}	
	}
	
	/**
	 * Ustawia tabele
	 * @return ustawiona tabela
	 */
	private JTable setupTable() {
		JTable table = new JTable();
		CanvasPanel canvasPanel = ((ContainerPanel)getParent().getParent()).getCanvasPanel();
		table.setModel(new EdgesTableModel(canvasPanel));
		return table;
	}
	
	/** Uaktualnia model tabeli */
	public void update() {
		((EdgesTableModel)edgesTable.getModel()).update();		
		((AbstractTableModel)edgesTable.getModel()).fireTableDataChanged();
	}
						
}
