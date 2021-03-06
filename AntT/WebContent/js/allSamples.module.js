var app = angular.module('databaseApp', ['cgBusy']);

app.directive('tooltip', function($parse) {
	return {
		restrict : 'A',
		link : function(scope, element, attrs) {
			$(element).tooltip();
		}
	};
});

app.factory('dataSource', [
    '$http',
    function($http) {
        var factory = [];

        factory.getData = function() {
            return $http.get("generated/allSamples.xml").then(
                function(response) {
                    var x2js = new X2JS();
                    var json = x2js.xml_str2json(response.data);

                    return json.samples.sample;
                });
        };

        factory.getCategoryData = function() {
            return $http.get("generated/allCategories.xml").then(
                function(response) {
                    var x2js = new X2JS();
                    var json = x2js.xml_str2json(response.data);

                    var categories = json.categories.category;

                    var finalResult = [];
                    $.each(categories, function(i, e) {
                        // Make sure the samples are arrays
                        antt.ensureArray(e, 'sample');

                        // -90 <= antarctic region latitude <= -55
                        e.sample = antt.filterSamplesByLatLon(e.sample, -90, -55, -180, 180);

                        // We only care about categories that have
                        // samples in the specified range
                        if (e.sample.length > 0) {
                            finalResult.push(e);
                        }
                    });

                    return finalResult;
                });
        };

        return factory;
    }
]);

app.filter('checkboxFilterCategories', function() {
    return function(categories, filterTypes, scope) {

        var result = [];

        var sampleFilters = [];

        // If there are filter types, use them to filter
        if (filterTypes && filterTypes.length > 0) {
            sampleFilters = _.filter(filterTypes[0].options, function(e) {
                return e.isIncluded;
            });
            // Show all of them if nothing is selected
            if (sampleFilters.length == 0) {
                sampleFilters = filterTypes[0].options;
            }
        }

        // If the category matches any of the checked filters add it!
        _.each(categories, function(c) {
            _.each(sampleFilters, function(filter) {
                if (c.sampleTypeLong == filter.value) {
                    result.push(c);
                    return false; // Stop early - found a match
                } else
                    return true;
            });
        });
        return result;
    };
});

app.filter('checkboxFilters', function() {
    return function(samples, filterTypes, scope) {
        var filtered = [];

        var sampleFilters = _.filter(filterTypes, function(ft) {
            return _.any(ft.options, {
                'isIncluded': true
            });
        });

        _.each(samples, function(samp) {
            var includeSample = true;
            _.each(sampleFilters, function(filter) {
                var temp = [];
                _.each(samp, function(val, key) {
                    temp.push({
                        name: key,
                        value: val
                    });
                });

                var props = _.filter(temp, {
                    'name': filter.name
                });

                if (!_.any(props, function(prop) {

                        return _.any(filter.options, {
                            'value': prop.value,
                            'isIncluded': true
                        });
                    })) {
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

/**
 * A filter to remove special characters in ID strings.
 */
function makeID(str) {
    return str.replace(/\s|-|\./g, "_");
}

app.filter('makeID', function() {
    return makeID;
});

app.controller('AllSamplesController', function($scope, dataSource) {

    $scope.AppController = [];

    // If we decide not to use default behavior, let's use this
    // alternative behavior
    // $scope.delay = 0;
    // $scope.minDuration = 500;
    $scope.message = 'Loading samples. Please wait...';
    // $scope.templateUrl = 'angular-busy.html';
    // $scope.backdrop = true;
    // $scope.promise = null;

    $scope.promise = dataSource.getData().then(function(allSamples) {

    	var allSamplesLength = allSamples.length;
    	
        // -90 <= antarctic region latitude <=
        // -55
        var samples = antt.filterSamplesByLatLon(allSamples, -90, -55, -180, 180);

        if (allSamplesLength > 0 && samples.length == 0) {
        	alert(allSamplesLength + " samples were not in the antarctic region and were filtered out. This left no samples to display. Things may not work as expected.");
        }
        
        $scope.dataSet = samples;

        // create checkbox filters on the fly
        // for dynamic data
        var filters = [];
        _.each(samples, function(sample) {

            sample.sampleTypeLong = '';

            _.each(sample, function(value, key) {

                if (value == 'B')
                    sample.sampleTypeLong = "Blue Ice Area (BIA)";
                if (value == 'I')
                    sample.sampleTypeLong = "Ice Core";
                if (value == 'O')
                    sample.sampleTypeLong = "Outcrop";
                if (value == 'L')
                    sample.sampleTypeLong = "Lake";
                if (value == 'M')
                    sample.sampleTypeLong = "Marine";

                value = sample.sampleTypeLong;

                var existingFilter = _.findWhere(
                    filters, {
                        name: key
                    });

                /* temp for now - we are only interested in sample type as a
                   filter we can use any other key filter later */
                if (key == 'sampleTypeLong') {
                    if (existingFilter) {
                        var existingOption = _.findWhere(
                            existingFilter.options, {
                                value: value
                            });
                        if (existingOption) {
                            existingOption.count += 1;
                        } else {
                            existingFilter.options.push({
                                value: value,
                                count: 1
                            });
                        }
                    } else {
                        var filter = {};
                        var key_long_name = ((key.replace(/([A-Z]+)/g, ",$1").replace(/^,/, "")).split(",")).join(" ");
                        key_long_name = key_long_name.charAt(0).toUpperCase() + key_long_name.slice(1);
                        filter.name = key;
                        filter.key_long_name = key_long_name.replace(/Long/g, "");
                        filter.options = [];
                        filter.options.push({
                            value: value,
                            count: 1
                        });
                        filters.push(filter);
                    }
                }
            });
        });
        $scope.Filters = filters;
    }).then(function() {
        dataSource.getCategoryData().then(function(allCategories) {
            $scope.allCategories = allCategories;

            $.each(
                allCategories,
                function(
                    i,
                    c) {
                    if (c.sample &&
                        c.sample.length > 0) {
                        c.sampleTypeLong = antt
                            .toSampleTypeLong(c.sample[0].sampleType);
                    }
                });
        });
    });

});
