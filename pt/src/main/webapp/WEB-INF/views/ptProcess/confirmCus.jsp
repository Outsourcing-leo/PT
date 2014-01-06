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
<link rel="stylesheet" href="${ctx}/uploadify/uploadify.css" type="text/css"></link>
<script type="text/javascript" src="${ctx}/uploadify/jquery.uploadify-3.1.min.js"></script>

<script type="text/javascript" src="${ctx}/static/js/calendar.js"></script>
</head>

<style type="text/css">
	

</style>

<body>
<form action="#${ctx}/ptApprove/ptAnalysing" method="post" id="analysing">
	<div style="padding:5px;">
  <h4 class="title">Confirm a PT</h4>
  
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
    <tr>
      <th>Divison</th>
      <td><input type="text" value="G"/></td>
      <th width="15%">Rate Category</th>
      <td><input type="text" value="07PTO"/></td>
    </tr>
	<tr>
      <th>Effective Date: </th>
      <td colspan="3"><input type="text" id="effDate" onclick="new Calendar().show(this,this)"/></td>
      
    </tr>
	<tr>
      <th>End Date: </th>
      <td><input type="text" value="Open"/></td>
      <th width="15%">Review Date: </th>
      <td><input type="text" value="Open"/></td>
    </tr>
  </table>
  </div>
 <br />
  <div style="text-align: center">
  <input type="button" value="Confirm" id="Confirm" class="cls-button" /> 
   	&nbsp;&nbsp;&nbsp;
	<input type="button" value="Export" class="cls-button" />
   	&nbsp;&nbsp;&nbsp;
	<input type="button" value="Previous" class="cls-button" onclick="javascript:window.history.back();"/>
	&nbsp;&nbsp;&nbsp;
	<input type="button" value="Close" class="cls-button" onclick="javascript:window.history.back();"/>
   </div>
   <input type="hidden" id="hiddenID" value="${business.id}" name="business.id"/>
   <input type="hidden" id="examOppion" value="${businessVO.examOppion}" name="examOppion"/>
</form>
</div>
</body>
<script language="JavaScript">
var date = new Date();
document.getElementById('effDate').value=date.getFullYear()+'-'+(date.getMonth()+1)+'-'+date.getDate();	

$(function() {
	$("#Confirm").click(function(){
        var examOppion = document.getElementById("examOppion").value;
            $.ajax({
                type:"POST",
                url:"${ctx}/ptApprove/commercialConfirm",
                data:"id="+document.getElementById("hiddenID").value+"&examOppion="+examOppion,
                success:function(data){
                	alert('提交成功！');
                },error:function(e) {
                    alert("error："+e);
                }
            });
        });
});
</script>
</html>
