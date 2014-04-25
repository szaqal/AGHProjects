package process.web.actions.lists;

import process.web.actions.AbstractProcessAction;

/**
 * Base class for actions that lists sth.
 * @author <a href="mailto:malczyk.pawel@gmail.com">malczyk.pawel@gmail.com</a>
 *
 * @param <T> type of listed entity.
 */
public abstract class AbstractListAction<T> extends AbstractProcessAction {
	
	/**
	 * Constant.
	 */
	public static final String LIST = "list";
	
	
	/**
	 * Serial.
	 */
	private static final long serialVersionUID = 1L;
	
	
	/** 
	 * Returns view.
	 * @return view. 
	 */
	public String doList() {
		return LIST;
	}
	
}
