
var app = angular.module('mainApp',['ngRoute','ngSanitize']);

app.config(function($routeProvider,$httpProvider) {




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
    $scope.roles=[];

    $scope.getRoles = function () {
        $http.get('http://cewebserver.azurewebsites.net/Service1.svc/getboffice').success(function (data, status, headers, config) {
            $scope.infosurcursal = (JSON).parse(data.toString());
            angular.forEach($scope.infosurcursal, function (item) {
                $scope.roles.push(item.Name)
            });
            $scope.selectedSucursal = $scope.roles[0];

        }).error(function (data, status, headers, config) {
            console.log(data);
        });
    }
    $scope.getRoles();
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
            ID_Customer:40089,
            Name: 'Carlos',
            Lastname_1:'perez',
            Lastname_2:'gonzalez',
            Phone: '898989',
            Email: 'Carlos@gmail.com',
            Username: 'cars23',
            Password: '123456'
        });

        $http.post('http://192.168.0.15:17476/ProductRESTService.svc/PostCustomer',parameter).success(function (data, status, headers, config) {
            // this callback will be called asynchronously
            // when the response is available
            console.log("status " + status);
            console.log("config " + data);
        }).error(function (data, status, headers, config) {
            // called asynchronously if an error occurs
            // or server returns response with an error status.
            console.log(data);
            console.log(status);
        });

    }});