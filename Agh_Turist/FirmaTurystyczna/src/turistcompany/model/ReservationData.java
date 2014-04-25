package turistcompany.model;

import java.util.Date;

/**
 * Pojedyncza istniejaca w bazie rezerwacja.
 * @author malczyk.pawel@gmail.com
 *
 */
public class ReservationData {
	
	private int uniqueId;
	private String clientName;
	private Date reservationDate;
	
	public int getUniqueId() {
		return uniqueId;
	}
	public void setUniqueId(int uniqueId) {
		this.uniqueId = uniqueId;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public Date getReservationDate() {
		return reservationDate;
	}
	public void setReservationDate(Date reservationDate) {
		this.reservationDate = reservationDate;
	}
}
