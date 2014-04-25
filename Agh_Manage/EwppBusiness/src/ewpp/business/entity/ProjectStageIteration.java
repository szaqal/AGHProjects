package ewpp.business.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 
 * Itearcja etapu projektu.
 * @author malczyk.pawel
 *
 */
@Entity
@SequenceGenerator(name="STAGE_ITER_SEQUENCE", sequenceName="ITER_SEQ")
@NamedQueries({
	@NamedQuery(name="iterationByStage", query="SELECT psi FROM ProjectStageIteration AS psi WHERE psi.projectStage.uniqueId=:stageId")
})
@Table(name="stageiteration")
public class ProjectStageIteration implements Serializable {

	/**
	 * Serial.
	 */
	private static final long serialVersionUID = -7082938298291990615L;
	
	/** Identyfikator. */
	private int uniqueId;
	
	/** Etap projektu. */
	private ProjectStage projectStage;
	
	/** Element projektu (zalaczony do etapu plik). */
	private File projectItem;
	
	/** Komentarz. */
	private String comment;
	
	/**
	 * Konstruktor.
	 */
	public ProjectStageIteration()  { }


	/**
	 * Getter.
	 * @return the uniqueId
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="STAGE_ITER_SEQUENCE")
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
	 * @return the projectStage
	 */
	@ManyToOne
	public ProjectStage getProjectStage() {
		return projectStage;
	}


	/**
	 * Setter.
	 * @param projectStage the projectStage to set
	 */
	public void setProjectStage(ProjectStage projectStage) {
		this.projectStage = projectStage;
	}


	/**
	 * Getter.
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}


	/**
	 * Setter.
	 * @param comment the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}


	/**
	 * Getter.
	 * @return the projectItem
	 */
	@OneToOne(cascade={CascadeType.PERSIST, CascadeType.REMOVE})
	public File getProjectItem() {
		return projectItem;
	}


	/**
	 * Setter.
	 * @param projectItem the projectItem to set
	 */
	public void setProjectItem(File projectItem) {
		this.projectItem = projectItem;
	}

}
