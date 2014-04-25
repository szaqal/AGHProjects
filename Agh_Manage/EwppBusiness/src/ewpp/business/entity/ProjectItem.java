package ewpp.business.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author malczyk
 *
 */
@Entity
@SequenceGenerator(name="PR_ITEM_SEQUENCE", sequenceName="PR_ITEM_SEQ")
@Table(name="projectitems")
public class ProjectItem implements Serializable {

	/**
	 * Serial.
	 */
	private static final long serialVersionUID = 4559116928311616502L;

	/**
	 * Typ elementu projekt.
	 * @author malczyk.pawel@gmail.com
	 *
	 */
	public enum ProjectItemType {
		/** Element projektu. */
		PROJECT_ITEM,
		/** Element etapu projektu. */
		STAGE_ITEM
	}

	/** Identyfikator. */
	private int uniqueId;

	/** Projekt do ktorego nalerzy element.*/
	private Project project;
	
	/** Iteracja etapu projektu. */
	private ProjectStageIteration projectStageIteration;

	/** Typ elementu projektu.*/
	private ProjectItemType projectItemType;

	/** Data dodania. */
	private Date createDate;
	

	/** Domyslny konstruktor. */
	public ProjectItem() {
		setCreateDate(new Date());
	}



	/**
	 * Projekt.
	 * @return the project
	 */
	@ManyToOne
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
	 * @return the projectItemType
	 */
	@Enumerated(EnumType.STRING)
	public ProjectItemType getProjectItemType() {
		return projectItemType;
	}

	/**
	 * @param projectItemType the projectItemType to set
	 */
	public void setProjectItemType(ProjectItemType projectItemType) {
		this.projectItemType = projectItemType;
	}

	/**
	 * Getter.
	 * @return the uniqueId
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PR_ITEM_SEQUENCE")
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
	 * @return the projectStageIteration
	 */
	@OneToOne
	public ProjectStageIteration getProjectStageIteration() {
		return projectStageIteration;
	}



	/**
	 * Setter.
	 * @param projectStageIteration the projectStageIteration to set
	 */
	public void setProjectStageIteration( ProjectStageIteration projectStageIteration) {
		this.projectStageIteration = projectStageIteration;
	}



	/**
	 * Getter.
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}



	/**
	 * Setter.
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}


}
