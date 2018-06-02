'use strict';
angular.module('stockApp').controller('AdminCtrl', ['$scope', '$http', function($scope, $http){

    $scope.activeIndex = 'PersonalInfo' ;
    var imageDir = "asset/images/" ;
    $scope.menu = [
        // customer permissions to admin
        {id:"PersonalInfo",      title:"", image:imageDir+"cash.png",   name:"اطلاعات شخصی"},
        {id:"Orders",        title:"",   image:imageDir+"order.png",  name:"سفارش ها"},
        {id:"SymbolList",        title:"",   image:imageDir+"order.png",  name:"لیست نمادها"},
        {id:"MarketInfo",       title:"",         image:imageDir+"status.png", name:"نبض بازار"},
        {id:"ShareBasket",       title:"",       image:imageDir+"config.png", name:"سبد سهام"},
        {id:"Credits",       title:"",       image:imageDir+"config.png", name:"امور مالی"},

        // admin specific permissions
        {id:"Reports",       title:"",       image:imageDir+"config.png", name:"گزارش ها"},
        {id:"ChangeLimit",       title:"",       image:imageDir+"config.png", name:"اعمال محدودیت"},
        {id:"CustomersList",       title:"",       image:imageDir+"config.png", name:"کاربران"},
        {id:"AddRole",       title:"",       image:imageDir+"config.png", name:"تعیین سطح دسترسی"},
        {id:"PendingSymbols",       title:"",       image:imageDir+"config.png", name:"نمادهای منتظر"},

        // financial permissions to admin
        {id:"ManageCreditRequests",       title:"",    image:imageDir+"report.png", name:"مدیریت درخواست"},
        {id:"ManageLimitedOrders",       title:"",       image:imageDir+"backup.png", name:"مدیریت سفارش ها"},

        // company permissions to admin
        {id:"CompanySymbolList",       title:"",              image:imageDir+"config.png", name:"نمادهای شرکت"},
        {id:"AddSymbol",        title:"",   image:imageDir+"order.png",  name:"نماد جدید"},
        {id:"ChargeSymbol",       title:"",              image:imageDir+"config.png", name:"شارژ نماد"}
    ];

    $scope.isActive = function(i){
        return (i === $scope.activeIndex) ;
    };

    $scope.goToPage = function(i){
        //alert($scope.activeIndex + '\t' + i);
        $scope.activeIndex = i ;
    };

    $scope.userInfo = {id:51, name:'آقامدیر'};

    $scope.welcome = $scope.userInfo.name + " عزیز به بخش میریت برنامه بورس خوش آمدید " ;

    $scope.symbolList = ['Rena', 'Saipa'];



    /// Reports Part :: Filter.html
    $scope.dater = function(x){
        return (new Date(x)).toDateString();
    };

    $scope.filteredExchanges = [] ;

    $scope.checkedPR = false;
    $scope.checkedID = false;
    $scope.checkedUN = false;
    $scope.checkedSB = false;
    $scope.checkedDate = false;
    $scope.checkedDate2 = false;

    $scope.search = function(){

        var parameters = {
            id : $scope.IdNum,
            lowerPrice : $scope.minPrice,
            upperPrice : $scope.maxPrice,
            name : $scope.UnameSerach,
            symbol: $scope.symbolSearch,
            fromDate : '%',
            toDate : '%'
        };

        if(!$scope.checkedPR){
            parameters.lowerPrice  = '%' ;
            parameters.upperPrice = '%' ;
        }
        if(!$scope.checkedID){
            parameters.id = '%' ;
        }
        if(!$scope.checkedUN){
            parameters.name = '%' ;
        }
        if(!$scope.checkedSB){
            parameters.symbol = '%' ;
        }
        if(!$scope.checkedDate){
            parameters.fromDate = '%' ;
        }
        else{
            parameters.fromDate = (new Date($scope.YY1, $scope.MM1-1, $scope.DD1, 0, 0, 0, 0)).getTime();
        }
        if(!$scope.checkedDate2){
            parameters.toDate = '%' ;
        }
        else{
            parameters.toDate = (new Date($scope.YY2, $scope.MM2-1, $scope.DD2, 0, 0, 0, 0)).getTime();
        }

        $http({
            method : "POST",
            url : "Filter.action",
            params : parameters
        }).then(function(response) {
            $scope.filteredExchanges = response.data;
            //alert(response.data);
        });
    };
    
    
	$http({
        method : "POST",
        url : "PendingSymbols.action"

    }).then(function(response) {
    	$scope.pendingSymbols = response.data;
    	//alert(response.data);
    });

	$scope.verifyPendingSymbol = function(name){
		$http({
            method : "POST",
            url : "VerifySymbol.action",
            params : {symbol: name}
        }).then(function(response) {
            $scope.message = response.data;
            //alert(response.data);
        });
	};
	
}]);