var app = angular.module('exampleApp', []);

app.factory('dataSource', [ '$http', function($http) {
	var factory = [];

	factory.getData = function() {
		return $http.get("generated/XMLSamples/BIA/BIAExample0.xml");
	};

	return factory;
} ]);

app.controller('BIA', function($scope, dataSource) {

	$scope.AppController = [];

	dataSource.getData().success(function(data) {
		console.log(data);
		var x2js = new X2JS();
		var json = x2js.xml_str2json(data);
		console.log(json);
		console.log(json.BIAExample);
		$scope.dataSet = json;
	});
});