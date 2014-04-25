package ewpp.web.projects;

import ewpp.business.entity.Project;
import ewpp.business.workers.NotesWorker;
import ewpp.business.workers.ProjectWorker;
import ewpp.web.AbstractEwppAction;

/**
 * Elemnty projektu.
 * @author malczyk.pawel@gmail.com
 *
 */
public class ProjectItemsAction extends AbstractEwppAction {

	/** Importuj plik. */
	public static final String IMPORT_FILE = "importProjectFile";

	/** Nowa notatka. */
	private static final String NEW_NOTE = "newnote";

	/** Gdy dodawan nowa notatka. */
	private static final String NOTE_ADD = "noteAdd";

	/** Serial. */
	private static final long serialVersionUID = -1346732568152017094L;

	/** Identyfikator projektu. */
	private int projectId;

	/** Tytul notatki. */
	private String noteTitle;

	/** Tresc notatki. */
	private String noteContent;
	
	/** Projekt. */
	private Project project;


	/** Konstruktor. */
	public ProjectItemsAction() { };


	/** {@inheritDoc} */
	@Override
	public String execute() throws Exception {
		String result = super.execute();
		if (NEW_NOTE.equals(getOperation())) {
			result = doAddNote();
		} else if (IMPORT_FILE.equals(getOperation())) {
			result = doImportFile();
		} else if (NOTE_ADD.equals(getOperation())) {
			result = processNoteAddition();
		}
		
		project = locate(ProjectWorker.class).retrieveProject(projectId);
		
		return result;
	}


	/**
	 * Dodaje notatke.
	 * @return widok
	 */
	private String processNoteAddition() {
		locate(NotesWorker.class).addNoteToProject(projectId, noteTitle, noteContent, getLoggedUserId());
		return doList();
	}



	/**
	 * Dodaje notatke.
	 * @return widok;
	 */
	private String doAddNote() {
		return NEW_NOTE;
	}

	/**
	 * Dodaje plik.
	 * @return plik
	 */
	private String doImportFile() {
		return IMPORT_FILE;
	}


	/** {@inheritDoc} */
	@Override
	public String getDefaultOpertation() {
		return LIST;
	}

	/** {@inheritDoc} */
	@Override
	protected String doList() {
		return LIST;
	}

	/** {@inheritDoc} */
	@Override
	protected String doNew() {
		return NEW;
	}

	/**
	 * @return the projectId
	 */
	public int getProjectId() {
		return projectId;
	}

	/**
	 * @param projId the projectId to set
	 */
	public void setProjectId(int projId) {
		this.projectId = projId;
	}

	/**
	 * @return the noteTitle
	 */
	public String getNoteTitle() {
		return noteTitle;
	}

	/**
	 * @param notesTitle the noteTitle to set
	 */
	public void setNoteTitle(String notesTitle) {
		this.noteTitle = notesTitle;
	}

	/**
	 * @return the noteContent
	 */
	public String getNoteContent() {
		return noteContent;
	}

	/**
	 * @param notesContent the noteContent to set
	 */
	public void setNoteContent(String notesContent) {
		this.noteContent = notesContent;
	}


	/**
	 * Getter.
	 * @return the project
	 */
	public final Project getProject() {
		return project;
	}


	/**
	 * Setter.
	 * @param project the project to set
	 */
	public final void setProject(Project project) {
		this.project = project;
	}



}
