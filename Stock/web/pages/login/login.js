angular.module('stockApp').controller('LoginCtrl', ['$scope', '$http', function($scope, $http){

    $('.form').find('input, textarea').on('keyup blur focus', function (e) {

        var $this = $(this),
            label = $this.prev('label');

        if (e.type === 'keyup') {
            if ($this.val() === '') {
                label.removeClass('active highlight');
            } else {
                label.addClass('active highlight');
            }
        } else if (e.type === 'blur') {
            if( $this.val() === '' ) {
                label.removeClass('active highlight');
            } else {
                label.removeClass('highlight');
            }
        } else if (e.type === 'focus') {

            if( $this.val() === '' ) {
                label.removeClass('highlight');
            }
            else if( $this.val() !== '' ) {
                label.addClass('highlight');
            }
        }

    });

    $('.tab a').on('click', function (e) {

        e.preventDefault();

        $(this).parent().addClass('active');
        $(this).parent().siblings().removeClass('active');

        target = $(this).attr('href');

        $('.tab-content > div').not(target).hide();

        $(target).fadeIn(600);

    });

    $scope.doLogin = function(username, password){
        //alert(username + password);

        $http({
            method : "POST",
            url : "j_security_check",
            params: {
                j_username: username,
                j_password: password
            }

        }).then(function(response) {
            //alert(response.data);
            //$scope.userInfo = response.data;
            alert(response);
        });

    };

    $scope.doSignup = function(user, name, family, email, pass, repeatPass){

        $http({
            method : "POST",
            url : "AddCustomer.action",
            params: {
                id: user,
                name: name,
                family: family,
                email: email,
                password: pass,
                repeat: repeatPass
            }

        }).then(function(response) {
            //alert(response.data);
            //$scope.userInfo = response.data;
            alert(response);
        });
    };

}]);