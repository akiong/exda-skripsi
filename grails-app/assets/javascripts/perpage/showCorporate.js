$(document).ready(function(){
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
	    
    $(".hide1").hide();
    $(".hide2").hide();
    $(".hide3").hide();
			      

		 
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