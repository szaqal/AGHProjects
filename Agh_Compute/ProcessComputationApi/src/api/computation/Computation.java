package api.computation;

import java.io.Serializable;


/**
 * Represents computation as a whole process.
 * 
 * @author <a href="mailto:malczyk.pawel@gmail.com>malczyk.pawel@gmail.com</a>
 *
 */
public interface Computation {
	
	/**
	 * End computation marker.
	 */
	String END_COMPUTATION = "end";
	
	/**
	 * Performs Computation.
	 * @return final result of computation.
	 */
	ComputationResult<? extends Serializable> performComptation();
}
