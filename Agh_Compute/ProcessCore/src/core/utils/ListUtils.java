package core.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Helper methods operating on lists.
 * @author <a href="mailto:malczyk.pawel@gmail.com">malczyk.pawel@gmail.com</a>
 *
 */
public final class ListUtils {
	
	/** Message passed to exception in case of wrong class list element. */
	private static final String INVALID_CLASS_MSG = "Lista '%s' na pozycji %d zawiera obiekt klasy '%s' inny niż oczekiwana klasa '%s'";
	
	/** Ignored warning. */
	private static final String IGNORED_WARNING = "unchecked"; 
	
	/**
	 * Private Constructor (Instantiating not allowed).
	 */
	private ListUtils() {}
	
	/**
	 * 
	 * Returns common elements in two lists if any of input lists are null
	 * then the other one will be returned. If both lists are null then exception i thrown.
	 * 
	 * @param <T>
	 *            list elements class.
	 * @param list1
	 *            first list.
	 * @param list2
	 *            second list.
	 * @return common elements that are not null.
	 * @throws IOException
	 *             if both lists are null.
	 */
	public static <T> List<T> getCommonElements( List<T> list1, List<T> list2 ) throws IOException {
		if ( list1 == null ) {
			if ( list2 == null ) {
				throw new IOException( "Both lists are null!" );
			} else {
				return list2;
			}
		} else {
			if ( list2 != null ) {
				list1.retainAll( list2 );
			} 
			return list1;
		}
	}
	
	

	/**
	 * Converts raw (non-generic list to generic of supplied type)
	 * 
	 * null's are allowed.
	 * 
	 * @param <T>
	 *            desired type
	 * @param clazz
	 *            desired type class
	 * @param inList
	 *            list
	 * @return converted list
	 */
	@SuppressWarnings( IGNORED_WARNING )
	public static <T> List<T> convertRawListToGenericList( Class<T> clazz, List inList ) {
		List<T> list = (List<T>) inList;
		if ( list == null ) {
			return null;
		}

		// early warning
		int i = 0;
		for ( T o : list ) {
			if ( o != null ) {
				if ( !clazz.isInstance( o ) ) {
					String msg = String.format( INVALID_CLASS_MSG, inList, Integer.valueOf( i ), o.getClass(), clazz );
					throw new ClassCastException( msg );
				}

			}
			i++;
		}
		return list;
	}

	/**
	 * Converts raw (non-generic collection to generic of supplied type)
	 * 
	 * null's are allowed.
	 * 
	 * @param <T>
	 *            desired type
	 * @param clazz
	 *            desired type class
	 * @param coll
	 *            list
	 * @return converted list
	 */
	@SuppressWarnings( IGNORED_WARNING )
	public static <T> Collection<T> convertRawCollectionToGeneric( Class<T> clazz, Collection coll ) {
		Collection<T> collection = (Collection<T>) coll;
		if ( collection == null ) {
			return null;
		}

		// early warning
		int i = 0;
		for ( T o : collection ) {
			if ( o != null ) {
				if ( !clazz.isInstance( o ) ) {
					String msg = String.format(
							INVALID_CLASS_MSG, coll, i, o.getClass(), clazz );
					throw new ClassCastException( msg );
				}

			}
			i++;
		}
		return collection;
	}
	
	/**
	 * Shifts array elements to the right.
	 * @param source
	 * 			source array.
	 * @param offset
	 * 			offset
	 * @return
	 * 			shifted array 
	 */
	public static String[] shitArrayElementsRight( String[] source, int offset ) {
		List<String> temp = new ArrayList<String>();
		for (int i = 0; i < offset; i++) {
			temp.add(StringUtils.EMPTY);
		}
	
		temp.addAll(Arrays.asList(source));
		return  temp.toArray( new String[temp.size()] );
	}

}
