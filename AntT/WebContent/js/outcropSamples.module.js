var app = angular.module('exampleApp', []);

app.config(function($locationProvider) {
	$locationProvider.html5Mode({
		enabled : true,
		requireBase : false
	});
});

app.filter('unsafe', function($sce) {
    return function(val) {
        return $sce.trustAsHtml(val);
    };
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
	});

	/**
	 * @param mmelements
	 *            {MMElement[]} List of MMElements (Not null)
	 * @param id
	 *            {string} The id to match (Not null)
	 * @returns An element with matching sampleID to the given id or null
	 */
	function getOutcropMMElements(mmelements, id) {
		for (var i = 0; i < mmelements.length; i++) {
			if (mmelements[i].sampleID === id) {
				return mmelements[i];
			}
		}
		return null; // No match!
	}

	dataSource.getMmelements().success(function(data) {
		// Requires antt.js
		var mmelements = antt.xmlToMMElements(data);
		$scope.mmElements = getOutcropMMElements(mmelements, param);
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