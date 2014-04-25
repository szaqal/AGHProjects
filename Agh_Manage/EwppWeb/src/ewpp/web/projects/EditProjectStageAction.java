package ewpp.web.projects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ewpp.business.workers.ProjectWorker;
import ewpp.web.AbstractEwppAction;

/**
 * Akcja sycji etapu projektu.
 * @author malczyk.pawel@gmail.com
 *
 */
public class EditProjectStageAction extends AbstractEwppAction {

	/**
	 * Serial.
	 */
	private static final long serialVersionUID = -2737020726784416743L;

	/** Logger. */
	private static final Logger LOG = LoggerFactory.getLogger(EditProjectStageAction.class);

	/** Identyfikator projektu. */
	private int projectId;

	/** Tytul. */
	private String stageTitle;

	/** opis. */
	private String stageDesc;

	/** Konruktor. */
	public EditProjectStageAction() { };


	/** {@inheritDoc} */
	@Override
	protected String doNew() {
		return NEW;
	}


	/** {@inheritDoc} */
	@Override
	protected String doSave() {
		LOG.debug("Saving project stage");
		locate(ProjectWorker.class).addProjectStage(projectId, stageTitle, stageDesc);
		return "saved";
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
	 * @return the stageTitle
	 */
	public String getStageTitle() {
		return stageTitle;
	}


	/**
	 * @param stageTitle the stageTitle to set
	 */
	public void setStageTitle(String stageTitle) {
		this.stageTitle = stageTitle;
	}


	/**
	 * @return the stageDesc
	 */
	public String getStageDesc() {
		return stageDesc;
	}


	/**
	 * @param stageDesc the stageDesc to set
	 */
	public void setStageDesc(String stageDesc) {
		this.stageDesc = stageDesc;
	}


}
