var app = angular.module('exampleApp', [ 'cgBusy' ]);

app.factory('dataSource', [
		'$http',
		function($http) {
			var factory = [];

			factory.getData = function() {
				return $http.get("generated/allSamples.xml");
			};

			factory.getAllMMElements = function() {
				return $http.get("generated/allMMElements.xml").then(
						function(response) {
							return antt.xmlToMMElements(response.data);
						});
			};

			factory.getChemistryOrder = function() {
				return $http.get("generated/allChemistries.xml").then(
						function(response) {
							var x2js = new X2JS();
							var json = x2js.xml_str2json(response.data);
							// Already sorted by preferred order
							return json.chemistries.chemistry;
						});
			};

			return factory;
		} ]);

app.filter('unsafe', function($sce) {
	return function(val) {
		return $sce.trustAsHtml(val);
	};
});

app.filter('encode', function() {
	return window.encodeURIComponent;
});

/**
 * A filter to remove special characters in ID strings.
 */
function makeID(str) {
	return str.replace(/\s|-|\./g, "_");
}

app.filter('makeID', function() {
	return makeID;
});

app.directive('tooltip', function() {
	return {
		restrict : 'A',
		link : function(scope, element, attrs) {
			$(element).tooltip();
		}
	};
});

app.directive('tephraDownload', ['dataSource', function(dataSource) {
	return {
		restrict : 'A',
		scope : {
			"tephraSearch" : "="
		},
		link : function(scope, element, attrs) {

			function download(evt) {

				var tephraSearch = scope.tephraSearch;
				
				// Go get the matching MMElements
				var selectedMMElements = [];
				$.each(tephraSearch.searchRes, function(i, r) {
					if (r.isDownloaded)
						selectedMMElements.push(r);
				});

				if (selectedMMElements.length <= 0) {
					alert("No sample results selected.");
					return;
				}

				dataSource.getChemistryOrder().then(function(chemistries) {
					
					console.log(selectedMMElements);
					console.log(chemistries);

					var now = new Date();

					var dateString = now.getFullYear() + '-'
							+ (now.getMonth() + 1) + '-' + now.getDate() + '_'
							+ now.getHours() + '-' + now.getMinutes();

					var downloadFileName = "anttSearch" + dateString + ".csv";
					antt.saveSelectedData(downloadFileName, selectedMMElements, chemistries);
				});

			}

			element.on('click', download);

		}
	};
}]);

app.directive('tephraSelectall', function() {
	return {
		restrict : 'A',
		scope : {
			"tephraSearch" : "="
		},
		link : function(scope, element, attrs) {

			function selectAll() {
				angular.forEach(scope.tephraSearch.searchRes, function(r) {
					r.isDownloaded = scope.tephraSearch.downloadAll;
				});
			};

			element.on('click', selectAll);
		}
	}
});

app.controller('results', function($scope, dataSource) {

	$scope.AppController = [];

	// If we decide not to use default behavior, let's use this alternative
	// behavior
	// $scope.delay = 0;
	// $scope.minDuration = 500;
	$scope.message = 'Calculating similarity coefficients. Please wait...';
	// $scope.templateUrl = 'angular-busy.html';
	// $scope.backdrop = true;
	// $scope.promise = null;

	$scope.promise = dataSource.getAllMMElements().then(
			function(allMMElements) {
				$scope.allMMElements = allMMElements;
			}).then(dataSource.getChemistryOrder).then(
			function(chemistries) {

				var values = antt.getUrlParameters();

				/**
				 * {object[]} Looks like
				 * [{"sio2":"3","tio":"40"},{"sio2":"10","tio":"40","ko":"1"}]
				 */
				var searches = antt.getQueriedElements(values, 's');

				var allResults = [];

				var symbolFormat = {};

				// Anything after this value will be below 'Orig. Total'
				var divider = "H2O-";

				var chemSymbols = chemistries.map(function(e) {
					return e.symbol
				});

				var dividerIndex = chemSymbols.indexOf(divider);

				/**
				 * @param i
				 *            {number} Index
				 * @param s
				 *            {object} Looks like {"sio2":"3","tio":"40"}
				 */
				$.each(searches, function(i, s) {

					var matchingMMElements = antt.filterMMElements(Object
							.keys(s), $scope.allMMElements, '%');

					var searchRes = [];

					/**
					 * @param i
					 *            {number} Index
					 * @param e
					 *            {MMElement}
					 */
					$.each(matchingMMElements,
							function(i, e) {

								var simVal = antt.statistics
										.similarityCoefficientListList($.map(s,
												function(val, key) {
													return val;
												}), antt.valuesArray(Object
												.keys(s), e));

								e.primaryElementData = [];
								e.secondaryElementData = [];

								$.each(e.elementData, function(i, val) {
									var i = chemSymbols.indexOf(val.symbol);
									if (0 <= i && i <= dividerIndex) {
										val.order = i;
										e.primaryElementData.push(val);
									} else {
										e.secondaryElementData.push(val);
									}
									if (!symbolFormat[val.symbol])
										symbolFormat[val.symbol] = val.format;
								});

								searchRes.push({
									simVal : simVal,
									mme : e
								});

							});

					searchRes.sort(function(a, b) {
						return b.simVal - a.simVal;
					});

					allResults.push({
						searchRes : searchRes,
						searchQuery : antt.createSearchQueryString(s,
								chemistries),
						count : i
					});

				});

				// reverse array so most recent search is displayed first
				allResults.reverse();

				$scope.allResults = allResults;
			});

});
