var canSearch = true;
$(document).ready(function(){
	$('#statusResetInvalid, #statusResetError, #statusBlockError, #corpNameBlank, #address1Blank, #firstNameBlank, #lastNameBlank, #userAliasBlank, #emailBlank, #phoneNoBlank, #emailInvalid').dialog({
		autoOpen: false,
		resizable: false,
		modal: true,
		buttons: {
			'OK': function() {
				$(this).dialog('close');
			}
		}
	});
	
	$('#blockStatusBtn').click(function() {
		changeUserStatusBlock();
	 });
	
	 $('#resetPassBtn').click(function() {
			changeUserStatusReset();
	 });
	 
	 $('#blockStatusBtn2').click(function() {
			changeUserStatusBlock2();
		 });
		
	 $('#resetPassBtn2').click(function() {
			changeUserStatusReset2();
	 });
});

function checkData(){
	if($("#corpName").val() == ""){
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
function changeUserStatusBlock(){
	$('#blockStatusBtn').attr('disabled',true);
	$('#userStatus').html('<img src='+urlImgSpinner+' alt="loading" width="12px" />');
		$.ajax(
     {
     	url: urlBlockUser,
     	type: "post",
         data: ({ id: id}),
         dataType: "json",
         callback: "myCallback",
         cache: false,
         success: function(data) {
        	 $('#userStatus').html(block);
        	 userStatus = block;
        	 $('#blockStatusBtn').removeAttr('disabled');
         },
         error: function(xhr) {
        	 if (xhr.status == 601){
            		window.location.reload();
               	}
        	 else{
                	$('#userStatus').html(userStatus);
                 	$('#blockStatusBtn').removeAttr('disabled');
                 	$('#statusBlockError').dialog('open');
        	 }
         }
     });         		
}

function changeUserStatusReset(){
	$('#resetPassBtn').attr('disabled',true);
	$('#userStatus').html('<img src='+urlImgSpinner+' alt="loading" width="12px" />');
		$.ajax(
     {
     	url: urlResetPassword,
     	type: "post",
         data: ({ id: id}),
         dataType: "json",
         callback: "myCallback",
         cache: false,
         success: function(data) {
        	 $('#userStatus').html(inactive);
        	 userStatus = inactive;
        	 $('#resetPassBtn').removeAttr('disabled');
         },
         error: function(xhr) {
        	 if (xhr.status == 601){
            		window.location.reload();
               	}
        	 else{
        		 if (xhr.status == 404){
	            	$('#userStatus').html(userStatus);
	             	$('#resetPassBtn').removeAttr('disabled');
	             	$('#statusResetError').dialog('open');
        		 }
        		 else if (xhr.status == 405){
 	            	$('#userStatus').html(userStatus);
 	             	$('#resetPassBtn').removeAttr('disabled');
 	             	$('#statusResetInvalid').dialog('open');
         		 }
        	 }
         }
     });         		
}
function changeUserStatusBlock2(){
	$('#blockStatusBtn2').attr('disabled',true);
	$('#userStatus2').html('<img src='+urlImgSpinner+' alt="loading" width="12px" />');
		$.ajax(
     {
     	url: urlBlockUser,
     	type: "post",
         data: ({ id: id2}),
         dataType: "json",
         callback: "myCallback",
         cache: false,
         success: function(data) {
        	 $('#userStatus2').html(block);
        	 userStatus = block;
        	 $('#blockStatusBtn2').removeAttr('disabled');
         },
         error: function(xhr) {
        	 if (xhr.status == 601){
            		window.location.reload();
               	}
        	 else{
                	$('#userStatus2').html(userStatus);
                 	$('#blockStatusBtn2').removeAttr('disabled');
                 	$('#statusBlockError').dialog('open');
        	 }
         }
     });         		
}

function changeUserStatusReset2(){
	$('#resetPassBtn2').attr('disabled',true);
	$('#userStatus2').html('<img src='+urlImgSpinner+' alt="loading" width="12px" />');
		$.ajax(
     {
     	url: urlResetPassword,
     	type: "post",
         data: ({ id: id2}),
         dataType: "json",
         callback: "myCallback",
         cache: false,
         success: function(data) {
        	 $('#userStatus2').html(inactive);
        	 userStatus = inactive;
        	 $('#resetPassBtn2').removeAttr('disabled');
         },
         error: function(xhr) {
        	 if (xhr.status == 601){
            		window.location.reload();
               	}
        	 else{
        		 if (xhr.status == 404){
	            	$('#userStatus2').html(userStatus);
	             	$('#resetPassBtn2').removeAttr('disabled');
	             	$('#statusResetError').dialog('open');
        		 }
        		 else if (xhr.status == 405){
 	            	$('#userStatus2').html(userStatus);
 	             	$('#resetPassBtn2').removeAttr('disabled');
 	             	$('#statusResetInvalid').dialog('open');
         		 }
        	 }
         }
     });         		
}