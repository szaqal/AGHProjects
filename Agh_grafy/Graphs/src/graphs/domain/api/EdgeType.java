package graphs.domain.api;

/**
 * Typ krawedzi
 * @author malczyk.pawel@gmail.com
 *
 */
public enum EdgeType {
	
	DIRECTED("-->"),
	NOT_DIRECTED("<->");
	
	private String val;
	
	EdgeType(String arg) {
		val = arg;
	}
	
	public String getEdgeType() {
		return val;
	}
}
