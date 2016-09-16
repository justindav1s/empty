'use strict';

/* Services */

var sellingAppServices = angular.module('sellingApp.services', ['ngResource']);
          
sellingAppServices.factory('PersonResource', ['$resource', function($resource) {
   return $resource('http://localhost:8080/selling/s/person/:verb;pid=:pid', 
   null,
{
		'create': {method:'POST', params:{verb:'create'}, url:'http://localhost:8080/selling/s/person/:verb'},
		'read': {method:'GET', params:{verb:'read', pid:'@person.personID'}},
		'update': {method:'PUT', params:{verb:'update', pid:'@person.personID'}},
		'delete': {method:'DELETE', params:{verb:'delete', pid:'@person.personID'}},
		'list': {method:'GET', params:{verb:'list'}, isArray:true, url:'http://localhost:8080/selling/s/person/:verb'}
       });
}]);

  
  

