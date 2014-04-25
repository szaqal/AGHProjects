package ewpp.business.workers;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ewpp.auth.entity.User;
import ewpp.business.constants.ProjectStageStatus;
import ewpp.business.constants.ProjectStatus;
import ewpp.business.entity.Project;
import ewpp.business.entity.ProjectStage;
import ewpp.business.entity.ProjectStageIteration;
import ewpp.business.entity.ProjectTeam;
import ewpp.business.workers.ProjectWorker.ProjectWorkerLocal;
import ewpp.business.workers.ProjectWorker.ProjectWorkerRemote;
import ewpp.business.workers.UsersWorker.UsersWorkerLocal;
import ewpp.messaging.workers.MailerWorker.MailerWorkerLocal;
import ewpp.utils.PagingInfo;
import ewpp.utils.SearchParameters;
import ewpp.utils.StringUtils;
import ewpp.workers.AbstractWorkerBean;

/**
 * Implementacja workra.
 * @author malczyk.pawel@gmail.com
 *
 */
@Stateless
@Local(ProjectWorkerLocal.class)
@Remote(ProjectWorkerRemote.class)
public class ProjectWorkerBean extends AbstractWorkerBean implements ProjectWorker {

	/** Stala. */
	private static final String PROJECT_TITLE = "projectTitle";

	/** Stala. */
	private static final int HUNDERED = 100;

	/** Parametr zapytania. */
	private static final String MEMBER_PARAM = "memberId";

	/** Parametr wyszukiwania. */
	private static final String PROJECT_ID_PARAM = "projectId";
	
	/** Parametr wyszukiwania. */
	private static final String PROJECT_STATUS = "projectStatus";
	

	/**
	 * Logger.
	 */
	private static final Logger LOG = LoggerFactory.getLogger(ProjectWorkerBean.class);

	/** Worker uzytkownikow. */
	@EJB
	private UsersWorkerLocal usersWorker;
	
	/** Worker powiadomien mailowych. */
	@EJB
	private MailerWorkerLocal mailerWorker;


	/** Konstruktor. */
	public ProjectWorkerBean() { };

	/** {@inheritDoc} */
	@Override
	public void saveNewProject(int lecturerId, List<Integer> students, String topic, String description, int projectCreatorId) {
		LOG.debug("Project Save {}", topic+" "+description);

		Project project = new Project();
		project.setTitle(topic);
		project.setDescription(description);
		project.setProjectStatus(ProjectStatus.REQUESTED);
		ProjectTeam projTeam = prepareProjectTeam(lecturerId, students);
		project.setProjectTeam(projTeam);
		saveEntity(project);
		
		String projectCreatorLabel = usersWorker.retrieveUser(projectCreatorId).getLabel();
		List<String> participantsEmails = new ArrayList<String>();
		for(User user : projTeam.getParticipants()) {
			participantsEmails.add(user.getEmail());
		}
		
		mailerWorker.sendProjectCreatedNotification( projectCreatorLabel, participantsEmails, topic);

	}


	/**
	 * Zespol projektowy.
	 * @param lecturerId id wykladowcy
	 * @param studentsIds identyfikatory studentow
	 * @return utworzony team projektowy
	 */
	private ProjectTeam prepareProjectTeam(int lecturerId, List<Integer> studentsIds) {

		ProjectTeam projectTeam = new ProjectTeam();
		List<User> students = new ArrayList<User>();

		for(int studentId : studentsIds){
			students.add(usersWorker.retrieveUser(studentId));
		}

		projectTeam.setMembers(students);
		projectTeam.setLecturer(usersWorker.retrieveUser(lecturerId));

		return projectTeam;
	}


	/** {@inheritDoc} */
	@Override
	public List<Project> retrieveMyActiveProjects(int userId) {
		return retrieveMyActiveProjects(userId, null, null);
	}
	
	/** {@inheritDoc} */
	@Override
	public List<Project> retrieveMyClosedProjects(int userId) {
		return retrieveMyClosedProject(userId, null, null);
	}
	
	/** {@inheritDoc} */
	@Override
	public List<Project> retrieveMyClosedProject(int userId, PagingInfo pagingInfo, String title) {
		String qry = "SELECT pt.project FROM ProjectTeam pt JOIN pt.members mem WHERE mem.uniqueId = :memberId AND pt.project.projectStatus = :projectStatus";
		
		if (StringUtils.isNotEmpty(title)) {
			qry += appendTitleToSearch();
		}
		
		Query query = getEntityManager().createQuery(qry);
		query.setParameter(MEMBER_PARAM, userId);
		query.setParameter(PROJECT_STATUS, ProjectStatus.FINISHED);
		
		if (StringUtils.isNotEmpty(title) ) {
			query.setParameter(PROJECT_TITLE, StringUtils.QL_ANY + title + StringUtils.QL_ANY );
		}
		
		if(pagingInfo != null) {
			query.setFirstResult(pagingInfo.getFirstResult());
			query.setMaxResults(pagingInfo.getRowsPerPage());
		}
		
		List<Project> resultList = getResultList(query, Project.class);
		
		return resultList;
	}
	
	/** {@inheritDoc} */
	@Override
	public List<Project> retrieveMyActiveProjects(int userId, PagingInfo pagingInfo, String title) {
		String qry = "SELECT pt.project FROM ProjectTeam pt JOIN pt.members mem WHERE mem.uniqueId = :memberId AND  pt.project.projectStatus <> :projectStatus";
		
		if (StringUtils.isNotEmpty(title)) {
			qry += appendTitleToSearch();
		}
		
		Query query = getEntityManager().createQuery(qry);
		query.setParameter(MEMBER_PARAM, userId);
		query.setParameter(PROJECT_STATUS, ProjectStatus.FINISHED);
		
		if (StringUtils.isNotEmpty(title) ) {
			query.setParameter(PROJECT_TITLE, StringUtils.QL_ANY + title + StringUtils.QL_ANY );
		}
		
		if(pagingInfo != null) {
			query.setFirstResult(pagingInfo.getFirstResult());
			query.setMaxResults(pagingInfo.getRowsPerPage());
		}
		
		List<Project> resultList = getResultList(query, Project.class);
		return resultList;
	}
	
	/** {@inheritDoc} */
	@Override
	public Long retrieveMyActiveProjetsCount(int userId, String title) {
		String query = "SELECT COUNT(pt.project) FROM ProjectTeam pt JOIN pt.members mem WHERE mem.uniqueId = :memberId AND pt.project.projectStatus <> :projectStatus";
		
		if (StringUtils.isNotEmpty(title)) {
			query += appendTitleToSearch();
		}
		
		Query qry = getEntityManager().createQuery(query);
		qry.setParameter(MEMBER_PARAM, userId);
		qry.setParameter(PROJECT_STATUS, ProjectStatus.FINISHED);
		
		if (StringUtils.isNotEmpty(title) ) {
			qry.setParameter(PROJECT_TITLE, StringUtils.QL_ANY + title + StringUtils.QL_ANY );
		}
		
		return getSingleResult(qry, Long.class);
	}

	/**
	 * Dodaje do zapytanie fragmentg dotyczacy tytulu.
	 * @return fragment zapytania
	 */
	private String appendTitleToSearch() {
		return " AND pt.project.title like :projectTitle";
	}


	/** {@inheritDoc} */
	@Override
	public Project retrieveProject(int projectId) {
		return retrieveEntityId(projectId, Project.class);
	}


	/** {@inheritDoc} */
	@Override
	public List<User> retrieveProjectMemebers(int projectId) {
		Project project = retrieveProject(projectId);
		List<User> result = new ArrayList<User>();
		for(User user: project.getProjectTeam().getMembers()) {
			result.add(user);
		}
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public void acceptProject(int projectId) {
		Project project = retrieveProject(projectId);
		project.setProjectStatus(ProjectStatus.ACCEPTED);
		saveEntity(project);
		
		List<User> members = project.getProjectTeam().getMembers();
		List<String> membersEmails = new ArrayList<String>();
		for(User member : members) {
			membersEmails.add(member.getEmail());
		}
		mailerWorker.sendProjectAcceptedNotification(membersEmails, project.getProjectTeam().getLecturer().getLabel());
	}


	/** {@inheritDoc} */
	@Override
	public void addProjectStage(int projectId, String title, String description) {
		Project project = retrieveProject(projectId);
		ProjectStage projectStage = new ProjectStage();
		projectStage.setDescription(description);
		projectStage.setTitle(title);
		projectStage.setProject(project);
		projectStage.setStageStatus(ProjectStageStatus.OPENED);
		saveEntity(projectStage);
		
		//Dodanie pierwszej iteracji
		addProjectStageIteration(projectStage.getUniqueId());
	}


	/** {@inheritDoc} */
	@Override
	public List<ProjectStage> retrieveProjectStages(int projectId) {
		LOG.debug("Retrieveing project stages");
		String qry = "SELECT pr FROM ProjectStage AS pr WHERE pr.project.uniqueId = :projectId";
		Query query = getEntityManager().createQuery(qry);
		query.setParameter(PROJECT_ID_PARAM, projectId);
		List<ProjectStage> resultList = getResultList(query, ProjectStage.class);
		LOG.debug("Found {}", resultList.size());
		return resultList;
	}

	/** {@inheritDoc} */
	@Override
	public ProjectStage retrieveProjectStage(int projectStageId) {
		LOG.debug("Retrieveing projectStage");
		return retrieveEntityId(projectStageId, ProjectStage.class);
	}


	
	/** {@inheritDoc} */
	@Override
	public Long retrieveMyClosedProjectsCount(int userId, String title) {
		String qry = "SELECT COUNT(pt.project) FROM ProjectTeam pt JOIN pt.members mem WHERE mem.uniqueId = :memberId "
				+ "AND pt.project.projectStatus = :projectStatus";
		
		if (StringUtils.isNotEmpty(title)) {
			qry += appendTitleToSearch();
		}
		
		Query query = getEntityManager().createQuery(qry);
		query.setParameter(MEMBER_PARAM, userId);
		query.setParameter(PROJECT_STATUS, ProjectStatus.FINISHED);
		
		if (StringUtils.isNotEmpty(title) ) {
			query.setParameter(PROJECT_TITLE, StringUtils.QL_ANY + title + StringUtils.QL_ANY );
		}

		return getSingleResult(query, Long.class);
	}



	/**
	 * {@inheritDoc}
	 */
	@Override
	public int computeProjectProgress(int projectId) {
		
		Query query = getEntityManager().createNamedQuery("countCompletedStages");
		query.setParameter(PROJECT_ID_PARAM, projectId);
		query.setParameter("status", ProjectStageStatus.FINISHED);
		long completedStages = getSingleResult(query, Long.class);
		
		query = getEntityManager().createNamedQuery("countAllStages");
		query.setParameter(PROJECT_ID_PARAM, projectId);
		long stagesCount = getSingleResult(query, Long.class);
		
		double ratio = (double) completedStages / (double) stagesCount;
		int progress = (int) (ratio * HUNDERED);
		
		return (stagesCount == 0) ? 0 : progress;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void acceptProjectStage(int projectStageId) {
		ProjectStage pstage = retrieveEntityId(projectStageId, ProjectStage.class);
		pstage.setStageStatus(ProjectStageStatus.FINISHED);
		saveEntity(pstage);
		
		List<User> members = pstage.getProject().getProjectTeam().getMembers();
		List<String> membersEmails = new ArrayList<String>();
		for(User member : members) {
			membersEmails.add(member.getEmail());
		}
		mailerWorker.sendProjectStageClosedNotification(membersEmails,  pstage.getProject().getProjectTeam().getLecturer().getLabel());
	}

	/** {@inheritDoc} */
	@Override
	public void addProjectStageIteration(int projectStageId) {
		ProjectStage projectStage = retrieveProjectStage(projectStageId);
		LOG.debug("Adding project stage iteration to {}", projectStage.getUniqueId());
		ProjectStageIteration iteration = new ProjectStageIteration();
		iteration.setProjectStage(projectStage);
		saveEntity(iteration);
		
	}

	/** {@inheritDoc} */
	@Override
	public List<ProjectStageIteration> retrieveIterations(int projectStageId) {
		Query query = getEntityManager().createNamedQuery("iterationByStage");
		query.setParameter("stageId", projectStageId);
		return getResultList(query, ProjectStageIteration.class);
	}

	/** {@inheritDoc} */
	@Override
	public ProjectStageIteration retrieveStageIteration(int stageIterationId) {
		return retrieveEntityId(stageIterationId, ProjectStageIteration.class);
	}

	/** {@inheritDoc} */
	@Override
	public void addIterationComment(int iterationId, String comment) {
		ProjectStageIteration iter = retrieveEntityId(iterationId, ProjectStageIteration.class);
		iter.setComment(comment);
		saveEntity(iter);
	}

	/** {@inheritDoc} */
	@Override
	public void closeProject(int projectId) {
		Project project = retrieveProject(projectId);
		project.setProjectStatus(ProjectStatus.FINISHED);
		saveEntity(project);
		
		List<User> members = project.getProjectTeam().getMembers();
		List<String> membersEmails = new ArrayList<String>();
		for(User member : members) {
			membersEmails.add(member.getEmail());
		}
		mailerWorker.sendProjectClosedNotification(membersEmails, project.getProjectTeam().getLecturer().getLabel());
	}

	/** {@inheritDoc} */
	@Override
	public List<Project> retrieveMyActiveProjects(PagingInfo pagingInfo, SearchParameters<Project> searchParameters) {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public Long retrieveMyActiveProjectsCount(SearchParameters<Project> searchParameters) {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public List<Project> retrieveMyClosedProject(PagingInfo pagingInfo, SearchParameters<Project> searchParameters) {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public Long retrieveMyClosedProjectsCount(SearchParameters<Project> searchParameters) {
		// TODO Auto-generated method stub
		return null;
	}


}
