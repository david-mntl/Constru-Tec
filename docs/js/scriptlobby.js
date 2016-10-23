
var app = angular.module('App',['ngRoute','ngSanitize']);
//const URL = 'http://192.168.0.15:17476/ProductRESTService.svc/';
const URL = 'http://cewebserver.tyhmn8q9pa.us-west-2.elasticbeanstalk.com/ProductRESTService.svc/';
app.config(function($routeProvider,$httpProvider) {
    $routeProvider

        .when('/editar', {
            templateUrl : 'editarUsuario.html',
            controller  : 'proyectoController'
        })

        .when('/proyectos', {
            templateUrl : 'proyectosEmpleado.html',//aqui va el link del que quiero ver OJOJOJOJOJOJO
            controller  : 'proyectoController'
        })

        .when('/about', {
            templateUrl : 'pages/about.html',
            controller  : 'AboutController'
        })

        .otherwise({redirectTo: '/'});
});

app.controller('proyectoController',function ($scope,$http) {
    $scope.message = "";

});
app.controller('Main',function($scope,$http) {

    $scope.idproject=1;
    $scope.rootFolders = 'bob@go.com';
    //$scope.projects = [ ]
    //$scope.stages = []

    $scope.loadEmail = function (user) {
        $scope.idproject = user.id;
    }


    $scope.nprojects = [];
    $scope.infoProject = [];
    $scope.selectedProject = $scope.nprojects[0];

    $scope.getProjects = function () {
        $scope.nprojects = [];
        $http.get(URL+'GetProjectsFrom?status=0&id=2014068784').success(function (data, status, headers, config) {
            $scope.infoProject = (JSON).parse(data.toString());
            console.log($scope.infoProject);
            angular.forEach($scope.infoProject, function (item) {
                $scope.nprojects.push(item)
            });

            $scope.selectedProject = $scope.nprojects[0];
        }).error(function (data, status, headers, config) {
            console.log(data);
        });

    }

    $scope.getProjects();

        $scope.nstages = [];
        $scope.info = [];
        $scope.selectedStage = $scope.nstages[0];

    $scope.getStages = function (project) {
        $scope.nstages = [];
        $http.get(URL+'GetStagesFromProject?params='+project).success(function (data, status, headers, config) {
            $scope.info = (JSON).parse(data.toString());
            console.log($scope.info);
            angular.forEach($scope.info, function (item) {
                $scope.nstages.push(item)
            });
            console.log($scope.nstages.toString());

            $scope.selectedStage = $scope.nstages[0];
        }).error(function (data, status, headers, config) {
            console.log(data);
        });
        $scope.stages = $scope.nstages;
    }



    $scope.ninfostages = [];
    $scope.infoStages = [];
    $scope.selectedinfoStage = $scope.ninfostages[0];

    $scope.getInfoStages = function (project) {
        $scope.infoStages = [];
        $http.get(URL+'GetInfoFromStage?id='+project).success(function (data, status, headers, config) {
            $scope.infoStages = (JSON).parse(data.toString());
            console.log($scope.infoStages);
            angular.forEach($scope.info, function (item) {
                $scope.ninfostages.push(item)
            });
            //console.log($scope.ninfostages.toString());

            $scope.selectedStage = $scope.nstages[0];
        }).error(function (data, status, headers, config) {
            console.log(data);
        });


    }

});





