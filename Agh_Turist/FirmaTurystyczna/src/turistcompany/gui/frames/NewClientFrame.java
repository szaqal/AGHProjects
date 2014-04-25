package turistcompany.gui.frames;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

import turistcompany.db.Dao;
import turistcompany.db.DaoImpl;
import turistcompany.gui.tables.ClientTableModel;
import turistcompany.model.Client;

/**
 * Dodawnie nowego clienta
 * @author malczyk.pawel@gmail.com
 *
 */
public class NewClientFrame extends AbstractNewItemFrame implements ActionListener {
	
	/** Serial. */
	private static final long serialVersionUID = -1965465343109043674L;
	
	/** Szerokosc ramki*/
	private static final int FRAME_WIDTH = 500;
	
	/** Wysokosc ramki */
	private static final int FRAME_HEIGHT = 300;
	
	/** Tytul ramki */
	private static final String FRAME_TITLE = "Nowy klient";
	
	/** Glowny panel*/
	private JPanel mainPanel;	
	
	/** Teksty labeli. */
	private static final String [] lbls = {"Nazwa klienta : ","Adres : ", "Nip : " ,"Kod pocztowy : "};
	
	/** Pola tekstowe*/
	private static final JTextField [] txtFilds = new JTextField[lbls.length];
	
	/** Tabela do odświeżenia */
	private JTable tableToRefresh;
	
	/**
	 * Konstruktor
	 * @param table table da odświeżenia
	 */
	public NewClientFrame(JTable table) {
		super(FRAME_WIDTH,FRAME_HEIGHT, FRAME_TITLE);
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
			jlabel.setBounds(10, 50+i+30*i, 150, 20);
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
			textFld.setBounds(150, 50+i+30*i, 250, 25);
			txtFilds[i] = textFld;
		}
	}

	/** Action listener zdarzen na tej ramce */
	@Override
	public void actionPerformed(ActionEvent e) {
		Client client = new Client();
		client.setName(txtFilds[0].getText());
		client.setAdress(txtFilds[1].getText());
		client.setNip(txtFilds[2].getText());
		client.setZipCode(txtFilds[3].getText());
		
		Dao dao = new DaoImpl();
		dao.storeClient(client);		
		List<Client> clients = dao.retrieveClients();
		((ClientTableModel)tableToRefresh.getModel()).updateData(clients);
		((ClientTableModel)tableToRefresh.getModel()).fireTableDataChanged();
		
		
		dispose();
	}
}
