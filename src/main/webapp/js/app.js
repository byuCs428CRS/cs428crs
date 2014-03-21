'use strict';

/* App Module */
var classregApp = angular.module('classregApp', [
  'ngRoute',
  'classregControllers',
  'ngCookies',
  'ngAnimate',
  'ui.calendar'
]).run(function($rootScope) {
    $rootScope.$rootScope = $rootScope;
});

classregApp.config(['$routeProvider',
  function($routeProvider) {
    $routeProvider.
      when('/home', {
        templateUrl: 'partials/home.html'
      }).
      when('/plan', {
        templateUrl: 'partials/plan.html'
      }).
      when('/about', {
      	templateUrl: 'partials/about.html'
      }).
      otherwise({
        redirectTo: '/home'
      });
  }]);

function getDisplayedTitle(event) {
    return event.title.split('-')[0];
}