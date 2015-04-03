$(document).ready(function(){
	
	$('#firstNameBlank, #lastNameBlank, #userIdBlank, #userAliasBlank, #emailBlank, #phoneNoBlank, #emailInvalid').dialog({
		autoOpen: false,
		resizable: false,
		modal: true,
		buttons: {
			'OK': function() {
				$(this).dialog('close');
			}
		}
	});
	
	$('#username').blur(function() {
		var usernamenya = $('#username').val();
		if(usernamenya != ""){
			var usernamePattern = /^[a-zA-Z0-9]*$/;
	    	if (usernamePattern.test(usernamenya)){
				$('#usernameAvailable').html('<img src='+imgSpinner+' alt="loading" width="12px" />');
				checkUsernameAvailable(usernamenya, "usernameAvailable");
	    	}
	    	else{
	    		$('#usernameAvailable').html('<img src='+imgFalse+' alt="not available" width="12px" />');
	    	}
		}
		else{
			$('#usernameAvailable').html("");
		}
	});	
});

function checkData(){
	if($("#firstName").val() == ""){
		$("#firstName").focus();
		$("#firstNameBlank").dialog('open');
		return;
	}
	else if($("#lastName").val() == ""){
		$("#lastName").focus();
		$("#lastNameBlank").dialog('open');
		return;
	}
	else if($("#username").val() == ""){
		$("#username").focus();
		$("#userIdBlank").dialog('open');
		return;
	}
	else if($("#userAlias").val() == ""){
		$("#userAlias").focus();
		$("#userAliasBlank").dialog('open');
		return;
	}
	else if($("#email").val() == ""){
		$("#email").focus();
		$("#emailBlank").dialog('open');
		return;
	}
	else if(validateEmail($("#email").val()) == false){
		$("#email").focus();
		$("#emailInvalid").dialog('open');
		return;
	}
	else if($("#mobilePhoneNo").val() == ""){
		$("#mobilePhoneNo").focus();
		$("#phoneNoBlank").dialog('open')
		return;
	}
	
	$('.price').each(function(){
		$(this).val(removeFormat($(this).val()));
	});

	$('form').submit();
	$('form').attr("disabled","true");
}

function validateEmail(elementValue){  
	 var emailPattern = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;  
	 var statusEmailVal= emailPattern.test(elementValue);  
	 if (statusEmailVal == true){
		 return true;
    }
	return false;
}  
function checkUsernameAvailable(usernamenya, idspan){
	$.ajax(
	{
		url: urlIsUsernameAvailable,
		contentType:"text/json",
		type: "get",
		data: ({ id: usernamenya}),
		dataType: "json",
		cache: false,
		async: false,
		success: function(data) {
			$('#'+idspan).html('<img src='+imgOk+' alt="available" width="12px" />');
		},
		error: function(xhr) {
			if (xhr.status == 601){
	    		window.location.reload();
	       	}
			else{
				$('#'+idspan).html('<img src='+imgFalse+' alt="not available" width="12px" />');
			}
		}
	});         		
}