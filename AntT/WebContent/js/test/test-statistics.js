(function() {

	QUnit.module("statistics", {
		beforeEach : function() {
			/* Nothing to setup */
		},
		afterEach : function() {
			/* Nothing to tear down */
		}
	});

	QUnit.test("Example Tests", function(assert) {

		assert.ok(1 == '1', "1 == '1' is ok");

		assert.notOk(1 === '1', "1 === '1' is notOk");

	});

	QUnit.test("Test weightingCoefficient()", function(assert) {

		assert.strictEqual(1.0, antt.statistics.weightingCoefficient(3, 0, 2,
				0, 0.33), "1.0 === weightingCoefficient(3, 0, 2, 0, 0.33)");

		assert.strictEqual(0.0, antt.statistics.weightingCoefficient(0.02,
				antt.statistics.calcStdDev(0.03, 23), 0.23, antt.statistics
						.calcStdDev(0.03, 23), 0.33), 
						"0.0 === weightingCoefficient(0.02, " +
						"calcStdDev(0.03, 23), 0.23, calcStdDev(0.03, 23), 0.33)");
		assert.strictEqual(0.0, antt.statistics.weightingCoefficient(54,
				antt.statistics.calcStdDev(20, 23), 0.23, antt.statistics
						.calcStdDev(0.03, 23), 0.33), "0.0 === weightingCoefficient(54, " +
								"calcStdDev(20, 23), 0.23, calcStdDev(0.03, 23), 0.33)");
	});
	
	QUnit.test("Test R()", function(assert) {

		assert.strictEqual(0.5, antt.statistics.R(1, 2), "0.5 === antt.statistics.R(1, 2)");
		assert.strictEqual(0.5, antt.statistics.R(2, 1), "0.5 === antt.statistics.R(2, 1)");

	});

	QUnit.test("Test RIllegalException()", function(assert) {

		// Invalid a value
		assert.throws(function(){antt.statistics.R(-1, 2);}, /Negative/, "R(-1, 2): Negative value not allowed");

		assert.throws(function(){antt.statistics.R(2, -1);}, /Negative/, "R(2, -1): Negative value not allowed");

	});

	QUnit.test("Test SimilarityCoefficientListList(number[], number[])", function(assert) {

		/** @Type {number[]} */
		var s1 = [1.0, 1.0, 1.0];
		/** @Type {number[]} */
		var s2 = [1.0, 2.0, 4.0];

		assert.strictEqual(1.0, antt.statistics.similarityCoefficientListList(s1, s1), "1.0 === similarityCoefficient(s1, s1)");

		assert.strictEqual(7.0 / 12.0, antt.statistics.similarityCoefficientListList(s1, s2), "7.0 / 12.0 === similarityCoefficient(s1, s2)");
	});
	
	
	QUnit.test("Test SimilarityCoefficientListListListListLimit(number[], number[], number[], number[], number", function(assert) {

		/** @Type {number[]} */
		var s1 = [1.0, 1.0, 1.0];
		/** @Type {number[]} */
		var s2 = [1.0, 2.0, 4.0];

		/** @Type {number[]} */
		var stderrsZero = Array(s1.length).fill(0.0);
		/** @Type {number[]} */
		var stderrsTenth = Array(s1.length).fill(0.1);

		assert.strictEqual(1.0, antt.statistics.similarityCoefficientListListListListLimit(s1, stderrsZero, s1, stderrsZero, 0.5), "1.0 === similarityCoefficientListListListListLimit(s1, stderrsZero, s1, stderrsZero, 0.5)");

		assert.strictEqual(7.0 / 12.0, antt.statistics.similarityCoefficientListListListListLimit(s1, stderrsZero, s2, stderrsZero, 0.5), "7.0 / 12.0 === similarityCoefficientListListListListLimit(s1, stderrsZero, s2, stderrsZero, 0.5)");

		assert.strictEqual(1.0, antt.statistics.similarityCoefficientListListListListLimit(s1, stderrsTenth, s1, stderrsTenth, 0.5), "1.0 === similarityCoefficientListListListListLimit(s1, stderrsTenth, s1, stderrsTenth, 0.5)");

		assert.strictEqual(0.5771054455663506, antt.statistics.similarityCoefficientListListListListLimit(s1, stderrsTenth, s2, stderrsTenth, 0.33), "0.5771054455663506 === similarityCoefficientListListListListLimit(s1, stderrsTenth, s2, stderrsTenth, 0.33)");

	});
	
	QUnit.test("Test SimilarityCoefficientMatrix(number[][], number[][], number)", function(assert) {

		/* From etc/data/borchardtINAAnoNaK.csv */
		/** @Type {number[][]}*/
		var samples = [
[163,6.5,54,18.4,35,15,4.4,0.04,0.34,4.2,1.8,0.48,19.6,7,57,3.6,2.2,257,0.23,0.51,2.57,1.8,1.05],
[166,6.7,49,16.7,33.4,19,4.3,0.03,0.61,3.9,1.7,0.46,18.3,7.2,68,3.4,2.1,259,0.07,0.47,2.51,1.5,0.71],
[169,6.9,51,16.8,34,20,4,0.03,0.44,4.1,1.4,0.49,19,7.1,76,3.6,2.1,249,0.02,0.46,2.56,1.1,1.06],
[162,6.5,51,17.7,33,16,4,0.04,0.39,3.2,1.6,0.45,18.6,6.8,91,3.4,2.1,238,0.07,0.46,2.42,0.8,1.1],
[147,6,49,22.4,38.9,21,4,0.05,0.63,3.5,1.5,0.44,17,6.5,8,3.3,1.9,244,0.15,0.52,2.33,1.2,0.94],
[161,6.5,105,21.4,40.7,20,3.9,0.04,0.61,3.2,1.5,0.45,18.5,6.3,95,3.7,2.1,244,0.12,0.52,2.48,1.9,1.12],
[153,5.4,68,18,31.3,8,3.9,0.03,0.37,3.7,1.5,0.45,17.9,6.9,88,3.3,2,244,0.08,0.44,2.44,2.1,0.97],
[158,6.5,130,24.8,40.6,22,3.9,0.08,0.61,3.3,1.6,0.42,18.1,6.2,74,3.6,1.8,241,0.22,0.53,2.38,1.2,0.97],
[145,5.5,106,27,47.3,15,3.6,0.1,0.4,3.1,1.3,0.39,16.3,5.8,75,3.2,1.7,213,0.45,0.54,2.21,1.9,0.89],
[286,11,119,10.9,23,21,7.1,0.2,1.66,9.2,4.7,1.27,14,15.9,63,4.6,5.4,834,0.3,0.33,9.09,3.8,0.52],
[267,10.3,204,12.1,23.5,20,6.7,0.17,0.97,9.4,4.7,1.18,13.7,14.2,88,4.5,5.2,831,0.42,0.32,8.69,2.9,0.37],
[213,5.3,179,81.4,139,65,12.5,0.46,1.73,12.3,8,1.28,30.3,6.7,177,7.6,3.6,232,0.37,0.96,1.4,3,0.23],
[250,5.9,293,74.1,132,62,12.3,0.4,2.23,11.5,6.5,1.33,32.7,7.5,187,7.9,3.7,213,0.35,0.95,1.46,2.8,0.35]
];
		
		/** @Type {number[][]}*/
		var stderr = [
[3,0.1,20,0.9,0.3,5,0.1,0.01,0.06,0.1,0.2,0.01,0.2,0.1,16,0.1,0.1,2,0.03,0.01,0.02,0.4,0.09],
[3,0.1,13,0.9,0.3,4,0.1,0.01,0.06,0.2,0.2,0.01,0.2,0.1,12,0.1,0.1,2,0.02,0.01,0.02,0.3,0.09],
[3,0.1,18,0.8,0.3,4,0.1,0.01,0.05,0.2,0.1,0.01,0.2,0.1,15,0.1,0.1,3,0.03,0.01,0.02,0.4,0.09],
[3,0.1,16,0.9,0.3,5,0.1,0.01,0.05,0.2,0.1,0.01,0.2,0.1,16,0.1,0.1,3,0.02,0.01,0.02,0.4,0.09],
[3,0.1,15,1,0.4,4,0.1,0.01,0.06,0.1,0.1,0.01,0.2,0.1,14,0.1,0.1,4,0.02,0.01,0.02,0.4,0.08],
[3,0.1,20,0.9,0.4,5,0.1,0.01,0.06,0.1,0.1,0.01,0.2,0.1,10,0.1,0.1,4,0.02,0.01,0.02,0.4,0.06],
[3,0.1,16,0.8,0.3,4,0.1,0.01,0.04,0.1,0.1,0.01,0.2,0.1,11,0.1,0.1,3,0.02,0.01,0.02,0.4,0.06],
[3,0.1,19,1,0.4,4,0.1,0.01,0.06,0.1,0.1,0.01,0.2,0.1,14,0.1,0.1,3,0.03,0.01,0.02,0.4,0.08],
[3,0.1,18,0.9,0.5,4,0.1,0.01,0.04,0.1,0.1,0.01,0.2,0.1,16,0.1,0.1,3,0.03,0.01,0.02,0.4,0.08],
[3,0.4,28,0.9,0.4,2,0.1,0.02,0.1,0.3,0.2,0.01,0.2,0.2,15,0.1,0.1,8,0.04,0.01,0.09,0.5,0.12],
[3,0.4,26,0.9,0.3,2,0.1,0.02,0.08,0.2,0.2,0.01,0.2,0.2,20,0.1,0.1,8,0.04,0.01,0.08,0.5,0.12],
[3,0.1,20,2,1.3,5,0.2,0.02,0.06,1.4,0.2,0.01,0.2,0.1,23,0.1,0.1,3,0.03,0.01,0.09,0.5,0.07],
[3,0.1,18,2,1.3,5,0.2,0.02,0.06,0.2,0.2,0.01,0.2,0.1,18,0.1,0.1,2,0.03,0.01,0.09,0.5,0.07]
];
	
		/** @Type {number[][]}*/
		var result = antt.statistics.similarityCoefficientMatrix(samples, stderr, 0.33);

		/* From etc/data/borchardtSimilarityCoefficients.csv */
		/** @Type {number[][]}*/
		var expectedResult = [
[1,,,,,,,,,,,,],
[0.93,1,,,,,,,,,,,],
[0.95,0.94,1,,,,,,,,,,],
[0.93,0.93,0.95,1,,,,,,,,,],
[0.88,0.9,0.89,0.91,1,,,,,,,,],
[0.9,0.9,0.91,0.94,0.94,1,,,,,,,],
[0.91,0.91,0.92,0.95,0.92,0.9,1,,,,,,],
[0.89,0.89,0.89,0.91,0.94,0.94,0.89,1,,,,,],
[0.8,0.81,0.83,0.84,0.87,0.85,0.85,0.88,1,,,,],
[0.52,0.53,0.53,0.5,0.49,0.49,0.5,0.5,0.46,1,,,],
[0.53,0.56,0.55,0.52,0.51,0.52,0.52,0.52,0.49,0.92,1,,],
[0.53,0.5,0.51,0.51,0.5,0.52,0.51,0.51,0.51,0.52,0.52,1,],
[0.52,0.5,0.5,0.49,0.49,0.49,0.49,0.49,0.49,0.54,0.53,0.91,1],
];
	
		for (var i = 0; i < expectedResult.length; i++) {
			for (var j = 0; j < i; j++) {
				assert.equal(result[i][j].toFixed(2), expectedResult[i][j]);
				// Symmetric case
				assert.equal(result[j][i].toFixed(2), expectedResult[i][j]);
			}
		}

	});
	
})();