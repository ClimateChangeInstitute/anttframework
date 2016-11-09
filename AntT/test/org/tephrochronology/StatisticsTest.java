/**
 * 
 */
package org.tephrochronology;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.supercsv.io.CsvListReader;
import org.supercsv.io.ICsvListReader;
import org.supercsv.prefs.CsvPreference;

/**
 * @author Mark Royer
 *
 */
public class StatisticsTest {

	/**
	 * Test method for
	 * {@link org.tephrochronology.Statistics#similarityCoefficient(java.util.List, java.util.List)}.
	 */
	@Test
	public void testSimilarityCoefficientListOfDoubleListOfDouble() {

		List<Double> s1 = Arrays.asList(1.0, 1.0, 1.0);
		List<Double> s2 = Arrays.asList(1.0, 2.0, 4.0);

		assertEquals(1.0, Statistics.similarityCoefficient(s1, s1), 0.001);

		assertEquals(7.0 / 12.0, Statistics.similarityCoefficient(s1, s2),
				0.001);
	}

	/**
	 * Test method for
	 * {@link org.tephrochronology.Statistics#similarityCoefficient(java.util.List, java.util.List, java.util.List, java.util.List)}.
	 */
	@Test
	public void testSimilarityCoefficientListOfDoubleListOfDoubleListOfDoubleListOfDouble() {

		List<Double> s1 = Arrays.asList(1.0, 1.0, 1.0);
		List<Double> s2 = Arrays.asList(1.0, 2.0, 4.0);

		List<Double> stderrsZero = Arrays.asList(0.0, 0.0, 0.0);
		List<Double> stderrsTenth = Arrays.asList(0.1, 0.1, 0.1);

		assertEquals(1.0, Statistics.similarityCoefficient(s1, stderrsZero, s1,
				stderrsZero, 0.5), 0.001);

		assertEquals(7.0 / 12.0, Statistics.similarityCoefficient(s1,
				stderrsZero, s2, stderrsZero, 0.5), 0.001);

		assertEquals(1.0, Statistics.similarityCoefficient(s1, stderrsTenth, s1,
				stderrsTenth, 0.5), 0.001);

		assertEquals(0.577, Statistics.similarityCoefficient(s1, stderrsTenth,
				s2, stderrsTenth, 0.33), 0.001);

	}

	/**
	 * Test method for
	 * {@link org.tephrochronology.Statistics#weightingCoefficient(double, double, double, double)}.
	 */
	@Test
	public void testWeightingCoefficient() {

		assertEquals(1.0, Statistics.weightingCoefficient(3, 0, 2, 0, 0.33),
				0.001);

		assertEquals(0.0,
				Statistics.weightingCoefficient(0.02,
						Statistics.calcStdDev(0.03, 23), 0.23,
						Statistics.calcStdDev(0.03, 23), 0.33),
				0.001);
		assertEquals(0.0,
				Statistics.weightingCoefficient(54,
						Statistics.calcStdDev(20, 23), 0.23,
						Statistics.calcStdDev(0.03, 23), 0.33),
				0.001);
	}

	/**
	 * Test method for
	 * {@link org.tephrochronology.Statistics#R(double, double)}.
	 */
	@Test
	public void testR() {

		assertEquals(0.5, Statistics.R(1, 2), 0.001);
		assertEquals(0.5, Statistics.R(2, 1), 0.001);

	}

	/**
	 * Test method for
	 * {@link org.tephrochronology.Statistics#R(double, double)}.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testRIllegalException() {

		// Invalid a value
		Statistics.R(-1, 2);

		Statistics.R(2, -1);

	}

	/**
	 * Test the similarity matrix function based on the weighted coefficient
	 * example from the Borchardt paper.
	 * 
	 * @throws IOException
	 */
	@Test
	public void testSimilarityCoefficientMatrix() throws IOException {

		ICsvListReader inputReader = new CsvListReader(
				new FileReader(new File("./etc/data/borchardtINAAnoNaK.csv")),
				CsvPreference.STANDARD_PREFERENCE);

		// Not used
		// String[] header =
		inputReader.getHeader(true);
		List<String> line = inputReader.read();
		List<List<Double>> samples = new ArrayList<>();
		List<List<Double>> stderr = new ArrayList<>();
		List<String> locales = new ArrayList<>();
		while (line != null) {
			List<Double> newSamples = new ArrayList<>();
			List<Double> newStderr = new ArrayList<>();
			locales.add(line.get(0));
			for (int i = 1; i < line.size() - 1; i += 2) {
				newSamples.add(Double.valueOf(line.get(i)));
				newStderr.add(line.get(i + 1) != null
						? Double.valueOf(line.get(i + 1)) : 0);
			}

			samples.add(newSamples);
			stderr.add(newStderr);
			line = inputReader.read();

		}

		List<List<Double>> result = Statistics
				.similarityCoefficientMatrix(samples, stderr, 0.33);

		ICsvListReader expectedReader = new CsvListReader(
				new FileReader(new File(
						"./etc/data/borchardtSimilarityCoefficients.csv")),
				CsvPreference.STANDARD_PREFERENCE);

		List<List<Double>> expectedResult = new ArrayList<>(result.size());

		// header =
		expectedReader.getHeader(true);
		line = expectedReader.read();
		while (line != null) {
			List<Double> newExpResult = new ArrayList<>();
			// First column is location number
			for (int i = 1; i < line.size(); i++) {
				newExpResult.add(line.get(i) != null
						? Double.valueOf(line.get(i)) : null);
			}
			expectedResult.add(newExpResult);
			line = expectedReader.read();
		}

		for (int i = 0; i < expectedResult.size(); i++) {
			for (int j = 0; j < i; j++) {
				// System.out.printf("%s=%s,%s ",
				// expectedResult.get(i).get(j).toString(),
				// String.format("%.2g", result.get(i).get(j)),
				// String.format("%.4g", result.get(i).get(j)));
				assertEquals(expectedResult.get(i).get(j), result.get(i).get(j),
						0.009);
				// Symmetric case
				assertEquals(expectedResult.get(i).get(j), result.get(j).get(i),
						0.009);
			}
			// System.out.println();
		}

		inputReader.close();
		expectedReader.close();
	}
}
