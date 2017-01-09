(function() {

	QUnit.assert.close = function(actual, expected, maxDifference, message) {
		var passes = (actual === expected)
				|| Math.abs(actual - expected) <= maxDifference;
		this.push(passes, actual, expected, message);
	}

	var mmelements = [];

	function createMMED(i) {

		var result = [];

		result.push({
			symbol : {
				symbol : 'Ca',
				name : "Calcium",
				format : 'Ca',
				molecularMass : 40.08,
				atomicNumber : 20
			},
			me : '' + i,
			std : '1' + i,
			unit : 'ppb',
			value : '' + i * 10
		});

		result.push({
			symbol : {
				symbol : 'K',
				name : "Potassium",
				format : 'K',
				molecularMass : 39.1,
				atomicNumber : 19
			},
			me : '' + i,
			std : '1' + i,
			unit : 'ppb',
			value : '' + i * 10
		});

		return result;
	}
	;

	function createMMElementData() {

		var result = [];
		var d = new Date();
		d = d.getFullYear() + '-' + (d.getMonth() + 1) + '-' + d.getDate();
		for (var i = 0; i < 10; i++) {
			result.push(new antt.MMElement({
				calculatedTotal : i,
				comments : 'comment ' + i,
				dateMeasured : d,
				elementData : createMMED(i),
				iid : 'iid ' + i,
				instrumentSettings : 'instrumentSettings ' + i,
				longsampleID : 'longsampleID ' + i,
				measuredBy : 'measuredBy ' + i,
				methodType : 'methodType ' + i,
				numberOfMeasurements : i,
				originalTotal : i * 10,
				sampleID : '' + i
			}));
		}

		return result;
	}

	QUnit.module("mmelements", {
		beforeEach : function() {
			mmelements = createMMElementData();
		},
		afterEach : function() {
			mmelements = null;
		}

	});

	QUnit.test("Test loadMMElements(string, function)", function(assert) {

		var done = assert.async();
		antt.loadMMElements("../../generated/allMMElements.xml", function(mms) {
			assert.ok(mms !== null, "result !== null");
			assert.ok(mms[1].elementData[0].unit, "Element unit data exists.");
			done();
		});

	});

	QUnit.test("Test filterMMElements(string[], MMElement[])", function(assert) {

		var result = antt.filterMMElements([ 'not in' ], mmelements);

		assert.ok(result !== null, "result !== null, empty result");

		assert.ok(result.length === 0, "result.length === 0, empty result");

		result = antt.filterMMElements([ 'ca' ], mmelements);

		assert.ok(result !== null, "result !== null");

	});

})();