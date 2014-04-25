package graphs.editor.gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.util.Enumeration;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;

/**
 * Panel zawierajzacy macierz incydencji.
 * @author malczyk.pawel@gmail.com
 *
 */
public class NeibourhoodMatrixPanel extends JPanel{
	
	/** Serial. */
	private static final long serialVersionUID = 1L;
	
	/** Maksymalna szerokosc komorki tabeli*/
	private static final int MAX_CELL_WIDTH = 10;
	
	/** Table wyswietlajaca maciez sasiedztwa*/
	private JTable neibourhoodMatrixTable;

	/**
	 * Konstruktor
	 * @param parentContainer komponent parenta.
	 */
	public NeibourhoodMatrixPanel(Container parentContainer){
		setLayout(new BorderLayout());			
		setTable();
		setLayout(new BorderLayout());
		JScrollPane scrollPane = new JScrollPane(neibourhoodMatrixTable);

		add(scrollPane, BorderLayout.CENTER);
		setVisible(true);
		parentContainer.add(this, BorderLayout.CENTER);
	}
	
	
	
	/** Ustawia tabele. */
	private void setTable(){
		neibourhoodMatrixTable = new JTable();
		neibourhoodMatrixTable.setModel(new NeibourhoodTableDataModel());
		Enumeration<TableColumn> enumeration = neibourhoodMatrixTable.getColumnModel().getColumns();
		while (enumeration.hasMoreElements()) {
			enumeration.nextElement().setMaxWidth(MAX_CELL_WIDTH);
		}
		
	}

	/**
	 * Getter tabela
	 * @return tablea
	 */
	public JTable getNebourhoodMatrixTable() {
		return neibourhoodMatrixTable;
	}
	
}
