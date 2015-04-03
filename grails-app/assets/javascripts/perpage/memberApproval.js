$(document).ready(function(){		
	$('.approve').click(function() {			
		//$("#aUserLimit").val(removeFormat($("#userLimit").val()));
		$(this).parent().submit();
		$(this).parent().attr("disabled","true");
	 });
	
	$('.reject').click(function() {
		$(this).parent().submit();
		$(this).parent().attr("disabled","true");
	 });
});