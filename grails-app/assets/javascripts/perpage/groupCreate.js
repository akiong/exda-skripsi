$(document).ready(function(){		
	$('#accountBlank, #grupIdBlank, #groupNameBlank, #groupDbaNameBlank, #groupLocBlank, #firstNameBlank, #lastNameBlank, #userIdBlank, #userAliasBlank, #emailBlank, #phoneNoBlank, #emailInvalid').dialog({
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
	
	$('#ousername').blur(function() {
		var usernamenya = $('#ousername').val();
		if(usernamenya != ""){
			var usernamePattern = /^[a-zA-Z0-9]*$/;
	    	if (usernamePattern.test(usernamenya)){
				$('#usernameAvailable1').html('<img src='+imgSpinner+' alt="loading" width="12px" />');
				checkUsernameAvailable(usernamenya, "usernameAvailable1");
	    	}
	    	else{
	    		$('#usernameAvailable1').html('<img src='+imgFalse+' alt="not available" width="12px" />');
	    	}
		}
		else{
			$('#usernameAvailable1').html("");
		}
	});	
	
	$('#username').blur(function() {
		var usernamenya = $('#username').val();
		if(usernamenya != ""){
			var usernamePattern = /^[a-zA-Z0-9]*$/;
	    	if (usernamePattern.test(usernamenya)){
				$('#usernameAvailable').html('<img src='+imgSpinner+' alt="loading" width="12px" />');
				checkUsernameAvailable(usernamenya, "usernameAvailable");
	    	}
	    	else{
	    		$('#usernameAvailable').html('<img src='+imgFalse+' alt="not available" width="12px" />');
	    	}
		}
		else{
			$('#usernameAvailable').html("");
		}
	});	
});

function checkData(){
	var name = $("#city").val();
	var province = $("#province").val();
	
	if($("#grupId").val() == ""){
		$("#grupId").focus();
		$("#grupIdBlank").dialog('open');
		return;
	}
	else if($("#name").val() == ""){
		$("#name").focus();
		$("#groupNameBlank").dialog('open');
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
	else if($("#dbaName").val() == ""){
		$("#dbaName").focus();
		$("#groupDbaNameBlank").dialog('open');
		return;
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
	else if($("#accoutnNo").val() == ""){
		$("#accoutnNo").focus();
		$("#accountBlank").dialog('open')
		return;
	}
	else if($("#firstName").val() == ""){
		$("#firstName").focus();
		$("#firstNameBlank").dialog('open');
		return;
	}
	else if($("#lastName").val() == ""){
		$("#lastName").focus();
		$("#lastNameBlank").dialog('open');
		return;
	}
	else if($("#username").val() == ""){
		$("#username").focus();
		$("#userIdBlank").dialog('open');
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
	
	var grupId = $("#grupId").val();
	$.ajax(
    {
    	url: url,
    	type: "get",
        data: ({grupId: grupId}),
        dataType: "jsonp",
        callback: "myCallback",
        cache: false,
        success: function(data) {
        	
        	$('.price').each(function(){
        		$(this).val(removeFormat($(this).val()));
        	});
        	
        	$('form').submit();
        	$('form').attr("disabled","true");
        },
        error: function(xhr) {
        	if(xhr.status == 405){
        		$('#duplicateName').dialog('open');
            }
            else if (xhr.status == 601){
        		window.location.reload();
           	}
        }
    }); 
}

function validateEmail(elementValue){  
	 var emailPattern = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;  
	 var statusEmailVal= emailPattern.test(elementValue);  
	 if (statusEmailVal == true){
		 return true;
    }
	return false;
}  
function checkUsernameAvailable(usernamenya, idspan){
	$.ajax(
	{
		url: urlIsUsernameAvailable,
		contentType:"text/json",
		type: "get",
		data: ({ id: usernamenya}),
		dataType: "json",
		cache: false,
		async: false,
		success: function(data) {
			$('#'+idspan).html('<img src='+imgOk+' alt="available" width="12px" />');
		},
		error: function(xhr) {
			if (xhr.status == 601){
	    		window.location.reload();
	       	}
			else{
				$('#'+idspan).html('<img src='+imgFalse+' alt="not available" width="12px" />');
			}
		}
	});         		
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