var userStatus;
$(document).ready(function(){	
	$('#statusResetInvalid, #statusResetError, #statusBlockError, #leaderInvalid').dialog({
			autoOpen: false,
			resizable: false,
			modal: true,
			buttons: {
				'CLOSE': function() {
					$(this).dialog('close');
				}
			}
		});
	
	$('.blockStatusBtn').click(function() {
		var element = $(this).parent().prev();
		var a = $(this);
		$(this).attr('disabled',true);
		userStatus = $(this).parent().prev().html();
		$(this).parent().prev().html('<img src='+urlImgSpinner+' alt="loading" width="12px" />');		
		$.ajax(
	     {
	     	url: urlBlockUser,
	     	type: "post",
	         data: ({ id: $(this).prev().prev().val()}),
	         dataType: "json",
	         callback: "myCallback",
	         cache: false,
	         success: function(data) {
	        	 element.html(block);
	        	 userStatus = block;
	        	 a.removeAttr('disabled');
	         },
	         error: function(xhr) {
	        	 if (xhr.status == 601){
	        		 a.removeAttr('disabled');
            		window.location.reload();
               	}
	        	 else{
	        		 element.html(userStatus);
	        		 a.removeAttr('disabled');
	                 $('#statusBlockError').dialog('open');
	        	 }
	         }
	     });  
	 });
	
	 $('.resetPassBtn').click(function() {
		var element = $(this).parent().prev();
		var a = $(this);
		$(this).attr('disabled',true);
		userStatus = $(this).parent().prev().html();
		$(this).parent().prev().html('<img src='+urlImgSpinner+' alt="loading" width="12px" />');
		$.ajax(
	     {
	     	url: urlResetPassword,
	     	type: "post",
	         data: ({ id: $(this).prev().val()}),
	         dataType: "json",
	         callback: "myCallback",
	         cache: false,
	         success: function(data) {
	        	 element.html(inactive);
	        	 userStatus = inactive;
	        	 a.removeAttr('disabled');
	         },
	         error: function(xhr) {
	        	 if (xhr.status == 601){
	        		 a.removeAttr('disabled');
            		window.location.reload();
               	}
	        	 else{
	        		 if (xhr.status == 404){
	        			element.html(userStatus);
		             	a.removeAttr('disabled');
		             	$('#statusResetError').dialog('open');
	        		 }
	        		 else if (xhr.status == 405){	 	             	
	 	             	 element.html(userStatus);
		        		 a.removeAttr('disabled');
		                 $('#statusResetInvalid').dialog('open');
	         		 }
	        	 }
	         }
	     });    
	 });
	 
	 $('.promote').click(function() {
		 var a = $(this);
		$(this).attr('disabled',true);
		$.ajax(
	     {
	     	url: urlPromote,
	     	type: "post",
	         data: ({ id: $(this).prev().val()}),
	         dataType: "json",
	         callback: "myCallback",
	         cache: false,
	         success: function(data) {
	        	 a.removeAttr('disabled');
	        	 window.location.reload();
	         },
	         error: function(xhr) {
	        	 if (xhr.status == 601){
	        		a.removeAttr('disabled');
	            	window.location.reload();
	             }
	        	 else{
	        		 a.removeAttr('disabled');
	                 $('#leaderInvalid').dialog('open');
	        	 }
	         }
	     });    
	 });
});