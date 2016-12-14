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

})();