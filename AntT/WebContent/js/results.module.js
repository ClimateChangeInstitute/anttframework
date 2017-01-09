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

app.controller('results', function($scope, dataSource) {

	$scope.AppController = [];

	// If we decide not to use default behavior, let's use this alternative
	// behavior
	// $scope.delay = 0;
	// $scope.minDuration = 500;
	$scope.message = 'Loading samples. Please wait...';
	// $scope.templateUrl = 'angular-busy.html';
	// $scope.backdrop = true;
	// $scope.promise = null;

	$scope.promise = antt.loadMMElements('generated/allMMElements.xml', function(
			allMMElements) {

		var values = antt.getUrlParameters();
		var searches = antt.getQueriedElements(values, 's');

		var allResults = [];

		$.each(searches, function(i, s) {

			var filtered = antt.filterMMElements(Object.keys(s), allMMElements, '%');

			var searchRes = [];

			$.each(filtered, function(i, e) {

				var simVal = antt.statistics.similarityCoefficientListList($.map(s, function(
						val, key) {
					return val;
				}), antt.valuesArray(Object.keys(s), e));

				e.primaryElementData = [];
				e.secondaryElementData = [];

				$.each(e.elementData, function(i, val) {
					if (val.symbol == "SiO2") {
						val.order = 1;
						e.primaryElementData.push(val);
					} else if(val.symbol == "TiO2") {
						val.order = 2;
						e.primaryElementData.push(val);
					} else if(val.symbol == "Al2O3") {
						val.order = 3;
						e.primaryElementData.push(val);
					} else if(val.symbol == "TiO2") {
						val.order = 4;
						e.primaryElementData.push(val);
					} else if(val.symbol == "FeO") {
						val.order = 5;
						e.primaryElementData.push(val);
					} else if(val.symbol == "MnO") {
						val.order = 6;
						e.primaryElementData.push(val);
					} else if(val.symbol == "MgO") {
						val.order = 7;
						e.primaryElementData.push(val);
					} else if(val.symbol == "CaO") {
						val.order = 8;
						e.primaryElementData.push(val);
					} else if(val.symbol == "Na2O") {
						val.order = 9;
						e.primaryElementData.push(val);
					} else if(val.symbol == "K2O") {
						val.order = 10;
						e.primaryElementData.push(val);
					} else if(val.symbol == "P2O5") {
						val.order = 11;
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