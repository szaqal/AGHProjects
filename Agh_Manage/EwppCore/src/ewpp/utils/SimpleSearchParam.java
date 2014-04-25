package ewpp.utils;

import java.io.Serializable;

import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Pojedynczy parametr wyszukiwania.
 * @author malczyk.pawel@gmail.com
 *
 */
public final class SimpleSearchParam implements SearchParam {

	/** Sta�a. */
	public static final String EQALS = "=";
	
	/** Sta�a. */
	public static final String LIKE = "LIKE";
	
	/** Sta�a. */
	public static final String IS = "is";
	
	/** Sta�a. */
	public static final String GREATER_THAN = ">";
	
	/** Sta�a. */
	public static final String LOWER_THAN = "<";
	
	/** Sta�a. */
	public static final String ANY_STRING = "%";
	
	/** Logger. */
	private static final Logger LOG = LoggerFactory.getLogger(SimpleSearchParam.class);
	
	/** Sta�a. */
	private static final String CLOSE_LOWER_PART = ")";

	/** Sta�a. */
	private static final String OPEN_LOWER_PART = "lower(";
	
	/** Sta�a. */
	private static final String NULL = "null";
	
	
	
	/**
	 * Nazwa parametru wyszukiwania.
	 * zwykle jest nazwa atybutu klasy
	 */
	private String name;
	
	/**
	 * Wartosc wyszukiwanego parametru.
	 */
	private Serializable value;
	
	/**
	 * Okresla typ por�wnania.
	 */
	private String compareType;
	
	/**
	 * Alias encji.
	 */
	private String entityAlias;
	
	/**
	 * Czy wielkosc liter brana pod uwage.
	 */
	private boolean caseSensitive;
	
	/**
	 * Gdy prawda wartosc powinna by� opakowana
	 * w %%.	
	 */
	private boolean wrapByWildcard;
	
	
	/**
	 * Domy�lny konsturktor.
	 */
	private SimpleSearchParam() { }
	
	/**
	 * Konstruktor.
	 * 
	 * @param name
	 *            nazwa parametru
	 * @param value
	 *            wartosc paramtru
	 * 
	 * @param entityAlias
	 *            alias encji
	 * 
	 * @param compareType
	 *            typ porownania
	 */
	public SimpleSearchParam(String name, Serializable value, String compareType, String entityAlias ) {
		this();
		this.name = name;
		this.value = value;
		this.compareType = compareType;
		this.caseSensitive = true;
		this.wrapByWildcard = false;
		this.entityAlias = entityAlias;
	}
	
	/**
	 * Konstruktor.
	 * 
	 * @param name
	 *            nazwa parametru
	 * @param value
	 *            wartosc paramtru
	 * @param compareType
	 *            typ porownania
	 * @param entityAlias
	 *            alias encji           
	 * @param caseSensitive
	 * 			  czy wielkosc liter rozroznialna
	 */
	public SimpleSearchParam(String name, Serializable value, String compareType, String entityAlias, boolean caseSensitive ) {
		this(name, value, compareType, entityAlias);
		this.caseSensitive = caseSensitive;
	}
	
	/**
	 * Konstruktor.
	 * 
	 * @param name
	 *            nazwa parametru
	 * @param value
	 *            wartosc paramtru
	 * @param compareType
	 *            typ porownania
	 * @param entityAlias
	 *            alias encji           
	 * @param caseSensitive
	 * 			  czy wielkosc liter rozroznialna
	 * @param wrapByWildcard
	 * 			czy opakowa� w %%
	 */
	public SimpleSearchParam(String name, String value, String compareType, String entityAlias, boolean caseSensitive, boolean wrapByWildcard ) {
		this(name, value, compareType, entityAlias);
		this.caseSensitive = caseSensitive;
		this.wrapByWildcard = wrapByWildcard;
	}
	
	/**
	 * Tworzy pojedynczy parametr wyszukiwania
	 * z like rozrozniajacy wielkosc liter oraz nie uzywajacy wildcardow.
	 * 
	 * @param name
	 *            nazwa parametru
	 * @param value
	 *            wartosc parametru.
	 * @param entityAlias
	 *            alias encji
	 * @return przygotowany parametr wyszukiwania.         
	 */
	public static SimpleSearchParam createStrictLikeSearchParam(String name, String value, String entityAlias) {
		return new SimpleSearchParam(name, value, LIKE, entityAlias);
	}
	
	/**
	 * Tworzy pojedynczy parametr wyszukiwania
	 * z like nie rozrozniajacy wielkosci liter oraz uzywajacy wildcardow.
	 * 
	 * @param name
	 *            nazwa parametru
	 * @param value
	 *            wartosc parametru.
	 * @param entityAlias
	 *            alias encji
	 * @return przygotowany parametr wyszukiwania.         
	 */
	public static SimpleSearchParam createLanientLikeSearchParam(String name, String value, String entityAlias) {
		return new SimpleSearchParam(name, value, LIKE, entityAlias, false, true);
	}
	


	/**
	 * Gettrer.
	 * @return wartosc pola.
	 */
	public String getName() {
		return name;
	}


	/**
	 * Gettrer.
	 * @return wartosc pola.
	 */
	public Serializable getValue() {
		return value;
	}
	


	/**
	 * Gettrer.
	 * @return wartosc pola.
	 */
	public String getCompareType() {
		return compareType;
	}
	
	/** {@inheritDoc} */
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		if(caseSensitive) {
			buffer.append(entityAlias).append(StringUtils.DOT).append(name).
			append(StringUtils.WHITESPACE).append(compareType).append(StringUtils.WHITESPACE);
			if(NULL.equals(value)) {
				buffer.append(NULL);
			} else {
				buffer.append(StringUtils.COLON).append(parseParam());
			}
			
		} else {
			buffer.append(OPEN_LOWER_PART).append(entityAlias).append(StringUtils.DOT).append(name).append(CLOSE_LOWER_PART)
			.append(StringUtils.WHITESPACE).append(compareType).append(StringUtils.WHITESPACE);
			if(NULL.equals(value)) {
				buffer.append(NULL);
			} else {
				buffer.append(StringUtils.COLON).append(parseParam());
			}
		}
		return buffer.toString();
	}

	/**
	 * Getter.
	 * @return wartosc pola
	 */
	public boolean isCaseSensitive() {
		return caseSensitive;
	}

	/**
	 * Getter.
	 * @return wartosc pola
	 */
	public boolean isWrapByWildcard() {
		return wrapByWildcard;
	}

	@Override
	public void processQueryValues(Query result) {
		Object value = getValue();
		LOG.debug(" >>> Query param name:" + getName() +" class: "+ value.getClass().getName());
		if (value instanceof String && NULL.equals((String) value)) {
			
			LOG.debug("Found null value in search parameters doing nothing...");
		} else if (value instanceof String){
			
			if(!isCaseSensitive()) {
				value = ((String) getValue()).toLowerCase();
			}
			
			if(isWrapByWildcard()) {
				value = SimpleSearchParam.ANY_STRING + value + SimpleSearchParam.ANY_STRING;
			}
			result.setParameter(parseParam(), value);
		} else {
			result.setParameter(parseParam(), value);
		}
		
	}
	
	/**
	 * HQL nie obsluguje "." w nazwie parametru ( np. :ll.kk )takze podmieniany jest na "_" (:ll_kk).
	 * @return podmieniony
	 */
	private String parseParam() {
		return name.replace(".", "_");
	}

}
