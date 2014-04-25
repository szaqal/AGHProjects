package ewpp.business.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import ewpp.business.constants.ProjectStageStatus;

/**
 * Reprezentuje etap projektu.
 * @author malczyk.pawel@gmail.com
 *
 */
@Entity
@SequenceGenerator(name="PROJECT_STAGE_SEQUENCE", sequenceName="PR_STAGE_SEQ")
@Table(name="projectstages")
@NamedQueries({
	@NamedQuery(name="countCompletedStages",
			query="SELECT COUNT(ps) FROM ProjectStage AS ps WHERE ps.stageStatus=:status AND ps.project.uniqueId=:projectId"),
	@NamedQuery(name="countAllStages",
			query="SELECT COUNT(ps) FROM ProjectStage AS ps WHERE ps.project.uniqueId=:projectId")		
})
public class ProjectStage implements Serializable{

	/**
	 * Serial.
	 */
	private static final long serialVersionUID = -4949158970074634359L;

	/** Identyfikator.*/
	private int uniqueId;

	/** Tytul. */
	private String title;

	/** Opis etapu. */
	private String description;

	/** Project. */
	private Project project;
	
	/** Status etapu projektu. */
	private ProjectStageStatus stageStatus;

	/** Konstruktor. */
	public ProjectStage() { };

	/**
	 * Getter.
	 * @return the uniqueId
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PROJECT_STAGE_SEQUENCE")
	public int getUniqueId() {
		return uniqueId;
	}

	/**
	 * Setter.
	 * @param uniqueId the uniqueId to set
	 */
	public void setUniqueId(int uniqueId) {
		this.uniqueId = uniqueId;
	}

	/**
	 * Getter.
	 * @return the project
	 */
	@ManyToOne
	public Project getProject() {
		return project;
	}

	/**
	 * Setter.
	 * @param project the project to set
	 */
	public void setProject(Project project) {
		this.project = project;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Getter.
	 * @return the stageStatus
	 */
	@Enumerated(EnumType.STRING)
	public ProjectStageStatus getStageStatus() {
		return stageStatus;
	}
	
	/**
	 * Zwraca biezacy status jako String.
	 * @return strin
	 */
	@Transient
	public String getCurrentStageStatus() {
		return stageStatus.toString();
	}

	/**
	 * Setter.
	 * @param stageStatus the stageStatus to set
	 */
	public void setStageStatus(ProjectStageStatus stageStatus) {
		this.stageStatus = stageStatus;
	}


}
