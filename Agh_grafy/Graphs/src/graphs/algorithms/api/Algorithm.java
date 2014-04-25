package graphs.algorithms.api;

import graphs.domain.api.Graph;
import graphs.domain.api.Vertex;

/**
 * Interfejs algorytmu.
 * @author malczyk.pawel@gmail.com
 *
 */
public interface Algorithm {
	
	/**
	 * Wykonuje algorytm.
	 * @param graph graf na ktorym wykonywany bedzie algorytm
	 */
	void processAlgorithm(Graph<? extends Vertex> graph);

}
