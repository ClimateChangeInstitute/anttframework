var app = angular.module('exampleApp', ['cgBusy']);

app.factory('dataSource', [ '$http', function($http) {
	var factory = [];

	factory.getData = function() {
		return $http.get("generated/allSamples.xml");
	};

	return factory;
} ]);

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
		
		var elements = [];
		var values = {};
		var url = '';
		var url_param_count = 0;
		// also create url here
		$("input[id^='element-'").each(function(i,e){
			var $e = $(e);
			var v = $e.val();

			if (v !== '') {
				if (url_param_count == 0) {
					url += '?'
					url_param_count = 1;
				}
				var n = $e.attr('id').split('-')[1];
				elements.push(n);
				values[n] = v;
				url += (n + '=' + v + '&'); 
			}
		})

		if (url.slice(-1) == '&') {
			url = url.slice(0, -1);
		}

		console.log(elements);
		console.log(url);

		window.location = 'results.html' + url;
	}

	$scope.promise = antt.loadMMElements('generated/allMMElements.xml', function(allMMElements){

	 	var values = antt.getUrlParameters();
	 	var searches = antt.getQueriedElements(values, 's');

	 	var allResults = [];

	 	$.each(searches, function(i, s) {

		    var filtered = antt.filterMMElements(Object.keys(s), allMMElements, '%');

		    var searchRes = [];

		    $.each(filtered, function(i, e) {

		    	var simVal = antt.statistics.similarityCoefficientListList(Object.values(s), antt.valuesArray(Object.keys(s), e));

		    	searchRes.push({simVal: simVal, mme: e});
		    })

		    searchRes.sort(function(a,b) {
		    	return b.simVal - a.simVal;
		    });

		    allResults.push(searchRes);
		});

	    console.log(allResults);

		$scope.filtered = allResults;
		$scope.$apply();
	});
});