package ewpp.business.workers;

import java.util.List;

import ewpp.auth.entity.User;
import ewpp.business.entity.File;
import ewpp.business.entity.ProjectItemContent;
import ewpp.utils.PagingInfo;
import ewpp.utils.SearchParameters;

/**
 *
 * Worker plikow.
 * @author malczyk.pawel@gmail.com
 *
 */
public interface FilesWorker {

	/**
	 * Wyciaga enje pliku.
	 *
	 * @param fileId
	 *            identyfikator pliku
	 * @return pik
	 */
	File retrieveFile(int fileId);
	
	/**
	 * Zwraca ilosc plikow w projekcie.
	 * @param projectId identyfikator projektu
	 * @return ilosc plikow w projekcie
	 */
	Long retrieveProjectFilesCount(int projectId);
	
	
	/**
	 * Pobiera liste plikow zwiazana z projektem.
	 * @param projectId identyfikator projektu
	 * @param pagingInfo inforamacje o stronicowaniu
	 * @return lista plikow
	 */
	List<ProjectItemContent> retrieveProjectFiles(int projectId, PagingInfo pagingInfo);
	
	/**
	 * Zwraca liste wszystkich plikow.
	 * @param pagingInfo informacje o stronicowaniu
	 * @return lista plikow
	 */
	List<ProjectItemContent> retrieveAllProjectFiles(PagingInfo pagingInfo);
	
	/**
	 * Zwraca liste publicznych notatek wraz z wyszukiwaniem.
	 * @param searchParams parametry wyszukiwania.
	 * @param pagingInfo instormacje o stronicowaniu
	 * @return lista plikow.
	 */
	List<ProjectItemContent> retrieveAllProjectFiles(SearchParameters<File> searchParams, PagingInfo pagingInfo);
	
	/**
	 * Zwraca ilosc publicznych notatek wraz z wyszukiwaniem.
	 * @param searchParams parametry wyszukiwania.
	 * @return lista plikow.
	 */
	Long retrieveAllProjectFilesCount(SearchParameters<File> searchParams);
	
	/**
	 * Zwraca ilosc wszystkich plikow dla wszystkich prjektow.
	 * @return ilosc plikow.
	 */
	Long retrieveAllProjectFilesCount();
	
	/**
	 * Dodaj plik do projektu.
	 *
	 * @param projectId
	 *            identyfikator projektu
	 * @param contentType
	 *            typ pliku
	 * @param fileName
	 *            nazwa pliku
	 * @param fileContent
	 *            zawartosc pliku
	 * @param title tytul
	 * @param isPublic czy publiczny
	 */
	void addFileToProject(int projectId, String contentType, String fileName, byte[] fileContent, String title, boolean isPublic);
	


	/**
	 * Dodaj plik jako rezultat pojedynczej iteracji etapu projektu.
	 * @param stageIterationId identyfikator iteracji etapu projektu
	 * @param fileContent zawartosc pliku
	 * @param contentType typ zawartosci
	 * @param fileName nazwa pliku
	 * @param creator osoba dodajac plik
	 */
	void addFileToIteration(int stageIterationId, byte [] fileContent, String contentType, String fileName, User creator );

	
	/**
	 * Pobiera element projektu (pliki).
	 *
	 * @param projectId
	 *            identyfikator projektu
	 * @return lista elementow projeku
	 */
	List<ProjectItemContent> retrieveProjectFiles(int projectId);
	
	/**
	 * Interfejs lokalny.
	 * @author malczyk.pawel@gmail.com
	 *
	 */
	interface FilesWorkerLocal extends FilesWorker {};

	/**
	 * Interfejs zdalny.
	 * @author malczyk.pawel@gmail.com
	 *
	 */
	interface FilesWorkerRemote extends FilesWorker {};

}
