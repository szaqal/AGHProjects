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
import turistcompany.db.Dao;
import turistcompany.db.DaoImpl;
import turistcompany.gui.frames.NewOfferFrame;
import turistcompany.gui.tables.DelButtonColumn;
import turistcompany.gui.tables.OffersTableModel;
import turistcompany.gui.tables.ReservationTableModel;
import turistcompany.gui.tables.ReserveButtonColumn;
import turistcompany.model.Offer;

/**
 * Panel z ofertami
 * @author malczyk.pawel@gmail.com
 * @author gchmiel@lkn.pl
 *
 */
public class OfferPanel extends AbstractPanel {

	/**
	 * Serial.
	 */
	private static final long serialVersionUID = 1L;
	/** Identyfikator w kontenerze kart */	
	public static final String OFFER_CARD = "offer_card";
	
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
		return "/applications-internet01.png";
	}

	/** {@inheritDoc} */
	@Override
	public String getTitle() {
		return "Oferty";
	}
	
	/**
	 * Przygotowuje pasek narzędziowy
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
				List<Offer> results = new DaoImpl().searchItems(txtSearch.getText(), Offer.class);
				((OffersTableModel)table.getModel()).updateData(results);
				((AbstractTableModel)table.getModel()).fireTableDataChanged();
			}
			
		});
		panel.add(button);
		
		
		return panel;
	}
	
	/**
	 * Przygotowuje panel tabli
	 * @return przygotowany panel tabeli.
	 */
	private JPanel tablePanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		final JTable offersTable = new JTable();
		table = offersTable;
		offersTable.setModel(new OffersTableModel());
		
		
		ActionListener al = new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				
				Object o = offersTable.getValueAt(offersTable.getSelectedRow(), 0);
				if (e.getActionCommand().equals("delete")) {
					Dao dao = new DaoImpl();
					dao.deleteOffer( (Integer)o );
					List<Offer> offerList = dao.retrieveOffers();
					((OffersTableModel)offersTable.getModel()).updateData(offerList);
					((AbstractTableModel)offersTable.getModel()).fireTableDataChanged();
					
				} else if (e.getActionCommand().equals("add")) {
					
					ReservationSingleton.getInstance().getCurrentReservation().addOffer((Integer)o);				
					JPanel reservationPanel = (JPanel)OfferPanel.this.getParent().getComponent(4);
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
		
		offersTable.getColumnModel().getColumn(7).setCellEditor(delButtonColumn);
		offersTable.getColumnModel().getColumn(7).setCellRenderer(delButtonColumn);
		offersTable.getColumnModel().getColumn(6).setCellEditor(addButtonColumn);
		offersTable.getColumnModel().getColumn(6).setCellRenderer(addButtonColumn);
		offersTable.getColumnModel().getColumn(6).setMinWidth(180);
		offersTable.getColumnModel().getColumn(7).setMinWidth(120);
		
		JScrollPane scrollPane = new JScrollPane(offersTable);
		panel.add(scrollPane, BorderLayout.CENTER);
		
		JToolBar toolBar = new JToolBar();
		JButton btnNew = new JButton("Nowa");
		btnNew.setIcon(new ImageIcon(getClass().getResource("/filenew.png")));
		btnNew.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				new NewOfferFrame(table);				
			}
			
		});
		
		toolBar.add(btnNew);		
		panel.add(toolBar, BorderLayout.NORTH);
		
		return panel;
	}

}
