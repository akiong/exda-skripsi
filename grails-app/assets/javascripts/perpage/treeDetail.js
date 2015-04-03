
			$(document).ready(function(){

				$("#tree").delegate("ins", "click.jstree", function(e) {
					return false;
				}).jstree({ 
					"themes" : {
			            "dots" : true,
			            "icons" : false,
						"theme" : theTheme,
						"url" : theUrl + '/' + theTheme + '/style.css'
			        },
			        "plugins" : [ "themes", "html_data"]
				});	
			});