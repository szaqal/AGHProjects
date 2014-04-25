package nsga.problems;

import nsga.core.ProblemVariables;
import nsga.core.Individual;

/**
 * Problem ZDT4
 */
public class ZDT4 extends Problem {

	/** Serial.  */
	private static final long serialVersionUID = -2284254828971901860L;

	/** Konstruktor. */
	public ZDT4() {
		this(10);
	}

	/** Konstruktor. */
	public ZDT4(Integer numberOfVariables) {
		n = numberOfVariables;
		numberOfResults = 2;

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

	/** {@inheritDoc} */
	public void evaluate(Individual individual) throws Exception {
		ProblemVariables poblemVariables = individual.getProblemVariables();

		double[] f = new double[numberOfResults];
		f[0] = poblemVariables.variables[0].getValue();
		double g = this.evalG(poblemVariables);
		double h = this.evalH(f[0], g);
		f[1] = h * g;

		individual.setResult(0, f[0]);
		individual.setResult(1, f[1]);
	}

	/**
	 * Zwraca wartosc funkcji g.
	 * 
	 * @param problemVariables
	 *            zmienne problemu
	 * @throws Exception
	 */
	public double evalG(ProblemVariables problemVariables) throws Exception {
		double g = 0.0;
		for (int var = 1; var < n; var++) {
			g += Math.pow(problemVariables.variables[var].getValue(), 2.0) + -10.0
				* Math.cos(4.0 * Math.PI * problemVariables.variables[var].getValue());
		}
		double constant = 1.0 + 10.0 * (n - 1);
		return g + constant;
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
