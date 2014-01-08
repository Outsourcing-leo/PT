<%@ page language="java" import="java.util.*,org.springside.modules.utils.DateProvider" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>New PT-Customer Description</title>
<link href="${ctx}/static/styles/main.css" type="text/css" rel="stylesheet" />
<script src="${ctx}/static/jquery/1.7.2/jquery.min.js" type="text/javascript"></script>
<script src="${ctx}/static/jquery-validation/1.10.0/jquery.validate.min.js" type="text/javascript"></script>
<script src="${ctx}/static/jquery-validation/1.10.0/messages_bs_en.js" type="text/javascript"></script>
<link href="${ctx}/static/jquery-validation/1.10.0/validate.css" type="text/css" rel="stylesheet" />
<script>
		$(document).ready(function() {
			//为inputForm注册validate函数
			$("#busCus").validate();
		});
		window.confirm = function(str) {   
			str=str.replace(/\'/g,   "'&chr(39)&'").replace(/\r\n|\n|\r/g,   "'&VBCrLf&'");   
			execScript("ret=msgbox('"+str+"',52)","vbscript");
			return (ret); 
		}
		
</script>
</head>
<body>
<form action="#" method="post" id="busCus">
	<div style="padding:5px;">
  <h4 class="title">New PT-Customer Description</h4>
  <table class="modify">
    <tr>
      <th>Application Date:</th>
      <td><span id="appDate"></span></td>
      <th>Depot:</th>
      <td>SHA<input  type="hidden" name="business.depotCode" value="SHA"/></td>
    </tr>
    <tr>
      <th>A new PT Customer?</th>
      <td><select name="business.isNewCus" >
      	<option value="YES">YES</option>
      	<option value="NO">NO</option>
      	</select>	
      </td>
      <th>Territory:</th>
      <td><input type="text"  name="business.territory" class="required"/></td>
    </tr>
    <tr>
      <th>Account #:</th>
      <td><input type="text"  name="customer.account" class="required" maxlength="9"/></td>
      <td colspan="2" style="color:#FF9900">("7777777"refer to new customer)</td>
    </tr>
    <tr>
      <th>Customer Name</th>
      <td><input type="text" name="customer.cusName" class="required"/></td>
      <th>Channel:</th>
      <td><select name="customer.channel" >
          <option value="TSM">TSM</option>
        </select></td>
    </tr>
    <tr>
      <th>Industry</th>
      <td colspan="1">
      	<select name="customer.industry" >
          <option value="ServiceIndustry(Advertising,Media,Agency,Laws)">ServiceIndustry(Advertising,Media,Agency,Laws)</option>
        </select></td>
    </tr>
    <tr>
      <th>Current Service Provider</th>
      <td colspan="3"><input type="text" size="100" name="customer.serviceProvider" class="required"/></td>
    </tr>
   
  </table>
  <table class="modify">
    <tr>
      <th rowspan="2">Is the customer on fuel surcharge exemption or deduction now?</th>
      <td rowspan="2">
      	<select name="customer.isFuleDeduction">
         <option value="YES">YES</option>
      	 <option value="NO">NO</option>
        </select></td>
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
      <th style="background-color:yellow"><span><B> Terms  of  Payments:</B></span></th>
      <td colspan="3">
	  <select id="tp" name="customer.payment">
           <option value="Sender Pay">Sender Pay</option>
		   <option value="Receive Pay">Receive Pay</option>
		   <option value="both">both</option>
       </select></td>
    </tr>
    <tr>
      <th>Is customer a mainly document sender?</th>
      <td><select  name="business.isDocumentSender">
          <option value="NO">No</option>
          <option value="YES">YES</option>
        </select></td>
      <th>Cons/Stop</th>
      <td><input type="text"  name="business.consStop" class="required number"></td>
    </tr>
     <tr>
      <th>Prdouct Description(eg:digital cameral)</th>
      <td colspan="1"><input type="text" value="Test DC"  name="business.description" class="required"></td>
      <th>Weight Range</th>
      <td ><select name="business.weightRange">
      	<option value="1">0.5-5kg</option>
      	<option value="2">5-10kg</option>
      	</select></td>
    </tr>
     <tr>
      <th>Reason for the PT:</th>
      <td colspan="3">
      	<textarea style="width:700px; height:100px" name="business.reson" class="required">test only</textarea>
      </td>
    </tr>
  </table>
  <span style="color:blue">*Frames highlighted by yellow must be filled in</span>
  <hr />
  <br>
  <div style="text-align: center">
  <input type="button" value="Submit" class="cls-button" onclick="tothenext(this)" /> 
   	&nbsp;&nbsp;&nbsp;<input type="button" value="Copy" class="cls-button"  id="copy"  />
   	 &nbsp;&nbsp;&nbsp;<input type="button" value="Close" class="cls-button" onclick="window.location.href='index.html';"/>
   	<input type="hidden" id="isFollow" name="isFollow">
   </div>
</form>
</div>

</body>
<script type="text/javascript">

function tothenext(obj){
		if($('#tp').val()=='both'){
			   var v = confirm("Term of Payment is BOTH ?");{
			   if (v=='6') {
				   var v = confirm("Is Receive Pay follow Sender Pay ?");
				   if (v=='6') {
					   $("#isFollow").val("YES");
					   $("#busCus").attr('action','${ctx}/ptCreate/disConfirm');
					   $("#busCus").submit();
				   } else if(v=='7') {
					   $("#isFollow").val("NO");
					   $("#busCus").attr('action','${ctx}/ptCreate/disConfirm');
					   $("#busCus").submit();
				   }
			   } else if(v=='7') {
			   	 return;
			   }
			}
		}else{
			$("#busCus").attr('action','${ctx}/ptCreate/disConfirm');
			$("#busCus").submit();
		}
}

$(function(){
    $("#copy").click(function(){
     	$("#busCus").attr('action',"${ctx}/ptQuery/copy");
 		$("#busCus").submit();
    });
});

var date = new Date();
$('#appDate').html(date.getFullYear()+'-'+(date.getMonth()+1)+'-'+date.getDate());	
</script>
</html>
