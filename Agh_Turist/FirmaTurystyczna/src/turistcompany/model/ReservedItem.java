package turistcompany.model;

/**
 * Reporezetuje pojedynczy zarezerwowany obiekt.
 * @author malczyk
 *
 */
public class ReservedItem {
	
	
	private int uniqueId; 
	private String name;
	
	
	public int getUniqueId() {
		return uniqueId;
	}
	public void setUniqueId(int uniqueId) {
		this.uniqueId = uniqueId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
