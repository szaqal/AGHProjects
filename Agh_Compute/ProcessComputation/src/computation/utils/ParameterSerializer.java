package computation.utils;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

/**
 * Parameter serializer.
 * 
 * @author malczyk.pawel@gmail.com
 *
 */
public final class ParameterSerializer {
	
	/**
	 * Constructor.
	 */
	private ParameterSerializer() { }
	
	/**
	 * Serializes parameter map to String.
	 * @param paramMap parameter map
	 * @return String reprezentation
	 */
	public static String serialize(Map<String, Number> paramMap) {
		
		String [] data = new String[paramMap.keySet().size()];
		int i=0;
		for (String key : paramMap.keySet()) {
			data[i] = key + core.utils.StringUtils.COLON + paramMap.get(key).toString();
			i++;
		}
		return StringUtils.join(data, core.utils.StringUtils.COMMA);
	}
	
	
	/**
	 * Serializes parameter map to String.
	 * @param paramMap parameter map
	 * @return String reprezentation
	 */
	public static String serializeString(Map<String, String> paramMap) {
		
		String [] data = new String[paramMap.keySet().size()];
		int i=0;
		for (String key : paramMap.keySet()) {
			String value = paramMap.get(key);
			data[i] = key + core.utils.StringUtils.COLON + String.valueOf(value);
			i++;
		}
		return StringUtils.join(data, core.utils.StringUtils.COMMA);
	}
	
	/**
	 * Deserializes string to map.
	 * @param data string representation
	 * @return prepared data
	 */
	public static Map<String, Number> deserialize(String data) {
		Map<String, Number> result = new HashMap<String, Number>();
		
		String [] splitted = data.split(core.utils.StringUtils.COMMA);
		for (String item : splitted) {
			String [] itemSplitted = item.split(core.utils.StringUtils.COLON);
			result.put(itemSplitted[0], NumberUtils.createNumber(itemSplitted[1]));
		}
		
		return result;
	}
	
	/**
	 * Deserializes string to map.
	 * @param data string representation
	 * @return prepared data
	 */
	public static Map<String, String> deserializeString(String data) {
		Map<String, String> result = new HashMap<String, String>();
		
		String [] splitted = data.split(core.utils.StringUtils.COMMA);
		for (String item : splitted) {
			String [] itemSplitted = item.split(core.utils.StringUtils.COLON);
			if(itemSplitted.length==2) {
				result.put(itemSplitted[0], itemSplitted[1]);
			}
		}
		
		return result;
	}
	
}
