package node.utils;

import java.io.Serializable;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * Responsible for serializing return objects to XML.
 * @author malczyk.pawel@gmail.com
 *
 */
public final class ResultSerializer {
	
	/**
	 * XStream instance.
	 */
	private XStream xStream;

	
	/**
	 * Constructor.
	 */
	private ResultSerializer() {
		xStream = new XStream(new DomDriver());
	}
	
	/**
	 * Serializes object to XML format.
	 * @param result object being serialized
	 * @return xml document.
	 */
	public String serialize(Serializable result) {
		return xStream.toXML(result);
	}
	
	/**
	 * Returns new instance of result serializer.
	 * @return {@linkplain ResultSerializer} object.
	 */
	public static ResultSerializer get() {
		return new ResultSerializer();
	}
	
	
}
