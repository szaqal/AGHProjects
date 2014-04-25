package nsga.problems;

import nsga.core.ProblemVariables;
import nsga.core.Individual;

/**
 * Klasa reprezentuje problem ZDT3 (n=30!)
 */
public class ZDT3 extends Problem {

	/** Serial. */
	private static final long serialVersionUID = 7388792456613451502L;

	/** Konstruktor. */
	public ZDT3() {
		this(30); 
	} 

	/** Konstruktor. */
	public ZDT3(Integer numberOfVariables) {
		n = numberOfVariables;
		numberOfResults = 2;

		xiMax = new double[n];
		xiMin = new double[n];

		// Xi [0,1]
		for (int var = 0; var < n; var++) {
			xiMin[var] = 0.0;
			xiMax[var] = 1.0;
		} 

	} 

	/** {@inheritDoc} */
	public void evaluate(Individual individual) throws Exception {
		ProblemVariables problemVariables = individual.getProblemVariables();

		double[] f = new double[numberOfResults];
		f[0] = problemVariables.variables[0].getValue();
		double g = this.evalG(problemVariables);
		double h = this.evalH(f[0], g);
		f[1] = h * g;

		individual.setResult(0, f[0]);
		individual.setResult(1, f[1]);
	} 

	/** Oblicza funkcje g. */
	private double evalG(ProblemVariables problemVariables)
			throws Exception {
		double g = 0.0;
		for (int i = 1; i < problemVariables.size(); i++)
			g += problemVariables.variables[i].getValue();
		double constant = (9.0 / (n - 1));
		g = constant * g;
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
