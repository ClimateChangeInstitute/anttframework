var app = angular.module('exampleApp', []);

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

app.controller('allSamples', function($scope, dataSource) {

	$scope.AppController = [];

	dataSource.getData().success(function(data) {
		var x2js = new X2JS();
		var json = x2js.xml_str2json(data);
		samples = json.samples.sample;
		$scope.dataSet = samples;

		// create checkbox filters on the fly for dynamic data
		var filters = [];
		_.each(samples, function(sample) {

			
		  _.each(sample, function(value, key) { 

		  	var existingFilter = _.findWhere(filters, { name: key });

		  	// temp for now - we are only interested in sample type as a filter
		  	// we can use any other key filter later
		    if (key == 'sampleType')
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
			      filter.name = key;
			      filter.options = [];
			      filter.options.push({ value: value, count: 1 });
			      filters.push(filter);      
			    }
			}   
		  });
		});
		$scope.Filters = filters;
	});
});