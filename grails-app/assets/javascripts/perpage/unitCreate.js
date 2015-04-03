
////$(function(){
////    $("#city").blur(function(){
////      if($(this).length > 0) {
////        var url = "${createLink(controller:'City', action:'cekavaiable')}"
////        $.getJSON(url, {id:$(this).val()}, function(json){
////          if(!json.available) {
////            $("#city").css("border", "1px solid red");
////            	$('#nameExistSection').dialog('open');
////          }
////        });
////      }
////    });
////  });


$(document).ready(function(){       
	$('#nameExistSection, #NameBlank, #codeBlank, #duplicateHost').dialog({
		autoOpen: false,
		resizable: false,
		modal: true,
		buttons: {
			'CLOSE': function() {
				$(this).dialog('close');
			}
		}
	});
	$('#replaceSection').dialog({
		autoOpen: false,
		resizable: false,
		modal: true,
		buttons: {	
			'YES': function() {
				$(this).dialog('close');
				$("form").attr("action","replaceUnit");
				$('form').submit();
			},
			'NO': function() {
				$(this).dialog('close');
			}
			
		}
	});
});	

function addHostCode(){
	if(currentHost < hostSize){
		var option = $("#host1").html();
		var inner = "<tr><td><select class=\"host\" name=\"host\">"+option+"</select></td>"
		inner += "<td><input type=\"text\" maxlength=\"100\" name=\"code\" class=\"code\" autocomplete\"off\"></td>";
		inner += "<td><input style='float:left; margin: 5px 0 0 4px;' class='ui-icon ui-icon-trash' type='button' value='' onclick='delThisTr(this)'/></td></tr>";
		$("#curHost").append(inner);
		currentHost += 1;
	}
}

function delThisTr(tr){
	$(tr).parent().parent().remove();
	currentHost -= 1;
}

function checkName(){	
	var name = $("#name").val();
	if(name == ""){
		$("#NameBlank").dialog('open')
		return;
	}
	$.ajax(
    {
    	url: url,
    	type: "get",
        data: ({name: name}),
        dataType: "jsonp",
        callback: "myCallback",
        cache: false,
        success: function(data) {
            $('form').submit();
        },
        error: function(xhr) {
        	if(xhr.status == 409){
        		$('#replaceSection').dialog('open');
            }
        	else if(xhr.status == 405){
        		$('#nameExistSection').dialog('open');
            }
            else if (xhr.status == 601){
        		window.location.reload();
           	}
        }
    });         		
}