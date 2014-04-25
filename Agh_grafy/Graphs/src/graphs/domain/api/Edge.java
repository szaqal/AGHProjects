package graphs.domain.api;

import graphs.editor.domain.EdgeDataModel;

import java.io.Serializable;



/**
 * Reporezentuje krawedz
 * malczyk.pawel@gmail.com
 */
public interface Edge extends Comparable<Edge>, Serializable{

	/** 
	 * Ustawia poczatkowy wezel (ma znaczenie gdy skierowany)
	 * @param startVertex wezel poczatkowy
	 */
	void setStartVertex( Vertex startVertex );
	
	/**
	 * Ustawia koncowy wezel (ma znaczenie gdy skierowany)
	 * @param endVertex wezel koncowy
	 */
	void setEndVertex( Vertex endVertex );
	
	
	/**
	 * Pobiera wezel poczatkowy
	 * @return wezel poczatkowy
	 */
	Vertex getStartVertex( );
	
	/**
	 * Pobiera wezel koncowy
	 * @return wezel koncowy
	 */
	Vertex getEndVertex( );
	
	/**
	 * Ustawia wage krawedzi
	 * @param weight waga
	 */
	void setWeight( int weight );
	
	/**
	 * Pobiera wage krawedzi
	 * @return waga krawedzi
	 */
	int getWeight();
	
	/**
	 * Sprawdza czy zostaa krawedz zaznaczona
	 * @return zaznaczone/nie zaznaczone
	 */
	boolean isSelected();	
	
	/**
	 * Ustawia zaznaczenie krawedzi.
	 * @param selected czy zaznaczono
	 */
	void setSelected(boolean selected);
	
	/**
	 * Pobierz przupstowosc (Wykorzystywane w przeplywach)
	 * @return przepustowosc
	 */
	int getCapacity();
	
	/**
	 * Pobiera biezaca wartosc przeplywu. (Wykorzystywane w przeplywach)
	 * @return wartosc przeplywu
	 */
	int getFlow();
	
	/**
	 * Ustawia przpustowosc.
	 * @param capacity
	 */
	void setCapacity(int capacity);
	
	/**
	 * UStawia przeplyw
	 * @param flow
	 */
	void setFlow(int flow);
	
	EdgeDataModel getDataModel();
	
	void setDataModel(EdgeDataModel edgeDataModel);
	
}
