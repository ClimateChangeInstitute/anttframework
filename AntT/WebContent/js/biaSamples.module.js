var app = angular.module('exampleApp', []);

app.config(function($locationProvider) {
	$locationProvider.html5Mode({
		enabled : true,
		requireBase : false
	});
});

app.factory('dataSource', [ '$http', function($http) {
	var factory = [];

	factory.getData = function(param) {
		return $http.get(param);
	};

	return factory;
} ]);


/**
 * @param $location
 *            The URL
 * @param $scope
 *            The current scope
 * @param dataSource
 *            object to get data from
 * @param dir The directory to load the data from
 */
app.setupSampleController = function($location, $scope, dataSource, dir) {
	$scope.AppController = [];

	var param = $location.search()['id'];

	dataSource.getData(dir + param + ".xml").success(function(data) {
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
};

app.controller('biaSample', function($location, $scope, dataSource) {
	app.setupSampleController($location, $scope, dataSource, "generated/XMLSamples/BIA/");
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
