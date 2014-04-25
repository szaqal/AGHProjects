package grid.client.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Abstrakcyjny panel dla wszystkich "kart" z CardLayout
 * @author malczyk.pawel@gmail.com
 *
 */
public abstract class AbstractPanel extends JPanel {
	
	
	/** Serial. */
	private static final long serialVersionUID = -8678375409126069989L;

	/**
	 * Konstruktor.
	 * Ustawia wspolne elementy  
	 */
	public AbstractPanel() {
		setLayout(new BorderLayout());
		add( prepareTitlePanel(), BorderLayout.NORTH );
		add( getBodyPanel(), BorderLayout.CENTER );
	}
	

	/**
	 * Zwraca gĹ�ĂłwnÄ� zawartoĹ�Ä� panel (do przesĹ�oniÄ�cia w klasach dziedziczÄ�cych)
	 * @return ustawiony gĹ�Ăłwny panel
	 */
	public abstract JPanel getBodyPanel();
	
	/**
	 * Przygotowuje panel nagĹ�Ăłwka
	 * @return przygotowany panel.
	 */
	public JPanel prepareTitlePanel() {
		JPanel panel = new JPanel();				
		panel.setLayout(new FlowLayout());
		panel.setBackground(new Color(165,237,212));
		JLabel label = new JLabel();
		label.setFont(new Font("Serif",Font.BOLD,12));
		label.setText(getTitle());
		panel.add(label);
		return panel;
	}
	
	/**
	 * Zwraca tytuĹ� panelu (do przesĹ�oniÄ�cia w klasach dziedziczÄ�cych)
	 * @return tekst nagĹ�Ăłwka
	 */
	public abstract String getTitle();

}
