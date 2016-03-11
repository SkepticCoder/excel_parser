/**
 * Created by Dmitrii on 10.03.2016.
 */
'use strict';

App.controller('PageController', ['$scope', 'PageService', function($scope, PageService) {
    var self = this;
    self.page = [];

    self.fetchAllRecords = function(){
        PageService.fetchAllRecords()
            .then(
            function(d) {
                self.page = d;
            },
            function(errResponse){
                console.error('Error while fetching records');
            }
        );
    };

    self.fetchAllRecords();
}
])

//App.controller('uploadCtrl', ['$scope', 'fileUpload', function($scope, fileUpload){
//
//    $scope.uploadFile = function(){
//        var file = $scope.myFile;
//        console.log('file is ' );
//        console.dir(file);
//        var uploadUrl = "/fileUpload";
//        fileUpload.uploadFileToUrl(file, uploadUrl);
//    };
//
//}]);