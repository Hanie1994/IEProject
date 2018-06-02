$(document).ready(function() {
    // hide the forms when page is ready
    $('#regForm').hide();

    $("#login-button").click(function(event){
		event.preventDefault();
		$('form').fadeOut(500);
		$('.wrapper').addClass('form-success');
	});

    $('#goReg').click(function(event){
		event.preventDefault();
        $('#logForm').hide();
		$('#regForm').show();
    });

	$('#goLog').click(function(event){
		event.preventDefault();
        $('#logForm').show();
		$('#regForm').hide();
    });

	$("#reg-button").click(function(event){
		event.preventDefault();
		$('form').fadeOut(500);
		$('.wrapper').addClass('form-success');
	});

});

