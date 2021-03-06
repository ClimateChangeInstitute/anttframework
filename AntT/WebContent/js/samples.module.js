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

/**
 * A filter to remove special characters in ID strings.
 */
function makeID(str) {
	return str.replace(/\s|-|\./g, "_");
}

app.filter('makeID', function() {
	return makeID;
});

app.factory('dataSource', [
		'$http',
		function($http) {
			var factory = [];

			factory.getSampleData = function(param) {
				return $http.get(param).then(function(response) {
					var x2js = new X2JS();
					var json = x2js.xml_str2json(response.data);
					return json.sample;
				});
			};

			factory.getMmelements = function() {
				return $http.get("generated/allMMElements.xml").then(function(
						response) {
					return antt.xmlToMMElements(response.data);
				});
			};

			factory.getChemistryOrder = function() {
				return $http.get("generated/allChemistries.xml").then(function(response) {
					var x2js = new X2JS();
					var json = x2js.xml_str2json(response.data);
					// Already sorted by preferred order
					return json.chemistries.chemistry;
				});
			};

			return factory;
		} ]);

app.directive('tooltip', function() {
	return {
		restrict : 'A',
		link : function(scope, element, attrs) {
			$(element).tooltip();
		}
	};
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
app.setupSampleController = function($location, $scope, dataSource, $anchorScroll, dir) {
	$scope.AppController = [];

	var param = $location.search()['id'];

	dataSource.getSampleData(dir + param + ".xml").then(function(sample) {

		// Have to make sure that arrays are actually arrays for Angular to work
		// properly!
		// Samples must have arrays of images and references
		antt.ensureArray(sample, 'images');
		antt.ensureArray(sample, 'refs');

		$scope.sample = sample;

	}).catch(function(error) {
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

	dataSource.getMmelements().then(function(mmelements) {
		$scope.mmElements = getSampleMMElements(mmelements, param);
	}).then(dataSource.getChemistryOrder).then(function(chemistries) {

		// Anything after this value will be below 'Orig. Total'
		var divider = 'H2O-';
		
		var chemSymbols = chemistries.map(function(e) { return e.symbol });
		var dividerIndex = chemSymbols.indexOf(divider);

		$.each($scope.mmElements, function(outerIndex, e) {

			e.primaryElementData = [];
			e.secondaryElementData = [];

			$.each(e.elementData, function(innerIndex, val) {
				var i = chemSymbols.indexOf(val.symbol);
				if (0 <= i && i <= dividerIndex) {
					val.order = i;
					e.primaryElementData.push(val);
				} else {
					e.secondaryElementData.push(val);
				}
			});

		});

		// Stupid hack to make the anchor links scroll into view
		setTimeout(function(){
			$anchorScroll();
			// Offset by 60 so that the anchor isn't covered by the navbar
			setTimeout(function(){scrollBy(0, -60);},0);
		}, 0);
		
	});

};

app.controller('biaSample', ['$location', '$scope', 'dataSource','$anchorScroll', function($location, $scope, dataSource, $anchorScroll) {
	app.setupSampleController($location, $scope, dataSource, $anchorScroll, "generated/XMLSamples/BIA/");
}]);

app.controller('iceCoreSample', ['$location', '$scope', 'dataSource','$anchorScroll', function($location, $scope, dataSource, $anchorScroll) {
	app.setupSampleController($location, $scope, dataSource, $anchorScroll, "generated/XMLSamples/IceCore/");
}]);

app.controller('lakeSample', ['$location', '$scope', 'dataSource','$anchorScroll', function($location, $scope, dataSource, $anchorScroll) {
	app.setupSampleController($location, $scope, dataSource, $anchorScroll, "generated/XMLSamples/Aquatic/Lake/");
}]);

app.controller('marineSample', ['$location', '$scope', 'dataSource','$anchorScroll', function($location, $scope, dataSource, $anchorScroll) {
	app.setupSampleController($location, $scope, dataSource, $anchorScroll, "generated/XMLSamples/Aquatic/Marine/");
}]);

app.controller('outcropSample', ['$location', '$scope', 'dataSource','$anchorScroll', function($location, $scope, dataSource, $anchorScroll) {
	app.setupSampleController($location, $scope, dataSource, $anchorScroll, "generated/XMLSamples/Outcrop/");
}]);
