/**
 * @author: a.stepanov
 */
angular.module('tallinkApp.participant', [
	'ngRoute',
	'ui.bootstrap',
	'tallinkApp.conference'
])
.config(['$routeProvider',function($routeProvider) {
	$routeProvider
		.when('/participant', {
			templateUrl: 'participant/all',
			controller: 'ParticipantCtrl'
		})
}])
.controller('ParticipantCtrl', function($scope, $http, ConferenceService) {

	$scope.editMode = false;

	$scope.participants = [];
	$scope.conferences = [];
	$scope.participant_form = {};
	$scope.conference = ''; //chosen from the select
		
	/* ----UI-Bootstrap Date Picker---- */
	  $scope.today = function() {
		$scope.participant_form.date = new Date();
	  };
	  $scope.today();

	  $scope.clear = function() {
		$scope.participant_form.date = null;
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
		$scope.participant_form.date = new Date(year, month, day);
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

	$scope.fetchParticipants = function() {

		$http.get('participant/participantList').success(function(participantList){

			$scope.participants = participantList;

			console.log("$scope.participants in ParticipantCtrl: " + $scope.participants);
			console.log("$scope.conferences in ParticipantCtrl: " + $scope.conferences);
		});
	};

	$scope.addParticipant = function(participant) { // participant comes from the form
		console.dir("participant to Add : "+participant);
		$scope.resetError();
		$http.post('participant/addParticipant', participant).success(function() {
			$scope.fetchParticipants();
			$scope.participant_form.name = '';
			$scope.participant_form.date = '';
			$scope.participant_form.conference = '';
		}).error(function() {
				$scope.setError('Невозможно добавить запись');
			});
	};

	$scope.updateParticipant = function(participant) { // participant comes from the form
		$scope.resetError();
		$http.put('participant/updateParticipant', participant).success(function() {
			$scope.fetchParticipants();
			$scope.participant_form.name = '';
			$scope.participant_form.date = '';
			$scope.participant_form.conference = '';
			$scope.editMode = false;
		}).error(function() {
				$scope.setError('Невозможно обновить запись');
			});
	};

	$scope.editParticipant = function(participant) { // participant comes from the list
		$scope.resetError();

		$scope.participant_form = {
			id:participant[0],
			name:participant[1],
			date:participant[2],
			conference:participant[3]
		}

		$scope.editMode = true;
	};

	$scope.removeParticipant = function(id) {
		$scope.resetError();
		$http.delete('participant/removeParticipant/' + id)
			.success(function() {
				$scope.fetchParticipants();
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
	$scope.fetchParticipants();
});