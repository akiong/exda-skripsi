$(document).ready(function(){
	DisplayTime();
	
	$(".setting").click(function(){
		$(this).addClass("settingclick");
		$('.popout').addClass("popoutclick");
	});
	
	$("#body").click(function(){
		$('.popout').removeClass("popoutclick");
		$('.setting').removeClass("settingclick");
	});
});
			
function DisplayTime(){
	var CurrentDate = new Date(serverDate);
	serverDate += 1000;
	
	$("#timeStr").text(dateFormat(CurrentDate, "mmmm d, yyyy HH:MM"));				
	setTimeout("DisplayTime()",1000);
}
