$(document).ready(function(){	
	$('#accountBlank, #grupIdBlank, #groupNameBlank, #groupDbaNameBlank, #groupLocBlank, #firstNameBlank, #lastNameBlank, #userAliasBlank, #emailBlank, #phoneNoBlank, #emailInvalid').dialog({
		autoOpen: false,
		resizable: false,
		modal: true,
		buttons: {
			'OK': function() {
				$(this).dialog('close');
			}
		}
	});
	
	$('#duplicateName').dialog({
		autoOpen: false,
		resizable: false,
		modal: true,
		buttons: {
			'CLOSE': function() {
				$(this).dialog('close');
			}
		}
	});
});

function checkData(){
	var name = $("#city").val();
	var province = $("#province").val();
	
	if($("#name").val() == ""){
		$("#name").focus();
		$("#groupNameBlank").dialog('open');
		return;
	}
	else if($("#dbaName").val() == ""){
		$("#dbaName").focus();
		$("#groupDbaNameBlank").dialog('open');
		return;
	}
	else if(province == 'null'){
		$('#provinceBlank').dialog('open');
		return
	}	
	else if(name == 'null')
	{
		$('#cityBlank').dialog('open');
		return
	}
	else if($("#location").val() == ""){
		$("#location").focus();
		$("#groupLocBlank").dialog('open');
		return;
	}
	else if($("#ofname").val() == ""){
		$("#ofname").focus();
		$("#firstNameBlank").dialog('open');
		return;
	}
	else if($("#olname").val() == ""){
		$("#olname").focus();
		$("#lastNameBlank").dialog('open');
		return;
	}
	else if($("#ousername").val() == ""){
		$("#ousername").focus();
		$("#userIdBlank").dialog('open');
		return;
	}
	else if($("#ouserAlias").val() == ""){
		$("#ouserAlias").focus();
		$("#userAliasBlank").dialog('open');
		return;
	}
	else if($("#oemail").val() == ""){
		$("#oemail").focus();
		$("#emailBlank").dialog('open');
		return;
	}
	else if(validateEmail($("#oemail").val()) == false){
		$("#oemail").focus();
		$("#emailInvalid").dialog('open');
		return;
	}
	else if($("#omobilePhoneNo").val() == ""){
		$("#omobilePhoneNo").focus();
		$("#phoneNoBlank").dialog('open')
		return;
	}
//	else if($("#accoutnNo").val() == ""){
//		$("#accoutnNo").focus();
//		$("#accountBlank").dialog('open')
//		return;
//	}

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

function updateCity(e, selectId) {
	// The response comes back as a bunch-o-JSON
	var json = eval("(" + e.responseText + ")"); 
	if (json) {		
		var rselect = document.getElementById(selectId);
		// Clear all previous options
		
		var l = rselect.length;
		
		while (l > 0) {
			l--;
			rselect.remove(l);
		}
		
	    for (var x=0; x < json.length; x++) {
			var js = json[x];
			var opt = document.createElement('option');
			opt.text = js.city;
			opt.value = js.id;
		  	try {
		    	rselect.add(opt, null); // standards compliant; doesn't work in IE
		  	}
	  		catch(ex) {
	    		rselect.add(opt); // IE only
	  		}
		}	    
	}
}