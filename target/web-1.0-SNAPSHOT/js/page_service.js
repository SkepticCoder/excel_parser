/**
 * Created by Dmitrii on 10.03.2016.
 */
'use strict';

App.factory('PageService', ['$http', '$q', function($http, $q){

    return {

        fetchAllRecords: function() {
            return $http.get('page')
                .then(
                function(response){
                    return response.data;
                },
                function(errResponse){
                    console.error('Error while fetching records');
                    return $q.reject(errResponse);
                }
            );
        },

    };

}]);

//App.factory('fileUpload', ['$http', function ($http) {
//    this.uploadFileToUrl = function(file, uploadUrl){
//        var fd = new FormData();
//        fd.append('file', file);
//        $http.post(uploadUrl, fd, {
//            transformRequest: angular.identity,
//            headers: {'Content-Type': undefined}
//        })
//            .success(function(){
//            })
//            .error(function(){
//            });
//    }
//}]);