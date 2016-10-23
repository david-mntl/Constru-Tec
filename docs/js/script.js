
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

app.controller('getRoles',function ($scope,$http) {

    $scope.sucursales = [];
    $scope.infosurcursal = [];
    $scope.selectedSucursal = $scope.sucursales[0];



    $scope.getSucursales = function () {
        $http.get('http://192.168.0.15:17476/ProductRESTService.svc/GetRoles').success(function (data, status, headers, config) {
            $scope.infosurcursal = (JSON).parse(data.toString());
            console.log($scope.infosurcursal);
            angular.forEach($scope.infosurcursal, function (item) {
                $scope.sucursales.push(item.get_roles)
            });
            $scope.selectedSucursal = $scope.sucursales[0];

        }).error(function (data, status, headers, config) {
            console.log(data);
        });
    }

    $scope.getSucursales();


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
    $scope.roles = {};
    $scope.roles.cliente = "cliente";
    $scope.roles.empleado = "empleado";
    $scope.rol = "cliente";

    $scope.changeRol = function (rol) {
        $scope.rol = rol;

    }


    $scope.myForm={};
    $scope.myForm.iDcard="";
    $scope.myForm.fname="";
    $scope.myForm.lname1="";
    $scope.myForm.lname2="";
    $scope.myForm.nickname="";
    $scope.myForm.password="";
    $scope.myForm.phone="";
    $scope.myForm.email="";
    $scope.myForm.carne = "";

    $scope.myForm.selectRegister=function (item,event) {
        if($scope.rol == "cliente"){
            $scope.myForm.registerClient();
        }else{
            $scope.myForm.registerEmployee();
        }

    }
    $scope.myForm.registerClient=function(item,event) {
        var parameter = JSON.stringify({
            ID_Customer:$scope.myForm.iDcard,
            Name: $scope.myForm.fname,
            Lastname_1:$scope.myForm.lname1,
            Lastname_2:$scope.myForm.lname2,
            Phone: $scope.myForm.phone,
            Email: $scope.myForm.email,
            Username: $scope.myForm.nickname,
            Password: $scope.myForm.password
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
        window.location.assign("pages/lobby.html")

    }
    $scope.myForm.registerEmployee=function(item,event) {
        var parameter = JSON.stringify({
            ID_Engineer:$scope.myForm.iDcard,
            Name: $scope.myForm.fname,
            Lastname_1:$scope.myForm.lname1,
            Lastname_2:$scope.myForm.lname2,
            Phone: $scope.myForm.phone,
            Email: $scope.myForm.email,
            Eng_Code : $scope.myForm.carne,
            Username: $scope.myForm.nickname,
            Password: $scope.myForm.password,
            Role : $scope.selectedSucursal

        });

        $http.post('http://192.168.0.15:17476/ProductRESTService.svc/PostEngineer',parameter).success(function (data, status, headers, config) {
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
        window.location.assign("pages/lobby.html")

    }


});