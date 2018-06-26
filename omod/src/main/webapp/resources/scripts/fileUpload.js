var app = angular.module('mpdApp', ['ngDialog']);
app.controller('mpdAppCont', function($scope, ngDialog) {
	$scope.showFileUploadDialog = function() {
		ngDialog.open({
			//template : '/' + $scope.getPartialsPath(OPENMRS_CONTEXT_PATH) + '/fileUpload.html',
			template : 'mpd-fileUploadTemplate',
			className : 'ngdialog-theme-default',
			controller : ['$scope', function($scope) {
				//TODO required logic goes here
			}],
			scope : $scope
		});
	},
	$scope.getPartialsPath = function(openmrsContextPath) {
		 return openmrsContextPath + '/ms/uiframework/resource/' + 'mergepatientdata' + '/partials';
	}
});