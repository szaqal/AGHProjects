package nsga.problems;

import nsga.core.ProblemVariables;
import nsga.core.Individual;

/**
 * Problem ZDT2
 */
public class ZDT2 extends Problem {

	/** Serial. */
	private static final long serialVersionUID = -6572519015153296542L;

	/** Konstruktor. */
	public ZDT2() {
		this(30); 
	}

	/** Konstruktor. */
	public ZDT2(Integer numberOfVariables) {
		n = numberOfVariables;
		numberOfResults = 2;

		xiMax = new double[n];
		xiMin = new double[n];

		for (int var = 0; var < n; var++) {
			xiMin[var] = 0.0;
			xiMax[var] = 1.0;
		} 

	} 

	/** {@inheritDoc} */
	public void evaluate(Individual indiviual) throws Exception {
		ProblemVariables problemVariables = indiviual.getProblemVariables();

		double[] f = new double[numberOfResults];
		f[0] = problemVariables.variables[0].getValue();
		double g = this.evalG(problemVariables);
		double h = this.evalH(f[0], g);
		f[1] = h * g;

		indiviual.setResult(0, f[0]);
		indiviual.setResult(1, f[1]);
	} 

	/**Oblicza funkcje b*/
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

	/**Oblicza funkcje h*/
	public double evalH(double f, double g) {
		double h = 0.0;
		h = 1.0 - java.lang.Math.pow(f / g, 2.0);
		return h;
	} 
}
