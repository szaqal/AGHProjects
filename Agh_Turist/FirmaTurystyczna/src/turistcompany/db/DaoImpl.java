package turistcompany.db;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import turistcompany.model.Attraction;
import turistcompany.model.Client;
import turistcompany.model.Offer;
import turistcompany.model.Reservation;
import turistcompany.model.ReservationData;
import turistcompany.model.ReservedItem;

/**
 * Implementacja dao.
 * @author malczyk.pawel@gmail.com
 * @author gchmiel@lkn.pl
 *
 */
public class DaoImpl implements Dao{
	
	/** 
	 * Zwraca polaczenie
	 * @return {@link Connection}
	 */
	public Connection getConnection() {
		try {
			return DriverManager.getConnection(
					DbConstants.getProperties().getProperty(DbConstants.CONNECTION_URL),
					DbConstants.getProperties().getProperty(DbConstants.CONNECTION_USERNAME),
					DbConstants.getProperties().getProperty(DbConstants.CONNECTION_PASSWORD)
					);
		} catch (SQLException e) {
			printErrorData(e);
		}
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public Client storeClient(Client client) {
		try {
			CallableStatement statement = getConnection().prepareCall(StoredProcedures.INSERT_CLIENT);
			statement.setString(1, client.getName());
			statement.setString(2, client.getAdress());
			statement.setString(3, client.getNip());
			statement.setString(4, client.getZipCode());
			statement.executeUpdate();
			statement.close();
		} catch (SQLException e) {
			printErrorData(e);
		}
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public List<Client> retrieveClients() {
		List<Client> result = new ArrayList<Client>();
		try {
			CallableStatement statement = getConnection().prepareCall(StoredProcedures.RETRIEVE_CLIENTS);
			ResultSet rs = statement.executeQuery();
			while( rs.next() ) {
				Client client = new Client();
				client.setUniqueId(rs.getInt(1));
				client.setName(rs.getString(2));
				client.setAdress(rs.getString(3));
				client.setNip(rs.getString(4));
				client.setZipCode(rs.getString(5));
				result.add(client);
			}
		} catch (SQLException e) {
			printErrorData(e);
		}
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public List<Attraction> retrieveAttractions() {
		List<Attraction> result = new ArrayList<Attraction>();
		CallableStatement statement;
		try {
			statement = getConnection().prepareCall(StoredProcedures.RETRIEVE_ATTRACTIONS);
			ResultSet rs = statement.executeQuery();
			while ( rs.next() ) {
				Attraction attraction = new Attraction();
				attraction.setUniqueId(rs.getInt(1));
				attraction.setName(rs.getString(2));
				attraction.setDescrption(rs.getString(3));
				attraction.setPrice(rs.getDouble(4));
				result.add(attraction);
			}
			statement.close();
		} catch (SQLException e) {
			printErrorData(e);
		}
		
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public Attraction storeAttraction(Attraction attraction) {
		CallableStatement statement;
		try {
			statement = getConnection().prepareCall(StoredProcedures.INSERT_ATTRACTION);
			statement.setString(1, attraction.getName());
			statement.setString(2, attraction.getDescrption());
			statement.setDouble(3, attraction.getPrice());
			statement.executeUpdate();
			statement.close();
		} catch (SQLException e) {
			printErrorData(e);
		}
		
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public List<Offer> retrieveOffers() {
		List<Offer> offersList = new ArrayList<Offer>();
		
		try {
			CallableStatement statement = getConnection().prepareCall(StoredProcedures.RETRIEVE_OFFERS);
			ResultSet rs = statement.executeQuery();
			while ( rs.next() ) {
				Offer offer = new Offer();
				offer.setUniqueId(rs.getInt(1));
				offer.setName(rs.getString(2));
				offer.setDesription(rs.getString(3));
				offer.setPrice(rs.getDouble(4));
				offer.setStartDate(rs.getDate(5));
				offer.setEndDate(rs.getDate(6));
				offersList.add(offer);
			}
		} catch (SQLException e) {
			printErrorData(e);
		}
		return offersList;
	}

	/** {@inheritDoc} */
	@Override
	public Offer storeOffer(Offer offer) {
		CallableStatement statement;
		try {
			statement = getConnection().prepareCall(StoredProcedures.INSERT_OFFER);
			statement.setString(1, offer.getName());
			statement.setString(2, offer.getDesription());
			statement.setDouble(3, offer.getPrice());
			statement.setDate(4, new java.sql.Date(offer.getStartDate().getTime()));
			statement.setDate(5, new java.sql.Date(offer.getEndDate().getTime()));
			statement.executeUpdate();
		} catch (SQLException e) {
			printErrorData(e);
		}
		return null;
	}

	/** {@inheritDoc} */
	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> searchItems(String arg, Class<T> itemClass) {
		List<T> result = new ArrayList<T>();
		try {
			if (itemClass.equals(Offer.class)) {
			
				PreparedStatement statement = prepareOfferSearchSatement(arg);			
				ResultSet rs = statement.executeQuery();
				while (rs.next()) {
					Offer offer = new Offer();
					offer.setUniqueId(rs.getInt(1));
					offer.setName(rs.getString(2));
					offer.setDesription(rs.getString(3));
					offer.setPrice(rs.getDouble(4));
					offer.setStartDate(rs.getDate(5));
					offer.setEndDate(rs.getDate(6));
					result.add((T)offer);
				}
			
			} else if ( itemClass.equals(Attraction.class)) {
				
				PreparedStatement statement = prepareAttractionSearchStatement(arg);			
				ResultSet rs = statement.executeQuery();
				while ( rs.next() ) {
					Attraction attraction = new Attraction();
					attraction.setUniqueId(rs.getInt(1));
					attraction.setName(rs.getString(2));
					attraction.setDescrption(rs.getString(3));
					attraction.setPrice(rs.getDouble(4));
					result.add((T)attraction);
				}
			} else if (itemClass.equals(Client.class)) {
				
				PreparedStatement statement = prepareClientSearchStatement(arg);			
				ResultSet rs = statement.executeQuery();
				while( rs.next() ) {
					Client client = new Client();
					client.setUniqueId(rs.getInt(1));
					client.setName(rs.getString(2));
					client.setAdress(rs.getString(3));
					client.setNip(rs.getString(4));
					client.setZipCode(rs.getString(5));
					result.add((T)client);
				}
			} else if (itemClass.equals(Reservation.class)) {
			}
		
		} catch (SQLException e) {
			printErrorData(e);
		}
		
		return result;
	}
	
	/**
	 * Przygotowuje parametry wyszukiwania oferty.
	 * @param val wartosc ktorej szukamy
	 * @return przygotowany {@link PreparedStatement}
	 */
	private PreparedStatement prepareOfferSearchSatement(String val) {
		String query = "SELECT * FROM tblOffers WHERE offername like ? or description like ?";
		try {
			PreparedStatement statement = getConnection().prepareStatement(query);
			statement.setString(1, "%"+val+"%");
			statement.setString(2, "%"+val+"%");
			return statement;
		} catch (SQLException e) {
			printErrorData(e);
		}
		return null;
	}
	
	/**
	 * Przygotowuje parametry wyszukiwania atrkacji.
	 * @param val wartosc ktorej szukamy
	 * @return przygotowany {@link PreparedStatement}
	 */
	private PreparedStatement prepareAttractionSearchStatement(String val) {
		String query = "SELECT * FROM tblattractions WHERE attrname like ? or description like ? ";
		try {
			PreparedStatement statement = getConnection().prepareStatement(query);
			statement.setString(1, "%"+val+"%");
			statement.setString(2, "%"+val+"%");
			return statement;
		} catch (SQLException e) {
			printErrorData(e);
		}
		return null;
	}
	
	/**
	 * Przygotowuje parametry wyszukiwania clienta.
	 * @param val wartosc ktorej szukamy
	 * @return przygotowany {@link PreparedStatement}
	 */
	private PreparedStatement prepareClientSearchStatement(String val) {
		String query = "SELECT * FROM tblclients WHERE clientname like ? or adres like ? or nip like ? or zip like ?";
		
		try {
			PreparedStatement statement = getConnection().prepareStatement(query);
			statement.setString(1, "%"+val+"%");
			statement.setString(2, "%"+val+"%");
			statement.setString(3, "%"+val+"%");
			statement.setString(4, "%"+val+"%");
			return statement;
		} catch (SQLException e) {
			printErrorData(e);			
		}
		return null;
	}

	/** {@inheritDoc}*/
	@Override
	public void deleteAttraction(int id) {
		try {
			CallableStatement statement = getConnection().prepareCall(StoredProcedures.DELETE_ATTRACTION);
			statement.setInt(1, id);
			statement.executeUpdate();
			statement.close();
		} catch (SQLException e) {
			printErrorData(e);
		}
	}

	/** {@inheritDoc}*/
	@Override
	public void deleteClient(int id) {
		try {
			CallableStatement statement = getConnection().prepareCall(StoredProcedures.DELETE_CLIENT);
			statement.setInt(1, id);
			statement.executeUpdate();
			statement.close();
		} catch (SQLException e) {
			printErrorData(e);
		}
		
	}

	/** {@inheritDoc}*/
	@Override
	public void deleteClientRow(int id) {
		Connection conn = getConnection();
		try {
			Statement statement = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = statement.executeQuery(String.format("SELECT * FROM tblclients WHERE uniqueid = %s", id));
			rs.first();
			rs.deleteRow();
			rs.close();
			conn.close();
		} catch (SQLException e) {
			printErrorData(e);
		}		
	}

	/** {@inheritDoc}*/
	@Override
	public void deleteOffer(int id) {
		try {
			CallableStatement statement = getConnection().prepareCall(StoredProcedures.DELETE_OFFER);
			statement.setInt(1, id);
			statement.executeUpdate();
			statement.close();
		} catch (SQLException e) {
			printErrorData(e);
		}
		
	}

	/** {@inheritDoc}*/
	@Override
	public Attraction retrieveAttraction(int id) {
		try {
			CallableStatement statement = getConnection().prepareCall(StoredProcedures.RETRIEVE_ATTRACTION);
			statement.setInt(1, id);
			ResultSet rs = statement.executeQuery();			
			rs.next();
			Attraction attraction = new Attraction();
			attraction.setUniqueId(rs.getInt(1));
			attraction.setName(rs.getString(2));
			attraction.setDescrption(rs.getString(3));
			return attraction;
		} catch (SQLException e) {
			printErrorData(e);
		}
		return null;
	}

	/** {@inheritDoc}*/
	@Override
	public Offer retrieveOffer(int id) {
		try {
			CallableStatement statement = getConnection().prepareCall(StoredProcedures.RETRIEVE_OFFER);
			statement.setInt(1, id);
			ResultSet rs = statement.executeQuery();
			rs.next();
			Offer offer = new Offer();
			offer.setUniqueId(rs.getInt(1));
			offer.setName(rs.getString(2));
			offer.setDesription(rs.getString(3));
			return offer;
		} catch (SQLException e) {
			printErrorData(e);
		}
		return null;
	}

	/** {@inheritDoc}*/
	@Override
	public void storeReservation(Reservation reservation, String clientId) {
		Connection conn = getConnection();
		try {
			conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			conn.setAutoCommit(false);
			CallableStatement statement = conn.prepareCall(StoredProcedures.INSERT_RESERVATION);
			java.sql.Date sqlDate = new java.sql.Date(new Date().getTime());
			statement.setInt(1, Integer.parseInt(clientId));
			statement.setDate(2, sqlDate);
			statement.executeUpdate();
			
			PreparedStatement prepStatemet = conn.prepareStatement("SELECT MAX(uniqueId) FROM tblReservation");
			ResultSet rs = prepStatemet.executeQuery();
			rs.next();
			int latestId = rs.getInt(1);
			
			for(Integer i : reservation.getReservedAttrations()){
				CallableStatement stat = conn.prepareCall(StoredProcedures.INSERT_RESERVED_ATTR_ITEM);
				stat.setInt(1, latestId);
				stat.setInt(2, i);
				stat.executeUpdate();
			}
			
			for(Integer i : reservation.getReservedOffers()){
				CallableStatement stat = conn.prepareCall(StoredProcedures.INSERT_RESERVED_OFFER_ITEM);
				stat.setInt(1, latestId);
				stat.setInt(2, i);
				stat.executeUpdate();
			}
			
			conn.commit();
			conn.close();
		} catch (SQLException e) {
			printErrorData(e);
		}
		
	}
	
	/** {@inheritDoc}*/
	@Override
	public void executeScript(String value) {
		Connection conn = getConnection();
		try {
			PreparedStatement statement = conn.prepareStatement(value);
			statement.executeUpdate();
			conn.close();
		} catch (SQLException e) {
			printErrorData(e);
		}
				
	}

	/** {@inheritDoc}*/
	@Override
	public List<ReservationData> retrieveReservations() {
		List<ReservationData> reservations = new ArrayList<ReservationData>();
		Connection conn = getConnection();
		try {
			CallableStatement statement = conn.prepareCall(StoredProcedures.RETRIEVE_EXISTING_RESERVATIONS);
			ResultSet rs = statement.executeQuery();
			
			while ( rs.next() ) {
				ReservationData reservationData = new ReservationData();
				reservationData.setUniqueId(rs.getInt(1));
				reservationData.setReservationDate(rs.getDate(2));
				reservationData.setClientName(rs.getString(3));				
				reservations.add(reservationData);
			}			
			conn.close();
		} catch (SQLException e) {
			printErrorData(e);
		}
		return reservations;
	}

	/** {@inheritDoc}*/
	@Override
	public List<ReservedItem> reservedItems(int reservationId) {
		Connection conn = getConnection();
		List<ReservedItem> reservedItems = new ArrayList<ReservedItem>();
		try {
			CallableStatement statement = conn.prepareCall(StoredProcedures.RETRIEVE_RESERVED_ITEMS_FOR_RESERVATION);
			statement.setInt(1, reservationId);
			ResultSet rs = statement.executeQuery();
			while(rs.next()) {
				ReservedItem item = new ReservedItem();
				item.setUniqueId(rs.getInt(1));
				String name1 = rs.getString(3);
				String name2 = rs.getString(4);
				if (name1 !=null ){
					item.setName(name1);	
				} else if( name2 != null) {
					item.setName(name2);
				}
				reservedItems.add(item);			
			}
			conn.close();
		} catch (SQLException e) {
			printErrorData(e);
		}
		
		return reservedItems;
	}

	/** {@inheritDoc}*/
	@Override
	public void updateAttractions(Attraction attr) {
		Connection conn = getConnection();
		Statement statement;
		try {
			statement = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = statement.executeQuery(String.format("SELECT * FROM tblattractions WHERE uniqueid = %s", attr.getUniqueId()));
			rs.first();
			rs.updateString("attrname", attr.getName());
			rs.updateString("description", attr.getDescrption());
			rs.updateDouble("price", attr.getPrice());
			rs.updateRow();
			rs.close();
			conn.close();
		} catch (SQLException e) {
			printErrorData(e);
		}
		
	}

	/** {@inheritDoc}*/
	@Override
	public void updateClient(Client client) {
		Connection conn = getConnection();
		try {
			Statement statement = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = statement.executeQuery(String.format("SELECT * FROM tblclients WHERE uniqueid = %s", client.getUniqueId()));
			rs.first();
			rs.updateString("clientname", client.getName());
			rs.updateString("adres", client.getAdress());
			rs.updateString("nip", client.getNip());
			rs.updateString("zip", client.getZipCode());
			rs.updateRow();
			rs.close();
			conn.close();
		} catch (SQLException e) {	
			printErrorData(e);
		}
		
	}

	/** {@inheritDoc}*/
	@Override
	public void updateOffer(Offer offer) {
		Connection conn = getConnection();
		try {
			Statement statement = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = statement.executeQuery(String.format("SELECT * FROM tbloffers WHERE uniqueid = %s", offer.getUniqueId()));
			rs.first();
			System.out.println(offer.getEndDate());
			rs.updateString("offername", offer.getName());
			rs.updateString("description", offer.getDesription());
			rs.updateDouble("price", offer.getPrice());
			rs.updateDate("startdate", new java.sql.Date(offer.getStartDate().getTime()));
			rs.updateDate("enddata", new java.sql.Date(offer.getEndDate().getTime()));
			rs.updateRow();
			rs.close();
			conn.close();
		} catch (SQLException e) {
			printErrorData(e);
		}
		
	}
	/**
	 * Metoda powoduje "ladniejsze" wyswietlenie zawartosci wyjatku jesli taki wystapil
	 * @param e wyjątek ktory w którejś z metod wystąpił
	 */
	private void printErrorData(SQLException e) {
		System.out.println("----- EXCEPTION ----");
		System.out.println(e.getMessage());
		System.out.println(e.getSQLState());
		System.out.println(e.getErrorCode());		
	}


	
}
