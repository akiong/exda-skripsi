function CurrencyFormatted(amount)
{
	var i = parseFloat(amount);
	if(isNaN(i)) { i = 0.00; }
	var minus = '';
	if(i < 0) { minus = '-'; }
	i = Math.abs(i);
	i = parseInt((i + .005) * 100);
	i = i / 100;
	s = new String(i);
	if(s.indexOf('.') < 0) { s += '.00'; }
	if(s.indexOf('.') == (s.length - 2)) { s += '0'; }
	s = minus + s;
	return s;
} // function CurrencyFormatted()

function CommaFormatted(amount)
{
	var delimiter = ".";
	var a = amount.split('.',2)
	var d = a[1];
	var i = parseInt(a[0]);
	if(isNaN(i)) { return ''; }
	var minus = '';
	if(i < 0) { minus = '-'; }
	i = Math.abs(i);
	var n = new String(i);
	var a = [];
	while(n.length > 3)
	{
		var nn = n.substr(n.length-3);
		a.unshift(nn);
		n = n.substr(0,n.length-3);
	}
	if(n.length > 0) { a.unshift(n); }
	n = a.join(delimiter);
	if(d.length < 1) { amount = n; }
	else { amount = n + ',' + d; }
	amount = minus + amount;
	return amount;
} // function CommaFormatted()
function removeFormat(str)
{
	//remove dot
	var n = str.replace(/\./g, "");
	//find comma and change to dot, so grails can accept it as bigdecimal
	return n.replace(',','.');
}

// default for all class price
$(".price").each(function(){
	var fval = CurrencyFormatted($(this).val());
	fval = CommaFormatted(fval);
	$(this).val(fval);
});

//$(".price").live('focus', function(){
$('body').on('focus','.price', function(){
	$(this).val(removeFormat($(this).val()));
});
$("span.price").each(function(){
	var fval = CurrencyFormatted($(this).text());
	fval = CommaFormatted(fval);
	$(this).text(fval);
});
$("span.priceBeforeSym").each(function(){
	var fval = CurrencyFormatted($(this).text());
	fval = CommaFormatted(fval);
	$(this).text(fval);
});
$("label.price").each(function(){
	var fval = CurrencyFormatted($(this).text());
	fval = CommaFormatted(fval);
	$(this).text(fval);
});
$("td.price").each(function(){
	var fval = CurrencyFormatted($(this).text());
	fval = CommaFormatted(fval);
	$(this).text(fval);
});
//$('.price').live('blur', function() {
$('body').on('blur','.price', function() {
	if(this.value == ""){
		this.value = "0,00"
	}
	else
	{
		var fvalue = CurrencyFormatted(this.value);
		fvalue = CommaFormatted(fvalue);
		this.value = fvalue;
	}
});