$(document).ready(function(){
				$("#tree").jstree({ 
					"themes" : {
			            "dots" : true,
			            "icons" : false,
			            "theme" : theTheme,
						"url" : theUrl + '/' + theTheme + '/style.css'
			        },
			        "plugins" : [ "themes", "html_data", "ui", "crrm"]
				}).bind("create.jstree", function (e, data) {
					var id = $("#menuOption").val();
					$("#menuOption option:selected").remove();
					$(data.rslt.obj).attr("id", id);
					var parentId = 0;
					var parentNode = $("#tree").jstree("get_selected")[0];
					if(parentNode != null){
						parentId = parentNode.id
					}
					$(data.rslt.obj).children().first().after("<input type='hidden' name='menu."+parentId+"' value='"+id+"'>")
				});	

				$("#menuBtn").click(function () {
					if($("#menuOption").val() != null){
						if($("#tree").jstree("get_selected")[0] == null || $("#tree").jstree("get_selected")[0].parentNode.className != ""){
							$("#tree").jstree("create", null, "last", $("#menuOption option:selected").text(), false, true);
						}
					}
				});

				$("#removeBtn").click(function () {					
					var node = $("#tree").jstree("get_selected")[0];
					if(node != null){
						var obj = $(node).children("a:eq(0)");
						obj.children("INS").remove();
						$("#menuOption").append("<option value='"+node.id+"'>"+obj.html()+"</option>");
						var arr = $(node).children("ul:eq(0)").children("li");
						if(arr.size() > 0){
							addOption(arr);		
						}		
					}
					$("#tree").jstree("remove");
					clearSelectNode();
				});			
			});

			function clearSelectNode(){
				$("#tree").jstree("deselect_all");						
			}

			function addOption(arr){
				var node;
				var obj;
				var childNode;
				for (var j=0; j<arr.length; j++){
					node = $(arr[j])[0];
					obj = $(node).children("a:eq(0)")
					obj.children("INS").remove()
					$("#menuOption").append("<option value='"+node.id+"'>"+obj.html()+"</option>");
					childNode = $(node).children("ul:eq(0)").children("li");
					if(childNode.size() > 0){
						addOption(childNode);
					}
				}
			}