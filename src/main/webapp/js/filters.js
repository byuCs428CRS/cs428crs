'use strict';

classregControllers.controller('FilterCtrl', ['$scope', '$rootScope', function ($scope,$rootScope) {
	$rootScope.clearCourseFilter = function(){
		$rootScope.creditFilter = "";
		$rootScope.profName = "";
		$('.courseFilterOption').prop('checked', false);
		$('.courseFilterOption').val('');
	};
	
	$rootScope.clearSectionFilter = function(){
		$(".sectionCheck").prop("checked", false);
		$rootScope.timeIncludes = [];
		$rootScope.dayIncludes = [];
	};
	
}]);