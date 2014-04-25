package turistcompany.gui.tables;

import java.awt.Component;
import java.awt.event.ActionListener;

import javax.swing.AbstractCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

public class ReserveButtonColumn extends AbstractCellEditor implements TableCellRenderer,TableCellEditor{

private static final long serialVersionUID = -6301976939083003067L;
	
	private JButton btnReserveRender;
	private JButton btnReserveEdit;
	
	public ReserveButtonColumn(ActionListener al) {
		btnReserveRender = new JButton("Dodaj do rezerwacji");
		btnReserveRender.setIcon(new ImageIcon(getClass().getResource("/add.png")));
		btnReserveEdit = new JButton("Dodaj do rezerwacji");
		btnReserveEdit.setIcon(new ImageIcon(getClass().getResource("/add.png")));
		btnReserveEdit.setActionCommand("add");
		btnReserveEdit.addActionListener(al);
	}
	
	
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		return btnReserveRender;
	}
	@Override
	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {
		return btnReserveEdit;
	}
	@Override
	public Object getCellEditorValue() {
		// TODO Auto-generated method stub
		return null;
	}

}
