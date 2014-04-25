package turistcompany;

import java.awt.Dimension;
import java.awt.Toolkit;

/**
 * Ogolne stałe dla aplikacji
 * @author malczyk.pawel@gmail.com
 * @author gchmiel@lkn.pl
 *
 */
public interface TuristCompanyConstants {
	
	/** Rozmiar ekranu. */
	Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
	/** Uzywany w aplikacji format daty. */
	String SIMPLE_DATE_FORMAT = "yyyy-MM-dd";
}
