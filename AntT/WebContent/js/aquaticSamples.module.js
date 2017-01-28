var app = angular.module('exampleApp', []);

app.config(function($locationProvider) {
	$locationProvider.html5Mode({
		enabled : true,
		requireBase : false
	});
});

app.factory('dataSource', [
		'$http',
		function($http) {
			var factory = [];

			factory.getData = function(param) {
				if (param.includes("Lake")) {
					return $http.get("generated/XMLSamples/Aquatic/Lake/"
							+ param + ".xml");
				}
				if (param.includes("Marine")) {
					return $http.get("generated/XMLSamples/Aquatic/Marine/"
							+ param + ".xml");
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

	// Hack to make blueimp image gallery work properly with base 64 images
	$scope.handleClick = function(event) {
		event.preventDefault();
		event = event || window.event;
		var target = event.target || event.srcElement, link = target.src ? target.parentNode
				: target, options = {
			index : link,
			event : event
		}, links = $('#links>a');
		links.each(function(i, e) {
			$(e).attr('href', $(e).attr('href').replace('unsafe:', ''));
		});
		blueimp.Gallery(links, options);
	};
});

app.directive('clipboardText', [ '$document', function($document) {
	return {
		restrict : 'A',
		link : function($scope, $element, $attr) {
			var clipboard = new Clipboard($element.context);
			clipboard.on('success', function(e) {
				alert("The BibTeX has been copied to the clipboard.");
			});
		}
	};
} ]);
