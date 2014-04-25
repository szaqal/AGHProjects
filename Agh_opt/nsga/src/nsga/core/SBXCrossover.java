package nsga.core;

import nsga.utils.RandomUtil;

/**
 * krzyżowanie SBX: 
 * tworzy dwie nowe jednostki poprzez wymieszanie informacji z dwóch jednostek rodzicielskich.
 * 
 * bazuje na wzorach m. in. z:
 * @see http://www.complexity.org.au/conference/upload/raghuw01/raghuw01.pdf
 * 
 */
public class SBXCrossover {

	/** Serial. */
	private static final long serialVersionUID = 485761542428572217L;

	/** DEFAULT_INDEX_CROSSOVER defines a default index crossover */
	public static final double DEFAULT_INDEX_CROSSOVER = 20.0;

	/** Minimalna roznica pomiedzy wartosciami zeby moglo zaistniec krzyzowanie. */
	private static final double EPS = 1.0e-14;

	/**
	 * Indek uzwany w procesie krzyzowania.
	 */
	private double eta_c;


	/**Konstruktor. */
	public SBXCrossover() {
		eta_c = DEFAULT_INDEX_CROSSOVER;
	}

	/** Konstruktor */
	public SBXCrossover(double indexCrossover) {
		eta_c = indexCrossover;
	}

	/**
	 * Przeprowadza krzyzowaie.
	 * 
	 * @param prawdopodobienstwo krzyzowania
	 *           prawdopodobienstwo krzyzowania
	 * @param parent1
	 *            pierwszy rodzic
	 * @param parent2
	 *            drugi rodzic
	 * @return tablica zawierajaca dwoch potomkow
	 */
	public Individual[] doCrossover(double probability, Individual parent1, Individual parent2) throws Exception {

		Individual[] offSpring = new Individual[2];

		offSpring[0] = new Individual(parent1);
		offSpring[1] = new Individual(parent2);

		int i;
		double rand;
		double y1, y2, yL, yu;
		double c1, c2;
		double alpha, beta, betaq;
		double valueX1, valueX2;
		if (RandomUtil.randDouble() <= probability) {
			
			for (i = 0; i < parent1.getProblemVariables().size(); i++) {
				valueX1 = parent1.getProblemVariables().variables[i].getValue();
				valueX2 = parent2.getProblemVariables().variables[i].getValue();
				
				if (RandomUtil.randDouble() <= 0.5) {

					if (java.lang.Math.abs(valueX1 - valueX2) > EPS) {

						if (valueX1 < valueX2) {
							y1 = valueX1;
							y2 = valueX2;
						} else {
							y1 = valueX2;
							y2 = valueX1;
						} 

						yL = parent1.getProblemVariables().variables[i].getLowerBound();
						yu = parent1.getProblemVariables().variables[i].getUpperBound();
						rand = RandomUtil.randDouble();
						beta = 1.0 + (2.0 * (y1 - yL) / (y2 - y1));
						alpha = 2.0 - java.lang.Math.pow(beta, -(eta_c + 1.0));

						if (rand <= (1.0 / alpha)) {
							betaq = java.lang.Math.pow((rand * alpha), (1.0 / (eta_c + 1.0)));
						} else {
							betaq = java.lang.Math.pow((1.0 / (2.0 - rand * alpha)), (1.0 / (eta_c + 1.0)));
						} // if

						c1 = 0.5 * ((y1 + y2) - betaq * (y2 - y1));
						beta = 1.0 + (2.0 * (yu - y2) / (y2 - y1));
						alpha = 2.0 - java.lang.Math.pow(beta, -(eta_c + 1.0));

						if (rand <= (1.0 / alpha)) {
							betaq = java.lang.Math.pow((rand * alpha), (1.0 / (eta_c + 1.0)));
						} else {
							betaq = java.lang.Math.pow((1.0 / (2.0 - rand * alpha)), (1.0 / (eta_c + 1.0)));
						} // if

						c2 = 0.5 * ((y1 + y2) + betaq * (y2 - y1));

						//zabezpieczenie dla prawidlowych wartosci
						if (c1 < yL)
							c1 = yL;

						if (c2 < yL)
							c2 = yL;

						if (c1 > yu)
							c1 = yu;

						if (c2 > yu)
							c2 = yu;

						if (RandomUtil.randDouble() <= 0.5) {
							offSpring[0].getProblemVariables().variables[i].setValue(c2);
							offSpring[1].getProblemVariables().variables[i].setValue(c1);
						} else {
							offSpring[0].getProblemVariables().variables[i].setValue(c1);
							offSpring[1].getProblemVariables().variables[i].setValue(c2);
						} // if
					} else {
						offSpring[0].getProblemVariables().variables[i].setValue(valueX1);
						offSpring[1].getProblemVariables().variables[i].setValue(valueX2);
					} // if
				} else {
					offSpring[0].getProblemVariables().variables[i].setValue(valueX2);
					offSpring[1].getProblemVariables().variables[i].setValue(valueX1);
				}
			}
		}

		
		for (i = 0; i < offSpring.length; i++) {
			offSpring[i].setCrowdingDistance(0.0);
			offSpring[i].setRank(0);
		}
		
		return offSpring;
	} 
} 
