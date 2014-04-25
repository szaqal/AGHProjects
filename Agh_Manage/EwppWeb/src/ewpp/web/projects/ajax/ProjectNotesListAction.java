package ewpp.web.projects.ajax;

import ewpp.business.entity.Note;
import ewpp.business.entity.ProjectItemContent;
import ewpp.business.workers.NotesWorker;
import ewpp.utils.PagingInfo;
import ewpp.web.ajax.AbstractAjaxPagedListAction;
import ewpp.web.ajax.AjaxPage;
import ewpp.web.ajax.AjaxRow;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Ajaxowa lista notatek projektu.
 * 
 * @author malczyk.pawel@gmail.com
 *
 */
public class ProjectNotesListAction extends AbstractAjaxPagedListAction<Note> {

	/**
	 * Serial.
	 */
	private static final long serialVersionUID = 1259064384264051513L;
	
	/**
	 * Identyfikator projektu.
	 */
	private static final String PROJECT_ID = "projectId";
	
	/**
	 * Logger.
	 */
	private static final Logger LOG = LoggerFactory.getLogger(ProjectNotesListAction.class);
	
	/**
	 * Zawwartosc strony.
	 */
	private AjaxPage<Note> pageContent;
	
	/**
	 * Identyfikator proektu.
	 */
	private int projectId;
	
	/**
	 * Konstruktor domyslny.
	 */
	public ProjectNotesListAction() { }
	
	/** {@inheritDoc} */
	@SuppressWarnings("unchecked")
	@Override
	public String execute() throws Exception {
		LOG.debug("execute () in {}", this.getClass().getName() );
		projectId = getProjectIdFromRequest();
		PagingInfo paginginfo = getPagingInfo();
		Collection<Note> contents = getContents(paginginfo);
		Collection<AjaxRow<Note>> rows = new ArrayList<AjaxRow<Note>>();
		
		for(Note note : contents ) {
			rows.add(new AjaxRow<Note>(note.getJsonData(), note.getUniqueId(), true));
		}
		AjaxRow<Note> [] resultRows = rows.toArray(new AjaxRow[rows.size()]);
		pageContent = new AjaxPage<Note>(resultRows, getPage(), paginginfo.getPageCount(getProjectNotesCount()), getProjectNotesCount());
		
		return SUCCESS;
	}
	
	/**
	 * Zwraca kolekcje notatek.
	 * @param paginginfo informacje o stronicowaniu
	 * @return kolekcja notatek
	 */
	private Collection<Note> getContents(PagingInfo paginginfo) {
		List<Note> notes = new ArrayList<Note>();
		List<ProjectItemContent> itemContents = locate(NotesWorker.class).retrieveProjectNotes(projectId, paginginfo);
		for (ProjectItemContent itemContent : itemContents) {
			notes.add((Note) itemContent);
		}
		return notes;
	}

	/**
	 * Zwraca ilosc notatek.
	 * @return ilosc notatek
	 */
	private long getProjectNotesCount() {
		return locate(NotesWorker.class).retrieveProjectNotesCount(projectId);
	}
	
	/** {@inheritDoc} */
	@Override
	public AjaxPage<Note> getPageContent() {
		return pageContent;
	}
	
	/**
	 * Wyciaga z requestu informacje o identyfikatorze projektu.
	 * @return identyfikator projektu
	 */
	private int getProjectIdFromRequest() {
		return Integer.parseInt((String) getReqAttributes().get(PROJECT_ID)[0]);
	}



}
