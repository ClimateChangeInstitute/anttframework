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
		this.me = parseFloat(mme.me);
		this.std = parseFloat(mme.std);
		this.unit = mme.unit;
		this.value = parseFloat(mme.value);
		// chemistry information
		this.symbol = mme.symbol.symbol;
		this.name = mme.symbol.name;
		this.format = mme.symbol.format;
		this.atomicNumber = mme.symbol.atomicNumber;
        this.molecularMass = parseFloat(mme.symbol.molecularMass);
	};
	scope.MMElementData = MMElementData;
	
	/**
	 * Static method is the same for all instances.
	 * 
	 * @return CSV header
	 */
	MMElementData.getHeader = function(){
		
		var headers = ["symbol", "name", "value", "unit", "std", "me", "format", "molecular mass", "atomic number"];
		
		for(var i=0; i< headers.length; i++){
			headers[i] = JSON.stringify(headers[i]);
		}
		
		return headers.join(','); 
	};
	
	/**
	 * @return CSV row format of this element data
	 */
	MMElementData.prototype.row = function(){
		return JSON.stringify(this.symbol) + ',' + JSON.stringify(this.name)+ ',' + this.value+ ',' + 
		JSON.stringify(this.unit)+ ',' +this.std + ',' +this.me+ ',' + JSON.stringify(this.format) + ',' + 
		this.molecularMass + ',' + this.atomicNumber; 
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
		this.elementData = [];
		var that = this;
		if (e.elementData) {
			$(e.elementData).each(function(i, e) {
				that.elementData.push(new MMElementData(e));
			});
		}
		this.iid = e.iid;
		this.instrumentSettings = e.instrumentSettings;
		this.longsampleID = e.longsampleID;
		this.measuredBy = e.measuredBy;
		this.methodType = e.methodType;
		this.numberOfMeasurements = parseInt(e.numberOfMeasurements);
		this.originalTotal = parseFloat(e.originalTotal);
		this.sampleID = e.sampleID;
		this.sampleType = (this.sampleID).charAt(0).toUpperCase();
		this.sampleTypeLong = '';

		if (this.sampleType == 'B')
			this.sampleTypeLong = "Blue Ice Area (BIA)";
		if (this.sampleType == 'I')
			this.sampleTypeLong = "Ice Core";
		if (this.sampleType == 'O')
			this.sampleTypeLong = "Outcrop";
		if (this.sampleType == 'L')
			this.sampleTypeLong = "Lake";
		if (this.sampleType == 'M')
			this.sampleTypeLong = "Marine";
	};
	scope.MMElement = MMElement;
		
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
	
	/**
	 * @param xml
	 *            Either an XML object or a string representation (Not null)
	 * @returns {MMElement[]} The mm elements or an empty list
	 */
	var xmlToMMElements = function(xml) {
		var x2js = new X2JS();
		var json = (typeof xml) === 'string' ? x2js.xml_str2json(xml) : x2js.xml2json(xml);
		var mmelements = [];
		$(json.mmelements.mmelement).each(function(i, e) {
			mmelements.push(new MMElement(e));
		});
		return mmelements;
	};
	scope.xmlToMMElements = xmlToMMElements;
	
	/**
	 * @param callback
	 *            {function} Called after the AJAX get request completes
	 * @returns {undefined}
	 */
	var loadMMElements = function(fileURL, callback) {
		$.ajax({
			type : "GET",
			url : fileURL,
			dataType : "xml",
			success : function(xml) {
				if (callback)
					callback(xmlToMMElements(xml));
			}
		});
	};
	scope.loadMMElements = loadMMElements;
	
	/**
	 * Filter MMElements based on the given array of keys. Make sure the
	 * MMElement data has already been loaded via antt.loadMMElements function.
	 * 
	 * @param keys
	 *            {string[]}
	 * @param mmelements
	 *            {MMElement[]}
	 * @param unit
	 *            {string} matching elements must also be the same unit
	 * @returns {MMElement[]} MMElements that contain every key in the given
	 *          array or an empty array
	 */
	var filterMMElements = function(keys, mmelements, unit) {
		var result = [];

		$(mmelements).each(function(i, e) {
			for (var i = 0; i < keys.length; i++) {
				var found = false;
				for (var j = 0; j < e.elementData.length; j++) {
					if (keys[i] === e.elementData[j].symbol) {
						found = true;
						break;
					}
				}
				if (!found) // No match! Don't add to the results
					return;
			}
			result.push(e);
		});

		return result;
	};
	scope.filterMMElements = filterMMElements;
	
	var searchButtonClicked = function(event) {
		
		var elements = [];
		var values = {};
		$("input[id^='element-'").each(function(i,e){
			var $e = $(e);
			var v = $e.val();
			if (v !== '') {
				var n = $e.attr('id').split('-')[1];
				elements.push(n);
				values[n] = v;
			}
		})
		
		
		loadMMElements('generated/allMMElements.xml', function(allMMElements){
			var filtered = filterMMElements(elements, allMMElements, '%');
			// console.log(filtered);
		});
		
	};
	scope.filtered;
	scope.searchButtonClicked = searchButtonClicked;

	// From
	// http://stackoverflow.com/questions/19491336/get-url-parameter-jquery-or-how-to-get-query-string-values-in-js
	var getUrlParameter = function (sParam) {
	    var sPageURL = decodeURIComponent(window.location.search.substring(1)),
	        sURLVariables = sPageURL.split('&'),
	        sParameterName,
	        i;

	    for (i = 0; i < sURLVariables.length; i++) {
	        sParameterName = sURLVariables[i].split('=');

	        if (sParameterName[0] === sParam) {
	            return sParameterName[1] === undefined ? true : sParameterName[1];
	        }
	    }
	};
	scope.getUrlParameter = getUrlParameter;

	// returns object of one or more search query strings
	var getUrlParameters = function() {
		var sPageURL = decodeURIComponent(window.location.search.substring(1)),
			        sURLVariables = sPageURL.split('&'),
			        sParameterName,
			        i;
		var values = [];

	    for (i = 0; i < sURLVariables.length; i++) {
	        sParameterName = sURLVariables[i].split('=');
	        values.push({key: sParameterName[0], value: sParameterName[1]});
	    }

	    return values;
	};
	scope.getUrlParameters = getUrlParameters;

	/**
	 * Return search query parameter array
	 * 
	 * ex. url -> ?s=sio2:3:tio:40&s=sio2:10:tio:40:ko:1
	 * 
	 * function param ->
	 * [{"key":"s","value":"sio2:3:tio:40"},{"key":"s","value":"sio2:10:tio:40:ko:1"}]
	 * returned -> [{"sio2":"3","tio":"40"},{"sio2":"10","tio":"40","ko":"1"}]
	 * 
	 * @param arr
	 *            {{key:value}[]} GET url of keys and values (Not null)
	 * @param key
	 *            only url keys matching this are used (Not null)
	 * @param delim
	 *            key:val separator; default is colon
	 * @return {object[]} Search query parameter array (Never null)
	 * 
	 * 
	 */
	var getQueriedElements = function(arr, key, delim = ':') {

		var result = [];
		for (var i = 0; i < arr.length; i++) {

			if (arr[i].key === key) {

				var url_param_string = arr[i].value.split(delim);
				var element = {};
				for (var j = 0; j < url_param_string.length; j+=2) {
					element[url_param_string[j]] = url_param_string[j+1];
				}
				result.push(element);
			}
		}

		return result;
	};
	scope.getQueriedElements = getQueriedElements;
	

	/**
	 * Takes MMElement type and returns and array of values that match each key
	 * 
	 * @param keys
	 * @param MMElement
	 * @return arr array of values that match each in keys
	 */
	var valuesArray = function(keys, mmelement) {
		
		var result = [];

		$.each(keys, function(i, k) {
			$.each(mmelement.elementData, function(j, e) {
				if (e.symbol === k && e.unit === '%') {
					result.push(e.value);
				}
			});
		})

		return result;
	};
	scope.valuesArray = valuesArray;

	/**
	 * Builds url based on search submission
	 * 
	 * @param
	 * @return none but do a redirect
	 * 
	 */
	 var builtUrl = function builtUrl(choice) {
	 	var fullUrl = "", existingUrl = decodeURIComponent(window.location.search.substring(1));

	 	if (choice == false) {
	 		// always start with fresh url
	 		existingUrl = "";
	 	}
	 	else {
	 		// start with next search
	 		existingUrl += "&";
	 	}

	 	fullUrl += existingUrl;

	 	var elements = [];
	 	var values = {};

	 	var urlParamCount = 0;

	 	$("input[id^='element-'").each(function(i, e) {
	 		var $e = $(e);
	 		var v = $e.val();

	 		if (v !== '') {
	 			if (urlParamCount == 0) {
	 				fullUrl += 's=';
	 				urlParamCount = 1;
	 			}
	 			var n = $e.attr('id').split('-')[1];
	 			elements.push(n);
	 			values[n] = v;
	 			fullUrl += (n + ':' + v + ':');
	 		}
	 	})

	 	if (fullUrl.slice(-1) == ':') {
	 		fullUrl = fullUrl.slice(0, -1);
	 	}

	 	window.location = 'results.html?' + fullUrl;
	}
	scope.builtUrl = builtUrl;



	// Perform common page functionality
	$(document).ready(function(){
		var id = getUrlParameter('id');
		$('title').text(id); 
	});
	
})(window.antt = window.antt || {});
