var app = angular.module('exampleApp', ['cgBusy']);

app.factory('dataSource', [ '$http', function($http) {
	var factory = [];

	factory.getData = function() {
		return $http.get("generated/allSamples.xml");
	};

	return factory;
} ]);

app.filter('checkboxFilters', function () {
    return function (samples, filterTypes, scope) {
      var filtered = [];

      var sampleFilters = _.filter(filterTypes, function(ft) {
      	return  _.any(ft.options, { 'IsIncluded': true });
      });

      _.each(samples, function(samp) {
        var includeSample = true;
        _.each(sampleFilters, function(filter) {
        	var temp = []; 
        	_.each(samp, function(val, key) {
        		temp.push({name: key, value: val});
        	});
        	
        	var props = _.filter(temp, {'name' : filter.name});

          if (!_.any(props, function(prop) { 
          	
          	return _.any(filter.options, { 'value': prop.value, 'IsIncluded': true }); })) {
            includeSample = false;
          }
        });
        if (includeSample) {
          filtered.push(samp);
        }
      });
      return filtered;
    };
  });

app.controller('results', function($scope, dataSource) {

	$scope.AppController = [];

	// If we decide not to use default behavior, let's use this alternative behavior
	// $scope.delay = 0;
	// $scope.minDuration = 500;
	$scope.message = 'Loading samples. Please wait...';
	// $scope.templateUrl = 'angular-busy.html';
	// $scope.backdrop = true;
	// $scope.promise = null;

	$(document).ready(function() {
		$("#search-confirm").click(searchButtonClicked);
	});

	var searchButtonClicked = function(event) {
		
		$("#morphingHeader").text("Search Query Results");

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
		};
		
		$scope.promise = loadMMElements('generated/allMMElements.xml', function(allMMElements){

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
							if (keys[i] === e.elementData[j].element) {
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

			var filtered = filterMMElements(elements, allMMElements, '%');
			$scope.filtered = filtered;


			// // create checkbox filters on the fly for dynamic data
			var filters = [];
			_.each(filtered, function(sample) {

				
			  _.each(sample, function(value, key) { 

			  	console.log(sample.sampleTypeLong);

			  	value = sample.sampleTypeLong;

			  	var existingFilter = _.findWhere(filters, { name: key });

			  	// temp for now - we are only interested in sample type as a filter
			  	// we can use any other key filter later
			    if (key == 'sampleTypeLong')
			    {
			    	if (existingFilter) {
				      var existingOption = _.findWhere(existingFilter.options, { value: value });
				      if (existingOption) {
				        existingOption.count += 1;
				      } else {
				        existingFilter.options.push({ value: value, count: 1 }); 
				      }   
				    } 
				    else {
					  var filter = {};
					  var key_long_name = ((key.replace(/([A-Z]+)/g, ",$1").replace(/^,/, "")).split(",")).join(" ");
					  key_long_name = key_long_name.charAt(0).toUpperCase() + key_long_name.slice(1);
				      filter.name = key;
				      filter.key_long_name = key_long_name.replace(/Long/g, "");
				      filter.options = [];
				      filter.options.push({ value: value, count: 1 });
				      filters.push(filter);      
				    }
				}   
			  });
			});
			$scope.Filters = filters;
			$scope.$apply();
		});
	};
});



// 	scope.statistics = {};
	
// 	/**
// 	 * @param xa
// 	 *            {number[]} Concentration of A elements
// 	 * @param xb
// 	 *            {number[]} Concentration of B elements
// 	 * @return {number} Similarity coefficient for comparison between sample A
// 	 *         and sample B
// 	 */
// 	var similarityCoefficientListList = function(xa, xb) {
// 		var stderr = Array(xa.length).fill(0.0);
// 		// Setting standard error to 0 for each element makes them be
// 		// considered equally. Detection limit is ignored.
// 		return similarityCoefficientListListListListLimit(xa, stderr, xb, stderr, 1 /*
// 																					 * Doesn't
// 																					 * matter
// 																					 */);
// 	}
// 	scope.statistics.similarityCoefficientListList = similarityCoefficientListList; 

// 	/**
// 	 * Assumes errors are symmetrical and positive with 95% confidence.
// 	 * 
// 	 * @param xa
// 	 *            {number[]} Concentration of A elements
// 	 * @param stderra
// 	 *            {number[]} Standard error for A elements
// 	 * @param xb
// 	 *            {number[]} Concentration of B elements
// 	 * @param stderrb
// 	 *            {number[]} Standard error for B elements
// 	 * @param detectionLimit
// 	 *            {number} Typically 0.33
// 	 * @return {number} Similarity coefficient for comparison between sample A
// 	 *         and sample B
// 	 */
// 	var similarityCoefficientListListListListLimit = function(xa, stderra, xb, stderrb, detectionLimit) {

// 		// Calculate weighting coefficients
// 		/** @Type {number[]} */
// 		var g = [];
// 		var i = 0;
// 		for (i = 0; i < xa.length; i++) {
// 			g.push(weightingCoefficient(xa[i],
// 					calcStdDev(stderra[i], xa.length), xb[i], calcStdDev(
// 							stderrb[i], xb.length), detectionLimit));
// 		}

// 		var n = 0;
// 		for (i = 0; i < xa.length; i++) {
// 			n += R(xa[i], xb[i]) * g[i];
// 		}
// 		var d = g.reduce(function(x, y) {
// 			return x + y;
// 		}, 0.0);

// 		return n / d;
// 	}
// 	scope.statistics.similarityCoefficientListListListListLimit = similarityCoefficientListListListListLimit;

// 	/**
// 	 * Assumes confidence level of 99%
// 	 * 
// 	 * @param stderror
// 	 *            {number} Assume positive symmetrical
// 	 * @param N
// 	 *            {number} sample size
// 	 * @return {number} Standard deviation
// 	 */
// 	var calcStdDev = function(stderror, N) {
// 		// TODO 2.819 is for t table value 22 with 99% confidence only
// 		return stderror * Math.sqrt(N) / 2.819;
// 	}
// 	scope.statistics.calcStdDev = calcStdDev;

// 	/**
// 	 * The similarity matrix that results from this method is symmetric. Access
// 	 * result[i][j] to get the list of the ith sample similarity coefficient vs
// 	 * the jth sample.
// 	 * 
// 	 * @param samples
// 	 *            {number[][]} List of samples of elements
// 	 * @param stderr
// 	 *            {number[][]} The error for each sample's elements
// 	 * @param detectionLimit
// 	 *            {number} The detection limit (eg 0.33)
// 	 * @return {number[][]} The similarity coefficient matrix
// 	 */
// 	var similarityCoefficientMatrix = function(
// 			samples, stderr,
// 			detectionLimit) {
// 		var result = [];

// 		for (var i = 0; i < samples.length; i++) {
// 			var s1 = samples[i];
// 			var std1 = stderr[i];
// 			var nextResult = [];// samples.size());
// 			for (var j = 0; j < samples.length; j++) {
// 				var s2 = samples[j];
// 				var std2 = stderr[j];
// 				var sc = similarityCoefficientListListListListLimit(s1, std1, s2, std2,
// 						detectionLimit);

// 				nextResult.push(sc);
// 			}
// 			result.push(nextResult);
// 		}

// 		return result;
// 	}
// 	scope.statistics.similarityCoefficientMatrix = similarityCoefficientMatrix;

// 	/**
// 	 * Weighting coefficient between 0 and 1 which reflects precision.
// 	 * 
// 	 * @param xai
// 	 *            {number} Concentration of element i in A
// 	 * @param stderrai
// 	 *            {number} Standard error for xai
// 	 * @param xbi
// 	 *            {number} Concentration of element i in B
// 	 * @param stderrbi
// 	 *            {number} Standard error for xbi
// 	 * @param detectionLimit
// 	 *            {number} Typically 0.33
// 	 * @return {number} Weighting coefficient between 0 and 1
// 	 */
// 	var weightingCoefficient = function(xai, stderrai,
// 			xbi, stderrbi, detectionLimit) {
// 		var result = 1 - Math.sqrt(
// 				(Math.pow(stderrai / xai, 2) + Math.pow(stderrbi / xbi, 2))
// 						/ detectionLimit);

// 		if (result < 0) {
// 			result = 0;
// 		}

// 		return result;
// 	}
// 	scope.statistics.weightingCoefficient = weightingCoefficient;
	
// 	/**
// 	 * Create a ratio from a and b. The values of a and b can not both be 0
// 	 * since one will be the denominator. If a < 0 or b < 0 then this function
// 	 * will throw
// 	 * 
// 	 * @param a
// 	 *            {number} >= 0
// 	 * @param b
// 	 *            {number} >= 0
// 	 * @return {number} a/b iff a <= b, b/a otherwise
// 	 */
// 	var R = function(a, b) {
// 		if (a < 0 || b < 0)
// 			throw "Negative values are not allowed for a and b.";

// 		return a <= b ? a / b : b / a;
// 	}
// 	scope.statistics.R = R;


// 	// From
// 	// http://stackoverflow.com/questions/19491336/get-url-parameter-jquery-or-how-to-get-query-string-values-in-js
// 	var getUrlParameter = function getUrlParameter(sParam) {
// 	    var sPageURL = decodeURIComponent(window.location.search.substring(1)),
// 	        sURLVariables = sPageURL.split('&'),
// 	        sParameterName,
// 	        i;

// 	    for (i = 0; i < sURLVariables.length; i++) {
// 	        sParameterName = sURLVariables[i].split('=');

// 	        if (sParameterName[0] === sParam) {
// 	            return sParameterName[1] === undefined ? true : sParameterName[1];
// 	        }
// 	    }
// 	};
	
// 	// Perform common page functionality
// 	$(document).ready(function(){
// 		var id = getUrlParameter('id');
// 		$('title').text(id); 
// 	});
	
// })(window.antt = window.antt || {});
