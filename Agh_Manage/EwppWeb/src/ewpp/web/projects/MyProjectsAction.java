package ewpp.web.projects;

import java.util.List;

import ewpp.business.entity.Project;
import ewpp.business.workers.ProjectWorker;
import ewpp.web.AbstractEwppAction;

/**
 * Akcja moje projekty.
 * @author malczyk.pawel@gmail.com
 *
 */
public class MyProjectsAction extends AbstractEwppAction {

	/** Serial. */
	private static final long serialVersionUID = -5234836208666123871L;

	/** Moje projekty. */
	private List < Project > myProjects;


	/** Konstruktor.*/
	public MyProjectsAction() { };


	/** {@inheritDoc} */
	@Override
	protected String doList() {

		myProjects = locate(ProjectWorker.class).retrieveMyActiveProjects(getLoggedUserId());
		return LIST;
	}


	/**
	 * @return the myProjects
	 */
	public List < Project > getMyProjects() {
		return myProjects;
	}


	/**
	 * @param myProjects the myProjects to set
	 */
	public void setMyProjects(List < Project > myProjects) {
		this.myProjects = myProjects;
	}




}
