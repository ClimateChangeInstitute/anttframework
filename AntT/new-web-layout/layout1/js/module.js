var app = angular.module('exampleApp', []);

app.factory('dataSource', [ '$http', function($http) {
	var factory = [];

	factory.getMembers = function() {
		return $http.get("team2.xml");
	};

	return factory;
} ]);

app.controller('appController', function($scope, dataSource) {

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