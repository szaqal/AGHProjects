package api.computation;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents computation configuration.
 * @author  <a href="mailto:malczyk.pawel@gmail.com>malczyk.pawel@gmail.com</a>
 */
public final class ComputationConfiguration {
	
	/**
	 * Contains list of computable tasks.
	 * @uml.property  name="computables"
	 */
	private List<ComputableTask> computables;
	
	/**
	 * Constructor.
	 */
	public ComputationConfiguration() {
		computables = new ArrayList<ComputableTask>();
	}

	/**
	 * Getter.
	 * @return  list of computable.
	 * @uml.property  name="computables"
	 */
	public List<ComputableTask> getComputables() {
		return computables;
	}
	

	/**
	 * Setter.
	 * @param computables  computable task list
	 * @uml.property  name="computables"
	 */
	public void setComputables(List<ComputableTask> computables) {
		this.computables = computables;
	}
	
}
