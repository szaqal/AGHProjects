package jmetal.util;

import java.util.Random;

/**
 * Klasa wspomagajaca generowanie liczb pseudolosowych
 */
public final class RandomUtil {

	private static Random random;

	/**
	 * Generuje double'a
	 */
	public static double randDouble() {
		return random.nextDouble();
	}

	/** Zwraca int'a z przedzalu od 0..n */
	public static int randInt(int maxBound) {
		return random.nextInt(maxBound);
	}
	
	static {
		random = new Random(System.currentTimeMillis());
	}
	
} 
