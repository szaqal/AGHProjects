package ewpp.web.projects;

import ewpp.web.AbstractEwppAction;

/**
 * Akcja grup pojektowyh.
 * @author malczyk.pawel@gmail.com
 *
 */
public class ProjectTeamsAction extends AbstractEwppAction {

	/**
	 * Serial.
	 */
	private static final long serialVersionUID = 2865435135449037781L;

	/** Konstruktor. */
	public ProjectTeamsAction() { }

	/** {@inheritDoc} */
	@Override
	protected String doList() {
		return LIST;
	}

}
