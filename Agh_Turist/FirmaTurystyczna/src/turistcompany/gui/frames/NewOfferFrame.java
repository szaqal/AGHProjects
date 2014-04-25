package turistcompany.gui.frames;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

import turistcompany.TuristCompanyConstants;
import turistcompany.db.Dao;
import turistcompany.db.DaoImpl;
import turistcompany.gui.tables.OffersTableModel;
import turistcompany.model.Offer;

/**
 * Ramka dodawnia nowej oferty (np. wycieczki).
 * @author malczyk.pawel@gmail.com
 *
 */
public class NewOfferFrame extends AbstractNewItemFrame implements ActionListener {
	
	/** Serial. */
	private static final long serialVersionUID = 1L;
	
	/** Szerokosc ramki*/	
	private static final int FRAME_WIDTH = 600;
	
	/** Wysokosc ramki */
	private static final int FRAME_HEIGHT = 350;
	
	/** Tytul ramki */
	private static final String FRAME_TITLE = "Nowa oferta";
	
	/** Teksty labeli. */
	private static final String [] lbls = {"Nazwa Oferty : ","Opis : ", "Cena : " ,"Data rozpoczęcia (YYYY-MM-DD) : ", "Data zakończenia (YYYY-MM-DD) : "};
	
	/** Pola tekstowe*/
	private static final JTextField [] txtFilds = new JTextField[lbls.length];
	
	/** Glowny panel*/
	private JPanel mainPanel;
	
	/** Tabela do odświeżenia */
	private JTable tableToRefresh;
	
	/**
	 * Konstruktor
	 * @param table table da odświeżenia
	 */
	public NewOfferFrame(JTable table) {
		super(FRAME_WIDTH, FRAME_HEIGHT, FRAME_TITLE);
		mainPanel = setupMainPanel();
		tableToRefresh = table;
		add(mainPanel);
	}

	
	/**
	 * Ustawia główny panel.
	 * @return ustawiony główny panel
	 */
	private JPanel setupMainPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(null);
		int i=0;
		for (String label : lbls) {
			JLabel jlabel = new JLabel();
			jlabel.setText(label);
			jlabel.setBounds(10, 50+i+30*i, 230, 20);
			i++;
			panel.add(jlabel);
		}
		setupTxtFlds();
		for (JTextField txtField: txtFilds) {
			panel.add(txtField);
		}
		
		JButton button = new JButton();
		button.setBounds(FRAME_WIDTH/2 -50 , FRAME_HEIGHT - 100, 100, 30);
		button.setText("Dodaj");
		button.addActionListener(this);
		panel.add(button);
		return panel;
	}
	
	/** Ustawia pola tekstowe */
	private void setupTxtFlds() {
		for(int i=0;i<lbls.length;i++) {
			JTextField textFld = new JTextField();
			textFld.setBounds(250, 50+i+30*i, 250, 25);
			txtFilds[i] = textFld;
		}
	}
	
	/** Action listener zdarzen na tej ramce */
	@Override
	public void actionPerformed(ActionEvent e) {
		Offer offer = new Offer();
		offer.setName(txtFilds[0].getText());
		offer.setDesription(txtFilds[1].getText());
		offer.setPrice(Double.parseDouble(txtFilds[2].getText()));
		Date startDate = null;
		Date endDate = null;
		try {
			startDate = new SimpleDateFormat(TuristCompanyConstants.SIMPLE_DATE_FORMAT).parse(txtFilds[3].getText());
			endDate = new SimpleDateFormat(TuristCompanyConstants.SIMPLE_DATE_FORMAT).parse(txtFilds[4].getText());
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		offer.setStartDate(startDate);
		offer.setEndDate(endDate);
		
		Dao dao = new DaoImpl();
		dao.storeOffer(offer);
		List<Offer> offersList = dao.retrieveOffers();
		((OffersTableModel)tableToRefresh.getModel()).updateData(offersList);
		((OffersTableModel)tableToRefresh.getModel()).fireTableDataChanged();
		dispose();
	}

}
