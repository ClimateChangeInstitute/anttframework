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
		return $http.get(param);
	};

	factory.getMmelements = function() {
		return $http.get("generated/allMMElements.xml");
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
 * @param dir
 *            The directory to load the data from
 */
app.setupSampleController = function($location, $scope, dataSource, dir) {
	$scope.AppController = [];

	var param = $location.search()['id'];

	dataSource.getData(dir + param + ".xml").success(function(data) {
		var x2js = new X2JS();
		var json = x2js.xml_str2json(data);
		var sample = json.sample;

		// Have to make sure that arrays are actually arrays for Angular to work
		// properly!
		function ensureArray(obj, key) {
			if (!Array.isArray(obj[key])) {
				obj[key] = obj[key] ? [ obj[key] ] : [];
			}
		};
		
		// Samples must have arrays of images and references
		ensureArray(sample, 'images');
		ensureArray(sample, 'refs');

		$scope.sample = json.sample;
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
	app.setupSampleController($location, $scope, dataSource,
			"generated/XMLSamples/BIA/");
});

app.controller('iceCoreSample', function($location, $scope, dataSource) {
	app.setupSampleController($location, $scope, dataSource,
			"generated/XMLSamples/IceCore/");
});

app.controller('lakeSample', function($location, $scope, dataSource) {
	app.setupSampleController($location, $scope, dataSource,
			"generated/XMLSamples/Aquatic/Lake/");
});

app.controller('marineSample', function($location, $scope, dataSource) {
	app.setupSampleController($location, $scope, dataSource,
			"generated/XMLSamples/Aquatic/Marine/");
});

app.controller('outcropSample', function($location, $scope, dataSource) {

	var param = $location.search()['id'];

	app.setupSampleController($location, $scope, dataSource,
			"generated/XMLSamples/Outcrop/");

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
