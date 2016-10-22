
var app = angular.module('App',['ngRoute','ngSanitize']);

app.config(function($routeProvider,$httpProvider) {
    $routeProvider

        .when('/editar', {
            templateUrl : 'editarUsuario.html',
            controller  : 'proyectoController'
        })

        .when('/proyectos', {
            templateUrl : 'proyectos.html',
            controller  : 'proyectoController'
        })

        .when('/about', {
            templateUrl : 'pages/about.html',
            controller  : 'AboutController'
        })

        .otherwise({redirectTo: '/'});
});

app.controller('proyectoController',function ($scope) {
    $scope.message = "";

});

app.controller('Main',function($scope) {
    $scope.idproject=1;
    $scope.rootFolders = 'bob@go.com';
    $scope.projects = [
        {
            id: 1,
            name: 'user1',
            login: 'user1@go.com',
            password: '123456'
        },{
            id: 2,
            name: 'user2',
            login: 'user1@go.com',
            password: '123456'
        },{
            id: 3,
            name: 'user3',
            login: 'user1@go.com',
            password: '123456'
        }, {
            id: 4,
            name: 'user4',
            login: 'user2@go.com',
            password: '123456'
        }, ]
    $scope.stages = [
        {   id: 1,
            project: 1,
            login: 'user1@go.com',
            password: '123456'
        },{
            id: 2,
            project: 1,
            login: 'user1@go.com',
            password: '123456'
        },{
            id: 3,
            project: 1,
            login: 'user1@go.com',
            password: '123456'
        },{
            id: 4,
            project: 2,
            login: 'user2@go.com',
            password: '123456'
        }, ]

    $scope.loadEmail = function (user) {
        $scope.idproject = user.id;
    }
});