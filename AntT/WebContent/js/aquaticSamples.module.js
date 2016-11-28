var app = angular.module('exampleApp', []);

app.config(function($locationProvider) {
  $locationProvider.html5Mode({
	enabled: true,
	requireBase: false
  });
});

app.factory('dataSource', [ '$http', function($http) {
	var factory = [];

	factory.getData = function(param) {
		if (param.includes("Lake"))
		{
			return $http.get("generated/XMLSamples/Aquatic/Lake/" + param + ".xml");
		}
		if (param.includes("Marine"))
		{
			return $http.get("generated/XMLSamples/Aquatic/Marine/" + param + ".xml");
		}

	};

	return factory;
} ]);

app.controller('aquaticSample', function($location, $scope, dataSource) {

	$scope.AppController = [];

	var param = $location.search()['id'];

	dataSource.getData(param).success(function(data) {
		var x2js = new X2JS();
		var json = x2js.xml_str2json(data);
		$scope.dataSet = json;
	});
});