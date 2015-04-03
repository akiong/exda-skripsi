var canSearch = true;
$(document).ready(function(){
	
	$('body').on('keyup.autocomplete','.roleName', function(){
		$(this).autocomplete({
			source: function( request, response ) {
				$.ajax({
					url: urlGetRoleLikeName,
					dataType: "jsonp",
					data: { name: request.term },
					success: function( data ) {
						response( $.map( data, function( item ) {
							return {
								label: item.authority,
								value: item.authority
							}
						}));
					},
					error: function(xhr) {
						if (xhr.status == 601){
                    		window.location.reload();
                       	}
					}
				});
			},
			minLength: minLength,
			delay: delay,
			select: function( event, ui ) {
				if(ui.item){
					$(this).val(ui.item.value);
					canSearch = true;
				}
			},
			focus: function(event, ui) { canSearch = false; },
			close: function(event, ui) { canSearch = true; }
		});
	});

	$('body').on('blur','.roleName', function(){
		if(canSearch && $(this).val() != ""){
			var id = ($(this).attr('id'));
			id = id.replace("roleName","");
			var duplicate = false;
			var oval
			for(var i = 0; i<roleCount;i++){
				if(i != id){
					oval = $("#roleName" + i).val().toUpperCase();
					if((oval != null) && (oval == $(this).val().toUpperCase())){
						duplicate = true;
						break;
					}
				}
			}
			if(duplicate == true){
				alert("role exist");
				$(this).val("");
			}
		}
	});
  
});
$('#username').blur(function() {
	if($('#username').val() != ""){
		var usernamePattern = /^[a-zA-Z0-9]*$/;
    	if (usernamePattern.test($('#username').val())){
			$('#usernameAvailable').html('<img src='+imgSpinner+' alt="loading" width="12px" />');
			checkUsernameAvailable();
    	}
    	else{
    		$('#usernameAvailable').html('<img src='+imgFalse+' alt="not available" width="12px" />');
    	}
	}
	else{
		$('#usernameAvailable').html("");
	}
});	
function checkUsernameAvailable(){
	var id = $('#username').val();
	$.ajax(
	{
		url: urlIsUsernameAvailable,
		contentType:"text/json",
		type: "get",
		data: ({ id: id}),
		dataType: "json",
		cache: false,
		async: false,
		success: function(data) {
			$('#usernameAvailable').html('<img src='+imgOk+' alt="available" width="12px" />');
		},
		error: function(xhr) {
			if (xhr.status == 601){
	    		window.location.reload();
	       	}
			else{
				$('#usernameAvailable').html('<img src='+imgFalse+' alt="not available" width="12px" />');
			}
		}
	});         		
}
function addRole() {
	var htmlId = "role" + roleCount; 
	var templateHtml = "<tr id='" + htmlId + "'>\n";
	templateHtml += "<td class='formLbl'><input type='text' style='float: left;' class='roleName' name='roleName' size='30' value='' id='roleName" + roleCount + "'/>";
	//templateHtml += "<input type='hidden' name='roleID' value='' id='roleID" + roleCount + "'/></td>\n";
	templateHtml += "<input class='redbtn' style='float: left; margin: 5px 0px 0px 4px;' type='button' value='"+btnremove+"' onclick='removeRole("+roleCount+")'/></td>\n";
	templateHtml += "</tr>\n";
	$("#roleTbl").append(templateHtml);
	roleCount++;
}
function removeRole(idx) {
	$("#role"+idx+"").remove()
}