package grid.client;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * Glowna klada clienta webserwisu
 * @author malczyk
 *
 */
public class GridClient {
	
	/** Metoda main */ 
	public static void main(String [] args){
	   try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

		
		new MainFrame();
	}

}
