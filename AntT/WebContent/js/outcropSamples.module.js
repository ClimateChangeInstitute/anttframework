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

	factory.getMmelements = function() {
		return $http.get("generated/allMMElements.xml");
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

		// set page title
		for (var item in json) {
		    $( "#pageTitle" ).text( json[ item ][ 'sampleID' ] );
		}
	});

	// refactor this search later
	function getOutcropMMElements(json) {
		for (var item in json) {
			var e = json[item];
			for (var i in e) {
				var q = e[i]
				for (var n in q) {
					if (param == q[n]['sampleID']){
						return q[n];
					}
				}
			} 
		}
	}

	dataSource.getMmelements().success(function(data) {
		var x2js = new X2JS();
		var json = x2js.xml_str2json(data);
		$scope.mmElements = getOutcropMMElements(json);
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