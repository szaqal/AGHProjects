package jmetal.metaheuristics.nsgaII;

import java.io.IOException;

import jmetal.base.Problem;
import jmetal.base.SolutionSet;
import jmetal.problems.ZDT.ZDT3;

public class NSGAII_main {

	/** Main. */
	public static void main(String[] args) throws Exception, SecurityException, IOException {

		Problem problem = new ZDT3(10);

		System.out.println("Problem." + problem);
		NSGAII algorithm = new NSGAII(problem);

		SolutionSet population = algorithm.execute();

		population.printObjectivesToFile("FUN");

	}
}
