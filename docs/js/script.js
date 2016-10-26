
var app = angular.module('mainApp',['ngCookies','ngRoute','ngSanitize','angular-loading-bar']);
//const URL = 'http://cewebserver.tyhmn8q9pa.us-west-2.elasticbeanstalk.com/ProductRESTService.svc/';
const URL  ='http://192.168.43.115:7676/ProductRESTService.svc/';
var roles = "";
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
    roles = $scope.selectedSucursal;


    $scope.getSucursales = function () {
        $http.get(URL + 'GetRoles').success(function (data, status, headers, config) {
            $scope.infosurcursal = (JSON).parse(data.toString());
            console.log($scope.infosurcursal);
            angular.forEach($scope.infosurcursal, function (item) {
                $scope.sucursales.push(item.get_roles)
            });
            $scope.selectedSucursal = $scope.sucursales[0];
            roles = $scope.selectedSucursal;

        }).error(function (data, status, headers, config) {
            console.log(data);
        });
    }

    $scope.getSucursales();

    $scope.printRoles = function () {
        roles = $scope.selectedSucursal;
        console.log(roles);

    }

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


app.controller('loginController',function ($scope,$cookies,$http,$window,$timeout) {

    //$cookies.put('username', "hola");

    $scope.Login= function () {

        console.log($cookies.username);
        var parameter = JSON.stringify({
            Username: $scope.UserFE,
            Password: $scope.PasswordFE
        });
        console.log(parameter);
        $scope.loginRol=[];
        $http.post(URL+'VerifyLogin',parameter).success(function (data, status, headers, config) {


            $scope.ROL = (JSON).parse(data.toString());
            console.log($scope.login);
            //$cookies.rol = $scope.ROL[0].login[9];
            console.log("ROL" + $scope.ROL[0].login[9]);
            if ($scope.ROL[0].login[1] == "groot"){
                $cookies.eng_code = 999;
            }
            else if ($scope.ROL[0].login[9] == "0"){
                $cookies.eng_code = 0;
            }else{
                $cookies.eng_code = 1;
            }

            $cookies.userid = $scope.ROL[0].login[0];
            $cookies.name = $scope.ROL[0].login[1];
            $cookies.lastname1 = $scope.ROL[0].login[2];
            $cookies.lastname2 = $scope.ROL[0].login[3];
            $cookies.phone = $scope.ROL[0].login[4];
            $cookies.email = $scope.ROL[0].login[5];
            $cookies.username = $scope.ROL[0].login[6];
            $cookies.password = $scope.ROL[0].login[7];

            console.log(data);
            console.log(status);
            $timeout($scope.toLobby(),2000);

        }).error(function (data, status, headers, config) {
            console.log("Error");
            console.log(data);
            console.log(status);
        });


    }

    
    $scope.toLobby = function () {
        $window.location.href = 'pages/lobby.html';
    }
});



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

        $http.post(URL+'PostCustomer',parameter).success(function (data, status, headers, config) {
            // this callback will be called asynchronously
            // when the response is available

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





            console.log("status " + status);
            console.log("config " + data);
        }).error(function (data, status, headers, config) {
            // called asynchronously if an error occurs
            // or server returns response with an error status.
            console.log(data);
            console.log(status);
        });


    }
    $scope.myForm.registerEmployee=function(item,event) {
            console.log(roles);
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
            Role : roles

        });
        console.log(parameter);
        $http.post(URL+'PostEngineer',parameter).success(function (data, status, headers, config) {
            // this callback will be called asynchronously
            // when the response is available
            console.log("status " + status);
            console.log("config " + data);

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






        }).error(function (data, status, headers, config) {
            // called asynchronously if an error occurs
            // or server returns response with an error status.
            console.log(data);
            console.log(status);
        });


    }


});