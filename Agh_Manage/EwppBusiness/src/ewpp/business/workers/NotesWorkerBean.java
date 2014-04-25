package ewpp.business.workers;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ewpp.business.entity.Note;
import ewpp.business.entity.Project;
import ewpp.business.entity.ProjectItem;
import ewpp.business.entity.ProjectItemContent;
import ewpp.business.entity.ProjectItem.ProjectItemType;
import ewpp.business.workers.NotesWorker.NotesWorkerLocal;
import ewpp.business.workers.NotesWorker.NotesWorkerRemote;
import ewpp.business.workers.ProjectWorker.ProjectWorkerLocal;
import ewpp.business.workers.UsersWorker.UsersWorkerLocal;
import ewpp.utils.PagingInfo;
import ewpp.workers.AbstractWorkerBean;


/**
 * Implementacja workera.
 * @author malczyk.pawel@gmail.com
 *
 */
@Stateless
@Local(NotesWorkerLocal.class)
@Remote(NotesWorkerRemote.class)
public class NotesWorkerBean extends AbstractWorkerBean implements NotesWorker {
	
	/** Logger. */
	private static final Logger LOG = LoggerFactory.getLogger(NotesWorkerBean.class);

	/** Parametr wyszukiwania. */
	private static final String PROJECT_ID_PARAM = "projectId";
	
	/** Worker projektow. */
	@EJB
	private ProjectWorkerLocal projectWorker;
	
	/** Worker uzytkownikow. */
	@EJB
	private UsersWorkerLocal usersWorker;
	
	/**
	 * Kontruktor.
	 */
	public NotesWorkerBean() { }
	
	/** {@inheritDoc} */
	@Override
	public Note retrieveNote(int noteId) {
		return retrieveEntityId(noteId, Note.class);
	}
	
	
	/** {@inheritDoc} */
	@Override
	public List<ProjectItemContent> retrieveProjectNotes(int projectId) {
		return retrieveProjectNotes(projectId, null);
	}
	
	/** {@inheritDoc} */
	@Override
	public Long retrieveProjectNotesCount(int projectId) {
		String qry = "SELECT COUNT(note) FROM Note AS note WHERE note.projectItem.project.uniqueId = :projectId";
		Query query = getEntityManager().createQuery(qry);
		query.setParameter(PROJECT_ID_PARAM, projectId);
		return getSingleResult(query, Long.class);
	}
	
	/** {@inheritDoc} */
	@Override
	public void addNoteToProject(int projectId, String title, String content, int ownerId) {
		Project project = projectWorker.retrieveProject(projectId);

		ProjectItem projectItem = new ProjectItem();
		projectItem.setProjectItemType(ProjectItemType.PROJECT_ITEM);
		projectItem.setProject(project);

		saveEntity(projectItem);

		Note note = new Note(content, title);
		note.setProjectItem(projectItem);
		note.setOwner(usersWorker.retrieveUser(ownerId));
		saveEntity(note);

		project.getProjectItems().add(projectItem);
		saveEntity(project);

	}
	
	
	/** {@inheritDoc} */
	@Override
	public List<ProjectItemContent> retrieveProjectNotes(int projectId, PagingInfo pagingInfo) {
		LOG.debug("Retrieving notes");
		String qry = "SELECT note FROM Note AS note WHERE note.projectItem.project.uniqueId = :projectId";
		Query query = getEntityManager().createQuery(qry);
		query.setParameter(PROJECT_ID_PARAM, projectId);
		
		if(pagingInfo != null) {
			query.setFirstResult(pagingInfo.getFirstResult());
			query.setMaxResults(pagingInfo.getRowsPerPage());
		}
		
		List<ProjectItemContent> result = getResultList(query, ProjectItemContent.class);
		return result;
	}

}
