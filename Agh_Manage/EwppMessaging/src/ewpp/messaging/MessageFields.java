package ewpp.messaging;

/**
 * Przechowuje klucze do pol wiadomosci JMS.
 * @author malczyk.pawel@gmail.com
 *
 */
public final class MessageFields {
	
	/**
	 * Kto wyslal wiadomosc (label uzytkownika).
	 */
	public static final String SENDER_LABEL = "senderLabel";
	
	/**
	 * Typ wiadomosc tzn czy informacja o norym projekcie/ torzymaniu wiadomosci itp.
	 */
	public static final String MESSAGE_EVENT = "messageEvent";
	
	/**
	 * email odbiorcy.
	 */
	public static final String RECIPIENT_EMAIL = "recEmail";
	
	/**
	 * Label uztkownika tworzacego wiadomosc.
	 */
	public static final String PROJECT_CREATOR_LABEL = "projectCreatorLabel";
	
	/**
	 * Wykladowca prowadzacy projekt.
	 */
	public static final String PROJECT_LEADER_LABEL = "projectLeaderLabel";
	
	/**
	 * Tytul projektu.
	 */
	public static final String PROJECT_TITLE = "projectTitle";
	
	/**
	 * Label uzytkownika.
	 */
	public static final String USER_LABEL = "userLabel";
	
	/**
	 * Konstruktor.
	 */
	private MessageFields() { }
	
	
}
