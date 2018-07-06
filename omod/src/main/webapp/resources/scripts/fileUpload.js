var app = angular.module('mpdApp', ['ngDialog']);
app.controller('mpdAppCont', function($scope, ngDialog) {
	$scope.showFileUploadDialog = function() {
		ngDialog.openConfirm({
			template : 'mpd-fileUploadTemplate',
			className : 'ngdialog-theme-default',
			scope : $scope
		});
	}
});
