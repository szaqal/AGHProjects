package ewpp.web.projects.ajax;

import ewpp.business.entity.File;
import ewpp.business.entity.ProjectItemContent;
import ewpp.business.workers.FilesWorker;
import ewpp.utils.PagingInfo;
import ewpp.web.ajax.AbstractAjaxPagedListAction;
import ewpp.web.ajax.AjaxPage;
import ewpp.web.ajax.AjaxRow;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Ajaxowa lista notatek projektu.
 * @author malczyk.pawel@gmail
 *
 */
public class ProjectFilesListAction extends AbstractAjaxPagedListAction<File>{

	/**
	 * Serial.
	 */
	private static final long serialVersionUID = -9095366202384620216L;
	
	/**
	 * Identyfikator projektu.
	 */
	private static final String PROJECT_ID = "projectId";
	
	/**
	 * Logger.
	 */
	private static final Logger LOG = LoggerFactory.getLogger(ProjectFilesListAction.class);
	
	/**
	 * Zawartosc strony.
	 */
	private AjaxPage<File> pageContent;
	
	/**
	 * Identyfikator proektu.
	 */
	private int projectId;
	
	/**
	 * Konstruktor.
	 */
	public ProjectFilesListAction() { }
	
	/** {@inheritDoc} */
	@SuppressWarnings("unchecked")
	@Override
	public String execute() throws Exception {
		LOG.debug("execute () in {}", this.getClass().getName() );	
		projectId = getProjectIdFromRequest();
		PagingInfo paginginfo = getPagingInfo();
		Collection<File> contents = getContents(paginginfo);
		Collection<AjaxRow<File>> rows = new ArrayList<AjaxRow<File>>();
		
		for(File file : contents ) {
			rows.add(new AjaxRow<File>(file.getJsonData(), file.getUniqueId(), true));
		}
		AjaxRow<File> [] resultRows = rows.toArray(new AjaxRow[contents.size()]);
		pageContent = new AjaxPage<File>(resultRows, getPage(), paginginfo.getPageCount(getProjectFilesCount()), getProjectFilesCount());
		return SUCCESS;
	}
	
	/**
	 * Zwraca kolekcje plikow.
	 * @param paginginfo informacje o stronicowaniu
	 * @return kolekcja plikow
	 */
	private Collection<File> getContents(PagingInfo paginginfo) {
		List<File> files = new ArrayList<File>();
		List<ProjectItemContent> itemContents = locate(FilesWorker.class).retrieveProjectFiles(projectId, paginginfo);
		for (ProjectItemContent projectItemContent : itemContents) {
			files.add((File) projectItemContent);
		}
		return files;
	}
	
	/**
	 * Zwraca ilosc plikow.
	 * @return ilosc plikow
	 */
	private long getProjectFilesCount() {
		return locate(FilesWorker.class).retrieveProjectFilesCount(projectId);
	}

	/** {@inheritDoc} */
	@Override
	public AjaxPage<File> getPageContent() {
		return pageContent;
	}
	
	/**
	 * Wyciaga z requestu informacje o identyfikatorze projektu.
	 * @return identyfikator projektu
	 */
	private int getProjectIdFromRequest() {
		return Integer.parseInt((String) getReqAttributes().get(PROJECT_ID)[0]);
	}



}
