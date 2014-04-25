package graphs.domain.api.gui;

import graphs.domain.api.Edge;

/**
 * Reporezetuje rysowalna krawedz
 * @author malczyk.pawel@gmail.com
 *
 */
public interface DrawableEdge extends Edge, Drawable {
	/**
	 * Rysuje krawedz
	 * @param <T>
	 * @param component
	 */
	<T> void draw(T component);
}
