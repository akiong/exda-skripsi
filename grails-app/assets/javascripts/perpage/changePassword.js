var keys;
$(document).ready(function(){
	getKeys();
	
	$('#changePassword').dialog({
		autoOpen: false,
		resizable: false,
		modal: true,
		buttons: {
			'OK': function() {
				$('form').submit();
			}
		},
		close: function() {
			$('form').submit();
		}
	});
	$('#passwordInvalid, #notMatchPassword, #blankOldPassword, #blankNewPassword, #incorrectOldPassword, #invalidNewPassword').dialog({
		autoOpen: false,
		resizable: false,
		modal: true,
		buttons: {
			'OK': function() {
				$(this).dialog('close');
			}
		}
	});
	$('#lockedID').dialog({
		autoOpen: false,
		resizable: false,
		modal: true,
		buttons: {
			'OK': function() {
				window.location.reload();
			}
		},
		close: function() {
			window.location.reload();
		}
	});
});

function cekPassword() {
	var url = urlUpdatePassword;
	var oldPassword = $("#oldPassword").val();
	var newPassword = $("#newPassword").val();
	var confirmNewPassword = $("#confirmNewPassword").val();
	if (oldPassword == '') {
		$('#blankOldPassword').dialog('open');
		return;
	} 
	else if (newPassword == '') {
		$('#blankNewPassword').dialog('open');
		return;
	}
	else if (newPassword != confirmNewPassword) {
		$('#notMatchPassword').dialog('open');
		document.changePasswordForm.newPassword.value = "";
		document.changePasswordForm.confirmNewPassword.value = "";
		return;
	} 
	else if(passConfig != ''){
		var passPattern = new RegExp(passConfig);
		if (passPattern.test(newPassword) == false){
			document.changePasswordForm.newPassword.value = "";
			document.changePasswordForm.confirmNewPassword.value = "";
			$('#invalidNewPassword').dialog('open');
			return;
		}
	}
	
	$('#save').attr('disabled',true);
	$('#reset').attr('disabled',true);
	
	$.jCryption.encrypt(oldPassword, keys, function(encryptedPasswd) {
		$.jCryption.encrypt(newPassword, keys, function(encryptedPasswdNew) {
			$.ajax(
		    {
		       	url: url,
		        type: "post",
		        data: ({ oldPass: encryptedPasswd, newPass: encryptedPasswdNew }),
		        dataType: "jsonp",
		        callback: "myCallback",
		        cache: false,
		        success: function(data) {
		        	if(data.status == 200){
			        	$("form input:text, form input:password").val("");
			          	$('#changePassword').dialog('open');
		        	}
		        	if(data.status == 405){
		           		document.changePasswordForm.oldPassword.value = "";
		                $('#incorrectOldPassword').dialog('open');
		            }
		          	else if(data.status == 400){
		           		document.changePasswordForm.newPassword.value = "";
		           		document.changePasswordForm.confirmNewPassword.value = "";
		                $('#invalidNewPassword').dialog('open');
		            }
		          	else if(data.status == 406){
		          		$('#lockedID').dialog('open');
		            }
		        	
		        	$('#save').removeAttr('disabled');
					$('#reset').removeAttr('disabled');
		        },
		        error: function(xhr) {		          	
		          	if (xhr.status == 601){
		        		window.location.reload();
		           	}
		          	$('#save').removeAttr('disabled');
					$('#reset').removeAttr('disabled');
		        }
		    });
		});
	});
}

function getKeys() {
	$.ajaxSetup({ cache: false });
	$.jCryption.getKeys(encryptPaswd, function(receivedKeys) {		
		keys = receivedKeys;
		$.ajaxSetup({ cache: true });
	});
}

function resetField() {
	document.changePasswordForm.oldPassword.value = ""
	document.changePasswordForm.newPassword.value = ""
	document.changePasswordForm.confirmNewPassword.value = ""
}