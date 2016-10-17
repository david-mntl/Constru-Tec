
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

/*app.controller("signin",function($scope,$http,SweetAlert,$timeout,$window) {
    $scope.myForm={};
    $scope.myForm.iDcard="";
    $scope.myForm.pass="";

    $scope.myForm.submitTheForm=function(item,event) {
        var parameter = JSON.stringify({
            Username:$scope.myForm.iDcard.toString(),
            Password: $scope.myForm.pass.toString()

        });
        $http.post('http://cewebserver.azurewebsites.net/Service1.svc/postcustomer', parameter).
        success(function(data, status, headers, config) {
            console.log("DATOS: " + data);
            if(data == "Error"){
                SweetAlert.error("Usuario o correo ya registrado, o datos incorrectos", {title: "Datos incorrectos"});
            }
            else{
                SweetAlert.success("Se ha registrado correctamente", {title: "Proceso Completado"});
                $timeout(function() {
                    $window.location.href = 'http://epatec.codigo22.com';
                }, 3000);
            }
        }).
        error(function(data, status, headers, config) {
            if(status != 200){
                SweetAlert.error("Por favor verifique los datos ingresados.", {title: "Datos incorrectos"});
            }
        });
    }});
*/

app.controller("MyController",function($scope,$http,$timeout,$window) {
    $scope.myForm={};
    $scope.myForm.iDcard="";
    $scope.myForm.fname="";
    $scope.myForm.lname1="";
    $scope.myForm.lname2="";
    $scope.myForm.nickname="";
    $scope.myForm.password="";
    $scope.myForm.phone="";
    $scope.myForm.email="";

    $scope.myForm.submitTheForm=function(item,event) {
        var parameter = JSON.stringify({
            ID_Customer:$scope.myForm.iDcard.toString(),
            Name: $scope.myForm.fname.toString(),
            Lastname_1:$scope.myForm.lname1.toString(),
            Lastname_2:$scope.myForm.lname2.toString(),
            Phone: $scope.myForm.phone.toString(),
            Email: $scope.myForm.email.toString(),
            Username: $scope.myForm.nickname.toString(),
            Password: $scope.myForm.password.toString()
        });

        $http.post('https://cewebserver.tyhmn8q9pa.us-west-2.elasticbeanstalk.com/ProductRESTService.svc/PostCustomer', parameter).
        success(function(data, status, headers, config) {
            console.log("DATOS: " + data);
            if(data == "Error"){
                //SweetAlert.error("Usuario o correo ya registrado, o datos incorrectos", {title: "Datos incorrectos"});
            }
            else{
                //SweetAlert.success("Se ha registrado correctamente", {title: "Proceso Completado"});
                $timeout(function() {
                    $window.location.href = 'pages/lobby.html';
                }, 3000);
            }
        }).
        error(function(data, status, headers, config) {
            if(status != 200){
                //SweetAlert.error("Por favor verifique los datos ingresados.", {title: "Datos incorrectos"});
            }
        });
    }});