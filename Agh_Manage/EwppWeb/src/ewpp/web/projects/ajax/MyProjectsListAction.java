package ewpp.web.projects.ajax;

import ewpp.business.entity.Project;
import ewpp.business.workers.ProjectWorker;
import ewpp.utils.PagingInfo;
import ewpp.web.ajax.AbstractAjaxPagedListAction;
import ewpp.web.ajax.AjaxPage;
import ewpp.web.ajax.AjaxRow;

import java.util.ArrayList;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Obsluguje ajaxowa liste oich projektow.
 * @author malczyk.pawel@gmail.com
 *
 */
public class MyProjectsListAction extends AbstractAjaxPagedListAction<Project> {

	/**
	 * Serial.
	 */
	private static final long serialVersionUID = -1523415928175158887L;
	
	/** Parametr requestu. */
	private static final String TITLE = "title";

	
	/**
	 * Logger.
	 */
	private static final Logger LOG = LoggerFactory.getLogger(MyProjectsListAction.class);
	
	/**
	 * Zawartosc strony.
	 */
	private AjaxPage<Project> pageContent;
	
	/**
	 * Konstruktor.
	 */
	public MyProjectsListAction() { }
	
	/** {@inheritDoc} */
	@SuppressWarnings("unchecked")
	@Override
	public String execute() throws Exception {
		LOG.debug("execute () in {}", this.getClass().getName() );
		PagingInfo pagingInfo = getPagingInfo();
		Collection<Project> contents = getContents(pagingInfo);
		Collection<AjaxRow<Project>> rows = new ArrayList<AjaxRow<Project>>();
	
		for (Project project : contents){
			rows.add(new AjaxRow<Project>(project.getJsonData(), project.getUniqueId(), true));
		}
		
		AjaxRow<Project> [] resultRows = rows.toArray(new AjaxRow[rows.size()]);
		pageContent = new AjaxPage<Project>(resultRows, getPage(), pagingInfo.getPageCount(getProjectsCount()), getProjectsCount());
		
		return SUCCESS;
	}
	
	/**
	 * Zwraca ilosc projektow.
	 * @return ilosc projektow.
	 */
	protected long getProjectsCount() {
		return locate(ProjectWorker.class).retrieveMyActiveProjetsCount(getLoggedUserId(), getTitle());
	}
	
	
	/**
	 * Zwraca elementy dla strony.
	 * @param pagingInfo informacje stronicowania
	 * @return lista projektow na strone.
	 */
	protected Collection<Project> getContents(PagingInfo pagingInfo) {
		return locate(ProjectWorker.class).retrieveMyActiveProjects(getLoggedUserId(), pagingInfo, getTitle());	
	}

	/** {@inheritDoc} */
	@Override
	public AjaxPage<Project> getPageContent() {
		return pageContent;
	}
	

	/**
	 * Zwraca parmaetr title z requestu.
	 * @return wartosc parametru.
	 */
	private String getTitle() {
		String result = null;
		try {
			result = getReqAttributes().get(TITLE)[0];
		} catch (NullPointerException e) {
			LOG.warn("no search parameter title");
		}
		return result;
	}

}
