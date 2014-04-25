package turistcompany.db;

/**
 * Przechwuje procedury skladowane.
 * @author malczyk.pawel@gmail.com
 * @author gchmiel@lkn.pl
 *
 */
public interface StoredProcedures {
	
	/** Określa wywołanie procedury składowanej. */
	String INSERT_CLIENT = "{call addclient(?,?,?,?)}";
	/** Określa wywołanie procedury składowanej. */
	String RETRIEVE_CLIENTS = "{call retrieveClients()}";
	/** Określa wywołanie procedury składowanej. */
	String INSERT_ATTRACTION = "{call addattraction(?,?,?)}";
	/** Określa wywołanie procedury składowanej. */
	String RETRIEVE_ATTRACTIONS = "{call retrieveattractions()}";
	/** Określa wywołanie procedury składowanej. */
	String INSERT_OFFER = "{call addoffer(?,?,?,?,?)}";
	/** Określa wywołanie procedury składowanej. */
	String RETRIEVE_OFFERS = "{call retrieveoffers()}";
	/** Określa wywołanie procedury składowanej. */
	String DELETE_CLIENT = "{call deleteClient(?)}";
	/** Określa wywołanie procedury składowanej. */
	String DELETE_OFFER = "{call deleteoffer(?)}";
	/** Określa wywołanie procedury składowanej. */
	String DELETE_ATTRACTION = "{call deleteattraction(?)}";
	/** Określa wywołanie procedury składowanej. */
	String RETRIEVE_ATTRACTION ="{call retrieveattraction(?)}";
	/** Określa wywołanie procedury składowanej. */
	String RETRIEVE_OFFER = "{call retrieveoffer(?)}";
	/** Określa wywołanie procedury składowanej. */
	String INSERT_RESERVATION = "{call addReservation(?,?)}";
	/** Określa wywołanie procedury składowanej. */
	String INSERT_RESERVED_ATTR_ITEM = "{call addReservedAttrItem(?,?)}";
	/** Określa wywołanie procedury składowanej. */
	String INSERT_RESERVED_OFFER_ITEM = "{call addReservedOfferItem(?,?)}";
	/** Określa wywołanie procedury składowanej. */
	String RETRIEVE_EXISTING_RESERVATIONS = "{call retrieveExistingReservations()}";
	/** Określa wywołanie procedury składowanej. */
	String RETRIEVE_RESERVED_ITEMS_FOR_RESERVATION = "{call retrieveReservedItems(?)}";
}
