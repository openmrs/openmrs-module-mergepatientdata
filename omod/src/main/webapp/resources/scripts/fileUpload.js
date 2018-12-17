var app = angular.module('mpdApp', ['ngDialog']);
app.controller('mpdAppCont', function($scope, ngDialog) {
	$scope.showFileUploadDialog = function() {
		// close the old dialog
		$scope.chooseUploadUiDialog.close();
		ngDialog.openConfirm({
			template : 'mpd-fileUploadTemplate',
			className : 'ngdialog-theme-default',
			scope : $scope
		});
	},
	$scope.chooseUploadInterface = function() {
		$scope.chooseUploadUiDialog = ngDialog.open({
			template : 'mpd-choose-upload-ui-template',
			className : 'ngdialog-theme-default',
			scope : $scope
		});
	},
	$scope.processFileUpload = function() {
		// get selected operation
		var selectedOperation = jQuery('input[name=upload-type]:checked').val();
		if (selectedOperation === 'web') {
			return $scope.showFileUploadDialog();
		} else {
			// submit form
			jQuery('#choose-upload-ui-form').submit();
		}
	},
	jQuery(document).ready(function() {
		jQuery.ajax({
            url: "/openmrs/ws/rest/mpd/workingDirPath",
            type: "GET",
            dataType: "text"
        }).done(function(url) {
 			$scope.path = url;
        });
	
	});
});

var enableElement = function(id) {
	jQuery(id).prop('disabled', false);	
}