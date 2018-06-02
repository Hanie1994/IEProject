angular.module('stockApp').controller('CustomerCtrl', ['$scope', '$http', function($scope, $http){


    $scope.activeIndex = 'PersonalInfo' ;
    var imageDir = "asset/images/" ;
    $scope.menu = [
        {id:"PersonalInfo",      title:"اطلاعات شخصی", image:imageDir+"cash.png",   name:"پروفایل"},
        {id:"Orders",        title:"",   image:imageDir+"order.png",  name:"سفارش ها"},
        {id:"SymbolList",        title:"",   image:imageDir+"order.png",  name:"لیست نمادها"},
        {id:"MarketInfo",       title:"بازار",         image:imageDir+"status.png", name:"نبض بازار"},
        {id:"ShareBasket",       title:"",       image:imageDir+"config.png", name:"سبد سهام"},
        {id:"Credits",       title:"",       image:imageDir+"config.png", name:"امور مالی"}
    ];

    $scope.isActive = function(i){
        return (i === $scope.activeIndex) ;
    };

    $scope.goToPage = function(i){
        $scope.activeIndex = i ;
    };

    $scope.symbolList = ['Rena', 'Saipa'];

    $scope.setSymbol = function(s){
        $scope.selectedSymbol = s;
        //reloadSymbolInfo(s);
    };
   
    var ajax = function(url, params, resp) {
    	$http({
            method : "POST",
            url : url,
            params: params

        }).then(function(response) {
        	resp = response.data;
        	//alert(response.data);
        });
	};
	
	$http({
        method : "POST",
        url : "MyProfile.action"

    }).then(function(response) {
    	
    	$scope.userInfo = response.data.user ;
    	$scope.userInfo.blockedCredit = response.data.blockedCredit ;
    	$scope.userInfo.roles = response.data.roles ;
    	
    	$scope.activeOrders = response.data.activeOrders ;
    	$scope.approvedOrders = response.data.approvedOrders ;
    	$scope.declinedOrders = response.data.declinedOrders ;
    	$scope.limitedOrders = response.data.limitedOrders ;
    	
    	$scope.freeShares = response.data.freeShares ;
    	$scope.blockedShares = response.data.blockedShares;
    	
    	$scope.pendingRequests = response.data.pendingRequests;
    	
    	 $scope.welcome = $scope.userInfo.name + " عزیز خوش آمدید " ;
    });
	
	
	var loadMarketInfo = function() {
		ajax("MarketInfo.action", {}, $scope.marketActiveOrders);
	}
	
	$scope.doDepositRequest = function(amount){
		ajax("DepositRequest.action", {amount: amount}, $scope.message);
	};
	
	$scope.doWithdrawRequest = function(amount){
		ajax("WithdrawRequest.action", {amount: amount}, $scope.message);
	};
	

	//$scope.userInfo = myProfile.user;
	loadMarketInfo();
	
	
	$scope.selectedSymbol = null;
    $scope.symbolInfo = null;
    
    $scope.setSymbol = function(s){
        $scope.selectedSymbol = s;
        //reloadSymbolInfo(s);
    };
}]);