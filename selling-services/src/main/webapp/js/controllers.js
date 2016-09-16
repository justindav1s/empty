'use strict';

/* Controllers */

var sellingApp = angular.module('sellingApp', ['sellingApp.services']);

sellingApp.config(['$httpProvider', function($httpProvider) {
        $httpProvider.defaults.useXDomain = true;
        delete $httpProvider.defaults.headers.common['X-Requested-With'];
    }
]);

sellingApp.controller('PersonCtrl', ['$scope', 'PersonResource', function ($scope, PersonResource) {
   
	$scope.people = {};
	$scope.searchperson = {};
	$scope.editperson = {};
	$scope.formTitle = "New Person";
	$scope.formSubmitButtonTitle = "Save";
			
	PersonResource.list(function(response) {
      	console.log("Success", response);
		$scope.people = response;
    });
	
	$scope.search = function(personID) {
		console.log("search");
		console.log("personID :" + angular.toJson(personID, false));
     	
		PersonResource.read({pid:personID}, function(response) {
			console.log("Success", response);
			$scope.searchperson = response;
    	});   	
    };
    
    $scope.edit = function(person)	{
    	console.log("edit");
    	$scope.editperson = angular.copy(person);
    	$scope.formTitle = "Update Person";
    	$scope.formSubmitButtonTitle = "Update";  	
    };
    
    $scope.createOrUpdate = function(person)	{
    	console.log("createOrUpdate ID : "+person.personID);
    	$scope.editperson = person;
    	if (typeof $scope.editperson.personID == 'undefined') {
    		console.log("creating !!!!");
    		create($scope.editperson);
    	}
    	else	{
    		console.log("Updating !!!!");
    		update($scope.editperson);
    	}	
    };
    
    
    $scope.delete = function(person) {
		console.log("delete");
		console.log("person :" + angular.toJson(person, false));
			
		PersonResource.delete({pid:person.personID}, person, function(response) {
      		console.log("Success", response);
      					
			PersonResource.list(function(response) {
      			console.log("Success", response);
				$scope.people = response;
    		}); 
    	});   	
	};
    
	$scope.reset = function() {
		console.log("reset");
		$scope.editperson = {};
		$scope.formTitle = "New Person";
		$scope.formSubmitButtonTitle = "Save";
	};
	
	$scope.reset();
    
    var create = function(person) {
		console.log("create");
		console.log("person :" + angular.toJson(person, false));
					
		PersonResource.create(person, function(response) {
      		console.log("Success", response);
			$scope.people.push(response);
			$scope.editperson = {};
    	});    	
	};
	
	var update = function(person) {
		console.log("update");
		console.log("person :" + angular.toJson(person, false));
			
		PersonResource.update({pid:person.personID}, person, function(response) {
      		console.log("Success", response);
			$scope.editperson = {};
			
			PersonResource.list(function(response) {
      			console.log("Success", response);
				$scope.people = response;
    		}); 
    	});   	
	};
    

}]);

