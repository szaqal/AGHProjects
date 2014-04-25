package turistcompany.db;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * Stałe do podaczania z baza
 * @author malczyk.pawel@gmail.com
 *
 */
public final class DbConstants {
	 
	/** Nazwa klasy sterownika JDBC*/
	public static final String JDBC_DRIVER_CLASS_NAME = "jdbc.driver.classname";
	/** URL połacznia do bazy*/
	public static final String CONNECTION_URL = "jdbc.connection.url";
	/** Uzytkownik na ktorego jest polaczenie. */
	public static final String CONNECTION_USERNAME="jdbc.connection.username";
	/** Haslo na łączącego się użytkownika. */
	public static final String CONNECTION_PASSWORD="jdbc.connection.password";
	/** Propertiesy przechowujace te dane*/
	private static final Properties properties; 
	
	/** 
	 * Statyczny blok inicjalizujacy
	 * wczytuje z pliku właściwości odpowiednioe dane wymagane do połączenia z bazą.
	 */
	static {
		properties = new Properties();
		try {
			properties.load(new FileInputStream(new File("properties/dbdata.properties")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Properties getProperties() {
		return properties;
	}
}
