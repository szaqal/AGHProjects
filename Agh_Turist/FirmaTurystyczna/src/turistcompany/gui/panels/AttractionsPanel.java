package turistcompany.gui.panels;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.table.AbstractTableModel;

import turistcompany.ReservationSingleton;
import turistcompany.db.DaoImpl;
import turistcompany.gui.frames.NewAttractionFrame;
import turistcompany.gui.tables.AttractionTableModel;
import turistcompany.gui.tables.DelButtonColumn;
import turistcompany.gui.tables.ReservationTableModel;
import turistcompany.gui.tables.ReserveButtonColumn;
import turistcompany.model.Attraction;

/**
 * Panel z atrakcjami
 * @author malczyk.pawel@gmail.com
 * @author gchmiel@lkn.pl
 *
 */
public class AttractionsPanel extends AbstractPanel {

	/**
	 * Serial.
	 */
	private static final long serialVersionUID = 1L;
	/** Identyfikator w kontenerze kart */	
	public static final String ATTRACTION_CARD ="attraction_card";
	
	/** Główna tabela panelu*/
	private JTable table;
	
	/** {@inheritDoc} */
	@Override
	public JPanel getBodyPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(setupToolBar(), BorderLayout.NORTH);
		panel.add(tablePanel(), BorderLayout.CENTER);
		return panel;
	}

	/** {@inheritDoc} */
	@Override
	public String getIcon() {
		return "/blackjack.png";
	}

	/** {@inheritDoc} */
	@Override
	public String getTitle() {
		return "Atrakcje";
	}
	
	
	/**
	 * Ustawia pasek narzędziowy
	 * @return przygotowany pasek narzędziowy
	 */
	private JPanel setupToolBar() {
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());
		final JTextField txtSearch = new JTextField();
		txtSearch.setColumns(20);
		panel.add(new JLabel("Wyszukaj"));
		panel.add(txtSearch);		
		
		JButton button = new JButton();
		button.setText("Szukaj");
		button.setIcon(new ImageIcon(getClass().getResource("/find.png")));
		button.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				List<Attraction> results = new DaoImpl().searchItems(txtSearch.getText(), Attraction.class);
				((AttractionTableModel)table.getModel()).updateData(results);
				((AbstractTableModel)table.getModel()).fireTableDataChanged();
			}
			
		});
		panel.add(button);
		
		return panel;
	}
	
	/**
	 * Przygotowuje panel z table
	 * @return przygotowany panel z tabel
	 */
	private JPanel tablePanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		final JTable attractionsTable = new JTable();
		table = attractionsTable;
		attractionsTable.setModel(new AttractionTableModel());
		
		
		ActionListener al = new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				
				Object o = attractionsTable.getValueAt(attractionsTable.getSelectedRow(), 0);
				
				if (e.getActionCommand().equals("delete")) {					
					new DaoImpl().deleteAttraction( (Integer)o );
					attractionsTable.setModel(new AttractionTableModel());
					((AbstractTableModel)attractionsTable.getModel()).fireTableDataChanged();	
				} else if (e.getActionCommand().equals("add")) {
					ReservationSingleton.getInstance().getCurrentReservation().addAttraction((Integer)o);
					
					JPanel reservationPanel = (JPanel)AttractionsPanel.this.getParent().getComponent(4);
					JPanel panel = (JPanel)reservationPanel.getComponent(1);
					JTabbedPane pane = (JTabbedPane)panel.getComponent(0);
					CurrentReservationPanel currentReservationPanel = (CurrentReservationPanel)pane.getComponent(0);
					((ReservationTableModel)currentReservationPanel.getReservationItemsTable().getModel()).update();
					((AbstractTableModel)currentReservationPanel.getReservationItemsTable().getModel()).fireTableDataChanged();
				}
				
				
				
			}
			
		};
		final DelButtonColumn delButtonColumn = new DelButtonColumn(al);
		final ReserveButtonColumn addButtonColumn = new ReserveButtonColumn(al);
		
		
		attractionsTable.getColumnModel().getColumn(5).setCellEditor(delButtonColumn);
		attractionsTable.getColumnModel().getColumn(5).setCellRenderer(delButtonColumn);
		
		attractionsTable.getColumnModel().getColumn(4).setCellEditor(addButtonColumn);
		attractionsTable.getColumnModel().getColumn(4).setCellRenderer(addButtonColumn);
		
		attractionsTable.getColumnModel().getColumn(4).setMinWidth(180);
		attractionsTable.getColumnModel().getColumn(5).setMinWidth(120);
		
		JScrollPane scrollPane = new JScrollPane(attractionsTable);
		panel.add(scrollPane, BorderLayout.CENTER);
		
		JToolBar toolBar = new JToolBar();
		JButton btnNew = new JButton("Nowa");
		btnNew.setIcon(new ImageIcon(getClass().getResource("/filenew.png")));
		btnNew.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				new NewAttractionFrame(table);
				
			}
			
		});
		
		toolBar.add(btnNew);
		
		panel.add(toolBar, BorderLayout.NORTH);
		
		return panel;
	}
	
}
