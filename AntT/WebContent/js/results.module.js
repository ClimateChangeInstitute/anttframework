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

				searchRes.push({
					simVal : simVal,
					mme : e
				});
			})

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