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
	
})(window.antt = window.antt || {});
