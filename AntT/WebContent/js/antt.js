/**
 * AntT namespace. Requires jQuery.
 */
(function(scope) {

	/**
	 * @param mme
	 *            {object} A JSON parsed allMMElements.xml MMElement entry value
	 * @returns {MMElementData} Fully typed MMEelementData object
	 */
	function MMElementData(mme) {
		this.element = mme.element;
		this.me = parseFloat(mme.me);
		this.std = parseFloat(mme.std);
		this.unit = mme.unit;
		this.value = parseFloat(mme.value);
	};

	/**
	 * @param e
	 *            {object} A JSON parsed allMMElements.xml MMElement
	 * @returns {MMEelement} A typed MMElement object
	 */
	function MMElement(e) {
		this.calculatedTotal = parseFloat(e.calculatedTotal);
		this.comments = e.comments;
		var parts = e.dateMeasured.split('-');
		this.dateMeasured = new Date(parts[0], parts[1], parts[2]);
		this.elementData = {};
		var that = this;
		if (e.elementData) {
			$(e.elementData.entry).each(function(i, e) {
				that.elementData[e.key] = new MMElementData(e.value);
			});
		}
		this.h2o_minus = parseFloat(e.h2o_minus);
		this.h2o_plus = parseFloat(e.h2o_plus);
		this.iid = e.iid;
		this.instrumentSettings = e.instrumentSettings;
		this.loi = parseFloat(e.loi);
		this.longsampleID = e.longsampleID;
		this.measuredBy = e.measuredBy;
		this.methodType = e.methodType.type;
		this.numberOfMeasurements = parseInt(e.numberOfMeasurements);
		this.originalTotal = parseFloat(e.originalTotal);
		this.sampleID = e.sampleID;
	};

	scope.statistics = {};
	
	/**
	 * @param xa
	 *            {number[]} Concentration of A elements
	 * @param xb
	 *            {number[]} Concentration of B elements
	 * @return {number} Similarity coefficient for comparison between sample A
	 *         and sample B
	 */
	var similarityCoefficientListList = function(xa, xb) {
		var stderr = Array(xa.length).fill(0.0);
		// Setting standard error to 0 for each element makes them be
		// considered equally. Detection limit is ignored.
		return similarityCoefficientListListListListLimit(xa, stderr, xb, stderr, 1 /*
																 * Doesn't
																 * matter
																 */);
	}
	scope.statistics.similarityCoefficientListList = similarityCoefficientListList; 

	/**
	 * Assumes errors are symmetrical and positive with 95% confidence.
	 * 
	 * @param xa
	 *            {number[]} Concentration of A elements
	 * @param stderra
	 *            {number[]} Standard error for A elements
	 * @param xb
	 *            {number[]} Concentration of B elements
	 * @param stderrb
	 *            {number[]} Standard error for B elements
	 * @param detectionLimit
	 *            {number} Typically 0.33
	 * @return {number} Similarity coefficient for comparison between sample A
	 *         and sample B
	 */
	var similarityCoefficientListListListListLimit = function(xa, stderra, xb, stderrb, detectionLimit) {

		// Calculate weighting coefficients
		/** @Type {number[]} */
		var g = [];
		var i = 0;
		for (i = 0; i < xa.length; i++) {
			g.push(weightingCoefficient(xa[i],
					calcStdDev(stderra[i], xa.length), xb[i], calcStdDev(
							stderrb[i], xb.length), detectionLimit));
		}

		var n = 0;
		for (i = 0; i < xa.length; i++) {
			n += R(xa[i], xb[i]) * g[i];
		}
		var d = g.reduce(function(x, y) {
			return x + y;
		}, 0.0);

		return n / d;
	}
	scope.statistics.similarityCoefficientListListListListLimit = similarityCoefficientListListListListLimit;

	/**
	 * Assumes confidence level of 99%
	 * 
	 * @param stderror
	 *            {number} Assume positive symmetrical
	 * @param N
	 *            {number} sample size
	 * @return {number} Standard deviation
	 */
	var calcStdDev = function(stderror, N) {
		// TODO 2.819 is for t table value 22 with 99% confidence only
		return stderror * Math.sqrt(N) / 2.819;
	}
	scope.statistics.calcStdDev = calcStdDev;

	/**
	 * The similarity matrix that results from this method is symmetric. Access
	 * result[i][j] to get the list of the ith sample similarity coefficient vs
	 * the jth sample.
	 * 
	 * @param samples
	 *            {number[][]} List of samples of elements
	 * @param stderr
	 *            {number[][]} The error for each sample's elements
	 * @param detectionLimit
	 *            {number} The detection limit (eg 0.33)
	 * @return {number[][]} The similarity coefficient matrix
	 */
	var similarityCoefficientMatrix = function(
			samples, stderr,
			detectionLimit) {
		var result = [];

		for (var i = 0; i < samples.length; i++) {
			var s1 = samples[i];
			var std1 = stderr[i];
			var nextResult = [];// samples.size());
			for (var j = 0; j < samples.length; j++) {
				var s2 = samples[j];
				var std2 = stderr[j];
				var sc = similarityCoefficientListListListListLimit(s1, std1, s2, std2,
						detectionLimit);

				nextResult.push(sc);
			}
			result.push(nextResult);
		}

		return result;
	}
	scope.statistics.similarityCoefficientMatrix = similarityCoefficientMatrix;

	/**
	 * Weighting coefficient between 0 and 1 which reflects precision.
	 * 
	 * @param xai
	 *            {number} Concentration of element i in A
	 * @param stderrai
	 *            {number} Standard error for xai
	 * @param xbi
	 *            {number} Concentration of element i in B
	 * @param stderrbi
	 *            {number} Standard error for xbi
	 * @param detectionLimit
	 *            {number} Typically 0.33
	 * @return {number} Weighting coefficient between 0 and 1
	 */
	var weightingCoefficient = function(xai, stderrai,
			xbi, stderrbi, detectionLimit) {
		var result = 1 - Math.sqrt(
				(Math.pow(stderrai / xai, 2) + Math.pow(stderrbi / xbi, 2))
						/ detectionLimit);

		if (result < 0) {
			result = 0;
		}

		return result;
	}
	scope.statistics.weightingCoefficient = weightingCoefficient;
	
	/**
	 * Create a ratio from a and b. The values of a and b can not both be 0
	 * since one will be the denominator. If a < 0 or b < 0 then this function
	 * will throw
	 * 
	 * @param a
	 *            {number} >= 0
	 * @param b
	 *            {number} >= 0
	 * @return {number} a/b iff a <= b, b/a otherwise
	 */
	var R = function(a, b) {
		if (a < 0 || b < 0)
			throw "Negative values are not allowed for a and b.";

		return a <= b ? a / b : b / a;
	}
	scope.statistics.R = R;

	var mmelements = [];
	
	/**
	 * @param callback
	 *            {function} Called after the AJAX get request completes
	 * @returns {undefined}
	 */
	scope.loadMMElements = function(callback) {
		$.ajax({
			type : "GET",
			url : "./generated/allMMElements.xml",
			dataType : "xml",
			success : function(xml) {

				var x2js = new X2JS();
				var json = x2js.xml2json(xml);
				$(json.mmelements.mmelement).each(function(i, e) {
					mmelements.push(new MMElement(e));
				});

				if (callback)
					callback();
			}
		});
	};

	/**
	 * @returns {MMElement[]} Currently loaded MMElement data
	 */
	scope.getMMElements = function() {
		return mmelements;
	};

	/**
	 * Filter MMElements based on the given array of keys. Make sure the
	 * MMElement data has already been loaded via antt.loadMMElements function.
	 * 
	 * @param keys
	 *            {string[]}
	 * @returns {MMElement[]} MMElements that contain every key in the given
	 *          array
	 */
	scope.filterMMElements = function(keys) {
		var result = [];

		$(mmelements).each(function(i, e) {
			var eKeys = Object.keys(e.elementData);

			for (var j = 0; j < keys.length; j++) {
				if ($.inArray(keys[j], eKeys) < 0)
					return;
			}
			result.push(e);
		});

		return result;
	};

})(window.antt = window.antt || {});
