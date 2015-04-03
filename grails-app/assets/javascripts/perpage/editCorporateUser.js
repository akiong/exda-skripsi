$(document).ready(function(){
	$('#firstNameBlank, #lastNameBlank, #userAliasBlank, #emailBlank, #phoneBlank, #phoneInvalid, #statusResetInvalid, #statusResetError, #statusBlockError').dialog({
		autoOpen: false,
		resizable: false,
		modal: true,
		buttons: {
			'CLOSE': function() {
				$(this).dialog('close');
			}
		}
	});
	
	if($('#sysAdmin').is(":checked")) {
    	$(".roles").attr("checked",false);
    	$(".roles").attr("disabled","disabled");
    }

	 $('#blockStatusBtn').click(function() {
			changeUserStatusBlock();
	 });

	 $('#resetPassBtn').click(function() {
			changeUserStatusReset();
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
	 
	 $('#sysAdmin').change(function() {
        if($(this).is(":checked")) {
        	$(".roles").attr("checked",false);
        	$(".roles").attr("disabled","disabled");
        }
        else{
        	$(".roles").removeAttr("disabled");
        }
    });         
});
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
 function checkValidation(){
	if($("#useralias").val() == ""){
		$('#userAliasBlank').dialog('open');
		return;
	}
	else if($("#firstName").val() == ""){
		$('#firstNameBlank').dialog('open');
		return;
	}
	else if($("#lastName").val() == ""){
		$('#lastNameBlank').dialog('open');
		return;
	}
	else if($("#email").val() == ""){
		$('#emailBlank').dialog('open');
		return;
	}
	else if($("#mobile").val() == ""){
		$('#phoneBlank').dialog('open');
		return;
	}
 
 	$('form').submit();	
	$('form').attr("disabled","true");
}

 function validateEmail(elementValue){  
	 var emailPattern = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;  
	 var statusEmailVal= emailPattern.test(elementValue);  
	 if (statusEmailVal == true){
		 $('#emailAvailable').html('<img src='+imgOk+' alt="available" width="12px" />');
     }
	 else {
    	 $('#emailAvailable').html('<img src='+imgFalse+' alt="not available" width="12px" />');
    }
 }  