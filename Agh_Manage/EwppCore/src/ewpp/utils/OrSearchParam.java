package ewpp.utils;

import javax.persistence.Query;

/**
 * Sklejone ORem kilka parametrow wyszukiwania traktowane jako calosc.
 * tzn jedna wartosc dla kazdej skladowaje ORa.
 * 
 * przyklad:
 * o.ss.aa = 1 OR o.ss.bb =1
 * 
 * @author malczyk.pawel@gmail.com
 *
 */
public class OrSearchParam implements SearchParam {
	
	/**
	 * Stala.
	 * 
	 */
	private static final String OR_PARAM = "orParam";
	
	/**
	 * Czesci OR'a.
	 */
	private SimpleSearchParam [] paramParts;
	
	
	/**
	 * Konstruktor.
	 */
	public OrSearchParam() {
		
	}
	
	/**
	 * Konstruktor.
	 * @param searchParts czscie OR'a
	 */
	public OrSearchParam(SimpleSearchParam ...searchParts) {
		paramParts = searchParts;
	}
	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append("(");
		for(int i=0; i<paramParts.length; i++) {
			SimpleSearchParam simpleParam = paramParts[i];
			String tempPart = simpleParam.toString();
			tempPart.replaceAll(simpleParam.getName(), OR_PARAM);
			result.append(tempPart);
			if(i<paramParts.length-1) {
				result.append(" OR ");
			}
		}
		result.append(")");
		return result.toString();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void processQueryValues(Query result) {
		System.out.println(">>>>>.<<<<<<<<<");
		for(SimpleSearchParam searchParamPart : paramParts) {
			searchParamPart.processQueryValues(result);
		}

	}
}
