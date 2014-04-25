package ewpp.business.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import ewpp.business.constants.ProjectStatus;
import ewpp.entity.AbstractEwppEntity;

/**
 * Reprezentuje realizowany projekt.
 * @author malczyk.pawel@gmail.com
 *
 */
@Entity
@SequenceGenerator(name="PROJECT_SEQUENCE", sequenceName="PROJECT_SEQ")
@Table(name="projects")
public class Project implements Serializable, AbstractEwppEntity {


	/** Serial. */
	private static final long serialVersionUID = 2568846536941128225L;

	/** Identyfikator. */
	private int uniqueId;

	/** Tutul. */
	private String title;

	/** Opis. */
	private String description;

	/**Grupa projektowa. */
	private ProjectTeam projectTeam;

	/** Project stages. */
	private List<ProjectStage> projectStages;

	/** Elementy projektu. */
	private List<ProjectItem> projectItems;

	/** Status projektu. */
	private ProjectStatus projectStatus;

	/** Konstruktor. */
	public Project() { };

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
	 * @return the projectTeam
	 */
	@OneToOne(cascade={CascadeType.MERGE, CascadeType.PERSIST})
	public ProjectTeam getProjectTeam() {
		return projectTeam;
	}

	/**
	 * @param projectTeam the projectTeam to set
	 */
	public void setProjectTeam(ProjectTeam projectTeam) {
		this.projectTeam = projectTeam;
	}

	/**
	 * @return the projectStages
	 */
	@OneToMany
	public List<ProjectStage> getProjectStages() {
		return projectStages;
	}

	/**
	 * @param projectStages the projectStages to set
	 */
	public void setProjectStages(List<ProjectStage> projectStages) {
		this.projectStages = projectStages;
	}

	/**
	 * @return the serialVersionUID
	 */
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	/**
	 * @return the uniqueId
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PROJECT_SEQUENCE")
	public int getUniqueId() {
		return uniqueId;
	}

	/**
	 * @param uniqueId the uniqueId to set
	 */
	public void setUniqueId(int uniqueId) {
		this.uniqueId = uniqueId;
	}

	/**
	 * @return the projectStatus
	 */
	@Enumerated(EnumType.STRING)
	public ProjectStatus getProjectStatus() {
		return projectStatus;
	}

	/**
	 * Zwraca jako string.
	 * @return status projektu
	 */
	@Transient
	public String getCurrentProjectStatus() {
		return projectStatus.toString();
	}

	/**
	 * @param projectStatus the projectStatus to set
	 */
	public void setProjectStatus(ProjectStatus projectStatus) {
		this.projectStatus = projectStatus;
	}

	/**
	 * @return the projectItems
	 */
	@OneToMany(mappedBy="project", cascade={CascadeType.PERSIST, CascadeType.MERGE} )
	public List<ProjectItem> getProjectItems() {
		return projectItems;
	}

	/**
	 * @param projectItems the projectItems to set
	 */
	public void setProjectItems(List<ProjectItem> projectItems) {
		this.projectItems = projectItems;
	}

	/** {@inheritDoc} */
	@Transient
	@Override
	public String[] getJsonData() {
		List<String> data = new ArrayList<String>();
		data.add(title);
		data.add(projectStatus.toString());
		return data.toArray(new String[data.size()]);
	}

}
