package ewpp.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Parametry wyszukiwania.
 * @author malczyk.pawel@gmail.com
 * @param <T> typ encji wyszukiwanej
 */
public final class SearchParameters<T> {


	/**
	 * Stala.
	 */
	public static final String AND = "AND";
	
	/**
	 * Stala.
	 */
	public static final String OR = "OR";
	
	/**
	 * Null.
	 */
	private static final String NULL = "null";
	
	
	
	/**
	 * Logger.
	 */
	private static final Logger LOG = LoggerFactory.getLogger(SearchParameters.class);
	
	/**
	 * Klasa encji.
	 * 
	 */
	private Class<T> entityClass;
	
	/**
	 * Alias encji.
	 */
	private String entityAlias;

	/**
	 * parametry po ktorych wyszukujemy.
	 */
	private List<SearchParam> params;
	
	/**
	 * Domyslny konstruktor.
	 */
	private SearchParameters() { }
	
	/**
	 * Konstruktor.
	 * @param entityClass
	 * 			klasa encji
	 * @param entityAlias
	 * 			alias encji
	 */
	public SearchParameters(Class<T> entityClass, String entityAlias) {
		this();
		this.entityClass = entityClass;
		this.entityAlias = entityAlias;
		params = new ArrayList<SearchParam>();
	}
	
	/**
	 * Dodaje parametr wysukiwania po encji.
	 * @param name nazwa atrybutu po ktorym wyszukujemy
	 * @param value wartosc.
	 * @param caseSensitive czy rozrozniana wielkosc liter
	 * @return biezace parametry wyszukiwania.
	 */
	public SearchParameters<T> addLikeSearchParam(String name, String value, boolean caseSensitive) {
		params.add(new SimpleSearchParam(name, value, SimpleSearchParam.LIKE, entityAlias, caseSensitive));
		return this;
	}
	
	/**
	 * Dodaje parametr wysukiwania po encji.
	 * @param name nazwa atrybutu  po ktorym wyszukujemy
	 * @param value wartosc.
	 * @param caseSensitive czy rozrozniana wielkosc liter
	 * @param wrapWildCard opakowuje w %%
	 * @return biezace parametry wyszukiwania.
	 */
	@Deprecated
	public SearchParameters<T> addLikeSearchParam(String name, String value, boolean caseSensitive, boolean wrapWildCard ) {
		params.add(new SimpleSearchParam(name, value, SimpleSearchParam.LIKE, entityAlias, caseSensitive, wrapWildCard));
		return this;
	}
	
	/**
	 * Dodaje parametr wysukiwania po encji.
	 * @param name nazwa atrybutu  po ktorym wyszukujemy
	 * @param value wartosc.
	 * @return biezace parametry wyszukiwania.
	 */
	public SearchParameters<T> addLenientLikeSearchParam(String name, String value){
		params.add(SimpleSearchParam.createLanientLikeSearchParam(name, value, entityAlias));
		return this;
	}
	
	/**
	 * Tworzy paramatry zlaczone z OR traktowane jako jeden (expr1 OR expr2).
	 * @param paramsMap mapa przechowujeca atrybut i wartosc
	 * @return search parameter bedacy zlorzeniem z operatorem OR
	 */
	public SearchParameters<T> addOrLenientLikeSearchParams(Map<String, String> paramsMap) {
		List<SimpleSearchParam> searchParamsList = new ArrayList<SimpleSearchParam>();
		for(String key: paramsMap.keySet()) {
			searchParamsList.add(SimpleSearchParam.createLanientLikeSearchParam(key, paramsMap.get(key), entityAlias));
		}
		
		OrSearchParam result = new OrSearchParam( searchParamsList.toArray(new SimpleSearchParam[searchParamsList.size()]));
		params.add(result);
		return this;
	}
	
	/**
	 * Dodaje parametr wysukiwania po encji.
	 * @param name nazwa atrybutu  po ktorym wyszukujemy
	 * @param value wartosc.
	 * @return biezace parametry wyszukiwania.
	 */
	public SearchParameters<T> addStrictLikeSearchParam(String name, String value){
		params.add(SimpleSearchParam.createStrictLikeSearchParam(name, value, entityAlias));
		return this;
	}
	
	
	/**
	 * Dodaje parametr wysukiwania po encji.
	 * @param name nazwa atrybutu po ktorym wyszukujemy
	 * @param value wartosc.
	 * @return biezace parametry wyszukiwania.
	 */
	public SearchParameters<T> addLikeSearchParam(String name, String value) {
		params.add(new SimpleSearchParam(name, value, SimpleSearchParam.LIKE, entityAlias));
		return this;
	}
	
	/**
	 * Dodaje parametr wysukiwania po encji.
	 * @param name nazwa atrybutu po ktorym wyszukujemy
	 * @param value wartosc.
	 * @return biezace parametry wyszukiwania.
	 */
	public SearchParameters<T> addEqualsSearchParam(String name, Serializable value) {
		params.add(new SimpleSearchParam(name, value, NULL.equals(value) ? SimpleSearchParam.IS : SimpleSearchParam.EQALS, entityAlias));
		return this;
	}
	
	/**
	 * Dodaje parametr wysukiwania po encji.
	 * @param name nazwa atrybutu po ktorym wyszukujemy
	 * @param value wartosc.
	 * @return biezace parametry wyszukiwania.
	 */
	public SearchParameters<T> addGreaterSearchParam(String name, String value) {
		params.add(new SimpleSearchParam(name, value, SimpleSearchParam.GREATER_THAN, entityAlias));
		return this;
	}
	
	/**
	 * Dodaje parametr wysukiwania po encji.
	 * @param name nazwa atrybutu po ktorym wyszukujemy
	 * @param value wartosc.
	 * @return biezace parametry wyszukiwania.
	 */
	public SearchParameters<T> addLowerSearchParam(String name, String value) {
		params.add(new SimpleSearchParam(name, value, SimpleSearchParam.LOWER_THAN, entityAlias));
		return this;
	}
	
	
	/**
	 * Generuje zapytanie.
	 * @param entityManager enitytyanager
	 * @param counting czy bedzie to zapytanie zliczajace
	 * @return wygenerowane zapytanie
	 */
	private Query createQuery(EntityManager entityManager, boolean counting) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("SELECT")
		.append(StringUtils.WHITESPACE)
		.append((!counting)?entityAlias:"COUNT("+entityAlias+")")
		.append(StringUtils.WHITESPACE)
		.append("FROM")
		.append(StringUtils.WHITESPACE)
		.append(entityClass.getSimpleName())
		.append(StringUtils.WHITESPACE)
		.append("AS")
		.append(StringUtils.WHITESPACE)
		.append(entityAlias)
		.append(StringUtils.WHITESPACE);

		Query result = null;
		if (!params.isEmpty()) {
			buffer.append("WHERE").append(StringUtils.WHITESPACE);

			for (int i=0; i<params.size(); i++) {
				SearchParam searchParam=params.get(i);
				
				buffer.append(searchParam.toString());
				
				if(i<params.size() - 1) {
					buffer
					.append(StringUtils.WHITESPACE)
					.append(StringUtils.AND)
					.append(StringUtils.WHITESPACE);
				}
			}
			
			LOG.debug("Genereted query " + buffer.toString());
			result = entityManager.createQuery(buffer.toString());
			processQueryValues(result);
			
		}
			
		return result;
	}

	/**
	 * Ustawia wartosci dla zmiennych w zapytaniu.
	 * @param result zapytanie.
	 */
	private void processQueryValues(Query result) {
		LOG.debug("Params size {}", params.size());
		for (SearchParam searchParam : params) {
			searchParam.processQueryValues(result);
		}
	}
	
	
	/**
	 * Generuje zapytanie.
	 * 
	 * @param entityManager
	 *            enitytmanager
	 * @return wygenerowane zapytanie
	 */
	public Query createQuery(EntityManager entityManager) {
		return createQuery(entityManager, false);
	}
	
	
	/**
	 * Generuje zapytanie.
	 * 
	 * @param entityManager
	 *            enitytmanager
	 * @return wygenerowane zapytanie
	 */
	public Query createCountingQuery(EntityManager entityManager) {
		return createQuery(entityManager, true);
	}

}
