var canSearch = true;
$(document).ready(function(){
	$('#corpNameBlank, #corpIdBlank, #address1Blank, #firstNameBlank, #lastNameBlank, #userIdBlank, #userIdSame, #userAliasBlank, #emailBlank, #phoneNoBlank, #emailInvalid').dialog({
		autoOpen: false,
		resizable: false,
		modal: true,
		buttons: {
			'OK': function() {
				$(this).dialog('close');
			}
		}
	});
	
	$('#username1').blur(function() {
		var usernamenya = $('#username1').val();
		if(usernamenya != ""){
			var usernamePattern = /^[a-zA-Z0-9]*$/;
	    	if (usernamePattern.test(usernamenya)){
				$('#usernameAvailable1').html('<img src='+imgSpinner+' alt="loading" width="12px" />');
				checkUsernameAvailable(usernamenya, "usernameAvailable1");
	    	}
	    	else{
	    		$('#usernameAvailable1').html('<img src='+imgFalse+' alt="not available" width="12px" />');
	    	}
		}
		else{
			$('#usernameAvailable1').html("");
		}
	});	

	$('#username2').blur(function() {
		var usernamenya = $('#username2').val();
		if(usernamenya != ""){
			var usernamePattern = /^[a-zA-Z0-9]*$/;
	    	if (usernamePattern.test(usernamenya)){
				$('#usernameAvailable2').html('<img src='+imgSpinner+' alt="loading" width="12px" />');
				checkUsernameAvailable(usernamenya, "usernameAvailable2");
	    	}
	    	else{
	    		$('#usernameAvailable2').html('<img src='+imgFalse+' alt="not available" width="12px" />');
	    	}
		}
		else{
			$('#usernameAvailable2').html("");
		}
	});	
	
	$('#cifId').blur(function() {
		var usernamenya = $('#cifId').val();
		if(usernamenya != ""){
			var usernamePattern = /^[a-zA-Z0-9]*$/;
	    	if (usernamePattern.test(usernamenya)){
				$('#corpNameAvailable').html('<img src='+imgSpinner+' alt="loading" width="12px" />');
				checkCorpnameAvailable(usernamenya);
	    	}
	    	else{
	    		$('#corpNameAvailable').html('<img src='+imgFalse+' alt="not available" width="12px" />');
	    	}
		}
		else{
			$('#corpNameAvailable').html("");
		}
	});	
	
	$(".kotak").click(function() {
		$(this).children('input').prop('checked',true);
	});
});

function checkData(){
	if($("#cifId").val() == ""){
		$("#cifId").focus();
		$("#corpIdBlank").dialog('open');
		return;
	}
	else if($("#corpName").val() == ""){
		$("#corpName").focus();
		$("#corpNameBlank").dialog('open');		
		return;
	}
	else if($("#address1").val() == ""){
		$("#address1").focus();
		$("#address1Blank").dialog('open');
		return;
	}
	else if($("#firstName1").val() == ""){
		$("#firstName1").focus();
		$("#firstNameBlank").dialog('open');
		return;
	}
	else if($("#lastName1").val() == ""){
		$("#lastName1").focus();
		$("#lastNameBlank").dialog('open');
		return;
	}
	else if($("#username1").val() == ""){
		$("#username1").focus();
		$("#userIdBlank").dialog('open');
		return;
	}
	else if($("#userAlias1").val() == ""){
		$("#userAlias1").focus();
		$("#userAliasBlank").dialog('open');
		return;
	}
	else if($("#email1").val() == ""){
		$("#email1").focus();
		$("#emailBlank").dialog('open');
		return;
	}
	else if(validateEmail($("#email1").val()) == false){
		$("#email1").focus();
		$("#emailInvalid").dialog('open');
		return;
	}
	else if($("#mobilePhoneNo1").val() == ""){
		$("#mobilePhoneNo1").focus();
		$("#phoneNoBlank").dialog('open')
		return;
	}
	else if($("#firstName2").val() == ""){
		$("#firstName2").focus();
		$("#firstNameBlank").dialog('open')
		return;
	}
	else if($("#lastName2").val() == ""){
		$("#lastName2").focus();
		$("#lastNameBlank").dialog('open')
		return;
	}
	else if($("#username2").val() == ""){
		$("#username2").focus();
		$("#userIdBlank").dialog('open')
		return;
	}
	else if($("#userAlias2").val() == ""){
		$("#userAlias2").focus();
		$("#userAliasBlank").dialog('open')
		return;
	}
	else if($("#email2").val() == ""){
		$("#email2").focus();
		$("#emailBlank").dialog('open')
		return;
	}
	else if(validateEmail($("#email2").val()) == false){
		$("#email2").focus();
		$("#emailInvalid").dialog('open')
		return;
	}
	else if($("#mobilePhoneNo2").val() == ""){
		$("#mobilePhoneNo2").focus();
		$("#phoneNoBlank").dialog('open')
		return;
	}
	else if($("#username1").val() == $("#username2").val()){
		$("#username2").focus();
		$("#userIdSame").dialog('open');
		return;
	}

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

function checkCorpnameAvailable(usernamenya){
	$.ajax(
	{
		url: urlIsCorpnameAvailable,
		contentType:"text/json",
		type: "get",
		data: ({ id: usernamenya}),
		dataType: "json",
		cache: false,
		async: false,
		success: function(data) {
			$('#corpNameAvailable').html('<img src='+imgOk+' alt="available" width="12px" />');
		},
		error: function(xhr) {
			if (xhr.status == 601){
	    		window.location.reload();
	       	}
			else{
				$('#corpNameAvailable').html('<img src='+imgFalse+' alt="not available" width="12px" />');
			}
		}
	});         		
}