
var app = angular.module('mainApp',['ngRoute','ngSanitize']);

app.config(function($routeProvider) {
    $routeProvider

        .when('/cliente', {
            templateUrl : 'pages/clienteReg.html',
            controller  : 'clienteController'
        })

        .when('/empleado', {
            templateUrl : 'pages/empleadoReg.html',
            controller  : 'empleadoController'
        })

        .when('/about', {
            templateUrl : 'pages/about.html',
            controller  : 'AboutController'
        })

        .otherwise({redirectTo: '/'});
});

var m = {
    "India": "2",
    "England": "2",
    "Brazil": "3",
    "UK": "1",
    "USA": "3",
    "Syria": "2"
};
app.controller('ex', function($scope) {
    $scope.items = m;
});
app.controller('clienteController',function ($scope) {
    $scope.message = "";

});
app.controller('empleadoController',function ($scope) {
    $scope.message = "";

});
app.controller('lobbyButton',function ($scope) {
    $scope.newDoc = function () {
        window.location.assign("pages/lobby.html")
    };

});

var centralApp = angular.module('viewsApp',['ngRoute','ngSanitize']);
centralApp.config(function($routeProvider) {
    $routeProvider

        .when('/lobby', {
            templateUrl : 'pages/lobby.html',
            controller  : 'lobbyController'
        })
        .when("/banana", {
        template : "<h1>Banana</h1><p>Bananas contain around 75% water.</p>"
        })

        .when('/empleado', {
            templateUrl : 'pages/empleadoReg.html',
            controller  : 'empleadoController'
        })

        .when('/about', {
            templateUrl : 'pages/about.html',
            controller  : 'AboutController'
        })

        .otherwise({redirectTo: '/'});
});
centralApp.controller('lobbyController', function($scope) {
    $scope.message = 'Hello from HomeController';
});
centralApp.controller('empleadoController', function($scope) {
    $scope.message = 'Hello from HomeController';
});
