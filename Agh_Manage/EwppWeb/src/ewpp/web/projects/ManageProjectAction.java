package ewpp.web.projects;

import java.util.ArrayList;
import java.util.List;

import ewpp.auth.entity.User;
import ewpp.business.constants.ProjectStatus;
import ewpp.business.entity.Project;
import ewpp.business.entity.ProjectStage;
import ewpp.business.workers.ProjectWorker;
import ewpp.web.AbstractEwppAction;

/**
 * Zarządzanie projektem.
 * @author malczyk.pawel@gmail.com
 *
 */
public class ManageProjectAction extends AbstractEwppAction {

	/**
	 * Serial.
	 */
	private static final long serialVersionUID = 1314706890605420731L;

	/** Operacja akceptacji. */
	private static final String ACCEPT_PROJECT = "acceptProject";
	
	/** Opearcja zamykania projektu. */
	private static final String CLOSE_PROJECT = "closeProject";

	/** Przegladany i zarzadzany projekt. */
	private Project project;

	/** Uczestnicy projektu. */
	private List < User > members;

	/** Ostrzezenia. */
	private List < String > warnings;

	/** Lista etapow projektu. */
	private List < ProjectStage > projectStages;

	/** Identyfikator projektu. */
	private int projectId;
	
	/** Postep projektu. */
	private int projectProgress;

	/** Konstruktor. */
	public ManageProjectAction() { };


	/** {@inheritDoc} */
	@Override
	public String execute() throws Exception {
		String result = super.execute();
		if (ACCEPT_PROJECT.equals(getOperation())) {
			result = doAccept();
		} else if (CLOSE_PROJECT.equals(getOperation())) {
			result = doClose();
		}
		return result;
	}


	/** {@inheritDoc} */
	@Override
	public String getDefaultOpertation() {
		return EDIT;
	}

	/** {@inheritDoc} */
	@Override
	protected String doEdit() {
		ProjectWorker projectWorker = locate(ProjectWorker.class);
		project = projectWorker.retrieveProject(projectId);
		members = projectWorker.retrieveProjectMemebers(projectId);
		projectStages = projectWorker.retrieveProjectStages(projectId);
		projectProgress = projectWorker.computeProjectProgress(projectId);
		warnings = new ArrayList < String >();
		populateWarnings();

		return EDIT;
	}

	/**
	 * Akceptuje.
	 * @return widok
	 */
	private String doAccept() {
		locate(ProjectWorker.class).acceptProject(projectId);
		return doEdit();
	}
	
	/**
	 * Zamyka projekt.
	 * @return widok
	 */
	private String doClose() {
		locate(ProjectWorker.class).closeProject(projectId);
		return doEdit();
	}

	/**
	 * Wypelnia panel z warningami.
	 */
	private void populateWarnings() {
		if (project.getProjectStatus().equals(ProjectStatus.REQUESTED)) {
			warnings.add("Projekt jeszcze nie zaakceptowany");
		}
	}

	/**
	 * @return the project
	 */
	public Project getProject() {
		return project;
	}

	/**
	 * @param project the project to set
	 */
	public void setProject(Project project) {
		this.project = project;
	}

	/**
	 * @return the projectId
	 */
	public int getProjectId() {
		return projectId;
	}

	/**
	 * @param projectId the projectId to set
	 */
	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	/**
	 * @return the memebers
	 */
	public List < User > getMembers() {
		return members;
	}

	/**
	 * @param memebers the memebers to set
	 */
	public void setMembers(List < User > memebers) {
		this.members = memebers;
	}

	/**
	 * @return the warnings
	 */
	public List < String > getWarnings() {
		return warnings;
	}

	/**
	 * @param warnings the warnings to set
	 */
	public void setWarnings(List < String > warnings) {
		this.warnings = warnings;
	}


	/**
	 * @return the projectStages
	 */
	public List < ProjectStage > getProjectStages() {
		return projectStages;
	}


	/**
	 * @param projectStages the projectStages to set
	 */
	public void setProjectStages(List < ProjectStage > projectStages) {
		this.projectStages = projectStages;
	}


	/**
	 * Getter.
	 * @return the projectProgress
	 */
	public final int getProjectProgress() {
		return projectProgress;
	}


	/**
	 * Setter.
	 * @param projectProgress the projectProgress to set
	 */
	public final void setProjectProgress(int projectProgress) {
		this.projectProgress = projectProgress;
	}

}
