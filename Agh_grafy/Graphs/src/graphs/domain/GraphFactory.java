package graphs.domain;

import graphs.domain.api.Graph;


/**
 * Fabryka grafow
 * @author malczyk.pawel@gmail.com
 */
public class GraphFactory {
	
	/** Singleton fabryki grafow*/
	private static final GraphFactory instance = new GraphFactory();
	
	/** Konstruktor */
	private GraphFactory() { }
	
	/**
	 * Zwraca instancje fabryki.
	 * @return fabryka
	 */
	public static GraphFactory getInstance() {
		return instance;
	}
	
	/**
	 * Generyczne factory method
	 * @param <T> typ wezlow
	 * @param arg klasa node'ow wezla
	 * @return przygotowany graf
	 */
	public <T> Graph<T> createGraph( Class<T> arg ) {
		Graph<T> result=new SimpleGraph<T>();		
		return result;
	}
	
	
}
