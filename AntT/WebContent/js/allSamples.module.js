var app = angular.module('exampleApp', []);

app.factory('dataSource', [ '$http', function($http) {
	var factory = [];

	factory.getData = function() {
		return $http.get("generated/allSamples.xml");
	};

	return factory;
} ]);

app.controller('AllSamples', function($scope, dataSource) {

	$scope.AppController = [];

	dataSource.getData().success(function(data) {
		console.log(data);
		var x2js = new X2JS();
		var json = x2js.xml_str2json(data);
		console.log(json);
		console.log(json.samples.sample);
		$scope.dataSet = json.samples.sample;
	});
});