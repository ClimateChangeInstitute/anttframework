/**
 * 
 */
package org.tephrochronology;

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

	public static double similarityCoefficient(List<Double> xa,
			List<Double> stda, List<Double> xb, List<Double> stdb) {
		return 0;
	}

	/**
	 * Weighting coefficient between 1 and 0 which reflects precision
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
	 * @return
	 */
	public static double weightingCoefficient(double xai, double stdai,
			double xbi, double stdbi, double detectionLimit) {
		return 1 - Math.sqrt((Math.pow(stdai / xai, 2) + Math.pow(stdbi / xbi, 2))
				/ detectionLimit);
	}
}
