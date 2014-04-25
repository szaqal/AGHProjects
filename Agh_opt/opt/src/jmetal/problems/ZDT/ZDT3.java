package jmetal.problems.ZDT;

import jmetal.base.ProblemVariables;
import jmetal.base.Problem;
import jmetal.base.Solution;

/**
 * Klasa reprezentuje problem ZDT3 (n=30!)
 */
public class ZDT3 extends Problem {

	/** Serial. */
	private static final long serialVersionUID = 7388792456613451502L;

	/** Konstruktor. */
	public ZDT3() {
		this(30); // 30 variables by default
	} 

	/** Konstruktor. */
	public ZDT3(Integer numberOfVariables) {
		n = numberOfVariables.intValue();
		numberOfObjectives_ = 2;

		xiMax = new double[n];
		xiMin = new double[n];

		// Xi [0,1]
		for (int var = 0; var < n; var++) {
			xiMin[var] = 0.0;
			xiMax[var] = 1.0;
		} // for

	} 

	/** Olicza problem */
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

	/** Oblicza funkcje g. */
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

	/** Oblicza funkcje h. */
	public double evalH(double f, double g) {
		double h = 0.0;
		h = 1.0 - java.lang.Math.sqrt(f / g) - (f / g)
				* java.lang.Math.sin(10.0 * java.lang.Math.PI * f);
		return h;
	} 
}
