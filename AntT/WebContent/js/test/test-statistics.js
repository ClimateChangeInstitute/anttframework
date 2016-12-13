(function() {

	QUnit.module("statistics", {
		setup : function() {
			/* Nothing to setup */
		},
		teardown : function() {
			/* Nothing to tear down */
			return moduleTeardown.apply(this, arguments);
		}
	});

	QUnit.test("Example Tests", function(assert) {
		
		assert.ok(1 == '1', "1 == '1' is ok");
		
		assert.notOk(1 === '1', "1 === '1' is notOk");
		
	});

})();