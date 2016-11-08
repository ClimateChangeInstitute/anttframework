/**
 * 
 */
package org.tephrochronology;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.supercsv.io.CsvListReader;
import org.supercsv.io.ICsvListReader;
import org.supercsv.prefs.CsvPreference;

/**
 * @author Mark Royer
 *
 */
public class StatisticsTest {

	@Before
	public void setUp() throws Exception {
		ICsvListReader reader = new CsvListReader(
				new FileReader(new File("./etc/data/borchardtINAA.csv")),
				CsvPreference.STANDARD_PREFERENCE);

		String[] header = reader.getHeader(true);
		List<String> line = reader.read();
		List<List<Double>> samples = new ArrayList<>();
		List<List<Double>> sampleStds = new ArrayList<>();
		while (line != null) {
			List<Double> newSamples = new ArrayList<>();
			List<Double> newSampleStds = new ArrayList<>();
			for (int i = 1; i < line.size() - 1; i += 2) {
				newSamples.add(Double.valueOf(line.get(i)));
				newSampleStds.add(line.get(i + 1) != null
						? Double.valueOf(line.get(i + 1)) : null);
			}
			samples.add(newSamples);
			sampleStds.add(newSampleStds);
			line = reader.read();
		}

	}

	/**
	 * Test method for
	 * {@link org.tephrochronology.Statistics#similarityCoefficient(java.util.List, java.util.List)}.
	 */
	@Test
	public void testSimilarityCoefficientListOfDoubleListOfDouble() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link org.tephrochronology.Statistics#similarityCoefficient(java.util.List, java.util.List, java.util.List, java.util.List)}.
	 */
	@Test
	public void testSimilarityCoefficientListOfDoubleListOfDoubleListOfDoubleListOfDouble() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link org.tephrochronology.Statistics#weightingCoefficient(double, double, double, double)}.
	 */
	@Test
	public void testWeightingCoefficient() {

		assertEquals(1.0, Statistics.weightingCoefficient(3, 0, 2, 0, 0.33),
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
}
