'use strict';

var tempEventColor = '#4FB14F';
var eventColor = '#428bca';
var greyedOutEventColor = '#DDD';
var conflictingEventColor = '#C25151';

/* Controllers */
var classregControllers = angular.module('classregControllers', []);

classregControllers.controller('HeaderController', ['$scope', '$http', '$rootScope', '$location',
    function($scope, $http, $rootScope, $location) {
        $scope.isActive = function(viewLocation) {
            return viewLocation === $location.path();
        };
		
		//check if logged in
		$http.get('auth/service').success(function (data) {
            if(data._id!==null){
				$rootScope.loggedIn = true;
				$rootScope.username = data._id;
				$rootScope.schedules = data.schedules;
			}
        });
    }
]);

classregControllers.controller('CourseListCtrl', ['$scope', '$http', '$cookies', '$rootScope', '$interval', '$timeout','classPeriodParser',
    function ($scope, $http, $cookies, $rootScope, $interval, $timeout, classPeriodParser) {

		$scope.loadSemesterCourses = function(data){
			$scope.departments = []
            $scope.courses = data.courses;
			angular.forEach(data.courses, function (apiCourse) {
				if ($scope.departments.indexOf(apiCourse.departmentCode) < 0)
                    $scope.departments.push(apiCourse.departmentCode)
                     angular.forEach(apiCourse.sections, function (apiSection) {
                         if( apiSection.pid === "undefined" || apiSection.pid === undefined
                                        || apiSection.pid === null || apiSection.pid === "" ){
                            apiSection.professor = (apiSection.professor==null)? 'Staff' : apiSection.professor;
                            apiSection.rateMyProfessorQuery = "search.jsp?query=BYU%20" + apiSection.professor.split(",")[0] + " " + apiSection.professor.split(",")[1]
                          }else
                            apiSection.rateMyProfessorQuery = "ShowRatings.jsp?tid="+apiSection.pid
                     });

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
                        apiCourse.creditRange = minCredits
                    else
                        apiCourse.creditRange = minCredits+' - '+maxCredits
                }

                if (apiCourse.department == null)
                    apiCourse.department = '';
                if (apiCourse.courseName == null)
                    apiCourse.courseName = '';
			});
			if($scope.importedClasses!=undefined)
				$scope.loadByuPlannedCoursesToSidebar();
		}
		
		$scope.semesterNames = function(year){
			var sType = year.substring(4,5);
			var sYear = year.substring(0,4);
			if(sType === "1"){
				return "Winter "+sYear;
			}
			if(sType === "3"){
				return "Spring "+sYear;
			}
			if(sType === "4"){
				return "Summer "+sYear;
			}
			if(sType === "5"){
				return "Fall "+sYear;
			}
			return year;
		};
		
		$scope.loadSemeseters = function(){
			$scope.previouslySavedPlan = {}
			$http.get('public-api/semesters').success(function (data) {
				$scope.semesters = [];
				for(var i=0;i<data.length;i++){
					var se = {};
					se["name"] = $scope.semesterNames(data[i]);
					se["value"] = data[i];
					if(parseInt(se["value"].substring(0,4))>=(new Date().getFullYear()))
						$scope.semesters.push(se);
				}
				$scope.selectedSemester = $scope.semesters[$scope.semesters.length-1];
				$scope.changeCurrentSemester($scope.selectedSemester);
			});
		};
		
		$scope.loadPreviouslySavedMyMap = function(){
			$http.get('public-api/loadCoursesFromMyMap').success(function (data) {
				$scope.importedClasses = JSON.parse(data.mymapPlanned);
				$scope.loadByuPlannedCoursesToSidebar();
			});
		};
		
		$scope.loadPreviouslySavedSchedule = function(semId){
			$scope.$broadcast("removeAllCourses");
			if($scope.plannedSemesterSchedules[semId]==undefined)
				$http.get('public-api/loadSchedule/'+semId).success(function (data) {
					if(data.classes!==undefined){
						$scope.plannedSemesterSchedules[semId] = data;
						angular.forEach($scope.plannedSemesterSchedules[semId].classes, function (section) {
							$scope.$broadcast("courseAdded", {course: $scope.getCID(section), classPeriods: section.timePlaces, color: eventColor });
						});
					}
				});
			else
				angular.forEach($scope.plannedSemesterSchedules[semId].classes, function (section) {
					$scope.$broadcast("courseAdded", {course: $scope.getCID(section), classPeriods: section.timePlaces, color: eventColor });
				});
		};
		
        // popular courses to be shown when there are no filters applied
        $scope.popularCourses = ['REL A121', 'REL A122', 'A HTG100', 'BIO100', 'C S142', 'MATH112', 'MATH113', 'WRTG150', 'CHEM111', 'CHEM101', 'PHSCS121', 'COMMS101', 'ACC200', 'EL ED202'];
		$scope.defaultCourses = $scope.popularCourses;
		
        var autoSave = $interval(function () {
            $scope._savePlan()
        }, 5000);
        $scope.$on('$destroy', function () { $interval.cancel(autoSave); });

        $scope.dismissLoadingOverlay = function() {
            $('#loading-overlay').css('display', 'none');
        };

        $scope.initStuff = function() {
			$scope.semesters = [];
            $scope.courseLevels = ['100', '200', '300', '400', '500', '600'];
			$scope.currentSemesterId = "20155"
            $scope.currentSemester = "Fall 2015" //updated after the load semesters is called
            $scope.initPlannedCourses();
            $scope.saved = false;
            $scope.filterOptions = {
                levels: {}
            };
            $scope.sortBy = ['departmentCode','courseNumber'];
            $scope.filteredDept = '';
            $scope.selectedCourse = undefined;
			
			if($rootScope.initLoadingCourses!=true){//prevent 2 calls during init
				$scope.loadSemeseters();
				$scope.loadPreviouslySavedMyMap();
			}
			$rootScope.initLoadingCourses = true;
			
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
            $scope.plannedSemesterSchedules = [];
            $scope.sumPlannedCredits = 0.0;
            $scope.$broadcast("removeAllCourses");
        };

        $scope.initStuff();

        $scope.allFilter = function(course) {
            return (($scope.filterText && $scope.filterText.length) || 
					($rootScope.creditFilter && $rootScope.creditFilter.length) ||
					($rootScope.profName && $rootScope.profName.length > 2) ||
					($scope.filteredDept && $scope.filteredDept.length) || 
					$scope.defaultCourses.indexOf(course.departmentCode + course.courseNumber) > -1);
        };
		
		$scope.profTeachesSection = function(course,profName){
			var found = false;
			angular.forEach(course.sections, function (apiSection) {
				if(!found)
					if(angular.lowercase(apiSection.professor).indexOf(angular.lowercase(profName)) > -1){
							found = true;
					}
			 });
			 return found;
		};
		
        // Searches both course name and course description fields
        $scope.searchQueryFilter = function(course) {
            var q = angular.lowercase($scope.filterText);
            return (!angular.isDefined(q) || q == "" ||
                (angular.lowercase(course.courseName).indexOf(q) >= 0 ||
                    angular.lowercase(course.departmentCode.replace(/\s/g, '')).indexOf(q) >= 0 ||
                    angular.lowercase(course.department).indexOf(q) >= 0 ||
                    angular.lowercase(course.courseNumber).indexOf(q) >= 0 ||
                    angular.lowercase(course.departmentCode + course.courseNumber).indexOf(q.replace(/\s/g,'')) >= 0 ||
                    angular.lowercase(course.departmentCode.replace(/\s/g,'') + course.courseNumber).indexOf(q.replace(/\s/g,'')) >= 0))
					&&
						(!angular.isDefined($rootScope.creditFilter) || 
						(angular.isDefined($rootScope.creditFilter) && $rootScope.creditFilter.length==0) || 
						course.credit==$rootScope.creditFilter)
					&&
						(!angular.isDefined($rootScope.profName) ||
						(angular.isDefined($rootScope.profName) && $rootScope.profName.length < 3) ||
						($rootScope.profName.length > 2 && $scope.profTeachesSection(course,$rootScope.profName)));
        };
		
        //Filters by department
        $scope.departmentFilter = function(course) {
            return $scope.filteredDept === /* all departments */ '' || $scope.filteredDept === course.departmentCode
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

        $scope.updateSelectedCourse = function(section) {
			var thisCourse = $scope.getCourseObject(section.courseID);
            var course = $.grep($scope.courses, function(c){ return c.departmentCode == thisCourse.departmentCode && c.courseNumber == thisCourse.courseNumber; });
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
                result += prefix+classPeriods[key].day+' '+classPeriods[key].startTime+'-'+classPeriods[key].endTime
                prefix = ', '
            }
            return result
        };
		
		$scope.classPeriodsToLocation = function(classPeriods) {
            var result = ''
            var prefix = ''
            for(var key in classPeriods) {
                result += prefix+classPeriods[key].location
                prefix = ', '
            }
            return result
        };
		
		$rootScope.timeIncludes = [];
		$rootScope.dayIncludes = [];
		
		$scope.includeTimeOfDay = function(timeFrame) {
			var i = $.inArray(timeFrame, $rootScope.timeIncludes);
			if (i > -1) {
				$rootScope.timeIncludes.splice(i, 1);
			} else {
				$rootScope.timeIncludes.push(timeFrame);
			}
		}
		
		$scope.includeDay = function(day) {
			var i = $.inArray(day, $rootScope.dayIncludes);
			if (i > -1) {
				$rootScope.dayIncludes.splice(i, 1);
			} else {
				$rootScope.dayIncludes.push(day);
			}
		}
		
		$scope.sectionFilter = function(section) {
			if ($rootScope.timeIncludes.length > 0 || $rootScope.dayIncludes.length > 0 ){
				for(var key in section.timePlaces) {
					if($rootScope.dayIncludes.length > 0){
						var days;
						if(section.timePlaces[key].day=="TTh"){
							days = ["T","Th"];
						}else if(section.timePlaces[key].day=="Th"){
							days = ["Th"];
						}else{
							days = section.timePlaces[key].day.split("");
						}
						for(var index=0; index < days.length; ++index){
							if ($.inArray(days[index], $rootScope.dayIncludes) < 0)
								return;
						}
					}
					if ($rootScope.timeIncludes.length > 0){
						var startTimeLen = section.timePlaces[key].startTime.length;
						var startRawTime = section.timePlaces[key].startTime.substring(0,startTimeLen-2); //section.timePlaces[key].startTime in the format "09:50am"  
						var startRawAMPM = section.timePlaces[key].startTime.substring(startTimeLen-2,startTimeLen);
						var startTime = new Date(Date.parse("2015/01/01 "+startRawTime+" "+startRawAMPM)); //converted to comparable date
						var startsInTimeConstraint = false;
						var compareTime;
						if($.inArray("Evening",$rootScope.timeIncludes)>-1){
							compareTime = new Date(Date.parse("2015/01/01 5:00 PM"));
							if(compareTime<startTime) 
								startsInTimeConstraint = true;
						}
						if($.inArray("Afternoon",$rootScope.timeIncludes)>-1){
							compareTime = new Date(Date.parse("2015/01/01 5:00 PM"));
							var compareTime2 = new Date(Date.parse("2015/01/01 11:59 AM"));
							if(startTime<compareTime && compareTime2<startTime) 
								startsInTimeConstraint = true;
						}
						if($.inArray("Morning",$rootScope.timeIncludes)>-1){
							compareTime = new Date(Date.parse("2015/01/01 11:59 AM"));
							if(startTime<compareTime) 
								startsInTimeConstraint = true;
						}
						if(!startsInTimeConstraint)return;
					}
				}
			}
			return section;
		}

        // check if a course is planned, where cid is a course/section id that looks like this: "CS256-1" for section 1
        $scope.isPlanned = function(cid) {
			if($scope.plannedSemesterSchedules[$scope.currentSemesterId]!== undefined)
            for (var i = 0; i < $scope.plannedSemesterSchedules[$scope.currentSemesterId].classes.length; i++) {
                if ($scope.getCID($scope.plannedSemesterSchedules[$scope.currentSemesterId].classes[i]) == cid) {
                    return true;
                }
            }
            return false;
        };

        // gets the planned section of a course. if no planned section, returns null.
        $scope.getPlannedCourse = function(dept, num) {
            for (var i = 0; i < $scope.plannedSemesterSchedules[$scope.currentSemesterId].classes.length; i++) {
				var thisCourse = $scope.getCourseObject($scope.plannedSemesterSchedules[$scope.currentSemesterId].classes[i].courseID);
                if (thisCourse.departmentCode == dept && thisCourse.courseNumber == num) {
                    return $scope.plannedSemesterSchedules[$scope.currentSemesterId].classes[i];
                }
            }
            return null;
        };
		
		$scope.loadByuCourses = function(){
			if($rootScope.loggedIn){
				$('#loadByuCoursesModal').show();
			}else{
				alert('please login before loading classes');
			}
		};
		
		$scope.changeCurrentSemester = function(selected){
			$scope.sortBy = ['departmentCode','courseNumber'];
            $scope.filteredDept = '';
            $scope.selectedCourse = undefined;
			$scope.currentSemesterId = selected.value;
			$scope.currentSemester = selected.name;
			$scope.isLoadingCourses = true;
			$http.get('public-api/courses/'+selected.value).success(function (data) {
				$scope.loadPreviouslySavedSchedule(selected.value);
				$scope.loadSemesterCourses(data);
				$scope.isLoadingCourses = false;
			});
		};
		
		$scope.loadByuPlannedCoursesToSidebar = function(){
			var plans = $scope.importedClasses.plans;
			$scope.defaultCourses = [];
			for(var i=0; i< plans.length; i++){
				if(plans[i]["yearTerm"]==$scope.selectedSemester.value){
					var courseTitle = plans[i]["title"];
					$scope.defaultCourses.push(courseTitle.substr(0,courseTitle.length-4) + courseTitle.substr(courseTitle.length-3,3));
				}
			}
			if($scope.defaultCourses.length==0)
				$scope.defaultCourses = $scope.popularCourses;
		};
				
		$scope.loadByuCoursesOkay = function(){
			try{
				$scope.importedClasses = JSON.parse($('#jsonPlannedClasses')[0].value);
				$http.post('public-api/saveCoursesFromMyMap', {mymapPlanned:$scope.importedClasses}).success(function (data) {
					//success!
				});
				$scope.loadByuPlannedCoursesToSidebar();
			}catch(e){
				//silently die, should tell users we couldn't parse their classes.
			}
			$('#loadByuCoursesModal').hide();
		};

        $scope.addCourseToPlan = function(course, section) {
            $scope.saved = false;
            $scope.added = false;
			if($scope.plannedSemesterSchedules[$scope.currentSemesterId]==undefined || $scope.plannedSemesterSchedules[$scope.currentSemesterId] == null){
				var newSemester = new Object();
				newSemester._id = $scope.currentSemesterId;
				newSemester.semesterID = $scope.currentSemesterId;
				newSemester.name = $scope.currentSemester;
				newSemester.classes = [];
				$scope.plannedSemesterSchedules[$scope.currentSemesterId] = newSemester;
			}
			
            // when the class is added, remove its temporary calendar event
            $scope.hideTempEvent(course, section);
            var fullCourseName = course.departmentCode + ' ' + course.courseNumber;
            var cid = fullCourseName + "-" + section._id;
            var classLocation = section.buildingAbbreviation + ' ' + section.room;

            $scope.$broadcast("courseAdded", {course: cid, classPeriods: section.timePlaces, color: eventColor });

            var theCourse = $scope.getPlannedCourse(course.departmentCode, course.courseNumber);
            if (theCourse) {
                // if there's already another section of the same course, just replace it
                $scope.removeCourseFromPlan(theCourse);
            }

            if (!$scope.isPlanned(cid)) {
                var plannedCourse = new Object();
                //plannedCourse.cid = cid;
                //plannedCourse.department = course.department;
				//plannedCourse.departmentCode = course.departmentCode;
                //plannedCourse.courseNumber = course.courseNumber;
                plannedCourse._id = section._id;
                plannedCourse.professor = section.professor;
                plannedCourse.timePlaces = section.timePlaces;
                plannedCourse.courseID = course.courseID;
                //plannedCourse.newTitleCode = course.newTitleCode;
                plannedCourse.credits = section.credits;
                plannedCourse.sectionType = section.sectionType;
                //plannedCourse.courseName = course.courseName;
				plannedCourse.semesterID = $scope.currentSemesterId;
                $scope.plannedSemesterSchedules[$scope.currentSemesterId].classes.push(plannedCourse);
            }
            $scope.savePlan();



            $scope.sumPlannedCredits += Number(section.credits);

            // put a cap on generating schedules or it will get way out of hand
            if($scope.plannedSemesterSchedules[$scope.currentSemesterId].classes.length >1 && $scope.sumPlannedCredits < 18) {
                $scope.generateAlternateSchedules();
            }

            var elId = '#plannedCourse-' + ($scope.plannedSemesterSchedules[$scope.currentSemesterId].classes.length - 1).toString();

            setTimeout(function() {
                $(elId).effect("highlight", {}, 1000);
            }, 100);
        };

        $scope.generateAlternateSchedules = function()
        {
            $scope.generatedSchedules = []; // reset generated schedules
            var courses = $scope.getCourses();
            $scope.exploreCourses([], courses);
            if($scope.generatedSchedules.length == 0)
            {
                return;
            }

            angular.forEach($scope.generatedSchedules, function(schedule){
                var emptySpace = $scope.findEmptySpace(schedule);
                schedule.emptySpace = emptySpace.empty;
                schedule.freeDays = emptySpace.freeDays;
            });

            $scope.generatedSchedules.sort(function(a,b) {
                return a.emptySpace - b.emptySpace;
            })

            // Can sort by free days as well
            //$scope.generatedSchedules.sort(function(a,b) {
            //    return b.freeDays - a.freeDays;
            //})

            console.log("Best Schedule is:");
            console.log($scope.generatedSchedules[0]);

        }

        /**
         * Finds the number of hours that are not spent in class from the start of the first class to the end of the last class
         * @param section
         * @return number of hours that are not spent in class
         */
        $scope.findEmptySpace = function(schedule)
        {
            var totalEmptyMins = 0;
            var freeDays = 0;
            var days = {"M":[], "T":[], "W":[], "Th":[], "F":[]};

            // place each class period in an imaginary calendar
            angular.forEach(schedule, function(section){
                var parsedPeriods = classPeriodParser.parse(section.timePlaces); // TODO: This should either be done on the server or done right when it comes from the server.
                // we make too many of these calls

                // assign each class period to its respective day
                angular.forEach(parsedPeriods, function(classPeriod)
                {
                    days[classPeriod.day].push(classPeriod);
                });
            });

            // iterate over each day of the "calendar" and find how much space is between each class
            var previousDaysClass = null;
            for(var dayLetter in days){
                var day = days[dayLetter];
                // sort by start time (and end time if necessary)
                day.sort(function(a,b){
                    var startDiff = (a.startHrs*60+ a.startMins) - (b.startHrs*60+ b.startMins);
                    if(startDiff == 0) {
                        // start times equal
                        return (a.endHrs*60+ a.endMins) - (b.endHrs*60+ b.endMins);
                    }

                    return startDiff;
                });

                // TODO: How do I get a schedule that prefers classes all on the same day - make this a separate objective
                if(day.length == 0)
                {
                    freeDays++;
                }
                if(day.length == 1)
                {
                    if(previousDaysClass == null){
                        totalEmptyMins += 18*60; // penalize for being on a different day than other days - it's better than a conflicting schedule, but worse than late/early
                    }
                    else{
                        totalEmptyMins += (24*60 - $scope.getTimeDifference(day[0], previousDaysClass)); // If you have a late class on M and early on T, it is better
                    }
                }

                for(var i=0; i < day.length-1; i++)
                {
                    var classPeriod = day[i];
                    var nextClass = day[i+1];

                    totalEmptyMins += $scope.getTimeDifference(classPeriod, nextClass);
                }
                previousDaysClass = nextClass;

            }

            return {empty:(totalEmptyMins/60.0), freeDays:freeDays};
        }

        $scope.getCourses = function()
        {
            var courses = [];
            angular.forEach($scope.plannedSemesterSchedules[$scope.currentSemesterId].classes, function (section) {
                courses.push($scope.getCourseObject(section.courseID));
            });

            return courses;
        }

        $scope.getTimeDifference = function(classPeriod, nextClass)
        {
            var nextStartTime = nextClass.startHrs*60 + nextClass.startMins;
            var classEndTime = classPeriod.endHrs*60 + classPeriod.endMins;

            var emptyMins = nextStartTime - classEndTime; // find difference from when one class ends to when another class begins
            if(emptyMins < 0) { // has same (or conflicting) start time as the previous one
                emptyMins += (24*60) // penalize by a whole day - an arbitrarily large amount for conflicting schedules
            }

            return emptyMins;
        }


        /**
         * DFS to explore all the sections in coursesLeftToPlan
         * @param currentSchedule the current schedule
         * @param coursesLeftToPlan which courses to plan for
         */
        $scope.exploreCourses = function(currentSchedule, coursesLeftToPlan)
        {
            if(coursesLeftToPlan.length == 0)
            {
                $scope.generatedSchedules.push(currentSchedule);
                return;
            }

            var newCoursesLeftToPlan = coursesLeftToPlan.slice(0); //
            var course = newCoursesLeftToPlan.shift(); // removes first element from array
            angular.forEach(course.sections, function(section){

                var newSchedule = currentSchedule.slice(0); // deep copy not needed since we're not changing the array elements
                newSchedule.push(section); // make sure currentSchedule doesn't get added to
                $scope.exploreCourses(newSchedule, newCoursesLeftToPlan);
            });
        }

        $scope.removeCourseFromPlan = function(course) {
            $scope.saved = false;
            $scope.added = false;

            $scope.$broadcast("courseRemoved", {course: $scope.getCID(course), temp: false});

            var i = $scope.plannedSemesterSchedules[$scope.currentSemesterId].classes.indexOf(course);
            if (i > -1)
                $scope.plannedSemesterSchedules[$scope.currentSemesterId].classes.splice(i, 1);

            $scope.savePlan()
            $scope.sumPlannedCredits -= course.credits;
        };

        $scope.showTempEvent = function(course, section) {
            var fullCourseName = course.departmentCode + ' ' + course.courseNumber;
            var cid = fullCourseName + "-" + section._id;
            if (!$scope.isPlanned(cid)) {
                // change color of other sections of this course to gray
                $scope.$broadcast("changeEventColor", {course: cid.split('-')[0], color: greyedOutEventColor});
                // show temp event on the calendar
                $scope.$broadcast("courseAdded", {course: cid, classPeriods: section.timePlaces, color: tempEventColor, className: 'temp' });
            }
        };

        $scope.hideTempEvent = function(course, section) {
            var fullCourseName = course.departmentCode + ' ' + course.courseNumber;
            var cid = fullCourseName + "-" + section._id;
            $scope.$broadcast("courseRemoved", {course: cid, temp: true});
            // change color of other sections of this course back to default
            $scope.$broadcast("changeEventColor", {course: cid.split('-')[0], color: eventColor});
        };
		
		$scope.savePlanToServer = function(semId){
			var scheduleToSend = angular.toJson($scope.plannedSemesterSchedules[$scope.currentSemesterId]);
			$http.post('public-api/saveSchedule/'+semId, scheduleToSend).success(function (data) {

					//success!
				});
		};
		
		$scope.getCourseObject = function(inCourseID){
			var thisCourse = null;
			angular.forEach($scope.courses, function (course) {
				if(course.courseID == inCourseID) 
					thisCourse = course;
			});
			return thisCourse;
		};
		
		$scope.getCID = function(inSection){
			var thisCourse = $scope.getCourseObject(inSection.courseID);
			var fullCourseName = thisCourse.departmentCode + ' ' + thisCourse.courseNumber;
            return fullCourseName + "-" + inSection._id;
		};

        $scope.savePlan = function() {
			$scope.savePlanToServer($scope.currentSemesterId);
			//this classes object needs to be set in the cookie this way for the registration process
			//look at register.js before making any changes.
            var classes = []
            for( var i=0; i<$scope.plannedSemesterSchedules[$scope.currentSemesterId].classes.length; i++ ) {
				var thisCourse = $scope.getCourseObject($scope.plannedSemesterSchedules[$scope.currentSemesterId].classes[i].courseID);
                var klass = {}
                klass.e = '@AddClass';
//                klass.e = '@ConfirmWaitlist'
                klass.courseNumber = thisCourse.courseID;
                klass.titleCode = thisCourse.newTitleCode;
                klass.credits = $scope.plannedSemesterSchedules[$scope.currentSemesterId].classes[i].credits;
                klass.sectionType = $scope.plannedSemesterSchedules[$scope.currentSemesterId].classes[i].sectionType;
                klass.sectionId = $scope.plannedSemesterSchedules[$scope.currentSemesterId].classes[i]._id;
                klass.dept = thisCourse.departmentCode;
                klass.title = thisCourse.courseName;
				klass.term = $scope.currentSemesterId;
                classes.push(klass)
            }
            $cookies.classes = JSON.stringify(classes)
        };

        $scope._savePlan = function() {
            $scope.saved = true;
        };

        $scope.registerClasses = function() {
            $cookies.c = "regOfferings";
            $scope.savePlan()

            var domain = location.protocol+'//'+location.hostname+(location.port ? ':'+location.port: '');
            var query = '?service='+encodeURIComponent(domain+'/byu-login-landing.html');
            var url = 'https://cas.byu.edu/cas/login';

            var regFrame = $("#registration-iframe");
            regFrame.attr("src", url + query);
            // $scope.initPlannedCourses();
            // $scope.added = true;
        };
    }]);

classregControllers.controller('CalendarCtrl', ['$scope', 'classPeriodParser',
    function($scope, classPeriodParser) {
        var calendar = $('#calendar');
        var sunday = new Date(moment().startOf('week'));
        var d = sunday.getDate();
        var m = sunday.getMonth();
        var y = sunday.getFullYear();
        var dayOffsets = {'M': 1, 'T': 2, 'W': 3, 'Th': 4, 'F': 5};

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
                                '<p>' + eventTime + '</p>' //+
                                //'<p>' + event.description + '</p>'
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

            var parsedPeriods = classPeriodParser.parse(classPeriods);
            for (var k in parsedPeriods) {
                var period = parsedPeriods[k];
                var event = new Object();
                event.title = course;
                event.start = new Date(y, m, d + dayOffsets[period.day], period.startHrs, period.startMins);
                event.end = new Date(y, m, d + dayOffsets[period.day], period.endHrs, period.endMins);
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

function getParameterByName(name) {
	name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
	var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
		results = regex.exec(location.search);
	return results === null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
}
if(getParameterByName('ticket')){
	window.location.replace('/auth/service?ticket='+getParameterByName('ticket'));
}

$(function() {
	$('#loginButton')[0].href = "https://cas.byu.edu/cas/login?service="+encodeURIComponent("http://"+window.location.host+"/");//must exactly match WebApplication.java:service
	$('#logoutButton').click(function(event){
		event.preventDefault();
		$.ajax('auth/logout').done(function () {
				location.replace('https://cas.byu.edu/cas/logout'); 
			});
	});
});
	
function loadRegistrationPage() {
    var domain = location.protocol+'//'+location.hostname+(location.port ? ':'+location.port: '');
    $("#registration-iframe").attr("src", domain + '/byu-login-landing.html')
}

function clearCaptchaCookies() {
    document.cookie = "recaptchaChallenge" + '=; expires=Thu, 01 Jan 1970 00:00:01 GMT;';
    document.cookie = "recaptchaAnswer" + '=; expires=Thu, 01 Jan 1970 00:00:01 GMT;';
}