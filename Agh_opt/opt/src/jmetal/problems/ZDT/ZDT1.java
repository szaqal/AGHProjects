package jmetal.problems.ZDT;

import jmetal.base.ProblemVariables;
import jmetal.base.Problem;
import jmetal.base.Solution;

/** Problem ZDT1 */
public class ZDT1 extends Problem {

	/**  Serial. */
	private static final long serialVersionUID = 6680347915034255781L;

	/** Konstruktor. */
	public ZDT1() {
		this(30); // 30 variables by default
	} 

	/** Konstruktor. */
	public ZDT1(Integer numberOfVariables) {
		n = numberOfVariables;
		numberOfObjectives_ = 2;

		xiMax = new double[n];
		xiMin = new double[n];

		// Stablishes upper and lower limits for the variables
		for (int var = 0; var < n; var++) {
			xiMin[var] = 0.0;
			xiMax[var] = 1.0;
		}

	} 

	/** Przprowadza obliczenia */
	public void evaluate(Solution solution) throws Exception {
		ProblemVariables decisionVariables = solution.getProblemVariables();

		double[] f = new double[numberOfObjectives_];
		f[0] = decisionVariables.variables[0].getValue();
		double g = this.evalG(decisionVariables);
		double h = this.evalH(f[0], g);
		f[1] = h * g;

		solution.setObjective(0, f[0]);
		solution.setObjective(1, f[1]);
	}

	/** Oblicza funkacje g */
	private double evalG(ProblemVariables decisionVariables)
			throws Exception {
		double g = 0.0;
		for (int i = 1; i < decisionVariables.size(); i++)
			g += decisionVariables.variables[i].getValue();
		double constante = (9.0 / (n - 1));
		g = constante * g;
		g = g + 1.0;
		return g;
	} 

	/** Oblicza funkacje h */
	public double evalH(double f, double g) {
		double h = 0.0;
		h = 1.0 - java.lang.Math.sqrt(f / g);
		return h;
	} 
} 
