package nsga.main;

import java.io.IOException;

import nsga.core.Population;
import nsga.problems.Problem;

/**
 * Glowna klasa.
 * @author malczyk.pawel@gmail.com
 *
 */
public class NSGAIImain {

	/** Main. */
	public static void main(String[] args) throws Exception, SecurityException, IOException {
		
		String problemName = args[0];
		System.out.println("Wybrany problem " + problemName);
		Class<?> clazz = Class.forName("nsga.problems."+problemName);
		Problem problem = (Problem)clazz.newInstance();

		System.out.println("Problem." + problem);
		NSGAII algorithm = new NSGAII(problem);
		Population population = algorithm.execute();
		population.printResultsToFile(problemName);

	}
}
