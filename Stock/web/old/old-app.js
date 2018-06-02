var app = angular.module('myApp', []);
app.controller('mainCtrl', function($scope, $http, $interval) {

    var AUTO_UPDATE_DURATION = 15000; //milliseconds

    $scope.firstName= "John";
    $scope.lastName= "Doe";
    $scope.pageTitle = "B_00_rs" ;

    $scope.activeIndex = 0;

    $scope.getStatus = function(){
        $http.post("customer/status")
            .then(function(response) {
                alert(response.data);
            });
    };

    $scope.isOn = function(i){
        return (5 === i);
    };

    $scope.loadSampleData = function(){
        $http({
            method : "POST",
            url : "api/load"
        }).then(function(response) {
            alert(response.data);
        });
    };

    $scope.symbolList = null;
    var reloadSymbolList = function(){
        $http({
            method : "POST",
            url : "api/symbols"
        }).then(function(response) {
            //alert(response.data);
            $scope.symbolList = response.data;
        });
    }

    $scope.selectedSymbol = null;
    $scope.symbolInfo = null;

    var reloadSymbolInfo = function(s){
        $http({
            method : "POST",
            url : "api/symbols/info",
            params: {
                symbol : s
            }

        }).then(function(response) {
            //alert(response.data);
            $scope.symbolInfo = response.data;
        });
    };

    $scope.setSymbol = function(s){
        $scope.selectedSymbol = s;
        reloadSymbolInfo(s);
    };

    $scope.changeData = function(){
      $scope.symbolInfo.push({userId:"goolakh"});
    };


    $scope.userInfo = null;
    $scope.customerId = 2;
    var reloadUserInfo = function(){
        $http({
            method : "POST",
            url : "api/customer/credit",
            params: {
                id : $scope.customerId
            }

        }).then(function(response) {
            //alert(response.data);
            $scope.userInfo = response.data;
        });
    };


    $scope.activeOrders = null;
    $scope.approvedOrders = null;
    $scope.declinedOrders = null;
    var reloadActiveOrders = function(){
        $http({
            method : "POST",
            url : "api/customer/activeOrders",
            params: {
                id : $scope.customerId
            }

        }).then(function(response) {
            //alert(response.data);
            $scope.activeOrders = response.data;
        });
    };

    var reloadApprovedOrders = function(){
        $http({
            method : "POST",
            url : "api/customer/approvedOrders",
            params: {
                id : $scope.customerId
            }

        }).then(function(response) {
            //alert(response.data);
            $scope.approvedOrders = response.data;
        });
    };

    var reloadDeclinedOrders = function(){
        $http({
            method : "POST",
            url : "api/customer/declinedOrders",
            params: {
                id : $scope.customerId
            }

        }).then(function(response) {
            //alert(response.data);
            $scope.declinedOrders = response.data;
        });
    };

    $scope.shareBasket = null;
    var reloadShareBasket = function(){
        $http({
            method : "POST",
            url : "api/customer/shares",
            params: {
                id : $scope.customerId
            }

        }).then(function(response) {
            //alert(response.data);
            $scope.shareBasket = response.data;
        });
    };

    $scope.isRefreshingMarket = false;
    $scope.marketActiveOrders = null;
    var reloadMarketStatus = function(){
        $http({
            method : "POST",
            url : "api/market/info"
        }).then(function(response) {
            //alert(response.data);
            $scope.marketActiveOrders = response.data;
        });
    };
    $scope.updateMarketStatus = function(){
        $scope.isRefreshingMarket = true;
        reloadMarketStatus() ;
        $scope.isRefreshingMarket = false;
        //alert("manual");
    };


    $scope.init = function(){
        reloadUserInfo();
        reloadSymbolList();
        reloadActiveOrders();
        reloadApprovedOrders();
        reloadDeclinedOrders();
        reloadShareBasket();
        reloadMarketStatus();
        reloadSymbolInfo($scope.selectedSymbol);
        //alert("done!");
    };


    //system will be auto updated
    $interval($scope.init, AUTO_UPDATE_DURATION);



    $scope.sellEnable = true;
    $scope.doSell = function(){

        $scope.sellEnable = false;

        $http({
            method : "POST",
            url : "customer/sell",
            params: {
                instrument : $scope.selectedSymbol,
                id : $scope.customerId,
                price : $scope.sellPrc,
                quantity : $scope.sellQty,
                type : $scope.sellTp
            }

        }).then(function(response) {
            //alert(response.data);
            $scope.sellMessage = response.data;
        });
        reloadSymbolInfo($scope.selectedSymbol);
    }

    $scope.buyEnable = true;
    $scope.doBuy = function(){

        $scope.buyEnable = false;

        $http({
            method : "POST",
            url : "customer/buy",
            params: {
                instrument : $scope.selectedSymbol,
                id : $scope.customerId,
                price : $scope.buyPrc,
                quantity : $scope.buyQty,
                type : $scope.buyTp
            }

        }).then(function(response) {
            //alert(response.data);
            $scope.buyMessage = response.data;
        });
        reloadSymbolInfo($scope.selectedSymbol);
    };

    var reset = function(){
        $scope.buyEnable = true;
        $scope.sellEnable = true;
        $scope.buyMessage = '' ;
        $scope.sellMessage = '' ;
    };

    $scope.newOrder = reset ;

});

app.controller('menuCtrl', function($scope){


    $scope.menu = [
        {id:"DepositMenu",      title:"افزایش موجودی", image:"images/cash.png",   name:"افزایش موجودی"},
        {id:"OrderMenu",        title:"خرید و فروش",   image:"images/order.png",  name:"ثبت سفارش"},
        {id:"AddCustomerMenu",  title:"ثبت نام",       image:"images/user.png",   name:"کاربر جدید"},
        {id:"ReportMenu",       title:"درخواست ها",    image:"images/report.png", name:"مدیریت درخواست ها"},
        {id:"BackupMenu",       title:"پشتیبان",       image:"images/backup.png", name:"نسخه پشتیبان"},
        {id:"StatusMenu",       title:"بازار",         image:"images/status.png", name:"نبض بازار"},
        {id:"ConfigMenu",       title:"تنظیمات",       image:"images/config.png", name:"تنظیمات"}
    ];

    $scope.isActive = function(i){
        return (i === $scope.activeIndex) ;
    };

    $scope.goToPage = function(i){
        //alert($scope.activeIndex + '\t' + i);
        $scope.activeIndex = i ;
    };

    //$scope.navigate = $scope.$parent.;
});

app.controller('addCtrl', function($scope, $http){
    $scope.addCustomer = function(_name, _family, _id){
        $http({
            method : "POST",
            url : "customer/add",
            params: {
                id : 1/*_id*/,
                name : "_name",
                family : "_family"
            }
        }).then(function(response) {
            alert(response.data);
        });
    }
});

app.controller('symbolCtrl', function($scope, $http){

});
