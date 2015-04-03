var tempPL="";
$(document).ready(function(){
	$('#dateOfBirth').datepicker({ autoSize: true,
		changeMonth: true,
		changeYear: true,
		dateFormat: 'dd/mm/yy',
		maxDate: new Date(time)
	});
	
	
	if(dob != ""){
		$('#dateOfBirth').datepicker( "setDate" , new Date(dob) );
	}
	
//	$("#parentCifId").watermark(enterCorp + "		");
//	
//	$('#parentCifId').live('keyup.autocomplete', function(){
//		$("#parentCompanyName").text("");
//		$(this).autocomplete({
//			source: function( request, response ) {
//				$.ajax({
//					url: urlGetCif,
//					dataType: "jsonp",
//					data: { name: request.term },
//					success: function( data ) {
//						response( $.map( data, function( item ) {
//							return {
//								label: item.cifId + " - " + item.firstName + " "+ item.lastName,
//								value: item.cifId,
//								name: item.firstName + " "+ item.lastName,
//								sp: ((item.servicePackage)? item.servicePackage.id : 0)
//							}
//						}));
//					},
//                	error: function(xhr) {
//                		if (xhr.status == 601){
//                    		window.location.reload();
//                       	}
//                	}
//				});
//			},
//			minLength: minLength,
//			delay: delay,
//			select: function( event, ui ) {
//				if(ui.item){
//					tempPL = ui.item.value;
//					$(this).val(ui.item.value);
//					$("#parentCompanyName").text(ui.item.name);
//					$("#servicePackage").val(ui.item.sp);
//				}
//			},
//			change: function(event, ui) { 				
//				if($('#parentCifId').val() != ""){
//					if($("#parentCifId").val() != tempPL){
//						$(".btn").attr('disabled',true);
//						tempPL = $("#parentCifId").val();
//						getParentCompany();
//					}
//				}
//				else{
//					tempPL = "";
//					$("#parentCompanyName").text("");
//				}
//			}
//		});
//	});
});

//function getParentCompany(){
//	$.ajax(
//            {
//    	url: urlGetParent,
//        type: "get",
//        data: ({ cifId: $("#parentCifId").val()}),
//        dataType: "jsonp",
//        callback: "myCallback",
//		cache: false,
//        success: function(data) {
//        	$("#parentCompanyName").text(data.firstName + " " + data.lastName);
//        	if(data.spId != 0){
//        		$("#servicePackage").val(data.spId);
//        	}
//            $(".btn").removeAttr('disabled');
//        },
//        error: function(xhr) {
//        	$("#parentCompanyName").text("");
//        	if (xhr.status == 601){
//        		window.location.reload();
//        	}
//        	$(".btn").removeAttr('disabled');
//        }
//    });
//}