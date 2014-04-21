'use strict';

var tempEventColor = '#4FB14F';
var eventColor = '#428bca';
var greyedOutEventColor = '#DDD';
var conflictingEventColor = '#C25151';

/* Controllers */
var classregControllers = angular.module('classregControllers', []);

classregControllers.controller('HeaderController', ['$scope', '$rootScope', '$location',
    function($scope, $rootScope, $location) {
        $scope.isActive = function(viewLocation) {
            return viewLocation === $location.path();
        };
    }
]);

classregControllers.controller('CourseListCtrl', ['$scope', '$http', '$cookies', '$rootScope', '$interval', '$timeout',
    function ($scope, $http, $cookies, $rootScope, $interval, $timeout) {

        $scope.isLoadingCourses = true;
        $http.get('public-api/courses/all').success(function (data) {
            var previouslySavedPlan = {}
            if( $cookies.classes !== undefined && $cookies.classes !== "undefined" && $cookies.classes !== null )
            {
                try {
                    var classes = JSON.parse($cookies.classes)
                    for (var i = 0; i < classes.length; i++)
                        previouslySavedPlan[classes[i].courseNumber] = classes[i].sectionId
                } catch( exception ) { /* don't die */}
            }
            $scope.departments = []
            $scope.courses = []
            angular.forEach(data.courses, function (apiCourse) {
                var course = {}
                course.title = apiCourse.courseName
                course.courseNumber = apiCourse.courseNumber
                course.outcomes = apiCourse.outcomes
                if (apiCourse.sections !== null && apiCourse.sections !== undefined && apiCourse.sections.length > 0) {
                    var minCredits = 1000, maxCredits = -1;
                    for( var i=0; i<apiCourse.sections.length; i++ ) {
                        var sectionCredits = parseInt(apiCourse.sections[i].credits, 10)
                        if( sectionCredits < minCredits )
                            minCredits = sectionCredits
                        if( sectionCredits > maxCredits )
                            maxCredits = sectionCredits
                    }
                    if( minCredits == maxCredits )
                        course.credits = minCredits
                    else
                        course.credits = minCredits+' - '+maxCredits
                }
                course.dept = {}
                course.dept.title = apiCourse.department == null ? '' : apiCourse.department
                course.dept.shortCode = apiCourse.departmentCode
                if ($scope.departments.indexOf(apiCourse.departmentCode) < 0)
                    $scope.departments.push(apiCourse.departmentCode)
                course.titleCode = apiCourse.newTitleCode
                course.byuId = apiCourse.courseID
                course.sections = []
                angular.forEach(apiCourse.sections, function (apiSection) {
                    var section = {}
                    section.sectionId = apiSection.sectionID
                    section.professor = apiSection.professor
                    if( apiSection.pid === "undefined" || apiSection.pid === undefined || apiSection.pid === null || apiSection.pid === "" ) {
                        section.rateMyProfessorQuery = "SelectTeacher.jsp?searchName="+section.professor.split(",")[0]+"&search_submit1=Search&sid=135"
                    } else
                        section.rateMyProfessorQuery = "ShowRatings.jsp?tid="+apiSection.pid

                    section.room = '' //TODO refactor
                    section.buildingAbbreviation = '' //TODO refactor
                    var roomPrefix = '' //TODO refactor
                    var buildingAbbreviationPrefix = '' //TODO refactor
                    section.classPeriods = [];
                    section.classSize = apiSection.totalSeats;
                    section.waitlistCount = apiSection.waitList;
                    section.seatsAvailable = parseInt(apiSection.seatsAvailable, 10)
                    section.sectionType = apiSection.sectionType === undefined || apiSection.sectionType === null ? "DAY" : apiSection.sectionType;
                    angular.forEach(apiSection.timePlaces, function (timePlace) {
                        var timeOfDay = timePlace.startTime.substring(0, timePlace.startTime.length - 2) + '-' + timePlace.endTime.substring(0, timePlace.endTime.length - 2);
                        var prefix
                        if (!(timeOfDay in section.classPeriods)) {
                            section.classPeriods[timeOfDay] = ''
                            prefix = ''
                        } else {
                            prefix = ', '
                        }
                        if (timePlace.day.indexOf('M') >= 0 && section.classPeriods[timeOfDay].indexOf('M') < 0) {
                            section.classPeriods[timeOfDay] += prefix + 'M'
                            prefix = ', '
                        }
                        if (timePlace.day.indexOf('T') >= 0 && section.classPeriods[timeOfDay].indexOf('Tu') < 0) {
                            section.classPeriods[timeOfDay] += prefix + 'Tu'
                            prefix = ', '
                        }
                        if (timePlace.day.indexOf('W') >= 0 && section.classPeriods[timeOfDay].indexOf('W') < 0) {
                            section.classPeriods[timeOfDay] += prefix + 'W'
                            prefix = ', '
                        }
                        if (timePlace.day.indexOf('Th') >= 0 && section.classPeriods[timeOfDay].indexOf('Th') < 0) {
                            section.classPeriods[timeOfDay] += prefix + 'Th'
                            prefix = ', '
                        }
                        if (timePlace.day.indexOf('F') >= 0 && section.classPeriods[timeOfDay].indexOf('F') < 0) {
                            section.classPeriods[timeOfDay] += prefix + 'F'
                        }
                        var location = timePlace.location.trim()
                        if (location === 'TBA') {
                            section.room = 'TBA'
                            section.buildingAbbreviation = 'TBA'
                        } else {
                            var splitLocation = location.split(' ')
                            section.room += roomPrefix + splitLocation[1]
                            section.buildingAbbreviation += buildingAbbreviationPrefix + splitLocation[0]
                            roomPrefix = ', '
                            buildingAbbreviationPrefix = ', '
                        }
                    });
                    course.sections.push(section)
                    if( previouslySavedPlan[course.byuId] === section.sectionId ) {
                        $scope.addCourseToPlan(course, section)
                    }
                });
                $scope.courses.push(course)
            });
            $scope.isLoadingCourses = false;
        });
        // popular courses to be shown when there are no filters applied
        $scope.popularCourses = [];

        var autoSave = $interval(function () {
            $scope._savePlan()
        }, 5000);
        $scope.$on('$destroy', function () { $interval.cancel(autoSave); });

        $scope.dismissLoadingOverlay = function() {
            $('#loading-overlay').css('display', 'none');
        };

        $scope.initStuff = function() {
            $scope.courseLevels = ['100', '200', '300', '400', '500', '600'];
            $scope.currentSemester = "Summer 2014" //Should do some kind of logic or API call here
            $scope.initPlannedCourses();
            $scope.saved = false;
            $scope.filterOptions = {
                levels: {}
            };
            $scope.sortBy = 'dept.shortCode';
            $scope.filteredDept = '';
            $scope.selectedCourse = undefined;

            angular.forEach($scope.courseLevels, function(level) {
                $scope.filterOptions.levels[level] = true;
            });
        };

        // This is what you will bind the filter to
        $scope.filterText = '';

        // Instantiate these variables outside the watch
        var tempFilterText = '',
            filterTextTimeout;
        $scope.$watch('searchText', function(val) {
            if (filterTextTimeout) $timeout.cancel(filterTextTimeout);

            tempFilterText = val;
            filterTextTimeout = $timeout(function() {
                $scope.filterText = tempFilterText;
            }, 250); // delay 250 ms
        })

        $scope.initPlannedCourses = function() {
            $scope.plannedCourses = [];
            $scope.sumPlannedCredits = 0.0;
            $scope.$broadcast("removeAllCourses");
        };

        $scope.initStuff();

        $scope.allFilter = function(course) {
            return (($scope.filterText && $scope.filterText.length) || ($scope.filteredDept && $scope.filteredDept.length) || $scope.popularCourses.indexOf(course.dept.shortCode + course.courseNumber) > -1);
        };

        // Searches both course name and course description fields
        $scope.searchQueryFilter = function(course) {
            var q = angular.lowercase($scope.filterText);
            return (!angular.isDefined(q) || q == "" ||
                (angular.lowercase(course.title).indexOf(q) >= 0 ||
                    angular.lowercase(course.dept.shortCode.replace(/\s/g, '')).indexOf(q) >= 0 ||
                    angular.lowercase(course.dept.title).indexOf(q) >= 0 ||
                    angular.lowercase(course.courseNumber).indexOf(q) >= 0 ||
                    angular.lowercase(course.dept.shortCode + course.courseNumber).indexOf(q.replace(/\s/g,'')) >= 0 ||
                    angular.lowercase(course.dept.shortCode.replace(/\s/g,'') + course.courseNumber).indexOf(q.replace(/\s/g,'')) >= 0));
        };

        //Filters by department
        $scope.departmentFilter = function(course) {
            return $scope.filteredDept === /* all departments */ '' || $scope.filteredDept === course.dept.shortCode
        };

        // Filters by course level
        $scope.courseLevelFilter = function(course) {
            var targetLevel = course.courseNumber[0] + '00';
            return ($scope.filterOptions.levels[targetLevel]) ? true : false;
        };

        // Sorts table by the selected column and updates ascending/descending order
        $scope.updateSort = function(selected) {
            $scope.desc = $scope.sortBy == selected && !$scope.desc;
            $scope.sortBy = selected;
        };

        // Retrieves the styling class for a sortable table header
        $scope.sortedClass = function(selected) {
            return $scope.sortBy == selected ? ($scope.desc ? 'sorted-desc' : 'sorted-asc') : '';
        };

        $scope.updateSelectedCourse = function(dept, id) {
            var course = $.grep($scope.courses, function(c){ return c.dept.shortCode == dept && c.courseNumber == id; });
            if (course) {
                $scope.selectedCourse = course[0];
            }
        };

        $scope.$on("eventClicked", function (event, args) {
            var course = args.courseName;
            course = course.split(' ');
            var dept = course[0];
            var id = course[1];
            $scope.updateSelectedCourse(dept, id);
        });

        $scope.classPeriodsToString = function(classPeriods) {
            var result = ''
            var prefix = ''
            for(var key in classPeriods) {
                result += prefix + classPeriods[key] + ' ' + key
                prefix = ', '
            }
            return result
        };

        // check if a course is planned, where cid is a course/section id that looks like this: "CS256-1" for section 1
        $scope.isPlanned = function(cid) {
            for (var i = 0; i < $scope.plannedCourses.length; i++) {
                if ($scope.plannedCourses[i].cid == cid) {
                    return true;
                }
            }
            return false;
        };

        // gets the planned section of a course. if no planned section, returns null.
        $scope.getPlannedCourse = function(dept, num) {
            for (var i = 0; i < $scope.plannedCourses.length; i++) {
                if ($scope.plannedCourses[i].dept.shortCode == dept && $scope.plannedCourses[i].courseNumber == num) {
                    return $scope.plannedCourses[i];
                }
            }
            return null;
        };

        $scope.addCourseToPlan = function(course, section) {
            $scope.saved = false;
            $scope.added = false;
            // when the class is added, remove its temporary calendar event
            $scope.hideTempEvent(course, section);
            var fullCourseName = course.dept.shortCode + ' ' + course.courseNumber;
            var cid = fullCourseName + "-" + section.sectionId;
            var classLocation = section.buildingAbbreviation + ' ' + section.room;

            $scope.$broadcast("courseAdded", {course: cid, classPeriods: section.classPeriods, classLocation: classLocation, color: eventColor });

            var theCourse = $scope.getPlannedCourse(course.dept.shortCode, course.courseNumber);
            if (theCourse) {
                // if there's already another section of the same course, just replace it
                $scope.removeCourseFromPlan(theCourse);
            }

            if (!$scope.isPlanned(cid)) {
                var plannedCourse = new Object();
                plannedCourse.cid = cid;
                plannedCourse.dept = course.dept;
                plannedCourse.courseNumber = course.courseNumber;
                plannedCourse.sectionId = section.sectionId;
                plannedCourse.instructor = section.professor;
                plannedCourse.classPeriods = section.classPeriods;
                plannedCourse.byuId = course.byuId;
                plannedCourse.titleCode = course.titleCode;
                plannedCourse.credits = course.credits;
                plannedCourse.sectionType = section.sectionType;
                plannedCourse.title = course.title;
                $scope.plannedCourses.push(plannedCourse);
            }
            $scope.sumPlannedCredits += course.credits;

            var elId = '#plannedCourse-' + ($scope.plannedCourses.length - 1).toString();

            setTimeout(function() {
                $(elId).effect("highlight", {}, 1000);
            }, 100);
        };

        $scope.removeCourseFromPlan = function(course) {
            $scope.saved = false;
            $scope.added = false;

            $scope.$broadcast("courseRemoved", {course: course.cid, temp: false});

            var i = $scope.plannedCourses.indexOf(course);
            if (i > -1)
                $scope.plannedCourses.splice(i, 1);

            $scope.sumPlannedCredits -= course.credits;
        };

        $scope.showTempEvent = function(course, section) {
            var fullCourseName = course.dept.shortCode + ' ' + course.courseNumber;
            var cid = fullCourseName + "-" + section.sectionId;
            if (!$scope.isPlanned(cid)) {
                // change color of other sections of this course to gray
                $scope.$broadcast("changeEventColor", {course: cid.split('-')[0], color: greyedOutEventColor});
                // show temp event on the calendar
                $scope.$broadcast("courseAdded", {course: cid, classPeriods: section.classPeriods, color: tempEventColor, className: 'temp' });
            }
        };

        $scope.hideTempEvent = function(course, section) {
            var fullCourseName = course.dept.shortCode + ' ' + course.courseNumber;
            var cid = fullCourseName + "-" + section.sectionId;
            $scope.$broadcast("courseRemoved", {course: cid, temp: true});
            // change color of other sections of this course back to default
            $scope.$broadcast("changeEventColor", {course: cid.split('-')[0], color: eventColor});
        };

        $scope.savePlan = function() {
            // TODO: save plan to cookies
            $scope._savePlan();
        };

        $scope._savePlan = function() {
            $scope.saved = true;
        };

        $scope.registerClasses = function() {
            $cookies.c = "regOfferings";
            var classes = []
            for( var i=0; i<$scope.plannedCourses.length; i++ ) {
                var klass = {}
                klass.e = '@AddClass';
//                klass.e = '@ConfirmWaitlist'
                klass.courseNumber = $scope.plannedCourses[i].byuId;
                klass.titleCode = $scope.plannedCourses[i].titleCode;
                klass.credits = $scope.plannedCourses[i].credits;
                klass.sectionType = $scope.plannedCourses[i].sectionType;
                klass.sectionId = $scope.plannedCourses[i].sectionId;
                klass.dept = $scope.plannedCourses[i].dept.shortCode
                klass.title = $scope.plannedCourses[i].title;
                classes.push(klass)
            }
            $cookies.classes = JSON.stringify(classes)

            var domain = location.protocol+'//'+location.hostname+(location.port ? ':'+location.port: '');
            var query = '?service='+encodeURIComponent(domain+'/byu-login-landing.html');
            var url = 'https://cas.byu.edu/cas/login';

            var regFrame = $("#registration-iframe");
            regFrame.attr("src", url + query);
            // $scope.initPlannedCourses();
            // $scope.added = true;
        };
    }]);

classregControllers.controller('CalendarCtrl', ['$scope',
    function($scope) {
        var calendar = $('#calendar');
        var sunday = new Date(moment().startOf('week'));
        var d = sunday.getDate();
        var m = sunday.getMonth();
        var y = sunday.getFullYear();
        var dayOffsets = {'M': 1, 'Tu': 2, 'W': 3, 'Th': 4, 'F': 5};

        $scope.uiConfig = {
            calendar:{
                height: 1000,
                editable: false,
                defaultView: 'agendaWeek',
                minTime: '06:00',
                maxTime: '22:00',
                allDaySlot: false,
                allDayText: false,
                slotMinutes: 60,
                weekends: false,
                header: {
                    left: '',
                    center: '',
                    right: ''
                },
                columnFormat: {
                    week: 'ddd' // Mon
                },
                eventClick: function(event, jsEvent, view) {
                    var courseInfo = event.title.split('-');
                    var courseName = courseInfo[0];
                    $scope.$emit("eventClicked", {courseName: courseName});
//                    alert('Event: ' + calEvent.title);
//                    alert('Coordinates: ' + jsEvent.pageX + ',' + jsEvent.pageY);
//                    alert('View: ' + view.name);


                },
                eventRender:function(event,element,view){
                    var courseInfo = event.title.split('-');
                    var courseName = courseInfo[0];
                    var sectionNum = courseInfo[1];
                    var eventTime = $.fullCalendar.formatDate(event.start, "h:mm")+" - "+
                        $.fullCalendar.formatDate(event.end, "h:mm");

                    element.qtip({
                        content:{
                            text: '<h5>' + courseName + ', Section ' + sectionNum + '</h5>' +
                                '<p>' + eventTime + '</p>' +
                                '<p>' + event.description + '</p>'
                        },
                        position:{
                            my:'top center',
                            at:'bottom center'
                        },
                        show:'mouseover'
                    });
                }
            }
        };

        // this is never used but it's necessary for the the fullcalendar plugin
        // instead, i just dynamically render and remove events
        $scope.eventSources = [];

        $scope.addCourseToCalendar = function(course, classPeriods, classLocation, color, className) {
            for (var k in classPeriods) {
                var timespan = k.split('-');
                var days = classPeriods[k];
                days = days.split(/[ ,]+/);
                for (var day in days) {
                    day = days[day];
                    // ["1", "00"]
                    var startTime = (timespan[0]).split(':');
                    var endTime = (timespan[1]).split(':');

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

                    var event = new Object();
                    event.title = course;
                    event.start = new Date(y, m, d + dayOffsets[day], startHrs, startMins);
                    event.end = new Date(y, m, d + dayOffsets[day], endHrs, endMins);
                    event.description = classLocation;
                    event.allDay = false;

                    // when you add a section, new conflicts may be introduced
                    var conflictingEvents = $scope.eventConflicts(event, null);
                    if (conflictingEvents.length) {
                        color = conflictingEventColor;
                        if (className != 'temp')
                            $scope.updateConflictsOnAdd(conflictingEvents);
                    }

                    event.color = color;
                    event.className = className;

                    calendar.fullCalendar('renderEvent', event);
                }
            }
        };

        $scope.eventConflicts = function(event, conflictToIgnore) {
            var start = new Date(event.start);
            var end = new Date(event.end);

            var overlap = $('#calendar').fullCalendar('clientEvents', function(ev) {
                if (ev == event)
                    return false;
                var estart = new Date(ev.start);
                var eend = new Date(ev.end);

                // overlaps and it's not the same course
                return ((Math.round(estart)/1000 < Math.round(end)/1000 && Math.round(eend) > Math.round(start)) &&
                    (ev.title.split('-')[0] != event.title.split('-')[0]) &&
                    (conflictToIgnore != ev.title));
            });

            return overlap;
        };

        $scope.isTemp = function(event) {
            return (event.className == 'temp');
        };

        $scope.getCourseName = function(event) {
            return event.title.split('-')[0];
        };

        $scope.$on("courseAdded", function (event, args) {
            var course = args.course;
            var color = args.color;
            var className = args.className;
            var classPeriods = args.classPeriods;
            var classLocation = args.classLocation;
            $scope.addCourseToCalendar(course, classPeriods, classLocation, color, className);
        });

        $scope.$on("courseRemoved", function (event, args) {
            var calendar = $('#calendar');

            if (!args.temp) {
                $scope.updateConflictsOnRemove(args.course);
            }

            calendar.fullCalendar('removeEvents', function (calEvent) {
                return (calEvent.title == args.course) &&
                    ((args.temp && $scope.isTemp(calEvent)) || (!args.temp && !$scope.isTemp(calEvent)));
            });
        });

        // origCourse -- will update any conflicts touching the conflict source (which is a course name, no section, like "CS142")
        // in the case we are removing a course, we'll need to ignore that course
        // this is a complicated & expensive process because the courses left over may or may not be affected by the removed course
        $scope.updateConflictsOnRemove = function(conflictSourceCourseName) {
            var conflictSource = conflictSourceCourseName.split('-');
            var conflictSourceCourse = conflictSource[0];
            var coursesUpdated = [];
            calendar.fullCalendar('clientEvents', function(event) {
                if (conflictSourceCourse == $scope.getCourseName(event)) {
                    var conflictingEvents = $scope.eventConflicts(event, null);
                    for (var i = 0, len = conflictingEvents.length; i < len; i++) {
                        var course = $scope.getCourseName(conflictingEvents[i]);
                        if (coursesUpdated.indexOf(course) == -1) {
                            coursesUpdated.push(course);
                            $scope.$broadcast("changeEventColor", {course: course, color: eventColor, toIgnore: conflictSourceCourseName});
                        }
                    }
                }
            });
        };

        // check for any new conflicts introduced and change their color
        $scope.updateConflictsOnAdd = function(conflictingEvents) {
            calendar.fullCalendar('clientEvents', function(event) {
                if (conflictingEvents.indexOf(event) != -1) {
                    event.color = conflictingEventColor;
                    calendar.fullCalendar('updateEvent', event);
                }
            });
        };

        // changes *all* events of a certain course (ignoring section), checking for conflicts
        // this is a big expensive if you don't need to check for conflicts
        $scope.$on("changeEventColor", function (event, args) {
            var calendar = $('#calendar');
            var events = calendar.fullCalendar('clientEvents');
            var conflictToIgnore = args.toIgnore;
            for (var i = 0, len = events.length; i < len; i++) {
                event = events[i];
                if ($scope.getCourseName(event) == args.course) {
                    event.color = $scope.eventConflicts(event, conflictToIgnore).length && args.color != greyedOutEventColor ? conflictingEventColor : args.color;
                    calendar.fullCalendar('updateEvent', event);
                }
            }
        });

        $scope.$on("removeAllCourses", function(event) {
            $('#calendar').fullCalendar('removeEvents', function (calEvent) {
                return true;
            });
        });

    }]);

function loadRegistrationPage() {
    var domain = location.protocol+'//'+location.hostname+(location.port ? ':'+location.port: '');
    $("#registration-iframe").attr("src", domain + '/byu-login-landing.html')
}

function clearCaptchaCookies() {
    document.cookie = "recaptchaChallenge" + '=; expires=Thu, 01 Jan 1970 00:00:01 GMT;';
    document.cookie = "recaptchaAnswer" + '=; expires=Thu, 01 Jan 1970 00:00:01 GMT;';
}