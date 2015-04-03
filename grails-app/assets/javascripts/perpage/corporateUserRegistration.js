var tempUser = "";
$(document).ready(function(){	
	if(username != ""){
		tempUser = username;
		var usernamePattern = /^[a-zA-Z0-9]*$/;
    	if (usernamePattern.test(username)){
			$('#idAvailable').html('<img src='+urlImgSpinner+' alt="loading" width="12px" />');
			checkIDAvailable();
    	}
    	else{
    		$('#idAvailable').html('<img src='+urlImgFalse+' alt="not available" width="12px" />');
    	}
	}
	
	$('#firstNameBlank, #lastNameBlank, #usernameBlank, #userAliasBlank, #emailBlank, #phoneBlank, #phoneInvalid').dialog({
		autoOpen: false,
		resizable: false,
		modal: true,
		buttons: {
			'CLOSE': function() {
				$(this).dialog('close');
			}
		}
	});

	$('#username').blur(function() {
		if($('#username').val() != ""){
			if(tempUser != $('#username').val()){
				tempUser = $('#username').val();
				var usernamePattern = /^[a-zA-Z0-9]*$/;
	        	if (usernamePattern.test($('#username').val())){
					$('#idAvailable').html('<img src='+urlImgSpinner+' alt="loading" width="12px" />');
					checkIDAvailable();
	        	}
	        	else{
	        		$('#idAvailable').html('<img src='+urlImgFalse+' alt="not available" width="12px" />');
	        	}
			}
		}
		else{
			tempUser = "";
			$('#idAvailable').html("");
		}
	});
		
	$('#email').blur(function() {
		if($('#email').val() != ""){
			var email=$("#email").val();
			$('#emailAvailable').html('<img src='+urlImgSpinner+' alt="loading" width="12px" />');
			validateEmail(email);
		}
		else{
			$('#emailAvailable').html("");
		}
 	});
});	

function checkIDAvailable(){
	var id = $('#username').val();
	
	$.ajax(
    {
    	url: url,
    	contentType:"text/json",
        type: "get",
        data: ({ id: id}),
        dataType: "json",
        cache: false,
        async: false,
        success: function(data) {
        	$('#idAvailable').html('<img src='+urlImgOk+' alt="available" width="12px" />');
        },
        error: function(xhr) {
        	if (xhr.status == 601){
        		window.location.reload();
           	}
        	else{
        		$('#idAvailable').html('<img src='+urlImgFalse+' alt="not available" width="12px" />');
        	}
        }
    });
}

function validateEmail(elementValue){  
	 var emailPattern = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;  
	 var statusEmailVal= emailPattern.test(elementValue);  
	 if (statusEmailVal == true){
		 $('#emailAvailable').html('<img src='+urlImgOk+' alt="available" width="12px" />');
     }
	 else {
    	 $('#emailAvailable').html('<img src='+urlImgFalse+' alt="not available" width="12px" />');
    }
}  
function checkValidation(){
	if($("#firstName").val() == ""){
		$("#firstName").focus();
		$('#firstNameBlank').dialog('open');
		return;
	}
	else if($("#lastName").val() == ""){
		$("#lastName").focus();
		$('#lastNameBlank').dialog('open');
		return;
	}
	else if($("#username").val() == ""){
		$("#username").focus();
		$('#usernameBlank').dialog('open');
		return;
	}
	else if($("#useralias").val() == ""){
		$("#useralias").focus();
		$('#userAliasBlank').dialog('open');
		return;
	}
	else if($("#email").val() == ""){
		$("#email").focus();
		$('#emailBlank').dialog('open');
		return;
	}
	else if($("#mobile").val() == ""){
		$("#mobile").focus();
		$('#phoneBlank').dialog('open');
		return;
	}
	$('form').submit();	
	$('form').attr("disabled","true");
}