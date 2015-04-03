$(document).ready(function(){
	var CurrentLongDate= serverDate;
	DisplayTime(CurrentLongDate);
	
	$("#userId").watermark("User ID		");
	$("#password").watermark("Password		");
	
	$('#usernamePassError').dialog({
		autoOpen: false,
		resizable: false,
		modal: true,
		buttons: {
			'CLOSE': function() {
				$(this).dialog('close');
			}
		}
	});
	
	$('#usernameBlock').dialog({
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
	
	$('#loginBtn').click(function(){
        authAjax();
	});
	
	$('#teamDialog').click(function(){
		$("#teraTeam").show();
		$("#teraTeam").animate({
			opacity: 0.8
		}, 200, 'linear').animate({
			opacity: 0.8
			}, 4000, 'linear').animate({
			opacity: 0
			}, 200, 'linear', function() {
				$("#teraTeam").hide();
			});
		$("#teraTeam .wrapper").delay(200).animate({
			top: "-50%"
			}, 4000, 'linear', function() {
				$("#teraTeam .wrapper").css({ top: "100%"});
	  });
	});
});

function authAjax() {
	var posturl = urlAjaxSuccess;	
	$.ajaxSetup({ cache: false });	 
	$.jCryption.getKeys(encryptPaswd, function(receivedKeys) {
		$.ajaxSetup({ cache: true });
		$.jCryption.encrypt($("#password").val(), receivedKeys, function(encryptedPasswd) {
			var data = {j_username:$('#userId').val(), j_password: encryptedPasswd}	
			$.ajax(
			  {	
				url: posturl,
				type: "post",
				data: (data),
				dataType: "json",
				callback: "myCallback",
				cache: false,
				success: function(data) {
					if(data.success == true){
						if(data.change == false){
							window.location.replace(urlBank);
						}
						else{
							window.location.replace(urlFirstPassword);
						}
					}
					else{
						if(data.reload == true){
							window.location.reload();
						}
						else if(data.block == true){
							$('#usernameBlock').dialog('open');
						}
						else{
							$('#usernamePassError').dialog('open');
						}
					}
					
				}
			});
		});
	});
}

function DisplayTime(CurrentLongDate){
	var CurrentDate = new Date(CurrentLongDate);
	CurrentLongDate += 1000;
	
	$("#timeStr").text(dateFormat(CurrentDate, "mmmm d, yyyy HH:MM"));				
	setTimeout("DisplayTime("+CurrentLongDate+")",1000);
}