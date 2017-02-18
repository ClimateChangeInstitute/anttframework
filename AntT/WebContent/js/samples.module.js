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

app.filter('encode', function() {
	return window.encodeURIComponent;
});

app.factory('dataSource', [ '$http', function($http) {
	var factory = [];

	factory.getData = function(param) {
		return $http.get(param);
	};

	factory.getMmelements = function() {
		return $http.get("generated/allMMElements.xml");
	};

	factory.getChemistryOrder = function() {
		return $http.get("chemistries_order.txt");
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

		$scope.sample = sample;

	}).error(function(error) {
		console.error('Unable to load file ' + param);
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

	/**
	 * @param mmelements
	 *            {MMElement[]} List of MMElements (Not null)
	 * @param id
	 *            {string} The id to match (Not null)
	 * @returns {MMElement[]} Elements with matching sampleID to the given id or
	 *          an empty array.
	 */
	function getSampleMMElements(mmelements, id) {
		var result = [];
		for (var i = 0; i < mmelements.length; i++) {
			if (mmelements[i].sampleID === id) {
				result.push(mmelements[i]);
			}
		}
		return result;
	}

	dataSource.getMmelements().success(function(data) {
		// Requires antt.js
		var mmelements = antt.xmlToMMElements(data);
		$scope.mmElements = getSampleMMElements(mmelements, param);

		// These are the Elements that will presented in the primary section.
		// They will appear in the order listed in this array.
		// var primaryElementOrder = [ "SiO2", "TiO2", "Al2O3", "TiO2", "FeO",
		// "MnO", "MgO", "CaO", "Na2O", "K2O", "P2O5", "Fe2O3", "Cr2O3" ];

		// Anything after this value will be below 'Orig. Total'
		var divider = "H2O-";

		dataSource.getChemistryOrder().success(function(data) {
			var elementOrder = data.split('\n').filter(function(str) {
				return !str.startsWith("#") && str.length > 0;
			});

			var dividerIndex = elementOrder.indexOf(divider);

			$.each($scope.mmElements, function(outerIndex, e) {

				e.primaryElementData = [];
				e.secondaryElementData = [];

				$.each(e.elementData, function(innerIndex, val) {
					 var i = elementOrder.indexOf(val.symbol);
                     if (0 <= i && i <= dividerIndex) {
                         val.order = i;
                         e.primaryElementData.push(val);
                     } else {
                         e.secondaryElementData.push(val);
                     }
				});

			});

		});

	});
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
	app.setupSampleController($location, $scope, dataSource,
			"generated/XMLSamples/Outcrop/");
});

app.directive('clipboardText', [ '$document', function($document) {
	return {
		restrict : 'A',
		link : function($scope, $element, $attr) {
			var clipboard = new Clipboard($element[0]);
			clipboard.on('success', function(e) {
				alert("The BibTeX has been copied to the clipboard.");
			});
		}
	};
} ]);
