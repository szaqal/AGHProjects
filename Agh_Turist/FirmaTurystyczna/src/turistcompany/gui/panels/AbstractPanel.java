package turistcompany.gui.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Abstrakcyjny panel dla wszystkich "kart" z CardLayout
 * @author malczyk.pawel@gmail.com
 * @author gchmiel@lkn.pl
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
	 * Zwraca główną zawartość panel (do przesłonięcia w klasach dziedziczących)
	 * @return ustawiony główny panel
	 */
	public abstract JPanel getBodyPanel();
	
	/**
	 * Przygotowuje panel nagłówka
	 * @return przygotowany panel.
	 */
	public JPanel prepareTitlePanel() {
		JPanel panel = new JPanel();				
		panel.setLayout(new FlowLayout());
		panel.setBackground(new Color(165,237,212));
		JLabel label = new JLabel();
		label.setFont(new Font("Serif",Font.BOLD,12));
		label.setText(getTitle());
		label.setIcon(new ImageIcon(getClass().getResource(getIcon())));
		panel.add(label);
		return panel;
	}
	
	/**
	 * Zwraca tytuł panelu (do przesłonięcia w klasach dziedziczących)
	 * @return tekst nagłówka
	 */
	public abstract String getTitle();
	/**
	 * Zwraca sciezke do ikony panely (do przesłonięcia w klasach dziedziczących)
	 * @return ścieżka do ikony.
	 */
	public abstract String getIcon();
}
