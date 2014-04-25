package ewpp.web.projects.ajax;

import ewpp.business.entity.Project;
import ewpp.business.workers.ProjectWorker;
import ewpp.utils.PagingInfo;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Obsluguje liste ajaxowa zakonczonych projektow.
 * @author malczyk.pawel@gmail.com
 *
 */
public class MyClosedProjectsListAction extends MyProjectsListAction {

	/**
	 * Serial.
	 */
	private static final long serialVersionUID = -8373427979364605663L;
	
	
	/**
	 * Logger. 
	 */
	private static final Logger LOG = LoggerFactory.getLogger(MyClosedProjectsListAction.class);
	

	/** Parametr requestu. */
	private static final String TITLE = "title";
	
	/**
	 * Konstruktor.
	 */
	public MyClosedProjectsListAction() {
		super();
	}
	
	/** {@inheritDoc} */
	@Override
	protected Collection<Project> getContents(PagingInfo pagingInfo) {
		return locate(ProjectWorker.class).retrieveMyClosedProject(getLoggedUserId(), getPagingInfo(), getTitle());
	}
	
	/** {@inheritDoc} */
	@Override
	protected long getProjectsCount() {
		return locate(ProjectWorker.class).retrieveMyClosedProjectsCount(getLoggedUserId(), getTitle());
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
