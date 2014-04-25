package ewpp.web.projects;

import ewpp.web.AbstractEwppAction;

/**
 * Akcja dostepny dokumentow.
 * @author malczyk.pawel@gmail.com
 *
 */
public class PublicDocumentsAction extends AbstractEwppAction {

	/**
	 * Serial.
	 */
	private static final long serialVersionUID = -7693601411966902413L;
	
	/**
	 * Konstrukotor.
	 */
	public PublicDocumentsAction() {
		
	}
	
	/** {@inheritDoc} */
	@Override
	protected String doList() {
		return LIST;
	}
	
	/** {@inheritDoc} */
	@Override
	public String getDefaultOpertation() {
		return LIST;
	}

}
