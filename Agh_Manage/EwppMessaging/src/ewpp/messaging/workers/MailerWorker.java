package ewpp.messaging.workers;

import java.util.List;

/**
 * Interfejs workera maili.
 * @author malczyk.pawel@gmail.com
 *
 */
public interface MailerWorker {

	/** Wysyla email o tym ze ktos przyszlal wiadomosc.
	 * @param senderLabel label nadawcy
	 * @param recipientEmail email
	 */
	void sendSentMessageNotification(String senderLabel, String recipientEmail);
	
	/**
	 * Wysyla mailem powiadomienie o utworzniue projektu.
	 * @param creatorLabel label tworcy projektu
	 * @param participantsEmail lista email studentow i wykladowcy do ktorych zostanie wyslana wiadomosc
	 * @param projectTitle tytul projektu
	 */
	void sendProjectCreatedNotification(String creatorLabel, List<String> participantsEmail, String projectTitle);
	
	/**
	 * Wysyla mailem powiadomienie do grupy projektowej o zaakceptowaniu przez prowadzacego projektu.
	 * @param participantsEmail liswta emaili studentow
	 * @param leaderLabel label prowadzacego.
	 */
	void sendProjectAcceptedNotification(List<String> participantsEmail, String leaderLabel);
	
	/**
	 * Wysyla mailem powiadomienie do grupy projektowej o zakonczeniu przez prowadzacego projektu.
	 * @param participantsEmail liswta emaili studentow
	 * @param leaderLabel label prowadzacego.
	 */
	void sendProjectClosedNotification(List<String> participantsEmail, String leaderLabel);	
	
	/**
	 * Wysyla mailem powiadomienie do grupy projektowej o zakonczeniu przez prowadzacego projektu.
	 * @param participantsEmail liswta emaili studentow
	 * @param leaderLabel label prowadzacego.
	 */
	void sendProjectStageClosedNotification(List<String> participantsEmail, String leaderLabel);	
	
	/**
	 * Wysyla powiadomienie o dadniu pliku do etapu projektu.
	 * @param addingUserLabel label uzytkownika dodajacego plik.
	 * @param participantsEmail osoby ktore beda powiadamiane
	 */
	void sendFileUploadedNotification(String addingUserLabel, List<String> participantsEmail);
	
	
	/**
	 * Interfejs lokalny.
	 * @author malczyk.pawel@gmail.com
	 *
	 */
	public interface MailerWorkerLocal extends MailerWorker { }
	
	
	/**
	 * Interfejs zdalny.
	 * @author malczyk.pawel@gmail.com
	 *
	 */
	public interface MailerWorkerRemote extends MailerWorker { }
	
}
