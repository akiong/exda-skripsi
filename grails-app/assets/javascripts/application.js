//= require jquery-ui.min
//= require jquery.jstree
//= require /perpage/jquery
//= require /perpage/jquery.dataTables
//= require jquery.watermark.min
//= require /jquery/jquery.jcryption-1.1
//= require bootstrap

//= require numeric
//= require common
//= require formatCurrency
//= require date.format


if (typeof jQuery !== 'undefined') {
	(function($) {
		$('#spinner').ajaxStart(function() {
			$(this).fadeIn();
		}).ajaxStop(function() {
			$(this).fadeOut();
		});
	})(jQuery);
}
