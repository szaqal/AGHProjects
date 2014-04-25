package turistcompany.gui.panels;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import turistcompany.db.DaoImpl;
import turistcompany.db.DbConstants;

/**
 * Panel z ustawieniami administracyjnymi. 
 * @author malczyk.pawel@gmail.com
 * @author gchmiel@lkn.pl
 *
 */
public class AdminPanel extends AbstractPanel implements ActionListener {

	/** Serial.  */
	private static final long serialVersionUID = 1L;
	/** Identyfikator w kontenerze kart */
	public static final String ADMIN_CARD ="admin_card";
	
	private JButton btnChoose;
	private JButton btnRun;
	private JTextField txtFile;
	private JTextField txtConnectionUrl;
	private File sqlFile;
	
	
	/** Konstruktor */
	public AdminPanel() {
		super();
	}
	
	/** {@inheritDoc} */
	@Override
	public JPanel getBodyPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(null);
		btnChoose = getBtnChoose();
		btnRun = getBtnRun();
		txtFile = getTxtField();
		txtConnectionUrl = getTxtConnUrl();
		panel.add(getSqlLabel());
		panel.add(getJdbcUrlLAbel());
		panel.add(txtFile);
		panel.add(btnChoose);
		panel.add(btnRun);
		panel.add(txtConnectionUrl);
		return panel;
	}
	
	/**
	 * Ustawia pole tekstowe
	 * @return ustawione pole tekstowe
	 */
	private JTextField getTxtField() {
		JTextField txtLocation = new JTextField();
		txtLocation.setBounds(new Rectangle(130,10,200,20));
		return txtLocation;
	}
	
	/**
	 * Ustawia pole tekstowe
	 * @return ustawione pole tekstowe
	 */
	private JTextField getTxtConnUrl() {
		JTextField txtLocation = new JTextField();
		txtLocation.setBounds(new Rectangle(130,80,300,20));
		txtLocation.setText(DbConstants.getProperties().getProperty(DbConstants.CONNECTION_URL));
		txtLocation.setEditable(false);
		return txtLocation;
	}
	
	/**
	 * Ustawia label
	 * @return ustawiony label
	 */
	private JLabel getSqlLabel() {
		JLabel label = new JLabel();
		label.setText("Plik SQL:");
		label.setBounds(new Rectangle(10,10,80,20));
		return label;
	}
	
	/**
	 * Ustawia label
	 * @return ustawiony label
	 */
	private JLabel getJdbcUrlLAbel() {
		JLabel label = new JLabel();
		label.setText("URL połączenia:");
		label.setBounds(new Rectangle(10,80,120,20));
		return label;
	}

	/**
	 * Ustawia label
	 * @return ustawiony label
	 */
	private JButton getBtnChoose() {
		JButton button = new JButton();
		button.setBounds(new Rectangle(350,10,140,20));
		button.setText("Wybierz");
		button.setIcon(new ImageIcon(getClass().getResource("/document-open.png")));
		button.addActionListener(this);
		return button;
	}
	
	
	/**
	 * Ustawia label
	 * @return ustawiony label
	 */
	private JButton getBtnRun() {
		JButton button = new JButton();
		button.setBounds(new Rectangle(350,40,140,20));
		button.setText("Wykonaj");
		button.setIcon(new ImageIcon(getClass().getResource("/gnome-run.png")));
		button.addActionListener(this);
		return button;
	}

	/** {@inheritDoc} */
	@Override
	public String getIcon() {
		return "/redhat-system_settings.png";
	}

	/** {@inheritDoc} */
	@Override
	public String getTitle() {
		return "Administracja";
	}

	/** ActionListener dla panelu. */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnChoose) {
			JFileChooser fileChooser = new JFileChooser();
			int val = fileChooser.showOpenDialog(this);
		    fileChooser.setDialogTitle("Choose a file");
		    if (val == JFileChooser.APPROVE_OPTION ){
		    	sqlFile = fileChooser.getSelectedFile();
		    	txtFile.setText(sqlFile.getAbsolutePath());
		    	txtFile.setEditable(false);
		    }	
		} else if (e.getSource() == btnRun ){
			run(txtFile.getText());
		}
		
	    
	}
		
	/**
	 * Wczytuje poli i przygotowuje z niego zapytanie,
	 * @param path sciezka do pliku SQL
	 */
	private void run(String path) {
		File file = new File(path);
		try {			
			FileReader fr = new FileReader(file);
			BufferedReader reader = new BufferedReader(fr);
			
			StringBuilder strBuilder = new StringBuilder();
			String line = null;
			while ( (line=reader.readLine()) != null ) {
				strBuilder.append(line);
			}
			
			new DaoImpl().executeScript(strBuilder.toString());
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
