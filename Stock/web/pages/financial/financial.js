angular.module('stockApp').controller('FinancialCtrl', ['$scope', '$http', function($scope, $http){

    $scope.activeIndex = 'PersonalInfo' ;
    var imageDir = "asset/images/" ;
    $scope.menu = [
        {id:"PersonalInfo",      title:"", image:imageDir+"cash.png",   name:"اطلاعات شخصی"},
        {id:"MarketInfo",       title:"",         image:imageDir+"status.png", name:"نبض بازار"},
        {id:"ManageCreditRequests",       title:"",    image:imageDir+"report.png", name:"مدیریت درخواست"},
        {id:"ManageLimitedOrders",       title:"",       image:imageDir+"backup.png", name:"مدیریت سفارش ها"}
    ];

    $scope.isActive = function(i){
        return (i === $scope.activeIndex) ;
    };

    $scope.goToPage = function(i){
        //alert($scope.activeIndex + '\t' + i);
        $scope.activeIndex = i ;
    };

    $scope.allPendingCreditRequests = [
        {userId:50}
    ];

    $scope.allLimitedOrders = [
        {symbol:'jo'}
    ];

}]);