var userStatus
$(document).ready(function(){
	$('#firstNameBlank, #lastNameBlank, #userAliasBlank, #emailBlank, #phoneNoBlank, #emailInvalid').dialog({
		autoOpen: false,
		resizable: false,
		modal: true,
		buttons: {
			'OK': function() {
				$(this).dialog('close');
			}
		}
	});
	
	$('#statusResetInvalid, #statusResetError, #statusBlockError').dialog({
		autoOpen: false,
		resizable: false,
		modal: true,
		buttons: {
			'CLOSE': function() {
				$(this).dialog('close');
			}
		}
	});
	
	$('#blockStatusBtn').click(function() {
		$(this).attr('disabled',true);
		userStatus = $('#userstattd').html();
		$('#userstattd').html('<img src='+urlImgSpinner+' alt="loading" width="12px" />');		
		$.ajax(
	     {
	     	url: urlBlockUser,
	     	type: "post",
	         data: ({ id: blockId}),
	         dataType: "json",
	         callback: "myCallback",
	         cache: false,
	         success: function(data) {
	        	 $('#userstattd').html(block);
	        	 userStatus = block;
	        	 $('#blockStatusBtn').removeAttr('disabled');
	         },
	         error: function(xhr) {
	        	 if (xhr.status == 601){
	            		window.location.reload();
	               	}
	        	 else{
	        		 $('#userstattd').html(userStatus);
	        		 $('#blockStatusBtn').removeAttr('disabled');
	                 $('#statusBlockError').dialog('open');
	        	 }
	         }
	     });  
	 });
	
	 $('#resetPassBtn').click(function() {
		$(this).attr('disabled',true);
		userStatus = $('#userstattd').html();
		$('#userstattd').html('<img src='+urlImgSpinner+' alt="loading" width="12px" />');
		$.ajax(
	     {
	     	url: urlResetPassword,
	     	type: "post",
	         data: ({ id: blockId}),
	         dataType: "json",
	         callback: "myCallback",
	         cache: false,
	         success: function(data) {
	        	 $('#userstattd').html(inactive);
	        	 userStatus = inactive;
	        	 $('#resetPassBtn').removeAttr('disabled');
	         },
	         error: function(xhr) {
	        	 if (xhr.status == 601){
	            		window.location.reload();
	               	}
	        	 else{
	        		 if (xhr.status == 404){
	        			$('#userstattd').html(userStatus);
	        			$('#resetPassBtn').removeAttr('disabled');
		             	$('#statusResetError').dialog('open');
	        		 }
	        		 else if (xhr.status == 405){	 	             	
	        			 $('#userstattd').html(userStatus);
	        			 $('#resetPassBtn').removeAttr('disabled');
		                 $('#statusResetInvalid').dialog('open');
	         		 }
	        	 }
	         }
	     });    
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