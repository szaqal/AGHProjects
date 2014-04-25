package graphs.utils;

import graphs.GraphsConstants;
import graphs.domain.SimpleLocation;

/**
 * 
 * Klasa zawiera pomocnicze metody,
 * @author malczyk.pawel@gmail.com
 *
 */
public class Utils {
	
	/**
	 * Oblicza polozenie nowej ramki
	 * @param frameWidth szerwokosc ramki
	 * @param frameHeight wysokosc ramki.
	 * @return x i y 
	 */
	public static final SimpleLocation computeFrameLocation(int frameWidth, int frameHeight) {
		
		int x = (int)GraphsConstants.SCREEN_SIZE.getWidth()/2 - frameWidth/2;
		int y = (int)GraphsConstants.SCREEN_SIZE.getHeight()/2 - frameHeight/2;
		return new SimpleLocation(x,y);
	}
}
