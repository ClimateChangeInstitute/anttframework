var app = angular.module('exampleApp', [ 'cgBusy' ]);

app.factory('dataSource', [ '$http', function($http) {
	var factory = [];

	factory.getData = function() {
		return $http.get("generated/allSamples.xml");
	};

	return factory;
} ]);

app.filter('unsafe', function($sce) {
	return function(val) {
		return $sce.trustAsHtml(val);
	};
});

app.directive('tephraDownload', function() {
	return {
		restrict : 'A',
		scope : true,
		link : function(scope, element, attrs) {

			function download(evt) {
				// Find out which sample ids are selected for download
				var selectedIds = [];
				$.each($(this).parents(".panel").find(
						"input.sample-select-box:checked"), function(i, e) {
					selectedIds.push($(e).attr("id").split('-')[2]);
				});

				// Go get the matching MMElements
				var selectedMMElements = [];
				$.each(app.allMMElements, function(i, e) {
					if (selectedIds.indexOf(e.sampleID) >= 0)
						selectedMMElements.push(e);
				});
				
				var finalStr = '';
				
				finalStr += '"sample id","long sample id",'
						+ antt.MMElementData.getHeader();
				finalStr += '\n';

				$.each(selectedMMElements, function(i, mme) {
					
					// Write out the row data for this MMElement
					$.each(mme.elementData, function(i, e) {
						finalStr += (JSON.stringify(mme.sampleID) + ','
								+ JSON.stringify(mme.longsampleID) + ','
								+ e.row());
						finalStr += '\n';
					});
				});
				
				var blob = new Blob([finalStr], {type: "text/plain;charset=utf-8"});
				 
				saveAs(blob, "mmelement_data.csv");

			}

			element.on('click', download);
		}
	};
});

app.controller('results', function($scope, dataSource) {

	$scope.AppController = [];

	// These are the Elements that will presented in the primary section.
	// They will appear in the order listed in this array.
	var primaryElementOrder = [ "SiO2", "TiO2", "Al2O3", "TiO2", "FeO", "MnO",
			"MgO", "CaO", "Na2O", "K2O", "P2O5" ];

	// If we decide not to use default behavior, let's use this alternative
	// behavior
	// $scope.delay = 0;
	// $scope.minDuration = 500;
	$scope.message = 'Loading samples. Please wait...';
	// $scope.templateUrl = 'angular-busy.html';
	// $scope.backdrop = true;
	// $scope.promise = null;

	$scope.promise = antt.loadMMElements('generated/allMMElements.xml',
			function(allMMElements) {
				app.allMMElements = allMMElements;

				var values = antt.getUrlParameters();
				var searches = antt.getQueriedElements(values, 's');

				var allResults = [];

				$.each(searches, function(i, s) {

					var filtered = antt.filterMMElements(Object.keys(s),
							allMMElements, '%');

					var searchRes = [];

					$.each(filtered,
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
									var i = primaryElementOrder
											.indexOf(val.symbol);
									if (i >= 0) {
										val.order = i;
										e.primaryElementData.push(val);
									} else {
										e.secondaryElementData.push(val);
									}
								});

								searchRes.push({
									simVal : simVal,
									mme : e
								});
							});

					searchRes.sort(function(a, b) {
						return b.simVal - a.simVal;
					});

					searchQuery = "";

					$.each(s, function(a, b) {
						searchQuery += a + " = " + b + ",";
					});

					searchQuery = searchQuery.slice(0, -1);

					allResults.push({
						searchRes : searchRes,
						searchQuery : searchQuery,
						count : i
					});

				});

				// reverse array so most recent search is displayed first
				allResults.reverse();

				$scope.filtered = allResults;
				$scope.$apply();
			});
});