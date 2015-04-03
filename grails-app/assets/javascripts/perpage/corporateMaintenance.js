var ticked = 0;
$(document).ready(function(){
	
	$('#checkAll').click(function() {
		if(this.checked){
			ticked = total;
			$('.checkk').prop('checked',true);
		}
		else{
			$('.checkk').prop('checked',false);
			ticked = 0;
		}
	});
	
	$('.checkk').click(function() {
		if(this.checked){
			ticked++;
		}
		else{
			ticked--;
		}
		if(ticked == total){
			$('#checkAll').prop('checked',true);
		}
		else{
			$('#checkAll').prop('checked',false);
		}
	});
});