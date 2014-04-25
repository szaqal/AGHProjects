package graphs;

import java.awt.Dimension;
import java.awt.Toolkit;

/**
 * Stałe współdzielone przez aplikacje
 * @author malczyk.pawel@gmail.com
 *
 */
public interface GraphsConstants {
	Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
	
	
	public interface StringsConstants {
		String EDGE_REPRESENTATION = "%s %s %s";
		String SPACE = " ";
	}
	
}
