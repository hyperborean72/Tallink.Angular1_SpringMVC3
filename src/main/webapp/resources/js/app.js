/**
 * @author: a.stepanov
 */

var mainApp = angular.module('tallinkApp', [
	'ngRoute',
	'ui.bootstrap',
	'tallinkApp.conference', 
	'tallinkApp.participant'
]).
config(['$routeProvider',function($routeProvider) {
	$routeProvider
		/*
		 .when('/error', {
			 templateUrl: 'conference/error',
			 controller: 'ConferenceCtrl'
		 })*/
		.otherwise({redirectTo: '/conference'});
 }]);