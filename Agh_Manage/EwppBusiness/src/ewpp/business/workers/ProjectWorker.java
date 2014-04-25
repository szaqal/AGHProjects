package ewpp.business.workers;

import java.util.List;

import ewpp.auth.entity.User;
import ewpp.business.entity.Project;
import ewpp.business.entity.ProjectStage;
import ewpp.business.entity.ProjectStageIteration;
import ewpp.utils.PagingInfo;
import ewpp.utils.SearchParameters;


/**
 * Interfejs workera zarzadzania projektami.
 * @author malczyk.pawel@gmail.com
 *
 */
public interface ProjectWorker {


	/**
	 * Zapisuje nowy projekt.
	 * @param lecturerId
	 * 			prowadzacy projekt
	 * @param students
	 * 			lista studentow stanowiacych grupe
	 * @param topic
	 * 			temat
	 * @param description
	 * 			opis
	 * @param projectCreatorId
	 * 			osoba tworzaca projekt (identyfikator)
	 */
	void saveNewProject(int lecturerId, List<Integer> students, String topic, String description, int projectCreatorId);

	/**
	 * Zapisuje komentarz do iteracji.
	 * @param iterationId identyfikator iteracji
	 * @param comment komentarz
	 */
	void addIterationComment(int iterationId, String comment);
	
	/**
	 * Zwraca liste projektow ktorego dany ozytkownik jest czescia.
	 *
	 * @param userId
	 *            identyfikator uzytkownika
	 * @return lista projektow
	 */
	List<Project> retrieveMyActiveProjects(int userId);
	
	/**
	 * Pobiera projekty uzytkownika (zakonczone).
	 * @param userId  identyfikator.
	 * @return lista projektow.
	 */
	List<Project> retrieveMyClosedProjects(int userId);
	
	/**
	 * Zwraca liste projektow z uwzglednienim stronicowania.
	 * @param userId identyfiaktor uzytkownika
	 * @param pagingInfo informacje o stronicowaniu
	 * @param title fragment tytulu gdy wyszukujemy albo null
	 * @return lista projektow
	 */
	List<Project> retrieveMyActiveProjects(int userId, PagingInfo pagingInfo, String title);
	
	/**
	 * Zwraca liste projektow z uwzglednienim stronicowania.
	 * @param pagingInfo informacje o stronicowaniu
	 * @param searchParameters parametry wyszukiwania.
	 * @return lista projektow
	 */
	List<Project> retrieveMyActiveProjects(PagingInfo pagingInfo, SearchParameters<Project> searchParameters);
	
	/**
	 * Zwraca liste projektow z uwzglednienim stronicowania. (Zakonczone)
	 * @param userId identyfiaktor uzytkownika
	 * @param pagingInfo informacje o stronicowaniu
	 * @param title fragment tytulu przy wyszukiwaniu
	 * @return lista projektow
	 */
	List<Project> retrieveMyClosedProject(int userId, PagingInfo pagingInfo, String title);
	
	/**
	 * Zwraca liste projektow z uwzglednienim stronicowania. (Zakonczone)
	 * @param pagingInfo informacje o stronicowaniu
	 * @param searchParameters parametry wyszukiwnia
	 * @return lista projektow
	 */
	List<Project> retrieveMyClosedProject(PagingInfo pagingInfo, SearchParameters<Project> searchParameters);
	
	/**
	 * Zwraca iteracje dla etapow projektu.
	 * 
	 * @param projectStageId
	 *            identyfikator etapu projektu
	 * @return lista itearcje etapu projektu
	 */
	List<ProjectStageIteration> retrieveIterations(int projectStageId);
	
	/**
	 * Zwraca ilosc moich projektow.
	 * @param userId identyfikator uzytkownika
	 * @param title fragment  tytulu uzywany przy wyszukiwaniu
	 * @return ilosc projektow.
	 */
	Long retrieveMyActiveProjetsCount(int userId, String title);
	
	
	/**
	 * Zwraca ilosc moich projektow.
	 * @param searchParameters parametry wyszukiwania
	 * @return ilosc projektow.
	 */
	Long retrieveMyActiveProjectsCount(SearchParameters<Project> searchParameters);
	
	
	/**
	 * Zwraca ilosc moich projektow (zakonczonych).
	 * @param userId identyfikator uzytkownika
	 * @param title fragment tytulu uzytwany przy wyszukiwaniu
	 * @return ilosc projektow.
	 */
	Long retrieveMyClosedProjectsCount(int userId, String title);
	
	
	/**
	 * Zwraca ilosc moich projektow (zakonczonych).
	 * @param searchParameters parametry wyszukiwania
	 * @return ilosc projektow.
	 */
	Long retrieveMyClosedProjectsCount(SearchParameters<Project> searchParameters);
	
	/**
	 * Zamyka projekt.
	 * @param projectId identyfikator zamykanego projektu
	 */
	void closeProject(int projectId);

	/**
	 * Akceptuje projekt.
	 * @param projectId id projektu
	 */
	void acceptProject(int projectId);

	/**
	 * Zwraca uczesnikow projektu.
	 *
	 * @param projectId
	 *            identyfikator projektu
	 * @return lista uczestnikow
	 */
	List<User> retrieveProjectMemebers(int projectId);
	

	/**
	 *
	 * Dodaj etap projektu.
	 * @param projectId id projektu
	 * @param title tytul
	 * @param description opis
	 */
	void addProjectStage(int projectId, String title, String description);

	/**
	 * Zwraca liste etapow projektu.
	 *
	 * @param projectId
	 *            identyfikator projektu
	 * @return lista etapow projektu
	 *
	 */
	List<ProjectStage> retrieveProjectStages(int projectId);


	/**
	 * Pobiera etap projektu.
	 * @param projectStageId
	 * 		id etapu projektu
	 * @return
	 * 		etap projektu
	 */
	ProjectStage retrieveProjectStage(int projectStageId);
	
	/**
	 * Zwraca iteracje etapu projektu.
	 * @param stageIterationId identyfikator etapu projektu.
	 * @return iteracja etapu projektu.
	 */
	ProjectStageIteration retrieveStageIteration(int stageIterationId);

	/**
	 * Pobiera projekt.
	 *
	 * @param projectId
	 *            identyfikator pobieranego projektu
	 * @return projekt
	 */
	Project retrieveProject(int projectId);
	
	
	/**
	 * Oblicza poste wykonania projektu.
	 * @param projectId identyfikator projektu
	 * @return postep
	 */
	int computeProjectProgress(int projectId);
	
	/**
	 * Akceptuje etap projektu.
	 * @param projectStageId identyfikator etapu projektu
	 */
	void acceptProjectStage(int projectStageId);
	
	/**
	 * Dodaje iteracje o etapu projektu.
	 * @param projectStageId identyfikator etapu projektu
	 */
	void addProjectStageIteration(int projectStageId);
	

	/**
	 * Interfejs lokalny.
	 * @author malczyk.pawel@gmail.com
	 *
	 */
	interface ProjectWorkerLocal extends ProjectWorker {};

	/**
	 * Interfejs zdalny.
	 * @author malczyk.pawel@gmail.com
	 *
	 */
	interface ProjectWorkerRemote extends ProjectWorker {};


}
