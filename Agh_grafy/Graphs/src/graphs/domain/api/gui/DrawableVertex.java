package graphs.domain.api.gui;

import graphs.domain.api.Vertex;

/**
 * Podstawowy interfejs wezlow ktore moga byc rysowane.
 * @author malczyk.pawel@gmail.com
 *
 */
public interface DrawableVertex extends Vertex, Drawable{
	/**
	 * Rysuje wezel
	 * @param component
	 * 			jakis komponenet na ktorym bedzie rysowany
	 * @param x 
	 * 			gdzie na x
	 * @param y
	 * 			gdzie na y
	 * @param index
	 * 			kolejny index
	 */
	<T> void draw(T component, int x, int y);
	
}
