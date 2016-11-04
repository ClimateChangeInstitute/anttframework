var app = angular.module('exampleApp', []);

app.factory('dataSource', [ '$http', function($http) {
	var factory = [];

	factory.getMembers = function() {
		return $http.get("team.xml");
	};
	factory.getPapers = function() {
		return $http.get("methodology.xml");
	};

	return factory;
} ]);

app.controller('team', function($scope, dataSource) {

	$scope.AppController = [];
	
	dataSource.getMembers().success(function(data) {
		console.log(data);
		var x2js = new X2JS();
		var json = x2js.xml_str2json(data);
		console.log(json);
		console.log(json.team.member);
		$scope.dataSet = json.team.member;
	});
});
app.controller('methodology', function($scope, dataSource) {

	$scope.AppController = [];
	
	dataSource.getPapers().success(function(data) {
		console.log(data);
		var x2js = new X2JS();
		var json = x2js.xml_str2json(data);
		console.log(json);
		console.log(json.methodology.paper);
		$scope.dataSet = json.methodology.paper;
	});
});