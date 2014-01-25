<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%
	String path = request.getContextPath();
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>New PT-Summary Info</title>
<link href="${ctx}/static/styles/main.css" type="text/css" rel="stylesheet" />
<script src="${ctx}/static/jquery/1.7.2/jquery.min.js" type="text/javascript"></script>
<script src="${ctx}/static/jquery-validation/1.10.0/jquery.validate.min.js" type="text/javascript"></script>
<link rel="stylesheet" href="${ctx}/uploadify/uploadify.css" type="text/css"></link>
<script type="text/javascript" src="${ctx}/uploadify/jquery.uploadify-3.1.min.js"></script>
</head>

<style type="text/css">
	

</style>

<body>
<form action="#${ctx}/ptApprove/ptAnalysing" method="post" id="analysing">
	<div style="padding:5px;">
  <h4 class="title">New PT-Summary Info</h4>
  
  <table class="modify">
    <tr>
      <th>Application Date:</th>
      <td><span id="appDate"><fmt:formatDate value="${business.applicationDate}" pattern="yyyy-MM-dd"/></span></td>
      <th>Depot:</th>
      <td>SHA<input  type="hidden" name="business.depotCode" value="SHA"/></td>
    </tr>
    <tr>
      <th>A new PT Customer?</th>
      <td>${business.isNewCus}</td>
      <th>Territory:</th>
      <td>${business.territory}</td>
    </tr>
    <tr>
      <th>PT Application Reference #:</th>
      <td>${business.applicationReference}</td>
      <th>Account #:</th>
      <td>${customer.account}</td>
      
    </tr>
    <tr>
      <th>Customer Name</th>
      <td>${customer.cusName}</td>
      <th>Channel:</th>
      <td>${customer.channel}</td>
    </tr>
    <tr>
      <th>Industry</th>
      <td colspan="1">ServiceIndustry(Advertising,Media,Agency,Laws)</td>
    </tr>
    <tr>
      <th>Current Service Provider</th>
      <td colspan="3">${customer.serviceProvider}</td>
    </tr>
  </table>
  <table class="modify">
    <tr>
      <th rowspan="2">Is the customer on fuel surcharge exemption or deduction now?</th>
      <td rowspan="2">${customer.isFuleDeduction}</td>
      <th>the Current fuel surcharge</th>
      <td>Per FS Index
      <input type="hidden" name="customer.fuelSurcharge" value="Per FS Index"/>
      <input type="hidden" name="customer.reqFuelSurcharge" value="Per FS Index"/>
      <input type="hidden" name="customer.isReq" value="NO"/></td>
    </tr>
    <tr>
     
      <th>the fuel surcharge requested</th>
      <td>Per FS Index</td>
    </tr>
    </tr>
    
  </table>
  <table class="modify">
    <tr>
      <th ><span><B> Terms  of  Payments:</B></span></th>
      <td colspan="3">${customer.payment}</td>
    </tr>
    <tr>
      <th>Is customer a mainly document sender?</th>
      <td>${business.isDocumentSender}</td>
      <th>Cons/Stop</th>
      <td>${business.consStop}</td>
    </tr>
     <tr>
      <th>Prdouct Description(eg:digital cameral)</th>
      <td colspan="1">${business.description}</td>
      <th>Weight Range</th>
      <td >${business.weightRange}</td>
    </tr>
     <tr>
      <th>Reason for the PT:</th>
      <td colspan="3">${business.reson}</td>
    </tr>
  </table>
  </div>
 <br />
 <div style="padding:5px;height:165px;">
 	<div style="padding:2px 2px 2px 2px">
		<span style="background-color:#F5C07B;">Commercial - Dynamic Analysis Matrix &nbsp;&nbsp;</span>
	</div>
	<div style="padding:2px 2px 2px 2px">
		<div style="float:left;width:35%; text-align:left;">Analysis Fields &nbsp;&nbsp;</div>
		<div style="float:left;width:15%;text-align:center;"></div>
		<div style="float:left;width:30%;text-align:right;">Selected Analysis Fields </div>
		<div style="float:right;width:20%;text-align:left;">&nbsp;&nbsp;</div>
	</div>
	<div style="padding:2px 2px 2px 2px">
		<div style="float:left;width:35%; text-align:left;">
			<div style="width:80%;height:130px;text-align:left;border-style:solid;border-width:1pt;">
			<ul id="ul1" style="float:left;width:40%;list-style:none;">
			<li id="liOne1" >Product</li>
			<li id="liOne2">PT Zone</li>
			<li id="liOne3">WeightBand</li>
			<li id="liOne4">Country</li>
			<li id="liOne5">GEO Zone</li>
			<li id="liOne6">TOP</li>
			<li id="liOne7">Product Type</li>
			<!-- <li id="liOne7">TOP</li> -->
			<!-- <li id="liOne3">Product Type</li>-->
			</ul>
			</div>
		<br /><br />
		</div>
		<div style="float:left;width:15%;text-align:left;">
		<br /><br />
		<input type="button" value="  > " class="cls-button" id="addLi"/><br /><br />
		<input type="button" value="  < " class="cls-button" id="removeLi"/>
		</div>
		<div style="float:left;width:30%;text-align:left;">
		<div style="width:80%;height:130px;text-align:left;border-style:solid;border-width:1pt;">
			<ul id="ul2" style="float:left;width:40%;"></ul>
				<li id="liTwo1" style="display:none;">Product</li>
				<li id="liTwo2" style="display:none;">PT Zone</li>
				<li id="liTwo3" style="display:none;">WeightBand</li>
				<li id="liTwo4" style="display:none;">Country</li>
				<li id="liTwo5" style="display:none;">GEO Zone</li>
				<li id="liTwo6" style="display:none;">TOP</li>
				<li id="liTwo7" style="display:none;">Product Type</li>
				<!-- <li id="liOne7">TOP</li> -->
				<!-- <li id="liOne3">Product Type</li>-->
			</div>
		<br /><br />
		</div>
		<div style="float:right;width:20%;text-align:left;">
		<br />
			<input type="button" id="Caculate" value="Caculate" class="cls-button"/>&nbsp;&nbsp;&nbsp;
			<input type="button" id="Export" value="Export" class="cls-button"/><br /><br />
			<input type="button" id="Adjusts" value="Adjust Cost" class="cls-button"/><br /><br />
			<input type="button" id="Review" value="Review" class="cls-button"/>
		</div>
	</div>
 </div>
 <br /><BR />
 <%--<BR />
 <div style="padding:0 5px 5px 5px;">
 	
	<BR />
	include subtotal &nbsp;&nbsp;<input type="checkbox" checked="checked"/>
 </div>
 --%><br />
<div style="padding:0 5px 5px 5px;">
<div class="clearboth"> </div>
<div id="hide" style="OVERFLOW-Y:auto;PADDING-LEFT:0px;SCROLLBAR-FACE-COLOR:#ecf7ff;PADDING-BOTTOM:0px;SCROLLBAR-HIGHLIGHT-COLOR:#919192;
		OVERFLOW:auto;WIDTH:100%;SCROLLBAR-SHADOW-COLOR:#919192;COLOR:blue;LINE-HEIGHT:100%;height:100px;display:none;">
<iframe name="show_review_iframe" id="show_review_iframe" width="100%" height="100" frameborder="0" scrolling="auto" ></iframe>
<div style="text-align:right">
<span style="background-color:#EEEEEE;">Total (After Cost Adjustment)&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;19.50%</span>
</div>
<br>
</div>

<div>
	<span style="width:30%;height:50px; background-color:#EEEEEE;">
		Commercial's Comment :
	</span>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<textarea style="width:70%;height:50px;" id="examOppion"></textarea>
</div>
<br />
<div>
	<span style="width:30%;height:30px; background-color:#EEEEEE;">
		Evidence for Approval
	    or Rejection :
	</span>
	&nbsp;&nbsp;&nbsp;&nbsp;
	<textarea style="width:70%;height:30px;" id="fileName">
	
	</textarea>
</div>
<br />
<div align="left">
	<input type="file" name="Browser" id="file_upload" />
	<a href="javascript:$('#file_upload').uploadify('upload','*')">upload</a>&nbsp;   
        <a href="javascript:$('#file_upload').uploadify('cancel', '*')">uploadCancel</a> 
</div>
<hr />
<br />
  <div style="text-align: center">
  <input type="button" value="Confirm" class="cls-button" id="Confirm" /> 
   	&nbsp;&nbsp;&nbsp;<input type="button" value="Reject" class="cls-button" id="Reject" />
   	 &nbsp;&nbsp;&nbsp;<input type="button" value="Adjust" class="cls-button" id="Adjust" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	 &nbsp;&nbsp;&nbsp;<input type="button" value="Return to PT List" class="cls-button" onclick="javascript:window.history.back();"/>
	 &nbsp;&nbsp;&nbsp;<input type="button" value="Close" class="cls-button" onclick="javascript:window.history.back();"/>
   </div>
   <input type="hidden" id="hiddenID" value="${business.id}" name="business.id">
</form>
</div>
</body>
<script language="JavaScript">

$(function() {

	var businessId = document.getElementById("hiddenID").value;
    
	$("#Reject").click(function(){
        var examOppion = document.getElementById("examOppion").value;
        $.ajax({
            type:"POST",
            url:"${ctx}/ptApprove/reject",
            data:"id="+businessId+"&state=commercialFail"+"&examOppion="+examOppion,
            success:function(data){
            	alert('Reject Success！');
            	window.history.back();
            },error:function(e) {
                alert("error："+e);
            }
        });
    });
    var addIndex=1;
    
    var removeIndex=1;
    
    $("#addLi").click(function(){
    	if(addIndex>7) return;
        var addId = "liOne" + addIndex;
        var removeId = "liTwo" + addIndex;
    	document.getElementById(addId).style.cssText="display:none;";
    	document.getElementById(removeId).style.cssText="display:block;";
    	addIndex++;
    	removeIndex=1;
    });
    
    $("#removeLi").click(function(){
    	if(removeIndex>7) return;
    	var addId = "liOne" + removeIndex;
        var removeId = "liTwo" + removeIndex;
    	document.getElementById(addId).style.cssText="display:block;";
    	document.getElementById(removeId).style.cssText="display:none;";
    	removeIndex++;
    	addIndex=1;
    });
    
    $("#Caculate").click(function(){
    	var liSelected = $("#ul1 li:hidden");
    	if(liSelected.length==0){
    		alert('Please select the Analysis Fields first! ');return;
    	}
    	
    });
    
    $("#Export").click(function(){
    	var multichoised = "";
    	var liSelected = $("#ul1 li:hidden");
    	if(liSelected.length==0){
    		alert('Please select the Analysis Fields first! ');return;
    	}
    	for(var i=0;i<liSelected.length;i++){
    		multichoised = multichoised+ ',' +liSelected.eq(i).text();
    	}
    	document.forms[0].action="${ctx}/ptApprove/exportBatchExcel?id="+businessId+"&multichoised="+multichoised;
		document.forms[0].submit();
    });
    
    $("#Review").click(function(){
    	var multichoised = "";
    	var liSelected = $("#ul1 li:hidden");
    	if(liSelected.length==0){
    		alert('Please select the Analysis Fields first! ');return;
    	}
    	for(var i=0;i<liSelected.length;i++){
    		multichoised = multichoised+ ',' +liSelected.eq(i).text();
    	}
    	document.getElementById("hide").style.cssText="display:block";
    	show_review_iframe.location = "${ctx}/ptApprove/review?id="+businessId+"&multichoised="+multichoised;
    	//$("#analysing").attr('action',"${ctx}/ptApprove/review?id="+document.getElementById("hiddenID").value+"&multichoised="+multichoised);
     	//$("#analysing").submit();
     });
    
    $("#Confirm").click(function(){
    	var examOppion = document.getElementById("examOppion").value;
    	if(examOppion==""){
    		if(confirm("Are you sure confirm without oppinon?"))
    		{ 
    			return true;
    			document.getElementById("examOppion").focus();
    		}else{
    		}
    		
    	}
    	$("#analysing").attr('action',"${ctx}/ptApprove/confirmCus?id="+businessId+"&examOppion="+examOppion);
     	$("#analysing").submit();
     });
 	
    $("#Adjust").click(function(){
    	$("#analysing").attr('action',"${ctx}/ptApprove/adjust?id="+businessId);
     	$("#analysing").submit();
     });
    
    $("#file_upload").uploadify({
    	'height'        : 27, 
    	'width'         : 80,  
    	'buttonText'    : 'browse',
        'swf'           : '<%=path%>/uploadify/uploadify.swf',
        'uploader'      : '${ctx}/ptApprove/uploadFile/'+businessId,
        'auto'          : false,
        //'fileTypeExts'  : '*.xls',
        'formData'      : {'userName':'','qq':''},
        'onUploadSuccess': function(file, data)
        {		
        	document.getElementById("fileName").innerText=file.name;
        }
    });
    
    $("#deleteFile").click(function(){
    	var fileName = document.getElementById("fileName").innerText;
        $.ajax({
            type:"POST",
            url:"${ctx}/ptApprove/deleteFile",
            data:"id="+businessId+"&fileName="+fileName,
            success:function(data){
            	alert("delete success!");
            	document.getElementById("fileName").innerText="";
            },error:function(e) {
                alert("error："+e);
            }
        });
    });
    
});

</script>
</html>
