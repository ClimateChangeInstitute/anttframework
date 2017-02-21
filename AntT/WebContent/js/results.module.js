var app = angular.module('exampleApp', ['cgBusy']);

app.factory('dataSource', ['$http', function($http) {
    var factory = [];

    factory.getData = function() {
        return $http.get("generated/allSamples.xml");
    };

    factory.getAllMMElements = function() {
        return $http.get("generated/allMMElements.xml").then(function(response) {
            return antt.xmlToMMElements(response.data);
        });
    };

    factory.getChemistryOrder = function() {
        return $http.get("chemistries_order.txt");
    };

    return factory;
}]);

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


app.directive('tooltip', function() {
    return {
        restrict: 'A',
        link: function(scope, element, attrs) {
            $(element).tooltip();
        }
    };
});

app.directive('tephraDownload', function() {
    return {
        restrict: 'A',
        scope: true,
        link: function(scope, element, attrs) {

            function download(evt) {
                // Find out which sample IDs are selected for download
                var selectedIds = [];
                var simCoefficients = [];
                                
                $.each($(this).parents(".panel").find("input.sample-select-box:checked"), function(
                    i, e) {
                    var id = $(e).attr("id").split('-')[2];
                    selectedIds.push(id);
                    simCoefficients.push($(e).parents(".panel-samples").find(".simCoefficient")[0].innerHTML);
                });

                if (selectedIds.length <= 0) {
                    alert("No sample results selected.");
                    return;
                }

                // Go get the matching MMElements
                var selectedMMElements = [];
                $.each(scope.allMMElements, function(i, e) {
                    if (selectedIds.indexOf(e.sampleID) >= 0)
                        selectedMMElements.push(e);
                });

                antt.loadChemistriesOrder("chemistries_order.txt", function(
                    order) {

                    var now = new Date();

                    var dateString = now.getFullYear() + '-' +
                        (now.getMonth() + 1) + '-' + now.getDate() + '_' +
                        now.getHours() + '-' + now.getMinutes();

                    var downloadFileName = "anttSearch" + dateString + ".csv";
                    antt.saveSelectedData(downloadFileName, selectedMMElements, simCoefficients, order);
                });

            }

            element.on('click', download);
        }
    };
});


app.controller('results', function($scope, dataSource) {

    $scope.AppController = [];

    // Anything after this value will be below 'Orig. Total'
    var divider = "H2O-";

    // If we decide not to use default behavior, let's use this alternative
    // behavior
    // $scope.delay = 0;
    // $scope.minDuration = 500;
    $scope.message = 'Calculating similarity coefficients. Please wait...';
    // $scope.templateUrl = 'angular-busy.html';
    // $scope.backdrop = true;
    // $scope.promise = null;

    $scope.promise = dataSource.getAllMMElements().then(function(allMMElements) {
        $scope.allMMElements = allMMElements;
    }).then(dataSource.getChemistryOrder).then(function(response) {

        var data = response.data;

        var values = antt.getUrlParameters();

        /**
         * {object[]} Looks like [{"sio2":"3","tio":"40"},{"sio2":"10","tio":"40","ko":"1"}]
         */
        var searches = antt.getQueriedElements(values, 's');

        var allResults = [];

        var symbolFormat = {};

        var elementOrder = data.split('\n').filter(function(str) {
            return !str.startsWith("#") && str.length > 0;
        });

        /**
         * @param i {number} Index
         * @param s {object} Looks like {"sio2":"3","tio":"40"}
         */
        $.each(searches, function(i, s) {

            var matchingMMElements = antt.filterMMElements(Object.keys(s), $scope.allMMElements, '%');

            var searchRes = [];

            /**
             * @param i {number} Index
             * @param e {MMElement}
             */
            $.each(matchingMMElements, function(i, e) {

                var simVal = antt.statistics.similarityCoefficientListList($.map(s, function(
                    val, key) {
                    return val;
                }), antt.valuesArray(Object.keys(s), e));

                e.primaryElementData = [];
                e.secondaryElementData = [];

                var dividerIndex = elementOrder.indexOf(divider);
                $.each(e.elementData, function(i, val) {
                    var i = elementOrder.indexOf(val.symbol);
                    if (0 <= i && i <= dividerIndex) {
                        val.order = i;
                        e.primaryElementData.push(val);
                    } else {
                        e.secondaryElementData.push(val);
                    }
                    if (!symbolFormat[val.symbol])
                        symbolFormat[val.symbol] = val.format;
                });

                searchRes.push({
                    simVal: simVal,
                    mme: e
                });


            });

            searchRes.sort(function(a, b) {
                return b.simVal - a.simVal;
            });

            /**
             * @param {object} Search query object (Not null)
             * @return {string} A search query string of the form k1 = v1, ... , kn = vn
             */
            function createSearchQueryString(searchObj) {
                searchQuery = "";

                // Process each key value pair
                $.each(searchObj, function(a, b) {
                    searchQuery += symbolFormat[a] + " = " + b + ",";
                });
                searchQuery = searchQuery.slice(0, -1);

                return searchQuery;
            };
            allResults.push({
                searchRes: searchRes,
                searchQuery: createSearchQueryString(s),
                count: i
            });

        });

        // reverse array so most recent search is displayed first
        allResults.reverse();

        $scope.allResults = allResults;
    });

});
