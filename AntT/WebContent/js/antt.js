/**
 * AntT namespace.  Requires jQuery.
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
		$(e.elementData.entry).each(function(i, e) {
			that.elementData[e.key] = new MMElementData(e.value);
		});
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
	
})(window.antt = window.antt || {});