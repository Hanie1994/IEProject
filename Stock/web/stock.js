// create the module and name it stockApp
var stockApp = angular.module('stockApp', ['ngRoute']);

// configure our routes
stockApp.config(function($routeProvider) {
    $routeProvider

    // route for the home page
        .when('/login', {
            templateUrl : 'pages/login/login.html',
            controller  : 'LoginCtrl'
        })

        // route for the admin page
        .when('/admin', {
            templateUrl : 'pages/admin/admin.html',
            controller  : 'AdminCtrl'
        })

        // route for the company page
        .when('/company', {
            templateUrl : 'pages/company/company.html',
            controller  : 'CompanyCtrl'
        })

        // route for the customer page
        .when('/customer', {
            templateUrl : 'pages/customer/customer.html',
            controller  : 'CustomerCtrl'
        })

        // route for the financial page
        .when('/financial', {
            templateUrl : 'pages/financial/financial.html',
            controller  : 'FinancialCtrl'
        })

        .otherwise({
            redirectTo: '/login'
        });

});