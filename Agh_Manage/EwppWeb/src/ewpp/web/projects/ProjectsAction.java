package ewpp.web.projects;

import ewpp.web.AbstractEwppAction;


/**
 * Lista wszytkich projektow.
 * @author malczyk.pawel@gmail.com
 *
 */
public class ProjectsAction extends AbstractEwppAction {

	/**
	 * Serial.
	 */
	private static final long serialVersionUID = 2202917637442736045L;

	/** Konstruktor. */
	public ProjectsAction() { }

	/** {@inheritDoc} */
	@Override
	protected String doList() {
		return LIST;
	}

}
