package ewpp.business.workers;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.Query;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ewpp.auth.entity.User;
import ewpp.business.entity.File;
import ewpp.business.entity.Project;
import ewpp.business.entity.ProjectItem;
import ewpp.business.entity.ProjectItemContent;
import ewpp.business.entity.ProjectStageIteration;
import ewpp.business.entity.ProjectItem.ProjectItemType;
import ewpp.business.workers.FilesWorker.FilesWorkerLocal;
import ewpp.business.workers.FilesWorker.FilesWorkerRemote;
import ewpp.business.workers.ProjectWorker.ProjectWorkerLocal;
import ewpp.messaging.workers.MailerWorker.MailerWorkerLocal;
import ewpp.utils.PagingInfo;
import ewpp.utils.SearchParameters;
import ewpp.workers.AbstractWorkerBean;


/**
 * Implementaja workera.
 * @author malczyk.pawel@gmail.com
 *
 */
@Stateless
@Local(FilesWorkerLocal.class)
@Remote(FilesWorkerRemote.class)
public class FilesWorkerBean extends AbstractWorkerBean implements FilesWorker {

	/**
	 * Logger.
	 */
	private static final Logger LOG = LoggerFactory.getLogger(FilesWorkerBean.class);
	
	/** Parametr wyszukiwania. */
	private static final String PROJECT_ID_PARAM = "projectId";
	
	/** Worker projektow. */
	@EJB
	private ProjectWorkerLocal projectWorker;
	
	/** Worker maili. */
	@EJB
	private MailerWorkerLocal mailerWorker;
	
	
	/** Konstruktor. */
	public FilesWorkerBean() { };

	/** {@inheritDoc} */
	@Override
	public File retrieveFile(int fileId) {
		return getEntityManager().find(File.class, fileId);
	}
	
	/** {@inheritDoc} */
	@Override
	public Long retrieveProjectFilesCount(int projectId) {
		String qry = "SELECT COUNT(file) FROM File AS file WHERE file.projectItem.project.uniqueId = :projectId";
		Query query = getEntityManager().createQuery(qry);
		query.setParameter(PROJECT_ID_PARAM, projectId);
		return getSingleResult(query, Long.class);
	}
	
	/** {@inheritDoc} */
	@Override
	public List<ProjectItemContent> retrieveProjectFiles(int projectId) {
		return retrieveProjectFiles(projectId, null);
	}
	
	/** {@inheritDoc} */
	@Override
	public List<ProjectItemContent> retrieveProjectFiles(int projectId, PagingInfo pagingInfo) {
		LOG.debug("Retrieving files");
		String qry = "SELECT file FROM File AS file WHERE file.projectItem.project.uniqueId = :projectId";
		Query query = getEntityManager().createQuery(qry);
		query.setParameter(PROJECT_ID_PARAM, projectId);
		
		if(pagingInfo != null) {
			query.setFirstResult(pagingInfo.getFirstResult());
			query.setMaxResults(pagingInfo.getRowsPerPage());
		}
		
		List<ProjectItemContent> result = getResultList(query, ProjectItemContent.class);
		return result;
	}
	
	/** {@inheritDoc} */
	@Override
	public void addFileToProject(int projectId, String contentType, String fileName, byte[] fileContent, String title, boolean isPublic) {
		LOG.debug("Adding File");

		Project project = projectWorker.retrieveProject(projectId);

		ProjectItem projectItem = new ProjectItem();
		projectItem.setProjectItemType(ProjectItemType.PROJECT_ITEM);
		projectItem.setProject(project);

		saveEntity(projectItem);

		File file = new File();
		file.setFileName(fileName);
		file.setContentType(contentType);
		file.setContent(fileContent);
		file.setTitle(title);
		file.setProjectItem(projectItem);
		file.setMd5Hash(getMd5Hash(fileContent));
		file.setIsPublic(isPublic);
		saveEntity(file);

		project.getProjectItems().add(projectItem);
		saveEntity(project);
	}
	
	/**
	 * Zwraca sume Md5 obliczona na danych.
	 * @param data
	 * 			wejsciowe dane
	 * @return
	 * 			suma md5.
	 */
	private String getMd5Hash(byte [] data) {
		return DigestUtils.md5Hex(data);
		
	}

	/** {@inheritDoc} */
	@Override
	public void addFileToIteration(int stageIterationId, byte[] fileContent, String contentType, String fileName, User creator) {
		
		ProjectStageIteration stageIteration = projectWorker.retrieveStageIteration(stageIterationId);
		ProjectItem projectItem = new ProjectItem();
		projectItem.setProjectItemType(ProjectItemType.STAGE_ITEM);
		projectItem.setProjectStageIteration(stageIteration);
		saveEntity(projectItem);
		
		
		File file = new File();
		file.setContent(fileContent);
		file.setContentType(contentType);
		file.setProjectItem(projectItem);
		file.setFileName(fileName);
		file.setMd5Hash(getMd5Hash(fileContent));
		
		stageIteration.setProjectItem(file);
		saveEntity(stageIteration);
		
		Project project = stageIteration.getProjectStage().getProject();
		
		List<String> participantsEmails = new ArrayList<String>();
		for(User user : project.getProjectTeam().getMembers()) {
			participantsEmails.add(user.getEmail());
		}
		participantsEmails.add(project.getProjectTeam().getLecturer().getEmail());
		
		mailerWorker.sendFileUploadedNotification(creator.getLabel(), participantsEmails);
	}

	/** {@inheritDoc} */
	@Override
	public List<ProjectItemContent> retrieveAllProjectFiles(PagingInfo pagingInfo) {
		LOG.debug("Retrieving all files");
		String qry = "SELECT file FROM File AS file WHERE file.projectItem.projectStageIteration is null AND file.isPublic = true";
		Query query = getEntityManager().createQuery(qry);
		
		if(pagingInfo != null) {
			query.setFirstResult(pagingInfo.getFirstResult());
			query.setMaxResults(pagingInfo.getRowsPerPage());
		}
		
		List<ProjectItemContent> result = getResultList(query, ProjectItemContent.class);
		return result;
	}
	
	/** {@inheritDoc} */
	@Override
	public List<ProjectItemContent> retrieveAllProjectFiles( SearchParameters<File> searchParams, PagingInfo pagingInfo) {
		LOG.debug("Retrieving all files (searchparams)");
		addPublicFilesSearchParams(searchParams);
		
		Query query = searchParams.createQuery(getEntityManager());
		
		if(pagingInfo != null) {
			query.setFirstResult(pagingInfo.getFirstResult());
			query.setMaxResults(pagingInfo.getRowsPerPage());
		}
		
		List<ProjectItemContent> result = getResultList(query, ProjectItemContent.class);
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public Long retrieveAllProjectFilesCount() {
		String qry = "SELECT COUNT(file) FROM File AS file WHERE file.projectItem.projectStageIteration is null AND file.isPublic = true";
		Query query = getEntityManager().createQuery(qry);
		return getSingleResult(query, Long.class);
	}
	
	/** {@inheritDoc} */
	@Override
	public Long retrieveAllProjectFilesCount(SearchParameters<File> searchParams) {
		addPublicFilesSearchParams(searchParams);
		Query query = searchParams.createCountingQuery(getEntityManager());
		return getSingleResult(query, Long.class);
	}
	
	/**
	 * Dodaje parametry wyszukiwania do plikow.
	 * @param searchParams parametry do ktorych dodane zostana nowe
	 */
	private void addPublicFilesSearchParams(SearchParameters<File> searchParams) {
		searchParams.addEqualsSearchParam("isPublic", Boolean.TRUE);
		searchParams.addEqualsSearchParam("projectItem.projectStageIteration", "null");
	}



}
