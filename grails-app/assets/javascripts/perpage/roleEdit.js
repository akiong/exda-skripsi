$(document).ready(function(){
	$(".hideFirst").hide();
	$("#tree").jstree({ 
		"themes" : {
            "dots" : true,
            "icons" : false,
            "theme" : theTheme,
			"url" : theUrl + '/' + theTheme + '/style.css'
        },
        "plugins" : [ "themes", "html_data", "checkbox", "ui" ]
	});			
});

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
	
	$("form").attr("action",urlUpdt);
	$('form').submit();
}