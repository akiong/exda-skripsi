$(document).ready(function() {
 
	$('#nameBlank, #numberSenderBlank, #emailInvalid, #numberReceiverBlank, #addressBlank, #userIdBlank, #userIdSame, #userAliasBlank, #emailBlank, #phoneNoBlank, #emailInvalid').dialog({
		autoOpen: false,
		resizable: false,
		modal: true,
		buttons: {
			'OK': function() {
				$(this).dialog('close');
			}
		}
	});
	
 });

function addbaris(x){

	var kolom1 = $("#kolom1").html();
	var kolom2 = $("#kolom2").html();
	var kolom3 = $("#kolom3").html();
	var kolom4 = $("#kolom4").html();
	
	x=x+1; 
	
	var withoutButton = "<tr id='rows"+x+"'><td>"+kolom1+"</td> <td>"+kolom2+"</td> <td>"+kolom3+"</td> <td>"+kolom4+"</td>";
	
	
	
	var kolom5 = "<td><input type='button' onClick='addbaris("+x+");' name='addRow' class='add addRow' value='+' /> \n";
	kolom5 = kolom5 +"<input type='button' onClick='minbaris("+x+");' name='remRow' class='redbtn remRow' value='-'/></td></tr>";
	
	withoutButton = withoutButton + kolom5;
	
	$('.table13 tr:last').after(withoutButton);
}

function minbaris(x){
	$("#rows"+x).remove();
}

function validateEmail(elementValue){  
	 var emailPattern = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;  
	 var statusEmailVal= emailPattern.test(elementValue);  
	 if (statusEmailVal == true){
		 return true;
   }
	return false;
}

function checkName(){
	if($("#senderName").val() == ""){
		$("#senderName").focus();
		$("#nameBlank").dialog('open');
		return;
	}

	if($("#noTelpSender").val() == "" && $("#noHpSender").val() == ""){
		$("#noTelpSender").focus();
		$("#numberSenderBlank").dialog('open');
		return;
	}
	if($("#emailSender").val() == ""){
		$("#emailSender").focus();
		$("#emailBlank").dialog('open');
		return;
	}
	if(validateEmail($("#emailSender").val()) == false){
		$("#emailSender").focus();
		$("#emailInvalid").dialog('open');
		return;
	}
	if($("#addressSender").val() == ""){
		$("#addressSender").focus();
		$("#addressBlank").dialog('open');
		return;
	}
	if($("#receiverName").val() == ""){
		$("#receiverName").focus();
		$("#nameBlank").dialog('open');
		return;
	}
	if($("#noTlpReceiver").val() == "" && $("#noHpReceiver").val() == ""){
		$("#noTlpReceiver").focus();
		$("#numberReceiverBlank").dialog('open');
		return;
	}
	if($("#emailReceiver").val() == ""){
		$("#emailReceiver").focus();
		$("#emailBlank").dialog('open');
		return;
	}
	if(validateEmail($("#emailReceiver").val()) == false){
		$("#emailReceiver").focus();
		$("#emailInvalid").dialog('open');
		return;
	}
	if($("#addressReceiver").val() == ""){
		$("#addressReceiver").focus();
		$("#addressBlank").dialog('open');
		return;
	}
}
