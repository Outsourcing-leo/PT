<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="org.tnt.pt.util.PTPARAMETERS"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>New PT-Summary Info</title>
<link href="${ctx}/static/styles/main.css" type="text/css" rel="stylesheet" />
<script src="${ctx}/static/jquery/1.7.2/jquery.min.js" type="text/javascript"></script>
</head>

<style type="text/css">
<!--
	.modify tr th, .modify tr td {
	text-align:left;
	font-size:14px;
}
	.modify tr th {
	background:#EFEFEF;
}
-->
</style>
<script language="JavaScript">
	var date = new Date();
	document.getElementById('appDate').value=date.getFullYear()+'-'+(date.getMonth()+1)+'-'+date.getDate();	
</script>
<body>
<form action="#" id="summaryInfo" method="post">
	<div style="padding:5px;">
  <h4 class="title">New PT-Summary Info</h4>
  <%--***************隐藏域********** --%>
    <input type="hidden" value="${business.customerId}" name="business.customerId">
    <input type="hidden" value="${business.id}" name="business.id">
    <input type="hidden" value="${business.zoneType}" name="business.zoneType">
    <%--***************隐藏域********** --%>
  <table class="modify">
    <tr>
    
      <th>Application Date:</th>
      <td><fmt:formatDate value="${business.applicationDate}" pattern="yyyy-MM-dd" /></td>
      <th>Depot:</th>
      <td>${business.depotCode}</td>
    </tr>
    <tr>
      <th>A new PT Customer?</th>
      <td>${business.isNewCus}</td>
      <th>Territory:</th>
      <td>${business.territory}</td>
    </tr>
    <tr>
	  <th>PT Application Reference #: </th>
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
      <td colspan="3">${customer.industry}</td>
        
    </tr>
    <tr>
      <th>Current Service Provider</th>
      <td colspan="3">${customer.serviceProvider}</td>
    </tr>
   
  </table>
  <table class="modify">
    <tr>
      <th width="45%">Is the customer on fuel surcharge exemption or deduction now?</th>
      <td>${customer.isFuleDeduction }</td>
      <th width="20%">the Current fuel surcharge</th>
      <td>${customer.fuelSurcharge }</td>
    </tr>
    <tr>
      <th width="45%">Are you requesting for fuel surcharge exemption or deduction now?</th>
      <td>${customer.isReq}</td>
      <th width="20%">the fuel surcharge requested</th>
      <td>${customer.reqFuelSurcharge}</td>
    </tr>
    </tr>
    
  </table>
  <table class="modify">
    <tr>
      <th>Term of Payment:</th>
      <td colspan="3">${customer.payment}</td>
    </tr>
    <tr>
      <th>Is customer a mainly document sender?</th>
      <td>${business.isDocumentSender}</td>
      <th width="15%">Cons/Stop</th>
      <td>${business.consStop}</td>
    </tr>
     <tr>
      <th width="30%">Prdouct Description(eg:digital cameral)</th>
      <td colspan="3">${business.description}</td>
      
    </tr>
     <tr>
      <th width="20%">Reason for the PT:</th>
      <td colspan="3">${business.reson}</td>
    </tr>
  </table>
  </div>
 <br />
 <div style="padding:5px;">
 <table>
 <tr>
 <td style="background-color:#F5C07B">Summary </td><td style="background-color:#EFEFEF">Rev(CNY)</td><td>${zoneSummary.revM}/M</td>
 <td>${zoneSummary.revY}/Y</td><td style="background-color:#EFEFEF">Cons</td><td>${zoneSummary.consM}/M</td><td>${zoneSummary.consY}/Y</td>
  <td style="background-color:#EFEFEF">Kilo(kg) </td><td>${zoneSummary.kiloM}/M</td><td>${zoneSummary.kiloY}/Y</td>
 </tr>
 </table>
 </div>
 <br />
<div style="padding:0 5px 5px 5px;">
<div class="clearboth"> </div>
<table class="table_B" width="100%">
        <thead>
			<tr align="center">
                <th colspan="7" style="text-align:center;">Summary by GEO Zone</th>
            </tr>
			<tr align="center" >
                <th rowspan="2" style="text-align:center;">GEO Zone</th>
				<th colspan="2" style="text-align:center;">Rev(CNY)</th>
				<th colspan="2" style="text-align:center;">Cons</th>
				<th colspan="2" style="text-align:center;">Kilo(kg)</th>
            </tr>
            <tr>
                <th>Per Month</th>
                <th>Per Year</th>
                <th>Per Month</th>
                <th>Per Year</th>
				<th>Per Month</th>
                <th>Per Year</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${geoSummaryList}" var="geoSummary">
				<tr>
					<td>${geoSummary.geoZone}</td>
					<td>${geoSummary.revM}</td>
					<td>${geoSummary.revY}</td>
					<td>${geoSummary.consM}</td>
					<td>${geoSummary.consY}</td>
					<td>${geoSummary.kiloM}</td>
					<td>${geoSummary.kiloY}</td>
			   </tr>
		 </c:forEach>
          </tbody>
</table>
<br>
<table class="table_B" width="100%">
       <thead>
			<tr align="center">
                <th colspan="7" style="text-align:center;">Summary by PT Zone</th>
            </tr>
			<tr align="center" >
                <th rowspan="2" style="text-align:center;">PT Zone</th>
				<th colspan="2" style="text-align:center;">Rev(CNY)</th>
				<th colspan="2" style="text-align:center;">Cons</th>
				<th colspan="2" style="text-align:center;">Kilo(kg)</th>
            </tr>
            <tr>
                <th>Per Month</th>
                <th>Per Year</th>
                <th>Per Month</th>
                <th>Per Year</th>
				<th>Per Month</th>
                <th>Per Year</th>
            </tr>
        </thead>
        <tbody>
        <c:forEach items="${zoneSummaryList}" var="zoneSummary">
				<tr>
					<td>${zoneSummary.zoneType}</td>
					<td>${zoneSummary.revM}</td>
					<td>${zoneSummary.revY}</td>
					<td>${zoneSummary.consM}</td>
					<td>${zoneSummary.consY}</td>
					<td>${zoneSummary.kiloM}</td>
					<td>${zoneSummary.kiloY}</td>
			   </tr>
		 </c:forEach>
          </tbody>
</table>
<br>
<table class="table_B" width="100%">
        <thead>
			<tr align="center">
                <th colspan="15" style="text-align:left;">Discounts Requested</th>
            </tr>
            <tr>
                <th>PRODUCT</th>
				<th width="8%"> Chargeable Weight(kg)</th>
                <c:forEach items="${zoneGroupList}" var="zoneGroup" begin="0">
						<th>${zoneGroup.zone}</th>
				</c:forEach>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${documentList}" var="weightBand">
				<tr>
					<td>${weightBand.name}</td>
					<td>${weightBand.chargeableWeight}</td>
					<c:forEach items="${zoneGroupList}" var="zoneGroup" begin="0">
						<c:set var="key">${weightBand.id}_${zoneGroup.id}</c:set>
						<td>${discountMap[key]}</td>
				   </c:forEach>
			   </tr>
			</c:forEach>
        </tbody>
    </table>
	<br />
<table class="table_B" width="100%">
        <thead>
			<tr align="center">
                <th colspan="15" style="text-align:left;">Discounts Profile-15N/12N/10N/09N</th>
            </tr>
            <tr>
                <th>Weightband</th>
				<th  width="8%"> Chargeable Weight(kg)</th>
                 <c:forEach items="${zoneGroupList}" var="zoneGroup" begin="0">
						<th>${zoneGroup.zone}</th>
				</c:forEach>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${ndocumentList}" var="weightBand">
				<tr>
					<td>${weightBand.name}</td>
					<td>${weightBand.chargeableWeight}</td>
					<c:forEach items="${zoneGroupList}" var="zoneGroup" begin="0">
						<c:set var="key">${weightBand.id}_${zoneGroup.id}</c:set>
						<td>${discountMap[key]}</td>
				   </c:forEach>
			   </tr>
			  </c:forEach>
          </tbody>
</table>
<br />
<table class="table_B" width="100%">
        <thead>
			<tr align="center">
                <th colspan="15" style="text-align:left;">Discounts Profile-48N</th>
            </tr>
            <tr>
                <th>Weightband</th>
				<th  width="8%"> Chargeable Weight(kg)</th>
                <c:forEach items="${zoneGroupList}" var="zoneGroup" begin="0">
						<th>${zoneGroup.zone}</th>
				</c:forEach>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${eonomyList}" var="weightBand">
				<tr>
					<td>${weightBand.name}</td>
					<td>${weightBand.chargeableWeight}</td>
					<c:forEach items="${zoneGroupList}" var="zoneGroup" begin="0">
						<c:set var="key">${weightBand.id}_${zoneGroup.id}</c:set>
						<td>${discountMap[key]}</td>
				   </c:forEach>
			   </tr>
			  </c:forEach>
          </tbody>
</table>
<br>
<table class="table_B" width="100%">
        <thead>
			<tr align="center">
                <th colspan="2" style="text-align:left;">Reason For The PT</th>
            </tr>
			
        </thead>
        <tbody>
            <tr>
                <td width="20%">Reason For The PT</td>
                <td>${business.reson}</td>
            </tr>
          </tbody>
</table>
<br />
  <div style="text-align: center">
  <input type="button" value="Previous" class="cls-button" id="Previous" /> 
   	&nbsp;&nbsp;&nbsp;<input type="button" value="Submit" class="cls-button" id="Submit"/>
   	 &nbsp;&nbsp;&nbsp;<input type="button" value="Close" class="cls-button" onclick="window.location.href='index.html';"/>
   </div>
   <input type="hidden" id="isFollow" value="${isFollow}" name="isFollow">
   <input type="hidden" id="payment" value="${payment}" name="payment">
</form>
</div>
<script type="text/javascript">
	$(function(){
		 $("#Previous").click(function(){
			 if($('#isFollow').val()=='NO'&&$('#payment').val()=='<%=PTPARAMETERS.PAYMENT[1]%>'){
				 $('#payment').val('');
				 $("#summaryInfo").attr('action',"${ctx}/ptModify/summaryInfo");
				 $("#summaryInfo").submit();
              }else{
            	  $("#summaryInfo").attr('action',"${ctx}/ptModify/consProfile");
     			 $("#summaryInfo").submit();
              }
			 
		});
	});

	$(function(){
		 $("#Submit").click(function(){
			 if($('#isFollow').val()=='NO'&&$('#payment').val()=='<%=PTPARAMETERS.PAYMENT[0]%>'){
              	$("#consignment").attr('action',"${ctx}/ptCreate/summaryInfo");
              	$("#consignment").submit();
              }else{
            	  $.ajax({
  	                type:"GET",
  	                url:"${ctx}/ptCreate/finishPT",
  	                dataType:"text",      
  	                contentType:"application/json",   
  	                data:{businessId:'${business.id}'},
  	                success:function(data){
  	                	alert(data);
  	                },
  	                error:function(e) {
  	                    alert("error："+e);
  	                }
  	            });
              }
	            
		});
	});
</script>
</body>
</html>
