/**
 * 
 */
package org.tephrochronology;

import static org.junit.Assert.*;

import org.junit.Test;

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

}
