//------------------------ajax���ֿ�ʼ---------------------------------
var xmlHttpFrom = 0;
var customerList="1,2,3,4,5,6,7,8,9,12,11,13,14,15,16,17";
function createXMLHttpRequest() {
	var xmlHttp;
	if (window.ActiveXObject) {  // IE
		try {
			xmlHttp = new ActiveXObject("Msxml2.XMLHTTP");
		}catch (e) {
			try {
				xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
			}catch (e) {
			}
		}
	} else {
		if (window.XMLHttpRequest) {	//
			xmlHttp = new XMLHttpRequest();
			if (xmlHttp.overrideMimeType) {
				xmlHttp.overrideMimeType("text/xml");
			}
		}
		xmlHttpFrom = 1;
	}
	return xmlHttp;
}	
		
function handleStateChange() {//alert("cust");
	if(xmlHttp.readyState == 4) {	
		if(xmlHttp.status == 200) {	 
			var value = xmlHttp.responseText;//
			countryList = value;
		}
	}
}

function getCustomersByType(exvalue){
	var url = "/pt/country/getCountryList?countryName="+ exvalue;
	xmlHttp = createXMLHttpRequest();	     
	xmlHttp.onreadystatechange = handleStateChange;
	if(xmlHttpFrom==1){
		xmlHttp.open("POST", url, true);//Firefox
	}
	else xmlHttp.open("POST", url, false);//IE
	xmlHttp.send(null);
}
//================================ajax���ֽ���===================================================
var _targetObject = null;
var _selectFlag = 0;
var _trLength = 0;
var countryid = 0;
var prefix = '';
function Customer(pre,index) {
	countryid = index;
	prefix = pre;
	this.dataControl = null;
	this.panel  = this.getElementById("__customerPanel");
	this.iframe = window.frames["__customerIframe"];
	this.form = null;	
	this.colors = {"bg_cur_day":"#00CC33","bg_over":"#FFFFFF","bg_out":"#8EC7FF"};
}

Customer.prototype.draw = function() {
	customer = this;
	var _cs = [];
	_cs[_cs.length] = '<form id="__customerForm" name="__customerForm" method="post">';
	_cs[_cs.length] = "<div id='__customerPanel2' style='overflow-x: hidden; overflow-y:auto;SCROLLBAR-FACE-COLOR: #c6e6fc; '>";
	_cs[_cs.length] = '<table id="__customerTable" width="200" border="0" cellpadding="0" cellspacing="0" align="left">';
	var customers =eval('('+countryList+')');//;
	for(var i = 0; i < customers.length; i++){
		_cs[_cs.length] = "<tr height='17' align='left'><td align='left' style='display: none;'>"+customers[i].id+"<\/td><td align='left'>"+customers[i].countryName+"<\/td><\/tr>";
	}
	_cs[_cs.length] = '<\/table>';
	_cs[_cs.length] = '<\/div>';
	_cs[_cs.length] = '<\/form>';
	this.iframe.document.body.innerHTML = _cs.join("");
	this.form = this.iframe.document.forms["__customerForm"];
};

Customer.prototype.bindCustomer = function() {
	var calendar = this;
	var tds = this.getElementsByTagName("tr", this.getElementById("__customerTable", this.iframe.document));
	if(tds.length > 0){
		calendar._trLength = tds.length;
		}
	for(var i = 0; i < tds.length; i++) {
  		tds[i].style.backgroundColor = calendar.colors["bg_over"];
		tds[i].onclick = null;
		tds[i].onmouseover = null;
		tds[i].onmouseout = null;
		var value=tds[i].innerHTML;
		tds[i].onclick = function () {
			if (calendar.dataControl){
				calendar.dataControl.value = this.cells[1].innerHTML;
                document.getElementById(prefix+"_country_id_"+countryid).value = this.cells[0].innerHTML;
			}
			calendar.hide();
		}
		tds[i].onmouseover = function () {this.style.backgroundColor = calendar.colors["bg_out"];}
		tds[i].onmouseout  = function () {this.style.backgroundColor = calendar.colors["bg_over"];}
	}//end for
};

Customer.prototype.getElementsByTagName = function(tagName, object){
	object = object || document;
	return document.getElementsByTagName ? object.getElementsByTagName(tagName) : document.all.tags(tagName);
};

Customer.prototype.getElementById = function(id, object){
	object = object || document;
	return document.getElementById ? object.getElementById(id) : document.all(id);
};

Customer.prototype.show = function (targetObject, eventObject , popuControl) {
	if (this.panel.style.visibility == "visible") {
		this.panel.style.visibility = "hidden";
	}
	if (!targetObject){
		throw new Error("arguments[0] is necessary!")
	}
	this.dataControl = targetObject;
	getCustomersByType(this.dataControl.value);
	_targetObject = targetObject;
	popuControl = popuControl || targetObject;
	this.draw();
	customer.bindCustomer();
	var xy = this.getAbsPoint(popuControl);
	this.panel.style.left = xy.x + 1 + "px";
	this.panel.style.top = (xy.y + targetObject.offsetHeight) + "px";
	this.layout();
	this.panel.style.visibility = "visible";
};
Customer.prototype.layout = function(){
	
	if(this._trLength > 10){
		this._trLength = 10;
		}
	var w = this.dataControl.offsetWidth;
	var h = document.getElementById("__customerPanel").style.height = this._trLength*15 + 3;
	document.getElementById("__customerPanel").style.width=w +"px";
	document.getElementById("__customerPanel").style.height = h+"px";
	
	this.iframe.document.getElementById("__customerPanel2").style.width=w +"px";
	this.iframe.document.getElementById("__customerPanel2").style.height = h+"px";
};
Customer.prototype.hide = function() {
	this.panel.style.visibility = "hidden";
};

Customer.prototype.getAbsPoint = function (e){
	var x = e.offsetLeft;
	var y = e.offsetTop;
	while(e = e.offsetParent){
		x += e.offsetLeft;
		y += e.offsetTop;
	}
	return {"x": x, "y": y};
};
document.onclick = function(){
	if(_targetObject!=window.event.srcElement&&window.event.srcElement!=document.getElementById("__customerPanel2"))
	{
		document.getElementById("__customerPanel").style.visibility = "hidden";
	}
	//<$$$$$
	try{
		//calendar.js�е�onclick����  2���ļ�һ�����ʱ  ���ú���ص�onclick����
		if(objectId!=window.event.srcElement&&window.event.srcElement!=document.getElementById("goPrevMonthButton")
				  &&window.event.srcElement!=document.getElementById("yearSelect")&&window.event.srcElement!=document.getElementById("monthSelect")
				  &&window.event.srcElement!=document.getElementById("goNextMonthButton")&&window.event.srcElement!=document.getElementById("__customerTable"))
		  {
		       document.getElementById("__calendarPanel").style.visibility = "hidden";
		  }
	}catch(e){
		  
	}
	//$$$$$>
};

document.writeln('<div id="__customerPanel" style="position:absolute;overflow-x: hidden; overflow-y:auto;visibility:hidden;z-index:9999;background:#FFFFFF;border:1px solid #666666;">');
document.writeln('<iframe name="__customerIframe" id="__customerIframe" width="100%" height="100%" scrolling="no" frameborder="0" style="margin:0px;"><\/iframe>');
var __ci = window.frames['__customerIframe'];
//__ci.document.writeln('<!DOCTYPE html PUBLIC "-\/\/W3C\/\/DTD XHTML 1.0 Transitional\/\/EN" "http:\/\/www.w3.org\/TR\/xhtml1\/DTD\/xhtml1-transitional.dtd">');
//__ci.document.writeln('<html xmlns="http:\/\/www.w3.org\/1999\/xhtml">');
__ci.document.writeln('<html>');
__ci.document.writeln('<head>');
__ci.document.writeln('<title>Web customer(UTF-8) Written By KimSoft<\/title>');
__ci.document.writeln('<style type="text\/css">');
__ci.document.writeln('<!--');
__ci.document.writeln('body {font-size:12px;margin:0px;text-align:left;}');
__ci.document.writeln('form {margin:0px;}');
__ci.document.writeln('select {font-size:12px;background-color:#FFFFFF;}');
__ci.document.writeln('table {border:0px solid #CCCCCC;background-color:#FFFFFF;}');
__ci.document.writeln('td {font-size:12px;text-align:left;}');
__ci.document.writeln('td.normal {background-color:#FFFFFF;cursor:pointer;}');
__ci.document.writeln('-->');
__ci.document.writeln('<\/style>');
__ci.document.writeln('<\/head>');
__ci.document.writeln('<body>');
__ci.document.writeln('<\/body>');
__ci.document.writeln('<\/html>');
__ci.document.close();
document.writeln('<\/div>');
var customer = new Customer();
//-->