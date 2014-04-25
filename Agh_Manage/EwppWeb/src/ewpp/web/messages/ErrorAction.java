package ewpp.web.messages;

import ewpp.web.AbstractEwppAction;


/**
 * Akcja bledu.
 * @author malczyk.pawel@gmail.com
 *
 */
public class ErrorAction extends AbstractEwppAction {


	/** Serial. */
	private static final long serialVersionUID = -7902456025361171747L;

	/** Konstruktor. */
	public ErrorAction() { };

	/** {@inheritDoc} */
	@Override
	public String getDefaultOpertation() {
		return VIEW;
	}

	/** {@inheritDoc} */
	@Override
	protected String doView() {
		return VIEW;
	}
}
