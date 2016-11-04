/**
 * 
 */
package org.tephrochronology;

import java.util.ArrayList;
import java.util.List;

/**
 * A collection of statistical routines for the project.
 * 
 * @author Mark Royer
 *
 */
public class Statistics {

	public static double similarityCoefficient(List<Double> xa,
			List<Double> xb) {
		return 0;
	}

	/**
	 * @param xa
	 *            Concentration of A elements
	 * @param stda
	 *            Standard deviations for A elements
	 * @param xb
	 *            Concentration of B elements
	 * @param stdb
	 *            Standard deviations for B elements
	 * @param detectionLimit
	 *            Typically 0.33
	 * @return Similarity coefficient for comparison between sample A and sample
	 *         B
	 */
	public static double similarityCoefficient(List<Double> xa,
			List<Double> stda, List<Double> xb, List<Double> stdb,
			double detectionLimit) {

		// Calculate weighting coefficients
		List<Double> g = new ArrayList<>();
		for (int i = 0; i < xa.size(); i++) {
			g.add(weightingCoefficient(xa.get(i), stda.get(i), xb.get(i),
					stdb.get(i), detectionLimit));
		}

		double n = 0;
		for (int i = 0; i < xa.size(); i++) {
			n += R(xa.get(i), xb.get(i)) * g.get(i);
		}
		double d = g.stream().mapToDouble(Double::doubleValue).sum();

		return n / d;
	}

	/**
	 * Weighting coefficient between 0 and 1 which reflects precision
	 * 
	 * @param xai
	 *            Concentration of element i in A
	 * @param stdai
	 *            One standard deviation for xai
	 * @param xbi
	 *            Concentration of element i in B
	 * @param stdbi
	 *            One standard deviation for xbi
	 * @param detectionLimit
	 *            Typically 0.33
	 * @return Weighting coefficient between 0 and 1
	 */
	public static double weightingCoefficient(double xai, double stdai,
			double xbi, double stdbi, double detectionLimit) {
		return 1 - Math
				.sqrt((Math.pow(stdai / xai, 2) + Math.pow(stdbi / xbi, 2))
						/ detectionLimit);
	}

	/**
	 * Create a ratio from a and b. The values of a and b can not both be 0
	 * since one will be the denominator. If a < 0 or b < 0 then this function
	 * will throw
	 * 
	 * @param a
	 *            >= 0
	 * @param b
	 *            >= 0
	 * @return a/b iff a <= b, b/a otherwise
	 */
	public static double R(double a, double b) {
		if (a < 0 || b < 0)
			throw new IllegalArgumentException(String
					.format("Negative values are not allowed for a and b.  "
							+ "Received a = %f and b = %f", a, b));

		return a <= b ? a / b : b / a;
	}
}
