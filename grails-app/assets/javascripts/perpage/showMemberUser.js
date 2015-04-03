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
		console.log("blockStatusBtn");
		changeUserStatusBlock();
	 });
	
	 $('#resetPassBtn').click(function() {
			changeUserStatusReset();
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