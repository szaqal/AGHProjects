package process.web.actions;

/**
 * Unauthorized action.
 * @author malczyk.pawel@gmail.com
 *
 */
public class UnauthorizedAction extends AbstractProcessAction {

	/**
	 * Serial.
	 */
	private static final long serialVersionUID = 4789681694506314308L;
	
	/**
	 * Constructor.
	 */
	public UnauthorizedAction() {
		super();
	}
	
	/** {@inheritDoc}*/
	@Override
	public String execute() throws Exception {
		return SUCCESS;
	}

}
