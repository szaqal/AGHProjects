package nsga.core;

import nsga.utils.RandomUtil;

/**
 * Mutacja.
 * 
 * Obliczane na podstawie Wzorow  m. in. z
 * @see http://www.complexity.org.au/conference/upload/raghuw01/raghuw01.pdf
 */
public class PolynomialMutation {

	/* *Serial. */
	private static final long serialVersionUID = -4694450476401572161L;

	/**
	 * DEFAULT_INDEX_MUTATION defines a default index for mutation
	 */
	public static final double DEFAULT_INDEX_MUTATION = 20.0;

	/** Indeks wykorzystywanu w obliczaniu mutacji. */
	private double eta_m;

	/** Konstruktor. */
	public PolynomialMutation() {
		eta_m = DEFAULT_INDEX_MUTATION;
	} 

	/** Konstruktor. */
	public PolynomialMutation(double eta) {
		eta_m = eta;
	}

	/**
	 * Przperowadza mutacje.
	 */
	public void doMutation(double probability, Individual individual)
			throws Exception {
		double rnd, delta1, delta2, mut_pow, deltaq;
		double y, yl, yu, val, xy;
		for (int var = 0; var < individual.getProblemVariables().size(); var++) {
			if (RandomUtil.randDouble() <= probability) {
				y = individual.getProblemVariables().variables[var].getValue();
				yl = individual.getProblemVariables().variables[var].getLowerBound();
				yu = individual.getProblemVariables().variables[var].getUpperBound();
				delta1 = (y - yl) / (yu - yl);
				delta2 = (yu - y) / (yu - yl);
				rnd = RandomUtil.randDouble();
				mut_pow = 1.0 / (eta_m + 1.0);
				if (rnd <= 0.5) {
					xy = 1.0 - delta1;
					val = 2.0 * rnd + (1.0 - 2.0 * rnd) * (Math.pow(xy, (eta_m + 1.0)));
					deltaq = java.lang.Math.pow(val, mut_pow) - 1.0;
				} else {
					xy = 1.0 - delta2;
					val = 2.0 * (1.0 - rnd) + 2.0 * (rnd - 0.5)* (java.lang.Math.pow(xy, (eta_m + 1.0)));
					deltaq = 1.0 - (java.lang.Math.pow(val, mut_pow));
				}
				
				//wlasciwy wzor obliczenia mutacji wielomianowej
				y = y +  (yu - yl) * deltaq;
				
				//zeby miescilo sie w zadanych granicach
				if (y < yl)
					y = yl;
				if (y > yu)
					y = yu;
				individual.getProblemVariables().variables[var].setValue(y);
			}
		}
	} 

}
