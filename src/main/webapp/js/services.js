'use strict';

/* Services */
angular.module('classregControllers')
    .factory('classPeriodParser', function() {

        var parser = {};

        parser.parse =  function(classPeriods)
        {
            var result = [];
            for (var k in classPeriods) {
                var classPeriod = classPeriods[k];
                //For each day classPeriod.day is like "MW"
                for (var i = 0, len = classPeriod.day.length; i < len; i++) {
                    var thisDay = classPeriod.day[i];
                    if (thisDay == "T" && classPeriod.day[i + 1] == "h") {
                        thisDay = "Th";
                        i++;
                    }
                    // ["1", "00"]
                    var startTime = (classPeriod.startTime).split(':');
                    var endTime = (classPeriod.endTime).split(':');

                    var startHrs = parseInt(startTime[0]);
                    if (startHrs >= 1 && startHrs <= 6) {
                        startHrs += 12;
                    }
                    var startMins = parseInt(startTime[1]);
                    var endHrs = parseInt(endTime[0]);
                    if (endHrs >= 1 && endHrs <= 6 || (endHrs >= 1 && endHrs <= 10 && startHrs >= 1 && startHrs <= 6)) {
                        endHrs += 12;
                    }
                    var endMins = parseInt(endTime[1]);

                    result.push({
                        day: thisDay,
                        startHrs: startHrs,
                        startMins: startMins,
                        endHrs: endHrs,
                        endMins: endMins
                    })
                }
            }

            return result;
        }

        return parser;
    });
