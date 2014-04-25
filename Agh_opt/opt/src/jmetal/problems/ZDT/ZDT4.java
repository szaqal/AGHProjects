package jmetal.problems.ZDT;

import jmetal.base.ProblemVariables;
import jmetal.base.Problem;
import jmetal.base.Solution;

/**
 * Klasa reprezentujace problem ZDT4
 */
public class ZDT4 extends Problem {

	/** Serial.  */
	private static final long serialVersionUID = -2284254828971901860L;

	/**
	 * Constructor. Creates a default instance of problem ZDT4 (10 decision
	 * variables)
	 * 
	 * @param representation
	 *            The solution type must "Real" or "BinaryReal".
	 */
	public ZDT4() {
		this(10); // 10 variables by default
	}

	/**
	 * Creates a instance of problem ZDT4.
	 * 
	 * @param numberOfVariables
	 *            Number of variables.
	 * @param solutionType
	 *            The solution type must "Real" or "BinaryReal".
	 */
	public ZDT4(Integer numberOfVariables) {
		n = numberOfVariables.intValue();
		numberOfObjectives_ = 2;

		xiMax = new double[n];
		xiMin = new double[n];

		//limit dla X1 [0,1]
		xiMin[0] = 0.0;
		xiMax[0] = 1.0;
		
		
		//Xi [-5,5]
		for (int var = 1; var < n; var++) {
			xiMin[var] = -5.0;
			xiMax[var] = 5.0;
		}

	}

	/**
	 * Evaluates a solution
	 * 
	 * @param solution
	 *            The solution to evaluate
	 * @throws Exception
	 */
	public void evaluate(Solution solution) throws Exception {
		ProblemVariables decisionVariables = solution.getProblemVariables();

		double[] f = new double[numberOfObjectives_];
		f[0] = decisionVariables.variables[0].getValue();
		double g = this.evalG(decisionVariables);
		double h = this.evalH(f[0], g);
		f[1] = h * g;

		solution.setObjective(0, f[0]);
		solution.setObjective(1, f[1]);
	} // evaluate

	/**
	 * Zwraca wartosc funkcji g.
	 * 
	 * @param decisionVariables
	 *            The decision variables of the solution to evaluate.
	 * @throws Exception
	 */
	public double evalG(ProblemVariables decisionVariables) throws Exception {
		double g = 0.0;
		for (int var = 1; var < n; var++) {
			g += Math.pow(decisionVariables.variables[var].getValue(), 2.0) + -10.0
				* Math.cos(4.0 * Math.PI * decisionVariables.variables[var].getValue());
		}
		double constante = 1.0 + 10.0 * (n - 1);
		return g + constante;
	} // evalG

	/**
	 * Zwraca wartosc funkcji h.
	 * 
	 * @param f
	 *            funkcja f.
	 * @param g
	 *            funkcja g.
	 */
	public double evalH(double f, double g) {
		return 1.0 - Math.sqrt(f / g);
	}
}
