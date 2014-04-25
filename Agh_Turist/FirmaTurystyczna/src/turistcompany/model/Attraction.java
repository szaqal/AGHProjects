package turistcompany.model;

/**
 * Pojedyncza atrakcja.
 * @author malczyk.pawel@mail.com
 *
 */
public class Attraction {
	
	private int uniqueId;
	private String name;
	private String descrption;
	private double price;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescrption() {
		return descrption;
	}
	public void setDescrption(String desctption) {
		this.descrption = desctption;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getUniqueId() {
		return uniqueId;
	}
	public void setUniqueId(int uniqueId) {
		this.uniqueId = uniqueId;
	}
}
