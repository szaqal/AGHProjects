package jmetal.base;

import java.io.Serializable;
import jmetal.base.variable.Real;

/**
 * Przechowuje n zmiennych rzeczywistych o odpowiednio ograniczonych<br>
 * co do wartosci patrz (xi w problemach ZDT)
 */
public class ProblemVariables implements Serializable {
	
	/** Serial. */
	private static final long serialVersionUID = 4466133386183087847L;

	/** Zmienne */
	public Real[] variables;

	/** Rozwiazywany problem. */
	private Problem problem;

	/** Konstruktor. */
	public ProblemVariables(Problem probl) {
		problem = probl;
		variables = new Real[problem.getN()];

		for (int var = 0; var < problem.getN(); var++) {
			variables[var] = new Real(problem.getLowerLimit(var), problem.getUpperLimit(var));
		}
	}

	/** Konstruktor. */
	public ProblemVariables(ProblemVariables problemVariables) {
		problem = problemVariables.problem;
		variables = new Real[problemVariables.variables.length];
		for (int var = 0; var < problemVariables.variables.length; var++) {
			variables[var] = problemVariables.variables[var].deepCopy();
		}
	} 

	/**Getter. */
	public int size() {
		return problem.getN();
	} 

	/** {@inheritDoc} */
	public String toString() {
		String aux = "";
		for (int i = 0; i < variables.length; i++) {
			aux += " " + variables[i].toString();
		}
		return aux;
	}
} 
