package ewpp.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Pomocnicze operacje na tabliach.
 * @author malczyk.pawel@gmail.com
 *
 */
public final class CollectionUtils {
	
	/**
	 * Logger.
	 */
	private static final Logger LOG = LoggerFactory.getLogger(CollectionUtils.class);
	
	/**
	 * Konstruktor.
	 */
	private CollectionUtils() {} 
	
	/**
	 * Przesuwa obiekty tablicy wejsciowej w prawo tworzac nowa tablica.
	 * @param source
	 * 			tablica zrodlowa.
	 * @param offset
	 * 			przesuniecie
	 * @return
	 * 			rozszerzona nowa tablica 
	 */
	public static String[] shitArrayElementsRight( String[] source, int offset ) {
		List<String> temp = new ArrayList<String>();
		for (int i = 0; i < offset; i++) {
			temp.add(StringUtils.EMPTY);
		}
	
		temp.addAll(Arrays.asList(source));
		return  temp.toArray( new String[temp.size()] );
	}
	
	/**
	 * Usuwa element z tablicy o okreslonym indeksie.
	 * @param index index do usuniecia
	 * @param source zrodlow tablica
	 * @return okrojonwa tablica
	 */
	public static String [] removeItemFromArray(int index, String [] source) {
		List<String> sourceList = new ArrayList<String>();
		for(String str : source) {
			sourceList.add(str);
		}

		sourceList.remove(index);
		
		return sourceList.toArray(new String[sourceList.size()]);
	}
}
