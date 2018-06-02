angular.module('stockApp').controller('CompanyCtrl', ['$scope', '$http', function($scope, $http){

    $scope.activeIndex = 'PersonalInfo' ;
    var imageDir = "asset/images/" ;
    $scope.menu = [
        {id:"PersonalInfo",      title:"", image:imageDir+"cash.png",   name:"اطلاعات شخصی"},
        {id:"CompanySymbolList",       title:"",              image:imageDir+"config.png", name:"نمادهای شرکت"},
        {id:"AddSymbol",        title:"",   image:imageDir+"order.png",  name:"نماد جدید"},
        {id:"ChargeSymbol",       title:"",              image:imageDir+"config.png", name:"شارژ نماد"},
        {id:"MarketInfo",       title:"",         image:imageDir+"status.png", name:"نبض بازار"}
    ];

    $scope.isActive = function(i){
        return (i === $scope.activeIndex) ;
    };

    $scope.goToPage = function(i){
        //alert($scope.activeIndex + '\t' + i);
        $scope.activeIndex = i ;
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
    	
    });
    
    $scope.doAddSymbol = function(symbol){
    	ajax("AddSymbol.action", {symbol:symbol}, $scope.message);
    };
    
}]);