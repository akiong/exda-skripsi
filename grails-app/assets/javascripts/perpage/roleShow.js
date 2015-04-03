$(document).ready(function(){
	$("#tree").delegate("ins.jstree-checkbox", "click.jstree", function(e) {
		e.stopImmediatePropagation();
		return false;
	}).jstree({ 
		"themes" : {
			"dots" : true,
			"icons" : false,
			"theme" : theTheme,
			"url" : theUrl + '/' + theTheme + '/style.css'
		},
		"plugins" : [ "themes", "html_data", "checkbox"]
	})
	
	$('body').on('click','.atr3', function(event){  
		event.preventDefault();
    });
});