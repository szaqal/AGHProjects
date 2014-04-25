package turistcompany.model;

import java.util.Date;

/**
 * Pojedyncza oferta wycieszki.
 * @author malczyk
 *
 */
public class Offer {
	
	private int uniqueId;
	private String name;
	private String desription;
	private double price;
	private Date startDate;
	private Date endDate;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDesription() {
		return desription;
	}
	public void setDesription(String desription) {
		this.desription = desription;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endData) {
		this.endDate = endData;
	}
	public int getUniqueId() {
		return uniqueId;
	}
	public void setUniqueId(int uniqueId) {
		this.uniqueId = uniqueId;
	}
}
