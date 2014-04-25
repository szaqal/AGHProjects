package graphs.algorithms;

import graphs.algorithms.api.Algorithm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Fabryka algorytmow.
 * @author malczyk.pawel@gmail.com
 *
 */
public class AlgorithmFactory {
	
	/** Konstruktor. */
	private AlgorithmFactory() { }

	
	/** Przetrzymuje zarejestrowane algorytmy. */
	private static Map< Class<? extends Algorithm>, Algorithm >  
			REGISTERED_ALGORITHMS = new HashMap< Class<? extends Algorithm>, Algorithm >();
	
	/**
	 * Zwraca okreslony algorytm
	 * @param algorithmClass klasa algorytmu
	 * @return obiekt algorytmu
	 */
	public static Algorithm getAlgorithm( Class<? extends Algorithm > algorithmClass ) {
		return REGISTERED_ALGORITHMS.get( algorithmClass );
	}
	
	/**
	 * Zwraca zarejestrowane algorytmy.
	 * @return kolekcja zarejestrowanych
	 */
	public static Collection<String> getAlgorithms() {
		List<String> algorithmsList = new ArrayList<String>();
		for (Algorithm alg : REGISTERED_ALGORITHMS.values()) {
			algorithmsList.add(alg.toString());
		}
		return algorithmsList;
	}
	
	/**
	 * Zwraca agorytm z fabryki.
	 * @param algName nazwa algorytmu.
	 * @return obiekt algorytmu
	 */
	public static Algorithm getAlgorithm(String algName) {
		for(Algorithm alg : REGISTERED_ALGORITHMS.values()) {
			if (alg.toString().equals(algName)) {
				return alg;
			}
		}
		return null;
	}
	
	static {
		REGISTERED_ALGORITHMS.put(KruskalAlgorithm.class, new KruskalAlgorithm() );
		REGISTERED_ALGORITHMS.put(DijkstraAlgorithm.class, new DijkstraAlgorithm() );
		REGISTERED_ALGORITHMS.put(MaxFlow.class, new MaxFlow());
		REGISTERED_ALGORITHMS.put(GraphColoring.class, new GraphColoring());
	}
	
}
