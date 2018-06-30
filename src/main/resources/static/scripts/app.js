(function(){
    'use strict';
    var app = angular.module('intelipost', ['ui.bootstrap','ui.router'])
        .config(function($stateProvider, $urlRouterProvider,$locationProvider) {
        $locationProvider.hashPrefix('');
        $urlRouterProvider.otherwise('/');   
        $stateProvider.state('site', {
            'abstract': true,
            views: {
            'navbar@': { 
                templateUrl: 'views/navbar.html'
                }  
            }          
        });
    });
    
    app.run(function($rootScope,$http,$window) {
        $rootScope.$on("$locationChangeStart", function(event, next, current) { 
        	$http.get("intelipost/auth/loggedAccount", { params: {}
            }).then(function (response) {
            	var test = response.status;
                $rootScope.username = response.data.username;
                $rootScope.isAuthenticated = true;
                $window.sessionStorage.setItem('authenticated', true);
            }, function (error) {
            	var test = error.status;
            	$rootScope.isAuthenticated = false;
            	$window.sessionStorage.setItem('authenticated', false);
	          });     
        });
    });
    
    
}());

(function() {
    'use strict';
    angular.module('intelipost')
    .config(function ($stateProvider) {
        $stateProvider
        .state('home', {
            parent: 'site',
            url: '/',
            views: {
                'content@': {
                    templateUrl: 'views/home.html',
                    controller: 'HomeController'
                }
            }
        });
    });
})();

(function(){
    'use strict';
    angular
        .module('intelipost')
        .controller('LoginController', LoginController);
    function LoginController($scope,$rootScope,$window,$uibModal,$uibModalInstance,$http){
    	$rootScope.isAuthenticated = false;
    	$window.sessionStorage.setItem('authenticated', false);
        var vm = this;        
        init();
        vm.message="In LoginController";
        vm.username=null;
        vm.password=null;
        vm.rememberMe=false;
        vm.login = login;
        vm.cancel = cancel;
        function login() {
            var data = "j_username=" + vm.username + "&j_password=" +  vm.password + "&_spring_security_remember_me=" + vm.rememberMe + "&submit=Login";
            $http.post('app/authentication', data, {
                headers: { "Content-Type": "application/x-www-form-urlencoded" }
            }).then(function (response) {
                var test = response.status;
                loggedInUsername();
                $uibModalInstance.close();
            }, function (error) {
                var data = error.data;
              });
        }
        function loggedInUsername() {
            $http.get("intelipost/auth/loggedAccount", { params: {}
            }).then(function (response) {
                $rootScope.username = response.data.username;
                $rootScope.isAuthenticated=true;
                $window.sessionStorage.setItem('authenticated', true);
            }, function (error) {
                var data = error.data;
              });
        }
        function cancel(){
              $uibModalInstance.dismiss('cancel');
        }
        function init(){
        	if($window.sessionStorage.getItem('authenticated')){
        		$rootScope.isAuthenticated = false;
        	}
        }
    };
}());

(function() {
    'use strict';
    angular.module('intelipost')
        .controller('LogoutController',LogoutController);
    function LogoutController($state,$scope,$rootScope,$window,$uibModal,$uibModalInstance,$http){
        var vm = this;
        vm.message="In LogoutController";
        vm.logout = logout;
        function logout(){
            $http.get("app/logout").then(function (response){
                var status = response.status;
                $state.go("home");
                $rootScope.username='';
                $rootScope.isAuthenticated=false;
                $uibModalInstance.close();
            })
        }
    }
}());

(function(){
    'use strict';
    angular
        .module('intelipost')
        .controller('HomeController', HomeController);
    function HomeController($scope,$window,$http,$uibModal,$log,$rootScope){
        var vm = this;
        init();
        vm.loginModal = loginModal;
        vm.logoutModal = logoutModal;
        vm.message = "HomeController";
        function init(){
        	$rootScope.isAuthenticated = false;
        	$window.sessionStorage.setItem('authenticated', false);
        };
        function loginModal(){
         var modalInstance =  $uibModal.open({
                templateUrl: 'views/login.html',
                controller: 'LoginController',
                controllerAs: 'login',
                backdrop: 'static',
                resolve: {
                    input: function () {
                        return "empt";
                    }
                }
		    });
            modalInstance.result.then(function (paramFromDialog) {
                vm.paramFromDialog = paramFromDialog;
            });
        };
        function logoutModal(){
            var modalInstance =  $uibModal.open({
                templateUrl: 'views/logout.html',
                controller: 'LogoutController',
                controllerAs: 'logout',
                backdrop: 'static',
                resolve: {
                    input: function () {
                        return "empt";
                    }
                }
            });
            modalInstance.result.then(function (paramFromDialog) {
                vm.paramFromDialog = paramFromDialog;
            });
        };
    };
}());