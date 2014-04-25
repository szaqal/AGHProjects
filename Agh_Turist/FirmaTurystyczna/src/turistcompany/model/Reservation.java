package turistcompany.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author malczyk.pawel@gmail.com
 * @author gchmiel@lkn.pl
 *
 */
public class Reservation {
	
	
	private List<Integer> reservedOffers;
	private List<Integer> reservedAttrations;
	
	public Reservation() {
		reservedOffers = new ArrayList<Integer>();
		reservedAttrations = new ArrayList<Integer>();
	}
	
	public void addAttraction(Integer attractionId) {
		reservedAttrations.add(attractionId);
	}
	
	public void addOffer(Integer offerId) {
		reservedOffers.add(offerId);
	}

	public List<Integer> getReservedOffers() {
		return reservedOffers;
	}

	public List<Integer> getReservedAttrations() {
		return reservedAttrations;
	}
	
	public void clearAll() {
		reservedOffers = new ArrayList<Integer>();
		reservedAttrations = new ArrayList<Integer>();
	}
}
