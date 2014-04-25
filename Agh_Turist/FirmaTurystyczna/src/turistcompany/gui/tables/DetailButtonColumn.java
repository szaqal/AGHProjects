package turistcompany.gui.tables;

import java.awt.Component;
import java.awt.event.ActionListener;

import javax.swing.AbstractCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

/**
 * Kolumna przyciskow ze szczegolami
 * @author malczyk.pawel@gmail.com
 * @author gchmiel@lkn.pl
 *
 */
public class DetailButtonColumn extends AbstractCellEditor implements TableCellRenderer,TableCellEditor{

	/** Serial. */
	private static final long serialVersionUID = 130803432109625101L;
	
	/** Renderer*/
	private JButton btnDetailsRender;
	
	/**Editor*/
	private JButton btnDtailsEdit;
	
	/**
	 * Konstruktor
	 * @param al actionListener gtory obsłuzy klikniecie
	 */
	public DetailButtonColumn(ActionListener al) {
		btnDetailsRender = new JButton("Szczegóły");
		btnDetailsRender.setIcon(new ImageIcon(getClass().getResource("/zoom-best-fit.png")));
		btnDtailsEdit = new JButton("Szczegóły");
		btnDtailsEdit.setActionCommand("details");
		btnDtailsEdit.setIcon(new ImageIcon(getClass().getResource("/zoom-best-fit.png")));
		btnDtailsEdit.addActionListener(al);
	}
	
	
	/** {@inheritDoc} */
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		return btnDetailsRender;
	}
	/** {@inheritDoc} */
	@Override
	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {
		return btnDtailsEdit;
	}
	/** {@inheritDoc} */
	@Override
	public Object getCellEditorValue() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
