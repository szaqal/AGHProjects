package ewpp.messaging;

/**
 * Zdarzenia powodujace wyslanie wiadomosci email.
 * @author malczyk.pawel@gmail.com
 *
 */
public enum MessageEvent {
	
	/** Utworzenie projektu. */
	PROJECT_CREATED("projectCreated"),
	/** Wyslano wiadomosc. */
	MESSAGE_SENT("messageSent"),
	/** Projekt zaakceptowany. */
	PROJECT_ACCEPTED("projectAccepted"),
	/** Projekt zakonczony. */
	PROJECT_CLOSED("projectClosed"),
	/** Etap projektu zakonczony. */
	PROJECT_STAGE_CLOSED("stageClosed"),
	/** Dodany plik do etapu projektu.*/
	STAGE_FILE_UPLOADED("stageFileUploaded");
	
	/** Wybor. */
	private String selected;
	
	/**
	 * Konstruktor. 
	 * @param arg wybor
	 */
	MessageEvent(String arg) {
		selected = arg;
	}
	
	/**
	 * Getter. 
	 * @return wybrana wartosc
	 */
	public String getEvent() {
		return selected;
	}
	
	
}
