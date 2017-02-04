/**
 * @author: a.stepanov
 */
angular.module('tallinkApp.conference', [
	'ngRoute',
	'ui.bootstrap'
])
.config(['$routeProvider',function($routeProvider) {
	$routeProvider
		.when('/conference', {
			templateUrl: 'conference/all',
			controller: 'ConferenceCtrl'
		})
}]) 
.factory('ConferenceService', ['$http', function($http) {
    return {
        fetchConferences: function() {
            return $http.get('conference/conferenceList')
        }
    }
}])
.controller('ConferenceCtrl', function($scope, $http, ConferenceService) {

        $scope.editMode = false;

        $scope.conferences = [];
		$scope.conference_form = {};

  /* ----UI-Bootstrap Date Picker---- */
	  $scope.today = function() {
		$scope.conference_form.date = new Date();
	  };
	  $scope.today();

	  $scope.clear = function() {
		$scope.conference_form.date = null;
	  };

	  $scope.inlineOptions = {
		customClass: getDayClass,
		minDate: new Date(),
		showWeeks: true
	  };

	  $scope.dateOptions = {
		dateDisabled: disabled, //disabled is a function below. BUT WHAT IS argument 'data'? 
		formatYear: 'yy',
		maxDate: new Date(2020, 5, 22),
		minDate: new Date(),
		startingDay: 1
	  };

	   // Disable weekend selection
	  function disabled(data) {
		var date = data.date,
		  mode = data.mode;
		return mode === 'day' && (date.getDay() === 0 || date.getDay() === 6);
	  }

	  $scope.toggleMin = function() {
		$scope.inlineOptions.minDate = $scope.inlineOptions.minDate ? null : new Date();
		$scope.dateOptions.minDate = $scope.inlineOptions.minDate;
	  };

	  $scope.toggleMin();

	  $scope.open1 = function() {
		$scope.popup1.opened = true;
	  };

	  $scope.setDate = function(year, month, day) {
		$scope.conference_form.date = new Date(year, month, day);
	  };

	  $scope.formats = ['yyyy-MM-dd','dd-MMMM-yyyy', 'yyyy/MM/dd', 'dd.MM.yyyy', 'shortDate'];
	  $scope.format = $scope.formats[0];
	  $scope.altInputFormats = ['M!/d!/yyyy'];

	  $scope.popup1 = {
		opened: false
	  };

	  var tomorrow = new Date();
	  tomorrow.setDate(tomorrow.getDate() + 1);
	  var afterTomorrow = new Date();
	  afterTomorrow.setDate(tomorrow.getDate() + 1);
	  $scope.events = [
		{
		  date: tomorrow,
		  status: 'full'
		},
		{
		  date: afterTomorrow,
		  status: 'partially'
		}
	  ];

	  function getDayClass(data) {
		var date = data.date,
		  mode = data.mode;
		if (mode === 'day') {
		  var dayToCheck = new Date(date).setHours(0,0,0,0);

		  for (var i = 0; i < $scope.events.length; i++) {
			var currentDay = new Date($scope.events[i].date).setHours(0,0,0,0);

			if (dayToCheck === currentDay) {
			  return $scope.events[i].status;
			}
		  }
		}

		return '';
	  }

  /* -------- */


	$scope.listConferences = function(){
		var promise = ConferenceService.fetchConferences();

		promise.then(function(payload){
				$scope.conferences = payload.data;
			}
		);
	};


	$scope.addConference = function(conference) { // conference comes from the form
		$scope.resetError();
		$http.post('conference/addConference', conference).success(function() {
			$scope.listConferences();
			$scope.conference_form.name = '';
			$scope.conference_form.date = '';
		}).error(function() {
				$scope.setError('Невозможно добавить запись');
			});
	};

	$scope.updateConference = function(conference) { // conference comes from the form
		$scope.resetError();
		console.log("conference in update: " + conference);
		$http.put('conference/updateConference', conference).success(function() {
			$scope.listConferences();
			$scope.conference_form.name = '';
			$scope.conference_form.date = '';
			$scope.editMode = false;
		}).error(function() {
				$scope.setError('Невозможно обновить запись');
			});
	};


	$scope.editConference = function(conference) {// conference comes from the list
		$scope.resetError();

		$scope.conference_form = {
			id: conference[0],
			name: conference[1],
			date: conference[2]
		};

		JSON.stringify($scope.conference_form)
		console.dir("conference in edit: " + conference + "; conference_form: " + $scope.conference_form);
		$scope.editMode = true;
	};

	$scope.removeConference = function(id) {
		$scope.resetError();
		$http.delete('conference/removeConference/' + id).success(function() {
			$scope.listConferences();
		}).error(function() {
				$scope.setError('Невозможно удалить запись');
			});
	};

	$scope.resetError = function() {
		$scope.error = false;
		$scope.errorMessage = '';
	};

	$scope.setError = function(message) {
		$scope.error = true;
		$scope.errorMessage = message;
	};

	$scope.listConferences();

});