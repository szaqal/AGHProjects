package ewpp.business.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import ewpp.auth.entity.User;


/**
 *
 * @author malczyk.pawel@gmail.com
 *
 */
@Entity
@SequenceGenerator(name="PROJECT_TEAM_SEQUENCE", sequenceName="PR_TEAM_SEQ")
@Table(name="projectteam")
public class ProjectTeam implements Serializable {

	/** Serial. */
	private static final long serialVersionUID = -3540140976566963097L;

	/** Identyfikator. */
	private int uniqueId;

	/** czlonkowie grupy. */
	private List<User> members;

	/** Prowadzcy projekt wykladowca.*/
	private User lecturer;

	/** Projekt. */
	private Project project;

	/** Konstruktor. */
	public ProjectTeam() { };

	/**
	 * Getter.
	 * @return the uniqueId
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PROJECT_TEAM_SEQUENCE")
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
	 * @return the members
	 */
	@ManyToMany
	@JoinTable(name="team_user")
	public List<User> getMembers() {
		return members;
	}

	/**
	 * Setter.
	 * @param members the members to set
	 */
	public void setMembers(List<User> members) {
		this.members = members;
	}


	/**
	 * Getter.
	 * @return the project
	 */
	@OneToOne(mappedBy="projectTeam")
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
	 * Zwraca prowadzacegoo wykladowce.
	 * @return the lecturer
	 */
	@OneToOne
	public User getLecturer() {
		return lecturer;
	}

	/**
	 * Zwraca prowadzacego wykladowce.
	 * @param lecturer the lecturer to set
	 */
	public void setLecturer(User lecturer) {
		this.lecturer = lecturer;
	}
	
	
	/*
	 * !!!! UWAGA !!!!
	 * Ostroznie z tym gdyz moze powodowac spore
	 * i wacale nie jednoznaczne problemy
	 * (kontekst utrwalania moze potraktowac i utrwalic w memberach lecturera)
	 */
	/**
	 * Zwraca wszystkich participantow projektu.
	 * @return lista wszystkich participantow projektu
	 */
	@Transient
	public List<User> getParticipants() {
		List<User> result = getMembers();
		result.add(getLecturer());
		return result;
	}

}
