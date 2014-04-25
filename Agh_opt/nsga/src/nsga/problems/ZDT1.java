package nsga.problems;

import nsga.core.ProblemVariables;
import nsga.core.Individual;

/** Problem ZDT1 */
public class ZDT1 extends Problem {

	/**  Serial. */
	private static final long serialVersionUID = 6680347915034255781L;

	/** Konstruktor. */
	public ZDT1() {
		this(30); 
	} 

	/** Konstruktor. */
	public ZDT1(Integer numberOfVariables) {
		n = numberOfVariables;
		numberOfResults = 2;

		xiMax = new double[n];
		xiMin = new double[n];

		// Stablishes upper and lower limits for the variables
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

	/** Oblicza funkacje g */
	private double evalG(ProblemVariables problemVariables)
			throws Exception {
		double g = 0.0;
		for (int i = 1; i < problemVariables.size(); i++)
			g += problemVariables.variables[i].getValue();
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
