package turistcompany;

import turistcompany.db.DbConstants;
import turistcompany.gui.MainFrame;

/**
 * Glowna klasa aplikacji
 * @author malczyk.pawel@gmail.com
 * @author gchmiel@lkn.pl
 *
 */
public class TuristCompany {
	
	
	/**
	 * Punkt wejscia aplikacji.
	 * @param args parametry wejsciowe
	 */
	public static void main ( String [] args ) {
		new MainFrame();
	}
	
	/** Statyczny blok inicjalizyjacy ustawienia dostępu do bazy danych */
	static {
		try {
			Class.forName(DbConstants.getProperties().getProperty(DbConstants.JDBC_DRIVER_CLASS_NAME));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
