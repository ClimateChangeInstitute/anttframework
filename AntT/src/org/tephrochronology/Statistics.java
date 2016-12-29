/**
 * 
 */
package org.tephrochronology;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A collection of statistical routines for the project.
 * 
 * @author Mark Royer
 *
 */
public class Statistics {

	/**
	 * @param xa
	 *            Concentration of A elements
	 * @param xb
	 *            Concentration of B elements
	 * @return Similarity coefficient for comparison between sample A and sample
	 *         B
	 */
	public static double similarityCoefficient(List<Double> xa,
			List<Double> xb) {
		List<Double> stderr = xa.stream().map(f -> 0.0)
				.collect(Collectors.toList());
		// Setting standard error to 0 for each element makes them be
		// considered equally. Detection limit is ignored.
		return similarityCoefficient(xa, stderr, xb, stderr,
				1 /* Doesn't matter */);
	}

	/**
	 * Assumes errors are symmetrical and positive with 95% confidence.
	 * 
	 * @param xa
	 *            Concentration of A elements
	 * @param stderra
	 *            Standard error for A elements
	 * @param xb
	 *            Concentration of B elements
	 * @param stderrb
	 *            Standard error for B elements
	 * @param detectionLimit
	 *            Typically 0.33
	 * @return Similarity coefficient for comparison between sample A and sample
	 *         B
	 */
	public static double similarityCoefficient(List<Double> xa,
			List<Double> stderra, List<Double> xb, List<Double> stderrb,
			double detectionLimit) {

		// Calculate weighting coefficients
		List<Double> g = new ArrayList<>();
		for (int i = 0; i < xa.size(); i++) {
			g.add(weightingCoefficient(xa.get(i),
					calcStdDev(stderra.get(i), xa.size()), xb.get(i),
					calcStdDev(stderrb.get(i), xb.size()), detectionLimit));
		}

		double n = 0;
		for (int i = 0; i < xa.size(); i++) {
			n += R(xa.get(i), xb.get(i)) * g.get(i);
		}
		double d = g.stream().mapToDouble(Double::doubleValue).sum();

		return n / d;
	}

	/**
	 * Assumes confidence level of 99%
	 * 
	 * @param stderror
	 *            Assume positive symmetrical
	 * @param N
	 *            sample size
	 * @return Standard deviation
	 */
	public static double calcStdDev(double stderror, int N) {
		// 2.819 is for t table value 22 with 99% confidence only
		return stderror * Math.sqrt(N) / 2.819;
	}

	/**
	 * The similarity matrix that results from this method is symmetric. Access
	 * result.get(i).get(j) to get the list of the ith sample similarity
	 * coefficient vs the jth sample.
	 * 
	 * @param samples
	 *            List of samples of elements
	 * @param stderr
	 *            The error for each sample's elements
	 * @param detectionLimit
	 *            The detection limit (eg 0.33)
	 * @return The similarity coefficient matrix
	 */
	public static List<List<Double>> similarityCoefficientMatrix(
			List<List<Double>> samples, List<List<Double>> stderr,
			double detectionLimit) {
		List<List<Double>> result = new ArrayList<>();

		for (int i = 0; i < samples.size(); i++) {
			List<Double> s1 = samples.get(i);
			List<Double> std1 = stderr.get(i);
			List<Double> nextResult = new ArrayList<>(samples.size());
			for (int j = 0; j < samples.size(); j++) {
				List<Double> s2 = samples.get(j);
				List<Double> std2 = stderr.get(j);
				double sc = Statistics.similarityCoefficient(s1, std1, s2, std2,
						detectionLimit);

				nextResult.add(sc);
			}
			result.add(nextResult);
		}

		return result;
	}

	/**
	 * Weighting coefficient between 0 and 1 which reflects precision.
	 * 
	 * @param xai
	 *            Concentration of element i in A
	 * @param stderrai
	 *            Standard error for xai
	 * @param xbi
	 *            Concentration of element i in B
	 * @param stderrbi
	 *            Standard error for xbi
	 * @param detectionLimit
	 *            Typically 0.33
	 * @return Weighting coefficient between 0 and 1
	 */
	public static double weightingCoefficient(double xai, double stderrai,
			double xbi, double stderrbi, double detectionLimit) {
		double result = 1 - Math.sqrt(
				(Math.pow(stderrai / xai, 2) + Math.pow(stderrbi / xbi, 2))
						/ detectionLimit);

		if (result < 0) {
			result = 0;
		}

		return result;
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
