package api.computation;

import java.util.ArrayList;

/**
 * Inteface for process aware classes.
 * @author malczyk.pawel@gmail.com
 *
 */
public interface Performable {
	
	/**
	 * Returns next task inputs for particular output.
	 * @param prevOutput revious output
	 * @return next intpu
	 */
	ArrayList<String> getNextInput(String prevOutput);
}
