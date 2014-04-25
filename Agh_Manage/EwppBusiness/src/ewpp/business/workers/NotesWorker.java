package ewpp.business.workers;

import java.util.List;

import ewpp.business.entity.Note;
import ewpp.business.entity.ProjectItemContent;
import ewpp.utils.PagingInfo;

/**
 * 
 * Worker notatek.
 * @author malczyk.pawel@gmail.com
 *
 */
public interface NotesWorker {
	
	
	/**
	 * Zwraca notatke.
	 * @param noteId identyfikator notatki.
	 * @return notatka.
	 */
	Note retrieveNote(int noteId);
	
	/**
	 * Pobiera element projektu (notatka).
	 *
	 * @param projectId identyfikator projektu
	 * @return lista notatek projektu
	 */
	List<ProjectItemContent> retrieveProjectNotes(int projectId);
	
	/**
	 * Pobiera liste notatek projektu z uwzglednieniem stronicowania.
	 * @param projectId identyfikator projektu
	 * @param pagingInfo informacje o stronicowaniu
	 * @return lista notatek
	 */
	List<ProjectItemContent> retrieveProjectNotes(int projectId, PagingInfo pagingInfo);
	
	
	/**
	 * Zwraca ilosc notatek w projekcie.
	 * @param projectId identyfikator projektu
	 * @return ilosc projektow
	 */
	Long retrieveProjectNotesCount(int projectId);
	

	/**
	 * Dodaj notatke do projektu.
	 * 
	 * @param projectId
	 *            identyfikator projektu
	 * @param title
	 *            tytul
	 * @param content
	 *            tresc
	 * @param ownerId
	 *            identyfikator wlasciciela
	 */
	void addNoteToProject(int projectId, String title, String content, int ownerId);

	/**
	 * Interfejs lokalny.
	 * @author malczyk.pawel@gmail.com
	 *
	 */
	interface NotesWorkerLocal extends NotesWorker { }
	
	/**
	 * Interfejs zdalny. 
	 * @author malczyk.pawel@gmail.com
	 *
	 */
	interface NotesWorkerRemote extends NotesWorker { }
	
	

}
