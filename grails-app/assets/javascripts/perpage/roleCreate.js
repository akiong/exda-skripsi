$(document).ready(function(){
	$(".hideFirst").hide();

	$("#tree").jstree({ 
		"themes" : {
            "dots" : true,
            "icons" : false,
            "theme" : theTheme,
			"url" : theUrl + '/' + theTheme + '/style.css'
        },
        "plugins" : [ "themes", "html_data", "checkbox","ui" ]
	});	
	 
	$('#authorityExistSection, #authorityError').dialog({
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

function checkName(){				
	var authority = $("#authority").val();
	if((authority.indexOf("'") >= 0) || (authority.indexOf(",") >= 0)){
		$('#authorityError').dialog('open');
		return;
	}
	$.ajax(
    {
    	url: url,
        type: "get",
        data: ({ authority: authority }),
        dataType: "jsonp",
        callback: "myCallback",
        cache: false,
        success: function(data) {
        	populateNodes();
        	$('form').submit();
        },
        error: function(xhr) {
        	if (xhr.status == 601){
        		window.location.reload();
           	}
        	else{
        		$('#authorityExistSection').dialog('open');
        	}
        }
    });
}

function populateNodes(){
	var node;
	var id;
	$('#tree .jstree-checked').each(function () {
        node = $(this);
        id = node.attr('id');
		$("<input>").attr("type", "hidden").attr("name", "menu").val(id).appendTo("#tree");
    });
	$('#tree .jstree-undetermined').each(function () {
        node = $(this);
        id = node.attr('id');
		$("<input>").attr("type", "hidden").attr("name", "menu").val(id).appendTo("#tree");
    });
}