package ewpp.web.projects;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ewpp.auth.entity.User;
import ewpp.business.workers.ProjectWorker;
import ewpp.business.workers.UsersWorker;
import ewpp.web.AbstractEwppAction;

/**
 * Akcja edycji projektu.
 * @author malczyk.pawel@gmail.com
 *
 */
public class EditProjectAction extends AbstractEwppAction {

	/** Logger. */
	private static final Logger LOG = LoggerFactory.getLogger(EditProjectAction.class);

	/** Serial. */
	private static final long serialVersionUID = -971849218129217665L;

	/** Lista wykladowcow. */
	private List < User > lecturers;

	/** Lista studentow. */
	private List < User > students;

	/** Lista wybranych do projektu studentow. */
	private List < Integer > selectedStudents;

	/**
	 * Identyfikator wykladowcy prowadzacego
	 * projekt.
	 */
	private int lecturerId;
	
	/**
	 * Identyfikator uzytkownika.
	 */
	private int usrId;

	/** description. */
	private String description;

	/** Temat. */
	private String topic;

	/** Konstruktor. */
	public EditProjectAction() { };

	/** {@inheritDoc} */
	@Override
	public String getDefaultOpertation() {
		return EDIT;
	}

	/** {@inheritDoc} */
	@Override
	protected String doNew() {
		usrId  = getLoggedUserId();
		UsersWorker usersWorker = locate(UsersWorker.class);
		lecturers = usersWorker.retrieveLecturers();
		students = usersWorker.retrieveStudents(null);
		return NEW;
	}

	/** {@inheritDoc} */
	@Override
	protected String doSave() {
		LOG.debug(" Saving {}", selectedStudents.size() );

		List < Integer > selectedStudentsIds = new ArrayList < Integer >();
		selectedStudentsIds.addAll(selectedStudents);

		locate(ProjectWorker.class).saveNewProject(lecturerId, selectedStudentsIds, topic, description, getLoggedUserId());
		return "saved";
	}


	/** {@inheritDoc} */
	@Override
	protected String doEdit() {
		return EDIT;
	}

	/**
	 * @return the lecturers
	 */
	public List < User > getLecturers() {
		lecturers = (lecturers == null) ? new ArrayList < User >() : lecturers;
		return lecturers;
	}

	/**
	 * @param lecturers the lecturers to set
	 */
	public void setLecturers(List < User > lecturers) {
		this.lecturers = lecturers;
	}

	/**
	 * @return the lecturerId
	 */
	public int getLecturerId() {
		return lecturerId;
	}

	/**
	 * @param lecturerId the lecturerId to set
	 */
	public void setLecturerId(int lecturerId) {
		this.lecturerId = lecturerId;
	}

	/**
	 * @return the students
	 */
	public List < User > getStudents() {
		students = (students == null) ? new ArrayList<User>() : students;
		return students;
	}

	/**
	 * @param students the students to set
	 */
	public void setStudents(List < User > students) {
		this.students = students;
	}

	/**
	 * @return the selectedStudents
	 */
	public List < Integer > getSelectedStudents() {
		return selectedStudents;
	}

	/**
	 * @param selectedStudents the selectedStudents to set
	 */
	public void setSelectedStudents(List < Integer > selectedStudents) {
		this.selectedStudents = selectedStudents;
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
	 * @return the topic
	 */
	public String getTopic() {
		return topic;
	}

	/**
	 * @param topic the topic to set
	 */
	public void setTopic(String topic) {
		this.topic = topic;
	}

	/**
	 * Getter.
	 * @return the usrId
	 */
	public final int getUsrId() {
		return usrId;
	}

	/**
	 * Setter.
	 * @param usrId the usrId to set
	 */
	public final void setUsrId(int usrId) {
		this.usrId = usrId;
	}

}
