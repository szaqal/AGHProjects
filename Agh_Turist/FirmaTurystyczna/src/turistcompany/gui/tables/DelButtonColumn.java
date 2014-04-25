package turistcompany.gui.tables;

import java.awt.Component;
import java.awt.event.ActionListener;

import javax.swing.AbstractCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

public class DelButtonColumn extends AbstractCellEditor implements TableCellRenderer,TableCellEditor{

	/** Serial. */
	private static final long serialVersionUID = -6301976939083003067L;
	
	/** Renderer*/
	private JButton btnDeleteRender;
	
	/**Editor*/
	private JButton btnDeleteEdit;
	
	/**
	 * Konstruktor
	 * @param al actionListener gtory obsłuzy klikniecie
	 */
	public DelButtonColumn(ActionListener al) {
		btnDeleteRender = new JButton("Usuń");
		btnDeleteRender.setIcon(new ImageIcon(getClass().getResource("/cancel.png")));
		btnDeleteEdit = new JButton("Usuń");
		btnDeleteEdit.setIcon(new ImageIcon(getClass().getResource("/cancel.png")));
		btnDeleteEdit.setActionCommand("delete");
		btnDeleteEdit.addActionListener(al);
	}
	
	
	/** {@inheritDoc} */
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		return btnDeleteRender;
	}
	/** {@inheritDoc} */
	@Override
	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {
		return btnDeleteEdit;
	}
	/** {@inheritDoc} */
	@Override
	public Object getCellEditorValue() {
		// TODO Auto-generated method stub
		return null;
	}

}
