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

	QUnit.test("Test SimilarityCoefficientListList(Number[], Number[])", function(assert) {

		/** @Type {number[]} */
		var s1 = [1.0, 1.0, 1.0];
		/** @Type {number[]} */
		var s2 = [1.0, 2.0, 4.0];

		assert.strictEqual(1.0, antt.statistics.similarityCoefficientListList(s1, s1), "1.0 === similarityCoefficient(s1, s1)");

		assert.strictEqual(7.0 / 12.0, antt.statistics.similarityCoefficientListList(s1, s2), "7.0 / 12.0 === similarityCoefficient(s1, s2)");
	});
	
	
	QUnit.test("Test SimilarityCoefficientListListListListLimit(Number[], Number[], Number[], Number[], Number", function(assert) {

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
	
})();