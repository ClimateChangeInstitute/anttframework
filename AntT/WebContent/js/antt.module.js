var app = angular.module('exampleApp', ['cgBusy']);

app.factory('dataSource', [ '$http', function($http) {
	var factory = [];

	factory.getData = function() {
		return $http.get("generated/allSamples.xml");
	};

	return factory;
} ]);

app.filter('checkboxFilters', function () {
    return function (samples, filterTypes, scope) {
      var filtered = [];

      var sampleFilters = _.filter(filterTypes, function(ft) {
      	return  _.any(ft.options, { 'IsIncluded': true });
      });

      _.each(samples, function(samp) {
        var includeSample = true;
        _.each(sampleFilters, function(filter) {
        	var temp = []; 
        	_.each(samp, function(val, key) {
        		temp.push({name: key, value: val});
        	});
        	
        	var props = _.filter(temp, {'name' : filter.name});

          if (!_.any(props, function(prop) { 
          	
          	return _.any(filter.options, { 'value': prop.value, 'IsIncluded': true }); })) {
            includeSample = false;
          }
        });
        if (includeSample) {
          filtered.push(samp);
        }
      });
      return filtered;
    };
  });

app.controller('results', function($scope, dataSource) {

	$scope.AppController = [];

	// If we decide not to use default behavior, let's use this alternative behavior
	// $scope.delay = 0;
	// $scope.minDuration = 500;
	$scope.message = 'Loading samples. Please wait...';
	// $scope.templateUrl = 'angular-busy.html';
	// $scope.backdrop = true;
	// $scope.promise = null;

	$(document).ready(function() {
		$("#search-confirm").click(searchButtonClicked);
	});

	var searchButtonClicked = function(event) {
		
		var elements = [];
		var values = {};
		var url = '';
		var url_param_count = 0;
		// also create url here
		$("input[id^='element-'").each(function(i,e){
			var $e = $(e);
			var v = $e.val();

			if (v !== '') {
				if (url_param_count == 0) {
					url += '?'
					url_param_count = 1;
				}
				var n = $e.attr('id').split('-')[1];
				elements.push(n);
				values[n] = v;
				url += (n + '=' + v + '&'); 
			}
		})

		if (url.slice(-1) == '&') {
			url = url.slice(0, -1);
		}

		console.log(elements);
		console.log(url);

		window.location = 'results.html' + url;
	}

	$scope.promise = antt.loadMMElements('generated/allMMElements.xml', function(allMMElements){

		// PARSE URL HERE

		// var sPageURL = decodeURIComponent(window.location.search.substring(1)),
		// 	        sURLVariables = sPageURL.split('&'),
		// 	        sParameterName,
		// 	        i;
		// var elements = [];
		// var values = {};

		// console.log(sPageURL);
		// console.log(sURLVariables);

	 //    for (i = 0; i < sURLVariables.length; i++) {
	 //        sParameterName = sURLVariables[i].split('=');
	 //        elements.push(sParameterName[0]);
	 //        values[sParameterName[0]] = sParameterName[1];
	 //    }

	 //    console.log(elements);
	 //    console.log(values);

	 	var values = antt.getUrlParameters();
	 	var searches = antt.getQueriedElements(values, 's');


	    // END PARSE URL
	    var filtered = antt.filterMMElements(searches[0], allMMElements, '%');
		$scope.filtered = filtered;

		// // create checkbox filters on the fly for dynamic data
		var filters = [];
		_.each(filtered, function(sample) {
			
			_.each(sample, function(value, key) { 

		  	value = sample.sampleTypeLong;

		  	var existingFilter = _.findWhere(filters, { name: key });

		  	// temp for now - we are only interested in sample type as a filter
		  	// we can use any other key filter later
		    if (key == 'sampleTypeLong')
		    {
		    	if (existingFilter) {
			      var existingOption = _.findWhere(existingFilter.options, { value: value });
			      if (existingOption) {
			        existingOption.count += 1;
			      } else {
			        existingFilter.options.push({ value: value, count: 1 }); 
			      }   
			    } 
			    else {
				  var filter = {};
				  var key_long_name = ((key.replace(/([A-Z]+)/g, ",$1").replace(/^,/, "")).split(",")).join(" ");
				  key_long_name = key_long_name.charAt(0).toUpperCase() + key_long_name.slice(1);
			      filter.name = key;
			      filter.key_long_name = key_long_name.replace(/Long/g, "");
			      filter.options = [];
			      filter.options.push({ value: value, count: 1 });
			      filters.push(filter);      
			    }
			}   
		  });
		});
		$scope.Filters = filters;
		$scope.$apply();
	});
});