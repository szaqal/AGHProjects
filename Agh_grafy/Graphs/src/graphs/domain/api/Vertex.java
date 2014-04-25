package graphs.domain.api;

import graphs.editor.domain.VertexDataModel;

import java.io.Serializable;



/**
 * Reprezentuje pojedynczy wezel
 * @author malczyk.pawel@gmail.com
 *
 */
public interface Vertex extends Serializable{
	
	/**
	 * Zwraca indeks wezla
	 * @return indeks wezla
	 */
	int getIndex();
	
	/**
	 * Ustawia index
	 * @param idx;
	 */
	void setIndex(int idx);
	
	/** Okresla czy jest to zrodlo (dla pewnych algorytmow). */
	boolean isSource();
	
	/** Okresla czy jest to cel. */
	boolean isTarget();
	
	/** Okresla czy jest pokolorowany wierzcholek*/
	boolean isColoured();
	
	/** 
	 * Ustawia czy jest to cel
	 * @param isSource czy cel.
	 */
	void setAsSource(boolean isSource);
	
	/** 
	 * Ustawia czy jest to cel
	 * @param isSource czy cel?
	 */
	void setAsTarget(boolean isSource);
	
	/**
	 * Ustawia czy jest pokolorowany
	 * @param isColored czy pokolorowany
	 */
	void setAsColored(boolean isColored);
	
	/**
	 * Zwraca wage wierzcholka
	 * @return liczby krawędzi z niego wychodzących.
	 */
	int getWeight();
	
	/**
	 * Ustawia wage wierzcholka
	 * @param weight waga wierzcholka
	 */
	void setWeight(int weight);
	
	
	
	
	/** Zwraca umiejscowienie wezla */
	Location getVertexLocation();
	
	/** Zwraca DataModel. */
	VertexDataModel getDataModel();
	
	/** Ustawia dataMoel */
	void setDataModel(VertexDataModel vertexDataModel);
	
	
}
