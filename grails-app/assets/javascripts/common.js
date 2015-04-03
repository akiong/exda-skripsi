$(document).ready(function(){
	var bodyWidth = $("#contentBank").width();
	$( ".listcontent" ).css({width: (bodyWidth - 220 - 25 - 15) + "px"});
	$( window ).resize(function() {
		bodyWidth = $("#contentBank").width();
		$( ".listcontent" ).css({width: (bodyWidth - 220 - 25 - 15) + "px"});
	});
    $(document).bind("contextmenu",function(e){
        return false;
    });
    $("input[type='text']").attr("autocomplete","off");
    
    // tricking css colspan for button for netbank n mutiara css
    if($("td.pre_button").css('display') == 'none')
	{
		$("td.pre_button + td").attr('colspan','2');
	}
    
    $('body').on('focus','.form .value input[type=text], .form .value input[type=password], .form .value select, .form .value textarea', function(){  
    	$("label[for='"+this.id+"']").addClass("focus");
    });
    $('.listfilter').on('focus','input[type=text], input[type=password], select, textarea', function(){
    	$("label[for='"+this.id+"']").addClass("focus");
    });
    $('.listfilterAtas').on('focus','input[type=text], input[type=password], select, textarea', function(){
    	$("label[for='"+this.id+"']").addClass("focus");
    });
    $('body').on('blur','.form .value input[type=text], .form .value input[type=password], .form .value select, .form .value textarea', function(){  
    	$("label[for='"+this.id+"']").removeClass("focus");
    });
    $('.listfilter').on('blur','input[type=text], input[type=password], select, textarea', function(){
    	$("label[for='"+this.id+"']").removeClass("focus");
    });
    $('.listfilterAtas').on('blur','input[type=text], input[type=password], select, textarea', function(){
    	$("label[for='"+this.id+"']").removeClass("focus");
    });
    $('body').on('keypress','.numeric', function(e){
    	if (!checkIt(e)){
    		e.preventDefault();
        }
    });
});

function checkIt(evt) {
    evt = (evt) ? evt : window.event
    
    var charCode = (evt.which) ? evt.which : evt.keyCode
    
    		if((evt.ctrlKey && charCode == 118 /* firefox */) || (evt.ctrlKey && charCode == 86) /* opera */
    				|| (evt.shiftKey && charCode == 45)) return true;
    		if((evt.ctrlKey && charCode == 99 /* firefox */) || (evt.ctrlKey && charCode == 67) /* opera */) return true;		
    		
    	if (charCode > 31 && (charCode < 48 || charCode > 57)) {
    	if(
    		charCode != 8 /* backspace */ &&
    		charCode != 9 /* tab */ &&
    		charCode != 13 /* enter */ &&
    		charCode != 35 /* end */ &&
    		charCode != 36 /* home */ &&
    		charCode != 37 /* left */ &&
    		charCode != 39 /* right */ &&
    		charCode != 46 /* del */
		)
		{
			return false;
		}
        return true;
    }
    return true;
}