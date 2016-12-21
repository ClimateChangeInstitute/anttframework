(function() {

	QUnit.assert.close = function(actual, expected, maxDifference, message) {
		var passes = (actual === expected)
				|| Math.abs(actual - expected) <= maxDifference;
		this.push(passes, actual, expected, message);
	}

	QUnit.module("mmelements", {
		beforeEach : function() {
			/* Nothing to setup */
		},
		afterEach : function() {
			/* Nothing to tear down */
		}
	});

	QUnit.test("Test loadMMElements()", function(assert) {
		var done = assert.async();
		var result;
		antt.loadMMElements("../../generated/allMMElements.xml", function(elements) {
			result = elements;
			done();
		})
		
		assert.ok(result !== null, "result !== null");

	});

})();