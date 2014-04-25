package graphs.domain;

import graphs.domain.api.Location;

/**
 * Polozenie wezla;
 * @author malczyk.pawel@gmail.com
 *
 */
public class SimpleLocation implements Location{
	
	/** polozenie x*/
	private int locationX;
		
	/** polozenie y*/
	private int locationY;
	
	/** Domyslny konstruktor */
	public SimpleLocation() { };
	
	/**
	 * Konstruktor parametryzowany
	 * @param x polozenie na plotnie
	 * @param y polozenie na plotnie
	 */
	public SimpleLocation(int x, int y) {
		locationX = x;
		locationY = y;
	}
	
	/**
	 * Getter locationY
	 * @return locationY
	 */
	public int getLocationY() {
		return locationY;
	}

	/**
	 * Getter locationX
	 * @return locationX
	 */
	public int getLocationX() {
		return locationX;
	}
	
	/** {@inheritDoc} */
	@Override
	public String toString() {
		return "X:"+locationX+" Y: "+ locationY;
	}

	
}
