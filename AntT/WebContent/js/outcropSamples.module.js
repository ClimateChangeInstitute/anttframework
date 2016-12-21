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
		return $http.get("generated/XMLSamples/Outcrop/" + param + ".xml");
	};

	return factory;
} ]);

app.controller('outcropSample', function($location, $scope, dataSource) {

	$scope.AppController = [];

	var param = $location.search()['id'];

	dataSource.getData(param).success(function(data) {
		var x2js = new X2JS();
		var json = x2js.xml_str2json(data);
		$scope.dataSet = json;
	});

	// Hack to make blueimp image gallery work properly with base 64 images
	$scope.handleClick = function (event) {
		  event.preventDefault();
		  event = event || window.event;
		    var target = event.target || event.srcElement,
		        link = target.src ? target.parentNode : target,
		        options = {index: link, event: event},
		        links = $('#links>a');
		    links.each(function(i, e){
		    	$(e).attr('href', $(e).attr('href').replace('unsafe:', ''));
		    });
		    blueimp.Gallery(links, options);
		};
});