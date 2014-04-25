package process.web.actions;

/**
 * Handled Some kind of action command.
 * @author <a href="mailto:malczyk.pawel@gmail.com">malczyk.pawel@gmail.com</a>
 *
 */
public interface ActionHandler {
	
	/** 
	 * Handle.
	 * @return view. 
	 */
	String handle();
}
