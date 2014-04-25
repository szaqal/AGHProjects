package ewpp.web.projects;

import ewpp.business.workers.ProjectWorker;
import ewpp.web.AbstractEwppAction;

/**
 * Akcja dodwanie komenetarza do korekty.
 * @author malczyk.pawel@r-data.pl
 *
 */
public class CorrectionAction extends AbstractEwppAction {

	/**
	 * Serial.
	 */
	private static final long serialVersionUID = -188804261625189043L;
	
	/** Stala. */
	private static final String CORRECT = "corrent";
	
	/** Identyfikator projektu. */
	private int projectId;
	
	/** Identyfikator etapu projektu. */
	private int projectStageId;
	
	/** Identyfikator iteracji etapu projektu. */
	private int projectStageIterationId;
	
	/** Komentarz. */
	private String comment;
	
	/**
	 * Konstruktor.
	 */
	public CorrectionAction() { }
	
	/** {@inheritDoc} */
	@Override
	public String execute() throws Exception {
		String operation = super.execute();
		if(CORRECT.equals(operation)) {
			operation = doCorrect();
		}
		
		return operation;
	}
	
	/** {@inheritDoc} */
	@Override
	protected String doEdit() {
		return EDIT;
	}
	
	/** 
	 * Dodaj korekte. 
	 * @return zwraca widok
	 */
	private String doCorrect() {
		ProjectWorker worker = locate(ProjectWorker.class);
		worker.addProjectStageIteration(projectStageId);
		worker.addIterationComment(projectStageIterationId, comment);
		return "correct";
	}
	
	/** {@inheritDoc} */
	@Override
	public String getDefaultOpertation() {
		return EDIT;
	}

	/**
	 * Getter.
	 * @return the projectId
	 */
	public final int getProjectId() {
		return projectId;
	}

	/**
	 * Setter.
	 * @param projectId the projectId to set
	 */
	public final void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	/**
	 * Getter.
	 * @return the projectStageId
	 */
	public final int getProjectStageId() {
		return projectStageId;
	}

	/**
	 * Setter.
	 * @param projectStageId the projectStageId to set
	 */
	public final void setProjectStageId(int projectStageId) {
		this.projectStageId = projectStageId;
	}

	/**
	 * Getter.
	 * @return the projectStageIterationId
	 */
	public final int getProjectStageIterationId() {
		return projectStageIterationId;
	}

	/**
	 * Setter.
	 * @param projectStageIterationId the projectStageIterationId to set
	 */
	public final void setProjectStageIterationId(int projectStageIterationId) {
		this.projectStageIterationId = projectStageIterationId;
	}

	/**
	 * Getter.
	 * @return the comment
	 */
	public final String getComment() {
		return comment;
	}

	/**
	 * Setter.
	 * @param comment the comment to set
	 */
	public final void setComment(String comment) {
		this.comment = comment;
	}

}
